package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.RepaymentPlan;
import com.hyjf.am.user.dao.model.auto.RepaymentPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RepaymentPlanMapper {
    int countByExample(RepaymentPlanExample example);

    int deleteByExample(RepaymentPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RepaymentPlan record);

    int insertSelective(RepaymentPlan record);

    List<RepaymentPlan> selectByExample(RepaymentPlanExample example);

    RepaymentPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RepaymentPlan record, @Param("example") RepaymentPlanExample example);

    int updateByExample(@Param("record") RepaymentPlan record, @Param("example") RepaymentPlanExample example);

    int updateByPrimaryKeySelective(RepaymentPlan record);

    int updateByPrimaryKey(RepaymentPlan record);
}