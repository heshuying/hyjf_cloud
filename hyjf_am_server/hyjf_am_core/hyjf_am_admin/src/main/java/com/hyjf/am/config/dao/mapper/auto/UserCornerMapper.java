package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.UserCorner;
import com.hyjf.am.config.dao.model.auto.UserCornerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCornerMapper {
    int countByExample(UserCornerExample example);

    int deleteByExample(UserCornerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserCorner record);

    int insertSelective(UserCorner record);

    List<UserCorner> selectByExample(UserCornerExample example);

    UserCorner selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserCorner record, @Param("example") UserCornerExample example);

    int updateByExample(@Param("record") UserCorner record, @Param("example") UserCornerExample example);

    int updateByPrimaryKeySelective(UserCorner record);

    int updateByPrimaryKey(UserCorner record);
}