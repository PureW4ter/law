define([], function () {
  var Validate = {
    isEmail: function (text) {
      var reg = /^(?:\w+\.?)*\w+@(?:\w+\.?)*\w+$/;
      return reg.test(text);
    },
    isPassword: function (text) {
      var reg = /^[a-zA-Z0-9]{6,20}$/;
      return reg.test(text);
    },
    isMobile: function (text) {
      var reg = /^(1[3-8][0-9])\d{8}$/;
      return reg.test(text);
    },
    isChinese: function (text) {
      var reg = /^[\u4e00-\u9fff]{0,}$/;
      return reg.test(text);
    },
    isEnglish: function (text) {
      var reg = /^[A-Za-z]+$/;
      return reg.test(text);
    },
    isZip: function (text) {
      var reg = /^[1-9]\d{5}$/;
      return reg.test(text);
    },
    isDate: function (text) {
      //yyyyMMdd格式正则加入润年2月
      var reg = /^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)$/;
      if (!reg.test(text)) {
        return false;
      }
      return true;
    },
    isNum: function (text) {
      var reg = /^\d+$/;
      return reg.test(text);
    },
    isIDCardNo: function (text) {
      var reg = /^[A-Za-z0-9]+$/;
      return reg.test(text);
    },
    isEnglishAndSpace: function (text) {
      var reg = /^([a-zA-Z ]+|[\u4e00-\u9fa5]+)$/;
      return reg.test(text);
    },
  };
  return Validate;
});