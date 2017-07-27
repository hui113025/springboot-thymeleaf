/**
 * 帮助首页
 * @author
 * @date 2017年7月27日
 */
(function ($) {


    $(document).ready(function () {
        bindEvents();
    })

    //绑定各种事件
    function bindEvents() {
        $(".help-left li").click(function () {
            var hm = $(".help-left li").index(this);
            $(".help-left li").eq(hm).css({
                "color": "#5583DB",
                "border-left": "3px solid #5583DB"
            }).siblings().css({"color": "#666666", "border-left": "3px solid #ffffff"});

            var url,title,sm,id = this.id;
            if(id == 1){
                url = '/help/html/helpRight.html';
                title = '帮助中心';
            } else if(id == 2){
                url = '/help/html/feedback.html';
                title = '意见反馈';
            }else if(id == 3){
                url = '/help/html/agreement.html';
                title = '服务协议';
                sm = Util.getRequestParam('sm');
            }else if(id == 4){
                url = '/help/html/relation.html';
                title = '联系我们';
            }else if(id == 5){
                url = '/help/html/aboutUS.html';
                title = '关于我们';
            }
            $('#help-right').load(url,function () {
                document.title= title;
                if (sm)
                    $('#sm-' + sm).click();
                else {
                    $('#sm-1').click();
                }
                sm = '';//用完清空
            })
        });

        //默认选中
        var m = Util.getRequestParam('m');
        m ?　$('#' + m).click() : $('#1').click();
    }


})(jQuery);