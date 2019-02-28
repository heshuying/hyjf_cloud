package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BankRedPacketAccountListRequest;
import com.hyjf.am.trade.dao.model.customize.BankMerchantAccountListCustomize;

import java.util.List;

public interface BankMerchantAccountListCustomizeMapper {
    List<BankMerchantAccountListCustomize> selectRecordList(BankRedPacketAccountListRequest form);

    int countByExample(BankRedPacketAccountListRequest form);

}