define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var AlbumManagement = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 1);
            header.initialize("i_header", "相册管理");
            //报备信息
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());

            this._sendRequest();
        },
        _sendRequest: function () {
            var params = {
                type:2
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/album/list",
                params, this, this._render, null);
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun({"result": data}));
            this._registEvent();
        },
        _registEvent: function () {
            $(".j_albumDetail").off("click", this._notPass).on("click", {ctx: this}, this._notPass);
        },
        _notPass:function(e){
            var id = $(e.currentTarget).parents("tr").data("id");
            var params = {
                "albumId":id
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/album/unpass",
                params, e.data.ctx, function(){
                    util.showToast("更新成功", this._sendRequest());
                }, null);
        }
    };
    return AlbumManagement;
});
