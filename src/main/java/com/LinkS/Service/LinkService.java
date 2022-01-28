package com.LinkS.Service;

import com.LinkS.DO.Link;

import java.util.List;

public interface LinkService {


    //    返回十条timeStamp之前的文字
    public List<Link> getTenLinksByTimestamp(long timeStamp);


    //    用户添加link方法
    public int addLink(Link link);
}
