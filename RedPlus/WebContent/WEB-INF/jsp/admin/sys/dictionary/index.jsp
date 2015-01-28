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
      <div class="span6">
        <div class="panel">
        <form id = "tree">
          <div class="panel-header clearfix">
            <h3 class="pull-left">数据字典分类</h3>
            <div id="tbar" class="pull-right"></div>
          </div>
          <div id="list">
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
          <!-- 修改新增 -->
   <div id="addOrUpdate" class="hide">
      <form id="addOrUpdateForm" class="form-horizontal">
        <div class="row">
          <div class="control-group span8">
            <label class="control-label"><s>*</s>名称</label>
            <div class="controls">
              <input name="name" type="text" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label">代码</label>
            <div class="controls">
              <input name="code" class="input-normal control-text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span8">
            <label class="control-label"><s>*</s>值</label>
            <div class="controls">
              <input name="value" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label">排序</label>
            <div class="controls">
              <input name="orderId" data-rules="{number:true}" class="input-normal control-text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span8">
            <label class="control-label">扩展属性1</label>
            <div class="controls">
              <input name="extend1" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label">扩展属性2</label>
            <div class="controls">
              <input name="extend2" type="text" class="input-normal control-text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span8">
            <label class="control-label">扩展属性3</label>
            <div class="controls">
              <input name="extend3" type="text" class="input-normal control-text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span12">
            <label class="control-label">描述</label>
            <div class="controls">
              <textarea name="memo" class="input-large"> </textarea>
            </div>
          </div>
        </div>
        <input type="hidden" name="dictionaryType.id" value="">
        <input type="hidden" name="id" value="">
      </form>
    </div>
  </div>
  </div>
<script type="text/javascript">
BUI.use(['bui/tree','bui/data','bui/toolbar','bui/select','bui/ux/crudgrid','bui/mask','bui/list'],
		function (Tree,Data,Toolbar,Select,CrudGrid,Mask,List) {
	
	
	//定义页面权限
	var add=false,update=false,del=false,list=false;
	//"framwork:crudPermission"会根据用户的权限给add，update，del,list赋值
	<framwork:crudPermission resource="/admin/sys/dictionary"/>
	
			var dictionaryTypeId = 0;

			var gridStore;//
			
			 var listStore = new Data.Store({
				  url : '${ctx}/admin/sys/dictionaryType/listAll',
				  autoLoad : true
			 });
	        
		     var list = new List.SimpleList({
		            elCls:'bui-select-list',//默认是'bui-simple-list'
		            //itemTpl : '<li>{text}[{value}]</li>',
		            width:225,
		            height: BUI.viewportHeight()-90,
		            render : '#list',
		            store : listStore
		          });
		     list.render();
		     list.on('itemclick', function(ev){
		    	 gridStore.set('lastParams',{});//清空上次的参数
		    	 gridStore.load({'search_EQ_dictionaryType.id' : ev.item.value});
		    	 dictionaryTypeId = ev.item.value;
		     });
	         
	        var bar = new Toolbar.Bar({
	            render : '#tbar',
	            elCls : 'button-group',
	            children : [
	              {
	                elCls : 'button button-small',
	                content : '<i class="icon-refresh"></i>刷新',
	                elAttrs:{title : '刷新'},
	                handler : function(){
	                	listStore.load();
	                }
	              }
	            ]
	          });
	          bar.render();
	          
	         var columns = [
	            {title : 'ID', dataIndex:'id',width:'5%'},
	            {title : '名称',dataIndex :'name',width:'15%'},
	            {title : '代码',dataIndex: 'code', width :'15%'},
	            {title : '值',dataIndex : 'value',width:'15%'},
	            {title : '扩展属性1',dataIndex : 'extend1',width:'15%'},
	            {title : '扩展属性2',dataIndex : 'extend2',width:'15%'},
	            {title : '扩展属性3',dataIndex : 'extend3',width:'15%'},
	            {title : '排序', dataIndex : 'orderId', width:'5%'},
	            {title : '上级资源', dataIndex : 'dictionaryType', withd:100,renderer: function (value) {
	            	if(value){
	            		return value.name;
	            	}else{
	            		return '';
	            	}
	           }}
	          ];
	         
	         
	         
	         var crudGrid = new CrudGrid({
	        	 gridId :'grid',
	         	 entityName : '资源',
	         	 storeUrl : '${ctx}/admin/sys/dictionary/list',
	            addUrl : '${ctx}/admin/sys/dictionary/add',
	            updateUrl : '${ctx}/admin/sys/dictionary/update',
	            removeUrl : '${ctx}/admin/sys/dictionary/del',
	            columns : columns,
	            showAddBtn : add,
	            showUpdateBtn : update,
	            showRemoveBtn : del,
	             addOrUpdateFormId : 'addOrUpdateForm',
	             dialogContentId : 'addOrUpdate',
	             autoSearch : false,//不自动查询
	             storeCfg : {
	            	 sortInfo : {
	            		 field : 'id',
	            		 direction : 'DESC' //升序ASC，降序DESC
	            	 }
	             }
	         });
	         gridStore = crudGrid.get('store');
	         

	         crudGrid.on('beforeAddShow', function(dialog,form){
	        	 if(!dictionaryTypeId){//dictionaryTypeId==0
	        		 BUI.Message.Alert('请先选择分类','info');
	        		 return false;
	        	 }else{
	        		 form.getField('dictionaryType.id').set('value',dictionaryTypeId);
	        	 }
	         });
	         
	         crudGrid.on('beforeUpdateShow', function(dialog,form,record){ 
	        	 form.getField('dictionaryType.id').set('value',record.dictionaryType.id);
	         });
	         

});
 
</script>
 
</body>
</html>
