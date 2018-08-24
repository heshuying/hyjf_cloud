package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.UserPlat;
import com.hyjf.am.user.dao.model.auto.UserPlatExample;

public interface UserPlatMapper {
    int countByExample(UserPlatExample example);

    int deleteByExample(UserPlatExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserPlat record);

    int insertSelective(UserPlat record);

    List<UserPlat> selectByExample(UserPlatExample example);

    UserPlat selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserPlat record, @Param("example") UserPlatExample example);

    int updateByExample(@Param("record") UserPlat record, @Param("example") UserPlatExample example);

    int updateByPrimaryKeySelective(UserPlat record);

    int updateByPrimaryKey(UserPlat record);
}