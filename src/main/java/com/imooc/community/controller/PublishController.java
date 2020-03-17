package com.imooc.community.controller;

import com.imooc.community.mapper.QuesstionMapper;
import com.imooc.community.model.Quseetion;
import com.imooc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuesstionMapper quesstionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    @PostMapping("/publish")
    public String doPublish(
                    @RequestParam("title") String title,
                    @RequestParam("description") String description,
                    @RequestParam("tag") String tag,
                    HttpServletRequest request,
                    Model model
                    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title == null || title ==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description ==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }

        User user =  (User)request.getSession().getAttribute("user");


        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Quseetion quseetion = new Quseetion();
        quseetion.setTitle(title);
        quseetion.setDescription(description);
        quseetion.setTag(tag);
        quseetion.setCreator(user.getId());
        quseetion.setGmtCreate(System.currentTimeMillis());
        quseetion.setGmtModified(System.currentTimeMillis());
        quesstionMapper.cretae(quseetion);
        return "redirect:/";
    }

}
