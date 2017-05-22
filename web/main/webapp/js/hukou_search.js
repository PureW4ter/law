define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var HukouSearch = {
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
			$("#i_pay").off("click", this._pay).on("click", {ctx: this}, this._pay);
		},
		_pay:function(e){
			var params = {
				"userId": util.getUserId(),
				"productId": $("#i_product").data("id"),
				"ownerName": $("#i_name").val(),
				"cityId": $("#i_city").val(),
				"address": $("#i_addr").val(),
				"email": $("#i_email").val(),
			}
			ajaxHelper.post("http://" + window.frontJSHost + "/order/icreate",
                params, this, function(){
                	window.location = "success.html";
                }, null);
		}
    };
    return HukouSearch;
});