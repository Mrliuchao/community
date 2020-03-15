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
        paginationDTO.setPageination(pageSum,page,pagesize);
        if (page<1){
            page = 1;
        }
        if (page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        Integer offset = pagesize * (page -1);
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
}
