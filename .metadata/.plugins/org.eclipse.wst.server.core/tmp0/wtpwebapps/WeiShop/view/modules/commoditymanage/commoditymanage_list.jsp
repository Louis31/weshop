<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品管理</title>
<link href="${BasePath}/static/css/base.css" rel="stylesheet" type="text/css" />
<link href="${BasePath}/static/jquery/jquery-easyui-1.4/themes/metro/easyui.css" rel="stylesheet" type="text/css" />
<link href="${BasePath}/static/artDialog5/skins/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${BasePath}/static/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript"  src="${BasePath}/static/artDialog5/artDialog.min.js"></script>
<script type="text/javascript"  src="${BasePath}/static/artDialog5/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="${BasePath}/static/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${BasePath}/static/jquery/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${BasePath}/static/jquery/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div class='rightcontent' style="height: 100%;">
	 <form id="menuForm" style="margin:10;text-align: center;" method="post">
	  <table cellspacing="0" cellpadding="0">
        <tbody>
          <tr> <td style="height: 20px;"> </td></tr>
          <tr>
            <td>
              &nbsp; 按商品名称查询：<input class="textbox" id="commodityName" type="text" name="commodityName">
                    &nbsp;<input class="button" id="searchdata" onclick="searchMenu()" type="button" value="搜  索" >
            </td>
          </tr>
          <tr><td style="height: 20px;"></td></tr>
        </tbody>
       </table>
       </form>
	   <table id="commotity_tb" style="height: 100%;width:100%"></table>	
	</div>
	<script type="text/javascript">
	var _pageSize=15;//每页显示的记录条数，默认为15
	var _pageList=[15,30,50];//可以设置每页记录条数的列表
	//分页栏显示定义
	var _pagination={
		    pageNumber: 1,  
		    beforePageText: '第',//页数文本框前显示的汉字     
		    afterPageText: '页    共 {pages} 页',    
		    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
	};
	var pageheight=$(".rightcontent").height();	
	$(".rightcontent").height(pageheight-150);
	$(document).ready(function() {
		$('#commotity_tb').datagrid({
							pagination : true,//分页控件
							fitColumns: true, //自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
							nowrap : false, //True 就会把数据显示在一行里
							striped : true, //奇偶条显示不同的颜色
							collapsible : true, //可折叠
							remoteSort : false, //是否从服务器给数据排序
							queryParams:{}, //查询条件
							idField : 'commodityId',
							loadMsg : '正在加载....',
							url: '${BasePath}/commodityManage/queryList?d='+new Date(),
							columns : [ [					
                                    {field:'ck',checkbox:true,width:2}, //显示复选框
									{
										title : '商品编号',
										field : 'commodityNumber',
										width : 400,
										sortable : true
									},{
										title : '商品名称',
										field : 'commodityName',
										width : 400,
										sortable : true
									},{
										title : '商品状态',
										field : 'commodityStatus',
										width : 400,
										sortable : true
									},
									{
										title : '市场价格',
										field : 'marketPrice',
										width : 400,
										sortable : true
									},
									{
										title : '销售价格',
										field : 'sellPrice',
										width : 400,
										sortable : true
									},
									{
										title : '分销商供货格',
										field : 'wholesalePrice',
										width : 400,
										sortable : true
									},
									{
										title : '是否赠品',
										field : 'ifPremiums',
										width : 400,
										sortable : true
									},
									{
										title : '发布者',
										field : 'publisher',
										width : 400,
										sortable : true
									},
									{
										title : '发布者编号',
										field : 'publishNumber',
										width : 400,
										sortable : true
									},
									{
										title : '发布时间',
										field : 'createTime',
										width : 400,
										sortable : true
									},
									{   field:  'commodityId',
										title : '操作',
										width : 300,
										formatter : function(value, row,index) {
											var  d = '<a  href="javascript:void(0)" >修改</a> ';
											f = ' <a href="javascript:void(0)" >删除</a>';
											return d + f;
										}
									} ] ],
									toolbar: [{  
							            text: '添加新商品',  
							            iconCls: 'icon-add',  
							            handler: function() {  
							                addCommodity();  
							            }  
							        }, '-',{  
							            text: '批量删除',  
							            iconCls: 'icon-add',  
							            handler: function() {  
							                newUser();  
							            }  
							        }, '-', {  
							            text: '审核通过',  
							            iconCls: 'icon-edit',  
							            handler: function() {
							                editUser();  
							            }  
							        }, '-',{  
							            text: '审核不通过',  
							            iconCls: 'icon-remove',  
							            handler: function(){  
							                deleteUser(); 
							            }  
							        }],  
								    pageSize: _pageSize,//每页显示的记录条数，默认为15   
								    pageList: _pageList,//可以设置每页记录条数的列表     
								    pagination: _pagination
						});
	});
	
	function searchMenu(){
		var params = $('#commotity_tb').datagrid('options').queryParams; //先取得 datagrid 的查询参数
		var fields =$('#menuForm').serializeArray(); //自动序列化表单元素为JSON对象
		$.each( fields, function(i, field){
			params[field.name] = field.value;  //设置查询参数
		});
		$('#commotity_tb').datagrid('reload');
    }
	function addCommodity(){
		openwin("新增商品","${BasePath}/commodityManage/commodityAdd",900,668,function(){$("#commotity_tb").datagrid('reload');myalert('保存成功','成功');});
	}
	//打开单层窗口
	function openwin(title, url, width, height, callback) {
		_callback = callback;
		var _wintitle = 'winiframe';
		var contents = "";
		contents += "<iframe scrolling='no' id='openwindow' frameborder='0' src='" + url + "'style='width:" + (width - 0) + "px;height:" + (height - 0) + "px;' title='" + _wintitle + "' ></iframe>";
		art.dialog({
			id : "winiframe",
			title : title,
			width : width,
			height : height,	
			padding:"0px 0px",
			lock : true,
			esc : false,				
			content : contents
		});
	}
	
	function myalert(message,title,cbk){
		toptips(message,1.2,cbk);
	}
	//消息提示		
	function toptips(message, _time,_callback) {		  
	   var msgcontent = "<div style='vertical-align:middle;padding:10px;'><span>" + message + "</span></div>";
	   if(!_time){_time=1.5}
	   art.dialog({
			id: 'Tips',				
			title: false,
			cancel: false,
			//esc:false,
			beforeunload:function(){
				
				if(_callback !=null && _callback!=undefined){
					_callback.call();
				}
				return true;
			},
			fixed: true,
			lock: false,
			padding : 0,
			content:msgcontent,
			time:_time*1000
		}); 			
	}
	</script>
</body>
</html>