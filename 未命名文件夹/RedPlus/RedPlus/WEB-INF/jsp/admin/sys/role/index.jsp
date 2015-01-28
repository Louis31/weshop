<!DOCTYPE HTML>
 <%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
 <head>
  <title>${sessionScope.logon_user.systemName}</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <%@include file="../../common/import-tags.jsp"%>
   <%@include file="../../common/import-static.jsp"%>
 </head>
 <body >

  
  <div class="container">
 <!-- 查询 -->
    <form id="searchForm" class="form-horizontal">
      <div class="row">
        <div class="control-group span8">
          <label class="control-label">角色名称：</label>
          <div class="controls">
            <input type="text" class="control-text" name="search_LIKE_name">
          </div>
        </div>
        <div class="control-group span8">
          <label class="control-label">角色代码：</label>
          <div class="controls">
            <input type="text" class="control-text" name="search_LIKE_code">
          </div>
        </div>
        <div class="span3 offset2">
          <button  type="button" id="btnSearch" class="button button-primary">搜索</button>
        </div>
      </div>
    </form>
    <!-- 修改新增 -->
   <div id="addOrUpdate" class="hide">
      <form id="addOrUpdateForm" class="form-inline">
        <div class="row">
          <div class="control-group span8">
            <label class="control-label"><s>*</s>角色名称</label>
            <div class="controls">
              <input name="name" type="text" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label"><s>*</s>角色代码</label>
            <div class="controls">
              <input name="code" type="text" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
        </div>
         <div class="row">
           <div class="control-group span15">
            <label class="control-label">备注</label>
            <div class="controls control-row4">
              <textarea  name="memo" class="input-large"></textarea>
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

BUI.use(['bui/ux/crudgrid','bui/common/search','bui/grid','bui/common/page'],function (CrudGrid,Search,Grid) {
	
	//定义页面权限
	var add=false,update=false,del=false,list=false;
	//"framwork:crudPermission"会根据用户的权限给add，update，del,list赋值
	<framwork:crudPermission resource="/admin/sys/role"/>
	
    var authUrl,authBtn=false;//授权按钮

  	<shiro:hasPermission name='/admin/sys/role:auth'>
  		authBtn = true;
  		authUrl = '${ctx}/admin/sys/role/auth/';
	</shiro:hasPermission>

    var columns = [
          {title:'ID',dataIndex:'id',width:'5%'},
          {title:'角色名称',dataIndex:'name',width:'20%'},
          {title:'角色代码',dataIndex:'code',width:'20%'},
          {title:'备注',dataIndex:'memo',width:'30%'}
        ];
    
    var crudGrid = new CrudGrid({
    	entityName : '角色',
    	storeUrl : '${ctx}/admin/sys/role/list',
        addUrl : '${ctx}/admin/sys/role/add',
        updateUrl : '${ctx}/admin/sys/role/update',
        removeUrl : '${ctx}/admin/sys/role/del',
        columns : columns,
        showAddBtn : add,
        showUpdateBtn : update,
        showRemoveBtn : del,
        addOrUpdateFormId : 'addOrUpdateForm',
        dialogContentId : 'addOrUpdate', 
        operationColumnRenderer : function(value, obj){
        	var editStr = '';
        	if(authBtn){
        		editStr = CrudGrid.createLink({
                	id : 'auth' + obj.id,
                	title : obj.name +'--权限授权',
                	text : '<li class="icon-ok auth"></li>',
                	href : authUrl +obj.id
        		});
        	}
        	return editStr;
        }
    });
    var grid = crudGrid.get('grid');

});
 
</script>
 
</body>
</html>  
