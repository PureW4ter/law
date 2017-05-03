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
            ajaxHelper.get("http://" + window.frontJSHost + "/article/tag",
                {}, this, this._getArticle, null);
        },
        _getArticle: function(tags){
            if(!util.getQueryParameter("id")){
                 this._render({result:{}, tags:tags});
            }else{
                ajaxHelper.post("http://" + window.frontJSHost + "/user/list",
                    {}, this, function(data){
                        this._render({result:data, tags:tags});
                    }, null);
            }
        },
        _xheditor: function(){
            var url="http://upload.do";
            this.editor = $('#elm1').xheditor({width:'100%',height:1000, html5Upload:true, upImgUrl:url, upImgExt:"jpg,jpeg,gif,png"});
        },
        _render: function (data) {
            this.mainBox.html(this.tplFun(data));
            this._xheditor();
            this._qiniuLoad();
        },
        _qiniuLoad:function(){
            var imgLoad = $(".img_load");
            for(var i=0;i<imgLoad.length;i++){
                new qiniu(imgLoad[i],"/exchange/upload/artical_img/",function(up, file, info){
                    var url = JSON.parse(info).key;
                    var oImg = this.parentNode.getElementsByTagName("img")[0];
                    var oClose = this.parentNode.getElementsByClassName("img_close")[0];
                    oImg.style.display = "block";
                    oClose.style.display = "block";
                    this.style.display = "none";
                    oImg.setAttribute("src",window.qiniuDomain+url);
                })
            }
            this._registEvent();
        },
        _registEvent: function () {
            $(".j_img_close").off("click",this._removeImg).on("click",this._removeImg);
            $(".j_act_check").off("click",this._actChecked).on("click",{ctx:this},this._actChecked);
            $(".j_selType li").off("click",this._selType).on("click",{ctx: this},this._selType);
            $(".j_saveArtical").off("click",this._doSave).on("click",{ctx: this},this._doSave);
        },
        _selType:function(){
            var type = $(this).data("type");
            var text = $(this).text();
            $(this).closest("ul").prev("button").data("type",type).find("span").eq(0).text(text);
        },
        _actChecked:function(){
            $(this).toggleClass("active");
        },
        _removeImg:function(){
            $(this).siblings("img").attr("src","").css("display","none");
            $(this).siblings(".img_load").css("display","block");
            $(this).parent().siblings(".removeModule").css("display","block");
            $(this).css("display","none");
        },
        _doSave:function(e){
            var params = {};
            params.instructions = e.data.ctx.editor.getSource();
            
        }

    };
    return UserManagement;
});
