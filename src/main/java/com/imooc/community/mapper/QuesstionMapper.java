package com.imooc.community.mapper;

import com.imooc.community.dto.QusetionDto;
import com.imooc.community.model.Quseetion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface QuesstionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public  void  cretae(Quseetion quseetion);
    //查询所有带分页
    @Select("select * from question limit #{offset},#{pagesize}")
    List<Quseetion> getList(@Param(value = "offset") Integer offset,@Param(value = "pagesize") Integer pagesize);

    // 查询总数
    @Select("select  count(1) from question ")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset},#{pagesize}")
    List<Quseetion> getListByUserId( @Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset,@Param(value = "pagesize") Integer pagesize);

    @Select("select  count(1) from question where  creator = #{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Quseetion getById(@Param("id") Integer id);
}
