package com.hyjf.admin.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.trade.CorpOpenAccountRecordRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

	/**
	 * 根据accountId获取用户
	 * @auther: hesy
	 * @date: 2018/7/14
	 */
	@Override
	public UserVO getUserByAccountId(String accountId) {
		String url = "http://AM-USER/am-user/user/getby_accountid/" + accountId;
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

	/**
	 * 更具userName获取开户信息
	 * @auther: hesy
	 * @date: 2018/7/14
	 */
	@Override
	public BankOpenAccountVO queryBankOpenAccountByUserName(String userName) {
		BankOpenAccountResponse response = restTemplate.
				getForEntity("http://AM-USER/am-user/userManager/get_openaccount_byusername/" + userName, BankOpenAccountResponse.class).
				getBody();
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
				.postForEntity("http://AM-USER/am-user/userManager/userslist", request, UserManagerResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}

	/**
	 * 根据筛选条件查找用户总数
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int countRecordTotal(UserManagerRequest request) {
		UserManagerResponse response = restTemplate
				.postForEntity("http://AM-USER/am-user/userManager/countUserList", request, UserManagerResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getCount();
		}
		return 0;
	}

	/**
	 * 根据用户id获取用户详情
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserManagerDetailVO selectUserDetailById(String userId) {
		UserManagerDetailResponse response = restTemplate.
				getForEntity("http://AM-USER/am-user/userManager/selectUserDetail/" + userId, UserManagerDetailResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取测评信息
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserEvalationResultVO getUserEvalationResult(String userId) {
		UserEvalationResultResponse response = restTemplate.
				getForEntity("http://AM-USER/am-user/user/selectUserEvalationResultByUserId/" + userId, UserEvalationResultResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取开户信息
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserBankOpenAccountVO selectBankOpenAccountByUserId(String userId) {
		UserBankOpenAccountResponse response = restTemplate.
				getForEntity("http://AM-USER/am-user/userManager/selectBankOpenAccountByUserId/" + userId, UserBankOpenAccountResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取企业用户开户信息
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(String userId) {
		CorpOpenAccountRecordResponse response = restTemplate.
				getForEntity("http://AM-USER/am-user/userManager/selectCorpOpenAccountRecordByUserId/" + userId, CorpOpenAccountRecordResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取第三方平台绑定信息
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public BindUserVo selectBindeUserByUserId(String userId) {
		BindUserResponse response = restTemplate.
				getForEntity("http://AM-USER/am-user/userManager/selectBindUserByUserId/" + userId, BindUserResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取用户CA认证记录表
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId) {
		CertificateAuthorityResponse response = restTemplate.
				getForEntity("http://AM-USER/am-user/userManager/selectCertificateAuthorityByUserId/" + userId, CertificateAuthorityResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id获取用户修改信息
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserManagerUpdateVO selectUserUpdateInfoByUserId(String userId) {
		UserManagerUpdateResponse response = restTemplate.
				getForEntity("http://AM-USER/am-user/userManager/selectUserUpdateInfoByUserId/" + userId, UserManagerUpdateResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 更新用户信息
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int updataUserInfo(UserManagerUpdateRequest request) {
		int intUpdFlg = restTemplate.
				postForEntity("http://AM-USER/am-user/userManager/updataUserInfo", request, Integer.class).
				getBody();
		return intUpdFlg;
	}

	/**
	 * 根据用户id获取推荐人信息
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserRecommendCustomizeVO selectUserRecommendByUserId(String userId) {
		UserRecommendCustomizeResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userManager/selectUserRecommendUserId/" +userId, UserRecommendCustomizeResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
	/**
	 * 校验手机号
	 * @auther: nxl
	 * @param userId
	 * @param mobile
	 * @return
	 */
	@Override
	public int countUserByMobile(int userId, String mobile) {
		int checkFlg = restTemplate.
				getForEntity("http://AM-USER/am-user/userManager/checkMobileByUserId/" + userId +"/"+ mobile, Integer.class).
				getBody();
		return checkFlg;
	}

	/**
	 * 校验推荐人
	 * @auther: nxl
	 * @param userId
	 * @param recommendName
	 * @return
	 */
	@Override
	public int checkRecommend(String userId, String recommendName) {
		int checkFlg = restTemplate.
				getForEntity("http://AM-USER/am-user/userManager/checkRecommend/" + userId+"/" + recommendName, Integer.class).
				getBody();
		return checkFlg;
	}

	/**
	 * 根据用户id查找用户信息
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserVO selectUserByUserId(int userId) {
		UserResponse response = restTemplate.
				getForEntity("http://AM-USER/am-user/userManager/selectUserByUserId/" + userId, UserResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id查找用户信息
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public List<BankCardVO> selectBankCardByUserId(int userId) {
		BankCardResponse response = restTemplate.
				getForEntity("http://AM-USER/am-user/callcenter/getTiedCardForBank/" + userId, BankCardResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 更新企业用户开户记录
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int updateCorpOpenAccountRecord(CorpOpenAccountRecordRequest request) {
		int updFlg = restTemplate.
				postForEntity("http://AM-USER/am-user/userManager/updateCorpOpenAccountRecord", request, Integer.class).
				getBody();
		return updFlg;
	}

	/**
	 * 插入企业用户开户记录
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int insertCorpOpenAccountRecord(CorpOpenAccountRecordRequest request) {
		int instFlg = restTemplate.
				postForEntity("http://AM-USER/am-user/userManager/insertCorpOpenAccountRecord", request, Integer.class).
				getBody();
		return instFlg;
	}

	/**
	 * 根据银行卡号获取bankId
	 * @auther: nxl
	 * @param cardNo
	 * @return
	 */
	@Override
	public String queryBankIdByCardNo(String cardNo) {
		String result = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/queryBankIdByCardNo/" + cardNo, String.class).getBody();
		return result;
	}

	/**
	 * 获取银行卡配置信息
	 * @auther: nxl
	 * @param bankId
	 * @return
	 */
	@Override
	public BanksConfigVO getBanksConfigByBankId(int bankId) {
		BanksConfigResponse response = restTemplate
				.getForEntity("http://AM-CONFIG/am-config/config/getBanksConfigByBankId/" + bankId, BanksConfigResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 更新用户绑定的银行卡
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int updateUserCard(BankCardRequest request) {
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/card/updateUserCard", request, Integer.class).getBody();
		return result;
	}

	/**
	 * 保存用户绑定的银行卡
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int insertUserCard(BankCardRequest request) {
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/card/insertUserCard", request, Integer.class).getBody();
		return result;
	}

	/**
	 * 单表查询开户信息
	 * @auther: nxl
	 * @return
	 */
	@Override
	public BankOpenAccountVO queryBankOpenAccountByUserId(int userId) {
		BankOpenAccountResponse response = restTemplate.
				getForEntity("http://AM-USER/am-user/userManager/queryBankOpenAccountByUserId/" + userId, BankOpenAccountResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 更新开户信息
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int updateBankOpenAccount(BankOpenAccountRequest request) {
		int result = restTemplate
				.postForEntity("http://AM-USER//am-user/userManager/updateBankOpenAccount", request, Integer.class).getBody();
		return result;
	}

	/**
	 * 插入开户信息
	 * @auther: nxl
	 * @param request
	 * @return
	 */
	@Override
	public int insertBankOpenAccount(BankOpenAccountRequest request) {
		int result = restTemplate
				.postForEntity("http://AM-USER//am-user/userManager/insertBankOpenAccount", request, Integer.class).getBody();
		return result;
	}

	/**
	 * 根据用户id获取用户信息
	 * @auther: nxl
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoVO findUserInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 更新用户信息表
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int updateUserInfoByUserInfo(UserInfoVO userInfoVO) {
		UserInfoRequest request = null;
		BeanUtils.copyProperties(userInfoVO, request);
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/userManager/updateUserInfoByUserInfo", request, Integer.class).getBody();
		return result;
	}

	/**
	 * 更新用户表
	 * @return
	 * @auther: nxl
	 */
	@Override
	public int updateUser(UserVO userVO) {
		UserRequest request = null;
		BeanUtils.copyProperties(userVO, request);
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/userManager/updateUser", request, Integer.class).getBody();
		return result;
	}

	/**
	 * 获取某一用户的信息修改列表
	 * @param request
	 * @return
	 */
	@Override
	public List<UserChangeLogVO> selectUserChageLog(UserChangeLogRequest request){
		UserChangeLogResponse response = restTemplate
				.postForEntity("http://AM-USER/am-user/userManager/selectUserChageLog",request, UserChangeLogResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}
	/**
	 * 根据推荐人姓名查找用户
	 * @auther: nxl
	 * @param recommendName
	 * @return
	 */
	@Override
	public UserVO selectUserByRecommendName(String recommendName){
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userManager/selectUserByRecommendName/"+recommendName, UserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
	/**
	 * 根据userId查找推荐人信息
	 * @param userId
	 * @auther: nxl
	 * @return
	 */
	@Override
	public SpreadsUserVO selectSpreadsUsersByUserId(String userId){
		SpreadsUserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userManager/selectSpreadsUsersByUserId/"+userId, SpreadsUserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
	/**
	 * 修改推荐人信息
	 * @param request
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int updateUserRecommend(AdminUserRecommendRequest request){
		int response = restTemplate
				.postForEntity("http://AM-USER/am-user/userManager/updateUserRecommend",request, Integer.class).getBody();
		return response;
	}
	/**
	 * 修改用户身份证
	 * @param request
	 * @auther: nxl
	 * @return
	 */
	@Override
	public int updateUserIdCard(AdminUserRecommendRequest request){
		int response = restTemplate
				.postForEntity("http://AM-USER/am-user/userManager/updateUserIdCard",request, Integer.class).getBody();
		return response;
	}

	/**
	 * 修改用户身份证
	 * @param updCompanyRequest
	 * @auther: nxl
	 * @return
	 */
	@Override
	public Response saveCompanyInfo(UpdCompanyRequest updCompanyRequest){
		Response response = restTemplate
				.postForEntity("http://AM-USER/am-user/userManager/saveCompanyInfo",updCompanyRequest, Response.class).getBody();
		return response;
	}
    /**
     * 根据参数查询用户画像信息
     * @param request
     * @return
     */
    @Override
    public UserPortraitResponse selectRecordList(UserPortraitRequest request){
        UserPortraitResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/userPortraitManage/findUserPortraitRecord", request, UserPortraitResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 根据用户id查找用户画像
     * @param userId
     * @return
     */
    @Override
    public UserPortraitVO selectUsersPortraitByUserId(Integer userId){
        UserPortraitResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/userPortraitManage/selectUserPortraitByUserId/"+ userId, UserPortraitResponse.class)
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
    public int updateUserPortrait(UserPortraitRequest request){
        int response = restTemplate
                .postForEntity("http://AM-USER/am-user/userPortraitManage/updateUserPortraitRecord",request,Integer.class)
                .getBody();
        return response;
    }

}
