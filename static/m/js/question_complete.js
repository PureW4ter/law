define('question_complete',["component/header","ajaxhelper","utility","lib/qiniu/up_xiniu"],function(e,t,n,r){var i={initialize:function(){this._qiniuLoad()},_qiniuLoad:function(){new r($("#i_img_selector"),"/order_img/",function(e,t,n){var r=JSON.parse(n).key,i=$("#i_img_selector");i.parents("ul").prepend('<li><img class="j_upload" src="'+window.qiniuDomain+r+'"></li>'),i.parents("ul").find("li").length>4&&(i.parents("ul").after("<ul></ul>"),i.parents("ul").next().append(i))}),this._registEvent()},_registEvent:function(){$("#i_commit").off("click",this._commit).on("click",{ctx:this},this._commit)},_commit:function(e){if(!e.data.ctx.validate())return;var r=new Array;$(".j_upload").each(function(e,t){r.push($(t).attr("src"))});var i={id:n.getQueryParameter("id"),comment:$("#i_comment").val(),picList:r};t.post("http://"+window.frontJSHost+"/order/complete",i,this,function(){window.location="success.html"})},validate:function(){var e=!0,t=$("#i_comment").val();return t?t.length>500?(n.showToast("最多不可以超过500字"),e=!1,e):$(".j_upload").length<=0?(n.showToast("至少上传一张图片"),e=!1,e):e:(n.showToast("补充内容不能为空"),e=!1,e)}};return i});