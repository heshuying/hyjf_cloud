package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.IncreaseInterestInvest;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestInvestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IncreaseInterestInvestMapper {
    int countByExample(IncreaseInterestInvestExample example);

    int deleteByExample(IncreaseInterestInvestExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IncreaseInterestInvest record);

    int insertSelective(IncreaseInterestInvest record);

    List<IncreaseInterestInvest> selectByExample(IncreaseInterestInvestExample example);

    IncreaseInterestInvest selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IncreaseInterestInvest record, @Param("example") IncreaseInterestInvestExample example);

    int updateByExample(@Param("record") IncreaseInterestInvest record, @Param("example") IncreaseInterestInvestExample example);

    int updateByPrimaryKeySelective(IncreaseInterestInvest record);

    int updateByPrimaryKey(IncreaseInterestInvest record);
}