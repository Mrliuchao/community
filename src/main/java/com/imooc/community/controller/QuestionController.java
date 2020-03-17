package com.imooc.community.controller;

import com.imooc.community.dto.QusetionDto;
import com.imooc.community.mapper.QuesstionMapper;
import com.imooc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public  String question(@PathVariable("id") Integer id, Model model){
        QusetionDto qusetionDto = questionService.getById(id);
        model.addAttribute("question",qusetionDto);
        return  "question";
    }

}
