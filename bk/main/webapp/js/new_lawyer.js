define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var NewLawyer = {
        lawyerId : "",
        initialize: function () {
            //nav_bar
            nav_bar.initialize("i_navbar", 4);
            header.initialize("i_header", "律师管理");
            this.lawyerId = util.getQueryParameter("id");
            this.mainBox = $('#i_mainbox');
            this.tplFun = _.template($("#i_tpl").html());
            this._sendRequest();
        },

        _sendRequest: function () {
            if(!!this.lawyerId){
                var params={
                    "id": this.lawyerId
                };
                ajaxHelper.get("http://" + window.frontJSHost + "/api/lawyer/detail",
                    params, this, this._render, null);
            }else{
                this._render({});
            }
            
        },
        _render: function (data) {
            if(data.r){
                for(var c in util.cities){
                    if(util.cities[c].id == data.r.cityId){
                        data.r.cityName = util.cities[c].name;
                    }
                }
            }
            this.mainBox.html(this.tplFun({"result":data, "cities": util.cities}));
            this._registEvent();
        },
        _registEvent: function () {
            $("#i_selAuth li").off("click",this._sel).on("click",{ctx: this},this._sel);
            $("#i_city li").off("click",this._sel).on("click",{ctx: this},this._sel);
            $("#i_save_btn").off("click", this._doSave).on("click",{ctx: this}, this._doSave);
            $("#i_cancel_btn").off("click",this._doCancel).on("click",{ctx: this},this._doCancel);
            
            $("[data-toggle='popover']").off({
                'focus': p_destroy,
                'checkis': input_check
            }).on({
                'focus': p_destroy,
                'checkis': input_check
            });
        },
        _sel:function(e){
            var value = $(this).data("value");
            var text = $(this).text();
            $(this).closest("ul").parents(".btn-group").data("value", value).find("button").eq(0).text(text);
        },
        _doSave:function(e){
            var formList = $("#i_typeForm");
            $("input,textarea").trigger('checkis');
            if ($(".popover.in").length < 1) { 
                var formJson = {};
                formJson.name = $("#i_name").val();
                formJson.loginName = $("#i_login_name").val();
                formJson.password = $("#i_login_password").val();
                formJson.phoneNum = $("#i_phone").val();
                formJson.cityId = $("#i_city").data("value");
                formJson.memo = $("#i_memo").val();

                if (e.data.ctx.lawyerId) { 
                    formJson.id=e.data.ctx.lawyerId;
                    ajaxHelper.post("http://" + window.frontJSHost + "/api/lawyer/create",
                        formJson, e.data.ctx, function(){
                            util.showToast("更新成功！", function(){
                                window.location = "lawyer_management.html";
                            })
                        });
                } else {
                    ajaxHelper.post("http://" + window.frontJSHost + "/api/lawyer/create",
                        formJson, e.data.ctx, function(){
                            util.showToast("保存成功！", function(){
                                window.location = "lawyer_management.html";
                            })
                        });
                }

                
            }

        },
        _doCancel:function(e){
            window.location = "lawyer_management.html";
        }

    };
    return NewLawyer;
});
