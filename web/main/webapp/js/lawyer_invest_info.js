define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var LawyerInvesttInfo = {
        initialize :function(){
			this._sendRequest();
		},
		_sendRequest :function(){
			this._render();
		},
		_render:function(){
			this._registEvent();
		},
		_registEvent:function(){
			$("#i_help").off("click", this._doCall).on("click", {ctx: this}, this._doCall);
			$("#i_go1").off("click", this._go1).on("click", {ctx: this}, this._go1);
			$("#i_go2").off("click", this._go2).on("click", {ctx: this}, this._go2);
		},
		_doCall:function(){
			window.location="tel://15800763802";
		},
		_go1:function(){
			window.location = "hukou_search.html";
		},
		_go2:function(){
			window.location = "sealup_search.html";
		},
    };
    return LawyerInvesttInfo;
});