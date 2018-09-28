package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.BankMerchantAccountList;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccountListExample;
import com.hyjf.am.trade.dao.model.customize.BankMerchantAccountListCustomize;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankMerchantAccountListCustomizeMapper {
    int countByExample(BankMerchantAccountListExample example);

    int deleteByExample(BankMerchantAccountListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankMerchantAccountList record);

    int insertSelective(BankMerchantAccountList record);

    List<BankMerchantAccountListCustomize> selectByExample(BankMerchantAccountListExample example);

    BankMerchantAccountListCustomize selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankMerchantAccountList record, @Param("example") BankMerchantAccountListExample example);

    int updateByExample(@Param("record") BankMerchantAccountList record, @Param("example") BankMerchantAccountListExample example);

    int updateByPrimaryKeySelective(BankMerchantAccountList record);

    int updateByPrimaryKey(BankMerchantAccountList record);
}