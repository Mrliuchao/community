package com.imooc.community.controller;

import com.imooc.community.dto.PaginationDTO;
import com.imooc.community.dto.QusetionDto;
import com.imooc.community.mapper.QuesstionMapper;
import com.imooc.community.mapper.UserMapper;
import com.imooc.community.model.Quseetion;
import com.imooc.community.model.User;
import com.imooc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(HttpServletRequest request,Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "pagesize",defaultValue = "2") Integer pagesize){
        //通过 request  获取到 cookies
        Cookie[] cookies = request.getCookies();
        //遍历cookie 判断 cookie的名字和 token 是否一致
        //取数据中查询  把 数据存到 session
        if (cookies!=null && cookies.length!=0){
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                System.out.println(token);
                User user = userMapper.findByToken(token);

                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        }

        PaginationDTO lists = questionService.getList(page,pagesize);
         model.addAttribute("question", lists);

        return "index";
    }

}
