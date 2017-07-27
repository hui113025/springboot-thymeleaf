/**
 * 帮助中心
 * @author
 * @date 2017年7月27日
 */
(function ($) {


    $(document).ready(function () {
        bindEvents();
    })

    //绑定各种事件
    function bindEvents() {
        $('.helpRight-bottom>div').hover(function(){
            $(this).find('.help-pro').show(100)
            $(this).css({'background':'#eee','padding-bottom':'15px'})
        },function(){
            $(this).find('.help-pro').hide(100)
            $(this).css({'background':'#eee','padding-bottom':'0px'})
        })
    }


})(jQuery);