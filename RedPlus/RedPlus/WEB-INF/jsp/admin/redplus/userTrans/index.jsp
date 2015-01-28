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
          <label class="control-label">nickname：</label>
          <div class="controls">
            <input type="text" class="control-text" name="search_LIKE_nickname">
          </div>
        </div>
        <div class="control-group span7">
          <label class="control-label">E-mail：</label>
          <div class="controls">
            <input type="text" class="control-text" name="search_LIKE_email">
          </div>
        </div>
        <div class="control-group span7">
          <label class="control-label">状态：</label>
          <div class="controls" >
            <select name="search_EQ_status">
              <option value="">全部</option>
              <option value="1">启用</option>
              <option value="2">停用</option>
            </select>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="control-group span9">
          <label class="control-label">注册时间：</label>
          <div class="controls bui-form-group" data-rules="{dateRange : true}">
          <!-- search_GTE_createTime_D 后面的D表示数据类型是Date -->
            <input type="text" class="calendar" name="search_GTE_createTime_D" data-tip="{text : '开始日期'}"> <span>
             - </span><input name="search_LTE_createTime_D" type="text" class="calendar" data-tip="{text : '开始日期'}">
          </div>
        </div>
        <div class="span3 offset2">
          <button  type="button" id="btnSearch" class="button button-primary">搜索</button>
        </div>
      </div>
    </form>
    <!-- 修改新增 -->
   <div id="addOrUpdate" class="hide">
      <form id="addOrUpdateForm" class="form-horizontal">
        <div class="row">
          <div class="control-group span8">
            <label class="control-label"><s>*</s>nickname</label>
            <div class="controls">
              <input name="nickname" type="text" data-rules="{required:true}"  class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label"><s>*</s>age</label>
            <div class="controls">
              <input name="age" type="text" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span8">
            <label class="control-label"><s>*</s>height</label>
            <div class="controls">
              <input name="height" data-rules="{required:true,email:true}" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8" >
          	
            <label class="control-label"><s>*</s>角色</label>
            <div class="controls" id ="roles" name='roles'>
              <input name="roleIds" type="hidden" id="roleIds" value='' data-rules="{required:true}">
            </div>
          </div>
        </div>
        <div class="row" id="password">
          <div class="control-group span8">
            <label class="control-label"><s>*</s>密码</label>
            <div class="controls">
              <input id="plainPassword" name="plainPassword" data-rules="{required:true,minlength:6,password:true}"
               class="input-normal control-text" type="password" data-tip="{text : '长度大于6的数字与字母'}">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label"><s>*</s>确认密码</label>
            <div class="controls">
              <input id="plainPassword2" name="plainPassword2" data-rules="{equalTo:'#plainPassword'}" class="input-normal control-text"  type="password">
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


BUI.use(['bui/ux/crudgrid','bui/form','bui/ux/savedialog'],function (CrudGrid,Form,SaveDialog) {
	
	//定义页面权限
	var add=false,update=false,del=false,list=false;
	//"framwork:crudPermission"会根据用户的权限给add，update，del,list赋值
	<framwork:crudPermission resource="/back/userInfo"/>
  var columns = [
          {title:'fromUser',dataIndex:'fromUser',width:'5%' ,renderer: function (value){
        	  if(value){
        		  return value.nickname;  
        	  }
          }},
          {title:'toUser',dataIndex:'toUser',width:'5%' ,renderer: function (value){
        	  if(value){
        		  return value.nickname;  
        	  }
          }},
          {title:'createtime',dataIndex:'createtime',width:'15%' ,renderer:BUI.Grid.Format.datetimeRenderer},
          {title:'statu',dataIndex:'statu',width:'15%'}
          
        ];

    var crudGrid = new CrudGrid({
    	entityName : '订单管理',
    	storeUrl : '${ctx}/back/userInfo/list',
        addUrl : '${ctx}/back/userInfo/add',
        updateUrl : '${ctx}/back/userInfo/update',
        removeUrl : '${ctx}/back/userInfo/del',
        columns : columns,
        showAddBtn : add,
        showUpdateBtn : update,
        showRemoveBtn : del,
        addOrUpdateFormId : 'addOrUpdateForm',
        searchBtnId :'btnSearch',
        dialogContentId : 'addOrUpdate'
       
    });
    
   	var grid = crudGrid.get('grid');
    grid.on('cellclick',function(ev){//定义点击行的出发事件
        var sender = $(ev.domTarget); //点击的Dom
        var record = ev.record;
        if(sender.hasClass('icon-lock')){
        	from = saveDialog.get('form');
        	from.getField('id').set('value',record.id);
        	saveDialog.update();
        }
    });
    
    //var addAdnUpdateForm = crudGrid.get('addOrUpdateForm');
    var beforeAddShow = function(dialog,form){
    	$('#password').show();
    	//form.getField('plainPassword').enable();
    	//form.getField('plainPassword2').enable();
    	
    	//form.getField('loginName').enable();
    	select.setSelectedValue('');
    };
    crudGrid.on('beforeAddShow', beforeAddShow);
   
    
    var beforeUpdateShow = function(dialog,form,record){
    	$('#password').hide();
    	form.getField('plainPassword').disable();
    	form.getField('plainPassword2').disable();
    	form.getField('loginName').disable();
    	var roles = record.roles;
  	  	var roleList = '';
	  	BUI.each(roles,function(role){
	  		roleList += role.id+',';
	    });
	  	roleList =  roleList.substring(0,roleList.length-1);
	  	select.setSelectedValue('');
    	select.setSelectedValue(roleList);
    };

    crudGrid.on('beforeUpdateShow', beforeUpdateShow);
    
    
    
    var Select = BUI.Select,
    Data = BUI.Data;

  	var store = new Data.Store({
    	url : '${ctx}/admin/sys/role/roleCheckList',
    	autoLoad : true
  	}),
  	select = new Select.Select({  
    	render:'#roles',
    	valueField:'#roleIds',
    	multipleSelect : true,
    	store : store
  	});
  	select.render();
  	

  	
  	
});


 
</script>
 
</body>
</html>  
