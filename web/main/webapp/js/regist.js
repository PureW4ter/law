define(['ajaxhelper', 'utility', 'component/time_button'], function(ajaxHelper, util, timeBtn) {
    var Regist = {
        initialize: function() {
           timeBtn.initialize("i_getcode_btn");
           this._sendRequest();
        },
		_sendRequest :function(){
			this._render();
		},
		_render:function(){
			this._registEvent();
		},
		_registEvent:function(){
			$("#i_regist_btn").off("click", this._go).on("click", {ctx: this}, this._go);
		},
		_go:function(e){
			window.location = "my_question_list.html";
		} 
    };
    return Regist;
});