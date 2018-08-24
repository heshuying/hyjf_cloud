package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.UserContact;
import com.hyjf.am.user.dao.model.auto.UserContactExample;

public interface UserContactMapper {
    int countByExample(UserContactExample example);

    int deleteByExample(UserContactExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UserContact record);

    int insertSelective(UserContact record);

    List<UserContact> selectByExample(UserContactExample example);

    UserContact selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UserContact record, @Param("example") UserContactExample example);

    int updateByExample(@Param("record") UserContact record, @Param("example") UserContactExample example);

    int updateByPrimaryKeySelective(UserContact record);

    int updateByPrimaryKey(UserContact record);
}