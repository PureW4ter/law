define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var UserManagement = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 1);
            header.initialize("i_header", "用户管理");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            var params = {
                "page": 0,
                "size":20
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/user/list",
                params, this, this._render, null);
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun({"result":data}));
            this._registEvent();
        },
        _registEvent: function () {
            $("#i_user_filter li").off("click", this._status).on("click",{ctx: this}, this._status);
            $("#i_search").off("click", this._doSearch).on("click",{ctx: this}, this._doSearch);
        },
        _status:function(e){
            var value = $(this).data("value");
            var text = $(this).text();
            var userId = $($(this).parents("tr")).data("id");
            $(this).closest("ul").prev("button").data("value", value).find("span").eq(0).text(text);
        },
        _doSearch:function(e){
            var params={
                "value": $("#i_search_value").data("value"),
                "page": 0,
                "size":20
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/user/list",
                params, e.data.ctx, e.data.ctx._render);
        }
    };
    return UserManagement;
});
