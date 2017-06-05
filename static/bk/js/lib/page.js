(function ($) {
    $.fn.extend({
        "paging": function (n, f, json, fun1, fun2, fun3) {
            this.html(page.html_ul);
            var i = n > 6 ? (f < (n - 5) ? (f < 6 ? 0 : f - 5) : (n - 10)) : 0, html_page = '', t, s = 0;
            if (n > 10) {
                for (var j = 0; j < 10; j++) {
                    i++;
                    html_page += getPage(i);
                }
                if (f < (n - 5) && f > 5) {
                    t = 5;
                } else if (f > (n - 6)) {
                    t = 10 - (n - f);
                } else {
                    t = f;
                }
            } else {
                for (var k = 0; k < n; k++) {
                    s++;
                    html_page += getPage(s);
                }
                t = f;
            }
            if (n > 1) {
                this.find('.pagination').html(page.prev + html_page + page.next);
            }
            var prev = this.find('.prev'), next = this.find('.next'), it_pages = this.find('.j_pages');
            it_pages.eq(t - 1).addClass('active');
            prev.hide();
            if (f == 1) {
                prev.hide();
            } else {
                prev.show();
            }
            if (n == f) {
                next.hide();
            } else {
                next.show();
            }
            json = typeof json == "object" ? json : {ctx: null};
            it_pages.off('click', fun1).on('click', json, fun1);
            next.off('click', fun2).on('click', json, fun2);
            prev.off('click', fun3).on('click', json, fun3);
        }
    });
    var page = {
        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
        html_ul: '<ul class="pagination"></ul>'
    };

    function getPage(i) {
        return '<li class="j_pages" data-id=' + i + '><a href="javascript:void(0);">' + i + '</a></li>'
    }
})(window.jQuery);

