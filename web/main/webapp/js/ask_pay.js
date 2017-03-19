define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var AskPay = {
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
		},
		_pay:function(e){
			window.location = "question_complete.html";
		}
    };
    return AskPay;
});