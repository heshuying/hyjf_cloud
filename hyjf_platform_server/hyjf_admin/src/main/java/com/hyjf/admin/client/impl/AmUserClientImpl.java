package com.hyjf.admin.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean;
import com.hyjf.admin.beans.request.SmsCodeRequestBean;
import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.resquest.trade.CorpOpenAccountRecordRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AmUserClientImpl, v0.1 2018/4/19 12:44
 */

@Service
public class AmUserClientImpl implements AmUserClient {
	private static Logger logger = LoggerFactory.getLogger(AmUserClient.class);

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 根据userName查询user信息
	 *
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public List<UserVO> searchUserByUsername(String userName) {
		String url = "http://AM-ADMIN/am-user/customertransfer/searchuserbyusername/" + userName;
		UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据userId查询accountChinapnr开户信息
	 *
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public List<AccountChinapnrVO> searchAccountChinapnrByUserId(Integer userId) {
		String url = "http://AM-ADMIN/am-user/customertransfer/searchaccountchinapnrbyuserid/" + userId;
		AccountChinapnrResponse response = restTemplate.getForEntity(url, AccountChinapnrResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据userId查询用户信息
	 *
	 * @auth sunpeikai
	 * @param userId
	 *            用户id
	 * @return
	 */
	@Override
	public UserVO searchUserByUserId(Integer userId) {
		String url = "http://AM-ADMIN/am-user/customertransfer/searchuserbyuserid/" + userId;
		UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据accountId获取用户
	 *
	 * @auther: hesy
	 * @date: 2018/7/14
	 */
	@Override
	public UserVO getUserByAccountId(String accountId) {
		String url = "http://AM-ADMIN/am-user/user/getby_accountid/" + accountId;
		UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserVO getUserByUserName(String loginUserName) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/user/findByCondition/" + loginUserName, UserResponse.class).getBody();
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
				.getForEntity("http://AM-ADMIN/am-user/user/findById/" + userId, UserResponse.class).getBody();
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
				.getForEntity("http://AM-ADMIN/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据userId列表查询user列表
	 *
	 * @auth sunpeikai
	 * @param userIds
	 *            用户id列表
	 * @return
	 */
	@Override
	public List<UserVO> findUserListByUserIds(String userIds) {
		UserResponse response = restTemplate.postForEntity(
				"http://AM-ADMIN/am-user/platformtransfer/finduserlistbyuserids", userIds, UserResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 利用borrowNid查询出来的异常标的借款人userId查询银行账户
	 *
	 * @auth sunpeikai
	 * @param userId
	 *            用户id
	 * @return
	 */
	@Override
	public BankOpenAccountVO searchBankOpenAccount(Integer userId) {
		String url = "http://AM-ADMIN/am-user/borrow_regist_repair/searchbankopenaccount/" + userId;
		BankOpenAccountResponse response = restTemplate.getForEntity(url, BankOpenAccountResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户名获取自定义属性的user信息
	 *
	 * @auth sunpeikai
	 * @param userName
	 *            用户名
	 * @return
	 */
	@Override
	public UserInfoCustomizeVO getUserInfoCustomizeByUserName(String userName) {
		String url = "http://AM-ADMIN/am-user/userInfo/queryUserInfoCustomizeByUserName/" + userName;
		UserInfoCustomizeResponse response = restTemplate.getForEntity(url, UserInfoCustomizeResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id查询自定义用户信息
	 *
	 * @auth sunpeikai
	 * @param userId
	 *            用户名
	 * @return
	 */
	@Override
	public UserInfoCustomizeVO getUserInfoCustomizeByUserId(Integer userId) {
		String url = "http://AM-ADMIN/am-user/userInfo/queryUserInfoCustomizeByUserId/" + userId;
		UserInfoCustomizeResponse response = restTemplate.getForEntity(url, UserInfoCustomizeResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id查询推荐人表信息
	 *
	 * @auth sunpeikai
	 * @param userId
	 *            用户id
	 * @return
	 */
	@Override
	public SpreadsUserVO searchSpreadsUserByUserId(Integer userId) {
		String url = "http://AM-ADMIN/am-user/userInfo/querySpreadsUsersByUserId/" + userId;
		SpreadsUserResponse response = restTemplate.getForEntity(url, SpreadsUserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id查询employee
	 *
	 * @auth sunpeikai
	 * @param userId
	 *            用户id
	 * @return
	 */
	@Override
	public EmployeeCustomizeVO searchEmployeeBuUserId(Integer userId) {
		String url = "http://AM-ADMIN/am-user/userInfo/selectEmployeeByUserId/" + userId;
		EmployeeCustomizeResponse response = restTemplate.getForEntity(url, EmployeeCustomizeResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查询自动出借债转异常列表
	 *
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public AdminUserAuthListResponse userAuthList(AdminUserAuthListRequest adminUserAuthListRequest) {
		AdminUserAuthListResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-user/userauth/userauthlist",
				adminUserAuthListRequest, AdminUserAuthListResponse.class).getBody();

		return response;
	}

	/**
	 * 同步用户授权状态
	 *
	 * @auth sunpeikai
	 * @param type
	 *            1自动出借授权 2债转授权
	 * @return
	 */
	@Override
	public JSONObject synUserAuth(Integer userId, Integer type) {
		JSONObject jsonObject = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userauth/synuserauth/" + userId + "/" + type, JSONObject.class)
				.getBody();

		return jsonObject;
	}

	/**
	 * 更具userName获取开户信息
	 *
	 * @auther: hesy
	 * @date: 2018/7/14
	 */
	@Override
	public BankOpenAccountVO queryBankOpenAccountByUserName(String userName) {
		BankOpenAccountResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/get_openaccount_byusername/" + userName,
						BankOpenAccountResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查找用户信息
	 *
	 * @param request
	 * @return
	 * @auth nxl
	 */
	@Override
	public UserManagerResponse selectUserMemberList(UserManagerRequest request) {
		UserManagerResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/userslist", request, UserManagerResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 根据筛选条件查找用户总数
	 *
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int countRecordTotal(UserManagerRequest request) {
		UserManagerResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/countUserList", request, UserManagerResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getCount();
		}
		return 0;
	}

	/**
	 * 根据用户id获取用户详情
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserManagerDetailVO selectUserDetailById(String userId) {
		UserManagerDetailResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectUserDetail/" + userId,
						UserManagerDetailResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取测评信息
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserEvalationResultVO getUserEvalationResult(String userId) {
		UserEvalationResultResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectUserEvalationResultByUserId/" + userId,
						UserEvalationResultResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取开户信息
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserBankOpenAccountVO selectBankOpenAccountByUserId(String userId) {
		UserBankOpenAccountResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectBankOpenAccountByUserId/" + userId,
						UserBankOpenAccountResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取企业用户开户信息
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(String userId) {
		CorpOpenAccountRecordResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectCorpOpenAccountRecordByUserId/" + userId,
						CorpOpenAccountRecordResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取第三方平台绑定信息
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public BindUserVo selectBindeUserByUserId(String userId) {
		BindUserResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectBindUserByUserId/" + userId,
						BindUserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取用户CA认证记录表
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId) {
		CertificateAuthorityResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectCertificateAuthorityByUserId/" + userId,
						CertificateAuthorityResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取用户修改信息
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserManagerUpdateVO selectUserUpdateInfoByUserId(String userId) {
		UserManagerUpdateResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectUserUpdateInfoByUserId/" + userId,
						UserManagerUpdateResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 更新用户信息
	 *
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int updataUserInfo(UserManagerUpdateRequest request) {
		IntegerResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/updataUserInfo", request, IntegerResponse.class).getBody();
		if (response == null || !Response.isSuccess(response)) {
			return 0;
		}
		return response.getResultInt().intValue();
	}

	/**
	 * 根据用户id获取推荐人信息
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserRecommendCustomizeVO selectUserRecommendByUserId(String userId) {
		UserRecommendCustomizeResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectUserRecommendUserId/" + userId,
						UserRecommendCustomizeResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 校验手机号
	 *
	 * @auther: nxl
	 * @param userId
	 * @param mobile
	 * @return
	 */
	@Override
	public int countUserByMobile(int userId, String mobile) {
		int checkFlg = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/checkMobileByUserId/" + userId + "/" + mobile,
						Integer.class)
				.getBody();
		return checkFlg;
	}

	/**
	 * 校验推荐人
	 *
	 * @auther: nxl
	 * @param userId
	 * @param recommendName
	 * @return
	 */
	@Override
	public int checkRecommend(String userId, String recommendName) {
		int checkFlg = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/checkRecommend/" + userId + "/" + recommendName,
						Integer.class)
				.getBody();
		return checkFlg;
	}

	/**
	 * 根据用户id查找用户信息
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserVO selectUserByUserId(int userId) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectUserByUserId/" + userId, UserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户List id查找用户信息
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public List<UserVO> selectUserByListUserId(List userId) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectUserByListUserId/" + userId, UserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据用户id查找用户信息
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public List<BankCardVO> selectBankCardByUserId(int userId) {
		BankCardResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/getTiedCardForBank/" + userId, BankCardResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 更新企业用户开户记录
	 *
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int updateCorpOpenAccountRecord(CorpOpenAccountRecordRequest request) {
		int updFlg = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/updateCorpOpenAccountRecord", request, Integer.class)
				.getBody();
		return updFlg;
	}

	/**
	 * 插入企业用户开户记录
	 *
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int insertCorpOpenAccountRecord(CorpOpenAccountRecordRequest request) {
		int instFlg = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/insertCorpOpenAccountRecord", request, Integer.class)
				.getBody();
		return instFlg;
	}

	/**
	 * 更新用户绑定的银行卡
	 *
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int updateUserCard(BankCardRequest request) {
		int result = restTemplate.postForEntity("http://AM-ADMIN/am-user/card/updateUserCard", request, Integer.class)
				.getBody();
		return result;
	}
	
	/**
	 * 单表查询开户信息
	 *
	 * @auther: nxl
	 * @return
	 */
	@Override
	public BankOpenAccountVO queryBankOpenAccountByUserId(int userId) {
		BankOpenAccountResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/queryBankOpenAccountByUserId/" + userId,
						BankOpenAccountResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 更新开户信息
	 *
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int updateBankOpenAccount(BankOpenAccountRequest request) {
		int result = restTemplate
				.postForEntity("http://AM-ADMIN//am-user/userManager/updateBankOpenAccount", request, Integer.class)
				.getBody();
		return result;
	}

	/**
	 * 插入开户信息
	 *
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int insertBankOpenAccount(BankOpenAccountRequest request) {
		int result = restTemplate
				.postForEntity("http://AM-ADMIN//am-user/userManager/insertBankOpenAccount", request, Integer.class)
				.getBody();
		return result;
	}

	/**
	 * 根据用户id获取用户信息
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoVO findUserInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 更新用户信息表
	 *
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int updateUserInfoByUserInfo(UserInfoVO userInfoVO) {
		UserInfoRequest request = new UserInfoRequest();
		BeanUtils.copyProperties(userInfoVO, request);
		IntegerResponse result = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/updateUserInfoByUserInfo", request, IntegerResponse.class)
				.getBody();
		if (result == null || !Response.isSuccess(result)) {
			return 0;
		}
		return result.getResultInt().intValue();
	}

	/**
	 * 更新用户信息表--开户掉单
	 *
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int updateUserInfoByUserInfoSelective(UserInfoVO userInfoVO) {
		UserInfoRequest request = new UserInfoRequest();
		BeanUtils.copyProperties(userInfoVO, request);
		IntegerResponse result = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/updateUserInfoByUserInfoSelective", request, IntegerResponse.class)
				.getBody();
		if (result == null || !Response.isSuccess(result)) {
			return 0;
		}
		return result.getResultInt().intValue();
	}

	/**
	 * 更新用户表
	 *
	 * @return
	 * @auther: nxl
	 */
	@Override
	public int updateUser(UserVO userVO) {
		UserRequest request = null;
		BeanUtils.copyProperties(userVO, request);
		IntegerResponse result = restTemplate.postForEntity("http://AM-ADMIN/am-user/userManager/updateUser", request, IntegerResponse.class)
				.getBody();
		if (result == null || !Response.isSuccess(result)) {
			return 0;
		}
		return result.getResultInt().intValue();
	}

	/**
	 * 更新用户表-开户掉单
	 *
	 * @return
	 * @auther: nxl
	 */
	@Override
	public int updateUserSelective(UserVO userVO) {
		UserRequest request = new UserRequest();
		BeanUtils.copyProperties(userVO, request);
		IntegerResponse result = restTemplate.postForEntity("http://AM-ADMIN/am-user/userManager/updateUserSelective", request, IntegerResponse.class)
				.getBody();
		if (result == null || !Response.isSuccess(result)) {
			return 0;
		}
		return result.getResultInt().intValue();
	}

	/**
	 * 获取某一用户的信息修改列表
	 *
	 * @param request
	 * @return
	 */
	@Override
	public List<UserChangeLogVO> selectUserChageLog(UserChangeLogRequest request) {
		UserChangeLogResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/selectUserChageLog", request,
						UserChangeLogResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据推荐人姓名查找用户
	 *
	 * @auther: nxl
	 * @param recommendName
	 * @return
	 */
	@Override
	public UserVO selectUserByRecommendName(String recommendName) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectUserByRecommendName/" + recommendName,
						UserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据userId查找推荐人信息
	 *
	 * @param userId
	 * @auther: nxl
	 * @return
	 */
	@Override
	public SpreadsUserVO selectSpreadsUsersByUserId(String userId) {
		SpreadsUserResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectSpreadsUsersByUserId/" + userId,
						SpreadsUserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 修改推荐人信息
	 *
	 * @param request
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int updateUserRecommend(AdminUserRecommendRequest request) {
		IntegerResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/updateUserRecommend", request, IntegerResponse.class)
				.getBody();
		if (response == null || !Response.isSuccess(response)) {
			return 0;
		}
		return response.getResultInt().intValue();
	}

	/**
	 * 修改用户身份证
	 *
	 * @param request
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int updateUserIdCard(AdminUserRecommendRequest request) {
		IntegerResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/updateUserIdCard", request, IntegerResponse.class).getBody();
		if (response == null || !Response.isSuccess(response)) {
			return 0;
		}
		return response.getResultInt().intValue();
	}

	/**
	 * 修改用户身份证
	 *
	 * @param updCompanyRequest
	 * @auther: nxl
	 * @return
	 */
	@Override
	public Response saveCompanyInfo(UpdCompanyRequest updCompanyRequest) {
		Response response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/saveCompanyInfo", updCompanyRequest, Response.class)
				.getBody();
		return response;
	}

	/**
	 * 根据参数查询用户画像信息
	 *
	 * @param request
	 * @return
	 */
	@Override
	public UserPortraitResponse selectRecordList(UserPortraitRequest request) {
		UserPortraitResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userPortraitManage/findUserPortraitRecord", request,
						UserPortraitResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}
	/**
	 * 导出根据参数查询用户画像信息
	 *
	 * @param request
	 * @return
	 */
	@Override
	public UserPortraitResponse exportRecordList(UserPortraitRequest request) {
		UserPortraitResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userPortraitManage/exportRecordList", request,
						UserPortraitResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}
	/**
	 * 根据用户id查找用户画像
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserPortraitVO selectUsersPortraitByUserId(Integer userId) {
		UserPortraitResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userPortraitManage/selectUserPortraitByUserId/" + userId,
						UserPortraitResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 修改用户画像
	 */
	@Override
	public int updateUserPortrait(UserPortraitRequest request) {
		IntegerResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-user/userPortraitManage/updateUserPortraitRecord",
				request, IntegerResponse.class).getBody();
		if (response == null || !Response.isSuccess(response)) {
			return 0;
		}
		return response.getResultInt().intValue();
	}

	/**
	 * 根据UserID查询开户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public BankOpenAccountVO getBankOpenAccountByUserId(Integer userId) {
		String url = "http://AM-ADMIN/am-user/bankopen/selectById/" + userId;
		BankOpenAccountResponse response = restTemplate.getForEntity(url, BankOpenAccountResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/* 加入明细start */
	@Override
	public UserVO getUserByUserId(int userId) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectUserByUserId/" + userId, UserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserInfoVO selectUsersInfoByUserId(int userid) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userInfo/findById/" + userid, UserInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/* 加入明细end */
	/**
	 * 查找注册记录列表
	 *
	 * @author nxl
	 * @param request
	 * @return
	 */
	@Override
	public RegistRecordResponse findRegistRecordList(RegistRcordRequest request) {
		RegistRecordResponse response = restTemplate.postForEntity(
				"http://AM-ADMIN/am-user/registRecord/registRecordList", request, RegistRecordResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 查找借款盖章用户信息
	 *
	 * @author nxl
	 * @param request
	 * @return
	 */
	@Override
	public LoanCoverUserResponse selectUserMemberList(LoanCoverUserRequest request) {
		LoanCoverUserResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/loanCoverUser/loanCoverUserRecord", request,
						LoanCoverUserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 保存借款盖章用户信息
	 *
	 * @param request
	 * @author nxl
	 */
	@Override
	public int insertLoanCoverUser(LoanCoverUserRequest request) {
		IntegerResponse updFlg = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/loanCoverUser/insertLoanCoverUserRecord", request, IntegerResponse.class)
				.getBody();
		if (updFlg == null || !Response.isSuccess(updFlg)) {
			return 0;
		}
		return updFlg.getResultInt().intValue();
	}

	/**
	 * 根据证件号码查找借款主体CA认证记录表
	 *
	 * @param strIdNo
	 * @author nxl
	 * @return
	 */
	@Override
	public LoanCoverUserVO selectIsExistsRecordByIdNo(String strIdNo,String userName) {
		LoanCoverUserResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/loanCoverUser/selectIsExistsRecordByIdNo/" + strIdNo+"/"+userName,
						LoanCoverUserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();

		}
		return null;
	}

	/**
	 * 根据id查找借款主体CA认证记录表
	 *
	 * @param strId
	 * @author nxl
	 * @return
	 */
	@Override
	public LoanCoverUserResponse selectIsExistsRecordById(String strId) {
		LoanCoverUserResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/loanCoverUser/selectIsExistsRecordById/" + strId,
						LoanCoverUserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 更新借款盖章用户记录
	 *
	 * @param request
	 * @author nxl
	 * @return
	 */
	@Override
	public int updateLoanCoverUserRecord(LoanCoverUserRequest request) {
		IntegerResponse updFlg = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/loanCoverUser/updateLoanCoverUserRecord", request, IntegerResponse.class)
				.getBody();
		if (updFlg == null || !Response.isSuccess(updFlg)) {
			return 0;
		}
		return updFlg.getResultInt().intValue();
	}

	/**
	 * 根据筛选条件查找(用户测评列表显示)
	 *
	 * @author nxl
	 * @param request
	 * @return
	 */
	@Override
	public EvalationResultResponse selectUserEvalationResultList(EvalationRequest request) {
		EvalationResultResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/evaluationManager/selectUserEvalationResultList", request,
						EvalationResultResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 查找用户测评结果
	 *
	 * @author nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserEvalationResultVO selectEvaluationDetailById(String userId) {
		UserEvalationResultResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/evaluationManager/selectEvaluationDetailById/" + userId,
						UserEvalationResultResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查找汇付银行开户记录列表
	 *
	 * @author nixiaoling
	 * @param request
	 * @return
	 */
	@Override
	public BankAccountRecordResponse findAccountRecordList(AccountRecordRequest request) {
		BankAccountRecordResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/bankOpenAccountRecord/findAccountRecordList", request,
						BankAccountRecordResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 查找江西银行开户记录列表
	 *
	 * @author nixiaoling
	 * @param request
	 * @return
	 */
	@Override
	public BankAccountRecordResponse findBankAccountRecordList(BankAccountRecordRequest request) {
		BankAccountRecordResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/bankOpenAccountRecord/findBankAccountRecordList", request,
						BankAccountRecordResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 根据筛选条件查找汇付银行卡信息列表
	 *
	 * @param request
	 *            筛选条件
	 * @return
	 */
	@Override
	public BankCardManagerResponse selectBankCardList(BankCardManagerRequest request) {
		BankCardManagerResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/bankCardManager/bankcardlistHF", request,
						BankCardManagerResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 根据筛选条件查找江西银行卡信息列表
	 *
	 * @author nixiaoling
	 * @return
	 */
	@Override
	public BankCardManagerResponse selectNewBankCardList(BankCardManagerRequest request) {
		BankCardManagerResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/bankCardManager/bankcardlistJX", request,
						BankCardManagerResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 查找用户银行卡操作记录表
	 *
	 * @param request
	 * @author nixiaoling
	 * @return
	 */
	@Override
	public BankCardLogResponse selectBankCardLogByExample(BankCardLogRequest request) {
		BankCardLogResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/bankCardManager/selectBankCardLogByExample", request,
						BankCardLogResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 根据证件号码和姓名查找用户CA认证记录表
	 *
	 * @param tureName
	 * @return
	 */
	@Override
	public CertificateAuthorityResponse selectCertificateAuthorityByIdNoName(String tureName) {
		CertificateAuthorityResponse response = restTemplate.getForEntity(
				"http://AM-ADMIN/am-user/loanCoverUser/selectCertificateAuthorityByIdNoName/" + tureName,
				CertificateAuthorityResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 根据证件号码和姓名查找用户CA认证记录表
	 *
	 * @param
	 * @param
	 * @return
	 */
	@Override
	public CertificateAuthorityResponse isCAIdNoCheck(String param, String name) {
		CertificateAuthorityResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/loanCoverUser/isCAIdNoCheck/" + param + "/" + name,
						CertificateAuthorityResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}
	@Override
	public AdminUserAuthListResponse userauthlist(AdminUserAuthListRequest adminUserAuthListRequest) {
		AdminUserAuthListResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userauth/userauthlist",adminUserAuthListRequest, AdminUserAuthListResponse.class)
				.getBody();

		return response;
	}

	@Override
	public AdminUserAuthListResponse cancelInvestAuth(int userId) {
		AdminUserAuthListResponse response = restTemplate.
				getForEntity("http://AM-ADMIN/am-user/userauth/userinvescancel/" + userId , AdminUserAuthListResponse.class).
				getBody();
		return response;
	}

	@Override
	public AdminUserAuthListResponse cancelCreditAuth(int userId) {
		AdminUserAuthListResponse response = restTemplate.
				getForEntity("http://AM-ADMIN/am-user/userauth/usercreditcancel/" + userId, AdminUserAuthListResponse.class).
				getBody();
		return response;
	}


	@Override
	public AdminUserAuthLogListResponse userauthLoglist(AdminUserAuthLogListRequest adminUserAuthListRequest) {
		AdminUserAuthLogListResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userauth/userauthloglist",adminUserAuthListRequest, AdminUserAuthLogListResponse.class)
				.getBody();

		return response;
	}
	@Override
	public CertificateAuthorityResponse getRecordList(CertificateAuthorityExceptionRequest aprlr) {
		String url = "http://AM-ADMIN/am-user/certificate/search";
		CertificateAuthorityResponse response = restTemplate
				.postForEntity(url, aprlr, CertificateAuthorityResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	@Override
	public CertificateAuthorityResponse updateUserCAMQ(String userId) {
		String url = "http://AM-ADMIN/am-user/certificate/modifyAction/"+userId;
		CertificateAuthorityResponse response = restTemplate
				.getForEntity(url, CertificateAuthorityResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	@Override
	public AdminPreRegistListResponse getRecordList(AdminPreRegistListRequest adminPreRegistListRequest) {
		AdminPreRegistListResponse adminPreRegistListResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/preregist/preregistlist" ,adminPreRegistListRequest,
						AdminPreRegistListResponse.class)
				.getBody();
		if (adminPreRegistListResponse != null) {
			return adminPreRegistListResponse;
		}
		return null;
	}

	@Override
	public AdminPreRegistListResponse getPreRegist(AdminPreRegistListRequest adminPreRegistListRequest) {
		AdminPreRegistListResponse adminPreRegistListResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/preregist/updatepreregistlist" ,adminPreRegistListRequest,
						AdminPreRegistListResponse.class)
				.getBody();
		if (adminPreRegistListResponse != null) {
			return adminPreRegistListResponse;
		}
		return null;
	}

	@Override
	public AdminPreRegistListResponse savePreRegist(AdminPreRegistListRequest adminPreRegistListRequest) {
		AdminPreRegistListResponse adminPreRegistListResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/preregist/savepreregistlist" ,adminPreRegistListRequest,
						AdminPreRegistListResponse.class)
				.getBody();
		if (adminPreRegistListResponse != null) {
			return adminPreRegistListResponse;
		}
		return null;
	}

	@Override
	public WhereaboutsPageResponse searchAction(WhereaboutsPageRequestBean requestBean) {
		WhereaboutsPageResponse amUserResponse = restTemplate.postForObject("http://AM-ADMIN/am-admin/content/whereaboutspage/searchaction",
				requestBean, WhereaboutsPageResponse.class);
		return  amUserResponse;

	}
	@Override
	public WhereaboutsPageResponse getWhereaboutsPageConfigById(WhereaboutsPageRequestBean form){
		WhereaboutsPageResponse amUserResponse = restTemplate.postForObject("http://AM-ADMIN/am-admin/content/whereaboutspage/getwhereaboutspageconfig",
				form, WhereaboutsPageResponse.class);
		return  amUserResponse;
	}



	@Override
	public WhereaboutsPageResponse insertAction(WhereaboutsPageRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-ADMIN/am-admin/content/whereaboutspage/insert",
				requestBean, WhereaboutsPageResponse.class);
	}

	@Override
	public WhereaboutsPageResponse updateAction(WhereaboutsPageRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-ADMIN/am-admin/content/whereaboutspage/update",
				requestBean, WhereaboutsPageResponse.class);
	}
	@Override
	public WhereaboutsPageResponse updateStatus(WhereaboutsPageRequestBean requestBean){
		return restTemplate.postForObject("http://AM-ADMIN/am-admin/content/whereaboutspage/updatestatus",
				requestBean, WhereaboutsPageResponse.class);
	}

	@Override
	public WhereaboutsPageResponse deleteById(Integer id) {
		return restTemplate.getForObject("http://AM-ADMIN/am-admin/content/whereaboutspage/delete/" + id,
				WhereaboutsPageResponse.class);
	}

	@Override
	public BankOpenAccountVO getBankOpenAccount(Integer userId) {
		String url = "http://AM-ADMIN/am-user/bank_account_manage/get_bank_open_account/" + userId;
		BankOpenAccountResponse response = restTemplate.getForEntity(url,BankOpenAccountResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
	@Override
	public StringResponse checkUtmId(Integer utmId){
		String url = "http://AM-ADMIN/am-admin/content/whereaboutspage/checkutmid/" + utmId;
		StringResponse response = restTemplate.getForEntity(url,StringResponse.class).getBody();
		return response;
	}

	@Override
	public StringResponse checkReferrer(String referrer){
		String url = "http://AM-ADMIN/am-admin/content/whereaboutspage/checkreferrer/" + referrer;
		StringResponse response = restTemplate.getForEntity(url,StringResponse.class).getBody();
		return response;
	}
	@Override
	public ChangeLogResponse getChangeLogList(ChangeLogRequest clr) {
		ChangeLogResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/changelog/init",clr, ChangeLogResponse.class)
				.getBody();
		return response;
	}

	@Override
	public ChannelStatisticsDetailResponse searchChannelStatisticsDetail(ChannelStatisticsDetailRequest request){
		ChannelStatisticsDetailResponse amUserResponse = restTemplate.postForObject("http://AM-ADMIN/am-user/extensioncenter/channelstatisticsdetail/searchaction",
				request, ChannelStatisticsDetailResponse.class);
		return amUserResponse;
	}

	/**
	 * 根据id查找用户测评的问题与答案
	 * @param evalationId
	 * @author nxl
	 * @return
	 */
	@Override
	public List<UserEvalationQuestionVO> getUserQuestionInfoById(int evalationId){
		String url = "http://AM-ADMIN/am-user/evaluationManager/getUserQuestionInfoById/" + evalationId;
		UserEvalationQuestionResponse response = restTemplate.getForEntity(url, UserEvalationQuestionResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 查询用户画像评分列表
	 * @param request
	 * @return
	 */
	@Override
	public UserPortraitScoreResponse selectScoreRecordList(UserPortraitScoreRequest request) {
		String url = "http://AM-ADMIN/am-user/userPortraitManage/selectUserPortraitScoreRecordList";
		UserPortraitScoreResponse response = restTemplate.postForEntity(url,request,UserPortraitScoreResponse.class).getBody();
		if (response != null) {
			response.getResultList();
			return response;
		}
		return null;
	}

	@Override
	public KeyCountResponse searchAction(KeyCountRequest request) {
		KeyCountResponse response = restTemplate.postForObject("http://AM-ADMIN/am-admin/extensioncenter/keycount/searchaction",
				request, KeyCountResponse.class);
		return  response;

	}

	/**
	 * 根据用户名查询id
	 * @param userName
	 * @return
	 */
	@Override
	public UserResponse findUserByCondition(String userName) {
		return restTemplate.getForObject("http://AM-ADMIN/am-user/user/findByCondition/"+userName, UserResponse.class);
	}

	/**
	 * 根据用户id查询用户
	 * @param userId
	 * @return
	 */
	@Override
	public UserResponse findUserByUserId(Integer userId) {
		return restTemplate.getForObject("http://AM-ADMIN/am-user/user/findById/"+userId, UserResponse.class);
	}

	@Override
	public UtmResponse getByPageList(Map<String, Object> map) {

		ResponseEntity<UtmResponse<UtmVO>> response = restTemplate.exchange(
				"http://AM-ADMIN/am-user/promotion/utm/getbypagelist", HttpMethod.POST, new HttpEntity<>(map),
				new ParameterizedTypeReference<UtmResponse<UtmVO>>() {
				});
		if (null != response) {
			return response.getBody();
		}
		return null;
	}

	@Override
	public UtmResponse getCountByParam(Map<String, Object> map) {
		UtmResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/promotion/utm/getcount", map, UtmResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	@Override
	public Integer getChannelCount(ChannelCustomizeVO channelCustomizeVO) {
		UtmResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/channel/getchannelcount", channelCustomizeVO, UtmResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getRecordTotal();
		}
		return null;
	}

	@Override
	public List<ChannelCustomizeVO> getChannelList(ChannelCustomizeVO channelCustomizeVO) {
		UtmResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/channel/getchannellist", channelCustomizeVO, UtmResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<UtmPlatVO> getUtmPlat() {
		UtmResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/promotion/utm/getmyutmplat", UtmResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public UtmChannelVO getRecord(String utmId) {
		UtmChannelResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/promotion/utm/getutmbyutmid/"+utmId, UtmChannelResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserVO getUser(String utmReferrer, String userId) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/user/getuser/"+utmReferrer+"/"+userId, UserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public boolean insertOrUpdateUtm(ChannelCustomizeVO channelCustomizeVO) {
		UtmResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/promotion/utm/insertorupdateutm",channelCustomizeVO, UtmResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return true;
		}else{
			return  false;
		}
	}

	@Override
	public boolean deleteAction(ChannelCustomizeVO channelCustomizeVO) {
		UtmResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/promotion/utm/deleteutm/",channelCustomizeVO, UtmResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return true;
		}else{
			return  false;
		}
	}

	@Override
	public UtmPlatVO getDataById(Integer id) {
		UtmPlatResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/promotion/utm/getutmbyid/"+id, UtmPlatResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}else{
			return null;
		}
	}

	@Override
	public int sourceNameIsExists(String sourceName, Integer sourceId) {
		UtmResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/promotion/utm/sourcenameisexists/"+sourceName+"/"+sourceId, UtmResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getRecordTotal();
		}else{
			return 0;
		}
	}

	@Override
	public boolean insertOrUpdateUtmPlat(UtmPlatVO utmPlatVO) {
		UtmResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/promotion/utm/insertorupdateutmplat/",utmPlatVO, UtmResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return true;
		}else{
			return  false;
		}
	}

	@Override
	public boolean utmClientdeleteUtmPlatAction(UtmPlatVO utmPlatVO) {
		UtmResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/promotion/utm/deleteutmplat/",utmPlatVO, UtmResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return true;
		}else{
			return  false;
		}
	}

	/**
	 * 查找VIP信息
	 * @param vipManageRequest
	 * @return
	 */
	@Override
	public VipManageResponse searchList(VipManageRequest vipManageRequest) {
		String url = "http://AM-ADMIN/am-user/vipManage/getUserList";
		VipManageResponse response = restTemplate.postForEntity(url,vipManageRequest,VipManageResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	@Override
	public VipDetailListResponse searchDetailList(VipDetailListRequest detailListRequest) {
		String url = "http://AM-ADMIN/am-user/vipManage/vipDetailList";
		VipDetailListResponse response = restTemplate.postForEntity(url,detailListRequest,VipDetailListResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	@Override
	public VipUpdateGradeListResponse searchUpdateGradeList(VipUpdateGradeListRequest vgl) {
		String url = "http://AM-ADMIN/am-user/vipManage/vipUpdateGradeList";
		VipUpdateGradeListResponse response = restTemplate.postForEntity(url,vgl,VipUpdateGradeListResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}
	/**
	 *  分账名单配置添加  查询用户名信息
	 * @param request
	 * @return
	 */
	@Override
	public UserInfoCustomizeResponse queryUserInfoByUserName(AdminSubConfigRequest request){
		return restTemplate.postForEntity("http://AM-ADMIN/am-admin/config/subconfig/queryUserInfoByUserName",request, UserInfoCustomizeResponse.class).getBody();
	}
	@Override
	public MspApplytResponse getRecordList(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapply/init" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse infoAction() {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapply/infoAction" ,null,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse insertAction(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapply/insertAction" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse updateAction(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapply/insertAction" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse deleteRecordAction(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapply/deleteRecordAction" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse validateBeforeAction(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapply/validateBeforeAction" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse applyInfo(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapply/applyAction" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse shareUser(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapply/shareUser" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspApplytResponse download(MspApplytRequest mspApplytRequest) {
		MspApplytResponse mspApplytResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapply/downloadFile" ,mspApplytRequest,
						MspApplytResponse.class)
				.getBody();
		if (mspApplytResponse != null) {
			return mspApplytResponse;
		}
		return null;
	}

	@Override
	public MspResponse searchAction(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapplyconfigure/searchAction" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse infoAction(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapplyconfigure/infoAction" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse insertAction(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapplyconfigure/insertAction" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse updateAction(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapplyconfigure/updateAction" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse configureNameError(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapplyconfigure/configureNameError" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse deleteAction(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapplyconfigure/deleteAction" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	@Override
	public MspResponse checkAction(MspRequest mspRequest) {
		MspResponse mspResponse = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/mspapplyconfigure/checkAction" ,mspRequest,
						MspResponse.class)
				.getBody();
		if (mspResponse != null) {
			return mspResponse;
		}
		return null;
	}

	/**
	 * 获取注册时的用户渠道
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UtmResponse getChannelNameByUserId(Integer userId) {
		String url = "http://AM-ADMIN/am-user/channel/getchannelnamebyuserd/" + userId;
		UtmResponse utmResponse = restTemplate.getForEntity(url, UtmResponse.class).getBody();
		if (utmResponse != null) {
			return utmResponse;
		}
		return null;
	}

	/**
	 * 查询手机号同步数量  用于前端分页显示
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public int countBankOpenAccountUser(MobileSynchronizeRequest request) {
		String url = "http://AM-ADMIN/am-user/mobilesynchronize/countBankOpenAccountUser";
		MobileSynchronizeResponse response = restTemplate.postForEntity(url,request, MobileSynchronizeResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getCount();
		}
		return 0;
	}

	/**
	 * 查询手机号同步列表
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public List<MobileSynchronizeCustomizeVO> selectBankOpenAccountUserList(MobileSynchronizeRequest request) {
		String url = "http://AM-ADMIN/am-user/mobilesynchronize/selectBankOpenAccountUserList";
		MobileSynchronizeResponse response = restTemplate.postForEntity(url,request, MobileSynchronizeResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 同步手机号
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public boolean updateMobile(MobileSynchronizeRequest request) {
		String url = "http://AM-ADMIN/am-user/mobilesynchronize/updateMobile";
		MobileSynchronizeResponse response = restTemplate.postForEntity(url,request, MobileSynchronizeResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.isUpdate();
		}
		return false;
	}

	/**
	 * 银行卡异常count
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public int getBankCardExceptionCount(BankCardExceptionRequest request) {
		String url = "http://AM-ADMIN/am-user/bankcardrepair/getBankCardRepairCount";
		AdminBankCardExceptionResponse response = restTemplate.postForEntity(url,request, AdminBankCardExceptionResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getCount();
		}
		return 0;
	}

	/**
	 * 银行卡异常列表
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public List<AdminBankCardExceptionCustomizeVO> searchBankCardExceptionList(BankCardExceptionRequest request) {
		String url = "http://AM-ADMIN/am-user/bankcardrepair/searchBankCardRepairList";
		AdminBankCardExceptionResponse response = restTemplate.postForEntity(url,request, AdminBankCardExceptionResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 更新银行卡(admin后台异常中心-银行卡异常用)
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public String updateAccountBankByUserId(BankCardExceptionRequest request) {
		String url = "http://AM-ADMIN/am-user/bankcardrepair/updateAccountBankByUserId";
		AdminBankCardExceptionResponse response = restTemplate.postForEntity(url,request,AdminBankCardExceptionResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultMsg();
		}
		return "";
	}

	@Override
	public UserVO getUserByMobile(String mobile) {
		UserResponse response = restTemplate.getForObject("http://AM-ADMIN/am-user/user/findByMobile/" + mobile, UserResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 获取CA认证异常列表
	 * @param request
	 * @return
	 */
	@Override
	public CertificateAuthorityResponse getExceptionRecordList(CertificateAuthorityExceptionRequest request) {
		String url = "http://AM-ADMIN/am-user/certificate/getExceptionRecordList";
		CertificateAuthorityResponse response = restTemplate
				.postForEntity(url, request, CertificateAuthorityResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	@Override
	public SmsCountCustomizeResponse querySmsCountList(SmsCountRequest request) {
		return restTemplate.postForObject("http://AM-ADMIN/am-user/sms_count/query_sms_count_list", request, SmsCountCustomizeResponse.class);
	}

	@Override
	public Integer querySmsCountNumberTotal(SmsCountCustomizeVO request) {
		return restTemplate.postForObject("http://AM-ADMIN/am-user/sms_count/query_sms_count_number_total", request, Integer.class);
	}

	@Override
	public List<OADepartmentCustomizeVO> queryDepartmentInfo(Object o) {
		SmsCountCustomizeResponse response = restTemplate.getForObject(
				"http://AM-ADMIN/am-user/sms_count/query_department_info", SmsCountCustomizeResponse.class,
				Integer.class);
		if (response != null) {
			return response.getList();
		}
		return null;
	}

	@Override
	public List<String> queryUser(SmsCodeRequestBean requestBean) {
		Response tradeResponse = restTemplate.postForObject("http://AM-ADMIN/am-trade/smsCode/queryUser",
				requestBean, Response.class);
//		Response userResponse = restTemplate.postForObject("http://AM-ADMIN/am-user/sms_count/queryUser", requestBean, Response.class);
		if (tradeResponse != null ) {
			List<String> tradeList = tradeResponse.getResultList();
//			List<String> userList = userResponse.getResultList();
//			if (!CollectionUtils.isEmpty(tradeList) ) {
//				tradeList.retainAll(userList);
//			}
			return tradeList;
		}
		return null;
	}

	/**
	 * 线下修改信息同步查询列表count
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public int getModifyInfoCount(AccountMobileSynchRequest request) {
		String url = "http://AM-ADMIN/am-user/accountmobilesynch/getModifyInfoCount";
		AccountMobileSynchResponse response = restTemplate.postForEntity(url,request,AccountMobileSynchResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getCount();
		}
		return 0;
	}

	/**
	 * 线下修改信息同步查询列表list
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public List<AccountMobileSynchVO> searchModifyInfoList(AccountMobileSynchRequest request) {
		String url = "http://AM-ADMIN/am-user/accountmobilesynch/searchModifyInfoList";
		AccountMobileSynchResponse response = restTemplate.postForEntity(url,request,AccountMobileSynchResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 添加信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public Integer insertAccountMobileSynch(AccountMobileSynchRequest request) {
		String url = "http://AM-ADMIN/am-user/accountmobilesynch/insertAccountMobileSynch";
		AccountMobileSynchResponse response = restTemplate.postForEntity(url,request,AccountMobileSynchResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getCount();
		}
		return 5;
	}

	/**
	 * 根据主键id删除一条信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public Integer deleteAccountMobileSynch(AccountMobileSynchRequest request) {
		String url = "http://AM-ADMIN/am-user/accountmobilesynch/deleteAccountMobileSynch";
		AccountMobileSynchResponse response = restTemplate.postForEntity(url,request,AccountMobileSynchResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getCount();
		}
		return 0;
	}

	/**
	 * 获取用户账户信息byaccountId
	 * @auth libin
	 * @param accountId
	 * @return
	 */
	@Override
	public BankOpenAccountVO getBankOpenAccountByAccountId(String accountId) {
		String url = "http://AM-ADMIN/am-user/bankopen/getBankOpenAccountByAccountId/" + accountId;
		BankOpenAccountResponse response = restTemplate.getForEntity(url, BankOpenAccountResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据关联关系查询OA表的内容,得到部门的线上线下属性
	 * @param userId
	 * @auth nxl
	 * @return
	 */
	@Override
	public UserUpdateParamCustomizeResponse queryUserAndDepartment(Integer userId){
		String url = "http://AM-ADMIN/am-user/userManager/queryUserAndDepartment/" + userId;
		UserUpdateParamCustomizeResponse response = restTemplate.getForEntity(url, UserUpdateParamCustomizeResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	/**
	 * 获取所有用户信息
	 * @auth nxl
	 * @return
	 */
	@Override
	public UserResponse selectAllUser(){
		String url = "http://AM-ADMIN/am-user/userManager/selectAllUser";
		UserResponse response = restTemplate.postForEntity(url,null, UserResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	/**
	 * 查询此段时间的用户推荐人的修改记录
	 * @param userId
	 * @param repairStartDate
	 * @param repairEndDate
	 * @auth nxl
	 * @return
	 */
	@Override
	public SpreadsUserLogResponse searchSpreadUsersLogByDate(Integer userId, String repairStartDate, String repairEndDate){
		String url = "http://AM-ADMIN/am-user/userManager/searchSpreadUsersLogByDate/"+userId+"/"+repairStartDate+"/"+repairEndDate;
		SpreadsUserLogResponse response = restTemplate.postForEntity(url,null, SpreadsUserLogResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}
	/**
	 * 查找员工信息
	 * @param userId
	 * @auth nxl
	 * @return
	 */
	@Override
	public EmployeeCustomizeResponse selectEmployeeInfoByUserId(Integer userId){
		String url = "http://AM-ADMIN/am-user/userManager/selectEmployeeInfoByUserId/"+userId;
		EmployeeCustomizeResponse response = restTemplate.postForEntity(url,null, EmployeeCustomizeResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}
	/**
	 * 根据用户id获取离职信息
	 * @param userId
	 * @auth nxl
	 * @return
	 */
	@Override
	public AdminEmployeeLeaveCustomizeResponse selectUserLeaveByUserId(Integer userId){
		String url = "http://AM-ADMIN/am-user/userManager/selectUserLeaveByUserId/"+userId;
		AdminEmployeeLeaveCustomizeResponse response = restTemplate.postForEntity(url,null, AdminEmployeeLeaveCustomizeResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}
	/**
	 * 通过手机号和身份证查询掉单信息
	 *
	 * @param request,idcard
	 * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
	 * @author Zha Daojian
	 * @date 2018/8/21 13:54
	 **/
	@Override
	public List<BankOpenAccountLogVO> bankOpenAccountLogSelect(BankOpenAccountLogRequest request) {
		String url = "http://AM-ADMIN/am-user/borrowOpenaccountenquiryException/bankOpenAccountLogSelect";
		BankOpenAccountLogResponse response = restTemplate.postForEntity(url,request,BankOpenAccountLogResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 获取掉单用户信息
	 *
	 * @param request
	 * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
	 * @author Zha Daojian
	 * @date 2018/8/21 13:54
	 **/
	@Override
	public OpenAccountEnquiryCustomizeVO searchAccountEnquiry(BankOpenAccountLogRequest request) {
		String url = "http://AM-ADMIN/am-user/borrowOpenaccountenquiryException/searchAccountEnquiry";
		OpenAccountEnquiryResponse response = restTemplate.postForEntity(url,request, OpenAccountEnquiryResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据订单号查询用户的开户记录
	 *
	 * @param orderId
	 * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
	 * @author Zha Daojian
	 * @date 2018/8/21 13:54
	 **/
	@Override
	public BankOpenAccountLogVO selectBankOpenAccountLogByOrderId(String orderId) {
		String url = "http://AM-ADMIN/am-user/borrowOpenaccountenquiryException/selectBankOpenAccountLogByOrderId/"+orderId;
		BankOpenAccountLogResponse response = restTemplate.getForEntity(url, BankOpenAccountLogResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 删除用户开户日志
	 *
	 * @param userId
	 * @return java.lang.Boolean
	 * @author Zha Daojian
	 * @date 2018/8/22 11:30
	 **/
	@Override
	public Boolean deleteBankOpenAccountLogByUserId(Integer userId) {
		String url = "http://AM-ADMIN/am-user/borrowOpenaccountenquiryException/deleteBankOpenAccountLogByUserId/"+userId;
		BankOpenAccountLogResponse response = restTemplate.getForEntity(url,BankOpenAccountLogResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.isBankOpenFlag();
		}
		return false;
	}

	/**
	 * 查询返回的电子账号是否已开户
	 *
	 * @param accountId
	 * @return java.lang.Boolean
	 * @author Zha Daojian
	 * @date 2018/8/22 13:38
	 **/
	@Override
	public Boolean checkAccountByAccountId(String accountId) {
		String url = "http://AM-ADMIN/am-user/borrowOpenaccountenquiryException/checkAccountByAccountId/"+accountId;
		BankOpenAccountLogResponse response = restTemplate.getForEntity(url,BankOpenAccountLogResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.isBankOpenFlag();
		}
		return false;
	}

	/**
	 * 根据userId查询用户渠道信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public AppUtmRegVO getAppChannelStatisticsDetailByUserId(Integer userId) {
		AppUtmRegResponse response = restTemplate.getForEntity(
				"http://AM-ADMIN/am-admin/app_utm_reg/findByUserId/" + userId,
				AppUtmRegResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 开户更新开户渠道统计开户时间
	 *
	 * @param appUtmRegVO
	 * @return java.lang.Boolean
	 * @author Zha Daojian
	 * @date 2018/8/22 13:38
	 **/
	@Override
	public Boolean updateByPrimaryKeySelective(AppUtmRegVO appUtmRegVO) {

		AppChannelStatisticsDetailRequest request = new AppChannelStatisticsDetailRequest();
		BeanUtils.copyProperties(appUtmRegVO, request);
		Boolean result = restTemplate.postForEntity("http://AM-ADMIN/am-user/userManager/updateUser", request, Boolean.class)
				.getBody();
		return result;
	}

	@Override
	public int isExistsUser(String userId) {
		int response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/user/isExistsUser/" + userId, Integer.class)
				.getBody();
		return response;
	}
	/**
	 * 根据推荐人id查找用信息
	 *
	 * @param userId
	 * @auther: nxl
	 * @return
	 */
	@Override
	public List<SpreadsUserVO> selectSpreadsUserBySpreadUserId(int userId) {
		SpreadsUserResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/selectSpreadsUserBySpreadUserId/" + userId,
						SpreadsUserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}
	/**
	 * 校验手机号
	 * @auther: nxl
	 * @param mobile
	 * @return
	 */
	@Override
	public int countByMobile(String mobile){
		int checkFlg = restTemplate.
				getForEntity("http://AM-ADMIN/am-user/userManager/countByMobileList/"+ mobile, Integer.class).
				getBody();
		return checkFlg;
	}

	/**
	 * 根据部门id查找是否有自级菜单
	 * @param deptId
	 * @return
	 * @auther: nxl
	 */
	@Override
	public List<OADepartmentCustomizeVO> getDeptInfoByDeptId(int deptId){
		OADepartmentResponse response = restTemplate.
				getForEntity("http://AM-ADMIN/am-user/userManager/getDeptInfoByDeptId/"+ deptId, OADepartmentResponse.class).
				getBody();
		if (Response.isSuccess(response)) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public UtmPlatResponse getAllUtmPlat(Map<String, Object> map) {
		UtmPlatResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/promotion/utm/getutmplat", map, UtmPlatResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	@Override
	public void insertUtmList(List<ChannelCustomizeVO> voList) {
		ChannelRequest request = new ChannelRequest();
		request.setList(voList);
		UtmResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-user/promotion/utm/insertutmlist",request, UtmResponse.class).getBody();
	}
	/**
	 * 根据证件号码和姓名查找用户CA认证记录表
	 *
	 * @param tureName
	 * @return
	 */
	@Override
	public CertificateAuthorityResponse selectCertificateAuthorityByCAName(String tureName) {
		CertificateAuthorityResponse response = restTemplate.getForEntity(
				"http://AM-ADMIN/am-user/loanCoverUser/selectCertificateAuthorityByCAName/" + tureName,
				CertificateAuthorityResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 渠道管理检查编号唯一性
	 * @Author cwyang 2018-10-22
	 * @param sourceId
	 * @return
	 */
	@Override
	public int sourceIdIsExists(Integer sourceId) {
		UtmResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/promotion/utm/sourceIdIsExists/"+sourceId, UtmResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getRecordTotal();
		}else{
			return 0;
		}
	}


	/**
	 * 根据用户id获取开户信息
	 *
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public BankCardVO getBankCardByUserId(int userId) {
		BankCardResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userManager/getBankCardByUserId/" + userId,
						BankCardResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
	/**
	 * 更新用户信息(基本信息,手机号,邮箱,用户角色)
	 *
	 * @param request
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int updateUserBaseInfo(UserInfosUpdCustomizeRequest request) {
		IntegerResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/updateUserBaseInfo", request, IntegerResponse.class).getBody();
		if (response == null || !Response.isSuccess(response)) {
			return 0;
		}
		return response.getResultInt().intValue();
	}
	/**
	 * 更新银行卡信息
	 *
	 * @param request
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int updateUserBankInfo(UserInfosUpdCustomizeRequest request) {
		IntegerResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/updateUserBankInfo", request, IntegerResponse.class).getBody();
		if (response == null || !Response.isSuccess(response)) {
			return 0;
		}
		return response.getResultInt().intValue();
	}

	@Override
	public void updateUserAuth(UserAuthRequest request) {
		restTemplate.postForEntity("http://AM-ADMIN/am-user//userauth/updateUserAuth", request,IntegerResponse.class) ;
	}

	@Override
	public HjhUserAuthLogVO selectByExample(String orderId) {
		HjhUserAuthLogResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/user/selectByExample/"+orderId, HjhUserAuthLogResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}


	/**
	 * 根据用户的查询条件查询用户缴费授权列表
	 * @param request
	 * @auther: nxl
	 * @return
	 */
	@Override
	public UserPayAuthResponse selectUserPayAuthList(UserPayAuthRequest request) {
		UserPayAuthResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userPayAuth/userPayAuthList", request, UserPayAuthResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}
	/**
	 * 根据用户id查询用户签约授权信息
	 * @param userId
	 * @auther: nxl
	 * @return
	 */
	@Override
	public HjhUserAuthResponse selectUserPayAuthByUserId(int userId) {
		HjhUserAuthResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userPayAuth/getHjhUserAuthByUserId/"+userId, HjhUserAuthResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}
	/**
	 * 查看该用户在出借表和标的放款记录中是否存在
	 * @param userId
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int isDismissPay(int userId) {
		IntegerResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userPayAuth/isDismissPay/"+userId, IntegerResponse.class)
				.getBody();
		if (response == null || !Response.isSuccess(response)) {
			return 0;
		}
		return response.getResultInt().intValue();
	}
	/**
	 * 查看该用户在投标的还款记录中是否存在
	 * @param userId
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int isDismissRePay(int userId) {
		IntegerResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userPayAuth/isDismissRePay/"+userId, IntegerResponse.class)
				.getBody();
		if (response == null || !Response.isSuccess(response)) {
			return 0;
		}
		return response.getResultInt().intValue();
	}

	/**
	 * 缴费授权解约
	 * @param userId
	 * @return
	 * @auther: nxl
	 */
	@Override
	public boolean updateCancelPayAuth(int userId) {
		BooleanResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userPayAuth/updateCancelPayAuth/"+userId, BooleanResponse.class)
				.getBody();
		if (response == null || !Response.isSuccess(response)) {
			return false;
		}
		return response.getResultBoolean().booleanValue();
	}
	/**
	 * 插入授权记录表
	 * @param hjhUserAuthLogRequest
	 * @return
	 * @auther: nxl
	 */
	@Override
	public boolean insertUserAuthLog2(HjhUserAuthLogRequest hjhUserAuthLogRequest) {
		BooleanResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userPayAuth/insertUserAuthLog2",hjhUserAuthLogRequest, BooleanResponse.class)
				.getBody();
		if (response == null || !Response.isSuccess(response)) {
			return false;
		}
		return response.getResultBoolean().booleanValue();
	}
	/**
	 * 根据用户的查询条件查询用户还款授权列表
	 * @param request
	 * @auther: nxl
	 * @return
	 */
	@Override
	public UserPayAuthResponse selectRecordListRePay(UserPayAuthRequest request) {
		UserPayAuthResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userPayAuth/selectRecordListRePay", request, UserPayAuthResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}
	/**
	 * 还款授权解约
	 * @param userId
	 * @return
	 * @auther: nxl
	 */
	@Override
	public int updateCancelRePayAuth(int userId) {
		IntegerResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/userPayAuth/updateCancelRePayAuth/"+userId, IntegerResponse.class)
				.getBody();
		if (response == null || !Response.isSuccess(response)) {
			return 0;
		}
		return response.getResultInt().intValue();
	}

	@Override
	public HjhUserAuthVO getHjhUserAuthByUserId(Integer userId) {
		HjhUserAuthResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/user/getHjhUserAuthByUserId/"+userId, HjhUserAuthResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}



	/**
	 * 根据用户Id查询开户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<BankOpenAccountLogVO> getBankOpenAccountLogVOByUserId(Integer userId) {
		String url = "http://AM-ADMIN/am-user/borrowOpenaccountenquiryException/selectBankOpenAccountLogByUserId/"+userId;
		BankOpenAccountLogResponse response = restTemplate.getForEntity(url, BankOpenAccountLogResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}




	@Override
	public int onlyCheckMobileCode(String mobile, String code) {
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(code);
		Integer result = restTemplate.postForEntity("http://AM-ADMIN/am-trade/sms_code/qianle_check/", request, IntegerResponse.class)
				.getBody().getResultInt();
		if (result == null) {
			return 0;
		}
		return result;
	}

    @Override
    public Integer getBySourceIdAndTerm(String utmId,String sourceId, String utmTerm) {
		UtmResponse response = restTemplate
				.getForEntity("http://AM-ADMIN/am-user/promotion/utm/getbysourceidandterm/"+sourceId+"/"+utmTerm+"/"+utmId, UtmResponse.class).getBody();
		if (response != null) {
			return response.getRecordTotal();
		}else{
			return  0;
		}
    }

	@Override
	public Integer getSmsCountForExport(SmsCountRequest request) {
		SmsCountCustomizeResponse response =
				restTemplate.postForEntity("http://AM-ADMIN/am-user/sms_count/getsmscountforexport", request, SmsCountCustomizeResponse.class).getBody();
		if(Response.isSuccess(response)){
			return response.getCount();
		}
		return 0;
	}

	@Override
	public List<SmsCountCustomizeVO> getSmsListForExport(SmsCountRequest request) {
		SmsCountCustomizeResponse response =
				restTemplate.postForEntity("http://AM-ADMIN/am-user/sms_count/getsmslistforexport", request, SmsCountCustomizeResponse.class).getBody();
		if(Response.isSuccess(response)){
			return response.getResultList();
		}
		return null;
	}

	@Override
	public int selectUserMemberCount(UserPayAuthRequest userPayAuthRequest) {
		IntegerResponse response = restTemplate.postForObject("http://AM-ADMIN/am-user/userPayAuth/selectUserMemberCount", userPayAuthRequest, IntegerResponse.class);
		if (response != null) {
			return response.getResultInt();
		}
		return 0;
	}

	@Override
	public int countUser(SmsCodeRequestBean requestBean) {
		IntegerResponse response = restTemplate.postForObject("http://AM-ADMIN/am-trade/smsCode/countUser",
				requestBean, IntegerResponse.class);
		if (response != null) {
			return response.getResultInt();
		}
		return 0;
	}

	/**
	 * 通过当前用户ID 查询用户所在一级分部,从而关联用户所属渠道
	 * @param userId
	 * @return
	 * @Author : huanghui
	 */
	@Override
	public UserUtmInfoCustomizeVO getUserUtmInfo(Integer userId) {
		String url = "http://AM-ADMIN/am-user/user/getUserUtmInfo/" + userId;
		UserUtmInfoResponse response = restTemplate.getForEntity(url, UserUtmInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/***
	 * 开户掉单，保存开户(User)数据
	 * @author Zha Daojian
	 * @date 2019/1/22 9:48
	 * @param requestBean
	 * @return openAccountEnquiryDefineRequestBeanVO
	 **/
	@Override
	public OpenAccountEnquiryDefineResultBeanVO updateUser(OpenAccountEnquiryDefineResultBean requestBean){

		OpenAccountEnquiryDefineRequest request = new OpenAccountEnquiryDefineRequest();
		BeanUtils.copyProperties(requestBean, request);
		OpenAccountEnquiryResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/borrowOpenaccountenquiryException/updateUser", request, OpenAccountEnquiryResponse.class).getBody();
		if (response == null || !Response.isSuccess(response)) {
			return null;
		}
		return response.getOpenAccountEnquiryDefineResultBeanVO();
	}

	/***
	 * 开户掉单，保存用户绑定的银行卡
	* @author Zha Daojian
	* @date 2019/3/15 10:13
	* @param requestBean
	* @return com.hyjf.am.vo.admin.OpenAccountEnquiryDefineResultBeanVO
	**/
	@Override
	public int insertUserCard(BankCardRequest requestBean){
		IntegerResponse response = restTemplate.postForObject("http://AM-ADMIN/am-user/borrowOpenaccountenquiryException/insertUserCard", requestBean, IntegerResponse.class);
		if (response != null) {
			return response.getResultInt();
		}
		return 0;
	}

	/***
	 * 开户掉单，保存开户(Account)数据
	 * @author Zha Daojian
	 * @date 2019/1/22 9:48
	 * @param requestBean
	 * @return openAccountEnquiryDefineRequestBeanVO
	 **/
	@Override
	public OpenAccountEnquiryDefineResultBeanVO updateAccount(OpenAccountEnquiryDefineResultBean requestBean){

		OpenAccountEnquiryDefineRequest request = new OpenAccountEnquiryDefineRequest();
		BeanUtils.copyProperties(requestBean, request);
		OpenAccountEnquiryResponse response = restTemplate
				.postForEntity("http://AM-ADMIN//am-trade/bankOpenUpdateAccountLog/updateAccount", request, OpenAccountEnquiryResponse.class).getBody();
		if (response == null || !Response.isSuccess(response)) {
			return null;
		}
		return response.getOpenAccountEnquiryDefineResultBeanVO();
	}

    @Override
    public int countUserNames(UserPortraitCustomizeRequest request) {
		IntegerResponse response = restTemplate.postForObject("http://AM-ADMIN/am-user/userPortraitManage/countUserNames",request, IntegerResponse.class);
		if (response != null) {
			return response.getResultInt();
		}
		return 0;
    }

	@Override
	public int updateBatch(UserPortraitCustomizeRequest request) {
		IntegerResponse response = restTemplate.postForObject("http://AM-ADMIN/am-user/userPortraitManage/importBatch", request, IntegerResponse.class);
		if (response != null) {
			return response.getResultInt();
		}
		return 0;
	}


	/**
	 * 企业信息补录时查询，根据对公账号查找银行信息
	 *
	 * @param updCompanyRequest
	 * @auther: nxl
	 * @return
	 */
	@Override
	public BankCardResponse getBankInfoByAccount(UpdCompanyRequest updCompanyRequest) {
		BankCardResponse response = restTemplate
				.postForEntity("http://AM-ADMIN/am-user/userManager/getBankInfoByAccount", updCompanyRequest, BankCardResponse.class)
				.getBody();
		return response;
	}

	/**
	 * 用户销户操作
	 *
	 * @param userId
	 * @param bankOpenAccount
	 * @return
	 */
	@Override
	public int cancellationAccountAction(String userId, Integer bankOpenAccount) {
		IntegerResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-user/userManager/cancellationAccountAction/" + userId + "/" + bankOpenAccount, IntegerResponse.class).getBody();
		if (response != null) {
			return response.getResultInt();
		}
		return 0;
	}

	/**
	 *
	 * 用户销户成功后,保存销户记录表
	 *
	 * @param bankCancellationAccountRequest
	 * @return
	 */
	@Override
	public int saveCancellationAccountRecordAction(BankCancellationAccountRequest bankCancellationAccountRequest) {
		IntegerResponse response = restTemplate.postForObject("http://AM-ADMIN/am-user/userManager/saveCancellationAccountRecordAction", bankCancellationAccountRequest, IntegerResponse.class);
		if (response != null) {
			return response.getResultInt();
		}
		return 0;
	}
}
