package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtLoanDetail;
import com.hyjf.am.trade.dao.model.auto.DebtLoanDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtLoanDetailMapper {
    int countByExample(DebtLoanDetailExample example);

    int deleteByExample(DebtLoanDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtLoanDetail record);

    int insertSelective(DebtLoanDetail record);

    List<DebtLoanDetail> selectByExample(DebtLoanDetailExample example);

    DebtLoanDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtLoanDetail record, @Param("example") DebtLoanDetailExample example);

    int updateByExample(@Param("record") DebtLoanDetail record, @Param("example") DebtLoanDetailExample example);

    int updateByPrimaryKeySelective(DebtLoanDetail record);

    int updateByPrimaryKey(DebtLoanDetail record);
}