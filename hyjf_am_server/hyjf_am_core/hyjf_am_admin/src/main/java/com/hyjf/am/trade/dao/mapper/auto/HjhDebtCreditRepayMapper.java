package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditRepay;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditRepayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HjhDebtCreditRepayMapper {
    int countByExample(HjhDebtCreditRepayExample example);

    int deleteByExample(HjhDebtCreditRepayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhDebtCreditRepay record);

    int insertSelective(HjhDebtCreditRepay record);

    List<HjhDebtCreditRepay> selectByExample(HjhDebtCreditRepayExample example);

    HjhDebtCreditRepay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhDebtCreditRepay record, @Param("example") HjhDebtCreditRepayExample example);

    int updateByExample(@Param("record") HjhDebtCreditRepay record, @Param("example") HjhDebtCreditRepayExample example);

    int updateByPrimaryKeySelective(HjhDebtCreditRepay record);

    int updateByPrimaryKey(HjhDebtCreditRepay record);
}