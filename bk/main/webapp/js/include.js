window.frontJSVersion = "{front_version}";
//window.frontJSHost = "{frontJSHost}";
window.frontJSHost = "localhost:8080";
 window.qiniuDomain = "http://7xlovk.com2.z0.glb.qiniucdn.com/";

document.write('<script data-main="js/app.js?ver='+ window.frontJSVersion + '" src="js/lib/require.js" type="text/javascript"></script>');
document.write('<script src="js/lib/jquery.js" type="text/javascript"></script>');
document.write('<script src="js/lib/bootstrap.js" type="text/javascript"></script>');
document.write('<script src="js/lib/underscore.js" type="text/javascript"></script>');
document.write('<script src="js/lib/iscroll.js" type="text/javascript"></script>');
document.write('<script src="js/lib/page.js" type="text/javascript"></script>');

//页面的初始化程序
document.write('<script src="js/init.js?ver='+ window.frontJSVersion + '" type="text/javascript"></script>');
document.write('<script src="js/form.js?ver='+ window.frontJSVersion + '" type="text/javascript"></script>');
