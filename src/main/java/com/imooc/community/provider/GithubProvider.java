package com.imooc.community.provider;


import com.alibaba.fastjson.JSON;
import com.imooc.community.dto.AccessTokenDTO;

import com.imooc.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();

                String token =  string.split("&")[0].split("=")[1];

                System.out.println(token);
                return token;
            } catch (IOException e) {

            }
            return null;
    }


    public GithubUser getUser(String accessToken)  {
        OkHttpClient client = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                //错误2  access_token必须加=  否者为空
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response  response = client.newCall(request).execute();
            String string = response.body().string();
            //把string的json对象转换成java类对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {

        }
        return null;

    }


}
