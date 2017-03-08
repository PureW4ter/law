"use strict";
//这里的与m站的不同，因为活动大多数都不需要登录，如果是登录，就直接使用
//APP中的ACCESS_TOKEN
define(["utility"],function(util){
	var AjaxHelper = {
		local:"LOCAL",
		get: function(url, param, ctx, successFun, errorFun, isPaging, access_token){
			this._send("GET", url, param, ctx, successFun, errorFun, isPaging, access_token);
		},
		post: function(url, param, ctx, successFun, errorFun, isPaging, access_token){
			this._send("POST", url, param, ctx, successFun, errorFun, isPaging, access_token);
		},
		put: function(url, param, ctx, successFun, errorFun, isPaging, access_token){
			this._send("PUT", url, param, ctx, successFun, errorFun, isPaging, access_token);
		},
		delete: function(url, param, ctx, successFun, errorFun, isPaging, access_token){
			this._send("DELETE", url, param, ctx, successFun, errorFun, isPaging, access_token);
		},
		_send: function(type, url, param, ctx, successFun, errorFun, isPaging, access_token){
			//增加本地调试功能
			if(window.localStorage.DEBUG === this.local){
				this._loadJSONData(url, ctx, successFun);
				return;
			}
			var tokenInfo = JSON.parse(localStorage.getItem("event_nosecret"));
            if(!tokenInfo || (tokenInfo && (tokenInfo.expires_time < new Date().getTime()))){
                $.ajax({
                    type: 'GET',
                    url: 'http://' + window.frontJSHost + '/oauth/oauth2/token?grant_type=client_credential'+ window.secretID,
                    async: false,
                    dataType: 'json',
                    success: function(data) {
                        data.expires_time = new Date().getTime() + data.expires_in * 1000;
                        localStorage.setItem('event_nosecret', JSON.stringify(data));
                        tokenInfo = data;
                    }
                });
            }
			//逻辑代码开始
			if(Object.prototype.toString.call(param) == "[object String]"){
				param = JSON.parse(param);
			}
			//判断传输类型
			var contentType = "application/x-www-form-urlencoded; charset=utf-8";
			if(type === "POST" || type === "PUT"){
				contentType  = "application/json;";
				param = JSON.stringify(param);
			}
			var me = this;
			me.isPaging=isPaging;
			if(!isPaging)
				this._showLoading();
			var token = access_token;
			if(!token){
				token = tokenInfo.access_token;
			}
			$.ajax({
		        type: type,
		        url: url.indexOf("?")>0?(url+"&access_token="+token):(url+"?access_token="+token),
		        data: param,
		        dataType: 'json',
		        timeout: 60000,
                contentType: contentType,
                beforeSend: function(xhr, settings) {
                    if(xhr.setRequestHeade){
                        xhr.setRequestHeader("Authorization", "Bearer " + token);
                    }
                },
		        success: function(data){
		        	me._hideLoading();
		        	if(successFun && Object.prototype.toString.call(successFun) === '[object Function]')
		            	successFun.apply(ctx, [data]);
		        },
		        error: function(xhr, type){
		        	me._hideLoading();
		        	if (xhr.status === 401) {
                        $.ajax({
                        	//刷新token
		                    type: 'GET',
		                    url: 'http://' + window.frontJSHost + '/oauth/oauth2/token?grant_type=client_credential'+ window.secretID,
		                    async: false,
		                    dataType: 'json',
		                    success: function(data) {
		                        data.expires_time = new Date().getTime() + data.expires_in * 1000;
		                        localStorage.setItem('event_nosecret', JSON.stringify(data));
		                    }
		                });
                    }else if(errorFun && Object.prototype.toString.call(errorFun) === '[object Function]'){
		            	errorFun.apply(ctx, [xhr, type]);
		        	}else{
		        		try {
                        	util.showToast("错误：" + JSON.parse(xhr.responseText).message);
	                    }
	                    catch (e) {
	                        util.showToast("服务器出错，类型：" + type);
	                    }
		        	}
		        }
	        });
		},
		_showLoading:function(){
			util.showLoading();
		},
		_hideLoading:function(){
			util.hideLoading();
		},
		loadError:function(errorInfo){
			
		},
		_loadJSONData:function(path, ctx, successFun){
			var newPath= path.substr(path.lastIndexOf("/")+1);
			if(newPath.indexOf("?")>0){
				newPath = newPath.substr(0, newPath.indexOf("?"));
			}
			newPath = "data/" + newPath + ".json";
			var testDataKey="test_data_" + new Date().getTime();
			$("body").append('<script type="text/javascript">testDataKey="' + testDataKey + '"</script>');
			util.loadJS(newPath);
			setTimeout(function(){
		        successFun.apply(ctx, [window[testDataKey]]);
			}, 10);
		}
	}
	return AjaxHelper;
});