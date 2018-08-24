package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.DebtLoan;
import com.hyjf.am.trade.dao.model.auto.DebtLoanExample;

public interface DebtLoanMapper {
    int countByExample(DebtLoanExample example);

    int deleteByExample(DebtLoanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtLoan record);

    int insertSelective(DebtLoan record);

    List<DebtLoan> selectByExample(DebtLoanExample example);

    DebtLoan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtLoan record, @Param("example") DebtLoanExample example);

    int updateByExample(@Param("record") DebtLoan record, @Param("example") DebtLoanExample example);

    int updateByPrimaryKeySelective(DebtLoan record);

    int updateByPrimaryKey(DebtLoan record);
}