define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var AlbumManagement = {
        mySwipper: null,
        currentTag: 1,
        currentPage: 1,
        size: 30,
        currentPicList: null,
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 1);
            header.initialize("i_header", "相册管理");
            //报备信息
            this.tabsBox = $('#i_tabs');
            this.tabsFun = _.template($("#i_tabs_tpl").html());

            this.waitingCheckBox = $('#i_waitingCheck');
            this.promoteBox = $('#i_promote');
            this.passBox = $('#i_pass');
            this.unpassBox = $('#i_unpass');

            this.albumListFun = _.template($("#i_albumList_tpl").html());
            var me = this;
            this.mySwipper = new Swiper('.swiper-container', {
                    loop: false,
                    nextButton: '.next',
                    prevButton: '.prev',
                    onSlideChangeEnd: function(swiper){
                        if(me.currentPicList){
                            me._showInfo(me.currentPicList[swiper.activeIndex]);
                        }
                    }
            });
            this._update();
        },
        _sendTotalRequest: function () {
            ajaxHelper.post("http://" + window.frontJSHost + "/album/counts",
                {}, this, this._renderTotal, null);
        },
        _sendWaitingCheckRequest: function (page) {
            var params = {
                from: page,
                size: this.size,
                type: 1
            };
            this._renderWaitingCheck();
            ajaxHelper.post("http://" + window.frontJSHost + "/album/manage",
                params, this, this._renderWaitingCheck, null);
        },
        _sendPromoteRequest: function (page) {
            var params = {
                from: page,
                size: this.size,
                type: 2
            };
            this._renderPromote();
            ajaxHelper.post("http://" + window.frontJSHost + "/album/manage",
                params, this, this._renderPromote, null);
        },
        _sendPassRequest: function (page) {
            var params = {
                from: page,
                size: this.size,
                type: 3
            };
            this._renderPass();
            ajaxHelper.post("http://" + window.frontJSHost + "/album/manage",
                params, this, this._renderPass, null);
        },
        _sendUnpassRequest: function (page) {
            var params = {
                from: page,
                size: this.size,
                type: 4
            };
            this._renderUnpass();
            ajaxHelper.post("http://" + window.frontJSHost + "/album/manage",
                params, this, this._renderUnpass, null);
        },
        _renderTotal: function (data) {
            data.index = this.currentTag;
            this.tabsBox.html(this.tabsFun({"result": data}));
            var count = 0;
            switch(this.currentTag){
                case 1: 
                    count = data.waitingCheck/this.size+(data.waitingCheck%this.size==0?0:1);
                    this.currentTag = 1;
                    break;
                case 2:
                    count = data.promotion/this.size+(data.promotion%this.size==0?0:1);
                    this.currentTag = 2;
                    break;
                case 3:
                    count = data.passed/this.size+(data.passed%this.size==0?0:1);
                    this.currentTag = 3;
                    break;
                case 4:
                    count = data.notPassed/this.size+(data.notPassed%this.size==0?0:1);
                    this.currentTag = 4;
                    break;
                default:
                    count = data.waitingCheck/this.size+(data.waitingCheck%this.size==0?0:1);
                    this.currentTag = 1;
            }
            $('#i_page').paging(count-1, this.currentPage, {ctx: this}, this.getPage, this.nextPage, this.prevPage);
            this._registEvent();
        },
        _renderWaitingCheck: function (data) {
            this.waitingCheckBox.html(this.albumListFun({"result": data}));
            this._registEvent();
        },
        _renderPromote: function (data) {
            this.promoteBox.html(this.albumListFun({"result": data}));
            this._registEvent();
        },
        _renderPass: function (data) {
            this.passBox.html(this.albumListFun({"result": data}));
            this._registEvent();
        },
        _renderUnpass: function (data) {
            this.unpassBox.html(this.albumListFun({"result": data}));
            this._registEvent();
        },
        _show:function(e){
            var index = $(e.currentTarget).data("index");
            var me = e.data.ctx;
            switch(index){
                case 1: 
                    me.currentTag = 1;
                    break;
                case 2:
                    me.currentTag = 2;
                    break;
                case 3:
                    me.currentTag = 3;
                    break;
                case 4:
                    me.currentTag = 4;
                    break;
                default:
                    me.currentTag = 1;
            }
            me._update(0);
        },
        _update:function(page){
            if(!page){
                page = 0;
            }
            this.currentPage = page+1;
            this._sendTotalRequest();
            switch(this.currentTag){
                case 1: 
                    this._sendWaitingCheckRequest(page);
                    break;
                case 2:
                    this._sendPromoteRequest(page);
                    break;
                case 3:
                    this._sendPassRequest(page);
                    break;
                case 4:
                    this._sendUnpassRequest(page);
                    break;
                default:
                    this._sendWaitingCheckRequest(page);
            }
        },
        _registEvent: function () {
            $("a[data-toggle='tab']").off("click", this._show).on("click", {ctx: this}, this._show);
            $(".j_unpass").off("click", this._notPass).on("click", {ctx: this}, this._notPass);
            $(".j_promote").off("click", this._promote).on("click", {ctx: this}, this._promote);
            $(".j_pass").off("click", this._pass).on("click", {ctx: this}, this._pass);
            $(".j_delete").off("click", this._delete).on("click", {ctx: this}, this._delete);
            $(".j_album_pic_box").off("click", this._detail).on("click", {ctx: this}, this._detail);
            $(".close_btn").off('click',this.hideOverDoxH).on('click',this.hideOverDoxH);
        },
        _notPass:function(e){
            var id = $(e.currentTarget).parents("li").data("id");
            var params = {
                "albumId":id
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/album/unpass",
                params, e.data.ctx, function(){
                    util.showToast("更新成功", this._update());
                }, null);
        },
        _pass:function(e){
            var id = $(e.currentTarget).parents("li").data("id");
            var params = {
                "albumId":id
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/album/pass",
                params, e.data.ctx, function(){
                    util.showToast("更新成功", this._update());
                }, null);
        },
        _delete:function(e){
            var id = $(e.currentTarget).parents("li").data("id");
            var params = {
                "albumId":id
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/album/delete",
                params, e.data.ctx, function(){
                    util.showToast("更新成功", this._update());
                }, null);
        },
        _detail:function(e){
            var id = $(e.currentTarget).parents("li").data("id");
            var params = {
                "id":id
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/album/detail",
                params, e.data.ctx, function(data){
                    $(".over_dox_h").show();
                    var photo = new Array();
                    var selectPhotos = data.picList;
                    for(var i=0; i<data.picList.length; i++){
                        photo.push('<div class="swiper-slide"><img src="' + selectPhotos[i].path + '"></div>');
                    }
                    this.mySwipper.removeAllSlides();
                    e.data.ctx.mySwipper.appendSlide(photo);
                    e.data.ctx.mySwipper.slideTo(0, 0, false);
                    e.data.ctx._showInfo(data.picList[0]);
                    e.data.ctx.currentPicList = data.picList;
                }, null);
        },
        _promote:function(e){
            var id = $(e.currentTarget).parents("li").data("id");
            var params = {
                "albumId":id
            };
            ajaxHelper.post("http://" + window.frontJSHost + "/album/promote",
                params, e.data.ctx, function(){
                    util.showToast("更新成功", this._update());
                }, null);
        },
        hideOverDoxH: function(e) {
            $(".over_dox_h").hide();
        },
        getPage: function (e) {
            var targetNum = $(e.currentTarget).data("id")-1;
            e.data.ctx._update(targetNum);
        },
        nextPage: function (e) {
            var targetNum = $(e.currentTarget).parents("ul").find(".active").data("id");
            e.data.ctx._update(targetNum);
        },
        prevPage: function (e) {
            var targetNum = $(e.currentTarget).parents("ul").find(".active").data("id") - 2;
            e.data.ctx._update(targetNum);
        },
        _showInfo:function(data){
            $("#i_photo_title").text(data.description);
            if(data.region){
                $("#i_photo_region").text(data.region.city + ", " + data.region.country);
                $("#i_photo_regionCN").text(data.region.cityCN + ", " + data.region.countryCN);
            }else{
                $("#i_photo_region").text("");
                $("#i_photo_regionCN").text("");
            }
        }
    };
    return AlbumManagement;
});
