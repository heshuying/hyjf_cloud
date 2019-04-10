/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.resquest.admin.CommissionComboRequest;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.finance.AdminHjhCommissionService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.hjh.HjhCommissionCustomizeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/*import com.hyjf.am.trade.mq.producer.AppMessageProducer;*/
/*import com.hyjf.am.trade.mq.producer.SmsProducer;*/

/**
 * @author libin
 * @version AdminHjhCommissionServiceImpl.java, v0.1 2018年8月7日 下午4:45:57   旧
 */
@Service
public class AdminHjhCommissionServiceImpl extends BaseServiceImpl implements AdminHjhCommissionService{

	private static final Logger logger = LoggerFactory.getLogger(AdminHjhCommissionServiceImpl.class);
	
    // 根据用户ID和模版号给某用户发短信
    public static final String SMSSENDFORUSER = "smsSendForUser";
	@Autowired
	private CommonProducer commonProducer;

	@Override
	public Integer countTotal(HjhCommissionRequest request) {
		// 部门
		if (Validator.isNotNull(request.getCombotreeSrch())) {
			if (request.getCombotreeSrch().contains(StringPool.COMMA)) {
				String[] list = request.getCombotreeSrch().split(StringPool.COMMA);
				request.setCombotreeListSrch(list);
			} else {
				request.setCombotreeListSrch(new String[] { request.getCombotreeSrch() });
			}
		}
		Integer count = this.adminHjhCommissionMapper.queryPushMoneyDetailCount(request);
		return count;
	}

	@Override
	public List<HjhCommissionCustomizeVO> selectHjhCommissionList(HjhCommissionRequest request, int limitStart,
			int limitEnd) {
		// 部门
		if (Validator.isNotNull(request.getCombotreeSrch())) {
			if (request.getCombotreeSrch().contains(StringPool.COMMA)) {
				String[] list = request.getCombotreeSrch().split(StringPool.COMMA);
				request.setCombotreeListSrch(list);
			} else {
				request.setCombotreeListSrch(new String[] { request.getCombotreeSrch() });
			}
		}
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
        	request.setLimitStart(limitStart);
        }
        if (limitEnd > 0) {
        	request.setLimitEnd(limitEnd);
        }
		List<HjhCommissionCustomizeVO> hjhCommissionList = this.adminHjhCommissionMapper.queryPushMoneyDetail(request);
		return hjhCommissionList;
	}

	@Override
	public Map<String, Object> queryPushMoneyTotle(HjhCommissionRequest request, int limitStart, int limitEnd) {
		// 部门
		if (Validator.isNotNull(request.getCombotreeSrch())) {
			if (request.getCombotreeSrch().contains(StringPool.COMMA)) {
				String[] list = request.getCombotreeSrch().split(StringPool.COMMA);
				request.setCombotreeListSrch(list);
			} else {
				request.setCombotreeListSrch(new String[] { request.getCombotreeSrch() });
			}
		}
/*        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
        	request.setLimitStart(limitStart);
        }
        if (limitEnd > 0) {
        	request.setLimitEnd(limitEnd);
        }*/
        Map<String, Object> map = this.adminHjhCommissionMapper.queryPushMoneyTotle(request);
		return map;
	}

	@Override
	public TenderCommissionVO queryTenderCommissionByPrimaryKey(int ids) {
		TenderCommissionVO vo = new TenderCommissionVO();
		TenderCommission tenderCommission = this.tenderCommissionMapper.selectByPrimaryKey(ids);
		if(tenderCommission != null){
			BeanUtils.copyProperties(tenderCommission, vo);
		}
		return vo;
	}

	@Override
	public List<OADepartmentCustomizeVO> getCrmDepartmentList(HjhCommissionRequest request) {
		List<OADepartmentCustomizeVO> departmentList = this.adminHjhCommissionMapper.getCrmDepartmentList();
		return departmentList;
	}

	@Override
	public Integer queryCrmCuttype(Integer userId) {
		Integer cuttype= this.adminHjhCommissionMapper.queryCuttype(userId);
		return cuttype;
	}

	@Override
	public Integer updateTenderCommissionRecord(CommissionComboRequest request) {
		int ret = 0;
		BigDecimal money = BigDecimal.ZERO;
		// 组合层已经校验过非空
		TenderCommissionVO commission = request.getTenderCommission();
		TenderCommission tenderCommission =tenderCommissionMapper.selectByPrimaryKey(commission.getId());
		
		/*1.修改提成表*/
		// 增加时间
		Integer time = GetDate.getMyTimeInMillis();
		// 发放人ID
		Integer userId = commission.getUserId();
		// 出借人ID
		Integer tenderUserId = commission.getTenderUserId();
		// 操作者用户名
		String operator = commission.getLoginUserName();
		// 更新发放状态
		tenderCommission.setStatus(1);// 已发放
		tenderCommission.setUpdateTime(new Date());
		tenderCommission.setSendTime(time);
		// 把 TenderCommissionVO 赋值给 TenderCommission
		// BeanUtils.copyProperties(commission, tenderCommission);
		tenderCommission.setRemark(commission.getBorrowNid());
		ret += this.tenderCommissionMapper.updateByPrimaryKeySelective(tenderCommission);

		/*2.修改提成日志表*/
		/*原SpreadsLog spreadsLog = new SpreadsLog();*/
		CommissionLog commissionLog = new CommissionLog();
		commissionLog.setUserId(tenderUserId);
		commissionLog.setSpreadsUserId(userId);
		commissionLog.setNid(commission.getOrdid());
		commissionLog.setType("full");
		commissionLog.setSpreadsType("tender");
		commissionLog.setAccountType("capital");
		/*原commissionLog.setScales(getScales(commission.getBorrowNid(), userId).toString());*/
		commissionLog.setScales(getScales(commission.getBorrowNid(), request.getAttribute()).toString());
		commissionLog.setBorrowNid(commission.getBorrowNid());
		commissionLog.setTenderId(commission.getTenderId());
		commissionLog.setRepayId(0);
		commissionLog.setAccountAll(BigDecimal.ZERO);
		commissionLog.setAccountCapital(commission.getAccountTender());
		commissionLog.setAccountInterest(BigDecimal.ZERO);
		commissionLog.setAccount(commission.getCommission());
		commissionLog.setRemark("");
		/*原commissionLog.setAddtime(String.valueOf(time));*/
		commissionLog.setCreateTime(new Date());
		/*原commissionLog.setAddip("");*/
		commissionLog.setCreateIp("");
		commissionLog.setPayStatus(1);
		commissionLog.setIsValid(1);
		
		ret += this.commissionLogMapper.insertSelective(commissionLog);

		/*3.更新账户信息*/
		AccountExample accountExample = new AccountExample();
		AccountExample.Criteria accountCriteria = accountExample.createCriteria();
		accountCriteria.andUserIdEqualTo(userId);
		Account account = accountMapper.selectByExample(accountExample).get(0);
		BigDecimal bankBalanceCash = account.getBankBalanceCash() == null ? BigDecimal.ZERO : account.getBankBalanceCash();
		
		// 提成
		/*原BigDecimal money = new BigDecimal(bankBean == null ? chinapnrBean.getTransAmt() : bankBean.getTxAmount());*/
		if(StringUtils.isNotEmpty(request.getAccountId()) && StringUtils.isNotEmpty(request.getSeqNo()) && StringUtils.isNotEmpty(request.getTxDate())){
			//如果上面三个参数为空，则说明 bankBean 为空,使用 bankBean 的 TxAmount
			money = new BigDecimal(request.getTxAmount());
		} else {
			// 如果 ankBean 为空 ，则使用 chinapnrBean 的 TransAmt
			money = new BigDecimal(request.getTransAmt());
		}
		/*原if(bankBean != null){*/
		if(StringUtils.isNotEmpty(request.getAccountId()) && StringUtils.isNotEmpty(request.getSeqNo()) && StringUtils.isNotEmpty(request.getTxDate())){
		    account.setBankBalance(account.getBankBalance().add(money));
		    account.setBankTotal(account.getBankTotal().add(money)); // 累加到账户总资产
		    account.setBankBalanceCash(bankBalanceCash.add(money));
		    logger.info("提成为：" + money);
		}
		/*原else if(chinapnrBean != null){*/
		else if(StringUtils.isNotEmpty(request.getTransAmt()) && StringUtils.isNotEmpty(request.getChinapnrlogIp())){
		    account.setTotal(account.getTotal().add(money)); // 累加到账户总资产
		    account.setBalance(account.getBalance().add(money)); // 累加可用余额
		    /*注意：微服务迁移将Income字段删除*/
		    //account.setIncome(account.getIncome().add(money));// 累加到总收入
		}
		ret += this.accountMapper.updateByExampleSelective(account, accountExample);

		/*4.写入收支明细*/
/*		BankOpenAccountVO bankOpenAccountInfo = null;
		bankOpenAccountInfo = getBankOpenAccount(userId);*/
		AccountList accountList = new AccountList();
		accountList.setNid(commission.getLogOrderId());
		accountList.setSeqNo(request.getSeqNo());
		accountList.setTxDate(Integer.parseInt(request.getTxDate()));
        accountList.setTxTime(Integer.parseInt(request.getTxTime()));
        accountList.setBankSeqNo(request.getTxDate() + request.getTxTime() + request.getSeqNo());
        accountList.setCheckStatus(0);
        accountList.setTradeStatus(1);
		accountList.setUserId(userId);
		/*原accountList.setAccountId(bankOpenAccountInfo.getAccount());*/
		accountList.setAccountId(request.getAccount());
		accountList.setAmount(money);
		accountList.setType(1);// 1收入2支出3冻结
		accountList.setTrade("borrow_spreads_tender");
		accountList.setTradeCode("balance");
		accountList.setBankTotal(account.getBankTotal()); // 银行总资产
        accountList.setBankBalance(account.getBankBalance()); // 银行可用余额
        accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
        accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
        accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
        accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
        accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
        accountList.setBankAwait(account.getBankAwait());// 银行待收总额
        accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
        accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计出借
        accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
        accountList.setPlanBalance(account.getPlanBalance());
        accountList.setPlanFrost(account.getPlanFrost());
		accountList.setTotal(account.getTotal());
		accountList.setBalance(account.getBalance());
		accountList.setFrost(account.getFrost());
		accountList.setAwait(account.getAwait());
		accountList.setRepay(account.getRepay());
		accountList.setRemark(commission.getBorrowNid());
		accountList.setCreateTime(new Date());
		accountList.setOperator(operator);
		// 前面 chinapnrBean 传的是null
		/*原accountList.setIp(bankBean == null ? chinapnrBean.getLogIp() : bankBean.getLogIp());*/
		accountList.setIp(request.getLogIp());
		// 下面三个字段微服务迁移已删除
/*		accountList.setIsUpdate(0);
		accountList.setBaseUpdate(0);
		accountList.setInterest(null);*/
		accountList.setWeb(2);
		/*原accountList.setIsBank(bankBean == null ? 0 : 1);*/
		// 是否是银行的交易记录(0:否,1:是)
		if(StringUtils.isNotEmpty(request.getAccountId()) && StringUtils.isNotEmpty(request.getSeqNo()) && StringUtils.isNotEmpty(request.getTxDate())){
			accountList.setIsBank(1);
		} else {
			accountList.setIsBank(0);
		}
		ret += this.accountListMapper.insertSelective(accountList);

		/*5.插入网站收支明细记录 改为插入mongo库*/
		AccountWebListVO accountWebList = new AccountWebListVO();
		accountWebList.setOrdid(accountList.getNid());// 订单号
		accountWebList.setUserId(accountList.getUserId()); // 出借者
		accountWebList.setAmount(Double.valueOf(accountList.getAmount().toString())); // 管理费
		accountWebList.setType(CustomConstants.TYPE_OUT); // 类型1收入 2支出
		accountWebList.setTrade(CustomConstants.TRADE_TGTC); // 提成
		accountWebList.setTradeType(CustomConstants.TRADE_TGTC_NM); // 出借推广提成
		accountWebList.setRemark(tenderCommission.getBorrowNid()); // 出借推广提成
		//accountWebList.setCreateTime(GetterUtil.getInteger(accountList.getCreateTime()));
        accountWebList.setCreateTime(GetDate.getTime10(accountList.getCreateTime()));
		//网站首支明细队列 参照 RealTimeBorrowLoanServiceImpl line 1656
		/*原ret += insertAccountWebList(accountWebList);*/
		try {
			logger.info("发送收支明细---" + request.getAccount() + "---------" + accountList.getAmount());
			commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebList));
        } catch (MQException e) {
            logger.error(e.getMessage());
        }
		
		/*原if(bankBean !=null){*/
		if(StringUtils.isNotEmpty(request.getAccountId()) && StringUtils.isNotEmpty(request.getSeqNo()) && StringUtils.isNotEmpty(request.getTxDate())){
			BankMerchantAccount nowBankMerchantAccount = this.getBankMerchantAccount(request.getAccountId());
		    nowBankMerchantAccount.setAvailableBalance(nowBankMerchantAccount.getAvailableBalance().subtract(money));
		    nowBankMerchantAccount.setAccountBalance(nowBankMerchantAccount.getAccountBalance().subtract(money));
		    nowBankMerchantAccount.setUpdateTime(new Date());
		    /*6.更新红包账户信息*/
		    int updateCount = this.updateBankMerchantAccount(nowBankMerchantAccount);
		    if(updateCount > 0){
		    	/*7.添加红包明细*/
		        BankMerchantAccountList bankMerchantAccountList = new BankMerchantAccountList();
		        bankMerchantAccountList.setOrderId(commission.getOrdid());
		        bankMerchantAccountList.setBorrowNid(commission.getBorrowNid());
		        bankMerchantAccountList.setUserId(commission.getUserId());
		        /*原bankMerchantAccountList.setAccountId(bankOpenAccountInfo.getAccount());*/
		        bankMerchantAccountList.setAccountId(request.getAccount());
		        bankMerchantAccountList.setAmount(money);
		        /*原bankMerchantAccountList.setBankAccountCode(bankBean.getAccountId());*/
		        bankMerchantAccountList.setBankAccountCode(request.getAccountId());
		        bankMerchantAccountList.setBankAccountBalance(nowBankMerchantAccount.getAccountBalance());
		        bankMerchantAccountList.setBankAccountFrost(nowBankMerchantAccount.getFrost());
		        bankMerchantAccountList.setTransType(CustomConstants.BANK_MER_TRANS_TYPE_AUTOMATIC);
		        bankMerchantAccountList.setType(CustomConstants.BANK_MER_TYPE_EXPENDITURE);
		        bankMerchantAccountList.setStatus(CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS);
		        /*原bankMerchantAccountList.setTxDate(Integer.parseInt(bankBean.getTxDate()));*/
		        bankMerchantAccountList.setTxDate(Integer.parseInt(request.getTxDate()));
		        /*原bankMerchantAccountList.setTxTime(Integer.parseInt(bankBean.getTxTime()));*/
		        bankMerchantAccountList.setTxTime(Integer.parseInt(request.getTxTime()));
		        /*原bankMerchantAccountList.setSeqNo(bankBean.getSeqNo());*/
		        bankMerchantAccountList.setSeqNo(request.getSeqNo());
		        bankMerchantAccountList.setCreateTime(new Date());
		        bankMerchantAccountList.setUpdateTime(new Date());
		        bankMerchantAccountList.setUpdateTime(new Date());
		        /*原bankMerchantAccountList.setRegionName(userInfoCustomize.getRegionName());*/
		        bankMerchantAccountList.setRegionName(request.getRegionName());
		        /*原bankMerchantAccountList.setBranchName(userInfoCustomize.getBranchName());*/
		        bankMerchantAccountList.setBranchName(request.getBranchName());
		        /*原bankMerchantAccountList.setDepartmentName(userInfoCustomize.getDepartmentName());*/
		        bankMerchantAccountList.setDepartmentName(request.getDepartmentName());
		        bankMerchantAccountList.setCreateUserId(userId);
		        bankMerchantAccountList.setUpdateUserId(userId);
		        /*原bankMerchantAccountList.setCreateUserName(userInfoCustomize.getUserName());*/
		        bankMerchantAccountList.setCreateUserName(request.getUserName());
		        /*原bankMerchantAccountList.setUpdateUserName(userInfoCustomize.getUserName());*/
		        bankMerchantAccountList.setUpdateUserName(request.getUserName());
		        bankMerchantAccountList.setRemark("出借推广提成");
		        this.bankMerchantAccountListMapper.insertSelective(bankMerchantAccountList);
		    }
		}

		// 原 发短信接口
/*		Map<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put("val_amount", commission.getCommission().toString());
		   SmsMessage smsMessage = new SmsMessage(userId, replaceMap, null, null, SMSSENDFORUSER, null,
                   		CustomConstants.PARAM_TPL_SDTGTC, CustomConstants.CHANNEL_TYPE_NORMAL);
          smsProcesser.gather(smsMessage);*/
		Map<String, String> msg = new HashMap<>();
		msg.put("val_amount", commission.getCommission().toString());
		SmsMessage smsMessage = new SmsMessage(userId, msg, null, null, MessageConstant.SMS_SEND_FOR_USER, null, CustomConstants.PARAM_TPL_SDTGTC,
				CustomConstants.CHANNEL_TYPE_NORMAL); 
		try {
			commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, String.valueOf(userId), smsMessage));
		} catch (MQException e2) {
			logger.error("发送短信失败..", e2);
		}
		
      //原 发app消息接口 
/*      UserInfoCustomize userInfo = this.userInfoCustomizeMapper.selectUserInfoByUserId(userId);
      if (userInfo != null) {
          Map<String, String> param = new HashMap<String, String>();
          if (userInfo.getTrueName() != null && userInfo.getTrueName().length() > 1) {
              param.put("val_name", userInfo.getTrueName().substring(0, 1));
          } else {
              param.put("val_name", userInfo.getTrueName());
          }
          if ("1".equals(userInfo.getSex())) {
              param.put("val_sex", "先生");
          } else if ("2".equals(userInfo.getSex())) {
              param.put("val_sex", "女士");
          } else {
              param.put("val_sex", "");
          }
          param.put("val_amount", commission.getCommission().toString());
          AppMsMessage appMsMessage = new AppMsMessage(null, param, userInfo.getMobile(), MessageDefine.APPMSSENDFORMOBILE,
                  CustomConstants.JYTZ_TPL_SDTGTC);
          appMsProcesser.gather(appMsMessage);
      }*/
		Map<String, String> param = new HashMap<String, String>();
        if (request.getTrueName() != null && request.getTrueName().length() > 1) {
            param.put("val_name", request.getTrueName().substring(0, 1));
        } else {
            param.put("val_name", request.getTrueName());
        }
        if ("1".equals(request.getSex())) {
            param.put("val_sex", "先生");
        } else if ("2".equals(request.getSex())) {
            param.put("val_sex", "女士");
        } else {
            param.put("val_sex", "");
        }
        param.put("val_amount", commission.getCommission().toString());
		AppMsMessage appsmsMessage = new AppMsMessage(userId, param, null,MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_TPL_SDTGTC);
		try {
			commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, String.valueOf(userId),
					appsmsMessage));
		} catch (MQException e) {
			logger.error("发送app消息失败..", e);
		}
		
		return ret;
	}
	
	/**
	 * 根据出借订单号取出借编号
	 *
	 * @param ordId
	 * @return
	 */
	private String getBorrowNidByOrdId(String ordId) {
		BorrowTenderExample example = new BorrowTenderExample();
		example.createCriteria().andNidEqualTo(ordId);
		List<BorrowTender> list = this.borrowTenderMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0).getBorrowNid();
		}
		return null;
	}
	
   /**
    * 
    * 加载红包账户
    * @param accountCode
    * @return
    */
   public BankMerchantAccount getBankMerchantAccount(String accountCode) {
        BankMerchantAccountExample bankMerchantAccountExample = new BankMerchantAccountExample();
        bankMerchantAccountExample.createCriteria().andAccountCodeEqualTo(accountCode);
        List<BankMerchantAccount> bankMerchantAccounts = bankMerchantAccountMapper.selectByExample(bankMerchantAccountExample);
        if (bankMerchantAccounts != null && bankMerchantAccounts.size() != 0) {
            return bankMerchantAccounts.get(0);
        } else {
            return null;
        }
    }
   
   /**
    * 
    * 更新红包账户
    * @param account
    * @return
    */
   public int updateBankMerchantAccount(BankMerchantAccount account){
       return bankMerchantAccountMapper.updateByPrimaryKeySelective(account);
   }
   
	/**
	 * 取得提成利率
	 *
	 * @param borrowNid
	 * @param attribute
	 */
	private BigDecimal getScales(String borrowNid, Integer attribute) {
		BigDecimal rate = BigDecimal.ZERO;
		if (Validator.isNotNull(borrowNid) && Validator.isNotNull(attribute)) {
			// 取得借款数据
			String borrowStyle = null;
			BorrowExample example = new BorrowExample();
			example.createCriteria().andBorrowNidEqualTo(borrowNid);
			List<Borrow> borrowList = this.borrowMapper.selectByExample(example);
			if (borrowList != null && borrowList.size() > 0) {
				borrowStyle = borrowList.get(0).getBorrowStyle();
			}

			/*原UsersInfo usersInfo = super.getUsersInfoByUserId(userId);*/
			if (Validator.isNotNull(attribute)) {
				String type = "";
				// 提成发放人时线上用户或51老用户
				if (attribute == 3) {
					type = "线上用户";
				} 
				// 微服務遷移后無is51字段
/*				else if (usersInfo.getIs51() == 1) {
					type = "51老用户";
				}*/
				
				// 取得提成利率
				PushMoney pushMoney = getPushMoney(type);
				if (pushMoney != null && NumberUtils.isNumber(pushMoney.getDayTender())) {
					if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
						rate = new BigDecimal(pushMoney.getDayTender());
					} else {
						rate = new BigDecimal(pushMoney.getMonthTender());
					}
				}
			}
		}
		return rate;
	}
	
	/**
	 * 取得提成配置
	 *
	 * @param type
	 * @return
	 */
	private PushMoney getPushMoney(String type) {
		PushMoneyExample example = new PushMoneyExample();
		example.createCriteria().andTypeEqualTo(type);
		List<PushMoney> list = this.pushMoneyMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<HjhCommissionCustomizeVO> selectHjhCommissionListWithOutPage(HjhCommissionRequest request) {
		// 部门
		if (Validator.isNotNull(request.getCombotreeSrch())) {
			if (request.getCombotreeSrch().contains(StringPool.COMMA)) {
				String[] list = request.getCombotreeSrch().split(StringPool.COMMA);
				request.setCombotreeListSrch(list);
			} else {
				request.setCombotreeListSrch(new String[] { request.getCombotreeSrch() });
			}
		}
		List<HjhCommissionCustomizeVO> hjhCommissionList = this.adminHjhCommissionMapper.queryPushMoneyDetail(request);
		return hjhCommissionList;
	}
}
