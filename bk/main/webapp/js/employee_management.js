define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var UserManagement = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 3);
            header.initialize("i_header", "员工管理");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            var params={};
            ajaxHelper.get("http://" + window.frontJSHost + "/api/ossuser/roles",
                params, this, this._getOssUsers, null);
        },
        _getOssUsers:function(roles){
            var params = {
                "page": 0,
                "size":20
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/ossuser/list",
                params, this, function(result){
                    this._render({"roles": roles, "result":result});
                }, null);
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun(data));
            this._registEvent();
        },
        _registEvent: function () {
            $(".j_selAuth li").off("click",this._selAuth).on("click",{ctx: this},this._selAuth);
            $(".j_status li").off("click",this._status).on("click",{ctx: this},this._status);
            $('#i_new').off("click", this._createEmployee).on("click", this._createEmployee);
        },
        _selAuth:function(e){
            var value = $(this).data("value");
            var userId = $($(this).parents("tr")).data("id");
            var text = $(this).text();
            $(this).closest("ul").prev("button").data("value", value).find("span").eq(0).text(text);
            
            var params={
                id: userId,
                role: value,
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/ossuser/urole",
                params, this, function(){
                    util.showToast("更新成功");
                });

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
            ajaxHelper.get("http://" + window.frontJSHost + "/api/ossuser/ustatus",
                params, this, function(){
                    util.showToast("更新成功");
                });

        },
        _createEmployee:function(e){
            window.location = "new_employee.html";
        }

    };
    return UserManagement;
});
