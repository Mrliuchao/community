package com.imooc.community.service;

import com.imooc.community.dto.PaginationDTO;
import com.imooc.community.dto.QusetionDto;
import com.imooc.community.exception.CustomizeErrorCode;
import com.imooc.community.exception.CustomizeException;
import com.imooc.community.mapper.QuseetionExtMapper;
import com.imooc.community.mapper.QuseetionMapper;
import com.imooc.community.mapper.UserMapper;
import com.imooc.community.model.Quseetion;
import com.imooc.community.model.QuseetionExample;
import com.imooc.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuseetionMapper quseetionMapper;
    @Autowired
    private QuseetionExtMapper quseetionExtMapper;
    public PaginationDTO getList(Integer page, Integer pagesize) {

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer pageSum =(int) quseetionMapper.countByExample(new QuseetionExample());
        Integer totalPage ;


        if (pageSum %pagesize == 0){
            totalPage =   pageSum/pagesize;
        }else {
            totalPage =  pageSum/pagesize+1;
        }

        if (page<1){
            page = 1;
        }
        if (page>totalPage){
            page = totalPage;
        }
        paginationDTO.setPageination(totalPage,page);

        Integer offset = pagesize * (page -1);
        if (offset <0){
            offset = 0;
        }
        List<Quseetion> list = quseetionMapper.selectByExampleWithBLOBsWithRowbounds(new QuseetionExample(),new RowBounds(offset,pagesize));
        List<QusetionDto> qusetionDtoList = new ArrayList<>();
        for (Quseetion quseetion : list) {
            User user =   userMapper.selectByPrimaryKey(quseetion.getCreator());
            QusetionDto qusetionDto = new QusetionDto();

            BeanUtils.copyProperties(quseetion,qusetionDto);
            qusetionDto.setUser(user);
            qusetionDtoList.add(qusetionDto);
        }

        paginationDTO.setQusetionDtos(qusetionDtoList);



        return paginationDTO;
    }

    public PaginationDTO getList(Long userId, Integer page, Integer pagesize) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage ;

        Integer pageSum =(int) quseetionMapper.countByExample(new QuseetionExample());
        if (pageSum %pagesize == 0){
            totalPage =   pageSum/pagesize;
        }else {
            totalPage =  pageSum/pagesize+1;
        }

        if (page<1){
            page = 1;
        }
        if (page>totalPage){
            page = totalPage;
        }
        paginationDTO.setPageination(totalPage,page);
        Integer offset = pagesize * (page -1);
        if (offset <0){
            offset = 0;
        }
        QuseetionExample quseetionExample = new QuseetionExample();
        quseetionExample.createCriteria().andCreatorEqualTo(userId);
        List<Quseetion> list = quseetionMapper.selectByExampleWithBLOBsWithRowbounds(new QuseetionExample(),new RowBounds(offset,pagesize));
        List<QusetionDto> qusetionDtoList = new ArrayList<>();
        for (Quseetion quseetion : list) {
            User user =   userMapper.selectByPrimaryKey(quseetion.getCreator());
            QusetionDto qusetionDto = new QusetionDto();

            BeanUtils.copyProperties(quseetion,qusetionDto);
            qusetionDto.setUser(user);
            qusetionDtoList.add(qusetionDto);
        }

        paginationDTO.setQusetionDtos(qusetionDtoList);



        return paginationDTO;
    }

    public QusetionDto getById(Long id) {
        Quseetion quseetion =  quseetionMapper.selectByPrimaryKey(id);
        if (quseetion == null){
            throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FQUND);
        }
        QusetionDto qusetionDto = new QusetionDto();
        if (quseetion.getViewCount() ==null){
            quseetion.setViewCount(0);
        }
        BeanUtils.copyProperties(quseetion,qusetionDto);
        User user = userMapper.selectByPrimaryKey(quseetion.getCreator());
        qusetionDto.setUser(user);
        return  qusetionDto;
    }

    public void cretaeOrUpatde(Quseetion quseetion) {
        if (quseetion.getId() == null){
        //创建
            quseetion.setGmtCreate(System.currentTimeMillis());
            quseetion.setGmtModified(System.currentTimeMillis());
            if (quseetion.getCommentCount() ==null){
                quseetion.setCommentCount(0);
            }
            if (quseetion.getViewCount() ==null){
                quseetion.setViewCount(0);
            }
            if (quseetion.getCommentCount() == null){
                quseetion.setCommentCount(0);
            }
            if (quseetion.getLikeCount()==null){
                quseetion.setLikeCount(0);
            }

           quseetionMapper.insertSelective(quseetion);
        }else{
            //修改
            Quseetion updateQuseetion = new Quseetion();
            updateQuseetion.setGmtModified(System.currentTimeMillis());
            updateQuseetion.setTitle(quseetion.getTitle());
            updateQuseetion.setDescription(quseetion.getDescription());
            updateQuseetion.setTag(quseetion.getTag());


            QuseetionExample example = new QuseetionExample();
            example.createCriteria().andIdEqualTo(quseetion.getId());

            int update = quseetionMapper.updateByExampleSelective(updateQuseetion, example);
            if (update !=1){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FQUND);
            }

        }
    }

    //增加阅读数
    public void incViiew(Long id) {
        Quseetion quseetion = new Quseetion();
        quseetion.setId(id);
        quseetion.setViewCount(1);
        quseetionExtMapper.incViiew(quseetion);
    }
}
