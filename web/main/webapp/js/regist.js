define(['ajaxhelper', 'utility', 'validate', 'component/time_button'], 
	function(ajaxHelper, util, validate, timeBtn) {
    var Regist = {
    	user:null,
        initialize: function() {
        	//清除登录缓存
            window.localStorage.removeItem("userInfo");

        	//body
			this.mainBox = $('#i_mainbox');
			this.tplfun = _.template($("#i_tpl").html());
            if(!util.getQueryParameter("code")){
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
            var redirect_uri = "http://wx.jf-zy.com/regist.html";
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
		_render:function(user){
			this.user = user.r;
			if(!!this.user.phone){
				util.saveData('userInfo', JSON.stringify(this.user));
				window.location = "index.html";
				return;
			}
			this.mainBox.html(this.tplfun());
			timeBtn.initialize("i_getcode_btn");
			this._registEvent();
		},
		_registEvent:function(){
			$("#i_regist_btn").off("click", this._bind).on("click", {ctx: this}, this._bind);
			timeBtn.registBtnEvent(this.getCode);
		},
		getCode:function(){
			var phone = $("#i_phone").val();
            if(!phone){
                util.showToast("请先输入手机号码");
                return;
            }
            if(!validate.isMobile(phone)){
                util.showToast("请输入正确的手机号码");
                return;
            }
            var params = {
            	"phoneNum":phone
            }
            ajaxHelper.get("http://" + window.frontJSHost + "/ssm/code",
                params, this, function(){});
            timeBtn.startCount();
		},
		_bind:function(e){
			if(!e.data.ctx.validate()){
                return;
            };
			var params = {
				"phone": $("#i_phone").val(),
				"code": $("#i_input_code").val(),
				"userId": e.data.ctx.user.id
			}
			ajaxHelper.get("http://" + window.frontJSHost + "/user/bind",
                params, e.data.ctx, function(data){
                	util.saveData('userInfo', JSON.stringify(data.r));
                	window.location = "index.html";
                });
		},
		validate:function(){
            var pass = true;
            var phone = $("#i_phone").val();
            if(!phone){
                util.showToast("手机号码不能为空");
                pass = false;
                return pass;
            }
            if(!validate.isMobile(phone)){
                util.showToast("请输入正确的手机号码");
                pass = false;
                return pass;
            }
            var code = $("#i_input_code").val();
            if(!code){
                util.showToast("验证码不能为空");
                pass = false;
                return pass;
            }
            return pass;
        },
    };
    return Regist;
});