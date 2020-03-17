package com.imooc.community.controller;

import com.imooc.community.dto.PaginationDTO;
import com.imooc.community.mapper.UserMapper;
import com.imooc.community.model.User;
import com.imooc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profiile(@PathVariable(name = "action") String action,
                           Model model,
                           HttpServletRequest request,
                           @RequestParam(name = "page",defaultValue = "1") Integer page,
                           @RequestParam(name = "pagesize",defaultValue = "2") Integer pagesize){
        if ("quesrions".equals(action)){
            model.addAttribute("section","quesrions");
            model.addAttribute("sectionName","我的提问");
        }else if ("repies".equals(action)){
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","最新回复");
        }


       User user =  (User)request.getSession().getAttribute("user");

        if (user == null){
            return "redirect:/";
        }
        System.out.println(user.getId());
        PaginationDTO list = questionService.getList(user.getId(), page, pagesize);
        model.addAttribute("question",list);

        return  "profile";
    }


}
