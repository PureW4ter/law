define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var MyQuestionList = {
        initialize :function(){
			//request
			this._sendRequest();
		},
		_sendRequest :function(){
			this._render();
		},
		_render:function(){
			this._registEvent();
		},
		_registEvent:function(){
			$("#i_pay").off("click", this._pay).on("click", {ctx: this}, this._pay);
			$("#i_complete").off("click", this._complete).on("click", {ctx: this}, this._complete);
			$("#i_view").off("click", this._view).on("click", {ctx: this}, this._view);
			$("#i_view_hukou").off("click", this._viewHukou).on("click", {ctx: this}, this._viewHukou);
			$("#i_view_ceil").off("click", this._viewCeil).on("click", {ctx: this}, this._viewCeil);
		},
		_pay:function(e){
			window.location = "nopay.html";
		},
		_complete:function(e){
			window.location = "question_complete.html";
		},
		_view:function(e){
			window.location = "lawyer_reply.html";
		},
		_viewHukou:function(e){
			window.location = "invest_result_hukou.html";
		},
		_viewCeil:function(e){
			window.location = "invest_result_ceil.html";
		}
    };
    return MyQuestionList;
});