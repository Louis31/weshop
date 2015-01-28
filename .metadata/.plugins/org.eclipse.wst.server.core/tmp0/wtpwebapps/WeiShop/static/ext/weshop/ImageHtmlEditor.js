HTMLEditor = Ext.extend(Ext.form.HtmlEditor, {
	enableFont: true,  
    enableFontSize: true,  
    enableFormat: true,
    addImage : function() {
        var editor = this;
        var imgform = new Ext.FormPanel({
            region : 'center',
            labelWidth : 55,
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            autoScroll : true,
            border : false,
            fileUpload : true,
            items : [{
                        xtype : 'textfield',
                        fieldLabel : '选择文件',
                        name : 'editimage',
                        inputType : 'file',
                        allowBlank : false,
                        blankText : '文件不能为空',
                        height : 25,
                        anchor : '90%'
                    },
                    {
    				   xtype : 'textfield',
    					name:'width',
    					fieldLabel:'宽度',
    					allowBlank : false,
    					 height : 25,
    					anchor : '90%'
    				},{
    					xtype : 'textfield',
    					name:'height',
    					fieldLabel:'高度',
    					allowBlank : false,
    					 height : 25,
    					anchor : '90%'
    				},{
    					xtype : 'textfield',
    					name:'content',
    					fieldLabel:'内容',
    					allowBlank : false,
    					 height : 25,
    					anchor : '90%'
    				}
                    ],
            buttons : [{
                text : '上传',
                type : 'submit',
                handler : function() {
                    if (!imgform.form.isValid()) {return;}
                    imgform.form.submit({
                        waitMsg : '正在上传',
                        url : ctx+'/editorimage/uploadEditorImage',
                        success : function(form, action) {
                            editor.insertImg(editor,action.result);
                            win.hide();
                        },
                        failure : function(form, action) {
                            form.reset();
                            if (action.failureType == Ext.form.Action.SERVER_INVALID)
                                Ext.MessageBox.alert('警告',
                                        action.result.errors.msg);
                        }
                    });
                }
            }, {
                text : '关闭',
                type : 'submit',
                handler : function() {
                    win.close(this);
                }
            }]
        });

        var win = new Ext.Window({
                    title : "上传图片",
                    width : 300,
                    height : 200,
                    modal : true,
                    border : false,
                    iconCls : ctx+"/static/images/picture.png",
                    layout : "fit",
                    items : imgform

                });
        win.show();
    },
    createToolbar : function(editor) {
        HTMLEditor.superclass.createToolbar.call(this, editor);
        this.tb.insertButton(16, {
                    cls : "x-btn-icon",
                    icon : ctx+"/static/images/picture.png",
                    handler : this.addImage,
                    scope : this
                });
    },
    /**
     * 插入图片
     * */
     insertImg: function (view, data) {
       var url = data.url;
       var content = data.content;
       var width = data.width;
       var height = data.height;
       var str = '<img src="' + ctx+"/upload"+url + '" border="0" ';
       if (content != undefined && content != null && content != '') {
         str += ' title="' + content + '" ';
       }
       if (width != undefined && width != null && width != 0) {
         str += ' width="' + width + '" ';
       }
       if (height != undefined && height != null && height != 0) {
         str += ' height="' + height + '" ';
       }
       str += ' />';
       view.insertAtCursor(str);
     },
     getValue:function(){
    	 var ret = HTMLEditor.superclass.getValue.apply(this, arguments);  
    	  if (ret) {  
    	   // fix edit grid panel compare startvalue & nowvalue  
    	   // fix '\n' patch for webkit(chrome) when ENTER pressed  
    	   ret = ret.replace(/^(&nbsp;|<br>|\s)*|(&nbsp;|<br>|\s)*$/ig, '')  
    	            ret = ret.replace(/<div>(.+?)<\/div>/ig,'<br>$1')  
    	  }  
    	  if (!ret) {  
    	   ret = '';  
    	  }  
    	  return ret;  
     }
});
Ext.reg('StarHtmleditor', HTMLEditor);