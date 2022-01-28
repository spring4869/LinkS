package com.LinkS.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/index")
    public String index(){
//        重定向，路径为项目下一级
//        return "redirect:/index.html";

//        springmvc转跳，路径为spirngmvc配置路径
//        return "signin";
        return "forward:/index.html";
    }


    /*
     * 为解决js转跳用户操作页面转发不经过filter，无法验证是否为登录状态
     * 特此添加每个页面两种转跳方法，转发和重定向
     * */

    //    视图解析器转发到link页面
    @GetMapping("/link")
    public String link(){
        return "link";
    }
    //    重定向到link页面
    @GetMapping("/redirect_link")
    public String redirect_link(){
        return "redirect:/link";
    }


    //    视图解析器转发到profile页面
    @GetMapping("/profile")
    public String profile(){
        return "profile";
    }
    //    重定向到profile页面
    @GetMapping("/redirect_profile")
    public String redirect_profile(){
        return "redirect:/profile";
    }

}
