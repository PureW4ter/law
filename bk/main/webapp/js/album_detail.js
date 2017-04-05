define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var AlbumDetail = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 1);
            header.initialize("i_header", "相册管理-相册详情");
            //报备信息
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());

            this._sendRequest();
        },
        _sendRequest: function () {
            var albumId = util.getQueryParameter("id");
            var params = {
                "id":albumId
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/album/detail",
                params, this, this._render, null);
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun({"result": data}));
            this._registEvent();
        },
        _registEvent: function () {
            $("#i_unpassBtn").off("click", this._notPass).on("click", {ctx: this}, this._notPass);
            $("#i_passBtn").off("click", this._pass).on("click", {ctx: this}, this._pass);
             $("#i_deleteBtn").off("click", this._delete).on("click", {ctx: this}, this._delete);
        },
        _notPass:function(e){
            var id = $(e.currentTarget).parents("h2").data("id");
            var params = {
                "albumId":id
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/album/unpass",
                params, e.data.ctx, function(){
                   util.showToast("设置不通过成功", function(){window.history.back()});
                }, null);
        },
        _pass:function(e){
            var id = $(e.currentTarget).parents("h2").data("id");
            var params = {
                "albumId":id
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/album/pass",
                params, e.data.ctx, function(){
                   util.showToast("设置通过成功", function(){window.history.back()});
                }, null);
        },
        _delete:function(e){
            var id = $(e.currentTarget).parents("h2").data("id");
            var params = {
                "albumId":id
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/album/delete",
                params, e.data.ctx, function(){
                   util.showToast("删除成功", function(){window.history.back()});
                }, null);
        }
    };
    return AlbumDetail;
});
