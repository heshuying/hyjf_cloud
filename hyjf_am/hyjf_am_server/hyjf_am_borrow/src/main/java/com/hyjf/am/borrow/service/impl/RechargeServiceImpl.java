package com.hyjf.am.borrow.service.impl;

import com.hyjf.am.borrow.dao.mapper.auto.*;
import com.hyjf.am.borrow.dao.model.auto.*;
import com.hyjf.am.borrow.service.RechargeService;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

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
	protected BankCardMapper bankCardMapper;

	@Autowired
	protected BanksConfigMapper banksConfigMapper;

	@Autowired
	protected CorpOpenAccountRecordMapper corpOpenAccountRecordMapper;

	@Autowired
	protected AccountRechargeMapper accountRechargeMapper;

	@Autowired
	protected BankReturnCodeConfigMapper bankReturnCodeConfigMapper;

	@Autowired
	protected AccountMapper accountMapper;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private TransactionDefinition transactionDefinition;

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

	/**
	 * 根据用户Id检索用户银行卡信息
	 * 
	 * @param userId
	 * @return
	 */
	public BankCard selectBankCardByUserId(Integer userId) {
		BankCardExample example = new BankCardExample();
		BankCardExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(userId);// 用户Id
		cra.andStatusEqualTo(1);// 银行卡是否有效 0无效 1有效
		List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
		if (bankCardList != null && bankCardList.size() > 0) {
			return bankCardList.get(0);
		}
		return null;
	}

	/**
	 * 获取银行卡配置信息
	 */
	public BanksConfig getBanksConfigByBankId(Integer bankId) {
		if(bankId == null){
			return null;
		}
		BanksConfigExample example = new BanksConfigExample();
		example.createCriteria().andIdEqualTo(bankId).andDelFlgEqualTo(0);
		List<BanksConfig> banksConfigList = banksConfigMapper.selectByExample(example);
		if(banksConfigList != null && !banksConfigList.isEmpty()){
			return banksConfigList.get(0);
		}
		return null;
	}

	/**
	 * 根据用户ID查询企业用户信息
	 *
	 * @param userId
	 * @return
	 */
	public CorpOpenAccountRecord getCorpOpenAccountRecord(Integer userId) {
		CorpOpenAccountRecordExample example = new CorpOpenAccountRecordExample();
		CorpOpenAccountRecordExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(userId);
		cra.andIsBankEqualTo(1);// 江西银行
		List<CorpOpenAccountRecord> list = this.corpOpenAccountRecordMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int insertRechargeInfo(BankCallBean bean) {
		int ret = 0;
		String ordId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId(); // 订单号
		AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
		accountRechargeExample.createCriteria().andNidEqualTo(ordId == null ? "" : ordId);
		List<AccountRecharge> listAccountRecharge = this.accountRechargeMapper.selectByExample(accountRechargeExample);
		if (listAccountRecharge != null && listAccountRecharge.size() > 0) {
			return ret;
		}
		int nowTime = GetDate.getNowTime10(); // 当前时间
		// 银行卡号
		String cardNo = bean.getCardNo();
		// 根据银行卡号检索银行卡信息
		BankCard bankCard = this.getBankCardByCardNo(Integer.parseInt(bean.getLogUserId()), cardNo);
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
	 * 根据银行卡号,用户Id检索用户银行卡信息
	 *
	 * @param userId
	 * @param cardNo
	 * @return
	 */
	public BankCard getBankCardByCardNo(Integer userId, String cardNo) {
		BankCardExample example = new BankCardExample();
		BankCardExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(userId);// 用户Id
		cra.andCardNoEqualTo(cardNo);// 银行卡号
		cra.andStatusEqualTo(1);// 银行卡是否有效 0无效 1有效
		List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
		if (bankCardList != null && bankCardList.size() > 0) {
			return bankCardList.get(0);
		}
		return null;
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

	public int updateByExampleSelective(AccountRecharge accountRecharge,AccountRechargeExample accountRechargeExample){
		int count = accountRechargeMapper.updateByExampleSelective(accountRecharge, accountRechargeExample);
		return count;
	}

	public int updateBankRechargeSuccess(Account newAccount){
		int isAccountUpdateFlag = adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount);
		return isAccountUpdateFlag;
	}

	public int insertSelective(AccountList accountList){
		int isAccountListUpdateFlag = accountListMapper.insertSelective(accountList);
		return isAccountListUpdateFlag;
	}

	public void updateByPrimaryKeySelective(AccountRecharge accountRecharge){
		this.accountRechargeMapper.updateByPrimaryKeySelective(accountRecharge);
	}


	public BankReturnCodeConfig selectByExample(BankReturnCodeConfigExample example){
		List<BankReturnCodeConfig> retCodes = this.bankReturnCodeConfigMapper.selectByExample(example);
		if (retCodes != null && retCodes.size() == 1) {
			return retCodes.get(0);
		}
		return null;
	}

}
