define(["component/nav_bar","component/header","ajaxhelper","utility"],function(e,t,n,r){var i={id:"",initialize:function(){e.initialize("i_navbar",7),t.initialize("i_header","咨询回复"),this.mainBox=$("#i_mainbox"),this.tplfun=_.template($("#i_tpl").html()),this._sendRequest()},_sendRequest:function(){var e={id:r.getQueryParameter("id")};n.get("http://"+window.frontJSHost+"/api/order/getreply",e,this,this._render)},_render:function(e){e.r.lawyerReplyVo&&(this.id=e.r.lawyerReplyVo.id),this.mainBox.html(this.tplfun({result:e})),this._xheditor(),this._registEvent()},_registEvent:function(){$("#i_save").off("click",this._doSave).on("click",{ctx:this,isTemp:!1},this._doSave),$("#i_save_temp").off("click",this._doSave).on("click",{ctx:this,isTemp:!0},this._doSave),$("#i_cancel").off("click",this._doCancel).on("click",{ctx:this},this._doCancel),$("#i_confirm").off("click",this._doConfirm).on("click",{ctx:this},this._doConfirm),$("#i_cancel_assign").off("click",this._doCancelAssign).on("click",{ctx:this},this._doCancelAssign)},_xheditor:function(){var e="http://"+window.frontJSHost+"/api/pic/upload";this.editorReply=$("#i_reply").xheditor({width:"100%",height:200,html5Upload:!1,upImgUrl:e,upImgExt:"jpg,jpeg,gif,png"}),this.editorShizhi=$("#i_shizhi").xheditor({width:"100%",height:200,html5Upload:!1,upImgUrl:e,upImgExt:"jpg,jpeg,gif,png"}),this.editorSuggest=$("#i_suggest").xheditor({width:"100%",height:200,html5Upload:!1,upImgUrl:e,upImgExt:"jpg,jpeg,gif,png"}),this.editorRules=$("#i_rules").xheditor({width:"100%",height:200,html5Upload:!1,upImgUrl:e,upImgExt:"jpg,jpeg,gif,png"})},_doConfirm:function(e){var t={orderId:r.getQueryParameter("id")};n.get("http://"+window.frontJSHost+"/api/order/confirm",t,e.data.ctx,function(){r.showToast("更新成功！",function(){window.location="lawyer_home.html"})})},_doCancelAssign:function(e){var t={id:r.getQueryParameter("id")};n.get("http://"+window.frontJSHost+"/api/order/assignment",t,e.data.ctx,function(){r.showToast("更新成功！",function(){window.location="lawyer_home.html"})})},_doSave:function(e){var t={orderId:r.getQueryParameter("id"),simpleReply:e.data.ctx.editorReply?e.data.ctx.editorReply.getSource():"",replySummary:e.data.ctx.editorShizhi?e.data.ctx.editorShizhi.getSource():"",replySuggests:e.data.ctx.editorSuggest?e.data.ctx.editorSuggest.getSource():"",replyRules:e.data.ctx.editorRules?e.data.ctx.editorRules.getSource():"",hasHukou:0,isTemp:e.data.ctx.isTemp,needConfirm:r.isLawyer()};e.data.ctx.id?(t.id=e.data.ctx.id,n.post("http://"+window.frontJSHost+"/api/order/reply",t,e.data.ctx,function(){r.showToast("更新成功！",function(){window.location="lawyer_home.html"})})):n.post("http://"+window.frontJSHost+"/api/order/reply",t,e.data.ctx,function(){r.showToast("保存成功！",function(){window.location="lawyer_home.html"})})},_doCancel:function(e){window.location="lawyer_home.html"}};return i});