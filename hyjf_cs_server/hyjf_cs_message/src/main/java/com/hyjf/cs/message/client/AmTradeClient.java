package com.hyjf.cs.message.client;

import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.vo.config.EventVO;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

import java.util.List;

/**
 * @author lisheng
 * @version AmTradeClient, v0.1 2018/7/30 14:42
 */

public interface AmTradeClient {

	AccountVO getAccountByUserId(Integer userId);

	/**
	 * 获取出借金额和预期金额
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	BorrowTenderResponse getBorrowTender(int userId, int begin, int end);

	/**
	 * 获取出借金额和预期金额
	 * 
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	CreditTenderResponse getCreditTender(int userId, int begin, int end);

	/**
	 * 获取汇计划预计
	 * 
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	HjhAccedeResponse getAccede(int userId, int begin, int end);

	/**
	 * 获取优惠券 预期金额
	 * 
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	BorrowTenderCpnResponse getBorrowTenderCPN(int userId, int begin, int end);

	/**
	 * 获取还款总额
	 * 
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	BorrowRecoverResponse getBorrowRecover(int userId, int begin, int end);

	/**
	 * 获取还款总额
	 * 
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	CreditRepayResponse getCreditRepay(int userId, int begin, int end);

	/**
	 * 获取债转总额
	 * 
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	CreditRepayResponse getCreditRepayToCredit(int userId, int begin, int end);

	/**
	 * 获取用户可用优惠券
	 * 
	 * @param userId
	 * @return
	 */
	boolean getCoupon(int userId);

	EventVO getEventsAll(int begin, int end);

	/**
	 * 获取统计数据
	 * 
	 * @return
	 */
	TotalInvestAndInterestVO getTotalInvestAndInterest();

	/**
	 * 发送定时短信筛选用户
	 *
	 * @param requestBean
	 * @return
	 */
	List<String> queryUser(SmsCodeUserRequest requestBean);

	/**
	 * 北互金根据条件获取智投数
	 * @param planNid
	 * @return
	 */
	HjhPlanVO getHjhPlan(String planNid);
}
