package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.DebtPlan;
import com.hyjf.am.trade.dao.model.auto.DebtPlanExample;
import com.hyjf.am.trade.dao.model.auto.DebtPlanWithBLOBs;

public interface DebtPlanMapper {
    int countByExample(DebtPlanExample example);

    int deleteByExample(DebtPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtPlanWithBLOBs record);

    int insertSelective(DebtPlanWithBLOBs record);

    List<DebtPlanWithBLOBs> selectByExampleWithBLOBs(DebtPlanExample example);

    List<DebtPlan> selectByExample(DebtPlanExample example);

    DebtPlanWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtPlanWithBLOBs record, @Param("example") DebtPlanExample example);

    int updateByExampleWithBLOBs(@Param("record") DebtPlanWithBLOBs record, @Param("example") DebtPlanExample example);

    int updateByExample(@Param("record") DebtPlan record, @Param("example") DebtPlanExample example);

    int updateByPrimaryKeySelective(DebtPlanWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(DebtPlanWithBLOBs record);

    int updateByPrimaryKey(DebtPlan record);
}