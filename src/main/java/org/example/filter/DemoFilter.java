package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
@Slf4j
public class DemoFilter implements Filter {
    //只执行一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        log.info("init");
//        Filter.super.init(filterConfig);
    }

    //拦截到请求之后就执行一次
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
        log.info("拦截到了请求");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    //只执行一次
    @Override
    public void destroy() {
        log.info("destroy");
//        Filter.super.destroy();
    }
}
