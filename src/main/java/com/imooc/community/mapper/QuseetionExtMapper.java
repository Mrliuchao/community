package com.imooc.community.mapper;

import com.imooc.community.model.Quseetion;
import com.imooc.community.model.QuseetionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuseetionExtMapper {
   int  incViiew (Quseetion record);
   int  inComment (Quseetion record);
}