define(['component/nav_bar','component/header', 'ajaxhelper', 'utility','lib/qiniu/up_xiniu'], 
        function (nav_bar, header, ajaxHelper, util, qiniu) {
    var UserManagement = {
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 2);
            header.initialize("i_header", "录入文章");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            /*var params = {
                type:2
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/user/list",
                params, this, this._render, null);*/
            this._render();
        },
        _xheditor: function(){
            var url="http://upload.do";
            this.editor = $('#elm1').xheditor({width:'100%',height:1000, html5Upload:true, upImgUrl:url, upImgExt:"jpg,jpeg,gif,png"});
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun());
            this._registEvent();
            this._xheditor();
        },
        _qiniuLoad:function(){
            var imgLoad = $(".img_load");
            for(var i=0;i<imgLoad.length;i++){
                new qiniu(imgLoad[i],"/exchange/upload/artical_img/",function(up, file, info){
                   var url = JSON.parse(info).key;
                   var oImg = this.parentNode.getElementsByTagName("img")[0];
                   var oClose = this.parentNode.getElementsByClassName("img_close")[0];
                   var oRemove = this.parentNode.parentNode.getElementsByClassName("removeModule")[0];
                   oImg.style.display = "block";
                   oClose.style.display = "block";
                   this.style.display = "none";
                   if(oRemove){
                        oRemove.style.display = "none";
                   }
                   oImg.setAttribute("src",window.qiniuDomain+url);
                })
            }
            this._rigistEvent();
        },
        _registEvent: function () {
            
        },

    };
    return UserManagement;
});
