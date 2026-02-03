package org.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //获取请求路径
//        String requestURL=request.getRequestURI();
//
//        //判断是否为登录请求 url有/login-->是-->放行
//        if(requestURL.contains("/login")) {
//            log.info("登录请求，放行");
//            return true;
//        }
// 可省略-->在WebConfig中用.excludePathPatterns()

        //获取请求头中token
        String token=request.getHeader("token");

        //判断token是否存在 不存在-->用户没有登录-->报错（401）
        if(token==null||token.isEmpty()){
            log.info("令牌为空，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("未登录，请先登录");
            return false;
        }

        //存在-->校验令牌 失败-->报错（401）
        try{
            JwtUtils.parseJWT(token);
        }catch(Exception e){
            log.info("令牌为空，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        //成功-->放行
        log.info("令牌合法，放行");
        return true;
    }
}
