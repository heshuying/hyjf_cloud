package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.ROaUsers;
import com.hyjf.am.trade.dao.model.auto.ROaUsersExample;

public interface ROaUsersMapper {
    int countByExample(ROaUsersExample example);

    int deleteByExample(ROaUsersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ROaUsers record);

    int insertSelective(ROaUsers record);

    List<ROaUsers> selectByExample(ROaUsersExample example);

    ROaUsers selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ROaUsers record, @Param("example") ROaUsersExample example);

    int updateByExample(@Param("record") ROaUsers record, @Param("example") ROaUsersExample example);

    int updateByPrimaryKeySelective(ROaUsers record);

    int updateByPrimaryKey(ROaUsers record);
}