define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var LawyerManagement = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 4);
            header.initialize("i_header", "律师主页");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            var params = {
                "page": 0,
                "size":30
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/lawyer/self",
                params, this, this._render, null);
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun({"result": data}));
            this._registEvent();
        },
        _registEvent: function () {
            $(".j_tasks li").off("click",this._tasks).on("click",{ctx: this},this._tasks);
        },
        _tasks:function(e){
            var value = $(this).data("value");
            var text = $(this).text();
            $(this).closest("ul").prev("button").data("value", value).find("span").eq(0).text(text);
        }

    };
    return LawyerManagement;
});
