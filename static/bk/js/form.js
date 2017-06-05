function getCode(getCheckMsg){
    if(typeof getCheckMsg != 'undefined'){
        /*验证码倒计时*/
        var send_time,that = $(getCheckMsg.obj1),
            count,show_msg = $(getCheckMsg.obj2),
            num = 60,oldtime,nowtime,setInter,
            msg1 = getCheckMsg.msg1,
            msg2 = getCheckMsg.msg2,
            msg3 = getCheckMsg.msg3;
        that.off('set',countdown).on('set',countdown);
        if(/time/.test(document.cookie)){
            oldtime = parseFloat(getCookie('time'));
            nowtime = parseFloat(new Date().getTime().toString());
            if(nowtime < (oldtime + 60000)){
                count = parseFloat(new Date().getTime());
                num = 60 - Math.floor((count - oldtime)/1000);
                that.text(num+msg1).off('set',countdown);
                show_msg.text(msg2);
                setInter =setInterval(function(){
                    count = parseFloat(new Date().getTime());
                    if(count < (oldtime + 60000)){
                        num = 60 - Math.floor((count - oldtime)/1000);
                        that.text(num+msg1);
                    }else{
                        delCookie('time');
                        clearInterval(setInter);
                        that.text(msg3).on('set',countdown);
                    }
                },1000);
            }
        }
        function countdown(){
            show_msg.text(msg2);
            send_time = parseFloat(new Date().getTime());
            num = 60;
            setCookie('time',send_time);
            that.text(num+msg1).off('set',countdown);
            setInter = setInterval(function(){
                count = parseFloat(new Date().getTime());
                if(count < (send_time + 60000)){
                    num = 60 - Math.floor((count - send_time)/1000);
                    that.text(num+msg1);
                }else{
                    delCookie('time');
                    clearInterval(setInter);
                    that.text(msg3).on('set',countdown);
                }
            },1000);
        }
    }
}
/*表单失去焦点验证*/
function input_check(){
    var t = $(this),
        pat = eval("(" + t.attr('data-pattern')+")"),
        reg = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]+}/im,
        msg = t.attr('data-keep'),
        content;
    if((!t.is('input'))&&(!t.is('textarea'))){
        content= t.text();
    }else{
        content= t.val();
    }
    if(!t.hasClass('j_cannull')){
        if(( !pat.test(content)) && (!reg.test(content) ) ){
            if(content.length <1){
                t.attr('data-content','不能为空');
            }else{
                t.attr('data-content',msg);
            }
            t.popover('show').addClass('on');
        }else{
            t.popover('destroy').removeClass('on');
        }
    }else{
        if(!pat.test(content) && content){
            t.popover('show').addClass('on')
        }else{
            t.popover('destroy').removeClass('on');
        }
    }
}
function setpopover(){
    var popover = $('.popover'), arrow = $('.arrow'), len = popover.length;
    for(var i = 0; i < len; i++){
        popover.eq(i).css({'top':(arrow.eq(i).offset().top - 10)});
    }
    arrow.css('top','16px');
}
/*表单提示摧毁*/
function p_destroy(e){
    $('.popover:not(.in)').remove();
    $(this).popover('destroy');
    $('.popover:not(.in)').addClass('d_ind');
}
/*刷新*/
function reloading(){
 window.reload();
}

/*验证相同密码*/
   function reCheck(cla){
      if($('input').hasClass(cla)){
          var obj = $('.'+cla);
          obj.eq(1).off().on('blur', function () {
              var val = $(this).val();
              if(val !== obj.eq(0).val()){
                  $(this).attr('data-content','二次输入密码错误');
                  $(this).popover('show').addClass('on');
              }else{
                  $(this).popover('destroy').removeClass('on');
              }
          });
      }
   }
/*重新设置表单*/
function reset(){
    $('.popover').popover('destroy');
    $('input:not(:radio):not(:checkbox)').val('');
    $('input:checkbox').prop("checked",false);
    $('.radios').prop('checked', true);
    $('#Select3').data('id','');
    $(".j_button").text("请选择");
    $('textarea').val('');
    $(this).trigger('removeImg');
}
function clearPopover(){
    $('.popover ').remove();
}
function _check_tel(e){
    var that = $(e.target).parents('.j_tel_no').siblings('.j_tel_hd').find('input');
    var reg = eval("(" + that.data('pattern')+")");
    var it_reg = eval("(" + $(e.target).data('pattern')+")");
    if(!that.hasClass('j_cannull')){
        if(!reg.test(that.val())){
            if(!$(e.target).hasClass('on')){
                $(e.target).popover('show');
            }
        }else{
            $(e.target).popover('hide');
        }
    }else{
        if($(e.target).val() || that.val()){
            if(!(reg.test(that.val()) && it_reg.test($(e.target).val()))){
                if(!$(e.target).hasClass('on')){
                    $(e.target).popover('show');
                }
            }else{
                $(e.target).popover('hide');
            }
        }
    }
}
//function _return_msg(e, obj){
//    var j_tel_no , nopat;
//    if(typeof obj == 'object'){
//        j_tel_no = obj.parents('.j_tel_no').siblings('.j_tel_hd').find('input');
//        if(!j_tel_no.hasClass('noIm')){
//            nopat = eval("(" + j_tel_no.data('pattern')+")");
//            if(!nopat.test(j_tel_no.val())){
//                obj.popover('show')
//            }
//        }else{
//            if(j_tel_no.val() || obj.val()){
//                nopat = eval("(" + j_tel_no.data('pattern')+")");
//                if(!nopat.test(j_tel_no.val())){
//                    obj.popover('show')
//                }
//            }
//        }
//    }else{
//        j_tel_no = $(e.target);
//        nopat = eval("(" + j_tel_no.data('pattern')+")");
//        if(!j_tel_no.hasClass('noIm')){
//            if(nopat.test(j_tel_no.val())){
//                j_tel_no.parents('.j_tel_hd').siblings('.j_tel_no').find('input').popover('hide');
//            }
//        }else{
//            if(j_tel_no.val() || j_tel_no.parents('.j_tel_no').siblings('.j_tel_hd').find('input').val()){
//                if(nopat.test(j_tel_no.val())){
//                    j_tel_no.parents('.j_tel_hd').siblings('.j_tel_no').find('input').popover('hide');
//                }
//            }
//        }
//    }
//}
/*刷新页面*/
function reload(){
    window.location.reload();
}
function setCookie(name,value){
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
function getCookie(name){
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
