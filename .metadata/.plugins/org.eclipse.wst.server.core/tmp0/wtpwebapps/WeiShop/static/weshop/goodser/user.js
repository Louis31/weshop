/*!
 * 用户管理
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var UuserObj = [
		{ name:'userid', type:'int'},
		{ name:'logincode', type:'string'},
		{ name:'password', type:'string'},
		{ name:'username', type:'string'},
		{ name:'roleid', type:'int'},
		{ name:'rolename', type:'string'},
		{ name:'state', type:'int'},
		{ name:'tel', type:'string'},
		{ name:'email', type:'string'},
		{ name:'area_code', type:'string'},
		{ name:'area_name', type:'string'},
		{ name:'last_login', type:'string'},
		{ name:'history_operation', type:'string'},
		{ name:'wechat', type:'string'},
		{ name:'bz', type:'string'}
	];
	
	var store = new Ext.data.JsonStore({
	    url: ctx+'/user/findPageUser.do',
	    root: 'root',
	    totalProperty: 'total',
	    autoLoad: {params:{start:0, limit:15}},
	    fields: UuserObj
	});
	
    var grid = new Ext.grid.GridPanel({
        store: store,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[new Ext.grid.RowNumberer(),
	            {header: '登录账号', width: 100,align:'center', dataIndex: 'logincode'},
	            {header: '用户姓名', width: 100,align:'center', dataIndex: 'username'},
	            {header: '角色', width: 100, align:'center',dataIndex: 'rolename'},
				{header: '电话', width: 100, align:'center',dataIndex: 'tel'},
				{header: '邮箱', width: 100, align:'center',dataIndex: 'email'},
				{header: '上次登录', width: 100, align:'center',dataIndex: 'last_login'},
				{header: '历史操作', width: 100, align:'center',dataIndex: 'history_operation'},
				{header: '微信', width: 100, align:'center',dataIndex: 'wechat'},
	            {id:'userbz',header: '用户说明', align:'center',dataIndex: 'bz'}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        autoExpandColumn: 'userbz', //自动扩展列
		loadMask : true,	//加载时的遮罩
		frame : true,
		title:'用户管理',
        iconCls:'menu-62',
        
        tbar:[ {xtype:'textfield',id:'KeyWord',
        	enableKeyEvents:true,
 			listeners:{
 				keyup:function(btn){
 					var search = btn.getValue();
 					store.load({params:{search:search}});
 				}
 			}
         },
         {text:'搜索',handler:function(){
        	 var search = document.getElementById("KeyWord").value;
        	 store.load({params:{search:search}});
         }},'->',{
        	text:'',
        	iconCls:'btn-add',
        	handler: function(){
        		uWindow.show();
        		uForm.getForm().reset();
        		uForm.getForm().findField("logincode").setDisabled(false);
        	}
        },'-',{
        	text:'修改',
        	iconCls:'btn-edit',
        	handler: function(){
        		var record= grid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要修改的数据');
				}else{
	        		uWindow.show();
					uForm.getForm().loadRecord(record);
					uForm.getForm().findField("logincode").setDisabled(true);
					if(record.get("roleid")==0)
						uForm.getForm().findField("roleid").setValue();
				}
        	}
        },'-',{
        	text:'删除',
        	iconCls:'btn-remove',
        	handler: function(){
        		var record= grid.getSelectionModel().getSelected();
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要删除的数据');  
				}else{
					if(record.get("logincode") == "admin"){
						Ext.Msg.alert('信息提示','admin用户不能被删除');
						return;
					}
					Ext.MessageBox.confirm('删除提示', '是否删除该用户？', function(c) {
					   if(c=='yes'){
					   		Ext.Ajax.request({
					   			url : ctx+"/user/deleteUser.do",
					   			params:{ userid : record.get("userid") },
					   			success : function() {
					   				store.reload();
					   			}
					   		});
					    }
					});
				}
        	}
        }],
        listeners:{
        	render:function(){
        		var tbar = new Ext.Toolbar ({
        			items:[{
        	        	text:'新增超级管理员',
        	        	iconCls:'btn-edit',
        	        	handler: function(){
        	        		uForm.getForm().findField("roleid").setValue("1");
        	        		uWindow.show();
        	        	}
        	        },'-',{
        	        	text:'新增管理员',
        	        	iconCls:'btn-edit',
        	        	handler: function(){
        	        		uForm.getForm().findField("roleid").setValue("0");
        	        		mWindow.show();
        	        	}
        	        },'-',{
        	        	text:'密码重置',
        	        	iconCls:'btn-edit',
        	        	handler: function(){
        	        		var record= grid.getSelectionModel().getSelected(); 
        					if(!record){
        	                	Ext.Msg.alert('信息提示','请选择要修改的商品');
        					}else{
        		        		
        					}
        	        	}
        	        },'-',{
        	        	text:'删除所选',
        	        	iconCls:'btn-edit',
        	        	handler: function(){
        	        		var record= grid.getSelectionModel().getSelected(); 
        					if(!record){
        	                	Ext.Msg.alert('信息提示','请选择要审核的商品');
        					}else{
        						var record= grid.getSelectionModel().getSelected();
        						if(!record){
        		                	Ext.Msg.alert('信息提示','请选择要删除的商品');  
        						}else{
        							Ext.MessageBox.confirm('删除提示', '是否删除该商品？', function(c) {
        							   if(c=='yes'){
        							   		
        							    }
        							});
        						}
        					}
        	        	}
        	        }]
        		});
        		tbar.render(grid.tbar);
        	}},
        bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: store,
            displayInfo: true
        })
    });
    
    var roleStore = new Ext.data.JsonStore({
	    url: ctx+'/role/findRoleType.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields : ["value", "text"]
	});
	
	roleStore.load();
	
	var supermanager = new Ext.data.SimpleStore({
	    fields: ['value', 'text'],
	    data : [['1','超级管理员']]
	});

    var mForm = new Ext.FormPanel({
		layout : 'form',
		baseCls:'x-plain',
		labelWidth:60,
		border : false,
		padding : '15 10 0 8',
		defaults : {
			anchor : '100%',
			xtype : 'textfield'
		},
		items:[{
				name:'logincode',
				fieldLabel:'登录账号',
				maxLength :20,
				allowBlank : false
			},{
				inputType: 'password',
				name:'password',
				fieldLabel:'密码',
				maxLength :20,
				allowBlank : false
			},{
				name:'username',
				fieldLabel:'用户姓名',
				maxLength :20,
				allowBlank : false
			},{
				name:'tel',
				fieldLabel:'电话',
				maxLength :20,
				allowBlank : false
			},{
				name:'email',
				fieldLabel:'邮箱',
				maxLength :20,
				allowBlank : false
			},{
				name:'wechat',
				fieldLabel:'微信',
				maxLength :20,
				allowBlank : false
			},{
				xtype:'textarea',
				name:'bz',
				fieldLabel:'用户说明',
				height:80,
				maxLength :100
			},{
				xtype : 'hidden',
			    name : 'userid'
			},{
				xtype : 'hidden',
			    name : 'roleid',
			    value:"1"
			}]
	});
    
    var uForm = new Ext.FormPanel({
		layout : 'form',
		baseCls:'x-plain',
		labelWidth:60,
		border : false,
		padding : '15 10 0 8',
		defaults : {
			anchor : '100%',
			xtype : 'textfield'
		},
		items:[{
				name:'logincode',
				fieldLabel:'登录账号',
				maxLength :20,
				allowBlank : false
			},{
				inputType: 'password',
				name:'password',
				fieldLabel:'密码',
				maxLength :20,
				allowBlank : false
			},{
				name:'username',
				fieldLabel:'用户姓名',
				maxLength :20,
				allowBlank : false
			},{
				name:'tel',
				fieldLabel:'电话',
				maxLength :20,
				allowBlank : false
			},{
				name:'email',
				fieldLabel:'邮箱',
				maxLength :20,
				allowBlank : false
			},{
				name:'wechat',
				fieldLabel:'微信',
				maxLength :20,
				allowBlank : false
			},{
				xtype:'textarea',
				name:'bz',
				fieldLabel:'用户说明',
				height:80,
				maxLength :100
			},{
				xtype : 'hidden',
			    name : 'userid'
			},{
				xtype : 'hidden',
			    name : 'roleid',
			    value:"0"
			}]
	});
    
    var uWindow = new Ext.Window({
		title : '新增超级管理员窗口',
		width:400,
		height:500,
		closeAction:'hide',
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [uForm],
		buttons : [{
			text : '保存',
			handler : function() {
				if (uForm.getForm().isValid()) {
					uForm.getForm().submit({
						url : ctx+'/user/saveOrUpdateUser.do',
						success : function(form, action) {
							Ext.Msg.alert('信息提示',action.result.message);
							uWindow.hide();
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
				uWindow.hide();
			}
		}]
	});
    
    var mWindow = new Ext.Window({
		title : '新增管理员窗口',
		width:400,
		height:500,
		closeAction:'hide',
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [mForm],
		buttons : [{
			text : '保存',
			handler : function() {
				if (mForm.getForm().isValid()) {
					mForm.getForm().submit({
						url : ctx+'/user/saveOrUpdateUser.do',
						success : function(form, action) {
							Ext.Msg.alert('信息提示',action.result.message);
							mWindow.hide();
							store.reload();
							mForm.s
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
				mWindow.hide();
			}
		}]
	});
    
    new Ext.Viewport({
		layout:'border',
		items:[{
			region:'center',
			layout:'fit',
			border:false,
			items:grid
		}]
	});

});


