define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var LawyerConsultInfo = {
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
			$("#i_pay").off("click", this._goPay).on("click", {ctx: this}, this._goPay);
			$("#i_free").off("click", this._goFree).on("click", {ctx: this}, this._goFree);
		},
		_goPay:function(e){
			window.location = "ask_pay.html";
		},
		_goFree:function(e){
			window.location = "ask_free.html";
		}
    };
    return LawyerConsultInfo;
});