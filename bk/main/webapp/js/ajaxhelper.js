"use strict";

define(["utility"], function(util) {
    var AjaxHelper = {
        local: "LOCAL",
        get: function(url, param, ctx, successFun, errorFun, isPaging, async) {
            this._send("GET", url, param, ctx, successFun, errorFun, isPaging, async);
        },
        post: function(url, param, ctx, successFun, errorFun, isPaging, async) {
            this._send("POST", url, param, ctx, successFun, errorFun, isPaging, async);
        },
        put: function(url, param, ctx, successFun, errorFun, isPaging, async) {
            this._send("PUT", url, param, ctx, successFun, errorFun, isPaging, async);
        },
        delete: function(url, param, ctx, successFun, errorFun, isPaging, async) {
            this._send("DELETE", url, param, ctx, successFun, errorFun, isPaging, async);
        },
        _send: function(type, url, param, ctx, successFun, errorFun, isPaging, async) {
            //增加本地调试功能
            if (window.localStorage.DEBUG === this.local) {
                this._loadJSONData(url, ctx, successFun);
                return;
            }
            //逻辑代码开始
            if(async == undefined){
                async = true;
            }
            //判断传输类型
            var contentType = "application/x-www-form-urlencoded; charset=utf-8";
            if (type === "POST" || type === "PUT") {
                contentType = "application/json;";
                param = JSON.stringify(param);
            }
            var me = this;
            me.isPaging = isPaging;
            if (!isPaging)
                this._showLoading();
            $.ajax({
                type: type,
                url: url,
                data: param,
                dataType: 'json',
                timeout: 600000,
                async: async,
                contentType: contentType,
                beforeSend: function(xhr, settings) {
                },
                success: function(data) {
                    me._hideLoading();
                    if((data && data.s == 200) || !data || !data.s){
                        if (successFun && Object.prototype.toString.call(successFun) === '[object Function]')
                            successFun.apply(ctx, [data]);
                    }else{
                        util.showToast("服务器出错，类型：" + data.s);
                    }
                },
                error: function(xhr, type) {
                    me._hideLoading();
                    util.showToast("服务器出错，类型：" + type);
                    if (errorFun && Object.prototype.toString.call(errorFun) === '[object Function]')
                        errorFun.apply(ctx, [xhr, type]);
                }
            });
        },
        _showLoading: function() {
            util.showLoading();
        },
        _hideLoading: function() {
            util.hideLoading();
        },
        loadError: function(errorInfo) {

        },
        _loadJSONData: function(path, ctx, successFun) {
            var newPath = path.substr(path.lastIndexOf("/") + 1);
            if (newPath.indexOf("?") > 0) {
                newPath = newPath.substr(0, newPath.indexOf("?"));
            }
            newPath = "data/" + newPath + ".json";
            var testDataKey = "test_data_" + new Date().getTime();
            $("body").append('<script type="text/javascript">testDataKey="' + testDataKey + '"</script>');
            $("body").append('<script src="' + newPath + '" type="text/javascript"></script>');
            setTimeout(function() {
                successFun.apply(ctx, [window[testDataKey]]);
            }, 10);
        },
    }
    return AjaxHelper;
});