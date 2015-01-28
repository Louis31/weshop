<!DOCTYPE HTML>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>${sessionScope.logon_user.systemName}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../common/import-tags.jsp"%>
<%@include file="../../common/import-static.jsp"%>
<script type="text/javascript">
    $(function() {
        // 创建AJAX方式上传文件
        var $uploadBtn = $("#icon_url");
        new AjaxUpload($uploadBtn, {
            action: '${ctx}/userImg/uploadSig',
            name: 'file',
            onSubmit: function(file, ext){
            },
            onComplete: function(file, response){
            	var dataObj=eval("("+response+")");//转换为json对象
            	if(dataObj!=null&&dataObj.success){
            		$("#icon_url").val(dataObj.msg);
            	}
            }
        });
    });
    </script>
</head>
<body>


	<div class="container">
		<!-- 查询 -->
		<form id="searchForm" class="form-horizontal">
			<div class="row">
				<div class="control-group span7">
					<label class="control-label">nickname：</label>
					<div class="controls">
						<input type="text" class="control-text"
							name="search_LIKE_nickname">
					</div>
				</div>
				<div class="control-group span7">
					<label class="control-label">loginname：</label>
					<div class="controls">
						<input type="text" class="control-text" name="search_LIKE_loginname">
					</div>
				</div>
				
				<div class="control-group span7">
					<label class="control-label">refreeUser：</label>
					<div class="controls">
						<input type="text" class="control-text" name="search_LIKE_refreeUser">
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
						<label class="control-label"><s>*</s>昵称</label>
						<div class="controls">
							<input name="nickname" type="text" data-rules="{required:true}"
								class="input-normal control-text">
						</div>
					</div>
					<div class="control-group span8">
						<label class="control-label"><s>*</s>年龄</label>
						<div class="controls">
							<input name="age" type="text" data-rules="{required:true}"
								class="input-normal control-text">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="control-group span8">
						<label class="control-label"><s>*</s>身高</label>
						<div class="controls">
							<input name="height" data-rules="{required:true}"
								class="input-normal control-text">
						</div>
					</div>
					<div class="control-group span8">

						<label class="control-label"><s>*</s>体重</label>
						<div class="controls">

							<input name="weight" data-rules="{required:true}"
								class="input-normal control-text">

						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="control-group span8">
						<label class="control-label"><s>*</s>爱好</label>
						<div class="controls">
							<input name="hobby" data-rules="{required:true}"
								class="input-normal control-text">
						</div>
					</div>
					<div class="control-group span9">

						<label class="control-label"><s>*</s>头像</label>
						<div class="controls">
							<input name="iconimg" id="icon_url" data-rules="{required:false}"
								class="input-normal control-text" value=""/>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="control-group span8">
						<label class="control-label"><s>*</s>地区</label>
						<div class="controls">
                           <select name="area">
						    <c:forEach var="area"   items="${areacode}"  varStatus="st" >
						    <option value="${area.id }">${area.areaname }</option>
						  </c:forEach>
						</select>
						</div>
					</div>
					<div class="control-group span8">

						<label class="control-label"><s>*</s>性别</label>
                        <div class="controls">
                                <select name="gender">
                                <option value="0">女</option>
								<option value="1">男</option>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="control-group span8">
						<label class="control-label"><s>*</s>电话</label>
						<div class="controls">
							<input name="telpone" data-rules="{required:true}"
								class="input-normal control-text">
						</div>
					</div>
					<div class="control-group span8">

						<label class="control-label"><s>*</s>coll</label>
						<div class="controls">

							<input name="coll" data-rules="{required:true}"
								class="input-normal control-text">

						</div>
					</div>
				</div>
				

				<div class="row">
					<div class="control-group span8">

						<label class="control-label"><s>*</s>登录名</label>
						<div class="controls">

							<input name="loginname" id="loginname" onblur="checkLoginName();" data-rules="{required:true}"
								class="input-normal control-text">

						</div>
					</div>

					<div class="control-group span8">

						<label class="control-label"><s>*</s>密码</label>
						<div class="controls">

							<input name="passwd" data-rules="{required:true}"
								class="input-normal control-text">

						</div>
					</div>
				</div>
				<div class="row">
					<div class="control-group span8">

						<label class="control-label"><s>*</s>上级号码</label>
						<div class="controls">

							<input name="refreeUser" value="13822219298" data-rules="{required:true}"
								class="input-normal control-text">

						</div>
					</div>
				
					<div class="control-group span8">

						<label class="control-label"><s>*</s>是否在线</label>
						<div class="controls">

							<select name="online">


								<option value="0">不在线</option>
								<option value="1">在线</option>



							</select>
						</div>
					</div>



				</div>

				<div class="row">
					<div class="control-group span8">

						<label class="control-label"><s>*</s>审核</label>
						<div class="controls">

							<select name="resv1">


								<option value="0">不可登陆</option>
								<option value="1">可登陆</option>



							</select>
						</div>
					</div>

					<div class="control-group span8">

						<label class="control-label"><s>*</s>标注</label>
						<div class="controls">

							<input name="remark" class="input-normal control-text">

						</div>
					</div>



				</div>
				<div class="row">
					<div class="control-group span8">

						<label class="control-label"><s>*</s>会员等级</label>
						<div class="controls">
                                <select name="resv2">
                                <option value="0">普通</option>
								<option value="1">银卡</option>
                                <option value="2">金卡</option>
                                <option value="3">白金</option>
                                <option value="4">钻石</option>
							</select>
						</div>
					</div>

					<div class="control-group span8">

						<label class="control-label"><s>*</s>分类</label>
						<div class="controls">
                           <select name="type_map">
                                <c:forEach var="te" items="${type}" varStatus="st" >
						        <option value="${te.id }">${te.typeName }</option>
						      </c:forEach>
							</select>
						</div>
					</div>



				</div>
				
				<div class="row" >
				   <div class="control-group span8">
						<label class="control-label"><s>*</s>财富</label>
						<div class="controls">
                            <input id="gold" name="gold" data-rules="{required:true}" class="input-normal control-text">
						</div>
					</div>
					<div class="control-group span8">
						<label class="control-label"><s>*</s>余额</label>
						<div class="controls">
						<input id="update_money" data-rules="{required:true}" name="update_money" value="0" class="input-normal control-text">
						</div>
					</div>
	
				</div>
				
				<div class="row" >
				   <div class="control-group span8">
						<label class="control-label"><s>*</s>排序</label>
						<div class="controls">
                            <input id="sort_value" name="sort_value" class="input-normal control-text">
						</div>
					</div>
				</div>

				<input type="hidden" name="id" value="">
			</form>
		</div>
		<div class="search-grid-container">
			<div id="grid"></div>
		</div>



	</div>
	<script type="text/javascript">
		BUI.use(
						[ 'bui/ux/crudgrid', 'bui/form', 'bui/ux/savedialog',
								'bui/common/page' ],
						function(CrudGrid, Form, SaveDialog) {
							//定义页面权限
							var add = false, update = false, del = false, list = false,distributor=false,update_image=false;
							//"framwork:crudPermission"会根据用户的权限给add，update，del,list赋值
							<framwork:crudPermission resource="/back/userInfo"/>
							var columns = [
									{
										title : 'ID',
										dataIndex : 'id',
										width : '5%'
									},

									{
										title : 'nickname',
										dataIndex : 'nickname',
										width : '15%'
									},
									{
										title : 'age',
										dataIndex : 'age',
										width : '15%'
									},
									{
										title : 'height',
										dataIndex : 'height',
										width : '15%'
									},
									{
										title : 'hobby',
										dataIndex : 'hobby',
										width : '20%'
									},
									{
										title : 'iconimg',
										dataIndex : 'iconimg',
										width : '40%',
										renderer : function(value) {
											if (value) {
												return '<img src="http://59.188.248.183//static'+value+'"/>';
											}
										}
									}, {
										title : 'area',
										dataIndex : 'area',
										width : '20%'
									}, {
										title : 'gender',
										dataIndex : 'gender',
										width : '20%'
									}, {
										title : 'telpone',
										dataIndex : 'telpone',
										width : '20%'
									},{
										title : 'loginname',
										dataIndex : 'loginname',
										width : '20%'
									}, {
										title : 'gender',
										dataIndex : 'gender',
										width : '20%'
									}, {
										title : 'refreeUser',
										dataIndex : 'refreeUser',
										width : '20%'

									}

							];

							var crudGrid = new CrudGrid(
									{
										entityName : '会员管理',
										storeUrl : '${ctx}/back/userInfo/list',
										addUrl : '${ctx}/back/userInfo/add',
										updateUrl : '${ctx}/back/userInfo/update',
										removeUrl : '${ctx}/back/userInfo/del',
										
										columns : columns,
										showAddBtn : add,
										showUpdateBtn : update,
										showRemoveBtn : del,
										addOrUpdateFormId : 'addOrUpdateForm',
										searchBtnId : 'btnSearch',
										dialogContentId : 'addOrUpdate',
										operationColumnRenderer : function(
												value, obj) {
											var editStr = '';
											alert(update_image);
											if (update_image) {
												editStr += '<span class="grid-command" title="新增图片"><i class="icon-plus"></i></span>';
											}
											if (distributor) {
												editStr += '<span class="grid-command" title="编辑礼品"><i class="icon-glass"></i></span>';
											}
											return editStr;
										}

									});

							var grid = crudGrid.get('grid');
							grid.on('cellclick', function(ev) {
								var sender = $(ev.domTarget); //点击的Dom
								var record = ev.record;
								if (sender.hasClass('icon-plus')) {
									addOrUpdateFunction('', record.id);

								}
								if (sender.hasClass('icon-glass')) {
									addGift('', record.id);

								}
							});

							var addOrUpdateFunction = function(event, id) {

								var title = '';
								var href = '${ctx}/upload/upload.jsp?uid=';
						
									title = '增加图片';
									href += '' + id;

									if (top.topManager) {
										//打开左侧菜单中配置过的页面
										top.topManager.openPage({
											id : 'main-menu',
											href : href,
											title : title
										});
									}
								
							}
							
							var checkMoneyFunction = function(event, id) {
								
							}
							
							var addGift = function(event, id) {

								var title = '';
								var href = '${ctx}//back/userGift/'+id;
						
									title = '编辑礼品';
							
									if (top.topManager) {
										//打开左侧菜单中配置过的页面
										top.topManager.openPage({
											id : 'main-menu2',
											href : href,
											title : title
										});
									}
								
							}
							

							var addOrUpdateFunction = function(event, id) {

								var title = '';
								var href = '${ctx}/upload/upload.jsp?uid=';
						
									title = '增加图片';
									href += '' + id;

									if (top.topManager) {
										//打开左侧菜单中配置过的页面
										top.topManager.openPage({
											id : 'main-menu',
											href : href,
											title : title
										});
									}
								
							}

							//var addAdnUpdateForm = crudGrid.get('addOrUpdateForm');
							var beforeAddShow = function(dialog, form) {
								 $("#update_money").val("0");
								 $("#gold").val("0");
							};
							crudGrid.on('beforeAddShow', beforeAddShow);

							var beforeUpdateShow = function(dialog, form,
									record) {
								 $("#update_money").val("0");
								 $("#gold").val("0");
                               $.ajax({
                                   type: "GET",
                                   url: "${ctx}/userMoney/getMoney",
                                   data: {uid:record.id},
                                   dataType: "json",
                                   success: function(data){
                                	   if(data.success){
                                		   $("#update_money").val(data.obj.money);
                                		   $("#gold").val(data.obj.gold);
                                	   }
                                   }
                               });
							};

							crudGrid.on('beforeUpdateShow', beforeUpdateShow);

							var Select = BUI.Select, Data = BUI.Data;

						});
		
		function checkLoginName(){
			if(""!=$("#loginname").val()){
			$.ajax({
                type: "GET",
                url: "${ctx}/userInfo/checkname",
                data: {loginname:$("#loginname").val()},
                dataType: "json",
                success: function(data){
             	   if(!data.success){
             		  alert("该登录名无效！");
             		 $("#loginname").val("")
             	   }else{
             		  alert("该登录名有效");
             	   }
                }
            });
		}
	}
	</script>

</body>
</html>
