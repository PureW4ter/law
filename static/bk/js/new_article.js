define(["component/nav_bar","component/header","ajaxhelper","utility"],function(e,t,n,r){var i={initialize:function(){e.initialize("i_navbar",2),t.initialize("i_header","录入文章"),this.mainBox=$("#i_mainbox"),this.tplFun=_.template($("#i_tpl").html()),this._sendRequest()},_sendRequest:function(){this._render()},_xheditor:function(){var e="http://upload.do";this.editor=$("#elm1").xheditor({width:"100%",height:1e3,html5Upload:!0,upImgUrl:e,upImgExt:"jpg,jpeg,gif,png"})},_render:function(e){this.mainBox.html(this.tplFun()),this._registEvent(),this._xheditor()},_registEvent:function(){}};return i});