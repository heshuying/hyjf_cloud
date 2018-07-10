/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.resquest.admin.BankRedPacketAccountListRequest;
import com.hyjf.am.trade.dao.model.customize.admin.BankMerchantAccountListCustomize;

import java.util.List;

/**
 * @author zhangqingqing
 * @version BankRedPacketAccountService, v0.1 2018/7/10 9:55
 */
public interface BankRedPacketAccountService {

    List<BankMerchantAccountListCustomize> selectRecordList(BankRedPacketAccountListRequest form, int offset, int limit);

    int queryRecordTotal(BankRedPacketAccountListRequest form);
}
