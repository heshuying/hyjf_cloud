package com.hyjf.cs.user.client;

import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.vo.borrow.AccountVO;
import com.hyjf.am.vo.borrow.BanksConfigVO;
import com.hyjf.am.vo.user.BankCardVO;

public interface AmBindCardClient {

	int insertBindCardLog(BankCardLogRequest request);

	String queryBankIdByCardNo(String cardNo);

	int insertUserCard(BankCardRequest request);

	int deleteUserCardByUserId(String userId);

	int countUserCardValid(String userId);

	BankCardVO queryUserCardValid(String userId, String cardNo);

	BanksConfigVO getBanksConfigByBankId(String bankId);

	int updateUserCard(BankCardRequest request);

	int deleteUserCardByCardNo(String cardNo);

	AccountVO getAccount(Integer userId);

}

	