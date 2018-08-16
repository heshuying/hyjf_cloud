package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.NifaReceivedPayments;
import com.hyjf.am.trade.dao.model.auto.NifaReceivedPaymentsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NifaReceivedPaymentsMapper {
    int countByExample(NifaReceivedPaymentsExample example);

    int deleteByExample(NifaReceivedPaymentsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NifaReceivedPayments record);

    int insertSelective(NifaReceivedPayments record);

    List<NifaReceivedPayments> selectByExample(NifaReceivedPaymentsExample example);

    NifaReceivedPayments selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NifaReceivedPayments record, @Param("example") NifaReceivedPaymentsExample example);

    int updateByExample(@Param("record") NifaReceivedPayments record, @Param("example") NifaReceivedPaymentsExample example);

    int updateByPrimaryKeySelective(NifaReceivedPayments record);

    int updateByPrimaryKey(NifaReceivedPayments record);
}