/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.user.dao.mapper.customize.BankOpenRecordCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.BankOpenAccountRecordCustomize;
import com.hyjf.am.user.service.BankOpenRecordService;
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
public class BankOpenRecordServiceImpl implements BankOpenRecordService {
    @Autowired
    public BankOpenRecordCustomizeMapper bankOpenRecordCustomizeMapper;
    /**
     * 查找江西银行开户记录
     *
     * @return
     */
    @Override
    public List<BankOpenAccountRecordCustomize> selectBankAccountList(BankAccountRecordRequest request ){
        Map<String,String> mapParam = new HashMap<String,String>();
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
    public List<BankOpenAccountRecordCustomize> selectAccountList(AccountRecordRequest request){
        Map<String,String> mapParam = new HashMap<String,String>();
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
    public int countBankRecordTotal(BankAccountRecordRequest request){
        Map<String,String> mapParam =setBankAccountRecordRequest(request);
        Integer intCount = bankOpenRecordCustomizeMapper.countBankRecordTotal(mapParam);
        return  intCount.intValue();
    }

    /**
     * 查找汇付银行的开户记录数
     * @return
     */
    @Override
    public int countRecordTotal(AccountRecordRequest request){
        Map<String,String> mapParam = setAccountRecordRequest(request);
        Integer integerCount = bankOpenRecordCustomizeMapper.countRecordTotal(mapParam);
        return integerCount.intValue();
    }

    /**
     * 设置汇付参数
     * @param request
     * @return
     */
    private Map<String,String>  setAccountRecordRequest(AccountRecordRequest request){
        Map<String,String> mapRaram = new HashMap<String,String>();
        mapRaram.put("openAccountPlat",request.getOpenAccountPlat());
        mapRaram.put("userName",request.getUserName());
        mapRaram.put("userProperty",request.getUserProperty());
        mapRaram.put("idCard",request.getIdCard());
        mapRaram.put("realName",request.getRealName());
        mapRaram.put("openTimeStart",request.getOpenTimeStart());
        mapRaram.put("openTimeEnd",request.getOpenTimeEnd());
        mapRaram.put("limitEnd",String.valueOf(request.getLimitEnd()));
        mapRaram.put("limitStart",String.valueOf(request.getLimitStart()));
        return mapRaram;

    }

    /**
     * 设置江西参数
     * @param request
     * @return
     */
    private Map<String,String>  setBankAccountRecordRequest(BankAccountRecordRequest request){
        Map<String,String> mapRaram = new HashMap<String,String>();
        mapRaram.put("openAccountPlat",request.getOpenAccountPlat());
        mapRaram.put("userName",request.getUserName());
        mapRaram.put("customerAccount",request.getCustomerAccount());
        mapRaram.put("mobile",request.getMobile());
        mapRaram.put("idCard",request.getIdCard());
        mapRaram.put("realName",request.getRealName());
        mapRaram.put("openTimeStart",request.getOpenTimeStart());
        mapRaram.put("openTimeEnd",request.getOpenTimeEnd());
        mapRaram.put("limitEnd",String.valueOf(request.getLimitEnd()));
        mapRaram.put("limitStart",String.valueOf(request.getLimitStart()));
        return mapRaram;
    }
}
