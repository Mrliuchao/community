package com.imooc.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long   parentId;
    private String content;
    private Integer type;
}
