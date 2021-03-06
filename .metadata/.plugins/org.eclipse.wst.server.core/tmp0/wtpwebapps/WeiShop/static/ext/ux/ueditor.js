Ext.ns('Ext.ux.form');
Ext.ux.form.Ueditor = Ext.extend(Ext.form.Field, {
    initComponent : function(){
        Ext.ux.form.Ueditor.superclass.initComponent.call(this);
        var me = this;
        me.addEvents('initialize', 'change'); // 为ueditor添加一个初始化完成的事件
        var id = me.id + '-ueditor';
        //me.html = '<script id="' + id + '" type="text/plain" name="' + me.name + '"></script>';
       // me.callParent(arguments);
        //me.initField();
        me.on('render', function () {
            var width = me.width - 105;
            var height = me.height - 109;
            debugger
            var config = {initialFrameWidth: "400", initialFrameHeight: "400"};
            me.ueditorInstance = UE.getEditor(id, config);
            me.ueditorInstance.ready(function () {
                me.initialized = true;
                me.fireEvent('initialize', me);
                me.ueditorInstance.addListener('contentChange', function () {
                    me.fireEvent('change', me);
                });
            });
        });
    },
    alias: 'widget.ueditor',
    alternateClassName: 'Ext.form.UEditor',
    ueditorInstance: null,
    initialized: false,
    getValue: function () {
        var me = this,
            value = '';
        if (me.initialized) {
            value = me.ueditorInstance.getContent();
        }
        me.value = value;
        return value;
    },
    setValue: function (value) {
        var me = this;
        if (value === null || value === undefined) {
            value = '';
        }
        if (me.initialized) {
            me.ueditorInstance.setContent(value);
        }
        return me;
    },
    onDestroy: function () {
        this.ueditorInstance.destroy();
    }
});
Ext.reg('ueditor', Ext.ux.form.Ueditor);