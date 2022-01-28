package com.LinkS.Controller;

import com.LinkS.DO.User;
import com.LinkS.Dao.UserMapper;
import com.LinkS.Service.UserService;
import com.LinkS.VO.Info;
import com.LinkS.VO.UserInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


//                                                  处理数据转跳方法

    /** 注册功能
     *
     * */
    @PostMapping("/register")
    public String register(String username, String password, HttpServletRequest request, HttpServletResponse response)
    {
        User user = userService.userRegister(username,password);

        //            将用户信息存在session中
        request.getSession().setAttribute("user",user);

        Cookie cookie = new Cookie("LinBlog",user.getId());
        cookie.setMaxAge(24*60*60);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
        return "redirect:/index";
    }


    /** 登录功能
     * */
    @PostMapping("/login")
    public String signin(String username, String password, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Info<User> info = userService.userLogin(username, password);
        if(info.getFlag()==1){
//            将用户信息存在session中
            request.getSession().setAttribute("user",info.getResult());

//            将用户uuid存在cookie中发给用户,日后改进
            Cookie cookie = new Cookie("LinBlog",info.getResult().getId());
            cookie.setMaxAge(24*60*60);

//            此处需要解决cookie不同域的问题
            cookie.setPath(request.getContextPath());

            response.addCookie(cookie);


            return "redirect:/index";
        }else {
            return "redirect:/login";
        }
    }



    /**
     * 注销功能
     * */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){

//        删除session中信息
        request.getSession().invalidate();

//        删除cookie中信息
        Cookie cookie = new Cookie("LinBlog",null);
        cookie.setMaxAge(0);
//        此处需要解决cookie不同域的问题
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);

        return "redirect:/index";
    }



    /**
     * 用户更改头像
     * */
    @PostMapping("/editHead")
    public String test(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
//        用户头像保存路径
        String path = request.getServletContext().getRealPath("/WEB-INF/profilePic");

        System.out.println(path);
        User user = (User) request.getSession().getAttribute("user");
//        保存图片
        file.transferTo(new File(path+"/"+user.getId()+".png"));

        return "redirect:/index";
    }



//                                                  ResponseBody方法

    /** 注册功能--ajax动态验证
     * */
    @PostMapping("/registerAjax")
    @ResponseBody
    public String registerAjax(String username, String password){
        return userService.userRegisterByAjax(username,password);
    }


    /** 请求当前用户的名称
     * */
    @PostMapping ("/getUserInfo")
    @ResponseBody
    public UserInfo getUserInfo(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        User user = (User) request.getSession().getAttribute("user");

        if(user==null){
            User user1 = null;
            for(Cookie cookie : cookies){
                if("LinBlog".equals(cookie.getName())){
                    request.getSession().setAttribute("user", userMapper.queryById(cookie.getValue()));
                }
            }
            if(user1 == null){
                return new UserInfo("00000000000000000000000000000000","Welcome");
            }else {
                return new UserInfo(user1.getId(),user1.getUsername());
            }
        }else{
            return new UserInfo(user.getId(),user.getUsername());
        }
    }



    /*
     * 返回图片文件，只是实验目前
     * */
    @GetMapping("/getProfilePic")
    @ResponseBody
    public void getHead(HttpServletRequest request,HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        response.setContentType("image/png");
//        返回的头像
        File file = null;
//        未登录
        if(user == null){
            file = new File(request.getServletContext().getRealPath("/WEB-INF/profilePic/init.png"));
        }else{
            file = new File(request.getServletContext().getRealPath("/WEB-INF/profilePic/"+user.getId()+".png"));

//            用户未设置头像，使用默认头像
            if(!file.exists()){
                file = new File(request.getServletContext().getRealPath("/WEB-INF/profilePic/init.png"));
            }
        }

        FileUtils.copyFile(file, response.getOutputStream());
    }

}

