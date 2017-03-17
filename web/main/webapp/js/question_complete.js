define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var QuestionCompltete = {
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
			$("#i_commit").off("click", this._commit).on("click", {ctx: this}, this._commit);
		},
		_commit:function(e){
			window.location = "success.html";
		}
    };
    return QuestionCompltete;
});