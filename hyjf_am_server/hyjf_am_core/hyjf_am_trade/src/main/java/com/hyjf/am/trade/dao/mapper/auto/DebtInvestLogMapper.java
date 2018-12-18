package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtInvestLog;
import com.hyjf.am.trade.dao.model.auto.DebtInvestLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DebtInvestLogMapper {
    int countByExample(DebtInvestLogExample example);

    int deleteByExample(DebtInvestLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtInvestLog record);

    int insertSelective(DebtInvestLog record);

    List<DebtInvestLog> selectByExample(DebtInvestLogExample example);

    DebtInvestLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtInvestLog record, @Param("example") DebtInvestLogExample example);

    int updateByExample(@Param("record") DebtInvestLog record, @Param("example") DebtInvestLogExample example);

    int updateByPrimaryKeySelective(DebtInvestLog record);

    int updateByPrimaryKey(DebtInvestLog record);
}