package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.CreditRepayLog;
import com.hyjf.am.trade.dao.model.auto.CreditRepayLogExample;

public interface CreditRepayLogMapper {
    int countByExample(CreditRepayLogExample example);

    int deleteByExample(CreditRepayLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CreditRepayLog record);

    int insertSelective(CreditRepayLog record);

    List<CreditRepayLog> selectByExampleWithBLOBs(CreditRepayLogExample example);

    List<CreditRepayLog> selectByExample(CreditRepayLogExample example);

    CreditRepayLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CreditRepayLog record, @Param("example") CreditRepayLogExample example);

    int updateByExampleWithBLOBs(@Param("record") CreditRepayLog record, @Param("example") CreditRepayLogExample example);

    int updateByExample(@Param("record") CreditRepayLog record, @Param("example") CreditRepayLogExample example);

    int updateByPrimaryKeySelective(CreditRepayLog record);

    int updateByPrimaryKeyWithBLOBs(CreditRepayLog record);

    int updateByPrimaryKey(CreditRepayLog record);
}