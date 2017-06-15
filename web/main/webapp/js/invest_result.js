"use strict";

define(['component/header', 'ajaxhelper', 'utility'],function(header, ajaxHelper, util){
	var InvestResult = {
		initialize :function(){
			//body
			this.mainBox = $('#i_mainbox');
			this.tplfun = _.template($("#i_tpl").html());
			//request
			this._sendRequest();
		},
		_sendRequest :function(){
			var params = {
				"id": util.getQueryParameter("id"),
			};
			ajaxHelper.get("http://" + window.frontJSHost + "/order/getreply",
                params, this, this._render);
		},
		_render:function(data){
			this.mainBox.html(this.tplfun({"result": data}));
			this._registEvent();
		},
		_registEvent:function(){
			
		}
	};
	return InvestResult;
});