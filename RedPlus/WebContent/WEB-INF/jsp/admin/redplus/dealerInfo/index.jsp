<!DOCTYPE HTML>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>${sessionScope.logon_user.systemName}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../common/import-tags.jsp"%>
<%@include file="../../common/import-static.jsp"%>
</head>
<body>


	<div class="container">
		<!-- 查询 -->
		<form id="searchForm" class="form-horizontal">
			<div class="row">
				<div class="control-group span7">
					<label class="control-label">callPhone：</label>
					<div class="controls">
						<input type="text" class="control-text"
							name="search_LIKE_callPhone">
					</div>
				</div>
				<div class="control-group span7">
					<label class="control-label">name：</label>
					<div class="controls">
						<input type="text" class="control-text" name="search_LIKE_name">
					</div>
				</div>
				
			</div>
			<div class="row">
				
				<div class="span3 offset2">
					<button type="button" id="btnSearch" class="button button-primary">搜索</button>
				</div>
			</div>
		</form>
		<!-- 修改新增 -->
		<div id="addOrUpdate" class="hide">
			<form id="addOrUpdateForm" class="form-horizontal">
				<div class="row">
					<div class="control-group span8">
						<label class="control-label"><s>*</s>callPhone</label>
						<div class="controls">
							<input name="callPhone" type="text" data-rules="{required:true}"
								class="input-normal control-text">
						</div>
					</div>
					<div class="control-group span8">
						<label class="control-label"><s>*</s>remark</label>
						<div class="controls">
							<input name="remark" type="text" data-rules="{required:true}"
								class="input-normal control-text">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="control-group span8">
						<label class="control-label"><s>*</s>name</label>
						<div class="controls">
							<input name="name" data-rules="{required:true}"
								class="input-normal control-text">
						</div>
					</div>
					<div class="control-group span8">

						<label class="control-label"><s>*</s>areacode</label>
						<div class="controls">

							<input name="areacode" data-rules="{required:true}"
								class="input-normal control-text">

						</div>
					</div>
				</div>

				<div class="row">
					<div class="control-group span8">

						<label class="control-label"><s>*</s>分销商级别</label>
						<div class="controls">
                            <select name="resv1">
						    <option value="1">一级分销商</option>
						    <option value="2">二级分销商</option>
						</div>
					</div>

					<div class="control-group span8">

						<label class="control-label"><s>*</s>上级</label>
						<div class="controls">
                            <select name="resv2" id="parent_re">
						     <option value="${area.id }">${area.areaname }</option>
						    </select>
							<input name="resv2" 
								class="input-normal control-text">

						</div>
					</div>
				</div>
				<div class="row">
					<div class="control-group span8">

						<label class="control-label">resv3</label>
						<div class="controls">

								<input name="resv3" 
								class="input-normal control-text">
						</div>
					</div>
					
					

					
					

				
				</div>

			
			</form>
		</div>
		<div class="search-grid-container">
			<div id="grid"></div>
		</div>



	</div>
	<script type="text/javascript">
	    function getTopDealer(){
	    	
	    }
		BUI.use(
						[ 'bui/ux/crudgrid', 'bui/form', 'bui/ux/savedialog' ],
						function(CrudGrid, Form, SaveDialog) {
							//定义页面权限
							var add = false, update = false, del = false, list = false;
							//"framwork:crudPermission"会根据用户的权限给add，update，del,list赋值
							<framwork:crudPermission resource="/back/dealerInfo"/>
							var columns = [
									{
										title : 'callPhone',
										dataIndex : 'callPhone',
										width : '5%'
									},
								
									{
										title : 'remark',
										dataIndex : 'remark',
										width : '15%'
									},
									{
										title : 'name',
										dataIndex : 'name',
										width : '15%'
									},
									{
										title : 'areacode',
										dataIndex : 'areacode',
										width : '15%'
									},
									{
										title : 'resv1',
										dataIndex : 'resv1',
										width : '20%',
										renderer : function(value) {
											if(value=="1"){
												return "一级分销商";
											}else if(value=="2"){
												return "二级分销商";
											}
										}
									},
									{
										title : 'resv2',
										dataIndex : 'resv2',
										width : '40%'
										
									}, {
										title : 'resv3',
										dataIndex : 'resv3',
										width : '20%'
									}

							];

							var crudGrid = new CrudGrid({
								entityName : '会员管理',
								storeUrl : '${ctx}/back/dealerInfo/list',
								addUrl : '${ctx}/back/dealerInfo/add',
								updateUrl : '${ctx}/back/dealerInfo/update',
								removeUrl : '${ctx}/back/dealerInfo/del',
								columns : columns,
								showAddBtn : add,
								showUpdateBtn : update,
								showRemoveBtn : del,
								addOrUpdateFormId : 'addOrUpdateForm',
								searchBtnId : 'btnSearch',
								dialogContentId : 'addOrUpdate',
								storeCfg:{
									sortInfo : {
				    	        		field : 'callPhone',
				    	        		direction : 'DESC' //升序ASC，降序DESC
				    	        	},
									
									
								}

							});

							var grid = crudGrid.get('grid');
							grid.on('cellclick',
									function(ev) {//定义点击行的出发事件
										var sender = $(ev.domTarget); //点击的Dom
										var record = ev.record;
										if (sender.hasClass('icon-lock')) {
											from = saveDialog.get('form');
											from.getField('id').set('value',
													record.id);
											saveDialog.update();
										}
									});

							//var addAdnUpdateForm = crudGrid.get('addOrUpdateForm');
							var beforeAddShow = function(dialog, form) {

							};
							crudGrid.on('beforeAddShow', beforeAddShow);

							var beforeUpdateShow = function(dialog, form,
									record) {

							};

							crudGrid.on('beforeUpdateShow', beforeUpdateShow);

							var Select = BUI.Select, Data = BUI.Data;

						});
	</script>

</body>
</html>
