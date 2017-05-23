define([],function(){
	var Utility = {
		pageSize:5,
		//生产
		appid: "wx3eb0212d543f4752",
		cities:[{"name":"北京", "id": 1}, {"name":"上海", "id": 2}, {"name":"广州", "id": 3}],
		PRODUCT_CODE_ZIXUN:"Y",
		PRODUCT_CODE_ZIXUNP:"YP",
		PRODUCT_CODE_JIANDANWEN:"J",
		PRODUCT_CODE_HUKOU:"H",
		PRODUCT_CODE_CHAFENG:"C",
		getQueryParameter: function(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
			var r = window.location.search.substr(1).match(reg);
			if (r!=null)
				return decodeURIComponent(r[2]);
			return "";
		},
		buildRequestParametrStr:function(){
			var urlParam = "";
			//TODO
			urlParam += "&city="+city;
			return urlParam;
		},
		buildRequestParameterObj:function(){
			var param = {};
			//TODO
			param["city"] = city;
			return param;
		},
		setBodyHeight:function(height){
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
	 	getJson: function(res) {
            return '{"' + res.replace(/=/g, '":"').replace(/&/g,'","') + '"}';
        },
        loadJS:function(file) {
		    var jsElm = document.createElement("script");
		    jsElm.type = "application/javascript";
		    jsElm.src = file;
		    document.body.appendChild(jsElm);
		},
		initMlink:function(options){
			new Mlink(options);
		},
		jumpToApp:function(path){
			var w = this.getBrowserVersion();
            if(w.isWeiXin){
               //微信 
               this.showWinxinMask();
            }else if(!window.WSBridge){
            	//不在微信也不在APP
                setTimeout(function(){
                    if((new Date().getTime()-openStartTime)<=4500){
                        window.location = "http://a.app.qq.com/o/simple.jsp?pkgname=com.juren.ws";
                    }
                }, 3000);
                openStartTime = new Date().getTime();
                window.location = path;
            }else{
            	//在APP
             	window.location = path;
            }
		},
		showWinxinMask:function(e){
			$("#i_weixin_mask").css("display", "block");
		},
		hideWinxinMask:function(e){
			$("#i_weixin_mask").css("display", "none");
		},
		insertGide: function(){
			$("body").append('<div id="i_weixin_mask" class="weixin_mask"><img src="img/weixin_mask.png"></div>');
		    //渲染完成以后，再进行事件的注册
		    setTimeout(function(){
		    	$("#i_weixin_mask").on("click", function(){
    				$("#i_weixin_mask").css("display", "none");
				});
		    }, 0);
		},
		weixinShare : function(attr, data, successFun, errorFun) {
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端util.showToast出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: this.appid, // 必填，公众号的唯一标识
                timestamp:data.timestamp , // 必填，生成签名的时间戳
                nonceStr: data.nonceStr, // 必填，生成签名的随机串
                signature: data.signature,// 必填，签名，见附录1
                jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });
            wx.ready(function () {
            	//分享至朋友圈
            	wx.onMenuShareTimeline({
                    title: attr[0].title, // 分享标题
                    desc: attr[0].desc, // 分享描述
                    link: attr[0].link, // 分享链接
                    imgUrl: attr[0].imgUrl, // 分享图标
                    success: function () {
                        // 用户确认分享后执行的回调函数
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                    }
                });
            	wx.onMenuShareAppMessage({
            		//分享至微信好友
                    title: attr[1].title, // 分享标题
                    desc: attr[1].desc, // 分享描述
                    link: attr[1].link, // 分享链接
                    imgUrl: attr[1].imgUrl, // 分享图标
                    type: '', // 分享类型,music、video或link，不填默认为link
                    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                    success: function () {
                    	// 用户确认分享后执行的回调函数
                    },
                    cancel: function () {
              			// 用户取消分享后执行的回调函数
                    }
            	});
       	 	});
        },
        getUserId:function(){
        	return 1;
        }
	};
	return Utility;
});