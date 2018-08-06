package com.hyjf.am.trade.service.front.trade.impl;

import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountRechargeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.trade.RechargeService;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
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


	@Override
    public int selectByOrdId(String ordId){
		int ret = 0;
		AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
		accountRechargeExample.createCriteria().andNidEqualTo(ordId == null ? "" : ordId);
		List<AccountRecharge> listAccountRecharge = this.accountRechargeMapper.selectByExample(accountRechargeExample);
		if (listAccountRecharge != null && listAccountRecharge.size() > 0) {
			return ret;
		}
		return -1;
	}

	@Override
	public int insertSelective(BankRequest bankRequest){
		int nowTime = GetDate.getNowTime10();
		String cardNo = bankRequest.getCardNo();
		BigDecimal money = new BigDecimal(bankRequest.getTxAmount());
		AccountRecharge record = new AccountRecharge();
		record.setNid(bankRequest.getLogOrderId());
		record.setUserId(Integer.parseInt(bankRequest.getLogUserId()));
		record.setUsername(bankRequest.getLogUserName());
		record.setTxDate(Integer.parseInt(bankRequest.getTxDate()));
		record.setTxTime(Integer.parseInt(bankRequest.getTxTime()));
		record.setSeqNo(Integer.parseInt(bankRequest.getSeqNo()));
		record.setBankSeqNo(bankRequest.getTxDate() + bankRequest.getTxTime() + bankRequest.getSeqNo());
		// 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
		record.setStatus(RECHARGE_STATUS_WAIT);
		record.setAccountId(bankRequest.getAccountId());
		record.setMoney(money);
		record.setCardid(cardNo);
		record.setFee(BigDecimal.ZERO);
		// 实际到账余额
		record.setBalance(money);
		record.setPayment(bankRequest == null ? "" : bankRequest.getBank());
		record.setGateType("QP");
		// 类型.1网上充值.0线下充值
		record.setType(1);
		record.setRemark("快捷充值");
		record.setOperator(bankRequest.getLogUserId());
		record.setAddIp(bankRequest.getUserIP());
		// 0pc
		record.setClient(bankRequest.getLogClient());
		// 资金托管平台 0:汇付,1:江西银行
		record.setIsBank(1);
		// 插入用户充值记录表
		return this.accountRechargeMapper.insertSelective(record);
	}

	/**
	 * 查询充值记录
	 * @param example
	 * @return
	 */
	@Override
    public AccountRecharge selectByExample(AccountRechargeExample example) {
		List<AccountRecharge> accountRecharges = accountRechargeMapper.selectByExample(example);
		if (accountRecharges != null && accountRecharges.size() == 1) {
			AccountRecharge accountRecharge = accountRecharges.get(0);
			return accountRecharge;
		}
		return null;
	}

	@Override
    public int updateByExampleSelective(AccountRecharge accountRecharge, AccountRechargeExample accountRechargeExample){
		int count = accountRechargeMapper.updateByExampleSelective(accountRecharge, accountRechargeExample);
		return count;
	}

	@Override
    public int updateBankRechargeSuccess(Account newAccount){
		int isAccountUpdateFlag = adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount);
		return isAccountUpdateFlag;
	}

	@Override
    public int insertSelective(AccountList accountList){
		int isAccountListUpdateFlag = accountListMapper.insertSelective(accountList);
		return isAccountListUpdateFlag;
	}

	@Override
    public void updateByPrimaryKeySelective(AccountRecharge accountRecharge){
		this.accountRechargeMapper.updateByPrimaryKeySelective(accountRecharge);
	}

	@Override
	public boolean updateBanks(AccountRechargeVO accountRechargeVO, String ip){
		{
			String orderId = accountRechargeVO.getLogOrderId();
			Integer userId = Integer.parseInt(accountRechargeVO.getLogUserId());
			BigDecimal txAmount = new BigDecimal(accountRechargeVO.getTxAmount());
			String accountId = accountRechargeVO.getAccountId();
			int nowTime = GetDate.getNowTime10();
			AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
			accountRechargeExample.createCriteria().andNidEqualTo(orderId).andStatusEqualTo(accountRechargeVO.getStatus());
			AccountRecharge accountRecharge = new AccountRecharge();
			BeanUtils.copyProperties(accountRechargeVO,accountRecharge);
			int isAccountRechargeFlag = accountRechargeMapper.updateByExampleSelective(accountRecharge, accountRechargeExample);
			Account newAccount = new Account();
			// 更新账户信息
			newAccount.setUserId(userId);// 用户Id
			newAccount.setBankTotal(txAmount); // 累加到账户总资产
			newAccount.setBankBalance(txAmount); // 累加可用余额
			newAccount.setBankBalanceCash(txAmount);// 银行账户可用户
			int isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount);
			AccountExample example = new AccountExample();
			AccountExample.Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(userId);
			List<Account> listAccount = accountMapper.selectByExample(example);
			Account account = new Account();
			if (listAccount != null && listAccount.size() > 0) {
				 account = listAccount.get(0);
			}
			AccountList accountList = new AccountList();
			accountList.setNid(orderId);
			accountList.setUserId(userId);
			accountList.setAmount(txAmount);
			accountList.setTxDate(accountRechargeVO.getTxDate());
			accountList.setTxTime(accountRechargeVO.getTxTime());
			accountList.setSeqNo(StringUtil.valueOf(accountRechargeVO.getSeqNo()));
			accountList.setBankSeqNo(StringUtil.valueOf(accountRechargeVO.getTxDate() + accountRechargeVO.getTxTime() + accountRechargeVO.getSeqNo()));
			accountList.setType(1);
			accountList.setTrade("recharge");
			accountList.setTradeCode("balance");
			accountList.setAccountId(accountId);
			accountList.setBankTotal(account.getBankTotal());
			accountList.setBankBalance(account.getBankBalance());
			accountList.setBankFrost(account.getBankFrost());
			accountList.setBankWaitCapital(account.getBankWaitCapital());
			accountList.setBankWaitInterest(account.getBankWaitInterest());
			accountList.setBankAwaitCapital(account.getBankAwaitCapital());
			accountList.setBankAwaitInterest(account.getBankAwaitInterest());
			accountList.setBankAwait(account.getBankAwait());// 银行待收总额
			accountList.setBankInterestSum(account.getBankInterestSum());
			accountList.setBankInvestSum(account.getBankInvestSum());
			accountList.setBankWaitRepay(account.getBankWaitRepay());
			accountList.setPlanBalance(account.getPlanBalance());
			accountList.setPlanFrost(account.getPlanFrost());
			accountList.setTotal(account.getTotal());
			accountList.setBalance(account.getBalance());
			accountList.setFrost(account.getFrost());
			accountList.setAwait(account.getAwait());
			accountList.setRepay(account.getRepay());
			accountList.setRemark("快捷充值");
			accountList.setCreateTime(new Date());
			accountList.setOperator(userId + "");
			accountList.setIp(ip);
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
