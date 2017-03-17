define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var Index = {
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
			$("#i_activities").off("click", this._goActivities).on("click", {ctx: this}, this._goActivities);
			$("#i_read").off("click", this._goRead).on("click", {ctx: this}, this._goRead);
			$("#i_consult").off("click", this._goConsult).on("click", {ctx: this}, this._goConsult);
			$("#i_invest").off("click", this._goInvest).on("click", {ctx: this}, this._goInvest);
			$("#i_ask").off("click", this._goAsk).on("click", {ctx: this}, this._goAsk);
			$("#i_my").off("click", this._goMy).on("click", {ctx: this}, this._goMy);
		},
		_goActivities:function(e){
			window.location = "";
		},
		_goRead:function(e){
			window.location = "read_keys.html";
		},
		_goConsult:function(e){
			window.location = "lawyer_consult_info.html";
		},
		_goInvest:function(e){
			window.location = "lawyer_invest_info.html";
		},
		_goAsk:function(e){
			window.location = "answer_list.html";
		},
		_goMy:function(e){
			window.location = "regist.html";
		},
    };
    return Index;
});