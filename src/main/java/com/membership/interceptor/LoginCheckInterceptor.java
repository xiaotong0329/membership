package com.membership.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.membership.pojo.Result;
import com.membership.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("LoginCheckIntercepter preHandle");

        //1. get req url
        String url = request.getRequestURI().toString();
        log.info("请求的url：{}", url);

        //2. have login ?
        if(url.contains("/login")) {
            log.info("登录操作，放行");
            return true;
        }

        //3. get token
        String jwt = request.getHeader("token");

        //4. token exist?
        if(!StringUtils.hasLength(jwt)) {
            log.info("请求头token为空，返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        //5. analyze token
        try{
            JWTUtils.parseJWT(jwt);
        } catch(Exception e){
            e.printStackTrace();
            log.info("解析令牌失败，返回错误信息");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        //6. 放行
        log.info("令牌合法，放行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("LoginCheckIntercepter postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("LoginCheckIntercepter afterCompletion");
    }
}
