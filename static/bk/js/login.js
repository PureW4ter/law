define(["component/header","ajaxhelper","utility","lib/MD5"],function(e,t,n,r){var i={initialize:function(){this.mainBox=$("#i_mainbox"),this.logontplfun=_.template($("#i_login_tpl").html()),this._sendRequest()},_sendRequest:function(e){this._render()},_render:function(e){this.mainBox.html(this.logontplfun()),this._registEvent()},_registEvent:function(){$("[data-toggle='popover']").bind({focus:p_destroy,blur:input_check}),$(".submit").off("click",this.subIt).on("click",{ctx:this},this.subIt)},subIt:function(e){var r=n.getFormValues("loginIt");$("input").blur(),$(".popover.in").length<1&&t.get("http://"+window.frontJSHost+"/api/user/login",r,e.data.ctx,e.data.ctx.success,null)},bindEnter:function(e){e.keyCode==13&&(e.preventDefault(),e.data.ctx.subIt(e))},success:function(e){e.expires_time=(new Date).getTime()+36e5,window.localStorage.setItem("userInfo",JSON.stringify(e.r)),window.location="blank.html"}};return i});