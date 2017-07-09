define(['component/header', 'ajaxhelper', 'utility','lib/MD5'], function (header, ajaxHelper, util, md5) {
    var Login = {
        initialize: function () {
            //body
            this.mainBox = $('#i_mainbox');
            this.logontplfun = _.template($("#i_login_tpl").html());
            //request
            this._sendRequest();
        },
        _sendRequest: function (isPaging) {
            this._render();
        },
        _render: function (data) {
            this.mainBox.html(this.logontplfun());
            this._registEvent();
        },
        _registEvent: function () {
            $("[data-toggle='popover']").bind({'focus': p_destroy, 'blur': input_check});
            $('.submit').off('click', this.subIt).on('click', {ctx: this}, this.subIt);
        },
        subIt: function (e) {
            var params = util.getFormValues('loginIt');
            $('input').blur();
            if ($('.popover.in').length < 1) {
                ajaxHelper.get("http://" + window.frontJSHost + "/api/user/login", 
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
            data.expires_time = new Date().getTime() + 3600 * 1000;
            window.localStorage.setItem('userInfo', JSON.stringify(data.r));
            if(util.isLawyer()){
                window.location = "lawyer_home.html";
            }else{
                window.location = "invest_orders.html";  
            }
            
        }
    };
    return Login;
});