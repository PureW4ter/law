define(['component/header','ajaxhelper', 'utility', 'lib/qiniu/up_xiniu'], 
	function(header, ajaxHelper, util, qiniu) {
    var QuestionCompltete = {
        initialize :function(){
			this._qiniuLoad();
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
		_registEvent:function(){
			$("#i_commit").off("click", this._commit).on("click", {ctx: this}, this._commit);
		},
		_commit:function(e){
			if(!e.data.ctx.validate()){
                return;
            };
			var picList = new Array();
			$(".j_upload").each(function(index, item){
				picList.push($(item).attr("src"));
			})
			var params = {
				"id": util.getQueryParameter("id"),
				"picList": picList
			};
			ajaxHelper.post("http://" + window.frontJSHost + "/order/complete",
                params, this, function(){
                	window.location = "success.html";
                });
		},
		validate:function(){
            var pass = true;
            if($(".j_upload").length<=0){
            	util.showToast("至少上传一张图片");
                pass = false;
                return pass;
            }
            return pass;
        }
    };
    return QuestionCompltete;
});