package com.hyjf.am.user.service.front.account;

import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.service.BaseService;

/**
 * 银行卡接口
 * @author jijun
 */
public interface BankCardService extends BaseService {

	BankCard getBankCard(Integer userId, String bankId);
	BankCard getBankCard(Integer userId);
}
