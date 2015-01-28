/**
 * @fileOverview 增删改查基本
 * @ignore
 */
define('bui/ux/crudgrid',['bui/common','bui/grid','bui/form','bui/data','bui/overlay'],
		function (require) {
  var BUI = require('bui/common'),
    Grid = require('bui/grid'),
    Data = require('bui/data'),
    Overlay = require('bui/overlay'),
    Form = require('bui/form');
  /**
   * @class CrudGrid
   * 增删改查基本
   */
  function CrudGrid(config){
	  CrudGrid.superclass.constructor.call(this, config);
	  this._init();
  }

  CrudGrid.ATTRS = {
	/**
	 * grid对象的主键列
	 * @type {String}
	 */
	pkColumn : {
		
	},
	/**
	 * grid对象的名字
	 * @type {String}
	 */
	entityName : {
		
	},
    /**
     * 是否自动查询，打开页面时未点击查询按钮时是否自动查询
     * @type {Boolean}
     */
    autoSearch :{
      value : true
    },
    /**
     * grid 容器的 id
     * @type {String}
     */
    gridId : {
      value : 'grid'
    },
    /**
     * 增加和修改的Form
     * @type {Object}
     */
    addOrUpdateFormId : {
        value : 'addOrUpdateForm'
      },
    /**
     * 表单的容器的id
     * @type {String}
     */
    searchFormId : {
      value : 'searchForm'
    },
    /**
     * 查询按钮的id
     * @type {Object}
     */
    searchBtnId : {
      value : 'btnSearch'
    },
    /**
     * 表单的配置项
     * @type {Object}
     */
    searchFormCfg : {
      value : {}
    },
    /**
     * grid 表格的配置项
     * @type {Object}
     */
    gridCfg : {

    },
    /**
     * grid的columns
     * @type {Array}
     */
    columns : {
    	
    },
    /**
     * 表单对象
     * @type {HForm}
     */
    searchForm : {

    },
    /**
     * 表格对象
     * @type {BUI.Grid.Grid}
     */
    grid : {

    },
    /**
     * 数据缓冲类 配置
     * @type {Object}
     */
    storeCfg : {

    },
    /**
     * 数据缓冲类
     * @type {BUI.Data.Store}
     */
    store : {

    },
    
    /**
     * 加载列表的URL
     * @type {String}
     */
    storeUrl :{
    	value : ''
    },
    /**
     * 添加记录时的URL
     * @type {String}
     */
    addUrl : {
    	value : ''
    },
    /**
     * 添加修改记录时的URL
     * @type {String}
     */
    updateUrl : {
    	value : ''
    },
    /**
     * 删除记录时的URL
     * @type {String}
     */
    removeUrl : {
    	value : ''
    },
    
    /**
     * 新增和修改对话框的配置属性
     */
    dialogCfg :{
    	
    },
    
    /**
     * 增加和修改的对话框对应的DOM ID
     * @type {String}
     */
    dialogContentId : {
    	
    },
    /**
     * 增加和修改的对话框
     * @type {Object}
     */
    addOrUpdatedialog : {
    	
    },
    /**
     * 增加和修改的From Id
     * 
     */
    addOrUpdateFromId :{
    	value : 'addOrUpdateForm'
    },
    /**
     * 增加和修改的From
     */
    addOrUpdateFrom :{
    	
    },
    /**
     * 增加和修改的From的配置
     */
    addOrUpdateFromCfg:{
    	
    },
    /**
     * grid.tbar.items追加
     * 可以是数组或者是单个对象
     */
    appendTbarItems : {
    	value : []
    },
    /**
     * 是否显示添加按钮
     */
    showAddBtn : {
    	value : true
    },
    /**
     * 是否显示编辑按钮
     */
    showUpdateBtn : {
    	value : true
    },
    /**
     * 是否显示删除按钮
     */
    showRemoveBtn : {
    	value : true
    },
    /**
     * 保存类型,add或update
     * 
     */
    saveType : {
    	value : 'add'
    },
    /**
     * 操作列配置，该配置中的项会覆盖默认配置的项，不是整个而是替换里面的配置项
     * 默认配置为 {title : '操作',dataIndex:'',sortable : false}
     */
    operationColumnCfg:{
    	
    },
    /**
     * 是否显示操作列
     */
    showOperationColumn : {
    	value : true
    },
    /**
     * 操作列的renderer，如果是showRemoveBtn和showUpdateBtn为true还会在renderer里添加编辑和删除两个按钮
     */
    operationColumnRenderer : {
    	
    },
    /**
     * 
     */
    operationColumn : {
    	value : ''
    },
    gridPlugins : {
    	value :[Grid.Plugins.CheckSelection,Grid.Plugins.AutoFit,Grid.Plugins.GridMenu]
    },
    pagingBar : {
    	value : true
    },events : {
        value : [
           /**
            * 显示添加窗口前的事件
            * @event beforeAddShow
            *  @param {dialog} 新增和修改的窗口
            *  @param {Form} 新增和修改的Form
            *  @resut {boolean} 当返回flase时，则不会弹出新增窗口
            */ 
            'beforeAddShow',
            /**
             * 显示修改窗口前的事件
             * @event beforeUpdateShow
             *  @param {dialog} dialog 新增和修改的窗口
             *  @param {Form}  form 新增和修改的Form
             *  @param {Object} record 修改的列的数据
             *  @resut {boolean} 当返回flase时，则不会弹出修改窗口
             */ 
            'beforeUpdateShow'
        ]
    }
  };

  BUI.extend(CrudGrid,BUI.Base);

  BUI.augment(CrudGrid,{
    _init : function(){
      var _self = this;
      _self._initStore();
      _self._initSearchForm();
      _self._initStoreEvent();
      _self._initOperationColumn();
      _self._initGrid();
      _self._initEvent();
      _self._initData();
      _self._initAddOrUpdateForm();
      _self._initAddOrUpdatedialog();
      //_self.set('self',_self);
    },
    //初始化事件
    _initEvent : function(){
      this._initDomEvent();
      this._initSearchFormEvent();
      this._initGridEvent();
    },
    _initDomEvent : function(){
      var _self = this,
      searchBtnId = _self.get('searchBtnId'),
        //store = _self.get('store'),
      searchForm = _self.get('searchForm');
      $('#'+searchBtnId).on('click',function(ev){
        ev.preventDefault();
        searchForm.valid();
        if(searchForm.isValid()){
          _self.load(true);
        }
      });
    },
    //初始化Searchform
    _initSearchForm : function(){
      var _self = this,
      searchForm = _self.get('form');
      if(!searchForm){
        var formCfg = BUI.merge(_self.get('searchFormCfg'),{
          srcNode : '#' + _self.get('searchFormId')
        });
        form = new Form.HForm(formCfg);
        form.render();
        _self.set('searchForm',form);
      }
    },
    //初始化Sore
    _initStore : function(){
    	var _self = this,
    	store = _self.get('store');
    	if(!store){
    		var cfg = {
    	    	url : _self.get('storeUrl'),
    	        proxy : {
    	        	save : { //也可以是一个字符串，那么增删改，都会往那么路径提交数据，同时附加参数saveType
    	        		addUrl : _self.get('addUrl'),
    	        		updateUrl : _self.get('updateUrl'),
    	        		removeUrl : _self.get('removeUrl')
    	            	},
    	            method : 'POST'
    	           },
    	          root : 'content',
    	    		totalProperty : 'totalElements',
    	    		pageSize : 15,
    	    		
    	        	autoLoad: false,
    	        	autoSync : true, //保存数据后，自动更新
    	    		remoteSort: true
    	 	};
    		var storeCfg =_self.get('storeCfg');
    	
    		if(!storeCfg){
    			cfg = BUI.merge(cfg,storeCfg);
    		}
    		var store = new Data.Store(cfg);
    		_self.set('store',store);
    	}
    },
    _initSearchFormEvent : function(){

    },
    //初始化操作列
    _initOperationColumn : function(){
    	var _self = this,
    	showOperationColumn = _self.get('showOperationColumn'),
    	operationColumnRenderer = _self.get('operationColumnRenderer');
    	if(showOperationColumn){
            	var showRemoveBtn = _self.get('showRemoveBtn'),
            	showUpdateBtun = _self.get('showUpdateBtn'),
            	operationColumnCfg = _self.get('operationColumnCfg'),
            	cfg = {title : '操作',dataIndex:'',sortable : false};
            	if(operationColumnCfg){
            		cfg = BUI.merge(cfg,operationColumnCfg);
            	}
    		var operationColumn = new Grid.Column(cfg);
    		var rendererStr = '';
    		if(showUpdateBtun){
    			rendererStr += '<span class="grid-command" title="编辑"><i class="icon-edit btn-edit"></i></span>';
    		}
    		if(showRemoveBtn){
    			rendererStr += '<span class="grid-command" title="删除"><i class="icon-trash btn-del"></i></span>';
    		}
    		
    		if(rendererStr.length>0||operationColumnRenderer){
    			operationColumn.set('renderer',function(value,obj){
    				var append = '';
    				if(operationColumnRenderer){
    					append = operationColumnRenderer(value,obj);//传进来的操作列的Renderer
    				}
    				return  append + rendererStr;
    			});
    			_self.set('operationColumn',operationColumn);
    		}
    	}
    },
    //初始化表格
    _initGrid : function(){
      var _self = this,
        grid = _self.get('grid');
      if(!grid){
        var gridCfg = _self.get('gridCfg'),
          store = _self.get('store'),
          gridId = _self.get('gridId'),
          shoAddBtn = _self.get('showAddBtn'),
          showRemoveBtn = _self.get('showRemoveBtn'),
          //showUpdateBtun = _self.get('showUpdateBtn'),
          showOperationColumn = _self.get('showOperationColumn'),
          operationColumn = _self.get('operationColumn'),
          columns = _self.get('columns'),
          appendTbarItems = _self.get('appendTbarItems'),
          gridPlugins = _self.get('gridPlugins');
          items = [];
        if(shoAddBtn){
        	var addFunction = function(){
        		_self.add(_self);
        	};
        	var addBtn = {text : '<i class="icon-plus"></i>新建',btnCls : 'button button-small',handler: addFunction};
        	items.push(addBtn);
        }
        if(showRemoveBtn){
        	var delFunction = function (){
                _self.del(_self);
            };
        	items.push({text : '<i class="icon-remove"></i>删除',btnCls : 'button button-small',handler : delFunction});
        }
        if(appendTbarItems&& appendTbarItems.length>0){
        	$.each(appendTbarItems,function(i,item){
        		items.push(item);
        	});
        }
        if(showOperationColumn && operationColumn){
        	columns.push(operationColumn);
        }
        
        var cfg =  {
         	 render:'#' + gridId,
         	 columns : columns,
         	 store : store,
              tbar : {
                  items : items
               },
             width:'100%',//这个属性一定要设置
             loadMask: true,
             plugins : gridPlugins, // 插件形式引入多选表格
             bbar:{
                 // pagingBar:表明包含分页栏
                 pagingBar: _self.get('pagingBar')
             }
         };
        cfg = BUI.merge(cfg,gridCfg);
        //gridCfg.store = store;
        //var gridId = _self.get('gridId');
        //gridCfg.render = '#' +gridId;
        grid = new Grid.Grid(cfg);
        grid.render();
        _self.set('grid',grid);
      }
    },
    _initGridEvent : function(){
        var _self = this,
        grid = _self.get('grid');
        grid.on('cellclick',function(ev){
            var sender = $(ev.domTarget); //点击的Dom
            if(sender.hasClass('btn-del')){
              var record = ev.record;
              _self.delItems([record]);
            }else if(sender.hasClass('btn-edit')){
                var record = ev.record;
                _self.edit(record);
            }
          });
    },
    _initData : function(){
      var _self = this,
        autoSearch = _self.get('autoSearch');
      if(autoSearch){
        _self.load(true);
      }
    },
    //初始化数据加载事件
    _initStoreEvent : function(){
      var _self = this,
        store = _self.get('store');
      //处理异常
      store.on('exception',function(ev){
        BUI.Message.Alert(ev.error);
      });
    },
    _initAddOrUpdateForm : function(){
    	var _self = this,
    	addOrUpdateForm = _self.get('addOrUpdateForm');
    	if(!addOrUpdateForm){
    		var addOrUpdateFormId = _self.get('addOrUpdateFormId');
            var formCfg = BUI.merge(_self.get('addOrUpdateFormCfg'),{
                srcNode : '#' + _self.get('addOrUpdateFormId')
              });
    	     addOrUpdateForm = new Form.HForm(formCfg);
    	     addOrUpdateForm.render();
    	     _self.set('addOrUpdateForm',addOrUpdateForm);
    	}
    },
    _initAddOrUpdatedialog : function(){
    	var _self = this,
    	dialogCfg = _self.get('dialogCfg'),
    		addOrUpdatedialog = _self.get('addOrUpdatedialog');
    	if(!addOrUpdatedialog){
    		var dialogContentId = _self.get('dialogContentId'),
    			addOrUpdateForm = _self.get('addOrUpdateForm'),
    			entityName = _self.get('entityName'),
    			store = _self.get('store');
    		var successCallBack = function () {
    			var crubGrid = _self;
    			addOrUpdateForm.valid();
               	 if(addOrUpdateForm.isValid()){
               		 var param = addOrUpdateForm.serializeToObject(); 
                   	 var callback = function(data){
                   		 if(data.success){
                       		 addOrUpdateForm.clearFields();
                       		 addOrUpdatedialog.close();
                   		 }else{
                   			 BUI.Message.Alert("操作失败:"+data.msg,'warning');
                   		 }
                   	 };
                   	var saveType = crubGrid.get('saveType');
               		 store.save(saveType, param, callback);//使用store的方法
               	 }
            };
            var cancel = function(){
           		addOrUpdateForm.clearFields();
           		var fields = addOrUpdateForm.getFields();
           		BUI.each(fields,function(field){
           			if(field.get('disabled')){
           				field.enable();//必须把disable的都打开，否则会出现update的时候无法赋值的问题
           			}
                });
           };
           var cfg =  {
                   title:'新增'+entityName,
                   //width:500,
                   //height:320,
                   //配置DOM容器的编号
                   contentId: dialogContentId,
                   success: successCallBack,
                   cancel : cancel
               };
           if(dialogCfg){
        	   cfg = BUI.merge(cfg,dialogCfg);
           }
            addOrUpdatedialog = new BUI.Overlay.Dialog(cfg);
            addOrUpdatedialog.on('show',function(){
            	addOrUpdateForm.clearErrors();
            });
          _self.set('addOrUpdatedialog',addOrUpdatedialog);
    	}
    },
    del : function(){
    	this._del(this);
    },
    _del : function(crudGrid){
        grid = crudGrid.get('grid');
        var selections = grid.getSelection();
        crudGrid.delItems(selections);
    },
    add : function(){
    	this._add(this);
    },
    _add : function(crudGrid){
    	var _self = this,
    		addOrUpdatedialog = crudGrid.get('addOrUpdatedialog'),
    		addOrUpdateForm = _self.get('addOrUpdateForm'),
    		entityName = crudGrid.get('entityName');
    	addOrUpdatedialog.set('title','新增'+entityName);
    	crudGrid.set('saveType','add');
        /**
         * 显示添加窗口前的事件
         * @event beforeAddShow
         */ 
    	var result = crudGrid.fire('beforeAddShow',addOrUpdatedialog,addOrUpdateForm);
    	if(typeof(result) == "undefined" || result){
    		addOrUpdatedialog.show();
    	}
    },
    edit : function(record){
    	var _self = this,
    		addOrUpdatedialog = _self.get('addOrUpdatedialog'),
    		addOrUpdateForm = _self.get('addOrUpdateForm'),
    		entityName = _self.get('entityName');
    	_self.set('saveType','update');
    	var a = _self.get('saveType');
    	addOrUpdatedialog.set('title','修改'+entityName);
    	addOrUpdateForm.setRecord(record);
        /**
         * 显示添加窗口前的事件
         * @event beforeAddShow
         */ 
    	var result = _self.fire('beforeUpdateShow',addOrUpdatedialog,addOrUpdateForm,record);
    	if(typeof(result) == "undefined" || result){
    		addOrUpdatedialog.show();
    	}
    },
    /**
     * 删除列表选中的数据
     * @@param {Array} items 选中的数据
     */
    delItems : function(items){

        var _self = this,
          store = _self.get('store'),
          ids = [];
        BUI.each(items,function(item){
          ids.push(item.id);
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
    },
    /**
     * 加载数据
     * @param {Boolean} reset 是否重置表格查询的页数
     */
    load : function(reset){
      var _self =this,
        form = _self.get('searchForm'),
        store = _self.get('store'),
        param = form.serializeToObject();
      if(reset){
        param.start=0;
        param.pageIndex = 0;
      }
      store.load(param);
    }
  });
  
  CrudGrid.createLink = function(cfg){
	    var temp = '<span class="page-action grid-command" data-id="{id}" data-href="{href}" title="{title}">{text}</span>';
	    return BUI.substitute(temp,cfg);
	  }
  return CrudGrid; 
});