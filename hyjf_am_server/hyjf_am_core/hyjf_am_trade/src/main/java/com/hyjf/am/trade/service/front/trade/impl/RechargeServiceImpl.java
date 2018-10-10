package com.hyjf.am.trade.service.front.trade.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.HandleAccountRechargeRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountListMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountRechargeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.AppMessageProducer;
import com.hyjf.am.trade.mq.producer.SmsProducer;
import com.hyjf.am.trade.service.front.trade.RechargeService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringUtil;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.util.*;

/**
 * 用户充值Service实现类
 * 
 * @author
 *
 */
@Service
public class RechargeServiceImpl extends BaseServiceImpl implements RechargeService {

	@Autowired
	protected AccountRechargeMapper accountRechargeMapper;

	@Autowired
	protected AccountMapper accountMapper;
	@Autowired
	protected  AccountListMapper accountListMapper;
	@Autowired
	private SmsProducer smsProducer;

	@Autowired
	private AppMessageProducer appMessageProducer;

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
		if(bankRequest != null){
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
			record.setPayment(null == bankRequest.getBank() ? "" : bankRequest.getBank());
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
		}else{
			return 0;
		}

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
			AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
			accountRechargeExample.createCriteria().andNidEqualTo(orderId);
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

	/**
	 * 插入充值记录
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public int insertAccountRecharge(AccountRechargeVO accountRechargeVO) {
		AccountRecharge accountRecharge = CommonUtils.convertBean(accountRechargeVO,AccountRecharge.class);
		return accountRechargeMapper.insertSelective(accountRecharge);
	}

	/**
	 * 根据orderId查询充值记录
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public List<AccountRecharge> selectAccountRechargeByOrderId(String orderId) {
		AccountRechargeExample example = new AccountRechargeExample();
		AccountRechargeExample.Criteria criteria = example.createCriteria();
		criteria.andNidEqualTo(orderId == null ? "" : orderId);
		return accountRechargeMapper.selectByExample(example);
	}

	/**
	 * 更新充值的相关信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public String handleRechargeInfo(HandleAccountRechargeRequest request) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error","");
		jsonObject.put("errorDesc","");
		jsonObject.put("flag",false);

		BankCallBeanVO bean = request.getBankCallBeanVO();

		TransactionStatus txStatus = null;
		// 用户Id
		Integer userId = Integer.parseInt(bean.getLogUserId());
		// 充值订单号
		String orderId = bean.getLogOrderId();
		// 当前时间
		Date nowTime = GetDate.getNowTime();
		// 错误信息
		String errorMsg = request.getErrorMsg();
		// ip
		String ip = request.getIp();
		// 交易日期
		String txDate = bean.getTxDate();
		// 交易时间
		String txTime = bean.getTxTime();
		// 交易流水号
		String seqNo = bean.getSeqNo();
		// 电子账户
		String accountId = bean.getAccountId();
		// 充值成功
		if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
			// 查询用户账户,为了版本控制,必须把查询用户账户放在最前面
			Account account = this.getAccount(userId);
			// 查询充值记录
			AccountRechargeExample example = new AccountRechargeExample();
			example.createCriteria().andNidEqualTo(orderId);
			List<AccountRecharge> accountRecharges = accountRechargeMapper.selectByExample(example);// 查询充值记录
			AccountRecharge accountRecharge = accountRecharges.get(0);
			// 如果没有充值记录
			if (accountRecharge != null) {
				// add by cwyang 增加redis防重校验 2017-08-02
				boolean reslut = RedisUtils.tranactionSet("recharge_orderid" + orderId, 10);
				if (!reslut) {
					jsonObject.put("error","0");
					jsonObject.put("errorDesc","充值成功");
					return jsonObject.toJSONString();
				}
				// end
				// 交易金额
				BigDecimal txAmount = bean.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT);
				logger.info("银行返回参数===============txAmount:[{}]",txAmount);
				// 判断充值记录状态
				if (accountRecharge.getStatus() == RECHARGE_STATUS_SUCCESS) {
					// 如果已经是成功状态
					jsonObject.put("error","0");
					jsonObject.put("errorDesc","充值成功");
					return jsonObject.toJSONString();
				} else {
					// 如果不是成功状态
					try {
						// 开启事务
						txStatus = this.transactionManager.getTransaction(transactionDefinition);
						// 将数据封装更新至充值记录
						AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
						accountRechargeExample.createCriteria().andNidEqualTo(orderId).andStatusEqualTo(accountRecharge.getStatus());
						accountRecharge.setFee(BigDecimal.ZERO); // 费用
						// 商户垫付金额 不知道拆到哪里去了

						accountRecharge.setBalance(txAmount);// 实际到账余额
						accountRecharge.setUpdateTime(nowTime);// 更新时间
						accountRecharge.setStatus(RECHARGE_STATUS_SUCCESS);// 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
						accountRecharge.setAccountId(accountId);// 电子账户
						accountRecharge.setBankSeqNo(txDate + txTime + seqNo);// 交易流水号
						boolean isAccountRechargeFlag = this.accountRechargeMapper.updateByExampleSelective(accountRecharge, accountRechargeExample) > 0 ? true : false;
						if (!isAccountRechargeFlag) {
							throw new Exception("充值后,回调更新充值记录表失败!" + "充值订单号:" + orderId + ".用户ID:" + userId);
						}
						Account newAccount = new Account();
						// 更新账户信息
						newAccount.setUserId(userId);// 用户Id
						newAccount.setBankTotal(txAmount); // 累加到账户总资产
						newAccount.setBankBalance(txAmount); // 累加可用余额
						newAccount.setBankBalanceCash(txAmount);// 银行账户可用户
						boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount) > 0 ? true : false;
						if (!isAccountUpdateFlag) {
							throw new Exception("提现后,更新用户Account表失败!");
						}
						// 重新获取用户账户信息
						account = this.getAccount(userId);
						// 生成交易明细
						AccountList accountList = new AccountList();
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
						accountList.setOperator(String.valueOf(userId));
						accountList.setIp(ip);
						accountList.setWeb(0);
						accountList.setIsBank(1);// 是否是银行的交易记录 0:否 ,1:是
						accountList.setCheckStatus(0);// 对账状态0：未对账 1：已对账
						accountList.setTradeStatus(1);// 成功状态
						// 插入交易明细
						boolean isAccountListUpdateFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
						if (isAccountListUpdateFlag) {
							// 提交事务
							this.transactionManager.commit(txStatus);

							// updateBankMobile 除了异常不管
							// updateBankMobile(userId,accountRecharge.getCardid(),mobile);
							jsonObject.put("flag",true);
							jsonObject.put("cardId",accountRecharge.getCardid());
							jsonObject.put("error","0");
							jsonObject.put("errorDesc","充值成功");
							return jsonObject.toJSONString();
						} else {
							// 回滚事务
							transactionManager.rollback(txStatus);
							// 查询充值交易状态
							accountRecharges = accountRechargeMapper.selectByExample(example);// 查询充值记录
							accountRecharge = accountRecharges.get(0);
							if (RECHARGE_STATUS_SUCCESS == accountRecharge.getStatus()) {
								jsonObject.put("error","0");
								jsonObject.put("errorDesc","充值成功");
								return jsonObject.toJSONString();
							} else {
								// 账户数据过期，请查看交易明细 跳转中间页
								jsonObject.put("error","1");
								jsonObject.put("errorDesc","账户数据过期，请查看交易明细");
								return jsonObject.toJSONString();
							}
						}
					} catch (Exception e) {
						// 回滚事务
						transactionManager.rollback(txStatus);
						System.err.println(e);
					}
				}
			} else {
				logger.info("充值失败,未查询到相应的充值记录.用户ID:[" + userId + ",充值订单号:[" + orderId + "].");
				jsonObject.put("error","1");
				jsonObject.put("errorDesc","充值失败,未查询到相应的充值记录");
				return jsonObject.toJSONString();
			}
		} else {
			// 更新订单信息
			AccountRechargeExample example = new AccountRechargeExample();
			example.createCriteria().andNidEqualTo(orderId);
			List<AccountRecharge> accountRecharges = this.accountRechargeMapper.selectByExample(example);
			if (accountRecharges != null && accountRecharges.size() == 1) {
				AccountRecharge accountRecharge = accountRecharges.get(0);
				if (RECHARGE_STATUS_WAIT == accountRecharge.getStatus()) {
					// 更新处理状态
					accountRecharge.setStatus(RECHARGE_STATUS_FAIL);// 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
					accountRecharge.setUpdateTime(nowTime);
					accountRecharge.setMessage(errorMsg);
					accountRecharge.setAccountId(accountId);// 电子账户
					accountRecharge.setBankSeqNo(txDate + txTime + seqNo);// 交易流水号
					this.accountRechargeMapper.updateByPrimaryKeySelective(accountRecharge);
				}
			}
			jsonObject.put("error","1");
			jsonObject.put("errorDesc",errorMsg);
			return jsonObject.toJSONString();
		}
		return jsonObject.toJSONString();
	}

	/**
	 * 更新充值的相关信息(页面调用)
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public String handleRechargeOnlineInfo(HandleAccountRechargeRequest request) {
		JSONObject jsonObject = new JSONObject();
		BankCallBeanVO bean = request.getBankCallBeanVO();

		Map<String,String> params = request.getParams();

		TransactionStatus txStatus = null;
		// 用户Id
		Integer userId = Integer.parseInt(bean.getLogUserId());
		// 充值订单号
		String orderId = bean.getLogOrderId();
		// 当前时间
		Date nowTime = GetDate.getNowTime();
		// 错误信息
		String errorMsg = request.getErrorMsg();
		// ip
		String ip = params.get("ip");// ip
		String mobile = params.get("mobile");
		// del by liuyang 20180119 银行文件对账功能修改 start
//		// 交易日期
//		String txDate = bean.getTxDate();
//		// 交易时间
//		String txTime = bean.getTxTime();
//		// 交易流水号
//		String seqNo = bean.getSeqNo();
		// del by liuyang 20180119 银行文件对账功能修改 end
		// 电子账户
		String accountId = bean.getAccountId();
		// 充值成功
		if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
			// 查询用户账户,为了版本控制,必须把查询用户账户放在最前面
			AccountExample accountExample = new AccountExample();
			AccountExample.Criteria accountCriteria = accountExample.createCriteria();
			accountCriteria.andUserIdEqualTo(userId);
			Account account = this.accountMapper.selectByExample(accountExample).get(0);
			// 查询充值记录
			AccountRechargeExample example = new AccountRechargeExample();
			example.createCriteria().andNidEqualTo(orderId);
			List<AccountRecharge> accountRecharges = accountRechargeMapper.selectByExample(example);// 查询充值记录
			AccountRecharge accountRecharge = accountRecharges.get(0);
			// 如果没有充值记录
			if (accountRecharge != null) {
				// 交易金额
				BigDecimal txAmount = bean.getBigDecimal(BankCallConstant.PARAM_TXAMOUNT);
				// 判断充值记录状态
				if (accountRecharge.getStatus() == RECHARGE_STATUS_SUCCESS) {
					// 如果已经是成功状态
					jsonObject.put("error","0");
					jsonObject.put("errorDesc","充值成功");
					return jsonObject.toJSONString();
				} else {
					// 如果不是成功状态
					try {
						// 开启事务
						txStatus = this.transactionManager.getTransaction(transactionDefinition);
						// 将数据封装更新至充值记录
						AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
						accountRechargeExample.createCriteria().andNidEqualTo(orderId).andStatusEqualTo(accountRecharge.getStatus());
						accountRecharge.setFee(BigDecimal.ZERO); // 费用
						//accountRecharge.setDianfuFee(BigDecimal.ZERO);// 商户垫付金额
						accountRecharge.setBalance(txAmount);// 实际到账余额
						accountRecharge.setUpdateTime(nowTime);// 更新时间
						accountRecharge.setStatus(RECHARGE_STATUS_SUCCESS);// 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
						accountRecharge.setAccountId(accountId);// 电子账户
						// del by liuyang 20180119 银行文件对账功能修改 start
						// accountRecharge.setBankSeqNo(txDate + txTime + seqNo);// 交易流水号
						// del by liuyang 20180119 银行文件对账功能修改 end
						boolean isAccountRechargeFlag = this.accountRechargeMapper.updateByExampleSelective(accountRecharge, accountRechargeExample) > 0 ? true : false;
						if (!isAccountRechargeFlag) {
							throw new Exception("充值后,回调更新充值记录表失败!" + "充值订单号:" + orderId + ".用户ID:" + userId);
						}
						Account newAccount = new Account();
						// 更新账户信息
						newAccount.setUserId(userId);// 用户Id
						newAccount.setBankTotal(txAmount); // 累加到账户总资产
						newAccount.setBankBalance(txAmount); // 累加可用余额
						newAccount.setBankBalanceCash(txAmount);// 银行账户可用户
						boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount) > 0 ? true : false;
						if (!isAccountUpdateFlag) {
							throw new Exception("提现后,更新用户Account表失败!");
						}
						// 重新获取用户账户信息
						account = this.getAccount(userId);
						// 生成交易明细
						AccountList accountList = new AccountList();
						accountList.setNid(orderId);
						accountList.setUserId(userId);
						accountList.setAmount(txAmount);
						// mod by liuyang 20180119 银行文件对账功能修改 start
						accountList.setTxDate(accountRecharge.getTxDate());// 交易日期
						accountList.setTxTime(accountRecharge.getTxTime());// 交易时间
						accountList.setSeqNo(String.valueOf(accountRecharge.getSeqNo()));// 交易流水号
						accountList.setBankSeqNo((accountRecharge.getTxDate() + accountRecharge.getTxTime() + String.valueOf(accountRecharge.getSeqNo())));
						// mod by liuyang 20180119 银行文件对账功能修改 end
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
						//accountList.setBaseUpdate(nowTime);
						accountList.setOperator(userId + "");
						accountList.setIp(ip);
						//accountList.setIsUpdate(0);
						//accountList.setBaseUpdate(0);
						//accountList.setInterest(null);
						accountList.setWeb(0);
						accountList.setIsBank(1);// 是否是银行的交易记录 0:否 ,1:是
						accountList.setCheckStatus(0);// 对账状态0：未对账 1：已对账
						accountList.setTradeStatus(1);// 成功状态
						// 插入交易明细
						boolean isAccountListUpdateFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;

						if (isAccountListUpdateFlag) {
							// 更新成功
							// 提交事务
							this.transactionManager.commit(txStatus);

							// updateBankMobile 除了异常不管
							//updateBankMobile(userId,accountRecharge.getCardid(),mobile);
							jsonObject.put("flag",true);
							jsonObject.put("cardId",accountRecharge.getCardid());
							jsonObject.put("error","0");
							jsonObject.put("errorDesc","充值成功");

							// 如果需要短信
							UserVO users = request.getUserVO();
							// 可以发送充值短信时
							if (users != null && users.getRechargeSms() != null && users.getRechargeSms() == 0) {
								// 替换参数
								Map<String, String> replaceMap = new HashMap<String, String>();
								replaceMap.put("val_amount", txAmount.toString());
								replaceMap.put("val_fee", "0");
								UserInfoVO info = request.getUserInfoVO();
								replaceMap.put("val_name", info.getTruename().substring(0, 1));
								replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
								SmsMessage smsMessage = new SmsMessage(userId, replaceMap, null, null, MessageConstant.SMS_SEND_FOR_USER, null, CustomConstants.PARAM_TPL_CHONGZHI_SUCCESS,
										CustomConstants.CHANNEL_TYPE_NORMAL);
								AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_TPL_CHONGZHI_SUCCESS);
								smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
								appMessageProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(appMsMessage)));

							}
							// 上市活动充值-暂时不迁
							//CommonSoaUtils.listedTwoRecharge(userId, txAmount);
							return jsonObject.toJSONString();
						} else {
							// 回滚事务
							transactionManager.rollback(txStatus);
							// 查询充值交易状态
							accountRecharges = accountRechargeMapper.selectByExample(example);// 查询充值记录
							accountRecharge = accountRecharges.get(0);
							if (RECHARGE_STATUS_SUCCESS == accountRecharge.getStatus()) {
								jsonObject.put("error","0");
								jsonObject.put("errorDesc","充值成功");
								return jsonObject.toJSONString();
							} else {
								// 账户数据过期，请查看交易明细 跳转中间页
								jsonObject.put("error","1");
								jsonObject.put("errorDesc","账户数据过期，请查看交易明细");
								return jsonObject.toJSONString();
							}
						}
					} catch (Exception e) {
						// 回滚事务
						transactionManager.rollback(txStatus);
						System.err.println(e);
					}
				}
			} else {
				System.out.println("充值失败,未查询到相应的充值记录." + "用户ID:" + userId + ",充值订单号:" + orderId);
				jsonObject.put("error","1");
				jsonObject.put("errorDesc","充值失败,未查询到相应的充值记录");
				return jsonObject.toJSONString();
			}
		} else {
			// 更新订单信息
			AccountRechargeExample example = new AccountRechargeExample();
			example.createCriteria().andNidEqualTo(orderId);
			List<AccountRecharge> accountRecharges = this.accountRechargeMapper.selectByExample(example);
			if (accountRecharges != null && accountRecharges.size() == 1) {
				AccountRecharge accountRecharge = accountRecharges.get(0);
				if (RECHARGE_STATUS_WAIT == accountRecharge.getStatus()) {
					// 更新处理状态
					accountRecharge.setStatus(RECHARGE_STATUS_FAIL);// 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
					accountRecharge.setUpdateTime(nowTime);
					accountRecharge.setMessage(errorMsg);
					accountRecharge.setAccountId(accountId);// 电子账户
					// del by liuyang 20180119 银行文件对账功能修改 start
					// accountRecharge.setBankSeqNo(txDate + txTime + seqNo);// 交易流水号
					// del by liuyang 20180119 银行文件对账功能修改 end
					this.accountRechargeMapper.updateByPrimaryKeySelective(accountRecharge);
				}
			}
			jsonObject.put("error","1");
			jsonObject.put("errorDesc",errorMsg);
			return jsonObject.toJSONString();
		}
		return null;
	}
}
