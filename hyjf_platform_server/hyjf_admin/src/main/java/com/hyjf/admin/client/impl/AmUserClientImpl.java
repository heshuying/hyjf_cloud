package com.hyjf.admin.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmUserClientImpl, v0.1 2018/4/19 12:44
 */

@Service
public class AmUserClientImpl implements AmUserClient {
	private static Logger logger = LoggerFactory.getLogger(AmUserClient.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value("${am.user.service.name}")
	private String userService;

	/**
	 * 根据userName查询user信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public List<UserVO> searchUserByUsername(String userName) {
		String url = "http://AM-USER/am-user/customertransfer/searchuserbyusername/" + userName;
		UserResponse response = restTemplate
				.getForEntity(url, UserResponse.class)
				.getBody();
		if(Response.isSuccess(response)){
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据userId查询accountChinapnr开户信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public List<AccountChinapnrVO> searchAccountChinapnrByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/customertransfer/searchaccountchinapnrbyuserid/" + userId;
		AccountChinapnrResponse response = restTemplate
				.getForEntity(url, AccountChinapnrResponse.class)
				.getBody();
		if(Response.isSuccess(response)){
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据userId查询用户信息
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @return
	 */
	@Override
	public UserVO searchUserByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/customertransfer/searchuserbyuserid/" + userId;
		UserResponse response = restTemplate
				.getForEntity(url, UserResponse.class)
				.getBody();
		if(Response.isSuccess(response)){
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserVO getUserByUserName(String loginUserName) {
		UserResponse response = restTemplate
				.getForEntity(userService+"/user/findByCondition/" + loginUserName, UserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据userId查询用户
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserVO findUserById(final int userId) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findById/" + userId, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据userId查询用户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoVO findUsersInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
	/**
	 * 根据userId列表查询user列表
	 * @auth sunpeikai
	 * @param userIds 用户id列表
	 * @return
	 */
	@Override
	public List<UserVO> findUserListByUserIds(String userIds) {
		UserResponse response = restTemplate
				.postForEntity("http://AM-USER/am-user/platformtransfer/finduserlistbyuserids",userIds, UserResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 利用borrowNid查询出来的异常标的借款人userId查询银行账户
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @return
	 */
	@Override
	public BankOpenAccountVO searchBankOpenAccount(Integer userId) {
		String url = "http://AM-USER/am-user/borrow_regist_exception/searchbankopenaccount/" + userId;
		BankOpenAccountResponse response = restTemplate.getForEntity(url,BankOpenAccountResponse.class).getBody();
		if(response != null){
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户名获取自定义属性的user信息
	 * @auth sunpeikai
	 * @param userName 用户名
	 * @return
	 */
	@Override
	public UserInfoCustomizeVO getUserInfoCustomizeByUserName(String userName) {
		String url = "http://AM-USER/am-user/userInfo/queryUserInfoCustomizeByUserName/" + userName;
		UserInfoCustomizeResponse response = restTemplate.getForEntity(url,UserInfoCustomizeResponse.class).getBody();
		if(response != null){
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id查询自定义用户信息
	 * @auth sunpeikai
	 * @param userId 用户名
	 * @return
	 */
	@Override
	public UserInfoCustomizeVO getUserInfoCustomizeByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/userInfo/queryUserInfoCustomizeByUserId/" + userId;
		UserInfoCustomizeResponse response = restTemplate.getForEntity(url,UserInfoCustomizeResponse.class).getBody();
		if(response != null){
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id查询推荐人表信息
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @return
	 */
	@Override
	public SpreadsUserVO searchSpreadsUserByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/userInfo/querySpreadsUsersByUserId/" + userId;
		SpreadsUserResponse response = restTemplate.getForEntity(url,SpreadsUserResponse.class).getBody();
		if(response != null){
			return response.getResult();
		}
		return null;
	}

    /**
     * 根据用户id查询employee
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public EmployeeCustomizeVO searchEmployeeBuUserId(Integer userId) {
        String url = "http://AM-USER/am-user/userInfo/selectEmployeeByUserId/" + userId;
        EmployeeCustomizeResponse response = restTemplate.getForEntity(url,EmployeeCustomizeResponse.class).getBody();
        if(response != null){
            return response.getResult();
        }
        return null;
    }

	/**
	 * 查询自动投资债转异常列表
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public AdminUserAuthListResponse userAuthList(AdminUserAuthListRequest adminUserAuthListRequest) {
		AdminUserAuthListResponse response = restTemplate
				.postForEntity("http://AM-USER/am-user/userauth/userauthlist",adminUserAuthListRequest, AdminUserAuthListResponse.class)
				.getBody();

		return response;
	}

	/**
	 * 同步用户授权状态
	 * @auth sunpeikai
	 * @param type 1自动投资授权  2债转授权
	 * @return
	 */
	@Override
	public JSONObject synUserAuth(Integer userId, Integer type) {
		JSONObject jsonObject = restTemplate
				.getForEntity("http://AM-USER/am-user/userauth/synuserauth/" + userId + "/" + type, JSONObject.class)
				.getBody();

		return jsonObject;
	}
}
