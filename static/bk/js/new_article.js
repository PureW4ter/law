define(["component/nav_bar","component/header","ajaxhelper","utility","lib/qiniu/up_xiniu"],function(e,t,n,r,i){var s={articleId:"",artType:["文章","问答"],cities:r.cities,initialize:function(){e.initialize("i_navbar",2),t.initialize("i_header","文章管理"),this.articleId=r.getQueryParameter("id"),this.mainBox=$("#i_mainbox"),this.tplFun=_.template($("#i_tpl").html()),this._sendRequest()},_sendRequest:function(){n.get("http://"+window.frontJSHost+"/api/article/tag",{},this,this._getArticle,null)},_getArticle:function(e){this.articleId?n.get("http://"+window.frontJSHost+"/api/article/detail",{id:this.articleId},this,function(t){for(var n in t.r.tags)for(var r in e.r)if(e.r[r].name==t.r.tags[n]){e.r[r].selected=!0;break}for(var i in this.cities)this.cities[i].id==t.r.cityId&&(t.r.cityName=this.cities[i].name);this._render({result:t,tags:e,cities:this.cities,artType:this.artType})},null):this._render({result:{r:{}},tags:e,cities:this.cities,artType:this.artType})},_xheditor:function(){var e="http://"+window.frontJSHost+"/api/pic/upload";this.editor=$("#elm1").xheditor({width:"100%",height:1e3,html5Upload:!1,upImgUrl:e,upImgExt:"jpg,jpeg,gif,png",sourceMode:!0})},_render:function(e){this.mainBox.html(this.tplFun(e)),this._xheditor(),this._qiniuLoad(),$(".head_img img").each(function(){$(this).attr("src")&&($(this).css("display","block"),$(this).siblings(".img_close").css("display","block"),$(this).siblings(".img_load").css("display","none"))}),e.result.r.type==1&&$("#i_keys").hide()},_qiniuLoad:function(){var e=$(".img_load");for(var t=0;t<e.length;t++)new i(e[t],"/exchange/upload/artical_img/",function(e,t,n){var r=JSON.parse(n).key,i=this.parentNode.getElementsByTagName("img")[0],s=this.parentNode.getElementsByClassName("img_close")[0];i.style.display="block",s.style.display="block",this.style.display="none",i.setAttribute("src",window.qiniuDomain+r)});this._registEvent()},_registEvent:function(){$(".j_img_close").off("click",this._removeImg).on("click",this._removeImg),$(".j_act_check").off("click",this._actChecked).on("click",{ctx:this},this._actChecked),$(".j_selType li").off("click",this._selType).on("click",{ctx:this},this._selType),$(".j_artType li").off("click",this._selArt).on("click",{ctx:this},this._selArt),$("#i_save_btn").off("click",this._doSave).on("click",{ctx:this},this._doSave),$("[data-toggle='popover']").off({focus:p_destroy,click:p_destroy,checkis:input_check}).on({focus:p_destroy,click:p_destroy,checkis:input_check})},_selType:function(){var e=$(this).data("id"),t=$(this).text();$(this).closest("ul").prev("button").data("id",e).find("span").eq(0).text(t)},_selArt:function(){var e=$(this).data("id"),t=$(this).text();t=="问答"?$("#i_keys").hide():$("#i_keys").show(),$(this).closest("ul").prev("button").data("id",e).find("span").eq(0).text(t)},_actChecked:function(){$(this).toggleClass("active")},_removeImg:function(){$(this).siblings("img").attr("src","").css("display","none"),$(this).siblings(".img_load").css("display","block"),$(this).parent().siblings(".removeModule").css("display","block"),$(this).css("display","none")},_doSave:function(e){var t=$("#i_typeForm");t.find("input, button, textarea").trigger("checkis"),setpopover();if($(".popover.in").length<1){var i={};i.title=$("#i_title").val(),i.titleImgUrl=$("#i_titleImgUrl").attr("src"),i.summary=$("#i_summary").val(),i.shareIconUrl=$("#i_head_img").attr("src"),i.cityId=$("#i_city").data("id"),i.type=$("#i_artType").data("id"),i.content=e.data.ctx.editor.getSource().replace(/&quot;/g,"'");var s=new Array;$("#i_articl_keys").find(".j_act_check").each(function(){$(this).hasClass("active")&&s.push($(this).closest("span").data("name"))}),i.tagStr=s.join(","),e.data.ctx.articleId?(i.id=e.data.ctx.articleId,n.post("http://"+window.frontJSHost+"/api/article/create",i,e.data.ctx,function(){r.showToast("更新成功！",function(){window.location="article_management.html"})})):n.post("http://"+window.frontJSHost+"/api/article/create",i,e.data.ctx,function(){r.showToast("保存成功！",function(){window.location="article_management.html"})})}}};return s});