//toast
$("body").append('<div id="i_toast_mask" class="toast_mask"></div>');
$("body").append('<div id="i_toast_content" class="toast_content"></div>');
//loading
$("body").append('<div id="i_loaing_mask" class="loading_mask"></div>');
$("body").append('<div id="i_loading_content" class="loading_content">' +
    '<div class="loading_icon"><div class="busyIcon"><div class="container">' +
    '<div class="spinner"><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span></div></div></div></div></div>');
//---------------------------------------------------------------------------------------
//initPage
var path = location.pathname;
require([path.substring(path.lastIndexOf("/")+1, path.indexOf(".html"))], function(o){
    o.initialize();
});