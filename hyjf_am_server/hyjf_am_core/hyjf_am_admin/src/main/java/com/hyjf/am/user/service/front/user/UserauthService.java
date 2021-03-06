package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.dao.model.customize.AdminUserAuthListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthLogListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserPayAuthCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserRePayAuthCustomize;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;
import java.util.Map;

public interface UserauthService {

	/**
	 * 获取权限列表
	 * 
	 * @return
	 */
	public List<AdminUserAuthListCustomize> getRecordList(Map<String, Object> bankCardUser, int limitStart,
			int limitEnd);

	/**
	 * 调银行api拿用户数据(同步用户授权状态用)
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @param type 1自动出借授权  2债转授权
	 * @return
	 */
	BankCallBean getUserAuthQuery(Integer userId, String type);

	/**
	 * 更新用户授权状态
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @param bankCallBean 更新的bean
	 * @return
	 */
	void updateUserAuthState(Integer userId,BankCallBean bankCallBean);

	/**
	 * @param authUser
	 * @return
	 */
	public int countRecordTotal(Map<String, Object> authUser);

	/**
	 * 
	 * 获取授权列表数量
	 * 
	 * @author pcc
	 * @param authUser
	 * @return
	 */
	public int countRecordTotalLog(Map<String, Object> authUser);

	/**
	 * 
	 * 获取授权列表
	 * 
	 * @author pcc
	 * @param authUser
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	public List<AdminUserAuthLogListCustomize> getRecordListLog(Map<String, Object> authUser, int limitStart,
			int limitEnd);

	/**
	 * 缴费授权记录数
	 */
	public int countRecordTotalPay(Map<String, Object> authUser);

	/**
	 * 缴费授权列表
	 */

	public List<AdminUserPayAuthCustomize> getRecordListPay(Map<String, Object> authUser, int limitStart, int limitEnd);

	/**
	 * 更新缴费授权的状态
	 */
	public void updatePayAuthRecord(int id, String signEndDate, int authtype);

	/**
	 * 还款记录数
	 * 
	 * @param authUser
	 * @return
	 */
	public int countRecordTotalRePay(Map<String, Object> authUser);

	/**
	 * 缴费授权列表
	 */

	public List<AdminUserRePayAuthCustomize> getRecordListRePay(Map<String, Object> authUser, int limitStart,
			int limitEnd);

	// /**
	// * 更新缴费授权的状态
	// */
	public void updateRePayAuthRecord(int id, String signEndDate, int authtype);

	public int isDismissPay(int userid);

	public int isDismissRePay(int userid);

	/**
	 * 出借授权解约更新
	 * 
	 * @param userId
	 */
	public void updateCancelInvestAuth(int userId);

	void insertUserAuthLog2(int userId, String orderId, String authType);

	/**
	 * 债转授权解约更新表
	 * 
	 * @param userId
	 */
	void updateCancelCreditAuth(int userId);
    /**
    *
    * 出借授权解约
    * @author sunss
    * @return
    */
   BankCallBean cancelInvestAuth(int userId, String channel);

   /**
    *
    * 债转授权解约
    * @author sunss
    * @return
    */
   BankCallBean cancelCreditAuth(int userId, String channel);
}
