package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtPlanInfoStaticCount;
import com.hyjf.am.trade.dao.model.auto.DebtPlanInfoStaticCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtPlanInfoStaticCountMapper {
    int countByExample(DebtPlanInfoStaticCountExample example);

    int deleteByExample(DebtPlanInfoStaticCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtPlanInfoStaticCount record);

    int insertSelective(DebtPlanInfoStaticCount record);

    List<DebtPlanInfoStaticCount> selectByExample(DebtPlanInfoStaticCountExample example);

    DebtPlanInfoStaticCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtPlanInfoStaticCount record, @Param("example") DebtPlanInfoStaticCountExample example);

    int updateByExample(@Param("record") DebtPlanInfoStaticCount record, @Param("example") DebtPlanInfoStaticCountExample example);

    int updateByPrimaryKeySelective(DebtPlanInfoStaticCount record);

    int updateByPrimaryKey(DebtPlanInfoStaticCount record);
}