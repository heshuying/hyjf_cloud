package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTender;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditTenderExample;

public interface HjhDebtCreditTenderMapper {
    int countByExample(HjhDebtCreditTenderExample example);

    int deleteByExample(HjhDebtCreditTenderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhDebtCreditTender record);

    int insertSelective(HjhDebtCreditTender record);

    List<HjhDebtCreditTender> selectByExample(HjhDebtCreditTenderExample example);

    HjhDebtCreditTender selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhDebtCreditTender record, @Param("example") HjhDebtCreditTenderExample example);

    int updateByExample(@Param("record") HjhDebtCreditTender record, @Param("example") HjhDebtCreditTenderExample example);

    int updateByPrimaryKeySelective(HjhDebtCreditTender record);

    int updateByPrimaryKey(HjhDebtCreditTender record);
}