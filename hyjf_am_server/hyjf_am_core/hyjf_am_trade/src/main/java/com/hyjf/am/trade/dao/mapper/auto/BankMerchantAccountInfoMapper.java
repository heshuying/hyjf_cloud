package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BankMerchantAccountInfo;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccountInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankMerchantAccountInfoMapper {
    int countByExample(BankMerchantAccountInfoExample example);

    int deleteByExample(BankMerchantAccountInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankMerchantAccountInfo record);

    int insertSelective(BankMerchantAccountInfo record);

    List<BankMerchantAccountInfo> selectByExample(BankMerchantAccountInfoExample example);

    BankMerchantAccountInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankMerchantAccountInfo record, @Param("example") BankMerchantAccountInfoExample example);

    int updateByExample(@Param("record") BankMerchantAccountInfo record, @Param("example") BankMerchantAccountInfoExample example);

    int updateByPrimaryKeySelective(BankMerchantAccountInfo record);

    int updateByPrimaryKey(BankMerchantAccountInfo record);
}