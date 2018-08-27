package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.DebtPlanBorrow;
import com.hyjf.am.trade.dao.model.auto.DebtPlanBorrowExample;

public interface DebtPlanBorrowMapper {
    int countByExample(DebtPlanBorrowExample example);

    int deleteByExample(DebtPlanBorrowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtPlanBorrow record);

    int insertSelective(DebtPlanBorrow record);

    List<DebtPlanBorrow> selectByExample(DebtPlanBorrowExample example);

    DebtPlanBorrow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtPlanBorrow record, @Param("example") DebtPlanBorrowExample example);

    int updateByExample(@Param("record") DebtPlanBorrow record, @Param("example") DebtPlanBorrowExample example);

    int updateByPrimaryKeySelective(DebtPlanBorrow record);

    int updateByPrimaryKey(DebtPlanBorrow record);
}