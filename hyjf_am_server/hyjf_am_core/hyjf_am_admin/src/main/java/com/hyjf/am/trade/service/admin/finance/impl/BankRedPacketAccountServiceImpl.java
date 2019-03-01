/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.resquest.admin.BankRedPacketAccountListRequest;
import com.hyjf.am.trade.dao.mapper.customize.BankMerchantAccountListCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccountListExample;
import com.hyjf.am.trade.dao.model.customize.BankMerchantAccountListCustomize;
import com.hyjf.am.trade.service.admin.finance.BankRedPacketAccountService;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingqing
 * @version BankRedPacketAccountServiceImpl, v0.1 2018/7/10 9:57
 */
@Service
public class BankRedPacketAccountServiceImpl implements BankRedPacketAccountService {

    @Autowired
    BankMerchantAccountListCustomizeMapper bankMerchantAccountListCustomizeMapper;

    /**
     * 获取商户子账户列表
     * @param form
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<BankMerchantAccountListCustomize> selectRecordList(BankRedPacketAccountListRequest form, int offset, int limit) {
        return bankMerchantAccountListCustomizeMapper.selectRecordList(form);
    }

    /**
     * 查询商户子账户表相应的数据总数
     * @param form
     * @return
     */
    @Override
    public int queryRecordTotal(BankRedPacketAccountListRequest form) {
        return bankMerchantAccountListCustomizeMapper.countByExample(form);
    }

    private BankMerchantAccountListExample createBankMerchantAccountListCustomize(BankRedPacketAccountListRequest form) {
        BankMerchantAccountListExample example = new BankMerchantAccountListExample();
        BankMerchantAccountListExample.Criteria criteria = example.createCriteria();
        if(null!=form.getTypeList()){
            criteria.andTypeIn(form.getTypeList());
        }
        if(null != form.getTransTypeList()){
            criteria.andTransTypeIn(form.getTransTypeList());
        }
        if(null != form.getStatusList()){
            criteria.andStatusIn(form.getStatusList());
        }
        if(null != form.getBankAccountCode()){
            criteria.andBankAccountCodeEqualTo(form.getBankAccountCode());
        }
        if(null != form.getSeqNo()){
            criteria.andSeqNoEqualTo(form.getSeqNo());
        }
        if(null != form.getOrderId()){
            criteria.andOrderIdEqualTo(form.getOrderId());
        }
        if(null != form.getAccountId()){
            criteria.andAccountIdEqualTo(form.getAccountId());
        }
        if(null != form.getType()){
            criteria.andTypeEqualTo(form.getType());
        }
        if(null != form.getStatus()){
            criteria.andStatusEqualTo(form.getStatus());
        }
        if(null != form.getTransType()){
            criteria.andTransTypeEqualTo(form.getTransType());
        }
        if(null != form.getTimeStartSrch()){
            criteria.andCreateTimeGreaterThan(GetDate.getDayStartOfSomeDay(form.getTimeStartSrch()));
        }
        if(null != form.getTimeEndSrch()){
            criteria.andCreateTimeLessThan(GetDate.getDayEndOfSomeDay(form.getTimeEndSrch()));
        }
        return example;
    }
}
