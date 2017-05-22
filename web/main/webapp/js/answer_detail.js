define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var ReadKeys = {
        initialize :function(){
        	//body
			this.detailBox = $('#i_detail');
			this.tplDetailfun = _.template($("#i_tpl_detail").html());

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
		},
		_registEvent:function(){
		}
    };
    return ReadKeys;
});