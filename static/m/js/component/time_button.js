define(["text!./time_button.html"],function(e){var t={intervalId:null,fun:null,count:0,title:"",initialize:function(t,n){this.title=n,$("html").append(e),this.timeButtonTplfun=_.template($("#i_timeButton_tpl").html()),$("#i_timeButton_tpl").remove(),$("#"+t).html(this.timeButtonTplfun({title:this.title?this.title:"获取验证码"}))},registBtnEvent:function(e){this.fun=e,$("#i_time_button").off("click",e).on("click",e)},startCount:function(){var e=this;e.count=60,$("#i_time_button").off("click",e.fun),$("#i_time_button").addClass("time_button_disable"),e.intervalId&&clearInterval(e.intervalId),e.intervalId=setInterval(function(){e.count--,e.count==0&&($("#i_time_button").on("click",e.fun),$("#i_time_button").removeClass("time_button_disable"),clearInterval(e.intervalId)),e._updateBtnLable(e.count)},1e3)},_updateBtnLable:function(e){e!=0?$("#i_time_button").text("还剩("+e+")"):$("#i_time_button").text(this.title?this.title:"获取验证码")}};return t});