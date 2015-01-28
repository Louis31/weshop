var FloatHtmlEditor = Ext.extend(Ext.form.HtmlEditor, {
	 createToolbar : function(editor) {
		 FloatHtmlEditor.superclass.createToolbar.call(this, editor);
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
       var str = '<img src="' + ctx+"/upload/editorimage/"+url + '" border="0" ';
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
	        })

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
    fixKeys : function() {  
        if (Ext.isIE) {  
            return function(e) {  
                var k = e.getKey(), doc = this.getDoc(), r;  
                if (k == e.TAB) {  
                    e.stopEvent();  
                    r = doc.selection.createRange();  
                    if (r) {  
                        r.collapse(true);  
                        r.pasteHTML('');  
                        this.deferFocus();  
                    }  
                } else if (k == e.ENTER) {  
                    r = doc.selection.createRange();  
                    if (r) {  
                        var target = r.parentElement();  
                        if (!target || target.tagName.toLowerCase() != 'li') {  
                            e.stopEvent();  
                            r.pasteHTML('<br />');  
                            r.collapse(false);  
                            r.select();  
                        }  
                    }  
                }  
            };  
        } else if (Ext.isOpera) {  
            return function(e) {  
                var k = e.getKey();  
                if (k == e.TAB) {  
                    e.stopEvent();  
                    this.win.focus();  
                    this.execCmd('InsertHTML', '');  
                    this.deferFocus();  
                }  
            };  
        } else if (Ext.isWebKit) {  
            return function(e) {  
                var k = e.getKey();  
                if (k == e.TAB) {  
                    e.stopEvent();  
                    this.execCmd('InsertText', '\t');  
                    this.deferFocus();  
                } else if (k == e.ENTER) {  
                    e.stopEvent();  
                    // fix chrome ENTER double pressed bug  
                    this.execCmd('InsertHTML', '\n<br>');  
                    this.deferFocus();  
                }  
            };  
        }  
    }(),  
    _measureDiv : null,  
    _getFrame : function() {  
        if (!this._frm)  
            this._frm = this.getEl().parent().query('iframe')[0];  
        return this._frm  
    },  
    _adjustHeight : function() {  
        var t = Ext.fly(this._getFrame());  
        var div = this._measureDiv  
        if (!div) {  
            div = this._measureDiv = this.__measureDiv = document  
                    .createElement('div');  
            div.style.position = 'absolute';  
            // div.style.border = '1px solid red'  
            div.style.top = '-1px';  
            div.style.left = '-10000px'  
            div.style.whiteSpace = 'normal';  
            div.style.wordWrap = 'break-word';  
            div.style.lineHeight = '15px'  
            document.body.appendChild(div)  
        }  
        if (t.dom.contentWindow.document.body.innerHTML == '') {  
            t.setHeight(this._min_h);  
            return  
        }  
        if (div.innerHTML == t.dom.contentWindow.document.body.innerHTML)  
            return;  
        var width = parseInt(t.getWidth());  
        Ext.isIE ? width -= 25 : width -= 7  
        div.style.width = width + 'px'  
        div.innerHTML = t.dom.contentWindow.document.body.innerHTML;  
        this._adjust_h = div.offsetHeight  
        if (this._adjust_h < this._min_h) {  
            this._adjust_h = this._min_h;  
        }  
        t.setHeight(this._adjust_h);  
    },  
    onEditorEvent : function(e) {  
        if (this.onSpecialKey) {  
            this.onSpecialKey(e);  
        }  
        return FloatHtmlEditor.superclass.onEditorEvent.apply(this, arguments)  
    },  
    getValue : function() {  
        var ret = FloatHtmlEditor.superclass.getValue.apply(this, arguments);  
        if (ret) {  
            // fix edit grid panel compare startvalue & nowvalue  
            // fix '\n' patch for webkit(chrome) when ENTER pressed  
            ret = ret.replace(/^(|<br>|\s)*|(|<br>|\s)*$/ig, '')  
                        ret = ret.replace(/<div>(.+?)<\/div>/ig,'<br>$1')  
        }  
        if (!ret) {  
            ret = '';  
        }  
        return ret;  
    },  
    markTeamMember : function() {  
        this.teamCmp = true  
    },  
    initComponent : function() {  
        this.valueEditToolbarId = Ext.id()  
        this.onBlur = Ext.form.Field.prototype.onBlur;  
        FloatHtmlEditor.superclass.initComponent.call(this);  
        this.addEvents('_specialkey')  
        this.on('afterrender', function() {  
                    this.getToolbar().hide();  
                }, this)  
        this.on('show', function() {  
                    this._adjust_h = 0;  
                    this._adjustHeight.defer(100, this);  
                    this.focus()  
                }, this)  
        this.on('initialize', function(ed) {  
            var toolbarWin = this.toolbarWin = new Ext.Window({  
                        html : '<div id="' + this.valueEditToolbarId  
                                + '" class=""></div>',  
                        height : 255,  
                        width : 50,  
                        closeAction : 'hide',  
                        resizable : false,  
                        iconCls : 'icon-toolbox'  
                    })  
            toolbarWin.show();  
            var xy = this.getHoverTarget().getEl().getXY();  
            toolbarWin.setPagePosition(xy[0] + this.getHoverTarget().getWidth()  
                            - toolbarWin.getWidth(), xy[1]);  
            var el = this.getToolbar().getEl();  
            var appentToEl = Ext.getDom(this.valueEditToolbarId);  
            appentToEl.className = el.dom.parentNode.className;  
            el.appendTo(appentToEl);  
            this.getToolbar().show();  
            var tbarRawRow = el.query('.x-toolbar-left-row')[0];  
            var btnNodes = Ext.Element.fly(tbarRawRow).query('.x-toolbar-cell');  
            Ext.each(btnNodes, function(btnNode) {  
                        var btnEl = Ext.Element.fly(btnNode);  
                        if (btnEl.query('.xtb-sep').length) {  
                            btnEl.remove();  
                        } else {  
                            var newRow = Ext.Element.fly(tbarRawRow)  
                                    .insertSibling({  
                                                tag : 'tr',  
                                                cls : 'x-toolbar-left-row'  
                                            });  
                            newRow.appendChild(btnNode);  
                            var nel = Ext.Element.fly(btnNode);  
                            nel.query('button')[0].setAttribute('unselectable',  
                                    'on')  
                            nel.on('click', function() {  
                                        this.fireEvent('styleBtnClick')  
                                    }, this)  
                        }  
                    }, this)  
            toolbarWin.hide();  
            if (!Ext.isIE) {  
                toolbarWin.on('move', function() {  
                            ed.markTeamMember()  
                        })  
            }  
            this.getEl().findParent("div", 2, true).query('.x-html-editor-tb')[0].style.display = 'none'  
            Ext.EventManager.on(ed.getWin(), 'blur', function() {  
                        if (this.adjustTimer) {  
                            clearInterval(this.adjustTimer);  
                            delete this.adjustTimer  
                        }  
                        if (this._measureDiv) {  
                            this._measureDiv.innerHTML = ''  
                        }  
                        this._adjust_h = 0;  
            (function   () {  
                            var teamCmp = ed.teamCmp;  
                            teamCmp ? delete ed.teamCmp : ed.onBlur.apply(ed,  
                                    arguments)  
                        }).defer(10, ed, arguments);  
                    }, ed);  
            ed.getWin().document.body.style.overflow = 'hidden'  
            ed.getWin().document.body.style.whiteSpace = 'normal';  
            ed.getWin().document.body.style.wordWrap = 'break-word';  
            ed.getWin().document.body.style.lineHeight = '15px';  
            Ext.EventManager.on(ed.getWin(), 'focus', function() {  
                        if (this.teamCmp)  
                            delete this.teamCmp  
                    }.dg(this), ed);  
            var win = Ext.isIE ? ed.getDoc() : ed.getWin()  
            Ext.EventManager.on(Ext.isIE ? ed.getDoc().documentElement : ed  
                            .getWin(), 'paste', function(e) {  
                        var html = ed.getDoc().body.innerHTML;  
                        if (html.length >= 4  
                                && html.substring(html.length - 4) == '<br>') {  
                            ed._beforePasteHTMLPos = html.length - 4;  
                        } else {  
                            ed._beforePasteHTMLPos = html.length  
                        }  
                    })  
            // fix chrome keydown problem  
            Ext.EventManager.on(!Ext.isWebKit ? win : win.document.body,  
                    'keydown', function(e) {  
                        if (!this.adjustTimer) {  
                            this.adjustTimer = setInterval(function() {  
                                        this._adjustHeight();  
                                    }.dg(this), 200);  
                        }  
                        if (e.isSpecialKey())  
                            this.fireEvent('_specialkey', this, e)  
                    }, ed);  
            // fix IE first range select  
            this.moveCursorToEnd()  
        }, this)  
    },  
    // public >>>  
    getHoverTarget : Ext.emptyFn,  
    toolbarWin : null,  
    valueEditToolbarId : null,  
    // public <<<  
    _adjust_h : 0,  
    _min_h : 34,  
    setSize : function(w, h) {  
        if (typeof w == 'object') {  
            h = w.height  
            w = w.width  
        }  
        if (this._adjust_h == 0)  
            this._min_h = h - 4;  
        FloatHtmlEditor.superclass.setSize.apply(this, arguments);  
        var t = Ext.Element.fly(this.getEl().parent().query('iframe')[0]);  
        t.setHeight(this._adjust_h ? this._adjust_h : this._min_h);  
    },  
    focus : function(st, delay) {  
        var t = this.getWin();  
        if (this._focus_delay)  
            clearTimeout(this._focus_delay)  
        this._focus_delay = window.setTimeout(function() {  
                    t.focus();  
                }, 10);  
    },  
    moveCursorToEnd : function() {  
        this.focusPatch()  
    },  
    focusPatch : function() {  
        var ed = this;  
        if (!ed.win)  
            return  
        var doc = ed.getDoc();  
        ed.win.focus();  
        if (Ext.isIE) {  
            var r = doc.selection.createRange();  
            if (r) {  
                r.moveStart('character', 1000);  
                r.collapse(true);  
                r.select();  
            }  
        } else {  
            var contentDoc = doc;  
            var range = contentDoc.createRange();  
            var lastNode = contentDoc.body.childNodes[contentDoc.body.childNodes.length  
                    - 1];  
            var end = 0;  
            var selection = ed.getWin().getSelection();  
            range.setStart(contentDoc.body.firstChild, 0);  
            if (lastNode.nodeType == 3) {  
                end = lastNode.textContent.length;  
                range.setEnd(lastNode, end);  
            } else {  
                range.setEndAfter(lastNode)  
            }  
            selection.removeAllRanges();  
            // chorme need the range collapsed before add to selection  
            range.collapse(false);  
            selection.addRange(range);  
        }  
    },  
    showTBWin : function() {  
        var tbwin = this.toolbarWin  
        if (tbwin) {  
            var a = tbwin.toFront  
            tbwin.toFront = function() {  
            }  
            tbwin.show()  
            tbwin.toFront = a  
        }  
    },  
    // clean all tags  
    syncValue : function() {  
        if (this.initialized) {  
            var bd = this.getEditorBody();  
            var len = bd.innerHTML.length;  
            FloatHtmlEditor.superclass.syncValue.call(this);  
            if (this._beforePasteHTMLPos != null) {  
                this.syncValue1();  
                var pastehtml = bd.innerHTML  
                // .substring(this._beforePasteHTMLPos);  
                // replace all tags,only plain text from clipboard  
                var adjustpastehtml = pastehtml  
                        // trim spaces between tags  
                        // replace \n for excel paste  
                        .replace(/\s+/ig, '')  
                        // replace tag except for <br>  
                        .replace(/<(?!br).*?>/ig, '')  
                if (pastehtml != adjustpastehtml) {  
                    bd.innerHTML = adjustpastehtml;  
                }  
                delete this._beforePasteHTMLPos;  
            }  
        }  
    },  
    destroy : function() {  
        if (this._measureDiv) {  
            this._measureDiv.parentNode.removeChild(this._measureDiv);  
            delete this._measureDiv  
        }  
        if (this.adjustTimer) {  
            clearInterval(this.adjustTimer)  
            delete this.adjustTimer  
        }  
        FloatHtmlEditor.superclass.destroy.call(this)  
    },  
    enableAlignments : false,  
    enableFont : false,  
    enableLinks : false,  
    enableSourceEdit : true,  
    // 清理excel,word copy paste的内容,code from HtmlLintEditor  
    dirtyHtmlTags : [  
            // http://stackoverflow.com/questions/2875027/clean-microsoft-word-pasted-text-using-javascript  
            // http://stackoverflow.com/questions/1068280/javascript-regex-multiline-flag-doesnt-work  
            {  
        regex : /<!--[\s\S]*?-->/gi,  
        replaceVal : ""  
    },  
            // http://www.1stclassmedia.co.uk/developers/clean-ms-word-formatting.php  
            {  
                regex : /<\\?\?xml[^>]*>/gi,  
                replaceVal : ""  
            }, {  
                regex : /<\/?\w+:[^>]*>/gi,  
                replaceVal : ""  
            }, // e.g. <o:p...  
            {  
                regex : /\s*MSO[-:][^;"']*/gi,  
                replaceVal : ""  
            }, {  
                regex : /\s*MARGIN[-:][^;"']*/gi,  
                replaceVal : ""  
            }, {  
                regex : /\s*PAGE[-:][^;"']*/gi,  
                replaceVal : ""  
            }, {  
                regex : /\s*TAB[-:][^;"']*/gi,  
                replaceVal : ""  
            }, {  
                regex : /\s*LINE[-:][^;"']*/gi,  
                replaceVal : ""  
            }, {  
                regex : /\s*FONT-SIZE[^;"']*/gi,  
                replaceVal : ""  
            }, {  
                regex : /\s*LANG=(["'])[^"']*?\1/gi,  
                replaceVal : ""  
            },  
            // keep new line  
            {  
                regex : /<(P)[^>]*>([\s\S]*?)<\/\1>/gi,  
                replaceVal : "$2<br>"  
            }, {  
                regex : /<(H\d)[^>]*>([\s\S]*?)<\/\1>/gi,  
                replaceVal : "$2"  
            },  
            {  
                regex : /\s*\w+=(["'])((|\s|;)*|\s*;+[^"']*?|[^"']*?;{2,})\1/gi,  
                replaceVal : ""  
            }, {  
                regex : /<span[^>]*>(|\s)*<\/span>/gi,  
                replaceVal : ""  
            },  
            // {regex: /<([^\s>]+)[^>]*>(|\s)*<\/\1>/gi, replaceVal: ""},  
            // http://www.codinghorror.com/blog/2006/01/cleaning-words-nasty-html.html  
            {  
                regex : /<(\/?title|\/?meta|\/?style|\/?st\d|\/?head|\/?html|\/?body|\/?link|!\[)[^>]*?>/gi,  
                replaceVal : ""  
            }, {  
                regex : /(\n(\r)?){2,}/gi,  
                replaceVal : ""  
            }],  
    syncValue1 : function() {  
        if (this.initialized) {  
            var bd = this.getEditorBody();  
            var html = bd.innerHTML;  
            if (this.hasDirtyHtmlTags(html)) {  
                // Note: the selection will be lost...  
                bd.innerHTML = this.cleanHtml(html);  
                if (Ext.isGecko) {  
                    // Gecko hack, see:  
                    // https://bugzilla.mozilla.org/show_bug.cgi?id=232791#c8  
                    this.setDesignMode(false); // toggle off first  
                    this.setDesignMode(true);  
                }  
            }  
        }  
    },  
    hasDirtyHtmlTags : function(html) {  
        if (!html)  
            return;  
        var hasDirtyHtmlTags = false;  
        Ext.each(this.dirtyHtmlTags, function(tag, idx) {  
                    return !(hasDirtyHtmlTags = html.match(tag.regex));  
                });  
        return hasDirtyHtmlTags;  
    },  
    cleanHtml : function(html) {  
        if (!html)  
            return;  
        Ext.each(this.dirtyHtmlTags, function(tag, idx) {  
                    html = html.replace(tag.regex, tag.replaceVal);  
                });  
        // http://www.tim-jarrett.com/labs_javascript_scrub_word.php  
        html = html.replace(new RegExp(String.fromCharCode(8220), 'gi'), '"'); // “  
        html = html.replace(new RegExp(String.fromCharCode(8221), 'gi'), '"'); // ”  
        html = html.replace(new RegExp(String.fromCharCode(8216), 'gi'), "'"); // ‘  
        html = html.replace(new RegExp(String.fromCharCode(8217), 'gi'), "'"); // ‘  
        html = html.replace(new RegExp(String.fromCharCode(8211), 'gi'), "-"); // –  
        html = html.replace(new RegExp(String.fromCharCode(8212), 'gi'), "--"); // —  
        html = html.replace(new RegExp(String.fromCharCode(189), 'gi'), "1/2"); // ½  
        html = html.replace(new RegExp(String.fromCharCode(188), 'gi'), "1/4"); // ¼  
        html = html.replace(new RegExp(String.fromCharCode(190), 'gi'), "3/4"); // ¾  
        html = html.replace(new RegExp(String.fromCharCode(169), 'gi'), "(C)"); // ©  
        html = html.replace(new RegExp(String.fromCharCode(174), 'gi'), "(R)"); // ®  
        html = html.replace(new RegExp(String.fromCharCode(8230), 'gi'), "..."); // …  
        return FloatHtmlEditor.superclass.cleanHtml.call(this, html);  
    }  
});
Ext.reg('FloatHtmlEditor', FloatHtmlEditor);