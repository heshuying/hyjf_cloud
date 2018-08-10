/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.resquest.admin.BankRedPacketAccountListRequest;
import com.hyjf.am.trade.dao.mapper.customize.BankMerchantAccountListCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccountListExample;
import com.hyjf.am.trade.dao.model.customize.BankMerchantAccountListCustomize;
import com.hyjf.am.trade.service.admin.finance.BankRedPacketAccountService;
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
        BankMerchantAccountListExample example=createBankMerchantAccountListCustomize(form);
        example.setLimitStart(offset);
        example.setLimitEnd(limit);
        example.setOrderByClause("create_time DESC");
        return bankMerchantAccountListCustomizeMapper.selectByExample(example);
    }

    /**
     * 查询商户子账户表相应的数据总数
     * @param form
     * @return
     */
    @Override
    public int queryRecordTotal(BankRedPacketAccountListRequest form) {
        BankMerchantAccountListExample example=createBankMerchantAccountListCustomize(form);
        return bankMerchantAccountListCustomizeMapper.countByExample(example);
    }

    private BankMerchantAccountListExample createBankMerchantAccountListCustomize(BankRedPacketAccountListRequest form) {
        BankMerchantAccountListExample example = new BankMerchantAccountListExample();
        BankMerchantAccountListExample.Criteria criteria = example.createCriteria();
        criteria.andTypeIn(form.getTypeList());
        criteria.andTransTypeIn(form.getTransTypeList());
        criteria.andStatusIn(form.getStatusList());
        criteria.andBankAccountCodeEqualTo(form.getBankAccountCode());
        criteria.andSeqNoEqualTo(form.getSeqNo());
        criteria.andOrderIdEqualTo(form.getOrderId());
        criteria.andAccountIdEqualTo(form.getAccountId());
        criteria.andTypeEqualTo(form.getType());
        criteria.andStatusEqualTo(form.getStatus());
        criteria.andTransTypeEqualTo(form.getTransType());
        criteria.andCreateTimeGreaterThan(form.getTimeStartSrch());
        criteria.andCreateTimeLessThan(form.getTimeEndSrch());
        return example;
    }
}
