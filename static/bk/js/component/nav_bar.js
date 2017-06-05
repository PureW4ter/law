"use strict";

define(['text!./nav_bar.html','../utility'], function (tempHtml,util) {
    var Navbar = {
        initialize: function (divId, nav_select) {
            //为了能够解析，插入
            $("html").append(tempHtml);
            this.navbartplfun = _.template($("#i_navbar_tpl").html());
            //使用完成后，删除
            $("#i_navbar_tpl").remove();
            $("#" + divId).html(this.navbartplfun({"nav_select": nav_select}));
            //util.checkAuth();
            this._registEvent();
        },
        _registEvent: function () {
            $(".i_tree_level ").off("click", this._list).on("click", {ctx: this}, this._list);
        },
        _list: function (e) {
            $(e.target).next().toggle();
            $(e.target).find("span").toggleClass("right-icon");
        },
    };
    return Navbar;
});