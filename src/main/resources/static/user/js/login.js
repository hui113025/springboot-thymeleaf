/**
 * 登录页面脚本
 * @author
 * @date 2016年6月28日 14:49:17
 */
(function ($) {


    $(document).ready(function () {
        bindEvents();
    })
    //表单字段验证bind
    function validateBind(id, callback) {
        $('#' + id).blur(function () {
            var tips = $(this).siblings('.positioning');
            var val = $.trim($(this).val());
            if (val != '')
                tips.html('');

            if (typeof callback == 'function')
                callback(val, tips);
        });
    }


    function validateFormBind() {
        validateBind('login-username');

        //IE8情况下使用Placeholder插件绑定实际的INPUT
        var isIE8 = $('#login-password').siblings('input')[0];
        if (isIE8){
            $('#login-password').siblings('input').blur(function () {

                var tips = $(this).siblings('.positioning');
                var val = $.trim(this.value);
                if (val == '') {
                    tips.html('请输入密码');
                } else if (!/^.{6,18}$/.test(val)) {
                    tips.html('密码长度为6~18位');
                }else{
                    tips.html('');
                }
            })
        }else{
            validateBind('login-password', function (val, tips) {
                if (val == '') {
                    tips.html('请输入密码');
                } else if (!/^.{6,18}$/.test(val)) {
                    tips.html('密码长度为6~18位');
                }
            });
        }

        $('#login-username').keydown(function () {
            if ($('#login-password').val() != '') {
                $('#login-password').val('');
            }
        });
    }

    //绑定各种事件
    function bindEvents() {
        validateFormBind();
        $('#login-register-btn').click(function () {
            Util.Modal.show('/modal/register.html');
        });
        $('#login-password-btn').click(function () {
            Util.Modal.show('/modal/password.html');
        });

        $('#duiaLoginBtn').click(function () {
            var backUrl = location.protocol + '//'+location.host;
            var loginBackUrl = Util.getRequestParam('login')
            if (loginBackUrl){
                backUrl += loginBackUrl;
            }else{
                backUrl += location.pathname;
            }
            location.href = '/login/duia?backUrl='+backUrl;
        });

    }
})(jQuery)


function login() {
    var backUrl = encodeURIComponent(window.location);
    window.location.href = '/login/duia?backUrl=' + backUrl;
}


$(".phone input").keyup(function () {
    var bs = $(".phone input").val();
    if (bs != "") {
        $(".phone").css("background", "#fff");
        $(".phone input").css("background", "#fff")
    }
})


$(".password input").keyup(function () {
    var bs = $(".password input").val();
    if (bs != "") {
        $(".password").css("background", "#fff");
        $(".password input").css("background", "#fff")
    }
});
