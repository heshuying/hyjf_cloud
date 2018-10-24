package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtCredit;
import com.hyjf.am.trade.dao.model.auto.DebtCreditExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DebtCreditMapper {
    int countByExample(DebtCreditExample example);

    int deleteByExample(DebtCreditExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtCredit record);

    int insertSelective(DebtCredit record);

    List<DebtCredit> selectByExample(DebtCreditExample example);

    DebtCredit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtCredit record, @Param("example") DebtCreditExample example);

    int updateByExample(@Param("record") DebtCredit record, @Param("example") DebtCreditExample example);

    int updateByPrimaryKeySelective(DebtCredit record);

    int updateByPrimaryKey(DebtCredit record);
}