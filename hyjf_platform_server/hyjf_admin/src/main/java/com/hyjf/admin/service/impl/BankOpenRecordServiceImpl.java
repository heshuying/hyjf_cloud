/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.response.UserManagerInitResponseBean;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.BankOpenRecordService;
import com.hyjf.am.response.user.BankAccountRecordResponse;
import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class BankOpenRecordServiceImpl implements BankOpenRecordService {
    @Autowired
    private AmUserClient bankOpenRecordClient;

    /**
     * 查找汇付银行开户记录列表
     *
     * @param request
     * @return
     */
    @Override
    public BankAccountRecordResponse findAccountRecordList(AccountRecordRequest request) {
        return bankOpenRecordClient.findAccountRecordList(request);
    }

    /**
     * 查找江西银行开户记录列表
     *
     * @param request
     * @return
     */
    @Override
    public BankAccountRecordResponse findBankAccountRecordList(BankAccountRecordRequest request) {
        return bankOpenRecordClient.findBankAccountRecordList(request);
    }
    @Override
    public UserManagerInitResponseBean initUserManaget(){
        UserManagerInitResponseBean userManagerInitResponseBean = new UserManagerInitResponseBean();
        // 用户属性
        Map<String, String> userPropertys = CacheUtil.getParamNameMap("USER_PROPERTY");
        // 注册平台
        Map<String, String> registPlat = CacheUtil.getParamNameMap("CLIENT");
        userManagerInitResponseBean.setUserPropertys(userPropertys);
        userManagerInitResponseBean.setRegistPlat(registPlat);
        return userManagerInitResponseBean;
    }


}
