package com.LinkS.Dao;

import com.LinkS.DO.Link;

import java.util.List;

public interface LinkMapper {

    //    增加推文
    public int add(Link link);

    //    删除推文
    public int delete(String id);

    //    查询推文
    public Link query(String id);

    //    查询timeStamp时间之前的近十条推文
    public List<Link> queryLinks(long timeStamp);
}
