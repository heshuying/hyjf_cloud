/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.user.dao.model.customize.BankOpenAccountRecordCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserCenterService, v0.1 2018/6/20 15:34
 */
public interface BankOpenRecordManagerService {
    /**
     * 查找江西银行开户记录
     *
     * @return
     */
    List<BankOpenAccountRecordCustomize> selectBankAccountList(Map<String,String> mapParam,int limitStart, int limitEnd);

    /**
     * 查找江西银行开户记录
     *
     * @return
     */
    List<BankOpenAccountRecordCustomize> selectAccountList(Map<String,String> mapParam,int limitStart, int limitEnd);

    /**
     * 查找江西银行开户记录数
     *
     * @return
     */
    int countBankRecordTotal(Map<String,String> mapParam);

    /**
     * 查找汇付银行的开户记录数
     *
     * @return
     */
    int countRecordTotal(Map<String,String> mapParam);
}
