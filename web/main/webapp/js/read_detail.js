define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var ReadKeys = {
        initialize :function(){
        	//body
			this.detailBox = $('#i_detail');
			this.tplDetailfun = _.template($("#i_tpl_detail").html());

			this.listBox = $('#i_list');
			this.tplListfun = _.template($("#i_tpl_list").html());
			//request
			this._sendRequest();
		},
		_sendRequest :function(){
			var params = {id: util.getQueryParameter("id")};
			ajaxHelper.get("http://" + window.frontJSHost + "/article/detail",
                params, this, this._render);
		},
		_render:function(data){
			this.detailBox.html(this.tplDetailfun({"result": data}));
			var params = {tags:data.r.tags.join(","), "page": 0, "size": 2};
			ajaxHelper.get("http://" + window.frontJSHost + "/article/list",
                params, this, this._renderList);
		},
		_renderList:function(data){
			this.listBox.html(this.tplListfun({"result": data}));
			this._registEvent();
		},
		_registEvent:function(){
			$("#i_info_list").off("click", 'li', this._go).on("click", 'li', this._go);
		},
		_go:function(e){
			var id = $(e.currentTarget).data("id");
			window.location = "read_detail.html?id=" + id;
		}
    };
    return ReadKeys;
});