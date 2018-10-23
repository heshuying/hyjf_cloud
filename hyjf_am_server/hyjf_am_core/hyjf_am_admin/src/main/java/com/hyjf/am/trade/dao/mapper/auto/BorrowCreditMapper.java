package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.BorrowCreditExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BorrowCreditMapper {
    int countByExample(BorrowCreditExample example);

    int deleteByExample(BorrowCreditExample example);

    int deleteByPrimaryKey(Integer creditId);

    int insert(BorrowCredit record);

    int insertSelective(BorrowCredit record);

    List<BorrowCredit> selectByExample(BorrowCreditExample example);

    BorrowCredit selectByPrimaryKey(Integer creditId);

    int updateByExampleSelective(@Param("record") BorrowCredit record, @Param("example") BorrowCreditExample example);

    int updateByExample(@Param("record") BorrowCredit record, @Param("example") BorrowCreditExample example);

    int updateByPrimaryKeySelective(BorrowCredit record);

    int updateByPrimaryKey(BorrowCredit record);
}