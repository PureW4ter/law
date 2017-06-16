define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var LawyerReply = {
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
			$("#i_view").off("click", this._view).on("click", {ctx: this}, this._view);
		},
		_view:function(e){
			window.location = "view_reply.html";
		},
		
    };
    return LawyerReply;
});