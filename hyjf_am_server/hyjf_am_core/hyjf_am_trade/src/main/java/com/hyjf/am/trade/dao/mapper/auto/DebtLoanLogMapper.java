package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtLoanLog;
import com.hyjf.am.trade.dao.model.auto.DebtLoanLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtLoanLogMapper {
    int countByExample(DebtLoanLogExample example);

    int deleteByExample(DebtLoanLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtLoanLog record);

    int insertSelective(DebtLoanLog record);

    List<DebtLoanLog> selectByExample(DebtLoanLogExample example);

    DebtLoanLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtLoanLog record, @Param("example") DebtLoanLogExample example);

    int updateByExample(@Param("record") DebtLoanLog record, @Param("example") DebtLoanLogExample example);

    int updateByPrimaryKeySelective(DebtLoanLog record);

    int updateByPrimaryKey(DebtLoanLog record);
}