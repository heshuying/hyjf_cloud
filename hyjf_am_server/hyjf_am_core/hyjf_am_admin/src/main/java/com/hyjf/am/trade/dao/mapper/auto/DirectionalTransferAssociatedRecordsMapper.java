package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.DirectionalTransferAssociatedRecords;
import com.hyjf.am.trade.dao.model.auto.DirectionalTransferAssociatedRecordsExample;

public interface DirectionalTransferAssociatedRecordsMapper {
    int countByExample(DirectionalTransferAssociatedRecordsExample example);

    int deleteByExample(DirectionalTransferAssociatedRecordsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DirectionalTransferAssociatedRecords record);

    int insertSelective(DirectionalTransferAssociatedRecords record);

    List<DirectionalTransferAssociatedRecords> selectByExample(DirectionalTransferAssociatedRecordsExample example);

    DirectionalTransferAssociatedRecords selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DirectionalTransferAssociatedRecords record, @Param("example") DirectionalTransferAssociatedRecordsExample example);

    int updateByExample(@Param("record") DirectionalTransferAssociatedRecords record, @Param("example") DirectionalTransferAssociatedRecordsExample example);

    int updateByPrimaryKeySelective(DirectionalTransferAssociatedRecords record);

    int updateByPrimaryKey(DirectionalTransferAssociatedRecords record);
}