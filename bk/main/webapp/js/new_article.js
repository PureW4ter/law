define(['component/nav_bar','component/header', 'ajaxhelper', 'utility','lib/qiniu/up_xiniu'], 
        function (nav_bar, header, ajaxHelper, util, qiniu) {
    var UserManagement = {
        articleId:"",
        artType:["文章", "问答"],
        cities: util.cities,
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 2);
            header.initialize("i_header", "录入文章");
            this.articleId = util.getQueryParameter("id");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());
            this._sendRequest();
        },
        _sendRequest: function () {
            ajaxHelper.get("http://" + window.frontJSHost + "/article/tag",
                {}, this, this._getArticle, null);
        },
        _getArticle: function(tags){
            if(!this.articleId){
                 this._render({"result":{"r":{}}, "tags":tags, "cities":this.cities, "artType":this.artType});
            }else{
                ajaxHelper.get("http://" + window.frontJSHost + "/article/detail",
                    {id:this.articleId}, this, function(data){
                        for(var tt in data.r.tags){
                            for(var t in tags.r){
                                if(tags.r[t].name == data.r.tags[tt]){
                                    tags.r[t].selected = true;
                                    break;
                                }
                            }
                        }
                        for(var c in this.cities){
                            if(this.cities[c].id == data.r.cityId){
                                data.r.cityName = this.cities[c].name;
                            }
                        }
                        this._render({"result":data, "tags":tags, "cities":this.cities});
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
            //七牛上传图片
            $(".head_img img").each(function(){
                if($(this).attr("src")){
                    $(this).css("display","block");
                    $(this).siblings(".img_close").css("display","block");
                    $(this).siblings(".img_load").css("display","none");
                }
            });
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
            $(".j_artType li").off("click",this._selArt).on("click",{ctx: this},this._selArt);
            $("#i_save_btn").off("click",this._doSave).on("click",{ctx: this},this._doSave);
            $("[data-toggle='popover']").off({
                'focus': p_destroy,
                'click': p_destroy,
                'checkis': input_check
            }).on({
                'focus': p_destroy,
                'click': p_destroy,
                'checkis': input_check
            });
        },
        _selType:function(){
            var id = $(this).data("id");
            var text = $(this).text();
            $(this).closest("ul").prev("button").data("id",id).find("span").eq(0).text(text);
        },
        _selArt:function(){
            var id = $(this).data("id");
            var text = $(this).text();
            $(this).closest("ul").prev("button").data("id",id).find("span").eq(0).text(text);
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
            var formList = $("#i_typeForm");
            formList.find("input, button, textarea").trigger('checkis');
            setpopover();
            if ($(".popover.in").length < 1) { 
                var formJson = {};
                formJson.title = $("#i_title").val();
                formJson.titleImgUrl = $("#i_titleImgUrl").attr("src");
                formJson.summary = $("#i_summary").val();
                formJson.shareIconUrl = $("#i_head_img").attr("src");
                formJson.cityId = $("#i_city").data("id");
                formJson.type = $("#i_artType").data("id");
                formJson.content = e.data.ctx.editor.getSource();
                var tags = new Array();
                $("#i_articl_keys").find(".j_act_check").each(function(){
                    if($(this).hasClass("active")){
                        tags.push($(this).closest("span").data("name"));
                    }
                });
                formJson.tagStr = tags.join(",");
                
                if (e.data.ctx.articleId) { 
                    formJson.id=e.data.ctx.articleId;
                    ajaxHelper.post("http://" + window.frontJSHost + "/article/create",
                        formJson, e.data.ctx, function(){
                            util.showToast("更新成功！", function(){
                                window.location = "article_management.html";
                            })
                        });
                } else {
                    ajaxHelper.post("http://" + window.frontJSHost + "/article/create",
                        formJson, e.data.ctx, function(){
                            util.showToast("保存成功！", function(){
                                //window.location = "article_management.html";
                            })
                        });
                }
            }
        }
    };
    return UserManagement;
});
