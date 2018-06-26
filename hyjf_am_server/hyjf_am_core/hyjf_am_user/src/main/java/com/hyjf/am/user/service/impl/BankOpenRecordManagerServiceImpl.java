/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.user.dao.mapper.customize.BankOpenRecordCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.BankOpenAccountRecordCustomize;
import com.hyjf.am.user.service.BankOpenRecordManagerService;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class BankOpenRecordManagerServiceImpl implements BankOpenRecordManagerService {
    @Autowired
    public BankOpenRecordCustomizeMapper bankOpenRecordCustomizeMapper;
    /**
     * 查找江西银行开户记录
     *
     * @return
     */
    @Override
    public List<BankOpenAccountRecordCustomize> selectBankAccountList(Map<String,String> mapParam,int limitStart, int limitEnd){
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", String.valueOf(limitStart));
        }
        if (limitEnd > 0) {
            mapParam.put("limitEnd", String.valueOf(limitEnd));
        }
        List<BankOpenAccountRecordCustomize> listBankAccountRecord = bankOpenRecordCustomizeMapper.selectBankAccountList(mapParam);
        if (CollectionUtils.isNotEmpty(listBankAccountRecord)) {
            Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            for (BankOpenAccountRecordCustomize bankOpenAccountRecordCustomize : listBankAccountRecord) {
                bankOpenAccountRecordCustomize.setAccountStatus(accountStatus.getOrDefault(bankOpenAccountRecordCustomize.getAccountStatus(),null));
                bankOpenAccountRecordCustomize.setOpenAccountPlat(client.getOrDefault(bankOpenAccountRecordCustomize.getOpenAccountPlat(),null));
            }
        }
        return listBankAccountRecord;
    }

    /**
     * 查找江西银行开户记录
     *
     * @return
     */
    public List<BankOpenAccountRecordCustomize> selectAccountList(Map<String,String> mapParam,int limitStart, int limitEnd){
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", String.valueOf(limitStart));
        }
        if (limitEnd > 0) {
            mapParam.put("limitEnd", String.valueOf(limitEnd));
        }
        List<BankOpenAccountRecordCustomize> listBankAccountRecord = bankOpenRecordCustomizeMapper.selectAccountList(mapParam);
        if (CollectionUtils.isNotEmpty(listBankAccountRecord)) {
            Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
            Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
            Map<String, String> userproperty = CacheUtil.getParamNameMap("USER_PROPERTY");
            for (BankOpenAccountRecordCustomize bankOpenAccountRecordCustomize : listBankAccountRecord) {
                bankOpenAccountRecordCustomize.setAccountStatus(accountStatus.getOrDefault(bankOpenAccountRecordCustomize.getAccountStatus(),null));
                bankOpenAccountRecordCustomize.setOpenAccountPlat(client.getOrDefault(bankOpenAccountRecordCustomize.getOpenAccountPlat(),null));
                bankOpenAccountRecordCustomize.setUserProperty(userproperty.getOrDefault(bankOpenAccountRecordCustomize.getUserProperty(),null));
            }
        }
        return listBankAccountRecord;
    }
    /**
     * 查找江西银行开户记录数
     * @return
     */
    @Override
    public int countBankRecordTotal(Map<String,String> mapParam){
//        Map<String,String> mapParam =setBankAccountRecordRequest(request);
        Integer intCount = bankOpenRecordCustomizeMapper.countBankRecordTotal(mapParam);
        return  intCount.intValue();
    }

    /**
     * 查找汇付银行的开户记录数
     * @return
     */
    @Override
    public int countRecordTotal(Map<String,String> mapParam){
//        Map<String,String> mapParam = setAccountRecordRequest(request);
        Integer integerCount = bankOpenRecordCustomizeMapper.countRecordTotal(mapParam);
        return integerCount.intValue();
    }


}
