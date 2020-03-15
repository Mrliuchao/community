package com.imooc.community.controller;

import com.imooc.community.dto.AccessTokenDTO;
import com.imooc.community.dto.GithubUser;
import com.imooc.community.mapper.UserMapper;
import com.imooc.community.model.User;
import com.imooc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response)  {
        //错误一  顺序问题  先set进去值在get  这里 顺序错误 会打印出 一个html页面
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //错误3  如果名字正确  错误原因 就是gitHub没有设置好公开信息  结果 在setting 设置
        GithubUser githubuser = githubProvider.getUser(accessToken);
        System.out.println(githubuser.toString());
       if (githubuser !=null && githubuser.getId()!=null){
           User user = new User();
           user.setName(githubuser.getName());
           user.setAccountId(String.valueOf(githubuser.getId()));
           String token =  UUID.randomUUID().toString();
           user.setToken(token);
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(System.currentTimeMillis());
           user.setAvatarUrl(githubuser.getAvatarUrl());
           userMapper.insert(user);
           //登录成功 写cookie 和Session
           response.addCookie(new Cookie("token",token));
           return "redirect:/";
       }else {
           //登录失败 重新登陆
           return "redirect:/";
       }

    }



}
