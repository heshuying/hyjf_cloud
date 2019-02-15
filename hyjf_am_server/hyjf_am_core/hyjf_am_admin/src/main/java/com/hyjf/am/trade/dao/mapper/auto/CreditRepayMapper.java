package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.CreditRepay;
import com.hyjf.am.trade.dao.model.auto.CreditRepayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreditRepayMapper {
    int countByExample(CreditRepayExample example);

    int deleteByExample(CreditRepayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CreditRepay record);

    int insertSelective(CreditRepay record);

    List<CreditRepay> selectByExample(CreditRepayExample example);

    CreditRepay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CreditRepay record, @Param("example") CreditRepayExample example);

    int updateByExample(@Param("record") CreditRepay record, @Param("example") CreditRepayExample example);

    int updateByPrimaryKeySelective(CreditRepay record);

    int updateByPrimaryKey(CreditRepay record);
}