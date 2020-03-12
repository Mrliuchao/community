package com.imooc.community.controller;

import com.imooc.community.dto.AccessTokenDTO;
import com.imooc.community.dto.GithubUser;
import com.imooc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret=}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state")String state)  {
        //错误一  顺序问题  先set进去值在get  这里 顺序错误 会打印出 一个html页面
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //错误3  如果名字正确  错误原因 就是gitHub没有设置好公开信息  结果 在setting 设置
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.toString());
        return  "index";
    }



}
