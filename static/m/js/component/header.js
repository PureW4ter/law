"use strict";

define(['text!./header.html'],function(tempHtml){
	var Header = {
		initialize :function(divId, title, showRefreshBtn, showBackBtn, rightFunBtn, showDownicon, showShareBtn){;
			//为了能够解析，插入
			$("html").append(tempHtml);
			this.headertplfun = _.template($("#i_header_tpl" ).html());
			//使用完成后，删除
			//alert("window.WSBridge=" + window.WSBridge);
			$("#i_header_tpl").remove();
			var data = {title:title,
		            	showHeader:(window.WSBridge?"hidderHidden":"header"),
		         	 	showRefreshBtn:showRefreshBtn, 
		         	 	showBackBtn:showBackBtn,
		         	 	rightFunBtn:rightFunBtn,
		         	 	showDownicon:showDownicon,
		         	 	showShareBtn:showShareBtn
		         	};
			$("#"+divId).html(this.headertplfun(data));
			$("#i_return").off("click", this._goBack).on("click", {ctx:this}, this._goBack);
		},
		_goBack:function(){
			var prevPage = window.location.href;
    		window.history.go(-1);
    		setTimeout(function(){ 
    			if (window.location.href == prevPage) 
    				window.location.href = "index.html"; 
    		}, 300);
		},
		setTitle: function(title){
			$("#i_header_title").text(title);
			if(window.WSBridge){
                if(window.WSBridge.updateTitle){
                    window.WSBridge.updateTitle(title);
                }
            }
            document.title = title;
		},
		setRightBtnTitle: function(title){
			$("#i_right_fun_button").text(title);
		},
		setBannerColor: function(bgColor, fontColor){

		},
		registRefreshEvent:function(fun, ctx, params){
			$("#i_refresh_btn").off("click", fun).on("click", ctx, fun);
		},
		registRightFunEvent:function(fun, ctx, params){
			$("#i_right_fun_button").off("click", fun).on("click", ctx, fun);
		},
		registDownEvent:function(fun, ctx, params){
			$("#i_header_title").off("click", fun).on("click", ctx, fun);
		},
		registShareEvent:function(fun, ctx, params){
			$("#i_share_btn").off("click", fun).on("click", ctx, fun);
		},
	};
	return Header;
});