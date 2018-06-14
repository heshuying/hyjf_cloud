package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.CallcenterServiceUsers;
import com.hyjf.am.user.dao.model.auto.CallcenterServiceUsersExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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