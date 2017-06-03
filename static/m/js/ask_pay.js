define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var AskPay = {
    	propertis: null,
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
			$("#i_question_types li").off("click", this._chooseType).on("click", {ctx: this}, this._chooseType);
			$("#i_identity").off("change", this._changeIdentity).on("change", {ctx: this}, this._changeIdentity);
			$("#i_trade_phase").off("change", this._changePhase).on("change", {ctx: this}, this._changePhase);
			$("#i_pay_way li").off("click", this._selectPayWay).on("click", {ctx: this}, this._selectPayWay);
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

			$("#i_question_types").find("option").remove();
			e.data.ctx.propertis.r[2][index].phases[0].subPhases.forEach(
				function(item, index){
					$("#i_question_types").append("<option>"+ item.phase + "</option>"); 
				}
			);
			e.data.ctx._registEvent();
		},
		_changePhase:function(e){
			var index = $(e.target).find("option").
						not(function(){ return !this.selected }).val();
			$("#i_question_types").find("option").remove();
			e.data.ctx.propertis.r[2][e.data.ctx.idIndex].phases[index].subPhases.forEach(
				function(item, index){
					$("#i_question_types").append("<option>"+ item.phase + "</option>"); 
				}
			);
			e.data.ctx._registEvent();
		},
		_pay:function(e){
			var params = {
				"userId": util.getUserId(),
				"productId": $($(".question_pay_selected")[0]).data("id"),
				"role": $("#i_identity option").not(function(){ return !this.selected }).text(),
				"tradePhase": $("#i_trade_phase option").not(function(){ return !this.selected }).text(),
				"tradeSubphase": $("#i_question_types option").not(function(){ return !this.selected }).text(),
				"sn": $("#i_sign option").not(function(){ return !this.selected }).text()
			}
			ajaxHelper.post("http://" + window.frontJSHost + "/order/screate",
                params, this, function(data){
                	var ps = {
                		"id": data.r.id
                	}
                	ajaxHelper.get("http://" + window.frontJSHost + "/order/pay",  ps, 
                		this, function(){
                			util.weixinPay(data.r, "question_complete.html");
                		});
                });
		},
		_selectPayWay:function(e){
			$(".j_tab").removeClass("question_pay_selected");
			$(e.currentTarget).addClass("question_pay_selected");
		},
		_chooseType:function(e){
			$(e.currentTarget).addClass("subPhase_selected");
		}
    };
    return AskPay;
});