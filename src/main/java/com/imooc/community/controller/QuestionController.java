package com.imooc.community.controller;

import com.imooc.community.dto.CommentDTO;
import com.imooc.community.dto.QusetionDto;
import com.imooc.community.service.CommentService;
import com.imooc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public  String question(@PathVariable("id") Long id, Model model){
        QusetionDto qusetionDto = questionService.getById(id);
        //实现列表回复功能
        List<CommentDTO>  comments =  commentService.listByQusertionId(id);

        //累加阅读数
        questionService.incViiew(id);

        model.addAttribute("question",qusetionDto);
        model.addAttribute("comments",comments);
        return  "question";
    }

}
