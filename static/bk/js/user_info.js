define(["component/nav_bar","component/header","ajaxhelper","utility"],function(e,t,n,r){var i={initialize:function(){e.initialize("i_navbar",1),t.initialize("i_header","用户详细信息"),this.mainBox=$("#i_mainbox"),this.tplFun=_.template($("#i_tpl").html()),this._sendRequest()},_sendRequest:function(){var e={id:r.getQueryParameter("id")};n.get("http://"+window.frontJSHost+"/api/user/detail",e,this,this._render,null)},_render:function(e){this.mainBox.html(this.tplFun({result:e})),this._registEvent()},_registEvent:function(){$("#i_save").off("click",this._doSave).on("click",this._doSave),$("#i_cancel").off("click",this._doCancel).on("click",this._doCancel)},_doSave:function(e){var t={id:r.getQueryParameter("id"),memo:$("#i_memo").val()};n.post("http://"+window.frontJSHost+"/api/user/memo",t,this,function(){r.showToast("更新成功")})},_doCancel:function(e){window.location="user_management.html"}};return i});