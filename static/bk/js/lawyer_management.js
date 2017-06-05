define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var LawyerManagement = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 4);
            header.initialize("i_header", "律师管理");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            var params = {
                "page": 0,
                "size":20
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/lawyer/list",
                params, this, this._render, null);
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun({"result": data}));
            this._registEvent();
        },
        _registEvent: function () {
            $(".j_status li").off("click",this._status).on("click",{ctx: this},this._status);
            $('#i_new').off("click", this._createLawyer).on("click", this._createLawyer);
        },
        _status:function(e){
            var value = $(this).data("value");
            var text = $(this).text();
            var userId = $($(this).parents("tr")).data("id");
            $(this).closest("ul").prev("button").data("value", value).find("span").eq(0).text(text);
            
            var params={
                id: userId,
                status: value,
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/lawyer/ustatus",
                params, this, function(){
                    util.showToast("更新成功");
                });

        },
        _createLawyer:function(e){
            window.location = "new_lawyer.html";
        }

    };
    return LawyerManagement;
});
