/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.user.BankAccountRecordResponse;
import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.vo.user.BankOpenAccountRecordVO;

import java.util.List;

/**
 * @author nxl
 * @version UserCenterService, v0.1 2018/6/20 15:34
 */
public interface BankOpenRecordService {
    /**
     * 查找汇付银行开户记录列表
     *
     * @param request
     * @return
     */
    BankAccountRecordResponse findAccountRecordList(AccountRecordRequest request);

    /**
     * 查找江西银行开户记录列表
     *
     * @param request
     * @return
     */
    BankAccountRecordResponse findBankAccountRecordList(BankAccountRecordRequest request);

}
