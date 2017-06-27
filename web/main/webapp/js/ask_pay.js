define(['component/header','ajaxhelper', 'utility', 'validate'], 
	function(header, ajaxHelper, util, validate) {
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
			$($(".j_sign_desc")[1]).hide();
			this._registEvent();
		},
		_registEvent:function(){
			$("#i_help").off("click", this._doCall).on("click", {ctx: this}, this._doCall);
			$("#i_pay").off("click", this._pay).on("click", {ctx: this}, this._pay);
			$("#i_question_types li").off("click", this._chooseType).on("click", {ctx: this}, this._chooseType);
			$("#i_identity").off("change", this._changeIdentity).on("change", {ctx: this}, this._changeIdentity);
			$("#i_trade_phase").off("change", this._changePhase).on("change", {ctx: this}, this._changePhase);
			$("#i_sign").off("change", this._changeSignDesc).on("change", {ctx: this}, this._changeSignDesc);
			$("#i_pay_way li").off("click", this._selectPayWay).on("click", {ctx: this}, this._selectPayWay);
		},
		_doCall:function(){
			window.location="tel://" + util.phone;
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
		_changeSignDesc:function(e){
			var index = $(e.target).find("option").
						not(function(){ return !this.selected }).data("index");
			$(".j_sign_desc").hide();
			$($(".j_sign_desc")[index]).show();
		},
		_pay:function(e){
			if($('#i_phone_box').css("display") == "block" && !e.data.ctx.validate()){
                return;
            };
			var params = {
				"userId": util.getUserId(),
				"productId": $($(".question_pay_selected")[0]).data("id"),
				"role": $("#i_identity option").not(function(){ return !this.selected }).text(),
				"tradePhase": $("#i_trade_phase option").not(function(){ return !this.selected }).text(),
				"tradeSubphase": $("#i_question_types option").not(function(){ return !this.selected }).text(),
				"sn": $("#i_sign option").not(function(){ return !this.selected }).text(),
				"ownerPhone": $("#i_phone").val()
			}
			ajaxHelper.post("http://" + window.frontJSHost + "/order/screate",
                params, e.data.ctx, function(data){
                	e.data.ctx.oid = data.r.id;
                	var ps = {
                		"id": data.r.id
                	}
                	ajaxHelper.get("http://" + window.frontJSHost + "/order/pay",  ps, 
                		this, function(data){
                			util.weixinPay(data.r, "question_complete.html?id="+this.oid);
                		});
                });
		},
		_selectPayWay:function(e){
			$(".j_tab").removeClass("question_pay_selected");
			$(e.currentTarget).addClass("question_pay_selected");
			if($(e.currentTarget).data("show")==1){
				$("#i_phone_box").show();
			}else{
				$("#i_phone_box").hide();
			}
			
		},
		_chooseType:function(e){
			$(e.currentTarget).addClass("subPhase_selected");
		},
		validate:function(){
            var pass = true;
            var phone = $("#i_phone").val();
            if(!phone){
                util.showToast("联系电话不能为空");
                pass = false;
                return pass;
            }
            if(!validate.isMobile(phone)){
                util.showToast("请输入正确的手机号码");
                pass = false;
                return pass;
            }
            
            return pass;
        }
    };
    return AskPay;
});