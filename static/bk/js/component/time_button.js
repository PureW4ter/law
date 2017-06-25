"use strict";

define(['text!./time_button.html'],function(tempHtml){
	var TimeButton = {
		intervalId: null,
		fun:null,
		count: 0,
		title:"",
		initialize :function(divId, title){
			this.title = title;
			$("html").append(tempHtml);
			this.timeButtonTplfun = _.template($("#i_timeButton_tpl" ).html());
			$("#i_timeButton_tpl").remove();
			$("#"+divId).html(this.timeButtonTplfun({"title":this.title?this.title:"获取验证码"}));
		},
		registBtnEvent:function(fun){
			this.fun = fun;
			$("#i_time_button").off("click", fun).on("click", fun);
		},
		//回调函数
		startCount:function(){
			var me = this;
			me.count = 60;
			$("#i_time_button").off("click", me.fun);
			$("#i_time_button").addClass("time_button_disable");
			if(me.intervalId)
				clearInterval(me.intervalId);
			me.intervalId = setInterval(function() {
				me.count--;
				if(me.count == 0){
					$("#i_time_button").on("click", me.fun);
					$("#i_time_button").removeClass("time_button_disable");
					clearInterval(me.intervalId);
				}
				me._updateBtnLable(me.count);
    		}, 1000);
		},
		_updateBtnLable:function(count){
			if(count!=0){
				$("#i_time_button").text("还剩(" + count + ")");
			}else{
				$("#i_time_button").text(this.title?this.title:"获取验证码");
			}
		}
	};
	return TimeButton;

});