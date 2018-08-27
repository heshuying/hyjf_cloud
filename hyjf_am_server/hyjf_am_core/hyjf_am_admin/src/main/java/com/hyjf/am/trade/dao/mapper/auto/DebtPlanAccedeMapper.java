package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.DebtPlanAccede;
import com.hyjf.am.trade.dao.model.auto.DebtPlanAccedeExample;

public interface DebtPlanAccedeMapper {
    int countByExample(DebtPlanAccedeExample example);

    int deleteByExample(DebtPlanAccedeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtPlanAccede record);

    int insertSelective(DebtPlanAccede record);

    List<DebtPlanAccede> selectByExample(DebtPlanAccedeExample example);

    DebtPlanAccede selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtPlanAccede record, @Param("example") DebtPlanAccedeExample example);

    int updateByExample(@Param("record") DebtPlanAccede record, @Param("example") DebtPlanAccedeExample example);

    int updateByPrimaryKeySelective(DebtPlanAccede record);

    int updateByPrimaryKey(DebtPlanAccede record);
}