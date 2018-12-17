package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.IncreaseInterestLoanDetail;
import com.hyjf.am.trade.dao.model.auto.IncreaseInterestLoanDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IncreaseInterestLoanDetailMapper {
    int countByExample(IncreaseInterestLoanDetailExample example);

    int deleteByExample(IncreaseInterestLoanDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IncreaseInterestLoanDetail record);

    int insertSelective(IncreaseInterestLoanDetail record);

    List<IncreaseInterestLoanDetail> selectByExample(IncreaseInterestLoanDetailExample example);

    IncreaseInterestLoanDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IncreaseInterestLoanDetail record, @Param("example") IncreaseInterestLoanDetailExample example);

    int updateByExample(@Param("record") IncreaseInterestLoanDetail record, @Param("example") IncreaseInterestLoanDetailExample example);

    int updateByPrimaryKeySelective(IncreaseInterestLoanDetail record);

    int updateByPrimaryKey(IncreaseInterestLoanDetail record);
}