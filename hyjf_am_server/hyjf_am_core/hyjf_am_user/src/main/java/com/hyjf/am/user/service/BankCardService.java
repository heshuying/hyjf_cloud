package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.BankCard;

/**
 * 银行卡接口
 * @author jijun
 */
public interface BankCardService {

	BankCard getBankCard(Integer userId, String bankId);
	
}
