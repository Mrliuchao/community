package com.imooc.community.service;

import com.imooc.community.dto.CommentDTO;
import com.imooc.community.enums.CommentTypeEnum;
import com.imooc.community.exception.CustomizeErrorCode;
import com.imooc.community.exception.CustomizeException;
import com.imooc.community.mapper.CommentMapper;
import com.imooc.community.mapper.QuseetionExtMapper;
import com.imooc.community.mapper.QuseetionMapper;
import com.imooc.community.mapper.UserMapper;
import com.imooc.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuseetionMapper quseetionMapper;
    @Autowired
    private QuseetionExtMapper quseetionExtMapper;
    @Autowired
    private UserMapper userMapper;

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

    //实现列表回复功能
    public List<CommentDTO> listByQusertionId(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0 ){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> collect = comments.stream().map(comment -> comment.getCommentatsor()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(collect);

        //获取评论人并转换为map
        UserExample example = new UserExample();
        example.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(example);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment  wei commentDto
        List<CommentDTO> dtoList = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentatsor()));
            return commentDTO;
        }).collect(Collectors.toList());
        return  dtoList;
    }
}
