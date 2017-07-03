"use strict";

define(['text!./header.html'], function (tempHtml) {
    var Header = {
        initialize: function (divId, title) {
            //为了能够解析，插入
            $("html").append(tempHtml);
            this.headertplfun = _.template($("#i_header_tpl").html());
            //使用完成后，删除
            $("#i_header_tpl").remove();
            var userData= $.parseJSON(localStorage.userInfo);
            $("#" + divId).html(this.headertplfun({"nickName": userData.name, "title": title}));
            this._registEvent();
        },
        _registEvent: function () {
            $('#i_logOut').off('click', this._cancelSuccess).on('click', this._cancelSuccess);
            $('#i_reset').off('click', this._reset).on('click', this._reset);
        },
        _cancelSuccess: function(data){
            localStorage.removeItem('userInfo');
            window.location = 'login.html';
        },
        _reset: function(data){
            window.location = 'reset.html';
        }
    };
    return Header;
});