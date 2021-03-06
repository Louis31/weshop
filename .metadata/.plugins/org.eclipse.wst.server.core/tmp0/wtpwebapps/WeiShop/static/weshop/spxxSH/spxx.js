/*!
 * 商品管理
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	var v_lbid="0", v_lbname="", v_start=0, v_limit=20;
	
	var SpxxObj = [
	       		{ name:'spid', type:'string'},
	       		{ name:'spname', type:'string'},
	       		{ name:'xinghao', type:'string'},
	       		{ name:'dw', type:'string'},
	       		{ name:'jhprice', type:'string'},
	       		{ name:'chprice', type:'string'},
	       		{ name:'scprice', type:'string'},
	       		{ name:'minnum', type:'string'},
	       		{ name:'csname', type:'string'},
	       		{ name:'bz', type:'string'},
	       		{ name:'lbid', type:'int'},
	       		{ name:'lbname', type:'string'},
	       		{ name:'usable', type:'string'},
	       		{ name:'imageinfo', type:'string'},
	       		{ name:'is_gift', type:'int'},
	       		{ name:'areaname', type:'string'},
	       		{ name:'timeStr', type:'string'},
	       		{ name:'areacode', type:'string'},
	       		{ name:'mancode', type:'string'},
	       		{ name:'manrole', type:'string'}
	       		,
	       		{ name:'icon', type:'string'}
	       	];
	
	//商品数据
	var store = new Ext.data.JsonStore({
	    url: ctx+'/spxx/findPageSpxx.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields: SpxxObj,
	    listeners:{beforeload:function(a){a.baseParams={start:v_start, limit:v_limit,searchParam:document.getElementById("KeyWord").value};}}
	});
	
	//商品列表
	var sm = new Ext.grid.CheckboxSelectionModel(); 
    var grid = new Ext.grid.GridPanel({
        store: store,
        defaults: {	menuDisabled : true},//禁止表头菜单
        cm: new Ext.grid.ColumnModel([
                sm,
			    new Ext.grid.RowNumberer(),
				{header: '商品编号', width: 100, sortable:true, dataIndex: 'spid'},
	            {header: '商品名称', width: 130, sortable:true, dataIndex: 'spname'},
	            {header: '商品状态', width: 100, sortable:true,renderer:zhUsable,dataIndex: 'usable'},
	            {header: '分销商供货价', width: 120, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'jhprice'},
	            {header: '市场价', width: 60, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'scprice'},
	            {header: '销售价', width: 60, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'chprice'},
	            {header: '赠品', width: 60, sortable:true, align:'right', renderer:function(value){
	            	if(value==1){
	            		return "是";
	            	}else{
	            		return "否";
	            	}
	            }, dataIndex: 'is_gift'},
	            {header: '发布者', width: 60, sortable:true, align:'right', dataIndex: 'manrole'},
	            {header: '发布者编号', width: 100, sortable:true, align:'right', dataIndex: 'mancode'},
	            {header: '发布地区', width: 100, sortable:true, align:'right', dataIndex: 'areaname'},
	            {header: '发布时间', width: 100, sortable:true, align:'right', dataIndex: 'timeStr'},
	            {header: '预览', width: 50, sortable:true, dataIndex: 'icon',renderer:function(value){
					   return "<img src='"+ctx+"/upload"+value+"' style='width:50px;height:50px;'/>";
				 }}
	            ]
        ),
        sm : sm,//这里必须有
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
		frame:true,
		region:'center',
		title:'商品信息',
        iconCls:'menu-53',

        tbar:[
             {xtype:"label",text:'商品编号或名称:'},
             {xtype:'textfield',id:'KeyWord',
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
             }},
            {
        	text:'商品进行状态',
        	iconCls:'btn-add',
        	handler: function(){
        		var record= grid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要进行的商品');
				}else{
	        		if(record.data.usable=="3"||record.data.usable=="2"||record.data.usable=="1"||record.data.usable=="9"){
	        			Ext.Msg.alert('信息提示','请选择其他商品');
	        		}else{
	        			Ext.MessageBox.confirm('提示', '是否进行商品："'+record.data.spname+'"', function(c) {
	 					   if(c=='yes'){
	 					   		Ext.Ajax.request({
	 					   			url : ctx+"/spxx/changeSpxxstate.do",
	 					   			params:{ spid : record.data.spid,shState:4},
	 					   			success : function(o) {
	 					   			    var dataObj=eval("("+o.responseText+")");
	 					   				if(dataObj.success){
	 					   					Ext.Msg.alert("信息提示","操作成功");
	 					   				    store.reload();
	 					   				}else{
	 					   				    Ext.Msg.alert("信息提示","该类别商品不能临时停止");
	 					   				}
	 					   			}
	 					   		});
	 					    }
	 					});
	        		}
				}
        	}
        },'-',{
        	text:'商品零时停止状态',
        	iconCls:'btn-edit',
        	handler: function(){
        		var record= grid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要零时停止的商品');
				}else{
	        		if(record.data.usable=="3"||record.data.usable=="2"||record.data.usable=="1"||record.data.usable=="9"){
	        			Ext.Msg.alert('信息提示','请选择其他商品');
	        		}else{
	        			Ext.MessageBox.confirm('提示', '是否零时停止商品："'+record.data.spname+'"', function(c) {
	 					   if(c=='yes'){
	 					   		Ext.Ajax.request({
	 					   			url : ctx+"/spxx/changeSpxxstate.do",
	 					   			params:{ spid : record.data.spid,shState:5},
	 					   			success : function(o) {
	 					   			    var dataObj=eval("("+o.responseText+")");
	 					   				if(dataObj.success){
	 					   					Ext.Msg.alert("信息提示","操作成功");
	 					   				    store.reload();
	 					   				}else{
	 					   				    Ext.Msg.alert("信息提示","该类别商品不能临时停止");
	 					   				}
	 					   			}
	 					   		});
	 					    }
	 					});
	        		}
				}
        	}
        },'-',{
        	text:'商品彻底终止状态',
        	iconCls:'btn-edit',
        	handler: function(){
        		var record= grid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要彻底终止的商品');
				}else{
	        		Ext.MessageBox.confirm('提示', '是否彻底终止商品："'+record.data.spname+'"', function(c) {
	 					   if(c=='yes'){
	 					   		Ext.Ajax.request({
	 					   			url : ctx+"/spxx/changeSpxxstate.do",
	 					   			params:{ spid : record.data.spid,shState:9},
	 					   			success : function(o) {
	 					   			    var dataObj=eval("("+o.responseText+")");
	 					   				if(dataObj.success){
	 					   					Ext.Msg.alert("信息提示","操作成功");
	 					   				    store.reload();
	 					   				}else{
	 					   				    Ext.Msg.alert("信息提示","该类别商品不能彻底终止");
	 					   				}
	 					   			}
	 					   		});
	 					    }
	 			  });
	        		
				}
        	}
        },'-'],
        
        bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: store,
            displayInfo: true
        }),
        
        listeners:{
        	render:function(){
        		var tbar = new Ext.Toolbar ({
        			items:[{
        	        	text:'通过审核',
        	        	iconCls:'btn-edit',
        	        	handler: function(){
        	        		var record= grid.getSelectionModel().getSelected(); 
        					if(!record){
        	                	Ext.Msg.alert('信息提示','请选择要审核的商品');
        					}else{
        		        		if(record.data.usable=="1"||record.data.usable=="2"||record.data.usable=="3"){
        		        			Ext.MessageBox.confirm('提示', '是否审核通过商品："'+record.data.spname+'"', function(c) {
        			 					   if(c=='yes'){
        		        			Ext.Ajax.request({
    	 					   			url : ctx+"/spxx/changeSpxxstate.do",
    	 					   			params:{ spid : record.data.spid,shState:4},
    	 					   			success : function(o) {
    	 					   			    var dataObj=eval("("+o.responseText+")");
    	 					   				if(dataObj.success){
    	 					   					Ext.Msg.alert("信息提示","操作成功");
    	 					   				    store.reload();
    	 					   				}else{
    	 					   				    Ext.Msg.alert("信息提示","该类别商品不能审核");
    	 					   				}
    	 					   			}
    	 					   		});
        			 			 }
        		        	});   
        		        		}else{
        		        			Ext.Msg.alert('信息提示','已被审核，请选择其他商品');
        		        		}
        					}
        	        	}
        	        },'-',{
        	        	text:'不通过审核',
        	        	iconCls:'btn-edit',
        	        	handler: function(){
        	        		var record= grid.getSelectionModel().getSelected(); 
        					if(!record){
        	                	Ext.Msg.alert('信息提示','请选择要审核的商品');
        					}else{
        		        		if(record.data.usable=="1"||record.data.usable=="2"){
        		        			Ext.Ajax.request({
    	 					   			url : ctx+"/spxx/changeSpxxstate.do",
    	 					   			params:{ spid : record.data.spid,shState:3},
    	 					   			success : function(o) {
    	 					   			    var dataObj=eval("("+o.responseText+")");
    	 					   				if(dataObj.success){
    	 					   					Ext.Msg.alert("信息提示","操作成功");
    	 					   				    store.reload();
    	 					   				}else{
    	 					   				    Ext.Msg.alert("信息提示","该类别商品不能彻底终止");
    	 					   				}
    	 					   			}
    	 					   		});
        		        			//spxxSHWindow.show();
        		        			//spxxSHForm.getForm().findField("spid").setValue(record.data.spid);
        		        		}else{
        		        			Ext.Msg.alert('信息提示','已被审核，请选择其他商品');
        		        		}
        					}
        	        	}
        	        }]
        		});
        		tbar.render(grid.tbar);
        	},
        	rowdblclick:function(){
        		var record= grid.getSelectionModel().getSelected(); 
				if(record){
	        		addWindow.show();
					addForm.getForm().loadRecord(record);
					addForm.getForm().findField("addupdate").setValue("update");
				}
        	}
        }
    });
    
    //商品单位下拉数据
    var spdwStore = new Ext.data.JsonStore({
	    xtype:'jsonstore',
		url: ctx+'/spdw/findAllSpdw.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields: [{name:'dwid',type:'int'},'dwname'],
		autoLoad:true
	});
    
    
	
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
		items:[{
			layout:'column',
			items:[{
				columnWidth:.5,
				layout:'form',
				defaults:{
					anchor : '95%'
				},
				items:[{
					layout:'column',
					items:[{
						layout:'form',
						width : 218,
						items:[{
							id:'lbtext',
							width : 145,
							xtype : 'textfield',
							name:'lbname',
							fieldLabel:'所属类别',
							allowBlank : false,
							maxLength :50,
							enableKeyEvents:true,
							listeners:{
								focus:function(){
									splbTreeWindow.show();
								},
								blur:function(){
									addForm.getForm().clearInvalid();
								}
							}
						}]
					},{
						width : 20,
						height : 20,
						xtype:'button',
						iconCls : 'btn-list',
						handler:function(){
							splbTreeWindow.show();
						}
					}]
				},{
					xtype : 'textfield',
					name:'spname',
					fieldLabel:'商品名称',
					allowBlank : false,
					maxLength :20
				},{
					xtype : 'textfield',
					name:'xinghao',
					fieldLabel:'商品型号',
					maxLength :20
				},{
					xtype : 'numberfield',
					name:'jhprice',
					fieldLabel:'采购价',
					maxLength :10
				},{
					xtype : 'numberfield',
					name:'scprice',
					fieldLabel:'市场价',
					maxLength :10
				},{
					xtype : 'numberfield',
					name:'chprice',
					fieldLabel:'销售价',
					maxLength :10
				}]
			},{
				columnWidth:.5,
				layout:'form',
				defaults:{
					anchor : '95%'
				},
				items:[{
					xtype : 'textfield',
					name:'spid',
					fieldLabel:'商品编码',
					allowBlank : false,
					style:"background:#F6F6F6",
					readOnly:true,
					maxLength :10
				},{
					layout:'column',
					items:[{
						layout:'form',
						width : 218,
						items:[{
							width : 145,
							xtype:'combo',
							name:'dw',
							fieldLabel:'单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位',
							mode: 'local',
							triggerAction: 'all',
							valueField :'dwid',
							displayField: 'dwname',
							allowBlank : false,
							editable : false,
							store : spdwStore
						}]
					},{
						width : 20,
						height : 20,
						xtype:'button',
						iconCls : 'btn-list',
						handler:function(){
							SpdwWindow.show();
						}
					}]
				},{
					xtype : 'numberfield',
					name:'minnum',
					fieldLabel:'库存下限',
					maxLength :10
				}]
			}]
		},{
			xtype : 'textfield',
			name:'csname',
			fieldLabel:'生产厂商',
			maxLength :50
		},{
			xtype:'textarea',
			name:'bz',
			fieldLabel:'备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',
			height:100,
			maxLength :200
		},{
			xtype : 'hidden',
		    name : 'lbid'
		},{
			xtype : 'hidden',
			name : 'addupdate'
		}]
	});
    
	//增加商品窗口
    var addWindow = new Ext.Window({
		title : '增加商品',
		width:550,
		height:370,
		closeAction:'hide',
		modal : true,
		border:false,
		resizable : false,
		layout : 'fit',
		buttonAlign : 'center',
		items : [addForm],
		buttons : [{
			text : '新增下一商品',
			handler : function() {
				submitSpxx(true);
			}
		},{
			text : '保存',
			handler : function() {
				submitSpxx(false);
			}
		}, {
			text : '取消',
			handler : function() {
				addWindow.hide();
			}
		}]
	});
    
    var submitSHSpxx = function(){
    	if (spxxSHForm.getForm().isValid()) {
			spxxSHForm.getForm().submit({
				url : ctx+'/spxx/publishSHSpxx.do',
				success : function(form, action) {
					spxxSHWindow.hide();
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
	
	var submitSpxx = function(next){
		if (addForm.getForm().isValid()) {
			addForm.getForm().submit({
				url : ctx+'/spxx/saveOrUpdateSpxx.do',
				success : function(form, action) {
				    if(next){
				    	addForm.getForm().reset();
			       		addForm.getForm().findField("addupdate").setValue("add");
			       		addForm.getForm().findField("lbid").setValue(v_lbid);
						addForm.getForm().findField("lbname").setValue(v_lbname);
						getCode();
				    }else{
						Ext.Msg.alert('信息提示',action.result.message);
						addWindow.hide();
				    }
					store.reload({params:{lbid:v_lbid}});
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
	
	//商品类别树
	var tree = new Ext.tree.TreePanel({
		title:'商品类别',
		region : 'west',
		width : 180,
        minSize: 150,
        maxSize: 300,
        split : true,
		useArrows: true,
        autoScroll:true,
        animate:true,
        enableDD:false,
        containerScroll: true,
        frame:true,
        dataUrl: ctx+'/splb/findSplbTree.do',
        root: {
            nodeType: 'async',
            text: '所有类别',
            draggable: false,
            id: '0'
        },
        buttonAlign : 'left',
        buttons:[{
        	text:'新增类别',
        	handler:function(){
        		splbWindow.show();
        		splbForm.getForm().reset();
        	}
        },{
        	text:'删除类别',
        	disabled:true,
        	handler:function(){
        		if(v_lbid){
	        		var node = tree.getNodeById(v_lbid);
	        		var pnode = node.parentNode;
					Ext.MessageBox.confirm('删除提示', '是否删除"'+node.text+'"类别？', function(c) {
					   if(c=='yes'){
					   		Ext.Ajax.request({
					   			url : ctx+"/splb/deleteSplb.do",
					   			params:{ lbid : v_lbid },
					   			success : function(o) {
					   				if(o.responseText=="false"){
					   					Ext.Msg.alert("信息提示","该类别有商品信息不能删除");
					   				}else{
						   				v_lbid = "0";     //设为默认节点
						   				v_lbname = "";
						   				tree.buttons[1].setDisabled(true);  //禁用删除按钮
						   				pnode.removeChild(node);	//删除节点
						   				if(pnode.childNodes.length==0){	//如果无子节点则修改属性
						   					pnode.leaf = true;
						   				}
						   				var wtree = splbTreeWindow.get(0);
						   				wtree.getLoader().load(wtree.root); //更新类别编辑窗口的树
					   				}
					   			}
					   		});
					    }
					});
				}
        	}
        }],
        listeners:{
        	click:function(n){
        		v_lbid = n.id;
        		v_lbname = n.text;
        		//设置删除按钮状态
        		if(!n.leaf||v_lbid=="0"){
        			tree.buttons[1].setDisabled(true);
        		}else{
        			tree.buttons[1].setDisabled(false);
        		}
        		//更新商品数据
        		store.load({params:{start:v_start, limit:v_limit,lbid:v_lbid}});
        	}
        }
	});
	
	//展开节点
	tree.getRootNode().expand();
	
	//商品类别表单
	var splbForm = new Ext.FormPanel({
		layout : 'form',
		baseCls: 'x-plain',
		labelWidth:60,
		border : false,
		padding : 20,
		items:[{
			xtype:'textfield',
			anchor : '100%',
			name:'lbname',
			fieldLabel:'商品类别',
			allowBlank : false,
			maxLength :20
		},{
			xtype : 'hidden',
		    name : 'lbid'
		}]
	});
	
	//商品审核类别表单
	var spxxSHForm = new Ext.FormPanel({
		layout : 'form',
		baseCls: 'x-plain',
		labelWidth:60,
		border : false,
		padding : 20,
		items:[{
			xtype : 'hidden',
		    name : 'spid'
		},{
			xtype : 'hidden',
		    name : 'shState'
		}]
	});
	
    // 审核该商品
	var spxxSHWindow = new Ext.Window({
		title : '商品审核',
		width:250,
		height:140,
		closeAction:'hide',
		modal : true,
		resizable : false,
		layout : 'fit',
		buttonAlign : 'center',
		items : [spxxSHForm],
		buttons : [
		       {
			       text : '通过',
				   handler : function() {
					   spxxSHForm.getForm().findField("shState").setValue("3");
					   submitSHSpxx();
				  }
		       },
		       {
			       text : '拒绝',
				   handler : function() {
					   spxxSHForm.getForm().findField("shState").setValue("2");
					   submitSHSpxx();
				  }
		       },{
		    	   text : '取消',
					handler : function() {
						spxxSHWindow.hide();
					}
		       }
		]
	});
	//增加商品类别窗口
    var splbWindow = new Ext.Window({
		title : '增加类别',
		width:250,
		height:140,
		closeAction:'hide',
		modal : true,
		resizable : false,
		layout : 'fit',
		buttonAlign : 'center',
		items : [splbForm],
		buttons : [{
			text : '保存',
			handler : function() {
				if (splbForm.getForm().isValid()) {
					splbForm.getForm().submit({
						url : ctx+'/splb/saveOrUpdateSplb.do',
						params:{pid : v_lbid},
						success : function(form, action) {
							splbWindow.hide();
							var id = action.result.message;
							//创建新节点
							var node = new Ext.tree.TreeNode({
								id:id,
								text:form.findField("lbname").getValue(),
								iconCls:'menu-folder',
								leaf:true
							});
							//修改父节点
							var pnode = tree.getNodeById(v_lbid);
							pnode.appendChild(node);
							pnode.leaf=false;
							pnode.expand();
							//更新类别编辑窗口的树
							var wtree = splbTreeWindow.get(0);
						   	wtree.getLoader().load(wtree.root); 
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
				splbWindow.hide();
			}
		}]
	});
    
	//商品类别树窗口
    var splbTreeWindow = new Ext.Window({
		width:200,
		height:300,
		closeAction:'hide',
		layout : 'fit',
		buttonAlign : 'center',
		items : [{
			xtype:'treepanel',
			useArrows: true,
	        autoScroll:true,
	        enableDD:false,
	        containerScroll: true,
	        dataUrl: ctx+'/splb/findSplbTree.do',
	        root: {
	            nodeType: 'async',
	            text: '所有类别',
	            draggable: false,
	            id: '0'
	        },
	        listeners:{
	        	click:function(n){
	        		if(n.id=="0"){
	        			splbTreeWindow.buttons[0].setDisabled(true);
	        		}else{
		        		splbTreeWindow.buttons[0].setDisabled(false);
		        		v_lbid = n.id;
		        		v_lbname = n.text;
	        		}
	        	},
	        	dblclick:function(n){
	        		if(n.id=="0"){
	        			splbTreeWindow.buttons[0].setDisabled(true);
	        		}else{
		        		splbTreeWindow.buttons[0].setDisabled(false);
		        		v_lbid = n.id;
			        	v_lbname = n.text;
		        		splbTreeWindow.hide();
						addForm.getForm().findField("lbid").setValue(v_lbid);
						addForm.getForm().findField("lbname").setValue(v_lbname);
	        		}
	        	}
	        }
		}],
		listeners:{
			beforeshow:function(){
				var xy = Ext.getCmp("lbtext").getPosition();
				splbTreeWindow.setPosition(xy[0],xy[1]+25);
			},
        	show:function(){
        		this.items.get(0).getRootNode().expand();
        	}
        },
		buttons : [{
			width:60,
			text : '选择',
			disabled : true,
			handler : function() {
				splbTreeWindow.hide();
				addForm.getForm().findField("lbid").setValue(v_lbid);
				addForm.getForm().findField("lbname").setValue(v_lbname);
			}
		}, {
			width:60,
			text : '取消',
			handler : function() {
				splbTreeWindow.hide();
				v_lbid = "0";
		        v_lbname = "";
			}
		}]
	});
	
	//商品单位编辑窗口
	var SpdwWindow = new Ext.Window({
		xtype:'window',
		title:'单位',
		width:250,
		height:250,
		resizable : false,
		closeAction:'hide',
		layout:'fit',
		items:[{
			xtype:'grid',
			store:spdwStore,
			columns:[new Ext.grid.RowNumberer(),{
					header:'单位名称',
					sortable:true,
					menuDisabled : true,
					dataIndex:'dwname',
					width:190
				}],
			tbar:[{
					text:'增加',
					handler:function(){
						SpdwAddWin.show();
					}
				},'-',{
					text:'删除',
					handler:function(){
						var grid = SpdwWindow.get(0);
						var record= grid.getSelectionModel().getSelected();
						if(!record){
		                	Ext.Msg.alert('信息提示','请选择要删除的单位');  
						}else{
							Ext.MessageBox.confirm('删除提示', '是否删除该单位？', function(c) {
							   if(c=='yes'){
							   		Ext.Ajax.request({
							   			url : ctx+"/spdw/deleteSpdw.do",
							   			params:{ dwid : record.get("dwid") },
							   			success : function() {
							   				grid.getStore().load();
							   			}
							   		});
							    }
							});
						}
					}
				},'-',{
					text:'确定',
					handler:function(){
						var record= SpdwWindow.get(0).getSelectionModel().getSelected();
						if(!record){
		                	Ext.Msg.alert('信息提示','请选择单位');  
						}else{
							addForm.getForm().findField("dw").setValue(record.get("dwname"));
							SpdwWindow.hide();
						}
					}
				},'-',{
					text:'取消',
					handler:function(){
						SpdwWindow.hide();
					}
				}]
		}]
	});
	
	//商品单位添加窗口
	var SpdwAddWin = new Ext.Window({
		title:'增加单位',
		width:250,
		height:140,
		resizable : false,
		closeAction:'hide',
		layout:'fit',
		fbar:[{
				text:'保存',
				handler:function(){
					var form = SpdwAddWin.get(0).getForm();
					if (form.isValid()) {
							form.submit({
								url : ctx+'/spdw/saveOrUpdateSpdw.do',
								success : function(form, action) {
									SpdwAddWin.hide();
									spdwStore.load();
								},
								failure : function(form, action) {
									if(action.result.errors){
										Ext.Msg.alert('信息提示',action.result.errors);
									}else{
										Ext.Msg.alert('信息提示','连接失败');
									}
								}
							});
						}
				}
			},{
				text:'取消',
				handler:function(){
					SpdwAddWin.hide();
				}
		}],
		items:[{
				id:'dwform',
				xtype:'form',
				baseCls: 'x-plain',
				labelWidth:60,
				padding:20,
				layout:'form',
				items:[{
						xtype:'textfield',
						name:'dwname',
						fieldLabel:'单位名称',
						allowBlank : false,
						maxLength :20,
						anchor:'100%'
				}]
		}]
	});
	
	//设置商品编号
	var getCode = function(){
		Ext.Ajax.request({
   			url : ctx+"/spxx/getSpxxCode.do",
   			success : function(o) {
   				if(o.responseText){
   					addForm.getForm().findField("spid").setValue(o.responseText);
   				}
   			}
   		});
	};
	
	//布局
    new Ext.Viewport({
		layout:'fit',
		items:[{
			frame:true,
			layout:'border',
			items:[tree,grid]
		}]
	});

});