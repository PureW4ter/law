define(['component/header', 'ajaxhelper', 'utility','lib/MD5'], function (header, ajaxHelper, util, md5) {
    var Reset = {
        initialize: function () {
            //body
            this.mainBox = $('#i_mainbox');
            this.tplfun = _.template($("#i_tpl").html());
            //request
            this._sendRequest();
        },
        _sendRequest: function (isPaging) {
            this._render();
        },
        _render: function (data) {
            this.mainBox.html(this.tplfun());
            this._registEvent();
        },
        _registEvent: function () {
            $("[data-toggle='popover']").bind({'focus': p_destroy, 'blur': input_check});
            $('.submit').off('click', this.subIt).on('click', {ctx: this}, this.subIt);
        },
        subIt: function (e) {
            var params = util.getFormValues('loginIt');
            params.id = util.getUserId();
            $('input').blur();
            if ($('.popover.in').length < 1) {
                ajaxHelper.get("http://" + window.frontJSHost + "/api/ossuser/restpwd", 
                    params, e.data.ctx, e.data.ctx.success, null);
            } 
        },
        bindEnter: function (e) {
            if (e.keyCode == 13){
                e.preventDefault();
                e.data.ctx.subIt(e);
            }
        },
        success: function (data) {
            window.location = "login.html";
        }
    };
    return Reset;
});