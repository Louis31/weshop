<!DOCTYPE HTML>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>${sessionScope.logon_user.systemName}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="../../common/import-tags.jsp"%>
<%@include file="../../common/import-static.jsp"%>

<link href="${ctx}/static/common/css/newReply.css" rel="stylesheet"
	type="text/css" />


</head>
<body>




	<div class="container">
		<div class="row">
			<div class="span12">
				<div class="panel ">
					<div class="panel-header clearfix">
						<form id="searchForm" class="form-horizontal">
							<div class="row">
								<div class="control-group span8">
									<label class="control-label">名称：</label>
									<div class="controls">
										<input type="text" class="control-text"
											name="search_LIKE_giftname">
									</div>
								</div>
								<div class="span3 ">
									<button type="button" id="btnSearch"
										class="button button-primary">搜索</button>
								</div>
							</div>
						</form>
						<div class="search-grid-container" id="grid" style="width: 400px;">
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-head-borded "
				style="overflow: hidden; padding-left: 10px;">
				<form id="addOrUpdateForm1" class="form-horizontal">


					<input type="hidden" name="id" value="${userId}">

					<div id="page2"></div>
			</div>
		</div>
		</form>
		<div class="row form-actions actions-bar">
			<div class="span13 offset12 ">
				<button class="button  button-primary" id="saveBtn">保存</button>
				<button type="button" class="button" id="restBtn">重置</button>
			</div>
		</div>
	</div>




	<!-- 查询 -->



	<!-- 修改新增 -->


	<script type="text/javascript">
		BUI.use([ 'bui/ux/crudgrid','bui/form','bui/grid'],function(CrudGrid,Form,Grid) {
			var muban = " <li> <span class=\"puff_left txtlist_l\">#content#</span>  <span class=\"puff_right txtlist_r\"><img src=\"#picUrl#\" /></span></li>";
		
			var userId = "${userId}";
		
			var imgContent = [];
			//显示图文
			
			var prefix = "id";
			var muban = "<div id=\"#gift.id#\">#gift.giftname#<input type=\"hidden\"  name=\"gift.id\" value=\"#gift.id#\" />: <input type=\"text\"  value=\"#price#\"  name=\"price\" /> </div>";
			
	
			var arr = '${userGift}';
			if(arr){
	
				arr =eval(arr);
				disArr(arr);
			
			}
			
			
			var back_html=$("#page2").html();
			function disArr(arr){
				
				
				for(var i  = 0 , b = arr.length ; i < b ; i ++){
					
					
					  if($("#"+prefix+arr[i].gift.id).length  == 0){
					
					  var str = muban.replace("#gift.id#",prefix+arr[i].gift.id);
					   str = str.replace("#gift.id#", arr[i].gift.id);
						
					   str = str .replace("#gift.giftname#", arr[i].gift.giftname);
					   str = str .replace("#price#", arr[i].price);
						
					    var context = $(str);
					   $("#page2").append(context);
						
						  
						  
					  } 
				
				}
			}
		function addArr(arr){
			
			
			for(var i  = 0 , b = arr.length ; i < b ; i ++){
				
				
				  if($("#"+prefix+arr[i].id).length  == 0){
					
				  var str = muban.replace("#gift.id#", prefix+arr[i].id);
				   str = str.replace("#gift.id#", arr[i].id);
					
				   str = str .replace("#gift.giftname#", arr[i].giftname);
				   str = str .replace("#price#", 0);
				   var context = $(str);
					$("#page2").append(context);
					
					  
					  
				  } 
			
			}
		}
		
							
							 var form=	new Form.Form({
								  srcNode : '#addOrUpdateForm1'
								}).render();
									
						

							var columns = [{title : 'giftname',dataIndex : 'giftname',width : 100},
										 
									{title:'giftinfo',dataIndex:'giftinfo',width:'30%'},
									{ title : 'remark', dataIndex : 'remark',width : 50,renderer : BUI.Grid.Format.cutTextRenderer(10)} ];
							function addNewsRelay() {
								var newSelects = grid.getSelection();
								addArr(newSelects);
							}
						
							var defaultAction = [];
							defaultAction.push({
								text : '<i class="icon-plus"></i>添加礼物给用户',
								btnCls : 'button button-small',
								handler : addNewsRelay
							});
							var crudGrid = new CrudGrid({
								entityName : '礼品',
								storeUrl : '${ctx}/back/giftInfo/list',
								columns : columns,
								showAddBtn : false,
								appendTbarItems : defaultAction,
								showUpdateBtn : false,
								showRemoveBtn : false,
								
								
							   gridPlugins : [Grid.Plugins.CheckSelection,Grid.Plugins.GridMenu]
							    
								
							});
							var grid = crudGrid.get("grid");
						
							$("#saveBtn").click(function(){
							
								
								var ids = [];
								var prices= []
									
								 var giftId= 	$("#page2 > div > input[name='gift.id']");
								 var  price  =$("#page2 > div >input[name='price']");
								 
								if(giftId.length  == 0  || price.length == 0){
									
									return;
								}
								
								for(var i =  0 , b = giftId.length ; i < b ; i++){
									
									ids.push($(giftId[i]).val());
									prices.push($(price[i]).val());
									
									}
									
							
							
									  var params ={"userId":userId, "ids" :ids,"prices":prices};
									  
									
									
									
								$.post("${ctx}/back/userGift/save",params,function(data){
									
							if(!data||!data.success)
								{

								BUI.Message.Alert('操作失败！');
								return ;
								}
									BUI.Message.Alert('操作成功！',function(){
			              				top.topManager.closePage();
			              			
			              				},'success');
										},'json')
								
								
								
							});
							$("#restBtn").click(function(){
								 $("#page2").html("");
								 $("#page2").html(back_html);
								 imgContent= [];
							});
							
						  
						  
						
						});
	</script>
</body>
</html>
