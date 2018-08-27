package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.BorrowFinserCharge;
import com.hyjf.am.trade.dao.model.auto.BorrowFinserChargeExample;

public interface BorrowFinserChargeMapper {
    int countByExample(BorrowFinserChargeExample example);

    int deleteByExample(BorrowFinserChargeExample example);

    int deleteByPrimaryKey(String chargeCd);

    int insert(BorrowFinserCharge record);

    int insertSelective(BorrowFinserCharge record);

    List<BorrowFinserCharge> selectByExample(BorrowFinserChargeExample example);

    BorrowFinserCharge selectByPrimaryKey(String chargeCd);

    int updateByExampleSelective(@Param("record") BorrowFinserCharge record, @Param("example") BorrowFinserChargeExample example);

    int updateByExample(@Param("record") BorrowFinserCharge record, @Param("example") BorrowFinserChargeExample example);

    int updateByPrimaryKeySelective(BorrowFinserCharge record);

    int updateByPrimaryKey(BorrowFinserCharge record);
}