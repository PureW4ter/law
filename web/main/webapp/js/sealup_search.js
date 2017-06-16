define(['component/header','ajaxhelper', 'utility', 'validate'], 
	function(header, ajaxHelper, util, validate) {
    var SealupSearch = {
    	cities:util.cities,
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
			data.r.push(this.cities);
			this.mainBox.html(this.tplfun({"result": data}));
			this._registEvent();
		},
		_registEvent:function(){
            $("#i_help").off("click", this._doCall).on("click", {ctx: this}, this._doCall);
			$("#i_pay").off("click", this._pay).on("click", {ctx: this}, this._pay);
		},
        _doCall:function(){
            window.location="tel://" + util.phone;
        },
		_pay:function(e){
			if(!e.data.ctx.validate()){
                return;
            };
			var params = {
				"userId": util.getUserId(),
				"productId": $("#i_product").data("id"),
				"ownerName": $("#i_name").val(),
				"ownerPhone": $("#i_phone").val(),
				"cityId": $("#i_city").val(),
				"address": $("#i_addr").val(),
				"email": $("#i_email").val(),
			}
			ajaxHelper.post("http://" + window.frontJSHost + "/order/icreate",
                params, this, function(data){
                	var ps = {
                		"id": data.r.id
                	}
                	ajaxHelper.get("http://" + window.frontJSHost + "/order/pay",  ps, 
                		this, function(data){
                			util.weixinPay(data.r, "question_complete2.html");
                		});
                }, null);
		},
		validate:function(){
            var pass = true;
            var name = $("#i_name").val();
            if(!name){
                util.showToast("产权人不能为空");
                pass = false;
                return pass;
            }
            var address = $("#i_addr").val();
            if(!address){
                util.showToast("详细地址不能为空");
                pass = false;
                return pass;
            }
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
    return SealupSearch;
});