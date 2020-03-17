package com.imooc.community.mapper;

import com.imooc.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user (name,account_id,token,gmt_create,gmt_modified,avatar_url) VALUES (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
      void  insert(User user);

    @Select("select * from  user where  token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from  user where  id = #{id}")
    User finById(@Param("id") Integer id);
    @Select("select * from  user where  account_id = #{accountId}")
    User findByAccounId(@Param("accountId") String accountId);

    @Update("update user set name= #{name} ,token = #{token}, gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where  id =#{id}")
    void update(User user);
}
