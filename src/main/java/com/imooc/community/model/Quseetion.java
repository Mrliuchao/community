package com.imooc.community.model;

import lombok.Data;

@Data
public class Quseetion {
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


}
