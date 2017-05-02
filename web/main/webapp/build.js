({
    appDir: './',
    baseUrl: './js',
    dir: './dist',
    optimize: "uglify",
    modules: [
        {
            name: 'main'
        }
    ],
    paths: {
    },
    fileExclusionRegExp: /^(r|build)\.js$/,
    optimizeCss: 'standard',
    removeCombined: true,
})