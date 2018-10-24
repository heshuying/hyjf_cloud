package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.IncreaseInterestLoan;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestLoanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IncreaseInterestLoanMapper {
    int countByExample(IncreaseInterestLoanExample example);

    int deleteByExample(IncreaseInterestLoanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IncreaseInterestLoan record);

    int insertSelective(IncreaseInterestLoan record);

    List<IncreaseInterestLoan> selectByExample(IncreaseInterestLoanExample example);

    IncreaseInterestLoan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IncreaseInterestLoan record, @Param("example") IncreaseInterestLoanExample example);

    int updateByExample(@Param("record") IncreaseInterestLoan record, @Param("example") IncreaseInterestLoanExample example);

    int updateByPrimaryKeySelective(IncreaseInterestLoan record);

    int updateByPrimaryKey(IncreaseInterestLoan record);
}