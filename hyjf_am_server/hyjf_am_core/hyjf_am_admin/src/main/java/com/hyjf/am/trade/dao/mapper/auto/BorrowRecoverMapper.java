package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverExample;

public interface BorrowRecoverMapper {
    int countByExample(BorrowRecoverExample example);

    int deleteByExample(BorrowRecoverExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowRecover record);

    int insertSelective(BorrowRecover record);

    List<BorrowRecover> selectByExample(BorrowRecoverExample example);

    BorrowRecover selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowRecover record, @Param("example") BorrowRecoverExample example);

    int updateByExample(@Param("record") BorrowRecover record, @Param("example") BorrowRecoverExample example);

    int updateByPrimaryKeySelective(BorrowRecover record);

    int updateByPrimaryKey(BorrowRecover record);
}