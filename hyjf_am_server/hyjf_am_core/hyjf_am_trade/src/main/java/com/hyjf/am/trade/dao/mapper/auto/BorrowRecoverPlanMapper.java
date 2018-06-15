package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowRecoverPlanMapper {
    int countByExample(BorrowRecoverPlanExample example);

    int deleteByExample(BorrowRecoverPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowRecoverPlan record);

    int insertSelective(BorrowRecoverPlan record);

    List<BorrowRecoverPlan> selectByExample(BorrowRecoverPlanExample example);

    BorrowRecoverPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowRecoverPlan record, @Param("example") BorrowRecoverPlanExample example);

    int updateByExample(@Param("record") BorrowRecoverPlan record, @Param("example") BorrowRecoverPlanExample example);

    int updateByPrimaryKeySelective(BorrowRecoverPlan record);

    int updateByPrimaryKey(BorrowRecoverPlan record);
}