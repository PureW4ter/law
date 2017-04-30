requirejs.config({
    //默认情况下模块所在目录为js
    baseUrl: './js',
    urlArgs: 'ver='+ window.frontJSVersion,
    paths: {
        text: 'lib/text'
    },
});
