
var addressInit = function (_cmbProvince, _cmbCity, _cmbArea, provinceList,codeId) {
    var s1 = $(_cmbProvince), s2 = $(_cmbCity), s3 = $(_cmbArea), xmls = '', id1, id2, id3, returnId = typeof arguments[4] == "string" ? arguments[4] : null, arr, ind1, ind2, ind3;
    for (var i = 0; i < provinceList.children.length; i++) {
        arr = provinceList.children[i].name.split('_');
        xmls += '<li class=d' + arr[1] + ' data-id=' + arr[1] + ' >' + arr[0] + '</li>';
    }
    s1.html(xmls).find('li').on('click', getCity);
    function getCity(e) {
        id1 = $(e.target).data('id');
        selectIt(e);
        setCity();
    }

    function getArea(e) {
        id2 = $(e.target).data('id');
        selectIt(e);
        setArea();
    }

    function getAdress(e){
        id3 = $(e.target).data('id');
        selectIt(e);
        setAdress();
    }
    function setCity(){
        var _cityArr;
        s1.data('id',id1);
        ind1 = $('.d'+id1).index();
        if(ind1 < 0){
            ind1 = 0;
        }
        xmls='';
        for(var i = 0; i < provinceList.children[ind1].children.length; i++){
            _cityArr = provinceList.children[ind1].children[i].name.split('_');
            xmls += '<li class=d'+ _cityArr[1] + ' data-id=' + _cityArr[1] +' >'+ _cityArr[0] + '</li>';
        }
        s2.html(xmls).find('li').off('click',getArea).on('click',getArea);
        s3.html('');
    }
    function setArea(){
        var _areaArr;
        s2.data('id',id2);
        ind2 = $('.d'+id2).index();
        if(ind2 < 0){
            ind2 = 0;
        }
        xmls='';
        for(var i = 0; i < provinceList.children[ind1].children[ind2].children.length; i++){
            _areaArr = provinceList.children[ind1].children[ind2].children[i].name.split('_');
            xmls += '<li class=d'+ _areaArr[1] + ' data-id=' + _areaArr[1] + ' >'+ _areaArr[0] + '</li>';
        }
        s3.html(xmls);
        if(s3.find('li').length > 0){
            s3.html(xmls).find('li').on('click',getAdress);
        }else{
            s3.data('id',id2);
        }
    }

    function setAdress() {
        s3.data('id', id3);
        $('#address_detail').val(id3);
    }

    function selectIt(e) {
        var val = $(e.target).text(), par = $(e.target).parents('.btn-group');
        par.find('button:eq(0)').text(val);
        par.nextAll('.btn-group').attr('data-id', '0').find('button:eq(0)').text('选择');
    }

    if (returnId != null) {
        var arr = codeId.split('.');
        if (arr.length > 2) {
            id1 = arr[0];
            id2 = arr[1];
            id3 = arr[2];
            setCity();
            setArea();
            setAdress();
            s1.data('id', id1).parents('.btn-group').find('.j_default').text($('.d' + id1).text());
            s2.data('id', id2).parents('.btn-group').find('.j_default').text($('.d' + id2).text());
            s3.data('id', id3).parents('.btn-group').find('.j_default').text($('.d' + id3).text());
        } else {
            s1.data('id', '').parents('.btn-group').find('.j_default').text('选择');
            s2.data('id', '').parents('.btn-group').find('.j_default').text('选择');
            s3.data('id', '').parents('.btn-group').find('.j_default').text('选择');
        }
    }
    returnId = id1 + id2 + id3;
};
