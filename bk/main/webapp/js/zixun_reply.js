"use strict";

define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'],function(nav_bar, header, ajaxHelper, util){
	var ZixunReply = {
        id: "",
		initialize :function(){
			nav_bar.initialize("i_navbar", 7);
            header.initialize("i_header", "咨询回复");
			//body
			this.mainBox = $('#i_mainbox');
			this.tplfun = _.template($("#i_tpl").html());
			//request
			this._sendRequest();
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
			this._registEvent();
		},
		_registEvent: function () {
            $('#i_save').off("click", this._doSave).on("click", {"ctx":this, isTemp:false}, this._doSave);
            $('#i_save_temp').off("click", this._doSave).on("click", {"ctx":this, isTemp:true}, this._doSave);
            $('#i_cancel').off("click", this._doCancel).on("click", {"ctx":this}, this._doCancel);
            $('#i_confirm').off("click", this._doConfirm).on("click", {"ctx":this}, this._doConfirm);
            $('#i_cancel_assign').off("click", this._doCancelAssign).on("click", {"ctx":this}, this._doCancelAssign);
        },
        _xheditor: function(){
            var url="http://" + window.frontJSHost + "/api/pic/upload";
            this.editorReply = $('#i_reply').xheditor({width:'100%',height:200, html5Upload:false, upImgUrl:url, upImgExt:"jpg,jpeg,gif,png"});
            this.editorShizhi = $('#i_shizhi').xheditor({width:'100%',height:200, html5Upload:false, upImgUrl:url, upImgExt:"jpg,jpeg,gif,png"});
            this.editorSuggest = $('#i_suggest').xheditor({width:'100%',height:200, html5Upload:false, upImgUrl:url, upImgExt:"jpg,jpeg,gif,png"});
            this.editorRules = $('#i_rules').xheditor({width:'100%',height:200, html5Upload:false, upImgUrl:url, upImgExt:"jpg,jpeg,gif,png"});
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
                "orderId": util.getQueryParameter("id"),
                "opId" : util.getUserId()
            };
            ajaxHelper.get("http://" + window.frontJSHost + "/api/order/cancelass",
                params, e.data.ctx, function(){
                        util.showToast("更新成功！", function(){
                            window.location = "lawyer_home.html";
                        })
                    });
        },
        _doSave:function(e){
            var params = {
                orderId: util.getQueryParameter("id"),
                simpleReply: e.data.ctx.editorReply?e.data.ctx.editorReply.getSource():"",
                replySummary:e.data.ctx.editorShizhi?e.data.ctx.editorShizhi.getSource():"",
                replySuggests: e.data.ctx.editorSuggest?e.data.ctx.editorSuggest.getSource():"",
                replyRules: e.data.ctx.editorRules?e.data.ctx.editorRules.getSource():"",
                hasHukou: 0,
                temp: e.data.isTemp,
                needConfirm: util.isLawyer()
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
	return ZixunReply;
});