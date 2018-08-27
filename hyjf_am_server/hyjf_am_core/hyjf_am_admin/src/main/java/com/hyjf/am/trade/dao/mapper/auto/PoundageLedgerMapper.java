package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.PoundageLedger;
import com.hyjf.am.trade.dao.model.auto.PoundageLedgerExample;

public interface PoundageLedgerMapper {
    int countByExample(PoundageLedgerExample example);

    int deleteByExample(PoundageLedgerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PoundageLedger record);

    int insertSelective(PoundageLedger record);

    List<PoundageLedger> selectByExample(PoundageLedgerExample example);

    PoundageLedger selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PoundageLedger record, @Param("example") PoundageLedgerExample example);

    int updateByExample(@Param("record") PoundageLedger record, @Param("example") PoundageLedgerExample example);

    int updateByPrimaryKeySelective(PoundageLedger record);

    int updateByPrimaryKey(PoundageLedger record);
}