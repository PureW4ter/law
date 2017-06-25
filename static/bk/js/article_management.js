define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var UserManagement = {
        currentPage: 0,
        size: 30,
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 2);
            header.initialize("i_header", "文章管理");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            var params = {"page": this.currentPage, "size": this.size};
            ajaxHelper.get("http://" + window.frontJSHost + "/api/article/list",
                params, this, this._render, null);
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun({'result': data}));
            this._registEvent();
        },
        _registEvent: function () {
            $('#i_new').off("click", this._createArticle).on("click", this._createArticle);
            $('.j_delete').off("click", this._doDelete).on("click", this._doDelete);
        },
        _createArticle:function(e){
            window.location = "new_article.html";
        },
        _doDelete:function(e){
            var params = {
                id: $(e.currentTarget).data("id")
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/article/delete",
                params, this, function(){
                    util.showToast("删除成功", function(){
                        window.refresh();
                    })
                }, null);
        },
    };
    return UserManagement;
});
