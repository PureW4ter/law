define([],function(){var e={pageSize:10,cities:[{name:"北京",id:1},{name:"上海",id:2},{name:"广州",id:3}],getQueryParameter:function(e){var t=new RegExp("(^|&)"+e+"=([^&]*)(&|$)","i"),n=window.location.search.substr(1).match(t);return n!=null?decodeURIComponent(n[2]):""},setBodyHeight:function(e){e=e?e:0,this.setBodyHeightWithHeader(e)},setBodyHeightWithHeader:function(e){$("#i_mainbox").css("top",e+44+"px"),$("#i_loaing_mask").css("top",e+44+"px")},setBodyHeightWithoutHeader:function(e){$("#i_mainbox").css("top",e+"px"),$("#i_loaing_mask").css("top",e+"px")},showToast:function(e,t){$("#i_toast_mask").css("display","block"),$("#i_toast_content").css("display","block"),$("#i_toast_content").text(e),setTimeout(function(){$("#i_toast_mask").css("display","none"),$("#i_toast_content").css("display","none"),t&&Object.prototype.toString.call(t)==="[object Function]"&&t.apply()},1500)},showLoading:function(){$("#i_loaing_mask").css("display","block"),$("#i_loading_content").css("display","block")},hideLoading:function(){$("#i_loaing_mask").css("display","none"),$("#i_loading_content").css("display","none")},isWeiXin:function(){var e=navigator.userAgent.toLowerCase();return e.indexOf("micromessenger")>0?!0:!1},getBrowserVersion:function(){var e=navigator.userAgent.toLowerCase();return{isWeiXin:e.indexOf("micromessenger")>=0,isUC:e.indexOf("ucbrowser")>=0,isQQ:e.indexOf("qqbrowser")>=0,isSafari:!!e.match(/.*version\/([\w.]+).*(safari).*/),isChrome:!!e.match(/.*(chrome)\/([\w.]+).*/),isOpera:!!e.match(/(opera).+version\/([\w.]+)/),isMobile:!!e.match(/applewebkit.*mobile.*/),isIos:!!e.match(/\(i[^;]+;( u;)? cpu.+mac os x/),isAndroid:e.indexOf("android")>-1}},saveData:function(e,t){window.localStorage[e]=t},getData:function(e){return window.localStorage[e]},geoLocation:function(e,t,n){var r=this;navigator.geolocation?(navigator.geolocation.getCurrentPosition(function(n){r.hideLoading(),t.apply(e,[n])},function(t){r.hideLoading(),n.apply(e,[t])},{enableHighAccuracy:!0,timeout:3e3,maximumAge:1e3}),r.showLoading()):n.apply(e,[{code:4}])},getFormValues:function(e){var t=$("#"+e).serializeArray(),n={};return $.each(t,function(e,t){n[t.name]=t.value}),n},getJson:function(e){return'{"'+e.replace(/=/g,'":"').replace(/&/g,'","').replace(/\n/g,"\\n").replace(/\r/g,"\\r")+'"}'}};return e});