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
     <div class="row">
      <div class="span20">
        <div class="panel">
        <form id = "tree">
          <div class="panel-header clearfix">
            <div id="tbar" class="pull-letf"></div>
            <div id="save" class="pull-right"></div>
          </div>
          <div id="t1">
          
          </div>
          </form> 
        </div>
      </div>
      <div>
        <div class="panel panel-head-borded " style="overflow: hidden;padding-left: 10px;">
          <div id="grid">
          </div>
        </div>
      </div>
     </div>
  </div>
<script type="text/javascript">
BUI.use(['bui/tree','bui/data','bui/toolbar','bui/editor','bui/select','bui/ux/crudgrid','bui/mask','bui/overlay'],
	function (Tree,Data,Toolbar,Editor,Select,CrudGrid,Mask) {
	
	var roleId =${id};
	
	//加载权限键值对
	var enumPermission = {};
 	$.ajax({url:'${ctx}/admin/sys/permission/permissionCheckList',
		async : false,
		dataType : 'json',
		success : function(json){
			$.each(json,function(index,item){
				enumPermission[item.value] = item.text;
			});
	}});
 	
 	
 	var permissionCheckboxBulider = function(item){
        var result = [];
        if (!BUI.isArray(item.value) && item.value) {
        	item.value = item.value.toString().split(',');
        }
       	var checkBoxList = '';
       	if(item.value){
        	$.each(item.value, function (index,value) {
        		var name = enumPermission[value] || '';
        		checkBoxList = checkBoxList + '&nbsp&nbsp&nbsp&nbsp<input name="resource_'+item.id+'" value="'+value+'" class="permission-checkbox" type="checkbox">'+name+'</input>';
		 	
        	});
        }	
        item.text = item.text + checkBoxList;
        
		 var children = item.children;
		 if(children){
            $.each(children, function (index,child) {
            	permissionCheckboxBulider(child);
             });
		 }
 	}
	
	
	var roleId = ${id};
	//资源树开始
    //一次性异步加载所有数据
    var treeStore = new Data.TreeStore({
        url : '${ctx}/admin/sys/resource/resourceTree', 
        autoLoad : true,
	    root : {                  //由于要显示根节点，所以需要配置根节点
	       id : '0',
	       text : '资源列表',
	       expanded : true,
	       cls : 'icon-home'
	     } 
    });
     treeStore.on('load',function(e){
    	 var result = treeStore.getResult();
    	 BUI.each(result,function(item){
    		 permissionCheckboxBulider(item);
    	 });
    	 loadRole();
     });

	 //由于这个树，不显示根节点，所以可以不指定根节点
	 var tree = new Tree.TreeList({
		 itemTpl : '<li id="{id}">{text}</li>',
	     render : '#t1',
	     store : treeStore,
	     showLine : true,
	     height: BUI.viewportHeight()-100,
	     dirCls : 'icon-tasks',
	     leafCls : 'icon-leaf',
	     showLine : true, //显示连接线
	     showRoot : true,
	     checkType: 'all',
	     loadMask : new Mask.LoadMask({el : '#t1'})
	   });
	 tree.render();
     
     var loadRole = function(){
    	 //加载并在权限数上打勾
     	$.ajax({ url :'${ctx}/admin/sys/role/update/'+roleId,
     		cache : false,
     		dataType : 'json',
     		success : function(role){
     		var roleAuths = role.roleAuths;
     		 if(roleAuths){
     			 $.each(roleAuths,function(index, roleAuth){
     				 var node = tree.getItem(roleAuth.resource.id);
     				 if(node.leaf){
     					tree.setChecked(tree.getItem(roleAuth.resource.id));
          				var permissionIds = roleAuth.permissionIds;
         		        if (permissionIds) {
         		        	permissionIds = permissionIds.toString().split(',');
            				 var checkboxs = $('#'+roleAuth.resource.id+'>input[type=\'checkbox\']');
             				 $.each(checkboxs,function(i,checkbox){
             					 $.each(permissionIds,function(j,id){
             						if($(checkbox).val()==id){
             							$(checkbox).attr('checked',true);
             						}
             					 });
             				 });
         		        }
     				 }
     			 });
     		 }
     	}});
      } 
     
    var bar = new Toolbar.Bar({
        render : '#tbar',
        elCls : 'button-group',
        children : [
          {
            elCls : 'button button-small',
            content : '<i class="icon-refresh"></i>刷新',
            elAttrs:{title : '刷新'},
            handler : function(){
            	treeStore.load();
            }
          },
          {
        	  elCls : 'button button-small',
        	  content : '<i class="icon-plus"></i>展开',
        	  elAttrs:{title : '展开'},
        	  handler : function(){
        		  tree.expandAll();
        	}
          },
          {
        	  elCls : 'button button-small',
        	  content : '<i class="icon-minus"></i>收缩',
        	  elAttrs:{title : '收缩'},
        	  handler : function(){
        		  tree.collapseAll();
        	}
          }
        ]
      });
      bar.render();
      
      var save = new Toolbar.Bar({
          render : '#save',
          elCls : 'button-group',
          children : [
            {
              elCls : 'button button-small',
              content : '<i class="icon-inbox"></i>保存',
              elAttrs:{title : '保存'},
              handler : function(){
            	var role = {
            		id : roleId
            	};
            	var roleAuths = new Array();
              	var checkNodes = tree.getCheckedNodes();
              	
              	var partialchecked = $('.bui-tree-item-partial-checked');//半选状态的节点的样式
              	$.each(partialchecked,function(index,item){
              		var id = $(item).attr('id');
              		if(id && id >0){
              			checkNodes.push(tree.getItem($(item).attr('id')));
              		}
              	});
              	
              	$.each(checkNodes,function(index,node){
              		var bb = {a:'b'};
              		var roleAuth = {};
              		var resource = new Object();
              		var permissionIds = '';
              		resource['id'] = node.id;
              		roleAuth['resource'] = resource;
              		var r = {id: roleId};
              		roleAuth['role'] = r;
              		var checkboxs = $('#'+node.id+'>input[type=\'checkbox\']');
              		
    				 $.each(checkboxs,function(i,checkbox){
    					 if($(checkbox).is(':checked')){
        					 permissionIds += $(checkbox).val()+",";
    					 }
     				 });
    				 
    				 roleAuth['permissionIds'] = permissionIds.substring(0,permissionIds.length-1);
    				 roleAuths.push(roleAuth);
              	});
              	role['roleAuths'] = roleAuths;
              	var json = JSON.stringify(role)
              	//var a =decodeURIComponent(param);
              	$.post('${ctx}/admin/sys/role/auth/'+roleId,{jsonRole: json},function(result){
              		if(result.success){
              			BUI.Message.Alert('操作成功！',function(){
              				top.topManager.closePage();
              				//不需要刷新top.topManager.reloadPage('/admin/sys/role');
              				},'success');
              		}
              	},'json');
              	
              }
            }
          ]
        });
      save.render();
});

 
</script>
 
</body>
</html>  
