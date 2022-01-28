package com.LinkS.Filter;

import com.LinkS.DO.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",
        urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;
        String path = request1.getRequestURI();
        User user = (User) request1.getSession().getAttribute("user");

        Cookie[] cookies = request1.getCookies();

        /**
         * 无需登录可访问的页面
         *      主页
         *      登录页面
         *      注册页面
         **/
        if(path.contains("/index")||
                path.contains("/login")||
                path.contains("/user")||
                path.contains("/link/get")){
            chain.doFilter(request1,response1);
            return;
        }

        /**
         * 静态资源,例如图片无需过滤*/
        if(path.contains("/static")){
            chain.doFilter(request1,response1);
            return;
        }

        if(user != null){
            chain.doFilter(request1,response1);
            //一定do完写return，不然他会继续往下执行其他转跳
            return;
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("LinBlog")){
                System.out.println(cookie.getValue());
            }
        }

        //不能用ifelse写，因为他要每一种条件都验证
        // 第一个条件满足后，若子条件不满足，就无法继续判断第二个条件了
        /**
         * 若检测出用户从未登录，则转跳到登录界面
         * */
        response1.sendRedirect("login");
    }
}


