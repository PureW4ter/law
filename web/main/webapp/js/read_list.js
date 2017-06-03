"use strict";

define(['component/header', 'ajaxhelper', 'utility', 'scroll'],function(header, ajaxHelper, util, listScroll){
	var ReadList = {
		//初始化标志
		initialized : false,
		pageNo : 0,
		pageSize: 5,
		initialize :function(){
			//body
			this.mainBox = $('#i_mainbox');
			this.tplfun = _.template($("#i_tpl").html());
			//request
			this._sendRequest(false);
		},
		_sendRequest :function(isPaging){
			var readKeys = JSON.parse(util.getData("readKeys"));
			if(readKeys){
			}
			var params = {
				"tags": (readKeys?readKeys.join(","):""),
				"page": this.pageNo, 
				"size":this.pageSize
			};
			ajaxHelper.get("http://" + window.frontJSHost + "/article/list",
                params, this, this._render, null, isPaging);
		},
		_render:function(data){
			this.pageNo++;
			if(!this.initialized){
				//第一次，初始化
				this.mainBox.html(this.tplfun({"result": data}));
				this._registEvent();
				this.initialized = true;
				if(data.r.length == this.pageSize){
					listScroll.initialize(this);
				}else{
					$("#i_refresh").css("display", "none");
				}
			}else{
				var a = $(this.tplfun({"result":data})).find("#i_info_list li");
				a.insertBefore('#i_refresh');
				if(data.r.length<this.pageSize){
					$("#i_refresh").css("display", "none");
					listScroll.setDisabled();
				}else{
					listScroll.setEnabled();
				}
				listScroll.refresh();
				this._registEvent();
			}
		},
		_registEvent:function(){
			$("#i_info_list").off("click", 'li', this._go).on("click", 'li', this._go);
		},
		_go:function(e){
			var id = $(e.currentTarget).data("id");
			window.location = "read_detail.html?id=" + id;
		},
		//下拉刷新回调函数
		dragFresh:function(){
        	//防止重复下拉刷新
			listScroll.setDisabled();
			this._sendRequest(true);
		}
	};
	return ReadList;
});