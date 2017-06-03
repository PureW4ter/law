define(['ajaxhelper', 'utility', 'component/time_button'], function(ajaxHelper, util, timeBtn) {
    var Regist = {
        initialize: function() {
           timeBtn.initialize("i_getcode_btn");
        },
    };
    return Regist;
});