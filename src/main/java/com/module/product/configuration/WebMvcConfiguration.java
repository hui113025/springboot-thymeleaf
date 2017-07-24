package com.module.product.configuration;

import com.module.core.common.GeneralWebMvcConfigurer;
import com.module.core.common.Result;
import com.module.core.common.ResultState;
import com.module.core.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Configuration
public class WebMvcConfiguration extends GeneralWebMvcConfigurer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        //统一的异常处理
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                ModelAndView mv = new ModelAndView();
                if (handler instanceof HandlerMethod) {
                    Result result = new Result()
                            .setResInfo(new Object());
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    if (null != handlerMethod.getBean().getClass().getAnnotation(RestController.class)
                            || null != handlerMethod.getMethodAnnotation(ResponseBody.class)) {//返回JSON格式

                        if (ex instanceof ServiceException) {
                            result.setState(ResultState.FAIL).setStateInfo(ex.getMessage());
                            logger.info(ex.getMessage());
                        } else {
                            result.setState(ResultState.INTERNAL_SERVER_ERROR).setStateInfo("接口暂不可用，请联系管理员");
                            String message = String.format("调用接口%s出现异常，方法：%s.%s，异常摘要：%s",
                                    request.getRequestURI(),
                                    handlerMethod.getBean().getClass().getName(),
                                    handlerMethod.getMethod().getName(),
                                    ex.getMessage());
                            logger.error(message, ex);
                        }

                        responseResult(response, result);

                    } else {//普通页面
                        if (response.getStatus() == 404) {
                            mv.setViewName("error/404");
                        } else {
                            mv.setViewName("error/500");
                            logger.error(ex.getMessage(), ex);
                        }

                    }
                } else {
                    logger.error(ex.getMessage(), ex);
                }
                return mv;
            }
        });

    }
}
