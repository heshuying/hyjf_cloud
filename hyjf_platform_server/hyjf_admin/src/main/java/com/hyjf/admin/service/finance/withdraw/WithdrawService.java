package com.hyjf.admin.service.finance.withdraw;

import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.am.response.admin.WithdrawCustomizeResponse;
import com.hyjf.am.resquest.admin.WithdrawBeanRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;

import java.util.Map;

public interface WithdrawService extends BaseAdminService {

	/**
	 * 获取提现列表数量
	 * @return
	 */
	public int getWithdrawRecordCount(WithdrawBeanRequest request);

	/**
	 * 获取提现列表
	 * @return
	 */
	public WithdrawCustomizeResponse getWithdrawRecordList(WithdrawBeanRequest request);

	/**
	 * 根据用户Id查询用户账户信息
	 * 
	 * @param userId
	 * @return
	 */
	public AccountVO getAccountByUserId(Integer userId);

	/**
	 * 根据订单号查询提现订单
	 * 
	 * @param nid
	 * @return
	 */
	public AccountWithdrawVO queryAccountwithdrawByNid(String nid, Integer userId);

	/**
	 * 提现成功后,更新用户账户信息,提现记录
	 * 
	 * @param userId
	 * @param nid
	 * @return
	 */
	public boolean updateAccountAfterWithdraw(Map<String, String> param);

	/**
	 * 提现失败后,更新用户的提现记录
	 * 
	 * @param userId
	 * @param nid
	 * @return
	 */
	public boolean updateAccountAfterWithdrawFail(Integer userId, String nid) throws Exception;

}
