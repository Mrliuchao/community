package com.imooc.community.controller;

import com.imooc.community.dto.QusetionDto;
import com.imooc.community.model.Quseetion;
import com.imooc.community.model.User;
import com.imooc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name =  "id") Long id,
                       Model model){
        //通过id 获取  获取  question 对象
        QusetionDto question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return  "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
                    @RequestParam(value = "title" ,required = false) String title,
                    @RequestParam(value = "description",required = false) String description,
                    @RequestParam(value = "tag"  ,required = false) String tag,
                    @RequestParam(value = "id"  ,required = false) Long id,
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
        quseetion.setId(id);
        questionService.cretaeOrUpatde(quseetion);

        return "redirect:/";
    }



}



