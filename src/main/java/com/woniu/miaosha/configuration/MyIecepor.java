package com.woniu.miaosha.configuration;

import com.woniu.miaosha.entity.User;
import com.woniu.miaosha.service.HasRole;
import com.woniu.miaosha.service.UserService;
import com.woniu.miaosha.utils.RedisUtil;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sun.java2d.pipe.OutlineTextRenderer;
import sun.java2d.pipe.TextPipe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class MyIecepor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //要查询商品就必须要登录

       String url =request.getRequestURI();

        System.out.println(url);
        if(url.contains("success")){
            System.out.println(888);
            String account = request.getSession().getAttribute("name").toString();
            User user = userService.findPremession(account);
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Method method = handlerMethod.getMethod();
            if(method.isAnnotationPresent(HasRole.class)){
                HasRole hasRole = method.getAnnotation(HasRole.class);
                String role = hasRole.value();

                System.out.println(role);
                if(role.equals(user.getRole())){
                    System.out.println(123);
                    redisUtil.set(account,"zz");
                    return true;
                }else {
                    System.out.println(234);
                    response.setStatus(302);
                    response.setHeader("Location", "show");
                }
            }
        }
        if(url.indexOf("login")!=-1){
            System.out.println(889);
            return true;
        }
        if(url.indexOf("show")!=-1){
            System.out.println(567);
            return true;
        }
        if(url.indexOf("static")!=-1){
            return true;
        }
        if(url.contains("jpg")){
            System.out.println("lll");
            return true;
        }
        if(url.contains("miaosha")){
            if(redisUtil.get(request.getSession().getAttribute("name").toString()).equals("zz")){
                return true;
            }
            else {
                System.out.println(234);
                response.setStatus(302);
                response.setHeader("Location", "show");
            }
        }
        if(url.contains("all")){
            if(redisUtil.get(request.getSession().getAttribute("name").toString()).equals("zz")){
                return true;
            }
            else {
                System.out.println(234);
                response.setStatus(302);
                response.setHeader("Location", "show");
            }
        }
        else {
            System.out.println(234);
            response.setStatus(302);
            response.setHeader("Location", "show");
        }


        return false;

    }
}
