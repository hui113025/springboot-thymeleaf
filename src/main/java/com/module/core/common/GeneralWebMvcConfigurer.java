package com.module.core.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonpHttpMessageConverter4;
import com.module.core.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * <p>
 * Ruhang Cloud 通用Spring MVC 配置，如需扩展定制可以继承该配置类，复写父类方法。
 */
public class GeneralWebMvcConfigurer extends WebMvcConfigurerAdapter {
    protected final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);

    //使用阿里 FastJson 作为MessageConverter
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonpHttpMessageConverter4 converter = new FastJsonpHttpMessageConverter4();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,//保留空的字段
                SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
                SerializerFeature.WriteNullNumberAsZero);//Number null -> 0
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter);
    }

    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                Result result = new Result()
                        .setResInfo(new Object());
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    String message = String.format("调用接口%s出现异常，方法：%s.%s，异常摘要：%s",
                            request.getRequestURI(),
                            handlerMethod.getBean().getClass().getName(),
                            handlerMethod.getMethod().getName(),
                            e.getMessage());
                    if (e instanceof ServiceException) {
                        result
                                .setState(ResultState.FAIL)
                                .setStateInfo(e.getMessage());
                        logger.debug(message);
                    } else {
                        result
                                .setState(ResultState.INTERNAL_SERVER_ERROR)
                                .setStateInfo("接口暂不可用，请联系管理员");
                        logger.error(message);
                    }
                } else {
                    logger.warn(String.format("调用接口 %s 出现异常,异常摘要：%s", request.getRequestURI(), e.getMessage()));
                    result
                            .setStateInfo(e.getMessage())
                            .setState(ResultState.INTERNAL_SERVER_ERROR);
                }

                responseResult(response, result);

                return new ModelAndView();
            }

        });
    }

    protected void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
}
