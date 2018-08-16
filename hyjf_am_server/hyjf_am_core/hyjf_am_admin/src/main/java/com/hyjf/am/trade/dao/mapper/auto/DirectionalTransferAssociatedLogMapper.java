package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DirectionalTransferAssociatedLog;
import com.hyjf.am.trade.dao.model.auto.DirectionalTransferAssociatedLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DirectionalTransferAssociatedLogMapper {
    int countByExample(DirectionalTransferAssociatedLogExample example);

    int deleteByExample(DirectionalTransferAssociatedLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DirectionalTransferAssociatedLog record);

    int insertSelective(DirectionalTransferAssociatedLog record);

    List<DirectionalTransferAssociatedLog> selectByExample(DirectionalTransferAssociatedLogExample example);

    DirectionalTransferAssociatedLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DirectionalTransferAssociatedLog record, @Param("example") DirectionalTransferAssociatedLogExample example);

    int updateByExample(@Param("record") DirectionalTransferAssociatedLog record, @Param("example") DirectionalTransferAssociatedLogExample example);

    int updateByPrimaryKeySelective(DirectionalTransferAssociatedLog record);

    int updateByPrimaryKey(DirectionalTransferAssociatedLog record);
}