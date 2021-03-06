imagePanel = Ext.extend(Ext.Panel, {
	_form : null,
	_grid : null,
	_tree : null,
	_panel:null,
	constructor : function(_config) {
		if (_config == null) {
			_config = {};
		}
		Ext.apply(this, _config);
		this._url = this.upload_url;
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
		       		{ name:'imageinfo', type:'string'}
		       	];
		this.store = new Ext.data.JsonStore({
		    url: ctx+'/spxx/findPageSpxx.do',
		    root: 'root',
		    totalProperty: 'total',
		    fields: SpxxObj,
		    listeners:{beforeload:function(a){a.baseParams={start:0, limit:10};}}
		});
//	    this.store = new Ext.data.ArrayStore({
//			autoLoad : {
//				start : 0,
//				limit : 10
//			},
//			pageSize : 10,
//			proxy : {
//				type : 'ajax',
//				url : "sys/file-info-manage.action?refGuId="+this._refGuId,
//				reader : {
//					type : 'json',
//					root : 'rows',
//					totalProperty : "totalCount"
//				}
//			},
//			model : 'FileInfo'
//
//    });      

 

    var tpl = new Ext.XTemplate(

     '<table cellpadding=0 cellspacing=0 border=1 width=400px>',
        
          '<tr><th>名称：</th><th>位置</th> <th>预览</th></tr>',
        
   '<tpl for=".">',

         '<tr><td>{fileName}</td><td>{first}</td><td><img src="{filePath}" height="40" width="60" title="{name}"></td></tr>',

    '</tpl>',
     
          '</table>',
    '<div class="x-clear"></div>'

    );

		

		_config._grid = this._grid ;

	imagePanel.superclass.constructor.call(this, {
		title : this._title,
		 id:'images-view',
		width : 800,
								height : 456,
	    frame:true,
	
	    autoWidth:true,
	
	    autoHeight:true,
	
	    collapsible:true,
	
	    layout:'fit',
	
	    title:'Simple DataView',
	
	    items: [
	    	new Ext.DataView({
	
	        store: this.store,
	
	        tpl: tpl,autoHeight:true,multiSelect: true,overClass:'x-view-over',
	
	        itemSelector:'div.thumb-wrap',
	
	        emptyText: 'No images to display'           
	
	        })] ,
			dockedItems : [ {
				dock : 'bottom',
				xtype : 'pagingtoolbar',
				store : this["store"],
				displayInfo : true
			}, {
				dock : 'top',
				xtype : 'toolbar',
				items : [ {
					text : "上传附件",
					id : 'BT_FILEINFO_UPLOAD',
					handler : this.onUploadHandler,
					scope : this
				}, "-", {
					text : "删除",
					id : 'BT_FILEINFO_DEL',
					handler : this.onDelHandler,
					scope : this
				} ]
			} ],
			listeners : {
				'itemdblclick' : {
					fn : function(_view,_record,_item) {
						var url = 'sys/file-info-manage!download.action?guId=' + _record.get('guId');
						window.open(url);
					},
					scope : this
				}
			}

		});

	},
	onUploadHandler : function(){
		
		var url = this._url;
		var store = this.store;
		var _win = new FileUploadWin( {
			_fileInfoGrid : this,
			callBack : function(){
				store.load();
			},
			file_post_name : 'file',
			post_params : {savePath:'temp\\'},
			file_types : '*.*',
			upload_url : url
		}).show();
	},
	onDelHandler : function(){
		var _guIds  = getGridValues(this,'guId');
		var _grid = this;
		askMesg({
			msg : '确定删除?',
			fn : function(btn, text){
			if (btn == 'ok') {
				ajaxRequest( {
					url : 'sys/file-info-manage!delete.action',
					params : {
						guIds : _guIds
					},
					callBack : function(returnData) {
						_grid.store.load();
					}
				});
			}
			}
		});
	},
	selectionChange : function() {
		var selectedCount = this.getSelectionModel().getSelection().length;
		if (selectedCount == 0) {
			this.setBtStatus();
		} else if (selectedCount == 1) {
			this.setBtStatus('single');
		} else {
			this.setBtStatus('multi');
		}
	},
	setBtStatus : function(type) {
		switch (type) {
		case "single": {
			Ext.getCmp('BT_FILEINFO_UPLOAD').enable();
			Ext.getCmp('BT_FILEINFO_DEL').enable();
			break;
		}
		case "multi": {
			Ext.getCmp('BT_FILEINFO_UPLOAD').enable();
			Ext.getCmp('BT_FILEINFO_DEL').enable();
			break;
		}
		default: {
			Ext.getCmp('BT_FILEINFO_UPLOAD').enable();
			Ext.getCmp('BT_FILEINFO_DEL').disable();
			break;
		}
		}
	}
});
Ext.reg('imagePanel', imagePanel);