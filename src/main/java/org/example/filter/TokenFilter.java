package org.example.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.CurrentHolder;
import org.example.utils.JwtUtils;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@Slf4j
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        try{
            //获取请求路径
            String requestURL=request.getRequestURI();

            //判断是否为登录请求 url有/login-->是-->放行
            if(requestURL.contains("/login")){
                log.info("登录请求，放行");
                filterChain.doFilter(request,response);
                return ;
            }

            //获取请求头中token
            String token=request.getHeader("token");

            //判断token是否存在 不存在-->用户没有登录-->报错（401）
            if(token==null||token.isEmpty()){
                log.info("令牌为空，响应401");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    //            response.getWriter().write("未登录，请先登录");
                return ;
            }

            //存在-->校验令牌 失败-->报错（401）
            Claims claims=JwtUtils.parseJWT(token);
            Integer empId=Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);  //存入ThreadLocal
            log.info("当前用户ID：{}",empId);

            //成功-->放行
            log.info("令牌合法，放行");
            filterChain.doFilter(request,response);
        }catch(Exception e){
            log.info("令牌为空，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return ;
        }finally {
            //删除ThreadLocal中的数据
            CurrentHolder.remove();
        }
    }
}
