package com.hyjf.wbs.trade.dao.mapper.auto;


import com.hyjf.wbs.trade.dao.model.auto.HjhPlan;
import com.hyjf.wbs.trade.dao.model.auto.HjhPlanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HjhPlanMapper {

    List<HjhPlan> selectByExample(HjhPlanExample example);
}