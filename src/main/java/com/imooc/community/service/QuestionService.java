package com.imooc.community.service;

import com.imooc.community.dto.PaginationDTO;
import com.imooc.community.dto.QusetionDto;
import com.imooc.community.mapper.QuesstionMapper;
import com.imooc.community.mapper.UserMapper;
import com.imooc.community.model.Quseetion;
import com.imooc.community.model.User;
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
    private QuesstionMapper quesstionMapper;
    public PaginationDTO getList(Integer page, Integer pagesize) {

        PaginationDTO paginationDTO = new PaginationDTO();
      Integer pageSum = quesstionMapper.count();
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
        List<Quseetion> list = quesstionMapper.getList(offset ,pagesize);
        List<QusetionDto> qusetionDtoList = new ArrayList<>();
        for (Quseetion quseetion : list) {
            User user =   userMapper.finById(quseetion.getCreator());
            QusetionDto qusetionDto = new QusetionDto();
            if (quseetion.getCommentCount() ==null){
                quseetion.setCommentCount(0);
            }
            if (quseetion.getViewounCt() ==null){
                quseetion.setViewounCt(0);
            }
            BeanUtils.copyProperties(quseetion,qusetionDto);
            qusetionDto.setUser(user);
            qusetionDtoList.add(qusetionDto);
        }

        paginationDTO.setQusetionDtos(qusetionDtoList);



        return paginationDTO;
    }

    public PaginationDTO getList(Integer userId, Integer page, Integer pagesize) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage ;
        Integer pageSum = quesstionMapper.countByUserId(userId);
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
        List<Quseetion> list = quesstionMapper.getListByUserId(userId,offset ,pagesize);
        List<QusetionDto> qusetionDtoList = new ArrayList<>();
        for (Quseetion quseetion : list) {
            User user =   userMapper.finById(quseetion.getCreator());
            QusetionDto qusetionDto = new QusetionDto();
            if (quseetion.getCommentCount() ==null){
                quseetion.setCommentCount(0);
            }
            if (quseetion.getViewounCt() ==null){
                quseetion.setViewounCt(0);
            }
            BeanUtils.copyProperties(quseetion,qusetionDto);
            qusetionDto.setUser(user);
            qusetionDtoList.add(qusetionDto);
        }

        paginationDTO.setQusetionDtos(qusetionDtoList);



        return paginationDTO;
    }

    public QusetionDto getById(Integer id) {
       Quseetion quseetion =  quesstionMapper.getById(id);
        QusetionDto qusetionDto = new QusetionDto();
        if (quseetion.getViewounCt() ==null){
            quseetion.setViewounCt(0);
        }
        BeanUtils.copyProperties(quseetion,qusetionDto);
        User user = userMapper.finById(quseetion.getCreator());
        qusetionDto.setUser(user);
        return  qusetionDto;
    }
}
