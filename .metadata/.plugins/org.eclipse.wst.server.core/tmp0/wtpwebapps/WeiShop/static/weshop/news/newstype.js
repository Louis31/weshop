/*!
 * 进货入库
 */
function deleteInfoType(id){
	Ext.MessageBox.confirm("删除提示", "是否删除该类别？", function(c) {
		   if(c=="yes"){
		   		Ext.Ajax.request({
		   			url : ctx+"/infortree/deleteInformationTree.do",
		   			params:{ id : id },
		   			success : function(o) {
		   				var dataObj=eval("("+o.responseText+")");
		   				if(dataObj.success){
		   					Ext.Msg.alert("信息提示","操作成功");
		   				    store.reload();
		   				}else{
		   				   
		   				}
		   			}
		   		});
		    }
		});
}
function updateInfoType(id){
	
}
var store;
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	var InfoObj = [
		{ name:'id', type:'int'},
		{ name:'infoname', type:'string'},
		{ name:'pid', type:'int'}
	];
	var v_spid="0", v_spname="",v_start="0",v_limit="10";
	//商品数据
	store = new Ext.data.JsonStore({
	    url: ctx+'/infortree/getPageInformationTree.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields: InfoObj,
	    listeners:{beforeload:function(a){a.baseParams={start:v_start, limit:v_limit};}}
	});
	
	//评论列表
	var grid = new Ext.grid.GridPanel({
    	id:'djspGrid',
    	layout:'fit',
    	viewConfig : {
    		  forceFit: true
    	},
        store: store,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : false},//禁止表头菜单
			
			columns:[
				{header: '类别名称',sortable:true, dataIndex: 'infoname'},
	            {header: '操作',sortable:true, align:'center', dataIndex: 'id',renderer:function(value){
	            	return "<a onclick='deleteInfoType("+value+")'>删除类别</a>";
	            }}
	            ]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        margins:'20',
        style:'border:1px solid',
		region:'center',
        iconCls:'',
        tbar:[
         {text:'新增',iconCls:'btn-add',handler:function(){
        	 addWindow.show();
          }
         },
         {text:'修改',iconCls:'btn-add',handler:function(){
        	    var record= grid.getSelectionModel().getSelected(); 
        		updateForm.getForm().loadRecord(record);
        		updateWindow.show();
          }
         }
         
         ]
        ,
        bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: store,
            displayInfo: true
        })
    });
    
    //  add
    //商品表单
   var addForm = new Ext.FormPanel({
		layout : 'form',
		frame:true,
		labelWidth:60,
		border : false,
		padding : '15 10 0 8',
		defaults : {
			anchor : '100%'
		},
		items:[
			{
				xtype : "textfield",
				name:"infoname",
				fieldLabel:"资讯类别",
				allowBlank : false,
				maxLength :20
			}
		]
	});
    
    //  add
    //商品表单
   var updateForm = new Ext.FormPanel({
		layout : 'form',
		frame:true,
		labelWidth:60,
		border : false,
		padding : '15 10 0 8',
		defaults : {
			anchor : '100%'
		},
		items:[
			{
				xtype : "textfield",
				name:"infoname",
				fieldLabel:"资讯类别",
				allowBlank : false,
				maxLength :20
			},{
				xtype : 'hidden',
			    name : 'id'
			},{
				xtype : 'hidden',
			    name : 'pid'
			}
		]
	});
    
  //增加商品窗口
    var addWindow = new Ext.Window({
		title : '增加类别',
		width:"50%",
		height:200,
		closeAction:'hide',
		modal : true,
		border:false,
		resizable : false,
		layout : 'fit',
		buttonAlign : 'center',
		items : [addForm],
		buttons : [{
			text : '保存',
			handler : function() {
				if (addForm.getForm().isValid()) {
					addForm.getForm().submit({
						url : ctx+'/infortree/saveInformationTree.do',
						success : function(form, action) {
							  addForm.getForm().reset();
							  Ext.Msg.alert('信息提示',action.result.message);
							  addWindow.hide();
							  store.reload();
						    
						},
						failure : function(form, action) {
							if(action.result.errors){
								Ext.Msg.alert('信息提示',action.result.errors);
							}else{
								Ext.Msg.alert('信息提示','连接失败');
							}
						},
						waitTitle : '提交',
						waitMsg : '正在保存数据，稍后...'
					});
				}
			}
		}, {
			text : '取消',
			handler : function() {
				
			}
		}]
	});
    // add
    
  //增加商品窗口
     var updateWindow = new Ext.Window({
		title : '增加类别',
		width:"50%",
		height:200,
		closeAction:'hide',
		modal : true,
		border:false,
		resizable : false,
		layout : 'fit',
		buttonAlign : 'center',
		items : [updateForm],
		buttons : [{
			text : '保存',
			handler : function() {
				if (updateForm.getForm().isValid()) {
					updateForm.getForm().submit({
						url : ctx+'/infortree/updateInformationTree.do',
						success : function(form, action) {
							  updateForm.getForm().reset();
							  Ext.Msg.alert('信息提示',action.result.message);
							  updateWindow.hide();
							  store.reload();
						    
						},
						failure : function(form, action) {
							if(action.result.errors){
								Ext.Msg.alert('信息提示',action.result.errors);
							}else{
								Ext.Msg.alert('信息提示','连接失败');
							}
						},
						waitTitle : '提交',
						waitMsg : '正在保存数据，稍后...'
					});
				}
			}
		}, {
			text : '取消',
			handler : function() {
				
			}
		}]
	});
  
	//布局
    new Ext.Viewport({
		layout:'fit',
		items:[{
			frame:true,
			title:'',
			iconCls:'menu-11',
			layout:'border',
			items:[grid]
		}],
		listeners:{
			render:function(){
				store.load();
			}
		}
	});
	
 
	

});