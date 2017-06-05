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
			if(util.getData("readKeys")){
				var readKeys = JSON.parse(util.getData("readKeys"));
				data.r.forEach(
					function(item, index){
						for(var i=0; i<readKeys.length; i++){
							if(item.name == readKeys[i]){
								item.selected = true;
							}
						}
					}
				);
			}
			this.mainBox.html(this.tplfun({"result": data, "readKeys": readKeys}));
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
					keys.push($(item).parents("li").data("name"));
				}
			);
			util.saveData('readKeys', JSON.stringify(keys));
			window.location = "read_list.html";
		},
		_select:function(e){
			$(e.currentTarget).find("div").toggleClass("key_selected");
		}
    };
    return ReadKeys;
});