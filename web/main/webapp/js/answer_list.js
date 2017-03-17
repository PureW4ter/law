define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var AnswerList = {
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
			$("#i_one").off("click", this._go).on("click", {ctx: this}, this._go);
		},
		_go:function(e){
			window.location = "";
		}
    };
    return AnswerList;
});