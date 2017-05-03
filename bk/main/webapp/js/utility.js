define([],function(){
	var Utility = {
		pageSize:10,
		getQueryParameter: function(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
			var r = window.location.search.substr(1).match(reg);
			if (r!=null) 
				return decodeURIComponent(r[2]);
			return "";
		},
		setBodyHeight:function(height){
			height = height?height:0;
			this.setBodyHeightWithHeader(height);
		},
		setBodyHeightWithHeader:function(height){
			$("#i_mainbox").css("top", (height+44)+"px");
			$("#i_loaing_mask").css("top", (height+44)+"px");
		},
		setBodyHeightWithoutHeader:function(height){
			$("#i_mainbox").css("top", height+"px");
			$("#i_loaing_mask").css("top", height+"px");
		},
		showToast: function(msg, callback){
			$("#i_toast_mask").css("display", "block");
			$("#i_toast_content").css("display", "block");
			$("#i_toast_content").text(msg);
			setTimeout(function(){
				$("#i_toast_mask").css("display", "none");
				$("#i_toast_content").css("display", "none");
				if(callback && Object.prototype.toString.call(callback) === '[object Function]')
		            	callback.apply();
			}, 1500);
		},
		showLoading: function(){
			$("#i_loaing_mask").css("display", "block");
			$("#i_loading_content").css("display", "block");
		},
		hideLoading: function(){
			$("#i_loaing_mask").css("display", "none");
			$("#i_loading_content").css("display", "none");
		},
		isWeiXin: function(){
			var ua = navigator.userAgent.toLowerCase();
	        if(ua.indexOf("micromessenger")>0) {
	            return true;
	        } else {
	            return false;
	        }
		},
		getBrowserVersion: function() {
	 		var u = navigator.userAgent.toLowerCase();
	 		return {
	 			//浏览器版本判断
	 			isWeiXin: u.indexOf("micromessenger")>=0,
	 		    isUC: u.indexOf('ucbrowser') >= 0,
	 		    isQQ: u.indexOf('qqbrowser') >= 0,
	 		    isSafari: !! u.match(/.*version\/([\w.]+).*(safari).*/),
	 		    isChrome: !! u.match(/.*(chrome)\/([\w.]+).*/),
	 		    isOpera: !! u.match(/(opera).+version\/([\w.]+)/),
	 		    //移动端判断
	 		    isMobile:!! u.match(/applewebkit.*mobile.*/),
	 		    //操作系统判断
	 		    isIos: !! u.match(/\(i[^;]+;( u;)? cpu.+mac os x/),
 				isAndroid: u.indexOf('android') > -1,
	 		}
	 	},
	 	saveData:function(key, value){
	 		window.localStorage[key] = value;
	 	},
	 	getData:function(key){
	 		return window.localStorage[key];
	 	},
	 	geoLocation:function(ctx, geoSucessFun, geoFailureFun){
	 		var me = this;
	 		if (navigator.geolocation) {  
		 		navigator.geolocation.getCurrentPosition(
	          		function(pos){
	          			me.hideLoading();
	          			geoSucessFun.apply(ctx, [pos]);
	          		}, function(err){
	          			me.hideLoading();
	          			geoFailureFun.apply(ctx, [err]);
	          		},
	          		{enableHighAccuracy: true,timeout: 3000,maximumAge: 1000}
	      		);
	      		me.showLoading();
		 	}else{
		 		geoFailureFun.apply(ctx, [{code:4}]);
		 	}
	 	},
	 	getFormValues:function(formId){
             var  x=$("#"+formId).serializeArray();
             var result = {};
            $.each(x, function(i, field){
                result[field.name] = field.value;
            });
            return result;
        },
        getJson: function (res) {
            return '{"' + res.replace(/=/g, '":"').replace(/&/g, '","').replace(/\n/g,'\\n').replace(/\r/g,'\\r') + '"}';
        },
	};
	return Utility;
});