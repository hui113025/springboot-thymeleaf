/**
 * 服务协议
 * @author
 * @date 2017年7月27日
 */
(function ($) {


	$(document).ready(function () {
		bindEvents();
	})

	//绑定各种事件
	function bindEvents() {

		$(".helpRight-top li").click(function () {
			var pig = $(".helpRight-top li").index(this);
			$(".helpRight-top li").eq(pig).css("border-bottom", "2px solid #5583DB").siblings().css("border-bottom", "0px");
			$(".agreement-xxk").eq(pig).css("display", "block").siblings().css("display", "none");
		});

	}


})(jQuery);