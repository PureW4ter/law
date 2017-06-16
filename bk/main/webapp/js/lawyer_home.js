define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var LawyerHome = {
        currentPage: 0,
        size: 30,
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 7);
            header.initialize("i_header", "律师主页");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            var userData= $.parseJSON(localStorage.userInfo);
            var params = {
                "lawyerId": userData.id,
                "page": this.currentPage, 
                "size": this.size
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/order/lawyer",
                params, this, this._render, null);
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun({'result': data}));
            this._registEvent();
        },
        _registEvent: function () {
            $('#i_new').off("click", this._createArticle).on("click", this._createArticle);
        },
        _createArticle:function(e){
            window.location = "new_article.html";
        }
    };
    return LawyerHome;
});
