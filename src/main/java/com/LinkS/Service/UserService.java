package com.LinkS.Service;

import com.LinkS.DO.User;
import com.LinkS.VO.Info;

public interface UserService {

    //    ajax动态验证
    public String userRegisterByAjax(String username, String password);

    //    注册
    public User userRegister(String username, String password);

    //    登录
    public Info<User> userLogin(String username, String password);

}
