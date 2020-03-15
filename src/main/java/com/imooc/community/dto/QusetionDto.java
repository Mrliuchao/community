package com.imooc.community.dto;

import com.imooc.community.model.User;
import lombok.Data;

@Data
public class QusetionDto {
    private  Integer id;
    private String title;
    private String description;
    private  String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private  Integer creator;
    private Integer commentCount;
    private Integer viewounCt;
    private Integer likeCount;
    private User user;
}
