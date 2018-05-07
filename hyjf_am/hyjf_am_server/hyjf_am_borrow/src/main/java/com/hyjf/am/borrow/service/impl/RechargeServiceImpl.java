package com.hyjf.am.borrow.service.impl;

import com.hyjf.am.borrow.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.borrow.dao.mapper.auto.AccountMapper;
import com.hyjf.am.borrow.dao.mapper.auto.AccountRechargeMapper;
import com.hyjf.am.borrow.dao.mapper.auto.AdminAccountCustomizeMapper;
import com.hyjf.am.borrow.dao.model.auto.*;
import com.hyjf.am.borrow.service.RechargeService;
import com.hyjf.am.vo.borrow.AccountListVO;
import com.hyjf.am.vo.borrow.AccountRechargeVO;
import com.hyjf.am.vo.borrow.AccountVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户充值Service实现类
 * 
 * @author
 *
 */
@Service
public class RechargeServiceImpl implements RechargeService {






	@Autowired
	protected AccountRechargeMapper accountRechargeMapper;

	@Autowired
	protected AccountMapper accountMapper;
	@Autowired
	protected  AccountListMapper accountListMapper;



	// 充值状态:充值中
	private static final int RECHARGE_STATUS_WAIT = 1;
	// 充值状态:失败
	private static final int RECHARGE_STATUS_FAIL = 3;
	// 充值状态:成功
	private static final int RECHARGE_STATUS_SUCCESS = 2;

	@Autowired
	private AdminAccountCustomizeMapper adminAccountCustomizeMapper;


	public int selectByOrdId(String ordId){
		int ret = 0;
		//String ordId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId(); // 订单号
		AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
		accountRechargeExample.createCriteria().andNidEqualTo(ordId == null ? "" : ordId);
		List<AccountRecharge> listAccountRecharge = this.accountRechargeMapper.selectByExample(accountRechargeExample);
		if (listAccountRecharge != null && listAccountRecharge.size() > 0) {
			return ret;
		}
		return -1;
	}

	@Override
	public int insertSelective(BankCallBean bean, BankCard bankCard){
		int nowTime = GetDate.getNowTime10(); // 当前时间
		// 银行卡号
		String cardNo = bean.getCardNo();
		BigDecimal money = new BigDecimal(bean.getTxAmount()); // 充值金额
		AccountRecharge record = new AccountRecharge();
		record.setNid(bean.getLogOrderId()); // 订单号
		record.setUserId(Integer.parseInt(bean.getLogUserId())); // 用户ID
		record.setUsername(bean.getLogUserName());// 用户 名
		record.setTxDate(Integer.parseInt(bean.getTxDate()));// 交易日期
		record.setTxTime(Integer.parseInt(bean.getTxTime()));// 交易时间
		record.setSeqNo(Integer.parseInt(bean.getSeqNo())); // 交易流水号
		record.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo()); // 交易日期+交易时间+交易流水号
		record.setStatus(RECHARGE_STATUS_WAIT); // 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
		record.setAccountId(bean.getAccountId());// 电子账号
		record.setMoney(money); // 金额
		record.setCardid(cardNo);// 银行卡号
		record.setFeeFrom(null);// 手续费扣除方式
		record.setFee(BigDecimal.ZERO); // 费用
		record.setDianfuFee(BigDecimal.ZERO);// 垫付费用
		record.setBalance(money); // 实际到账余额
		record.setPayment(bankCard == null ? "" : bankCard.getBank()); // 所属银行
		record.setGateType("QP"); // 网关类型：QP快捷支付
		record.setType(1); // 类型.1网上充值.0线下充值
		record.setRemark("快捷充值");// 备注
		record.setCreateTime(nowTime);
		record.setOperator(bean.getLogUserId());
		record.setAddtime(String.valueOf(nowTime));
		record.setAddip(bean.getUserIP());
		record.setClient(bean.getLogClient()); // 0pc
		record.setIsBank(1);// 资金托管平台 0:汇付,1:江西银行
		// 插入用户充值记录表
		return this.accountRechargeMapper.insertSelective(record);
	}

	/**
	 * 查询充值记录
	 * @param example
	 * @return
	 */
	public AccountRecharge selectByExample(AccountRechargeExample example) {
		List<AccountRecharge> accountRecharges = accountRechargeMapper.selectByExample(example);
		if (accountRecharges != null && accountRecharges.size() == 1) {
			AccountRecharge accountRecharge = accountRecharges.get(0);
			return accountRecharge;
		}
		return null;
	}

	public int updateByExampleSelective(AccountRechargeVO accountRecharge,AccountRechargeExample accountRechargeExample){
		int count = accountRechargeMapper.updateByExampleSelective(accountRecharge, accountRechargeExample);
		return count;
	}

	public int updateBankRechargeSuccess(AccountVO newAccount){
		int isAccountUpdateFlag = adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount);
		return isAccountUpdateFlag;
	}

	public int insertSelective(AccountListVO accountList){
		int isAccountListUpdateFlag = accountListMapper.insertSelective(accountList);
		return isAccountListUpdateFlag;
	}

	public void updateByPrimaryKeySelective(AccountRechargeVO accountRecharge){
		this.accountRechargeMapper.updateByPrimaryKeySelective(accountRecharge);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateBanks(AccountRechargeVO accountRecharge, BankCallBean bean, String ip){
		{
			// 充值订单号
			String orderId = bean.getLogOrderId();
			// 用户Id
			Integer userId = Integer.parseInt(bean.getLogUserId());
			// 交易金额
			BigDecimal txAmount = bean.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT);
			// 电子账户
			String accountId = bean.getAccountId();
			// 当前时间
			int nowTime = GetDate.getNowTime10();
			AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
			accountRechargeExample.createCriteria().andNidEqualTo(orderId).andStatusEqualTo(accountRecharge.getStatus());
			boolean isAccountRechargeFlag = accountRechargeMapper.updateByExampleSelective(accountRecharge, accountRechargeExample) > 0 ? true : false;
			AccountVO newAccount = new AccountVO();
			// 更新账户信息
			newAccount.setUserId(userId);// 用户Id
			newAccount.setBankTotal(txAmount); // 累加到账户总资产
			newAccount.setBankBalance(txAmount); // 累加可用余额
			newAccount.setBankBalanceCash(txAmount);// 银行账户可用户
			boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount) > 0 ? true : false;
			AccountExample example = new AccountExample();
			AccountExample.Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(userId);
			// 重新获取用户账户信息
			List<Account> listAccount = accountMapper.selectByExample(example);
			Account account = new Account();
			if (listAccount != null && listAccount.size() > 0) {
				 account = listAccount.get(0);
			}
			// 生成交易明细
			AccountListVO accountList = new AccountListVO();
			accountList.setNid(orderId);
			accountList.setUserId(userId);
			accountList.setAmount(txAmount);
			accountList.setTxDate(Integer.parseInt(bean.getTxDate()));// 交易日期
			accountList.setTxTime(Integer.parseInt(bean.getTxTime()));// 交易时间
			accountList.setSeqNo(bean.getSeqNo());// 交易流水号
			accountList.setBankSeqNo((bean.getTxDate() + bean.getTxTime() + bean.getSeqNo()));
			accountList.setType(1);
			accountList.setTrade("recharge");
			accountList.setTradeCode("balance");
			accountList.setAccountId(accountId);
			accountList.setBankTotal(account.getBankTotal()); // 银行总资产
			accountList.setBankBalance(account.getBankBalance()); // 银行可用余额
			accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
			accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
			accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
			accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
			accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
			accountList.setBankAwait(account.getBankAwait());// 银行待收总额
			accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
			accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计投资
			accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
			accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
			accountList.setPlanFrost(account.getPlanFrost());
			accountList.setTotal(account.getTotal());
			accountList.setBalance(account.getBalance());
			accountList.setFrost(account.getFrost());
			accountList.setAwait(account.getAwait());
			accountList.setRepay(account.getRepay());
			accountList.setRemark("快捷充值");
			accountList.setCreateTime(nowTime);
			accountList.setBaseUpdate(nowTime);
			accountList.setOperator(userId + "");
			accountList.setIp(ip);
			accountList.setIsUpdate(0);
			accountList.setBaseUpdate(0);
			accountList.setInterest(null);
			accountList.setWeb(0);
			accountList.setIsBank(1);// 是否是银行的交易记录 0:否 ,1:是
			accountList.setCheckStatus(0);// 对账状态0：未对账 1：已对账
			accountList.setTradeStatus(1);// 成功状态
			// 插入交易明细
			boolean isAccountListUpdateFlag = this. accountListMapper.insertSelective(accountList) > 0 ? true : false;
			return isAccountListUpdateFlag;
		}
	}

}
