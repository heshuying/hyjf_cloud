package com.hyjf.am.user.service.front.account;

import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.service.BaseService;

/**
 * 银行卡接口
 * @author jijun
 */
public interface BankCardService extends BaseService {

	BankCard getBankCard(Integer userId, String bankId);
	BankCard getBankCard(Integer userId);

	/**
	 * 根据用户id和银行卡号查询银行卡信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	BankCard selectBankCardByUserIdAndCardNo(BankCardRequest request);
}
