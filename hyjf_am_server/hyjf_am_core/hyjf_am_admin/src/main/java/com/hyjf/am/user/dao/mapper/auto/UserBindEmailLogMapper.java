package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.UserBindEmailLog;
import com.hyjf.am.user.dao.model.auto.UserBindEmailLogExample;

public interface UserBindEmailLogMapper {
    int countByExample(UserBindEmailLogExample example);

    int deleteByExample(UserBindEmailLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserBindEmailLog record);

    int insertSelective(UserBindEmailLog record);

    List<UserBindEmailLog> selectByExample(UserBindEmailLogExample example);

    UserBindEmailLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserBindEmailLog record, @Param("example") UserBindEmailLogExample example);

    int updateByExample(@Param("record") UserBindEmailLog record, @Param("example") UserBindEmailLogExample example);

    int updateByPrimaryKeySelective(UserBindEmailLog record);

    int updateByPrimaryKey(UserBindEmailLog record);
}