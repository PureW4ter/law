define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var UserManagement = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 1);
            header.initialize("i_header", "用户详细信息");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            var params = {
                id: util.getQueryParameter("id")
            }
            ajaxHelper.get("http://" + window.frontJSHost + "/api/user/detail",
                params, this, this._render, null);
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun({"result":data}));
            this._registEvent();
        },
        _registEvent: function () {
            $('#i_save').off("click", this._doSave).on("click", this._doSave);
            $('#i_cancel').off("click", this._doCancel).on("click", this._doCancel);
        },
        _doSave:function(e){
            var params = {
                id: util.getQueryParameter("id"),
                memo: $("#i_memo").val()
            }
            ajaxHelper.post("http://" + window.frontJSHost + "/api/user/memo",
                params, this, function(){
                    util.showToast("更新成功", function(){
                        window.location = "user_management.html";
                    });
                });
        },
        _doCancel:function(e){
            window.location = "user_management.html";
        }
    };
    return UserManagement;
});
