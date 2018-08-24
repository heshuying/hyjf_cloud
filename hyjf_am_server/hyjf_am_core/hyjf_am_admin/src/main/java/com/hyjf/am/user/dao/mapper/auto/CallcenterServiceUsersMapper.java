package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.CallcenterServiceUsers;
import com.hyjf.am.user.dao.model.auto.CallcenterServiceUsersExample;

public interface CallcenterServiceUsersMapper {
    int countByExample(CallcenterServiceUsersExample example);

    int deleteByExample(CallcenterServiceUsersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CallcenterServiceUsers record);

    int insertSelective(CallcenterServiceUsers record);

    List<CallcenterServiceUsers> selectByExample(CallcenterServiceUsersExample example);

    CallcenterServiceUsers selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CallcenterServiceUsers record, @Param("example") CallcenterServiceUsersExample example);

    int updateByExample(@Param("record") CallcenterServiceUsers record, @Param("example") CallcenterServiceUsersExample example);

    int updateByPrimaryKeySelective(CallcenterServiceUsers record);

    int updateByPrimaryKey(CallcenterServiceUsers record);
}