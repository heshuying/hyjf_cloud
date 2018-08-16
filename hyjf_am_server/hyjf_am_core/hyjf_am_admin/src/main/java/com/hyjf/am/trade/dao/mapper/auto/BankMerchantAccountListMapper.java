package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BankMerchantAccountList;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccountListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankMerchantAccountListMapper {
    int countByExample(BankMerchantAccountListExample example);

    int deleteByExample(BankMerchantAccountListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankMerchantAccountList record);

    int insertSelective(BankMerchantAccountList record);

    List<BankMerchantAccountList> selectByExample(BankMerchantAccountListExample example);

    BankMerchantAccountList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankMerchantAccountList record, @Param("example") BankMerchantAccountListExample example);

    int updateByExample(@Param("record") BankMerchantAccountList record, @Param("example") BankMerchantAccountListExample example);

    int updateByPrimaryKeySelective(BankMerchantAccountList record);

    int updateByPrimaryKey(BankMerchantAccountList record);
}