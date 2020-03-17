package com.imooc.community.controller;

import com.imooc.community.dto.PaginationDTO;
import com.imooc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(HttpServletRequest request,Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "pagesize",defaultValue = "2") Integer pagesize){
        PaginationDTO lists = questionService.getList(page,pagesize);

         model.addAttribute("question", lists);

        return "index";
    }

}
