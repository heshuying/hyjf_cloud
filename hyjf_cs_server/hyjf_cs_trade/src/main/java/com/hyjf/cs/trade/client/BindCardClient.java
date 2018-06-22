package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.BankCardVO;
/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date  
 */
public interface BindCardClient {


	BankCardVO queryUserCardValid(String userId, String cardNo);


	AccountVO getAccount(Integer userId);

	BanksConfigVO getBanksConfigByBankId(String bankId);

	/**
	 * @Description 修改银行联行号
	 * @Author pangchengchao
	 * @Version v0.1
	 * @Date
	 */
	int updateBankCardPayAllianceCode(BankCardVO updateBankCard);

	/**
	 * 根据用户Id,银行卡号检索用户银行卡信息
	 * @param userId
	 * @return
	 */
	BankCardVO selectBankCardByUserId(Integer userId);
}

	