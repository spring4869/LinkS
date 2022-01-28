package com.LinkS.Service.Impl;

import cn.hutool.core.util.StrUtil;
import com.LinkS.DO.User;
import com.LinkS.Dao.UserMapper;
import com.LinkS.Service.UserService;
import com.LinkS.VO.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public String userRegisterByAjax(String username, String password) {
        if (StrUtil.isBlank(username)) {
            return "username is empty";
        } else if (userMapper.query(username)!=null) {
            return "username already exists";
        }else if(StrUtil.isBlank(password)){
            return "password is empty";
        }else{
            return "Welcome";
        }
    }


    @Override
    public User userRegister(String username, String password) {

        String id = UUID.randomUUID().toString().replace("-","");

        User user = new User(id,username,password);

        userMapper.add(user);

        return user;
    }


    @Override
    public Info<User> userLogin(String username, String password) {
        Info<User> info = new Info<>();

        User user = userMapper.query(username);

        if(user == null){
            info.setFlag(0);
        }else {
            if(user.getUsername().equals(username)){
                if(user.getPassword().equals(password)){
                    info.setFlag(1);
                    info.setResult(user);
                }else{
                    info.setFlag(0);
                }
            }
        }
        return info;
    }


}
