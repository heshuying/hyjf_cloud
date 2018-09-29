package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlan;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BorrowRepayPlanMapper {
    int countByExample(BorrowRepayPlanExample example);

    int deleteByExample(BorrowRepayPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowRepayPlan record);

    int insertSelective(BorrowRepayPlan record);

    List<BorrowRepayPlan> selectByExample(BorrowRepayPlanExample example);

    BorrowRepayPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowRepayPlan record, @Param("example") BorrowRepayPlanExample example);

    int updateByExample(@Param("record") BorrowRepayPlan record, @Param("example") BorrowRepayPlanExample example);

    int updateByPrimaryKeySelective(BorrowRepayPlan record);

    int updateByPrimaryKey(BorrowRepayPlan record);
}