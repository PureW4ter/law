({
    appDir: './',
    baseUrl: './js',
    dir: './dist',
    optimize: "uglify",
    modules: [],
    paths: {
        zepto: 'lib/zepto',
        underscore: 'lib/underscore',
        text: 'lib/text',
        iscroll: 'lib/iscroll',
    },
    fileExclusionRegExp: /^(r|build)\.js$/,
    optimizeCss: 'standard',
    removeCombined: true,
})