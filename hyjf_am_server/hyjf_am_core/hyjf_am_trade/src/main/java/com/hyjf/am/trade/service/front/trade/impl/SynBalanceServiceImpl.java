package com.hyjf.am.trade.service.front.trade.impl;

import com.hyjf.am.resquest.trade.SynBalanceBeanRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountRechargeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.trade.SynBalanceService;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.SynBalanceVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author pangchengchao
 * @version SynBalanceServiceImpl, v0.1 2018/6/20 9:58
 */
@Service
public class SynBalanceServiceImpl implements SynBalanceService {

    private static final Logger logger = LoggerFactory.getLogger(SynBalanceServiceImpl.class);
    // 充值状态:成功
    private static final int RECHARGE_STATUS_SUCCESS = 2;
    @Autowired
    private AccountListMapper accountListMapper;

    @Autowired
    private AccountRechargeMapper accountRechargeMapper;
    @Autowired
    private AdminAccountCustomizeMapper adminAccountCustomizeMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public boolean insertAccountDetails(SynBalanceBeanRequest synBalanceBeanRequest) {

        AccountVO account=synBalanceBeanRequest.getAccountUser();
        SynBalanceVO synBalanceBean=synBalanceBeanRequest.getSynBalanceBean();
        String username=synBalanceBeanRequest.getUsername();
        String ip=synBalanceBeanRequest.getIpAddr();
        // 添加重复校验
        try {
            // 校验交易明细是否已经插入当前笔充值
            AccountListExample accountListExample = new AccountListExample();
            accountListExample.createCriteria().andTxDateEqualTo(Integer.parseInt(synBalanceBean.getInpDate())).andTxTimeEqualTo(Integer.parseInt(synBalanceBean.getInpTime()))
                    .andSeqNoEqualTo(synBalanceBean.getTraceNo() + "").andTypeEqualTo(CustomConstants.TYPE_IN)
                    .andBankSeqNoEqualTo(synBalanceBean.getInpDate() + synBalanceBean.getInpTime() + synBalanceBean.getTraceNo());
            List<AccountList> accountLists = accountListMapper.selectByExample(accountListExample);
            if (accountLists != null && accountLists.size() != 0) {
                return false;
            }
            // 校验充值信息是否已经插入当前笔充值
            AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
            accountRechargeExample.createCriteria().andTxDateEqualTo(Integer.parseInt(synBalanceBean.getInpDate())).andTxTimeEqualTo(Integer.parseInt(synBalanceBean.getInpTime()))
                    .andSeqNoEqualTo(synBalanceBean.getTraceNo()).andBankSeqNoEqualTo(synBalanceBean.getInpDate() + synBalanceBean.getInpTime() + synBalanceBean.getTraceNo());
            List<AccountRecharge> accountRecharges = accountRechargeMapper.selectByExample(accountRechargeExample);
            if (accountRecharges != null && accountRecharges.size() != 0) {
                return false;
            }
            Account updateAccount = new Account();
            updateAccount.setUserId(account.getUserId());
            updateAccount.setBankTotal(synBalanceBean.getTxAmount()); // 累加到账户总资产
            updateAccount.setBankBalance(synBalanceBean.getTxAmount());// 累加可用余额(江西账户)

            // 更新账户信息
            boolean isAccountUpdateFlag = adminAccountCustomizeMapper.updateOfSynBalance(updateAccount) > 0 ? true : false;
            if(!isAccountUpdateFlag){
            	throw new RuntimeException("同步线下充值,更新用户账户信息失败~~~~,用户ID:"+account.getUserId());
            }
            // 重新获取用户账户信息
            Account accountNew = this.getAccount(account.getUserId());
            // 生成交易明细
            AccountList accountList = new AccountList();
            accountList.setNid(GetOrderIdUtils.getOrderId2(accountNew.getUserId()));
            accountList.setUserId(accountNew.getUserId());
            accountList.setAmount(synBalanceBean.getTxAmount());
            accountList.setType(CustomConstants.TYPE_IN);// 收入
            accountList.setTrade("recharge_offline");
            accountList.setTradeCode("balance");
            accountList.setAccountId(synBalanceBean.getAccountId());
            accountList.setBankTotal(accountNew.getBankTotal()); // 银行总资产
            accountList.setBankBalance(accountNew.getBankBalance()); // 银行可用余额
            accountList.setBankFrost(accountNew.getBankFrost());// 银行冻结金额
            accountList.setBankWaitCapital(accountNew.getBankWaitCapital());// 银行待还本金
            accountList.setBankWaitInterest(accountNew.getBankWaitInterest());// 银行待还利息
            accountList.setBankAwaitCapital(accountNew.getBankAwaitCapital());// 银行待收本金
            accountList.setBankAwaitInterest(accountNew.getBankAwaitInterest());// 银行待收利息
            accountList.setBankAwait(accountNew.getBankAwait());// 银行待收总额
            accountList.setBankInterestSum(accountNew.getBankInterestSum()); // 银行累计收益
            accountList.setBankInvestSum(accountNew.getBankInvestSum());// 银行累计出借
            accountList.setBankWaitRepay(accountNew.getBankWaitRepay());// 银行待还金额
            accountList.setPlanBalance(accountNew.getPlanBalance());//汇计划账户可用余额
            accountList.setPlanFrost(accountNew.getPlanFrost());
            accountList.setTotal(accountNew.getTotal());
            accountList.setBalance(accountNew.getBalance());
            accountList.setFrost(accountNew.getFrost());
            accountList.setAwait(accountNew.getAwait());
            accountList.setRepay(accountNew.getRepay());
            accountList.setRemark("线下充值");
            accountList.setCreateTime(GetDate.getDate());
            accountList.setOperator(accountNew.getUserId() + "");
            accountList.setIp(ip);
            accountList.setWeb(0);
            accountList.setTxDate(Integer.parseInt(synBalanceBean.getInpDate()));
            accountList.setTxTime(Integer.parseInt(synBalanceBean.getInpTime()));
            accountList.setSeqNo(synBalanceBean.getTraceNo() + "");
            accountList.setBankSeqNo(synBalanceBean.getInpDate() + synBalanceBean.getInpTime() + synBalanceBean.getTraceNo());
            accountList.setIsBank(1);//资金托管平台 0:汇付,1:江西银行
            accountList.setTradeStatus(1);
            this.accountListMapper.insertSelective(accountList);// 插入交易明细

            // 插入充值信息
            BankCardVO bankCard =synBalanceBeanRequest.getBankCardVO();
            Integer nowTime = GetDate.getNowTime10();
            AccountRecharge record = new AccountRecharge();
            record.setNid(GetOrderIdUtils.getOrderId2(account.getUserId())); // 订单号
            record.setAccountId(synBalanceBean.getAccountId());
            record.setUserId(account.getUserId()); // 用户ID
            record.setUsername(username);// 用户 名
            record.setStatus(RECHARGE_STATUS_SUCCESS); // 状态 0：失败；1：成功 2:充值中
            record.setMoney(synBalanceBean.getTxAmount()); // 金额
            record.setCardid(bankCard.getCardNo());// 银行卡号
            record.setFee(BigDecimal.ZERO); // 费用
            record.setBalance(synBalanceBean.getTxAmount()); // 实际到账余额
            record.setPayment(""); // 所属银行
            record.setGateType("OFFLINE"); // 网关类型：QP快捷充值 B2C个人网银充值 B2B企业网银充值
            record.setType(0); // 类型.1网上充值.0线下充值
            record.setRemark("线下充值");// 备注
            record.setOperator(account.getUserId() + "");
            record.setAddIp(ip);
            record.setClient(0); // 0pc 1WX 2AND 3IOS 4other
            record.setIsBank(1);// 资金托管平台 0:汇付,1:江西银行
            record.setTxDate(Integer.parseInt(synBalanceBean.getInpDate()));
            record.setTxTime(Integer.parseInt(synBalanceBean.getInpTime()));
            record.setSeqNo(synBalanceBean.getTraceNo());
            record.setBankSeqNo(synBalanceBean.getInpDate() + synBalanceBean.getInpTime() + synBalanceBean.getTraceNo());
            // 插入用户充值记录表
            this.accountRechargeMapper.insertSelective(record);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("同步线下充值,更新用户账户信息失败~~~~,用户ID:"+account.getUserId());
        }
        return true;
    }

    public Account getAccount(Integer userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Account> listAccount = this.accountMapper.selectByExample(example);
        if (listAccount != null && listAccount.size() > 0) {
            return listAccount.get(0);
        }
        return null;
    }
}
