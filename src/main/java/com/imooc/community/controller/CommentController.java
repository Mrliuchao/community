package com.imooc.community.controller;

import com.imooc.community.dto.CommentDTO;
import com.imooc.community.dto.ResultDTO;
import com.imooc.community.exception.CustomizeErrorCode;
import com.imooc.community.mapper.CommentMapper;
import com.imooc.community.model.Comment;
import com.imooc.community.model.User;
import com.imooc.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    //实现回复功能
    @ResponseBody
    @RequestMapping(value = "/comment" ,method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.No_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentatsor(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return  ResultDTO.okOf();
    }
}
