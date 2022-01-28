package com.LinkS.Dao;

import com.LinkS.DO.User;

public interface UserMapper {

    //    增加一个用户
    int add(User user);

    //    删除用户
    int delete(int id);

    //    查询此用户
    User query(String username);

    //    根据id查询用户
    User queryById(String id);

    //    根据用户名查询用户
    User queryByUsername(String username);

}