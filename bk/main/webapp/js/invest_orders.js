define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var InvestOrders = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 5);
            header.initialize("i_header", "咨询订单管理");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());

            this.modalBox = $('#i_modal_body');
            this.modalTplFun = _.template($("#i_modal_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            var params = {
                "page": 0,
                "size":20
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/order/ilist",
                params, this, this._render, null);
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun({"result":data}));
            this._registEvent();
        },
        _registEvent: function () {
            $("#i_search").off("click", this._doSearch).on("click",{ctx: this}, this._doSearch);
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
        },
        _doSearch:function(e){
            var value = $("#i_order_search").val();
            var params = {
                
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/order/"+value,
                params, e.data.ctx, e.data.ctx._render, null);
        }
    };
    return InvestOrders;
});
