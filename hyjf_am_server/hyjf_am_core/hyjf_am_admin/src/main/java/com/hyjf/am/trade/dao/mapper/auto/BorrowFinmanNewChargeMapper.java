package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.BorrowFinmanNewCharge;
import com.hyjf.am.trade.dao.model.auto.BorrowFinmanNewChargeExample;

public interface BorrowFinmanNewChargeMapper {
    int countByExample(BorrowFinmanNewChargeExample example);

    int deleteByExample(BorrowFinmanNewChargeExample example);

    int deleteByPrimaryKey(String manChargeCd);

    int insert(BorrowFinmanNewCharge record);

    int insertSelective(BorrowFinmanNewCharge record);

    List<BorrowFinmanNewCharge> selectByExample(BorrowFinmanNewChargeExample example);

    BorrowFinmanNewCharge selectByPrimaryKey(String manChargeCd);

    int updateByExampleSelective(@Param("record") BorrowFinmanNewCharge record, @Param("example") BorrowFinmanNewChargeExample example);

    int updateByExample(@Param("record") BorrowFinmanNewCharge record, @Param("example") BorrowFinmanNewChargeExample example);

    int updateByPrimaryKeySelective(BorrowFinmanNewCharge record);

    int updateByPrimaryKey(BorrowFinmanNewCharge record);
}