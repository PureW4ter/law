define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var UserManagement = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 2);
            header.initialize("i_header", "用户管理");
            //报备信息
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());

            this._sendRequest();
        },
        _sendRequest: function () {
            var params = {
                type:2
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/user/list",
                params, this, this._render, null);
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun({"result": data}));
            this._registEvent();
        },
        _registEvent: function () {
            $(".j_user_disable").off("click", this._disable).on("click", {ctx: this}, this._disable);
            $(".j_user_enable").off("click", this._enable).on("click", {ctx: this}, this._enable);
        },
        _disable:function(e){
            var id = $(e.currentTarget).parents("tr").data("id");
            var params = {
                "id":id
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/user/disable",
                params, e.data.ctx, function(){
                    util.showToast("更新成功", this._sendRequest());
                }, null);
        },
        _enable:function(e){
            var id = $(e.currentTarget).parents("tr").data("id");
            var params = {
                "id":id
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/user/enable",
                params, e.data.ctx, function(){
                    util.showToast("更新成功", this._sendRequest());
                }, null);
        }

    };
    return UserManagement;
});
