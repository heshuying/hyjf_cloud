package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.auto.HjhPlanExample;
import com.hyjf.am.trade.dao.model.auto.HjhPlanWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HjhPlanMapper {
    int countByExample(HjhPlanExample example);

    int deleteByExample(HjhPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhPlanWithBLOBs record);

    int insertSelective(HjhPlanWithBLOBs record);

    List<HjhPlanWithBLOBs> selectByExampleWithBLOBs(HjhPlanExample example);

    List<HjhPlan> selectByExample(HjhPlanExample example);

    HjhPlanWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhPlanWithBLOBs record, @Param("example") HjhPlanExample example);

    int updateByExampleWithBLOBs(@Param("record") HjhPlanWithBLOBs record, @Param("example") HjhPlanExample example);

    int updateByExample(@Param("record") HjhPlan record, @Param("example") HjhPlanExample example);

    int updateByPrimaryKeySelective(HjhPlanWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(HjhPlanWithBLOBs record);

    int updateByPrimaryKey(HjhPlan record);
}