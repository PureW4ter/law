define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var UserOrders = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 1);
            header.initialize("i_header", "用户订单");

            this.mainBox1 = $('#i_mainbox1');
            this.tplFun1 = _.template($("#i_tpl1").html());

            this.mainBox2 = $('#i_mainbox2');
            this.tplFun2 = _.template($("#i_tpl2").html());

            this.modalBox = $('#i_modal_body');
            this.modalTplFun = _.template($("#i_modal_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            var params = {
                "page": 0,
                "size":20,
                "userId": util.getQueryParameter("id")
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/order/ilistbyuser",
                params, this, this._render1, null);

            ajaxHelper.get("http://" + window.frontJSHost + "/api/order/slistbyuser",
                params, this, this._render2, null);
        },
        _render1: function (data) {
            this.mainBox1.html(this.tplFun1({"result":data}));
            this._registEvent();
        },
        _render2: function (data) {
            this.mainBox2.html(this.tplFun2({"result":data}));
            this._registEvent();
        },
        _registEvent: function () {
            $('.j_assign').off("click", this._assign).on("click", {"ctx":this}, this._assign);
            $('.j_do_assign').off("click", this._doAssign).on("click", {"ctx":this}, this._doAssign);
        },
        _assign:function(e){
            var params = {
                cityId: $(e.currentTarget).data("city")
            };
            var oid = $(e.currentTarget).parents("tr").data("id");
            ajaxHelper.get("http://" + window.frontJSHost + "/api/lawyer/listbycity",
                params, e.data.ctx, function(data){
                    $("#i_modal").data({"id":oid}).modal("show");
                    this.modalBox.html(this.modalTplFun({"result":data}));
                    this._registEvent();
                }, null);
        },
        _doAssign:function(e){
            var oid = $("#i_modal").data("id");
            var lawyerId = $(e.currentTarget).parents("tr").data("id");
            var params = {
                opId : util.getUserId(),
                lawyerId : lawyerId,
                orderId : oid
            };
            $("#i_modal").modal("hide");
            ajaxHelper.get("http://" + window.frontJSHost + "/api/order/assignment",
                params, e.data.ctx, function(data){
                    util.showToast("指派成功");
                    this._sendRequest();
                }, null);
        }
    };
    return UserOrders;
});
