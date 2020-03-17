package com.imooc.community.service;

import com.imooc.community.dto.GithubUser;
import com.imooc.community.mapper.UserMapper;
import com.imooc.community.model.User;
import com.imooc.community.provider.GithubProvider;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
       User dbuser =  userMapper.findByAccounId(user.getAccountId());
       if (dbuser == null){
           //插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(System.currentTimeMillis());
           userMapper.insert(user);
       }else{
           //修改

           dbuser.setGmtModified(System.currentTimeMillis());
           dbuser.setName(user.getName());
           dbuser.setAvatarUrl(user.getAvatarUrl());
           dbuser.setToken(user.getToken());
            userMapper.update(dbuser);
       }


    }
}
