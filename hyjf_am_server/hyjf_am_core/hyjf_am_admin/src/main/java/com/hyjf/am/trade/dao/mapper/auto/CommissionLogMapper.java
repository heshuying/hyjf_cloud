package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.CommissionLog;
import com.hyjf.am.trade.dao.model.auto.CommissionLogExample;

public interface CommissionLogMapper {
    int countByExample(CommissionLogExample example);

    int deleteByExample(CommissionLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommissionLog record);

    int insertSelective(CommissionLog record);

    List<CommissionLog> selectByExample(CommissionLogExample example);

    CommissionLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommissionLog record, @Param("example") CommissionLogExample example);

    int updateByExample(@Param("record") CommissionLog record, @Param("example") CommissionLogExample example);

    int updateByPrimaryKeySelective(CommissionLog record);

    int updateByPrimaryKey(CommissionLog record);
}