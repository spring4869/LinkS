package com.LinkS.Service;

import com.LinkS.DO.Link;

import java.util.List;

public interface LinkService {


    //    返回5条timeStamp之前的文字
    public List<Link> getLinksByTimestamp(long timeStamp);


    //    用户添加link方法
    public int addLink(Link link);
}
