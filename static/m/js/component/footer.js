define(["text!./footer.html","utility"],function(e,t){var n={initialize:function(t,n,r){$("html").append(e),this.footertplfun=_.template($("#i_footer_tpl").html()),$("#i_footer_tpl").remove();var i={bigText:n,smallText:r};$("#"+t).html(this.footertplfun({result:i})),this._registEvent()},_registEvent:function(){$("#i_openFooter").off("click",this._openFooter).on("click",{ctx:this},this._openFooter),$("#i_closeFooter").off("click",this._closeFooter).on("click",{ctx:this},this._closeFooter),$("#i_footerTel").off("click",this._footerTel).on("click",{ctx:this},this._footerTel),$("#i_footerDown").off("click",this._footerDown).on("click",{ctx:this},this._footerDown)},_openFooter:function(){$("#i_openFooter").css({left:"-100%"},600),setTimeout(function(){$("#i_footerBox").css({left:0})},600)},_closeFooter:function(){$("#i_footerBox").css({left:"-100%"},600),setTimeout(function(){$("#i_openFooter").css({left:0})},700)},_footerTel:function(){window.location="tel://4009903838"},_footerDown:function(){window.location="http://a.app.qq.com/o/simple.jsp?pkgname=com.juren.ws"}};return n});