"use strict";

define(['component/header', 'ajaxhelper', 'utility'],function(header, ajaxHelper, util){
	var InvestResult = {
		starLength: 15,
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
			$("#i_start_click").off("click", this._setScore).on("click", {ctx: this}, this._setScore);
			$("#i_score_submit").off("click", this._doScore).on("click", {ctx: this}, this._doScore);
		},
		_setScore:function(e){
			var me = e.data.ctx;
			$("#i_star_over").width(Math.ceil(e.offsetX/me.starLength)*me.starLength);
		},
		_doScore:function(e){
			var score = $("#i_star_over").width()/e.data.ctx.starLength;
			if(score<1){
				util.showToast("至少评价1星");
				return;
			}
			var params = {
				"replyId": $("#i_star_over").data("replyid"),
				"score":score
			};
			ajaxHelper.get("http://" + window.frontJSHost + "/order/scorereply",
                params, e.data.ctx, function(){
                	util.showToast("评分成功！", function(){
                		e.data.ctx._sendRequest()
                	});
                });
		}
	};
	return InvestResult;
});