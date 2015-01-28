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
          <label class="control-label">用户名：</label>
          <div class="controls">
            <input type="text" class="control-text" name="search_LIKE_name">
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
            <label class="control-label"><s>*</s>登录名</label>
            <div class="controls">
              <input name="loginName" type="text" data-rules="{required:true}" data-remote="${ctx}/admin/sys/user/checkLoginName" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label"><s>*</s>姓名</label>
            <div class="controls">
              <input name="name" type="text" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span8">
            <label class="control-label"><s>*</s>E-mail</label>
            <div class="controls">
              <input name="email" data-rules="{required:true,email:true}" class="input-normal control-text">
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
        <div class="row">
          <div class="control-group span8">
            <label class="control-label"><s>*</s>状态</label>
            <div class="controls">
            	<select name="status">
              		<option value="1">启用</option>
              		<option value="2">停用</option>
            	</select>
            </div>
          </div>
          
          <div class="control-group span8">
            <label class="control-label"><s>*</s>手机</label>
            <div class="controls">
            	 <input name="telphone" id="telphone" type="text" data-rules="{required:true,mobile:true}" class="input-normal control-text">
            </div>
          </div>
          
          
        </div>
        <input type="hidden" name="id" value="">
      </form>
    </div>
    <div class="search-grid-container">
      <div id="grid"></div>
    </div>

	<!-- 修改密码框 -->
	<div id="chPwdFormDiv" class="hide">
      <form id="chPwdFormId"  class="form-horizontal " method="post" >
      <div id="passwordDiv" class="control-group" >
        <label class="control-label"><s>*</s>新密码：</label>
        <div class="controls">
          <input id="plainPassword_change" name="plainPassword" type="password" class="input-normal" data-messages="{required:'密码不能为空'"  data-rules="{required:true,minlength:6,password:true}">
        </div>
      </div>
      <div id="repasswordDiv" class="control-group">
        <label class="control-label"><s>*</s>新密码确认：</label>
        <div class="controls">
          <input id="plainPassword1" name="plainPassword1" type="password" class="input-normal" data-messages="{required:'确认密码不能为空'}"  data-rules="{required : true,equalTo:'#plainPassword_change'}">
        </div>
      </div>
     <input type="hidden" name="id" value="">
      </form>
	</div> 
    
  </div>
<script type="text/javascript">


BUI.use(['bui/ux/crudgrid','bui/form','bui/ux/savedialog'],function (CrudGrid,Form,SaveDialog) {
	
	//定义页面权限
	var add=false,update=false,del=false,list=false;
	//"framwork:crudPermission"会根据用户的权限给add，update，del,list赋值
	<framwork:crudPermission resource="/admin/sys/user"/>
	
  	//管理员修改密码部分
  	<shiro:hasRole name='Admin'>
		changePasswdBtn = true;
	   	var saveDialog = new SaveDialog({
	   		entityName: '密码', 
	  	     url :{
	  	    	 update : '${ctx}/admin/sys/user/changePasswd'
	  	     }, 
	      	 dialogContentId:'chPwdFormDiv',
	  		 formId:'chPwdFormId'
	     });
	</shiro:hasRole>

  	
  	
    //添加 密码校验规则
    Form.Rules.add({
      name : 'password',  //规则名称
      msg : '密码必须同时包括数字和字母',//默认显示的错误信息
      validator : function(value,baseValue,formatMsg){ //验证函数，验证值、基准值、格式化后的错误信息
        var d = new RegExp('\\d+');
        var w = new RegExp('[a-zA-Z]+');
        var a = d.test(value);
        var b = w.test(value);
        if(!(d.test(value) && w.test(value))){
          return formatMsg;
        }
      }
    });
    
    

	
	var enumObj = {"1":"启用","2":"停用"};
	
    var columns = [
          {title:'ID',dataIndex:'id',width:'5%'},
          {title:'姓名',dataIndex:'name',width:'15%'},
          {title:'登录名',dataIndex:'loginName',width:'15%'},
          {title:'角色', dataIndex:'roles',width:'15%',renderer: function (value) {
        	  var roles = '';
        	  BUI.each(value,function(role){
        	        roles += role.name+',';
        	      });
              return roles.substring(0,roles.length-1);
           }},
          {title:'E-mail', dataIndex:'email',width:'20%'},
          {title:'手机', dataIndex:'telphone',width:'20%'},
          {title:'注册时间',dataIndex:'createTime',width:'20%',renderer:BUI.Grid.Format.datetimeRenderer},
          {title:'状态',dataIndex:'status',width:'20%',renderer:BUI.Grid.Format.enumRenderer(enumObj)}
        ];

    var crudGrid = new CrudGrid({
    	entityName : '用户',
    	storeUrl : '${ctx}/admin/sys/user/list',
        addUrl : '${ctx}/admin/sys/user/add',
        updateUrl : '${ctx}/admin/sys/user/update',
        removeUrl : '${ctx}/admin/sys/user/del',
        columns : columns,
        showAddBtn : add,
        showUpdateBtn : update,
        showRemoveBtn : del,
        addOrUpdateFormId : 'addOrUpdateForm',
        searchBtnId :'btnSearch',
        dialogContentId : 'addOrUpdate',
        operationColumnRenderer : function(value, obj){//操作列最追加按钮
        	var editStr = '';
        	if(changePasswdBtn){
        		editStr= '<span class="grid-command" title="修改密码"><i class="icon-lock"></i></span>';
        	}
          	return editStr;
          }
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
   // var grid = crudGrid.get('grid');
   // var store = crudGrid.get('store');
    

/*      var grid = new Grid.Grid({
    	 render:'#grid',
    	 columns : columns,
         tbar : {
             items : [
               {text : '<i class="icon-plus"></i>新建',btnCls : 'button button-small',handler:addFunction},
               {text : '<i class="icon-remove"></i>删除',btnCls : 'button button-small',handler : delFunction}
             ]
          },
        width:'100%',//这个属性一定要设置
        loadMask: true,
        store : store,
        plugins : [BUI.Grid.Plugins.CheckSelection,BUI.Grid.Plugins.AutoFit], // 插件形式引入多选表格
        bbar:{
            // pagingBar:表明包含分页栏
            pagingBar:true
        }
      });
     grid.render();
 
     var searchForm = new Form.HForm({
    	 srcNode : '#searchForm'
     }).render();
     
     var reLoad = function(){
    	 searchForm.valid();
         if(searchForm.isValid()){
        	 var param = searchForm.serializeToObject();
             param.start=0;
             param.pageIndex = 0;
             store.load(param);
         }
     };
     
     $('#btnSearch').on('click',function(ev){
    	 reLoad();
     }); 
     
     var addAdnUpdateForm = new Form.HForm({
    	 srcNode : '#addOrUpdateForm'
     }).render();
     
      var dialog = new Overlay.Dialog({
         title:'新增',
         //width:500,
         //height:320,
         //配置DOM容器的编号
         contentId:'addOrUpdate',
         success:function () {
        	 if(addAdnUpdateForm.isValid()){
        		 var param = addAdnUpdateForm.serializeToObject(); 
            	 var callback = function(data){
            		 if(data.success){
                		 addAdnUpdateForm.clearFields();
                		 dialog.close();
            		 }else{
            			 BUI.Message.Alert(data.message,'warning');
            		 }
            	 };
        		 store.save('add', param, callback);//使用store的方法
        	 }
         }
       }); 
    
     function addFunction(){
    	addAdnUpdateForm.dialog = '新增用户';
    	$('#password').show();
    	addAdnUpdateForm.getField('plainPassword').enable();
    	addAdnUpdateForm.getField('plainPassword2').enable();
    	dialog.show();
    } 
    
    function editFunction(record){
    	addAdnUpdateForm.dialog = '修改用户';
    	addAdnUpdateForm.setRecord(record);
    	$('#password').hide();
    	addAdnUpdateForm.getField('plainPassword').disable();
    	addAdnUpdateForm.getField('plainPassword2').disable();
    	dialog.show();
    }
 
    //删除操作
     function delFunction(){
      var selections = grid.getSelection();
      delItems(selections);
    }

    function delItems(items){
      var ids = [];
      BUI.each(items,function(item){
        ids.push(item['id']);
      });

      if(ids.length){
        BUI.Message.Confirm('确认要删除选中的记录么？',function(){
       	 var callback = function(data){
       		 if(!data.success){
       			 BUI.Message.Alert(data.message,'warning');
    		 }
    	 };
         store.save('remove',{ids : ids},callback);
        },'question');
      }
    } 

    //监听事件，删除一条记录
    crudGrid.on('cellclick',function(ev){
      var sender = $(ev.domTarget); //点击的Dom
      if(sender.hasClass('btn-del')){
        var record = ev.record;
        delItems([record]);
      }else if(sender.hasClass('btn-edit')){
          var record = ev.record;
          editFunction(record);
      }
    });
    */

 
</script>
 
</body>
</html>  
