package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ProducerTransactionMessage;
import com.hyjf.am.trade.dao.model.auto.ProducerTransactionMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProducerTransactionMessageMapper {
    int countByExample(ProducerTransactionMessageExample example);

    int deleteByExample(ProducerTransactionMessageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProducerTransactionMessage record);

    int insertSelective(ProducerTransactionMessage record);

    List<ProducerTransactionMessage> selectByExample(ProducerTransactionMessageExample example);

    ProducerTransactionMessage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProducerTransactionMessage record, @Param("example") ProducerTransactionMessageExample example);

    int updateByExample(@Param("record") ProducerTransactionMessage record, @Param("example") ProducerTransactionMessageExample example);

    int updateByPrimaryKeySelective(ProducerTransactionMessage record);

    int updateByPrimaryKey(ProducerTransactionMessage record);
}