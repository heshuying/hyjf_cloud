package com.hyjf.am.user.service.front.account;

import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.service.BaseService;

import java.util.List;

public interface BankOpenService extends BaseService {

	/**
	 * 保存用户的初始开户记录
	 * @param userId
	 * @param userName
	 * @param
	 * @param clientPc
	 * @param
	 * @return
	 */
	public boolean updateUserAccountLog(int userId, String userName, String mobile, String logOrderId, String clientPc,String name,String idno,String cardNo, String srvAuthCode);



	/**
	 * 更新开户日志表
	 *
	 * @param userId
	 * @param logOrderId
	 * @param status
	 */
	void updateUserAccountLog(Integer userId, String logOrderId, int status , String retCode , String retMsg);


	boolean updateUserAccount(Integer userId, String trueName, String orderId, String accountId, String idNo, Integer bankAccountEsb, String mobile, Integer roleId,Integer isSetPassword);


	UserInfo findUserInfoByCradId(String cardNo);

	/**
	 * 根据用户Id,银行卡号检索用户银行卡信息
	 * @param userId
	 * @return
	 */
	BankCard selectBankCardByUserId(Integer userId);

	/**
	 * 根据用户Id和status,银行卡号检索用户银行卡信息
	 * @param userId
	 * @return
	 */
	List<BankCard> selectBankCardByUserIdAndStatus(Integer userId);


	BankCard getBankCardByCardNo(Integer userId, String cardNo);
	/**
	 * 开户成功后保存用户银行卡信息
	 * @param request
	 * @return
	 */
	boolean saveCardNoToBank(BankCardRequest request);

	/**
	 * @Description 根据订单号查询失败原因
	 * @Author sunss
	 * @Date 2018/6/21 15:52
	 */
	String getBankOpenAccountFiledMess(String logOrdId);

	/**
	 * 获取银行卡信息
	 * @param userId
	 * @param status
	 * @return
	 */
	List<BankCard> selectBankCardByUserIdAndStatus(Integer userId, Integer status);

	/**
	 * 获取微服务上线三天后的开户用户
	 */
	void getBankOpenAccountForCrmRepair(BankOpenAccountExample bankOpenAccountExample);
}
