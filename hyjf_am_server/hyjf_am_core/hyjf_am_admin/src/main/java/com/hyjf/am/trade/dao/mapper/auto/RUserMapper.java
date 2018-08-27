package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.RUser;
import com.hyjf.am.trade.dao.model.auto.RUserExample;

public interface RUserMapper {
    int countByExample(RUserExample example);

    int deleteByExample(RUserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(RUser record);

    int insertSelective(RUser record);

    List<RUser> selectByExample(RUserExample example);

    RUser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") RUser record, @Param("example") RUserExample example);

    int updateByExample(@Param("record") RUser record, @Param("example") RUserExample example);

    int updateByPrimaryKeySelective(RUser record);

    int updateByPrimaryKey(RUser record);
}