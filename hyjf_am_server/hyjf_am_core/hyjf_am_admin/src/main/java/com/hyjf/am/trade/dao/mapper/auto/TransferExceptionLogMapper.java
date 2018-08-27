package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.TransferExceptionLog;
import com.hyjf.am.trade.dao.model.auto.TransferExceptionLogExample;
import com.hyjf.am.trade.dao.model.auto.TransferExceptionLogWithBLOBs;

public interface TransferExceptionLogMapper {
    int countByExample(TransferExceptionLogExample example);

    int deleteByExample(TransferExceptionLogExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(TransferExceptionLogWithBLOBs record);

    int insertSelective(TransferExceptionLogWithBLOBs record);

    List<TransferExceptionLogWithBLOBs> selectByExampleWithBLOBs(TransferExceptionLogExample example);

    List<TransferExceptionLog> selectByExample(TransferExceptionLogExample example);

    TransferExceptionLogWithBLOBs selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") TransferExceptionLogWithBLOBs record, @Param("example") TransferExceptionLogExample example);

    int updateByExampleWithBLOBs(@Param("record") TransferExceptionLogWithBLOBs record, @Param("example") TransferExceptionLogExample example);

    int updateByExample(@Param("record") TransferExceptionLog record, @Param("example") TransferExceptionLogExample example);

    int updateByPrimaryKeySelective(TransferExceptionLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TransferExceptionLogWithBLOBs record);

    int updateByPrimaryKey(TransferExceptionLog record);
}