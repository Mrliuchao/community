package com.imooc.community.service;

import com.imooc.community.dto.GithubUser;
import com.imooc.community.mapper.UserMapper;
import com.imooc.community.model.User;
import com.imooc.community.model.UserExample;
import com.imooc.community.provider.GithubProvider;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);

        if (users.size() == 0){
           //插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(System.currentTimeMillis());
           userMapper.insert(user);
       }else{
           //修改
            User dbuser = users.get(0);
            User userupdate = new User();
            userupdate.setGmtModified(System.currentTimeMillis());
            userupdate.setName(user.getName());
            userupdate.setAvatarUrl(user.getAvatarUrl());
            userupdate.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbuser.getId());

            userMapper.updateByExampleSelective(userupdate,example);
       }


    }
}
