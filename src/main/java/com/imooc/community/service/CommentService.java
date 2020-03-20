package com.imooc.community.service;

import com.imooc.community.enums.CommentTypeEnum;
import com.imooc.community.exception.CustomizeErrorCode;
import com.imooc.community.exception.CustomizeException;
import com.imooc.community.mapper.CommentMapper;
import com.imooc.community.mapper.QuseetionExtMapper;
import com.imooc.community.mapper.QuseetionMapper;
import com.imooc.community.model.Comment;
import com.imooc.community.model.Quseetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuseetionMapper quseetionMapper;

    @Autowired
    private QuseetionExtMapper quseetionExtMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId()== 0){
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FQUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw  new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

        }else{
            //回复问题
            Quseetion quseetion = quseetionMapper.selectByPrimaryKey(comment.getParentId());
            if (quseetion == null){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FQUND);
            }
            commentMapper.insert(comment);
            quseetion.setCommentCount(1);
            quseetionExtMapper.inComment(quseetion);
        }
    }
}
