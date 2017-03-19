define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var LawyerInvesttInfo = {
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
			$("#i_hukou").off("click", this._goHukou).on("click", {ctx: this}, this._goHukou);
			$("#i_ceil").off("click", this._goCeil).on("click", {ctx: this}, this._goCeil);
		},
		_goHukou:function(e){
			window.location = "hukou_search.html";
		},
		_goCeil:function(e){
			window.location = "hukou_search.html";
		}
    };
    return LawyerInvesttInfo;
});