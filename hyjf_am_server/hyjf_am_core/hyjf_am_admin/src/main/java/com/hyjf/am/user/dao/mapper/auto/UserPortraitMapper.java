package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.UserPortrait;
import com.hyjf.am.user.dao.model.auto.UserPortraitExample;

public interface UserPortraitMapper {
    int countByExample(UserPortraitExample example);

    int deleteByExample(UserPortraitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserPortrait record);

    int insertSelective(UserPortrait record);

    List<UserPortrait> selectByExample(UserPortraitExample example);

    UserPortrait selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserPortrait record, @Param("example") UserPortraitExample example);

    int updateByExample(@Param("record") UserPortrait record, @Param("example") UserPortraitExample example);

    int updateByPrimaryKeySelective(UserPortrait record);

    int updateByPrimaryKey(UserPortrait record);
}