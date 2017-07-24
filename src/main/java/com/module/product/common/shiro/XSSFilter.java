package com.module.product.common.shiro;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.web.servlet.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Created by on 2016/8/16.
 *
 * XSS 过滤器
 */
public class XSSFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(new XSSHttpServletRequestWrapper((HttpServletRequest) request), response);
    }

    private class XSSHttpServletRequestWrapper extends HttpServletRequestWrapper {

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request
         * @throws IllegalArgumentException if the request is null
         */
        public XSSHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getHeader(String name) {
            return filterXSS(super.getHeader(name));
        }

        @Override
        public String getParameter(String name) {
            return filterXSS(super.getParameter(name));
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            if(values != null) {
                int length = values.length;
                String[] escapeValues = new String[length];
                for(int i = 0; i < length; i++){
                    escapeValues[i] = filterXSS(values[i]);
                }
                return escapeValues;
            }
            return values;
        }

        @Override
        public String getQueryString() {
            return super.getQueryString();
        }

        //利用Apache-Commons-Lang 提供的方法进行过滤，当然Spring也提供了HtmlUtil JavascriptUtil供使用，不过会和Spring耦合，所以这里使用Apache的。
        private String filterXSS(String value) {
            value = StringEscapeUtils.escapeHtml4(value);
            //value = StringEscapeUtils.escapeEcmaScript(value);
            return value;
        }
    }

}


