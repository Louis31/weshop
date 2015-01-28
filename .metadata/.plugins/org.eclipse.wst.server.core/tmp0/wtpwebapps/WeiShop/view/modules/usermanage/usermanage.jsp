<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ include file="/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
</head>
<body>
	<div class='rightcontent' style="height: 100%;">
	 <form id="menuForm" style="margin:10;text-align: center;" method="post">
	  <table cellspacing="0" cellpadding="0">
        <tbody>
          <tr> <td style="height: 20px;"> </td></tr>
          <tr>
            <td>
              &nbsp; 按名称查询：<input class="textbox" id="userName" type="text" name="userName">
                    &nbsp;<input class="button" id="searchdata" onclick="searchMenu()" type="button" value="搜  索" >
            </td>
          </tr>
          <tr><td style="height: 20px;"></td></tr>
        </tbody>
       </table>
       </form>
	   <table id="userinfo_tb" style="height: 100%;width:100%"></table>	
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
		$('#userinfo_tb').datagrid({
							pagination : true,//分页控件
							fitColumns: true, //自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
							nowrap : false, //True 就会把数据显示在一行里
							striped : true, //奇偶条显示不同的颜色
							collapsible : true, //可折叠
							remoteSort : false, //是否从服务器给数据排序
							//queryParams:{}, //查询条件
							idField : 'userId',
							loadMsg : '正在加载....',
							url: '${BasePath}/usermanage/queryUserList?d='+new Date(),
							columns : [ [					
                                    {field:'ck',checkbox:true,width:2}, //显示复选框
									{
										title : '用户名',
										field : 'userName',
										width : 400,
										sortable : true
									},
									{   field:  'userId',
										title : '操作',
										width : 300,
										formatter : function(value, row,index) {
											var  d = '<a  href="javascript:void(0)" >修改</a> ';
											f = ' <a href="javascript:void(0)" >删除</a>';
											return d + f;
										}
									} ] ],
								    pageSize: _pageSize,//每页显示的记录条数，默认为15   
								    pageList: _pageList,//可以设置每页记录条数的列表     
								    pagination: _pagination
						});
	});
	
	function searchMenu(){
		var params = $('#userinfo_tb').datagrid('options').queryParams; //先取得 datagrid 的查询参数
		var fields =$('#menuForm').serializeArray(); //自动序列化表单元素为JSON对象
		$.each( fields, function(i, field){
			params[field.userName] = field.value; //设置查询参数
		});
		$('#userinfo_tb').datagrid('reload');
    }
	
	</script>
</body>
</html>