define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var LawyerConsultInfo = {
    	myScroll:"",
        initialize :function(){
        	$("#i_detail2").hide();
			//request
			this._sendRequest();
		},
		_sendRequest :function(){
			this._render();
		},
		_render:function(){
			this.myScroll=new iScroll("i_detail_box",{
	                useTransition : true,
	                topOffset : 0,
	                hScroll: false
	        });
			this._registEvent();
		},
		_registEvent:function(){
			$(".j_tab").off("click", this._doselect).on("click", {ctx: this}, this._doselect);
			$("#i_ok_btn").off("click", this._go).on("click", {ctx: this}, this._go);
		},
		_doselect:function(e){
			$(".j_tab").removeClass("product_info_selected").addClass("product_info_onselected");
			$(e.currentTarget).removeClass("product_info_onselected").addClass("product_info_selected");
			if($(e.currentTarget).data("type") == 1){
				$("#i_detail1").show();
				$("#i_detail2").hide();
			}else{
				$("#i_detail1").hide();
				$("#i_detail2").show();
			}
			e.data.ctx.myScroll.refresh();
			e.data.ctx.myScroll.scrollTo(0, 0);
		},
		_go:function(e){
			if($($(".product_info_selected")[0]).data("type") == 1){
				window.location = "ask_pay.html";
			}else{
				window.location = "ask_free.html";
			}
		}
    };
    return LawyerConsultInfo;
});