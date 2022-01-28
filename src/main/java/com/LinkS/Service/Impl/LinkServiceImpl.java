package com.LinkS.Service.Impl;

import com.LinkS.DO.Link;
import com.LinkS.Dao.LinkMapper;
import com.LinkS.Dao.UserMapper;
import com.LinkS.Service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("LinkServiceImpl")
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private UserMapper userMapper;


    /*
     * 主页加载links的方法
     * */
    @Override
    public List<Link> getTenLinksByTimestamp(long timeStamp) {

        List<Link> links = linkMapper.queryTenLinks(timeStamp);

        return links;
    }


    /*
     * 用户添加自己的link的方法
     * */
    @Override
    public int addLink(Link link) {
        return linkMapper.add(link);
    }



}
