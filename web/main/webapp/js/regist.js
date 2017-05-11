define(['ajaxhelper', 'utility', 'component/time_button'], function(ajaxHelper, util, timeBtn) {
    var Regist = {
    	userAccount:null,
        initialize: function() {
        	//body
			this.mainBox = $('#i_mainbox');
			this.tplfun = _.template($("#i_tpl").html());
            if(util.getQueryParameter("code")){
           		this._createWXUser();
            }else{
           		this._sendRequest();
            }
        },
		_sendRequest :function(){
			var params = {code: util.getQueryParameter("code")};
			ajaxHelper.get("http://" + window.frontJSHost + "/user/wxlogin",
                params, this, this._render);
		},
		_createWXUser :function(){
            var url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
            var redirect_uri = "http://m.weshare123.net/law/regist.html";
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
		_render:function(userAccount){
			this.userAccount = userAccount;
			this.mainBox.html(this.tplfun());
			timeBtn.initialize("i_getcode_btn");
			this._registEvent();
		},
		_registEvent:function(){
			$("#i_regist_btn").off("click", this._bind).on("click", {ctx: this}, this._bind);
			timeBtn.registBtnEvent(this.getCode);
		},
		getCode:function(){

		},
		_bind:function(e){
			var params = {
				"phone": $("#i_phone").val(),
				"code": $("#i_input_code").val(),
				"userId": e.data.ctx.userAccount
			}
			ajaxHelper.get("http://" + window.frontJSHost + "/user/bind",
                params, this, this._render);
		} 
    };
    return Regist;
});