package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowSendType;
import com.hyjf.am.trade.dao.model.auto.BorrowSendTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BorrowSendTypeMapper {
    int countByExample(BorrowSendTypeExample example);

    int deleteByExample(BorrowSendTypeExample example);

    int deleteByPrimaryKey(String sendCd);

    int insert(BorrowSendType record);

    int insertSelective(BorrowSendType record);

    List<BorrowSendType> selectByExample(BorrowSendTypeExample example);

    BorrowSendType selectByPrimaryKey(String sendCd);

    int updateByExampleSelective(@Param("record") BorrowSendType record, @Param("example") BorrowSendTypeExample example);

    int updateByExample(@Param("record") BorrowSendType record, @Param("example") BorrowSendTypeExample example);

    int updateByPrimaryKeySelective(BorrowSendType record);

    int updateByPrimaryKey(BorrowSendType record);
}