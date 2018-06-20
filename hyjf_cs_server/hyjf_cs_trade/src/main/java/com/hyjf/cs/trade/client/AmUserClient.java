package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date  
 */
public interface AmUserClient {

	UserVO findUserById(Integer userId);

	UserInfoVO findUsersInfoById(int userId);

    CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(Integer userId);

	BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);

	/**
	 * @Description 查询用户授权状态
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 11:52
	 */
	HjhUserAuthVO getHjhUserAuthVO(Integer userId);

	/**
	 * @Description 根据userId查询开户信息
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 15:32
	 */
    BankOpenAccountVO selectBankAccountById(Integer userId);
}
