/**
 * 注册页面脚本
 * @author
 * @date 2016年6月28日
 */
(function ($) {


    $(document).ready(function () {
        bindEvents();
    })


    //表单字段验证bind util
    function validateBind(id, callback) {
        $('#' + id).blur(function () {
            var tips = $(this).siblings('.positioning');
            var val = $.trim($(this).val());
            if (val == $(this).attr('data-tag')){
                val = '';
            }
            if (val != '') {
                tips.html('');
            }
            callback(val, tips);
        });
    }



    function onRegisterSuccess(){
        $('#container').html('');
        $('#register-success-btn').click(function () {
            onRegisterSuccessAfter();
        });
        $(".succeed").fadeIn(500,function () {
            var time = 3;
            var task = setInterval(function () {
                if (time > 0){
                    $('#register-success-btn').val('确定('+time+')');
                }else{
                    clearInterval(task);
                    onRegisterSuccessAfter();
                }
                time--;
            }, 1000);
        });
    }

    function onRegisterSuccessAfter() {
        location.reload();
    }
    //绑定各种事件
    function bindEvents() {
        Util.onEnter(function () {
            $("#phone-register-btn").click();
        });

        validateFormBinds();

        $('.user-protocol').click(function () {
                if ($(this).prop('checked')){
                    $(this).siblings('.positioning').html('');
                }else{
                    $(this).siblings('.positioning').html('请确认您已阅读并同意该协议');
                }
        });

        $("#phone-register-btn").click(function () {
            $('#phone-form input').each(function () {
                if ($(this).val() == '' || $(this).val() == $(this).attr('data-tag')) {
                    $(this).blur();
                    return false;
                }
            });
            var pass = true;
            $('#phone-form .positioning').each(function (key, val) {
                if ($(val)[0].innerText != '')
                    pass = false;
            });
            if (!$('#user-protocol-phone').prop('checked')){
                $('#user-protocol-email').siblings('.positioning').html('请确认您已阅读并同意该协议');
                pass = false;
            }
            if (pass) {
                var data = Util.fromToJson('phone-form');
                data.isPhone = true;
                $.post('/user/register', data).done(function (result) {
                    if (result.state == 200) {
                        onRegisterSuccess();
                    } else {
                        $('#sms-captcha').siblings('.positioning').html(result.stateInfo);
                    }
                })
            }
        });
        $("#email-register-btn").click(function () {
            var pass = true;
            $('#email-form input').each(function () {
                if ($(this).val() == '' || $(this).val() == $(this).attr('data-tag')) {
                    $(this).blur();
                    return false;
                }
            });
            $('#email-form .positioning').each(function (key, val) {
                if ($(val)[0].innerText != '')
                    pass = false;
            });
            if (!$('#user-protocol-email').prop('checked')){
                $('#user-protocol-email').siblings('.positioning').html('请确认您已阅读并同意该协议');
                pass = false;
            }
            if (pass) {
                var data = Util.fromToJson('email-form');
                data.isPhone = false;
                $.post('/user/register', data).done(function (result) {
                    if (result.state == 200) {
                        onRegisterSuccess();
                    } else {
                        $('#email-images-captcha').siblings('.positioning').html(result.stateInfo);
                    }
                })
            }

        })

        Util.sendSms('sms-btn', 1);

        function validateFormBinds() {
            validateBind('phone', function (val, tips) {
                if (val == '') {
                    tips.html('请输入手机号');
                } else if (!/^(13[0-9]|17[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/.test(val)) {
                    tips.html('请输入有效的手机号');
                } else {
                    $.post('/user/validate/exists/mobile', {moblie: val}).done(function (exists) {
                        if (exists)tips.html('该手机号已被注册');
                    })
                }
            });

            validateBind('phone-password', function (val, tips) {
                if (val == '') {
                    tips.html('请输入密码');
                } else if (!/^.{6,18}$/.test(val)) {
                    tips.html('密码长度为6~18位');
                }
            });

            //IE8情况下使用Placeholder插件绑定实际的INPUT
            $('#phone-password').siblings('input')[0] && $('#phone-password').siblings('input').blur(function () {
                var tips = $(this).siblings('.positioning');
                var val = $.trim(this.value);
                if (val == '') {
                    tips.html('请输入密码');
                } else if (!/^.{6,18}$/.test(val)) {
                    tips.html('密码长度为6~18位');
                }else{
                    tips.html('');
                }
            });

            validateBind('email-password', function (val, tips) {
                if (val == '') {
                    tips.html('请输入密码');
                } else if (!/^.{6,18}$/.test(val)) {
                    tips.html('密码长度为6~18位');
                }
            });
            validateBind('images-captcha', function (val, tips) {
                if (val == '') {
                    tips.html('请输入验证码');
                }
            });
            validateBind('email-images-captcha', function (val, tips) {
                if (val == '') {
                    tips.html('请输入验证码');
                }
            });
            validateBind('sms-captcha', function (val, tips) {
                if (val == '') {
                    tips.html('请输入验证码');
                }
            });
        }

    }


})(jQuery)
$(".phone-number input").keyup(function(){
    var bs = $(".phone-number input").val();
    if(bs!=""){
        $(".phone-number").css("background","#fff");
        $(".phone-number input").css("background","#fff")
    }
});
$(".password-number input").keyup(function(){
    var bs = $(".password-number input").val();
    if(bs!=""){
        $(".password-number").css("background","#fff");
        $(".password-number input").css("background","#fff")
    }
});
$(".code-number input").keyup(function(){
    var bs = $(".code-number input").val();
    if(bs!=""){
        $(".code-number").css("background","#fff");
        $(".code-number input").css("background","#fff")
    }
});

$(".note-number input").keyup(function(){
    var bs = $(".note-number input").val();
    if(bs!=""){
        $(".note-number").css("background","#fff");
        $(".note-number input").css("background","#fff")
    }
});

$(".email input").keyup(function(){
    var bs = $(".email input").val();
    if(bs!=""){
        $(".email").css("background","#fff");
        $(".email input").css("background","#fff")
    }
});

$(".email-pass input").keyup(function(){
    var bs = $(".email-pass input").val();
    if(bs!=""){
        $(".email-pass").css("background","#fff");
        $(".email-pass input").css("background","#fff")
    }
});

$(".code-numberO input").keyup(function(){
    var bs = $(".code-numberO input").val();
    if(bs!=""){
        $(".code-numberO").css("background","#fff");
        $(".code-numberO input").css("background","#fff")
    }
})

