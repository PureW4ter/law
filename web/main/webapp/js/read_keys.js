define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var ReadKeys = {
        initialize :function(){
			//body
			this.mainBox = $('#i_mainbox');
			this.tplfun = _.template($("#i_tpl").html());
			//request
			this._sendRequest();
		},
		_sendRequest :function(){
			var params = {

			};
			ajaxHelper.get("http://" + window.frontJSHost + "/article/tag",
                params, this, this._render);
		},
		_render:function(data){
			this.mainBox.html(this.tplfun({"result": data}));
			this._registEvent();
		},
		_registEvent:function(){
			$("#i_read_keys").off("click", 'li', this._select).on("click", 'li', {ctx:this}, this._select);
			$("#i_go").off("click", this._go).on("click", {ctx: this}, this._go);
		},
		_go:function(e){
			var keys = new Array();
			$(".key_selected").forEach(
				function(item, index){
					keys.push($(item).data("name"));
				}
			);
			window.location = "read_list.html";
		},
		_select:function(e){
			$(e.currentTarget).find("div").toggleClass("key_selected");
		}
    };
    return ReadKeys;
});