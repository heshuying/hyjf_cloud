/**
 * Description:会员用户开户记录初始化列表查询
 * Copyright: (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 上午11:01:57
 * Modification History:
 * Modified by :
 * */

package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.BankMerchantAccount;
import com.hyjf.am.trade.dao.model.customize.AdminAccountListCustomize;

public interface AdminAccountCustomizeMapper {

	/**
	 * 根据用户的查询条件查询用户开户列表
	 *
	 * @param accountUser
	 * @return
	 */
	List<AdminAccountListCustomize> selectAccountList(Map<String, Object> accountUser);

	/**
	 * @return
	 */
	int countRecordTotal(Map<String, Object> accountUser);


	/**
	 * @return
	 */
	int updateOfLoansTender(Account account);
	
	/**
	 * 汇计划-优惠券放款
	 * @param account
	 * @return
	 */
	int updateOfLoansTenderHjh(Account account);
	/**
	 * 计划投资用户金额变化
	 */
	int updateOfPlanLoansTender(Account account);
	/**
	 * @return
	 */
	int updateOfLoansBorrow(Account account);

	/**
	 * @return
	 */
	int updateOfRepayTender(Account account);

	/**
	 * @return
	 */
	int updateOfRepayBorrow(Account account);

	/**
	 * @return
	 */
	int updateOfTender(Account account);

	int updateOfTransfer(Account accountNew);

	/**
	 * 回滚相应的预约用户的余额
	 * 
	 * @return
	 */

	int updateAppointAccount(Account account);

	int updateOfDebtInvest(Account account);

	/**
	 * 更新汇添金计划债权承接用户的账户信息
	 * 
	 * @param creditAssignAccount
	 * @return
	 */

	int updateOfPlanCreditAssign(Account creditAssignAccount);

	/**
	 * 更新汇添金计划债权转让用户的账户信息
	 * 
	 * @param creditSellerAccount
	 * @return
	 */

	int updateOfPlanCreditSeller(Account creditSellerAccount);

	/**
	 * 更新相应的汇添金计划专属标的投资的用户的账户信息
	 * 
	 * @param creditSellerAccount
	 * @return
	 */

	int updateOfPlanTender(Account creditSellerAccount);

	/**
	 * 更新相应的汇添金计划专属标的加入的用户的账户信息
	 *
	 * @return
	 */
	int updateOfPlanJoin(Account investAccount);

	/**
	 * 更新相应的汇添金计划专属标解冻用户信息
	 *
	 * @return
	 */
	int updateOfPlanUnFreeze(Account account);

	/**
	 * 冻结金额，更新account表
	 * 
	 * @param investAccount
	 * @return
	 */

	int updateOfPlanFreeze(Account investAccount);

	/**
	 * 更新账户计划余额
	 * 
	 * @Title updateOfPlanBalance
	 * @param investAccount
	 * @return
	 */
	int updateOfPlanBalance(Account investAccount);

	/**
	 * 计划还款更新账户余额
	 * 
	 * @Title updateOfPlanBalance
	 * @param investAccount
	 * @return
	 */
	int updateOfPlanRepay(Account investAccount);

	/**
	 * 
	 * @method: updateOfPlanRepayUnFreeze
	 * @description: 插入还款解冻明细
	 * @param investAccount
	 * @return
	 * @return: int
	 * @mender: zhouxiaoshuai
	 * @date: 2016年11月11日 上午9:28:05
	 */
	int updateOfPlanRepayUnFreeze(Account investAccount);

	/**
	 * 计划还款更新账户余额
	 * 
	 * @Title updateOfPlanBalance
	 * @param investAccount
	 * @return
	 */
	int updateOfPlanRepayAll(Account investAccount);

	/**
	 * @return
	 */
	int updateOfRepayBorrowUser(Account account);

	/**
	 * 汇添金-优惠券还款
	 * 
	 * @param account
	 * @return
	 */
	int updateOfRepayCouponHtj(Account account);
	
	/**
	 * 汇计划-优惠券还款
	 */
	int updateOfRepayCouponHjh(Account account);

	/**
	 * 根据用户的查询条件查询用户开户列表
	 *
	 * @param accountUser
	 * @return
	 */
	List<AdminAccountListCustomize> selectBankAccountList(Map<String, Object> accountUser);

	/**
	 * @return
	 */
	int countBankRecordTotal(Map<String, Object> accountUser);

	/**
	 * 
	 * 同步余额线下充值
	 * 
	 * @author pcc
	 * @param updateAccount
	 * @return
	 */
	int updateOfSynBalance(Account updateAccount);

	/**
	 * 
	 * 同步平台子账户信息
	 * 
	 * @author pcc
	 * @param updateBankMerchantAccount
	 * @return
	 */
	int updateOfBankMerchantAccountSynBalance(BankMerchantAccount updateBankMerchantAccount);

	/**
	 * 还款冻结还款用户资金
	 * 
	 * @param repayAccount
	 * @return
	 */
	int updateOfRepayBorrowFreeze(Account repayAccount);

	/***
	 * 垫付机构还款更新垫付机构账户金额
	 * 
	 * @param newRepayUserAccount
	 * @return
	 */
	int updateOfRepayOrgUser(Account newRepayUserAccount);

	/**
	 * 垫付机构还款 操作借款人账户
	 * 
	 * @param borrowUserAccount
	 * @return
	 */
	int updateOfRepayOrgBorrowUser(Account borrowUserAccount);

	/**
	 * 用户提现成功
	 * 
	 * @param account
	 * @return
	 */
	int updateUserWithdrawSuccess(Account account);

	/**
	 * 用户提现失败
	 * 
	 * @param account
	 * @return
	 */
	int updateUserWithdrawFail(Account account);

	/**
	 * 江西银行提现成功
	 * 
	 * @param account
	 * @return
	 */
	int updateBankWithdrawSuccess(Account account);
	
	/**
	 * 手动冲正余额恢复
	 * 
	 * @param account
	 * @return
	 */
	int updateManualReverseSuccess(Account account);

	/**
	 * 融通宝相关放款
	 * 
	 * @param account
	 * @return
	 */
	int updateOfRTBLoansTender(Account account);

	/**
	 * 充值成功更新账户信息
	 * 
	 * @param newAccount
	 * @return
	 */
	int updateBankRechargeSuccess(Account newAccount);

	/**
	 * 债转投资后,更新出让人账户信息
	 * 
	 * @param accountTender
	 * @return
	 */
	int updateCreditSellerSuccess(Account accountTender);

	/**
	 * 债转投资后,更新投资人账户信息
	 * 
	 * @param newAccountCrediter
	 * @return
	 */
	int updateCreditAssignSuccess(Account newAccountCrediter);

	/**
	 * 债权迁移后,更新加息投资的投资人账户的累计收益等
	 * @param newAccount
	 * @return
	 */
	int updateTenderUserAccount(Account newAccount);
	
	/**
	 * 债权迁移后,更新投资人的累计投资
	 * @param newAccount
	 * @return
	 */
	int updateUserAccountInvestSum(Account newAccount);
	
	/**
	 * 债权迁移后,更新投资人的累计收益
	 * @param newAccount
	 * @return
	 */
	int updateUserAccountInterestSum(Account newAccount);

	/**
	 * 债权迁移后,更新借款人的待还金额
	 * @return
	 */
	int updateBorrowUserBankWaitRepay(Account newAccount);

	/**
	 * 债权迁移后,更新用户江西银行总资产
	 * @param newAccount
	 * @return
	 */
	int updateBankTotal(Account newAccount);
	
	/**
	 * 融通宝加息部分减扣
	 * @param newAccount
	 * @return
	 */
	int updateIncreaseInterestSubtract(Account newAccount);

	/**
	 * 自动投资计算
	 * @param newAccount
	 * @return
	 */
	int updateOfPlanAccedeTender(Account newAccount);
	/**
	 * 汇计划还款
	 * @param account
	 * @return
	 */
	int updateOfPlanRepayAccount(Account account);
	/**
	 * 汇计划还款投资人金额变化
	 * @param tenderAccount
	 * @return
	 */
	int updateOfRepayPlanTender(Account tenderAccount);

	/**
	 * 账户分佣更新转出用户账户信息
	 * @param tranferUserAccount
	 * @return
	 */
	int updateOfSubCommissionTransfer(Account tranferUserAccount);

    /**
     * 账户分佣更新转入用户账户信息
     * @param receiveUserAccount
     * @return
     */
    int updateOfSubCommissionTransferIn(Account receiveUserAccount);

	
	/**
	 * 汇计划全部流程对用户账户表计算更新
	 * @param newAccount
	 * @return
	 */
	int updateAccountForHjhProcess(Account newAccount);

	/**
	 * 
	 * 汇计划二期锁定计划时候修改账户总资产
	 * @param accountTender
	 * @return
	 */
    int updateBankTotalForLockPlan(Account accountTender);
}
