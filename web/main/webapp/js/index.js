define(['component/header','ajaxhelper', 'utility'], function(header, ajaxHelper, util) {
    var Index = {
        initialize :function(){
			//request
			this._sendRequest();
		},
		_sendRequest :function(){
			this._render();
		},
		_render:function(){
			this._registEvent();
		},
		_registEvent:function(){
			$("#i_activities").off("click", this._goActivities).on("click", {ctx: this}, this._goActivities);
			$("#i_read").off("click", this._goRead).on("click", {ctx: this}, this._goRead);
			$("#i_consult").off("click", this._goConsult).on("click", {ctx: this}, this._goConsult);
			$("#i_invest").off("click", this._goInvest).on("click", {ctx: this}, this._goInvest);
			$("#i_ask").off("click", this._goAsk).on("click", {ctx: this}, this._goAsk);
			$("#i_my").off("click", this._goMy).on("click", {ctx: this}, this._goMy);
		},
		_goActivities:function(e){
			window.location = "https://mp.weixin.qq.com/s?__biz=MzIxOTQyNDQ5NA==&mid=2247483836&idx=1&sn=d4f63d2fc4d90ac91c4435ac31a40ca7&chksm=97da323da0adbb2bcc6e2e05adaf4ce224d5fc0d0a0350dfc9d2a0027f6f32dbab92f58ca0d6&mpshare=1&scene=1&srcid=03239YygbTAGSW5rFJ5VNqR9&key=317ecbca617f644bb476ce4dfb73c7d63e13670af9021da072d2ff1e70b6f7daf3790b3b88b2690cf07d431f94b7cc9659d1e80d7d77ffeeedb2d0e9677b57b5dc141200f09fe3667f138bc64050248e&ascene=0&uin=MTEwMTQ3NjcwMg%3D%3D&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.10.5+build(14F1808)&version=12020010&nettype=WIFI&fontScale=100&pass_ticket=KoXM62J0vCFrlZ%2F2QEwZxO%2FnrXuULrlMtEdKX1Rc8vOndQUdIUtBMVCjasLOiDMF";
		},
		_goRead:function(e){
			window.location = "read_keys.html";
		},
		_goConsult:function(e){
			window.location = "lawyer_consult_info.html";
		},
		_goInvest:function(e){
			window.location = "lawyer_invest_info.html";
		},
		_goAsk:function(e){
			window.location = "answer_list.html";
		},
		_goMy:function(e){
			window.location = "regist.html";
		},
    };
    return Index;
});