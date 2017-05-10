define(['ajaxhelper', 'utility', 'component/time_button'], function(ajaxHelper, util, timeBtn) {
    var Regist = {
        initialize: function() {
           timeBtn.initialize("i_getcode_btn");
           this._sendRequest();
        },
		_sendRequest :function(){
			this._render();
		},
		_createWXUser :function(){
            var url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
            var redirect_uri = "http://m.weshare12.com/weshare/drawRedirect";
            var params = {};
            params["appid"] = util.appid;
            params["redirect_uri"] = encodeURIComponent(redirect_uri);
            params["response_type"] = "code";
            params["scope"] = "snsapi_userinfo";
            for(var i in params){
                url += i + '=' + params[i] + '&';
            }
            url = url.substr(0, url.length-1);
            window.location = url + "#wechat_redirect"
        },
		_render:function(){
			this._registEvent();
		},
		_registEvent:function(){
			$("#i_regist_btn").off("click", this._go).on("click", {ctx: this}, this._go);
			timeBtn.registBtnEvent(this.getCode);
		},
		_go:function(e){
			window.location = "my_question_list.html";
		} 
    };
    return Regist;
});