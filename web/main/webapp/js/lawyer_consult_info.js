define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var LawyerConsultInfo = {
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
			$("#i_under1").off("click", this._under1).on("click", {ctx: this}, this._under1);
			$("#i_go1").off("click", this._go1).on("click", {ctx: this}, this._go1);
			$("#i_under2").off("click", this._under2).on("click", {ctx: this}, this._under2);
			$("#i_go2").off("click", this._go2).on("click", {ctx: this}, this._go2);
			$("#i_all_mask").off("click", this._hide).on("click", {ctx: this}, this._hide);
		},
		_doCall:function(){
			window.location="tel://" + util.phone;
		},
		_under1:function(){
			$("#i_all_mask").show();
			$("#i_all_mask img").attr("src", "img/zixun/p1.jpg");
		},
		_go1:function(){
			window.location = "ask_pay.html";
		},
		_under2:function(){
			$("#i_all_mask").show();
			$("#i_all_mask img").attr("src", "img/zixun/p2.jpg");
		},
		_go2:function(){
			window.location = "ask_free.html";
		},
		_hide:function(){
			$("#i_all_mask").hide();
		}
    };
    return LawyerConsultInfo;
});