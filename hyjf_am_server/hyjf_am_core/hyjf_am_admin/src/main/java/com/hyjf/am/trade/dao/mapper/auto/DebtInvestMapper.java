package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtInvest;
import com.hyjf.am.trade.dao.model.auto.DebtInvestExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DebtInvestMapper {
    int countByExample(DebtInvestExample example);

    int deleteByExample(DebtInvestExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtInvest record);

    int insertSelective(DebtInvest record);

    List<DebtInvest> selectByExample(DebtInvestExample example);

    DebtInvest selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtInvest record, @Param("example") DebtInvestExample example);

    int updateByExample(@Param("record") DebtInvest record, @Param("example") DebtInvestExample example);

    int updateByPrimaryKeySelective(DebtInvest record);

    int updateByPrimaryKey(DebtInvest record);
}