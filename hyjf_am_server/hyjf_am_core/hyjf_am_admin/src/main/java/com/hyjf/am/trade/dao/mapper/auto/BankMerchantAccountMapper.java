package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BankMerchantAccount;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankMerchantAccountMapper {
    int countByExample(BankMerchantAccountExample example);

    int deleteByExample(BankMerchantAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankMerchantAccount record);

    int insertSelective(BankMerchantAccount record);

    List<BankMerchantAccount> selectByExample(BankMerchantAccountExample example);

    BankMerchantAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankMerchantAccount record, @Param("example") BankMerchantAccountExample example);

    int updateByExample(@Param("record") BankMerchantAccount record, @Param("example") BankMerchantAccountExample example);

    int updateByPrimaryKeySelective(BankMerchantAccount record);

    int updateByPrimaryKey(BankMerchantAccount record);
}