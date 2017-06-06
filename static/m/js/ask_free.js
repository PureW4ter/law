define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var AskFree = {
        ipropertis: null,
    	idIndex: 0,
        initialize :function(){
        	//body
			this.mainBox = $('#i_mainbox');
			this.tplfun = _.template($("#i_tpl").html());
			//request
			this._sendRequest();
		},
		_sendRequest :function(){
			var params = {};
			ajaxHelper.get("http://" + window.frontJSHost + "/ask/props",
                params, this, this._render, null);
		},
		_render:function(data){
			this.propertis = data;
			this.mainBox.html(this.tplfun({"result": data}));
			this._registEvent();
		},
		_registEvent:function(){
			$("#i_pay").off("click", this._pay).on("click", {ctx: this}, this._pay);
			$("#i_identity").off("change", this._changeIdentity).on("change", {ctx: this}, this._changeIdentity);
		},
		_changeIdentity:function(e){
			var index = $(e.target).find("option").
						not(function(){ return !this.selected }).val();
			e.data.ctx.idIndex = index;
			$("#i_trade_phase").find("option").remove();
			e.data.ctx.propertis.r[2][index].phases.forEach(
				function(item, index){
					$("#i_trade_phase").append("<option value='"+ index + "'>"+ item.phase + "</option>"); 
				}
			);
			e.data.ctx._registEvent();
		},
		_pay:function(e){
			if(!e.data.ctx.validate()){
                return;
            };
			var params = {
				"userId": util.getUserId(),
				"productId": $("#i_product").data("id"),
				"role": $("#i_identity option").not(function(){ return !this.selected }).text(),
				"tradePhase": $("#i_trade_phase option").not(function(){ return !this.selected }).text(),
				"memo": $("#i_memo").val()
			}
			ajaxHelper.post("http://" + window.frontJSHost + "/order/screate",
                params, this, function(data){
                	var ps = {
                		"id": data.r.id
                	}
                	ajaxHelper.get("http://" + window.frontJSHost + "/order/pay",  ps, 
                		this, function(data){
                			util.weixinPay(data.r, "success.html");
                		});
                });
		},
		validate:function(){
            var pass = true;
            var memo = $("#i_memo").val();
            if(!memo){
                util.showToast("交易情况，诉求不能为空");
                pass = false;
                return pass;
            }
            if(memo.length>500){
            	util.showToast("最多不可以超过500字");
                pass = false;
                return pass;
            }
            return pass;
        }
    };
    return AskFree;
});