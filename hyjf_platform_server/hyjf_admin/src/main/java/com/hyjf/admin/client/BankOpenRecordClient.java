/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import java.util.List;

import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.vo.user.BankOpenAccountRecordVO;

/**
 * @author nixiaoling
 * @version UserCenterClient, v0.1 2018/6/20 15:37
 */
public interface BankOpenRecordClient {

    /**
     * 查找汇付银行开户记录列表
     *
     * @param request
     * @return
     */
    List<BankOpenAccountRecordVO> findAccountRecordList(AccountRecordRequest request);

    /**
     * 查找江西银行开户记录列表
     *
     * @param request
     * @return
     */
    List<BankOpenAccountRecordVO> findBankAccountRecordList(BankAccountRecordRequest request);
}