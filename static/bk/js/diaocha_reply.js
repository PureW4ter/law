define(["component/nav_bar","component/header","ajaxhelper","utility","lib/qiniu/up_xiniu"],function(e,t,n,r,i){var s={id:"",initialize:function(){e.initialize("i_navbar",7),t.initialize("i_header","调查回复"),this.mainBox=$("#i_mainbox"),this.tplfun=_.template($("#i_tpl").html()),this._sendRequest()},_qiniuLoad:function(){new i($("#i_img_selector"),"/order_img/",function(e,t,n){var r=JSON.parse(n).key,i=$("#i_img_selector");i.parents("ul").prepend('<li><img class="j_upload" src="'+window.qiniuDomain+r+'"></li>'),i.parents("ul").find("li").length>4&&(i.parents("ul").after("<ul></ul>"),i.parents("ul").next().append(i))}),this._registEvent()},_sendRequest:function(){var e={id:r.getQueryParameter("id")};n.get("http://"+window.frontJSHost+"/api/order/getreply",e,this,this._render)},_render:function(e){e.r.lawyerReplyVo&&(this.id=e.r.lawyerReplyVo.id),this.mainBox.html(this.tplfun({result:e})),this._xheditor(),this._qiniuLoad(),this._registEvent()},_registEvent:function(){$("#i_save").off("click",this._doSave).on("click",{ctx:this},this._doSave),$("#i_cancel").off("click",this._doCancel).on("click",{ctx:this},this._doCancel),$(".j_hukou li").off("click",this._hukou).on("click",{ctx:this},this._hukou)},_hukou:function(e){var t=$(this).data("value"),n=$(this).text();$(this).closest("ul").prev("button").data("value",t).find("span").eq(0).text(n)},_xheditor:function(){var e="http://"+window.frontJSHost+"/api/pic/upload";this.editorReply=$("#i_reply").xheditor({width:"100%",height:200,html5Upload:!1,upImgUrl:e,upImgExt:"jpg,jpeg,gif,png"})},_doSave:function(e){var t=new Array;$(".j_upload").each(function(e,n){t.push($(n).attr("src"))});var i={orderId:r.getQueryParameter("id"),simpleReply:e.data.ctx.editorReply?e.data.ctx.editorReply.getSource():"",hasHukou:$("#i_hukou").data("value"),picList:t};e.data.ctx.id?(i.id=e.data.ctx.id,n.post("http://"+window.frontJSHost+"/api/order/reply",i,e.data.ctx,function(){r.showToast("更新成功�?",function(){window.location="lawyer_home.html"})})):n.post("http://"+window.frontJSHost+"/api/order/reply",i,e.data.ctx,function(){r.showToast("保存成功�?",function(){window.location="lawyer_home.html"})})},_doCancel:function(e){window.location="lawyer_home.html"}};return s});