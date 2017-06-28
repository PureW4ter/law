"use strict";

define(['component/nav_bar','component/header', 'ajaxhelper', 'utility', 'lib/qiniu/up_xiniu'],
    function(nav_bar, header, ajaxHelper, util, qiniu){
	var DiaochaReply = {
		id: "",
		initialize :function(){
			nav_bar.initialize("i_navbar", 7);
			header.initialize("i_header", "调查回复");
			//body
			this.mainBox = $('#i_mainbox');
			this.tplfun = _.template($("#i_tpl").html());
			//request
			this._sendRequest();
		},
        _qiniuLoad:function(){
            new qiniu($("#i_img_selector"),"/order_img/",function(up, file, info){
                var url = JSON.parse(info).key;
                var selector = $("#i_img_selector");
                selector.parents("ul").prepend('<li><img class="j_upload" src="'+ window.qiniuDomain+url + '"></li>')
                if(selector.parents("ul").find("li").length>4){
                    selector.parents("ul").after("<ul></ul>");
                    selector.parents("ul").next().append(selector);
                }
            })
            this._registEvent();
        },
		_sendRequest :function(){
			var params = {
				"id": util.getQueryParameter("id"),
			};
			ajaxHelper.get("http://" + window.frontJSHost + "/api/order/getreply",
                params, this, this._render);
		},
		_render:function(data){
			if(data.r.lawyerReplyVo){
                this.id = data.r.lawyerReplyVo.id;
            }
			this.mainBox.html(this.tplfun({"result": data}));
			this._xheditor();
            this._qiniuLoad();
			this._registEvent();
		},
		_registEvent: function () {
            $('#i_save').off("click", this._doSave).on("click", {"ctx":this, isTemp:false}, this._doSave);
            $('#i_save_temp').off("click", this._doSave).on("click", {"ctx":this, isTemp:true}, this._doSave);
            $('#i_cancel').off("click", this._doCancel).on("click", {"ctx":this}, this._doCancel);
             $('#i_confirm').off("click", this._doConfirm).on("click", {"ctx":this}, this._doConfirm);
            $('#i_cancel_assign').off("click", this._doCancelAssign).on("click", {"ctx":this}, this._doCancelAssign);
            $(".j_hukou li").off("click",this._hukou).on("click",{ctx: this},this._hukou);
        },
        _hukou:function(e){
            var value = $(this).data("value");
            var text = $(this).text();
            $(this).closest("ul").prev("button").data("value", value).find("span").eq(0).text(text); 
        },
        _xheditor: function(){
            var url="http://" + window.frontJSHost + "/api/pic/upload";
            this.editorReply = $('#i_reply').xheditor({width:'100%',height:200, html5Upload:false, upImgUrl:url, upImgExt:"jpg,jpeg,gif,png"});
        },
        _doConfirm:function(e){
            var params = {
                "orderId": util.getQueryParameter("id"),
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/order/confirm",
                params, e.data.ctx, function(){
                        util.showToast("更新成功！", function(){
                            window.location = "lawyer_home.html";
                        })
                    });
        },
        _doCancelAssign:function(e){
            var params = {
                "id": util.getQueryParameter("id"),
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/order/assignment",
                params, e.data.ctx, function(){
                        util.showToast("更新成功！", function(){
                            window.location = "lawyer_home.html";
                        })
                    });
        },
        _doSave:function(e){
            var picList = new Array();
            $(".j_upload").each(function(index, item){
                picList.push($(item).attr("src"));
            })

            var params = {
                "orderId": util.getQueryParameter("id"),
                "simpleReply": e.data.ctx.editorReply?e.data.ctx.editorReply.getSource():"",
                "hasHukou": $("#i_hukou").data("value"),
                "picList": picList,
                "isTemp": e.data.ctx.isTemp,
                "needConfirm": util.isLawyer()
            }
            if (e.data.ctx.id) { 
                params.id=e.data.ctx.id;
                ajaxHelper.post("http://" + window.frontJSHost + "/api/order/reply",
                    params, e.data.ctx, function(){
                        util.showToast("更新成功！", function(){
                            window.location = "lawyer_home.html";
                        })
                    });
            } else {
                ajaxHelper.post("http://" + window.frontJSHost + "/api/order/reply",
                    params, e.data.ctx, function(){
                        util.showToast("保存成功！", function(){
                            window.location = "lawyer_home.html";
                        })
                    });
            }
        },
        _doCancel:function(e){
            window.location = "lawyer_home.html";
        }
	};
	return DiaochaReply;
});