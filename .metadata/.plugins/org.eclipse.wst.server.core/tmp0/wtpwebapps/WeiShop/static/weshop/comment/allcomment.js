/*!
 * 进货入库
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var commontObj = [
	                  { name:'id', type:'int'},
	          		{ name:'spid', type:'string'},
	          		{ name:'spname', type:'string'},
	          		{ name:'stars', type:'string'},
	          		{ name:'reviewer', type:'string'},
	          		{ name:'revTime', type:'date',mapping : 'revTime.time', dateFormat : 'time'},
	          		{ name:'content', type:'string'},
	          		{ name:'top', type:'string'},
	          		{ name:'revcon', type:'string'}
	          	];
	          	var v_spid="0", v_spname="",v_start="0",v_limit="10";
	          	//商品数据
	          	var store = new Ext.data.JsonStore({
	          	    url: ctx+'/comment/getCommentByStar.do',
	          	    root: 'root',
	          	    totalProperty: 'total',
	          	    fields: commontObj,
	          	    listeners:{beforeload:function(a){a.baseParams={start:v_start, limit:v_limit,star:"1",end:"2"};}}
	          	});
    var grid = new Ext.grid.GridPanel({
    	id:'grid',
        store: store,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[
				     new Ext.grid.RowNumberer(),
						{header: '商品编号', width: 100, sortable:true, dataIndex: 'spid'},
			            {header: '商品名称', width: 200, sortable:true, dataIndex: 'spname'},
			            {header: '评价星级', width: 150, sortable:true, dataIndex: 'stars',renderer:function(value){
			            	
			               switch(value){
			                case "1":
			                	return "★";
			            	   break;
			                case "2":
			                	return "★★";
			            	   break;
			                case "3":
			                	return "★★★";
			            	   break;
			                case "4":
			                	return "★★★★";
			            	   break;
			                case "5":
			                	return "★★★★★";
			                default:
			                	return "暂无评分";
			            	   break;
			               }
			            }},
			            {header: '发布者', width: 100, sortable:true, dataIndex: 'string'},
			           
			            {header: '浏览评价', width: 100, sortable:true, align:'center', dataIndex: 'content'}
			            ]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        margins:'20',
        style:'border:1px solid',
		region:'center',
        iconCls:'',
        
        tbar:[{
        	text:'★好差',
        	iconCls:'',
        	handler: function(){
        		store.reload({params:{star:"1"}});
        	}
        },'-',{
        	text:'★★差',
        	iconCls:'',
        	handler: function(){
        		store.reload({params:{star:"2"}});
        	}
        },'-',{
        	text:'★★★良好',
        	iconCls:'',
        	handler: function(){
        		store.reload({params:{star:"3"}});
        	}
        },'-',{
        	text:'★★★★好',
        	iconCls:'',
        	handler: function(){
        		store.reload({params:{star:"4"}});
        	}
        }
        ,'-',{
        	text:'★★★★★非常好',
        	iconCls:'',
        	handler: function(){
        		store.reload({params:{star:"5"}});
        	}
        }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: store,
            displayInfo: true
        }),
        listeners:{
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