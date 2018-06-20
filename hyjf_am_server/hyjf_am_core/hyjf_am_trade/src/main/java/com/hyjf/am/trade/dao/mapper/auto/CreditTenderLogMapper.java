package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CreditTenderLogMapper {
    int countByExample(CreditTenderLogExample example);

    int deleteByExample(CreditTenderLogExample example);

    int deleteByPrimaryKey(Integer assignId);

    int insert(CreditTenderLog record);

    int insertSelective(CreditTenderLog record);

    List<CreditTenderLog> selectByExample(CreditTenderLogExample example);

    CreditTenderLog selectByPrimaryKey(Integer assignId);

    int updateByExampleSelective(@Param("record") CreditTenderLog record, @Param("example") CreditTenderLogExample example);

    int updateByExample(@Param("record") CreditTenderLog record, @Param("example") CreditTenderLogExample example);

    int updateByPrimaryKeySelective(CreditTenderLog record);

    int updateByPrimaryKey(CreditTenderLog record);
}