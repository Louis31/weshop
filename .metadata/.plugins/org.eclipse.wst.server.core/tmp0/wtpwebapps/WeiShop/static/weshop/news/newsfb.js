/*!
 * 进货入库
 */

Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var SpxxObj = [
		{ name:'inforname', type:'string'},
		{ name:'treeName', type:'string'},
		{ name:'ctime', type:'date',mapping :'ctime.time', dateFormat : 'time'},
		{ name:'author', type:'string'},
		{ name:'dateU', type:'string'},
		{ name:'uauthor', type:'string'},
		{ name:'click', type:'string'},
		{ name:'collect', type:'string'},
		{ name:'id', type:'int'},
		{ name:'content', type:'string'}
	];

	var v_start="0", v_limit="10";
	//资讯数据
	var store = new Ext.data.JsonStore({
	    url: ctx+'/information/getInformationBytree.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields: SpxxObj,
	    listeners:{beforeload:function(a){a.baseParams={tree:'0',start:v_start, limit:v_limit};}}
	});
	
	
	//评论列表
    var jhspGrid = new Ext.grid.GridPanel({
    	id:'djspGrid',
        store: store,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[
			     new Ext.grid.RowNumberer(),
				{header: '资讯类别', width: 100, sortable:true, dataIndex: 'treeName'},
	            {header: '资讯标题', width: 100, sortable:true, dataIndex: 'inforname'},
	            {header: '发布时间', width: 150, sortable:true, dataIndex: 'ctime',renderer:Ext.util.Format.dateRenderer('Y-m-d')},
	            {header: '发布者', width: 100, sortable:true, dataIndex: 'author'},
	            {header: '更新时间', width: 100, sortable:true, align:'right',dataIndex:"dateU"},
	            {header: '更新者', width: 100, sortable:true, align:'center', dataIndex: 'uauthor'},
	            {header: '点击次数', width: 100, sortable:true, align:'center', dataIndex: 'click'},
				{header: '收藏次数', width: 100, sortable:true, align:'center', dataIndex: 'collect'}
              ]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        margins:'20',
        style:'border:1px solid',
		region:'center',
        iconCls:'',
        
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
			 alert(search);
        	 store.load({params:{search:search}});
         }}],
    bbar: new Ext.PagingToolbar({
        pageSize: 15,
        store: store,
        displayInfo: true
    })
        ,
        listeners:{
             	render:function(){
             		var tbar = new Ext.Toolbar ({
             			items:[
             			    {
             	        	text:'发布新资讯',
             	        	iconCls:'btn-edit',
             	        	handler: function(){
             	        		addForm.getForm().findField("updateOrAdd").setValue("0");
								addWindow.show();
             	        	}
             	           },{
                	        	text:'删除所选',
                 	        	iconCls:'btn-edit',
                 	        	handler: function(){
                 	        		var record= jhspGrid.getSelectionModel().getSelected(); 
							Ext.MessageBox.confirm("删除提示", "是否删除", function(c) {
							if(c=="yes"){
					   		Ext.Ajax.request({
					   			url : ctx+"/information/deleteInformation.do",
					   			params:{ id : record.id },
					   			success : function(o) {
					   				if(o.responseText=="false"){
					   					Ext.Msg.alert("信息提示","该类别有商品信息不能删除");
					   				}else{
						   				store.reload();
					   				}
					   			}
					   		});
					    }
					});
					
					
                 	        	}
                 	         },
                 	        {
                 	        	text:'修改',
                  	        	iconCls:'btn-edit',
                  	        	handler: function(){
                  	        		var record= jhspGrid.getSelectionModel().getSelected(); 
                  	        		if(record==null){
                  	        			alert("请选择");
                  	        			return;
                  	        		}
                  	        		addForm.getForm().loadRecord(record);
                  	        		addForm.getForm().findField("id").setValue(record.get("id"));
                  	        		addForm.getForm().findField("updateOrAdd").setValue("1");
                  	        		addWindow.show();
 							      }
                  	         }
             			]
             		});
             tbar.render(jhspGrid.tbar);
            },
             
        	rowdblclick:function(){
        		var record= jhspGrid.getSelectionModel().getSelected(); 
				if(record){
	        		addJhWindow.show();
	        		addJhWindow.buttons[0].setVisible(false);
	        		record.set("update","true");
					addJhForm.getForm().loadRecord(record);
					updateWindow.show();
				}
        	}
        }
    });
    //  add
	
     //资讯表单
    var addForm = new Ext.FormPanel({
    	id:'addZXForm',
		layout : 'form',
		frame:true,
		labelWidth:60,
		border : false,
		padding : '15 10 0 8',
		defaults : {
			anchor : '100%'
		},
		items:[
		          {id:'lbtext',
				    width : 40,
				    xtype : 'textfield',
				    name:'inforname',
					fieldLabel:'资讯标题',
					allowBlank : false,
					maxLength :40,
					},{
						id:'treeName',
						width:40,
						xtype : "textfield",
						name:"treeName",
						fieldLabel:"资讯类别",
						value:"所有类别",
						maxLength :50,
						enableKeyEvents:true,
						listeners:{
							focus:function(){
								splbTreeWindow.show();
							}
						}
				},{
						xtype: "StarHtmleditor",
			            height: 250,
			            name:"content",
			            fieldLabel: '图文详情'
					},{
						xtype : 'hidden',
						name : 'treeId',
						value:'0'
					},{
						xtype : 'hidden',
						name : 'updateOrAdd',
						value:'0'
					},{
						xtype : 'hidden',
						name : 'id'
					}
		      ]
	});

  
		var v_lbid="0",v_lbname="所有类别",v_start=0, v_limit=20;
		
	//商品类别树窗口
    var splbTreeWindow = new Ext.Window({
		width:240,
		height:260,
		closeAction:"hide",
		layout : "fit",
		buttonAlign : "center",
		items : [{
			xtype:"treepanel",
			useArrows: true,
	        autoScroll:true,
	        enableDD:false,
	        containerScroll: true,
	        dataUrl: ctx+"/infortree/getInformationTree.do",
	        root: {
	            nodeType: "async",
	            text: "所有类别",
	            select:true,
	            draggable: false,
	            id: "0"
	        },
	        listeners:{
	        	load:function(){
	        		this.getSelectionModel().select(this.root);
	        	},
	        	click:function(n){
	        		v_lbid = n.id;
	        		v_lbname = n.text;
	        	},
	        	dblclick:function(n){
	        		v_lbid = n.id;
	        		v_lbname = n.text;
	        		splbTreeWindow.hide();
					addForm.getForm().findField("treeName").setValue(v_lbname);
					addForm.getForm().findField("treeId").setValue(v_lbid);
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
			text : "选择",
			handler : function() {
				splbTreeWindow.hide();
				addForm.getForm().findField("treeName").setValue(v_lbname);
				addForm.getForm().findField("treeId").setValue(v_lbid);
			}
		}, {
			width:60,
			text : "取消",
			handler : function() {
				splbTreeWindow.hide();
			}
		}]
	});
	
  
    var submitSpxx = function(next){
		if (addForm.getForm().isValid()) {
			var url = "saveInformation";
			if(addForm.getForm().findField("updateOrAdd").getValue()=="1"){
				url = "updateInformation";
			}
			addForm.getForm().submit({
				url : ctx+'/information/'+url,
				success : function(form, action) {
				    if(next){
				    	addForm.getForm().reset();
				    }else{
						Ext.Msg.alert('信息提示',action.result.message);
						addWindow.hide();
						store.reload();
				    }
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
  //增加商品窗口
    var addWindow = new Ext.Window({
		title : '增加资讯',
		width:"60%",
		height:450,
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
			 submitSpxx(false);
			}
		}, {
			text : '取消',
			handler : function() {
				addWindow.hide();
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
			items:[jhspGrid]
		}],
		listeners:{
			render:function(){
			store.load();
			}
		}
	});
	
 
	

});