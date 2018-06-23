package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.cs.trade.client.AmUserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date  
 */

@Service
public class AmUserClientImpl implements AmUserClient {
	private static Logger logger = LoggerFactory.getLogger(AmUserClient.class);
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public UserVO findUserById(int userId) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findById/" + userId, UserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserInfoVO findUsersInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(Integer userId) {
		CorpOpenAccountRecordResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/corpOpenAccountRecord/findByUserId/" + userId, CorpOpenAccountRecordResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode) {
		BankReturnCodeConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/getBankReturnCodeConfig/"+retCode,BankReturnCodeConfigResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * @param userId
	 * @Description 查询用户授权状态
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 11:52
	 */
	@Override
	public HjhUserAuthVO getHjhUserAuthVO(Integer userId) {
		HjhUserAuthResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/getHjhUserAuthByUserId/" + userId, HjhUserAuthResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * @param userId
	 * @Description 根据userId查询开户信息
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 15:32
	 */
	@Override
	public BankOpenAccountVO selectBankAccountById(Integer userId) {
		String url = "http://AM-USER/am-user/bankopen/selectById/" + userId;
		BankOpenAccountResponse response = restTemplate.getForEntity(url, BankOpenAccountResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * @param userId
	 * @Description 根据userId查询推荐人
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/20 16:11
	 */
	@Override
	public UserVO getSpreadsUsersByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/user/findReffer/" + userId;
		UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * @param userId
	 * @Description 根据用户ID查询查询CRM
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/20 18:13
	 */
	@Override
	public UserInfoCrmVO queryUserCrmInfoByUserId(int userId) {
		String url = "http://AM-USER/am-user/userInfo/findUserCrmInfoByUserId/" + userId;
		UserInfoCrmResponse response = restTemplate.getForEntity(url, UserInfoCrmResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查看用户详情
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoCustomizeVO queryUserInfoCustomizeByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/userInfo/queryUserInfoCustomizeByUserId/" + userId;
		UserInfoCustomizeResponse response = restTemplate.getForEntity(url,UserInfoCustomizeResponse.class).getBody();
		if (response!=null){
			return response.getResult();
		}
		return null;
	}

	/**
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public SpreadsUserVO querySpreadsUsersByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/userInfo/querySpreadsUsersByUserId/" + userId;
		SpreadsUserResponse response = restTemplate.getForEntity(url,SpreadsUserResponse.class).getBody();
		if (response!=null){
			return response.getResult();
		}
		return null;
	}

	@Override
	public EmployeeCustomizeVO selectEmployeeByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/userInfo/selectEmployeeByUserId/" + userId;
		EmployeeCustomizeResponse response = restTemplate.getForEntity(url,EmployeeCustomizeResponse.class).getBody();
		if (response!=null){
			return response.getResult();
		}
		return null;
	}

	/**
	 * 修改用户对象
	 *
	 * @param user
	 * @return
	 */
	@Override
	public boolean updateByPrimaryKeySelective(UserVO user) {
		Integer result = restTemplate
				.postForEntity("http://AM-USER/am-user/user/updateByUserId", user, Integer.class).getBody();
		if (result != null) {
			return result == 0 ? false : true;
		}
		return false;
	}

    /**
     * 根据userId查询用户推广链接注册
     *
     * @param userId
     * @return
     */
    @Override
    public UtmRegVO findUtmRegByUserId(Integer userId) {
        String url = "http://AM-USER/am-user/user/findUtmRegByUserId/" + userId;
        UtmRegResponse response = restTemplate.getForEntity(url, UtmRegResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新huiyingdai_utm_reg的首投信息
     *
     * @param params
     * @return
     */
    @Override
    public boolean updateFirstUtmReg(Map<String, Object> params) {
        Integer result = restTemplate
                .postForEntity("http://AM-USER/am-user/user/updateFirstUtmReg", params, Integer.class).getBody();
        if (result != null) {
            return result == 0 ? false : true;
        }
        return false;
    }

}
