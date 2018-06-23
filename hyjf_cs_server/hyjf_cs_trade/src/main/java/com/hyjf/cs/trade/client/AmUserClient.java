package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date  
 */
public interface AmUserClient {

	UserVO findUserById(int userId);

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

    /**
     * @Description 根据userId查询推荐人
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/20 16:11
     */
	UserVO getSpreadsUsersByUserId(Integer userId);

	/**
	 * @Description 根据用户ID查询查询CRM
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/20 18:13
	 */
	UserInfoCrmVO queryUserCrmInfoByUserId(int userId);
}
