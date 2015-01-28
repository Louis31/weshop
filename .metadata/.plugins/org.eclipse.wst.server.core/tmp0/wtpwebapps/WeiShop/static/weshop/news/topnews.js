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
		{ name:'ftop', type:'string'},
		{ name:'ptop', type:'string'},
		{ name:'id', type:'int'},
		{ name:'content', type:'string'}
	];
		var v_start="0", v_limit="10";
	//商品数据
	var jhspStore = new Ext.data.JsonStore({
	    url: ctx+'/information/getInformationBytree.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields: SpxxObj,
	    listeners:{beforeload:function(a){a.baseParams={tree:'0',start:v_start, limit:v_limit};}}
	});
	
	//评论列表
    var jhspGrid = new Ext.grid.GridPanel({
    	id:'djspGrid',
        store: jhspStore,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[
			     new Ext.grid.RowNumberer(),
				{header: '资讯类别', width: 100, sortable:true, dataIndex: 'treeName'},
	            {header: '资讯标题', width: 100, sortable:true, dataIndex: 'inforname'},
	            {header: '发布时间', width: 100, sortable:true, dataIndex: 'ctime',renderer:Ext.util.Format.dateRenderer('Y-m-d')},
	            {header: '发布者', width: 100, sortable:true, dataIndex: 'author'},
	            {header: '更新时间', width: 100, sortable:true, align:'right',dataIndex: 'dateU'},
	            {header: '更新者', width: 100, sortable:true, align:'center', dataIndex: 'uauthor'},
	            {header: '点击次数', width: 100, sortable:true, align:'center', dataIndex: 'click'},
				{header: '收藏次数', width: 100, sortable:true, align:'center', dataIndex: 'collect'},
				{header: '首页置顶', width: 100, sortable:true, align:'center', dataIndex: 'ftop',renderer:function(value){
				    if(value=='1'){
	            	return "是"
					}else{
					return "否"
					};}},
				{header: '频道置顶', width: 100, sortable:true, align:'center', dataIndex: 'ptop',renderer:function(value){
				    if(value=='1'){
	            	return "是"
					}else{
					return "否"
					};
	            }}
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
        	 store.load({params:{search:search}});
         }}],
    bbar: new Ext.PagingToolbar({
        pageSize: 15,
        store: jhspStore,
        displayInfo: true
    })
        ,
        listeners:{
             	render:function(){
             		var tbar = new Ext.Toolbar ({
             			items:[
             			    {
             	        	text:'设置至首页置顶',
             	        	iconCls:'btn-edit',
             	        	handler: function(){
             	        		var record= jhspGrid.getSelectionModel().getSelected(); 
								if(!record){
								Ext.Msg.alert("信息提示","请选择一条资讯");
								return;
								}
								
								Ext.MessageBox.confirm("设置置顶", "是否设置首页置顶", function(c) {
							if(c=="yes"){
					   		Ext.Ajax.request({
					   			url : ctx+"/information/setFTop.do",
					   			params:{ id : record.id,top:'1' },
					   			success : function(o) {
					   				if(o.responseText=="false"){
					   					Ext.Msg.alert("信息提示","设置失败");
					   				}else{
						   				Ext.Msg.alert("信息提示","成功");
										jhspStore.reload();
					   				}
					   			}
					   		});
					    }
					});
								
								
								
             	        	}
             	           },{
                	        	text:'设置至频道置顶',
                 	        	iconCls:'btn-edit',
                 	        	handler: function(){
                 	        		var record= jhspGrid.getSelectionModel().getSelected(); 
									if(!record){
								Ext.Msg.alert("信息提示","请选择一条资讯");
								return;
								}
									
										Ext.MessageBox.confirm("设置置顶", "是否设置频道置顶", function(c) {
							if(c=="yes"){
					   		Ext.Ajax.request({
					   			url : ctx+"/information/setPTop.do",
					   			params:{ id : record.id,top:'1' },
					   			success : function(o) {
					   				if(o.responseText=="false"){
					   					Ext.Msg.alert("信息提示","设置失败");
					   				}else{
						   				Ext.Msg.alert("信息提示","成功");
										jhspStore.reload();
					   				}
					   			}
					   		});
					    }
					});
									
									
									
                 	        	}
								
                 	         },
                 	        {
                 	        	text:'取消首页置顶',
                  	        	iconCls:'btn-edit',
                  	        	handler: function(){
                  	        		var record= jhspGrid.getSelectionModel().getSelected(); 
									if(!record){
								Ext.Msg.alert("信息提示","请选择一条资讯");
								return;
								}
									
										Ext.MessageBox.confirm("设置置顶", "是否取消首页置顶", function(c) {
							if(c=="yes"){
					   		Ext.Ajax.request({
					   			url : ctx+"/information/setFTop.do",
					   			params:{ id : record.id,top:'0' },
					   			success : function(o) {
					   				if(o.responseText=="false"){
					   					Ext.Msg.alert("信息提示","设置失败");
					   				}else{
						   				Ext.Msg.alert("信息提示","成功");
										jhspStore.reload();
					   				}
					   			}
					   		});
					    }
					});
									
									
                  	        	}
                  	         },
                  	       {
                  	        	text:'取消频道置顶',
                   	        	iconCls:'btn-edit',
                   	        	handler: function(){
                   	        		var record= jhspGrid.getSelectionModel().getSelected(); 
									if(!record){
								Ext.Msg.alert("信息提示","请选择一条资讯");
								return;
								}
									
										Ext.MessageBox.confirm("设置置顶", "是否取消频道置顶", function(c) {
							if(c=="yes"){
					   		Ext.Ajax.request({
					   			url : ctx+"/information/setPTop.do",
					   			params:{ id : record.id,top:'0' },
					   			success : function(o) {
					   				if(o.responseText=="false"){
					   					Ext.Msg.alert("信息提示","设置失败");
					   				}else{
						   				Ext.Msg.alert("信息提示","成功");
										jhspStore.reload();
					   				}
					   			}
					   		});
					    }
					});
									
									
                   	        	}
                   	         },
                   	      {
                   	        	text:'取消首页和频道置顶',
                    	        	iconCls:'btn-edit',
                    	        	handler: function(){
                    	        		var record= jhspGrid.getSelectionModel().getSelected(); 
										if(!record){
								Ext.Msg.alert("信息提示","请选择一条资讯");
								return;
								}
										
										
										Ext.MessageBox.confirm("取消置顶", "是否取消频道和首页置顶", function(c) {
							if(c=="yes"){
					   		Ext.Ajax.request({
					   			url : ctx+"/information/setFTop.do",
					   			params:{ id : record.id,top:'0' },
					   			success : function(o) {
					   				if(o.responseText=="false"){
					   					Ext.Msg.alert("信息提示","设置失败");
					   				}else{
						   				Ext.Ajax.request({
										url : ctx+"/information/setPTop.do",
										params:{ id : record.id,top:'0' },
										success : function(o) {
					   				if(o.responseText=="false"){
					   					Ext.Msg.alert("信息提示","设置失败");
					   				}else{
						   				Ext.Msg.alert("信息提示","设置成功");
										jhspStore.reload();
					   				}
					   			}
					   		});
					   				}
					   			}
					   		});
					    }
					});
										
										
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
				}
        	}
        }
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
		items:[]
	});
    
  //增加商品窗口
    var addWindow = new Ext.Window({
		title : '增加评论',
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
				
			}
		}, {
			text : '取消',
			handler : function() {
				
			}
		}]
	});
    // add
  
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
				jhspStore.load();
			}
		}
	});
	
 
	

});