/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.BankOpenRecordClient;
import com.hyjf.admin.service.BankOpenRecordService;
import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.vo.user.BankOpenAccountRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class BankOpenRecordServiceImpl implements BankOpenRecordService {
    @Autowired
    private BankOpenRecordClient bankOpenRecordClient;

    /**
     * 查找汇付银行开户记录列表
     *
     * @param request
     * @return
     */
    @Override
    public List<BankOpenAccountRecordVO> findAccountRecordList(AccountRecordRequest request) {
        return bankOpenRecordClient.findAccountRecordList(request);
    }

    /**
     * 查找江西银行开户记录列表
     *
     * @param request
     * @return
     */
    @Override
    public List<BankOpenAccountRecordVO> findBankAccountRecordList(BankAccountRecordRequest request) {
        return bankOpenRecordClient.findBankAccountRecordList(request);
    }

}
