package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmp;
import com.hyjf.am.trade.dao.model.auto.HjhPlanBorrowTmpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HjhPlanBorrowTmpMapper {
    int countByExample(HjhPlanBorrowTmpExample example);

    int deleteByExample(HjhPlanBorrowTmpExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhPlanBorrowTmp record);

    int insertSelective(HjhPlanBorrowTmp record);

    List<HjhPlanBorrowTmp> selectByExample(HjhPlanBorrowTmpExample example);

    HjhPlanBorrowTmp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhPlanBorrowTmp record, @Param("example") HjhPlanBorrowTmpExample example);

    int updateByExample(@Param("record") HjhPlanBorrowTmp record, @Param("example") HjhPlanBorrowTmpExample example);

    int updateByPrimaryKeySelective(HjhPlanBorrowTmp record);

    int updateByPrimaryKey(HjhPlanBorrowTmp record);
}