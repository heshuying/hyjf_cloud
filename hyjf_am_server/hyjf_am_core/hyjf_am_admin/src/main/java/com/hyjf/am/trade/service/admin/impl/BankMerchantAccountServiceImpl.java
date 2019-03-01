/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.admin.config.SystemConfig;
import com.hyjf.am.resquest.admin.BankMerchantAccountListRequest;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccount;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccountExample;
import com.hyjf.am.trade.service.admin.BankMerchantAccountService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangqingqing
 * @version BankMerchantAccountServiceImpl, v0.1 2018/7/9 16:19
 */
@Service
public class BankMerchantAccountServiceImpl extends BaseServiceImpl implements BankMerchantAccountService {

    @Autowired
    SystemConfig systemConfig;
    /**
     * 查询商户子账户表相应的数据总数
     * @param form
     * @return
     */
    @Override
    public int queryRecordTotal(BankMerchantAccountListRequest form) {
        BankMerchantAccountExample example = new BankMerchantAccountExample();
        return bankMerchantAccountMapper.countByExample(example);
    }

    /**
     * 获取商户子账户列表
     *
     * @return
     */
    @Override
    public List<BankMerchantAccount> selectRecordList(BankMerchantAccountListRequest form, int limitStart, int limitEnd) {
        BankMerchantAccountExample example = new BankMerchantAccountExample();
        example.setOrderByClause("create_time DESC");
        return bankMerchantAccountMapper.selectByExample(example);
    }

    /**
     * 更新商户子账户金额
     * @param recordList
     * @param userId
     * @return
     */
    @Override
    public List<BankMerchantAccount> updateBankMerchantAccount(List<BankMerchantAccount> recordList,Integer userId) {
        // 返回数组
        List<BankMerchantAccount> accountList = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            BankMerchantAccount record = recordList.get(i);
            // 调用存管接口
            BankCallBean bean = new BankCallBean();
            // 银行存管
            bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);
            // 存管平台分配的账号19位
            bean.setAccountId(record.getAccountCode());
            // 订单号
            bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
            bean.setLogUserId(String.valueOf(userId));
            bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
            bean.setInstCode(systemConfig.getBankInstcode());// 机构代码
            bean.setBankCode(systemConfig.getBankBankcode());// 银行代码
            bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
            bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
            bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
            bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道000001手机APP
            bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
            bean.setLogUserId(String.valueOf(userId));
            bean.setLogClient(0);// 平台
            // 平台
            bean.setLogClient(0);
            try {
                BankCallBean retBean = BankCallUtils.callApiBg(bean);
                if (retBean == null) {
                    return recordList;
                }
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
                    // 账面余额 账面余额-可用余额=冻结金额
                    BigDecimal currBal = new BigDecimal(retBean.getCurrBal());
                    // 可用余额
                    BigDecimal availBal = new BigDecimal(retBean.getAvailBal());
                    // 处理成功返回，更新数据
                    record.setAccountBalance(currBal);
                    record.setAvailableBalance(availBal);
                    record.setFrost(currBal.subtract(availBal));
                    record.setUpdateTime(new Date());
                    this.bankMerchantAccountMapper.updateByPrimaryKeySelective(record);
                    accountList.add(record);
                }else{
                    accountList.add(record);
                }
            } catch (Exception e) {
                return recordList;
            }
        }
        return accountList;
    }

}
