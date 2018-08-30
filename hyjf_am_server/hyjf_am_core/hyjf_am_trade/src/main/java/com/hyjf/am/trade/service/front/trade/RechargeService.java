package com.hyjf.am.trade.service.front.trade;

import com.hyjf.am.resquest.trade.HandleAccountRechargeRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.auto.AccountRechargeExample;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;

import java.util.List;

/**
 * 用户充值Service
 * 
 * @author
 *
 */
public interface RechargeService extends BaseService {

    int selectByOrdId(String ordId);

    int insertSelective(BankRequest bankRequest);

	 AccountRecharge selectByExample(AccountRechargeExample example);

	 int updateByExampleSelective(AccountRecharge accountRecharge, AccountRechargeExample accountRechargeExample);

	 int updateBankRechargeSuccess(Account newAccount);

	 int insertSelective(AccountList accountList);

	 void updateByPrimaryKeySelective(AccountRecharge accountRecharge);

	boolean updateBanks(AccountRechargeVO accountRecharge, String ip);

	/**
	 * 插入充值记录
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	int insertAccountRecharge(AccountRechargeVO accountRechargeVO);

	/**
	 * 根据orderId查询充值记录
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	List<AccountRecharge> selectAccountRechargeByOrderId(String orderId);

	/**
	 * 更新充值的相关信息(接口调用)
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	String handleRechargeInfo(HandleAccountRechargeRequest request);

	/**
	 * 更新充值的相关信息(页面调用)
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	String handleRechargeOnlineInfo(HandleAccountRechargeRequest request);

}
