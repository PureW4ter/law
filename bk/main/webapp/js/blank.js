define(['component/nav_bar','component/header', 'ajaxhelper', 'utility'], function (nav_bar, header, ajaxHelper, util) {
    var Blank = {
        initialize: function () {
            nav_bar.initialize("i_navbar", 1);
            header.initialize("i_header", "登录成功");
        }
    };
    return Blank;
});