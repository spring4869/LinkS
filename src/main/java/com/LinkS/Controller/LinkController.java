package com.LinkS.Controller;

import com.LinkS.DO.Link;
import com.LinkS.DO.User;
import com.LinkS.Dao.UserMapper;
import com.LinkS.Service.LinkService;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserMapper userMapper;


    /**主页动态加载link接口
     * */
    @GetMapping("/getTenlinkByTimestamp")
    @ResponseBody
    public String getLinks(long timeStamp){
        List<Link> links =  linkService.getTenLinksByTimestamp(timeStamp);

        String tenlink = new Gson().toJson(links);
        return tenlink;
    }


    /**用户添加新link
     * */
    @PostMapping("/addNewLink")
    @ResponseBody
    public String addLink(String content, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        Link newLink = new Link(UUID.randomUUID().toString().replace("-",""),user.getUsername(),content,new Date().getTime());

        if(linkService.addLink(newLink) == 1){
            return "success";
        } else {
            return "fail";
        }
    }


    /**
     * 获取index每条link作者的头像
     * */
    @GetMapping("/getLinkProfilePicById")
    @ResponseBody
    public void getProfilePicById(String author, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("image/png");
        String id = userMapper.queryByUsername(author).getId();

        File file = null;

        file = new File(request.getServletContext().getRealPath("/WEB-INF/profilePic/"+id+".png"));

        if (!file.exists()){
            file = new File(request.getServletContext().getRealPath("/WEB-INF/profilePic/init.png"));
        }

        FileUtils.copyFile(file, response.getOutputStream());
    }

}
