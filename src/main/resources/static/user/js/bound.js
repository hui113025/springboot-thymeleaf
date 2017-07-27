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


    function onRegisterSuccess() {
        $('#container').html('');
        $('#register-success-btn').click(function () {
            onRegisterSuccessAfter();
        });
        $(".succeed").fadeIn(500, function () {
            var time = 3;
            var task = setInterval(function () {
                if (time > 0) {
                    $('#register-success-btn').val('确定(' + time + ')');
                } else {
                    clearInterval(task);
                    onRegisterSuccessAfter();
                }
                time--;
            }, 1000);
        });
    }

    function onRegisterSuccessAfter() {
        if (location.pathname == '/plan') {
            goBuy();
        } else {
            location.href = location.href.indexOf('?') == -1 ? location.href : (location.href.substring(0, location.href.indexOf('?')));
        }
    }

    //绑定各种事件
    function bindEvents() {
        Util.onEnter(function () {
            $("#phone-register-btn").click();
        });
        $(".register-top li").click(function () {
            $(this).css("border-bottom", "2px solid #5583db").siblings().css("border-bottom", "2px solid #ffffff")
            var ka = $(this).index();
            if (ka == 0) {
                $(document).unbind('onkeydown');
                Util.onEnter(function () {
                    $("#phone-register-btn").click();
                });
                $(".phong-Control").fadeIn(500)
                $(".email-Control").fadeOut(100)
                $(".alert").css("height", "450px")
            } else if (ka == 1) {
                $(document).unbind('onkeydown');
                Util.onEnter(function () {
                    $("#email-register-btn").click();
                });
                $(".email-Control").fadeIn(500)
                $(".phong-Control").fadeOut(100)
                $(".alert").css("height", "400px")
            }
            //切换注册方式时刷新验证码
            /* setTimeout(function () {
             $('.code-img img').attr('src', '/captcha/images');
             }, 200);*/
        });

        validateFormBinds();

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

            if (pass) {
                var data = Util.fromToJson('phone-form');
                data.isPhone = true;
                data.id = Util.getRequestParam('id');
                data.info = Util.getRequestParam('info');
                $.post('/user/perfection', data).done(function (result) {
                    if (result.success) {
                        onRegisterSuccess();
                    } else {
                        $('#sms-captcha').siblings('.positioning').html(result.message);
                    }
                })
            }
        });

        Util.sendSms('sms-btn', 1);


        function validateFormBinds() {
            validateBind('phone', function (val, tips) {
                $('#images-captcha').siblings('.positioning').html('');
                if (val == '') {
                    tips.html('请输入手机号');
                } else if (!/^(13[0-9]|17[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/.test(val)) {
                    tips.html('请输入有效的手机号');
                } else {
                    $.getJSON('/user/validate/exists/mobile', {moblie: val}).done(function (exists) {
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

            validateBind('images-captcha', function (val, tips) {
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


})(jQuery);