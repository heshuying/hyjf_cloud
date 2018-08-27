package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.BorrowFinhxfmanCharge;
import com.hyjf.am.trade.dao.model.auto.BorrowFinhxfmanChargeExample;

public interface BorrowFinhxfmanChargeMapper {
    int countByExample(BorrowFinhxfmanChargeExample example);

    int deleteByExample(BorrowFinhxfmanChargeExample example);

    int deleteByPrimaryKey(String manChargeCd);

    int insert(BorrowFinhxfmanCharge record);

    int insertSelective(BorrowFinhxfmanCharge record);

    List<BorrowFinhxfmanCharge> selectByExample(BorrowFinhxfmanChargeExample example);

    BorrowFinhxfmanCharge selectByPrimaryKey(String manChargeCd);

    int updateByExampleSelective(@Param("record") BorrowFinhxfmanCharge record, @Param("example") BorrowFinhxfmanChargeExample example);

    int updateByExample(@Param("record") BorrowFinhxfmanCharge record, @Param("example") BorrowFinhxfmanChargeExample example);

    int updateByPrimaryKeySelective(BorrowFinhxfmanCharge record);

    int updateByPrimaryKey(BorrowFinhxfmanCharge record);
}