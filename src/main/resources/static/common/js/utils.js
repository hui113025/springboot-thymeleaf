/**
 * Created by 李恒名 on 2016/6/28.
 * 工具包
 */
(function (window, $) {
    //封装的模态框对象
    var Modal = {
        show: function modal(url) {
            $.get(url).done(function (html) {
                layer.closeAll();
                layer.open({
                    offset: '40%',
                    type: 1,
                    closeBtn: 0,
                    shadeClose: false,
                    content: html
                });
                $(".alert").css("height", "450px")
                $('.layui-layer-shade').mouseleave(function(){
                    $(this).click(function (){
                        $('.close-btn').click();
                    });
                });
            });
        }
    }

    /**
     * 将表单序列化为json对象
     * @param 表单的id
     * @returns {{}}
     */
    function fromToJson(form) {
        var result = {};
        var fieldArray = $('#' + form).serializeArray();
        for (var i = 0; i < fieldArray.length; i++) {
            var field = fieldArray[i];
            if (field.name in result) {
                result[field.name] += ',' + field.value;
            } else {
                result[field.name] = field.value;
            }
        }
        return result;
    }


    var User = {

        login: login,
        indexLogin: function () {
            this.login(function (user) {
                location.href = location.href.indexOf('?') == -1 ? location.href : (location.href.substring(0, location.href.indexOf('?')));
                if (location.href.indexOf('?') == -1) location.reload();
            });
        }
    }

    /**
     * 通用的登录方法，调用该方放将在调用页面弹出登录框
     * @param callback  回掉函数，登录成功后执行的方法，会将user对象传入，可以进下一步操作，一般情况下传入function(){location.reload()}刷新页面即可。
     */
    function login(callback) {
        $.get('/modal/login.html').done(function (html) {
            layer.closeAll();
            layer.open({
                offset: '40%',
                type: 1,
                closeBtn: 0,
                shadeClose: false,
                content: html
            });
            $('#login-btn').click(function () {
                if ($.trim($('#login-username').val()) == '' || $.trim($('#login-username').val()) == $('#login-username').attr('data-tag')) {
                    $('#login-username').siblings('.positioning').html('请输入账号');
                    $('#login-username').focus();
                }
                else if ($.trim($('#login-password').val()) == '' || $.trim($('#login-password').val()) == '请输入密码' || $.trim($('#login-password').val()) == $('#login-password').attr('data-tag')) {
                    $('#login-password').siblings('.positioning').html('请输入密码');
                    $('#login-username').focus();
                } else {
                    var data = Util.fromToJson('login-form');
                    $.post('/user/login/auth', data).done(function (result) {
                        if (result.state == 200) {
                            if (typeof callback == 'function')
                                callback(result.resInfo);
                            layer.closeAll();
                        } else {
                            $('#login-username').siblings('.positioning').html(result.stateInfo);
                        }
                    })
                }
            });
            //回车登录
            onEnter(function () {
                $('#login-btn').click();
            });
            $('.layui-layer-shade').mouseleave(function(){
                $(this).click(function (){
                    $('.close-btn').click();
                });
            });
        });
    }

    function getRequestParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }

    /**
     * 初始化 jQuery File Upload 上传文件插件
     * @param fileId
     * @param previewId  预览图id
     * @param savePathInputId  保存图片路径的input id
     * @param saveDirName  服务器上保存图片的目录
     */
    function initFileupload(fileId, previewId, savePathInputId, saveDirName, callback) {
        $('#' + fileId).fileupload({
            url: '/file/upload/images?dirName=' + saveDirName,
            done: function (e, data) {
                if (data.result.state == 200) {
                    var path = data.result.resInfo.url;
                    $('#' + previewId).attr('src', path);
                    $('#' + savePathInputId).val(path);
                    (typeof callback == 'function' && callback())
                } else {
                    var alert = layer.alert || window.alert;
                    layer.alert(result.result.stateInfo);
                }
            }
        });
    }

    /**
     * 页面右侧浮动
     */

    function rightFloat() {
        //alert(document.cookie.split(";")[0])
       if(document.cookie.split(";")[0]!="superh"){
           $(".toolbar").css("display","block")

       }
       if(document.cookie.split(";")[0]=="superh"){
           $('.too-ewm em').css("display","none")
           $(".too-ewm").css("background","#444851")
           $('.too-ewm em small').css("display","none");
           $(".res").removeClass("end-res")
           $('.too-ewm em').attr("data-end","1");
           $(".toolbar").css("display","block")
       }
        $('.too-ewm em small').click(function(){
            $('.too-ewm em').css("display","none")
            $('.too-ewm em small').css("display","none");
            $('.too-ewm em').attr("data-end","1");
            $(".too-ewm").css("background","#444851")
            $(".res").removeClass("end-res")
            document.cookie="superh";

        })

            $('.too-ewm').hover(function () {
                if($('.too-ewm em').attr("data-end")==1) {
                    $('.too-ewm em').css("display","block")
                $(".res").addClass("end-res")
            }
            }, function () {
                if($('.too-ewm em').attr("data-end")==1) {
                    $('.too-ewm em').css("display","none")
                    $(".res").removeClass("end-res")
                }
            });

        $('.too-xn').hover(function () {
            if($('.too-ewm em').attr("data-end")==0){
            $('.too-ewm em').stop().animate({'opacity': 'hide'}, 'fast');
            $('.too-ewm em small').css("display","none");
                $('.too-ewm em').attr("data-end","1")
            }
            $('.too-xn em').css("display","block")

        }, function () {
            $('.too-xn em').css("display","none")
        });

        $('.too-scrollT').on('click', function () {
            $('body,html').animate({scrollTop: 0})
        });
    }

    //点击回车调用方法
    function onEnter(callback) {
        window.document.onkeydown = function (e) {
            var ev = document.all ? window.event : e;
            ev.keyCode == 13 && callback();
        }
    }

    //设置导航栏颜色
    function NavbarCss() {
        var currentUrl = window.location.href;
        var s = currentUrl.lastIndexOf('/') + 1;
        var e = currentUrl.lastIndexOf('?') != -1 ? currentUrl.lastIndexOf('?') : currentUrl.length;
        var cssObj = currentUrl.substring(s, e);
        $('#' + cssObj + ' a').css('color', '#5583db');
    }

    //获取当前时间,适用各种浏览器,格式如2011-08-03 09:15:11
    function getTime() {
        var time = new Date();
        var y = time.getFullYear();
        var M = time.getMonth();
        var d = time.getDate();
        var h = time.getHours();
        var m = time.getMinutes();
        var s = time.getSeconds();

        M = (M < 10 ? ('0' + M) : M);
        d = (d < 10 ? ('0' + d) : d);
        h = (h < 10 ? ('0' + h) : h);
        m = (m < 10 ? ('0' + m) : m);
        s = (s < 10 ? ('0' + s) : s);

        return new Date(y, M, d, h, m, s);
    }

    //获取来源地址
    function getRefererUrl() {
        var ref = document.referrer;
        try {

            var jsRef = window.opener.location.href;
            if (ref.length == 0 && jsRef.length > 0) {
                ref = jsRef;
            }

        } catch (e) {
        }

        return ref;
    }

    //根据不同浏览器做离开监听事件
    function leaveEvent() {
        var s = 'beforeunload';
        var o = navigator.userAgent.toLowerCase();
        if (/firefox/.test(o)) s = 'unload';//火狐
        return s;
    }

    //阿拉伯数字转中文
    function convertToChinese(num) {
        var N = [
            "零", "一", "二", "三", "四", "五", "六", "七", "八", "九"
        ];
        var str = num.toString();
        var len = num.toString().length;
        var C_Num = [];
        for (var i = 0; i < len; i++) {
            C_Num.push(N[str.charAt(i)]);
        }
        return C_Num.join('').toString();
    }

    /**
     * 发送短信验证码
     * @param buttonId 发送按钮ID
     * @param type 发送类型
     */
    function sendSms(buttonId, type) {
        var locked = false; //防止重复点击的锁
        $("#" + buttonId).click(function (event) {
            if (!locked) {
                var time = 59;
                var $readOnlyBtn = $('#sms-btn-readonly');
                var $sendBtn = $(this);
                var $phone = $('#phone');
                var $imageCaptcha = $('#image-captcha');

                var captcha = $.trim($imageCaptcha.val());
                var mobile = $.trim($phone.val());
                var positioning = $('#phone').siblings('.positioning').html()
                if (!/^(13[0-9]|17[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/.test(mobile)) {
                    $phone.siblings('.positioning').html('请输入正确的手机号');
                } else if (positioning == '该手机号已被注册' || positioning == '此手机号尚未绑定账号') {
                    return;
                } else if (captcha == '') {
                    $imageCaptcha.siblings('.positioning').html('请输入图形验证码');
                } else {
                    locked = true;
                    $.post('/user/captcha/sms', {imageCaptcha: captcha, mobile: mobile, type: type}).done(function (result) {
                        if (result.state == 200) {
                            $sendBtn.hide();
                            $readOnlyBtn.html(time + "秒");
                            $readOnlyBtn.show();
                            var t = setInterval(function () {
                                time--;
                                $readOnlyBtn.html(time + "秒");
                                if (time == 0) {
                                    clearInterval(t);
                                    $sendBtn.html('重新获取');
                                    $('#capt-images').click();
                                    $readOnlyBtn.hide();
                                    locked = false;
                                    $sendBtn.show();
                                }
                            }, 1000);
                        } else {
                            $imageCaptcha.siblings('.positioning').html(result.stateInfo);
                            locked = false;
                        }
                    });
                }
            }
        });
    }

    window.Util = {
        Modal: Modal,
        User: User,
        fromToJson: fromToJson,
        getRequestParam: getRequestParam,
        uploadFile: initFileupload,
        rightFloat: rightFloat,
        onEnter: onEnter,
        leaveEvent: leaveEvent,
        convertToChinese: convertToChinese,
        sendSms: sendSms,
        navbarCss: NavbarCss
    };

})(this, jQuery);


//百度统计
var _hmt = _hmt || [];

$(function () {
    var hm = document.createElement("script");
    hm.src = "//hm.baidu.com/hm.js?6340b0bfb57596fe8af2668cb897848c";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
});