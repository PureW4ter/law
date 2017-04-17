define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var UserManagement = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 2);
            header.initialize("i_header", "录入文章");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            /*var params = {
                type:2
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/user/list",
                params, this, this._render, null);*/
            this._render();
        },
        _xheditor: function(){
            var url="http://upload.do";
            this.editor = $('#elm1').xheditor({width:'100%',height:1000, html5Upload:true, upImgUrl:url, upImgExt:"jpg,jpeg,gif,png"});
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun());
            this._registEvent();
            this._xheditor();
        },
        _registEvent: function () {
            
        },

    };
    return UserManagement;
});
