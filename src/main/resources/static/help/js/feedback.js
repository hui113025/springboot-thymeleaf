/**
 * 意见反馈
 * @author
 * @date 2017年7月27日
 */
(function ($) {


    $(document).ready(function () {
        bindEvents();
    })

    //绑定各种事件
    function bindEvents() {
        Util.uploadFile('imageFile', 'preview-img', 'images-path', 'feedback', function () {
            $('#upload-btn').hide();
            $('#preview-img-div').show();
        });

        $('#submit-btn').click(function () {
            var title = $.trim($('input[name=feedbackTitle]').val());

            if (validate()) {
                var data = Util.fromToJson('feedback-form');
                $.post('/feedback/submit', data).done(function (result) {
                    if (result.success){
                        layer.alert('感谢您的反馈！', {title: '成功'});
                        $('#feedback-form')[0].reset();
                        $('#upload-btn').show();
                        $('#preview-img-div').hide();
                    }else {
                        layer.alert(result.message, {title: '失败'});
                    }
                });
            }
        });
    }

    function validate(){
        var title = $.trim($('input[name=feedbackTitle]').val());
        var desc = $.trim($('textarea[name=feedbackDesc]').val());
        var phone = $.trim($('input[name=phone]').val());
        var pass = false;
        if (!title)
            layer.alert('标题不能为空哦!',{title: '温馨提示'});
        else if (!desc)
            layer.alert('内容不能为空哦!',{title: '温馨提示'});
        else if (!phone)
            layer.alert('联系方式不能为空哦!',{title: '温馨提示'});
        else
            pass = true;
        return pass;
    }


})(jQuery);