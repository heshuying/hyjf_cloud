package com.hyjf.cs.user.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.response.config.CustomerServiceGroupConfigResponse;
import com.hyjf.am.response.trade.AdminBankAccountCheckCustomizeResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.api.WrbRegisterRequest;
import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.util.ReflectUtils;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author xiasq
 * @version AmUserClientImpl, v0.1 2018/4/19 12:44
 */

@Cilent
public class AmUserClientImpl implements AmUserClient {
	private static Logger logger = getLogger(AmUserClient.class);

	@Value("${am.user.service.name}")
	private String userService;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserVO findUserByMobile(String mobile) {
		UserResponse response = restTemplate
				.getForEntity(userService+"/user/findByMobile/" + mobile, UserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据推荐人手机号或userId查询推荐人
	 */
	@Override
	public int countUserByRecommendName(String reffer) {
		UserResponse response = restTemplate
				.getForEntity(userService+"/user/findUserByRecommendName/" + reffer, UserResponse.class).getBody();
		if (response != null  && response.getResult() != null) {
			return 1;
		}
		return 0;
	}

	@Override
	public UserVO register(RegisterUserRequest request) {
		UserResponse response = restTemplate
				.postForEntity(userService+"/user/register", request, UserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 获取银行卡信息
	 * @param userId
	 * @param status
	 * @return
	 */
	@Override
	public List<BankCardVO> selectBankCardByUserIdAndStatus(Integer userId, Integer status) {
		BankCardResponse response = restTemplate
				.getForEntity(userService+"/bankopen/selectBankCardByUserIdAndStatus/" + userId+"/"+status, BankCardResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public UserAliasVO findAliasesByUserId(Integer userId) {
		UserAliasResponse response = restTemplate
				.getForEntity(userService+"/userAlias/findAliasesByUserId/" + userId, UserAliasResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public void updateAliases(UserAliasVO mobileCode) {
		Integer cnt = restTemplate
				.postForEntity(userService+"/userAlias/updateMobileCode", mobileCode, Integer.class).getBody();

	}

	@Override
	public void insertMobileCode(UserAliasVO mobileCode) {
		Integer cnt = restTemplate
				.postForEntity(userService+"/userAlias/insertMobileCode", mobileCode, Integer.class).getBody();
	}

	@Override
	public VipInfoVO findVipInfoById(Integer vipId) {
		VipInfoResponse response = restTemplate
				.getForEntity(userService+"/vipInfo/findVipInfoById/" + vipId, VipInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<AccountBankVO> selectAccountBank(Integer userId, int status) {
		AccountBankResponse response = restTemplate
				.getForEntity(userService+"/accountbank/selectAccountBank/" + userId+"/"+status, AccountBankResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public UserVO fUserById(int userId) {
		String url = userService + "/user/findMainById/" + userId;
		UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
		if (response != null) {
			if (Response.SUCCESS.equals(response.getRtn())) {
				UserVO userVO =  response.getResult();
				return userVO;
			}
			logger.info("response rtn is : {}", response.getRtn());
		} else {
			logger.info("response is null....");
		}
		return null;
	}

	@Override
	public UserVO findUserById(int userId) {
		String url = userService + "/user/findById/" + userId;
		UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
		if (response != null) {
			if (Response.SUCCESS.equals(response.getRtn())) {
				UserVO userVO =  response.getResult();
				return userVO;
			}
			logger.info("response rtn is : {}", response.getRtn());
		} else {
			logger.info("response is null....");
		}
		return null;
	}

	/**
	 * 通过当前用户ID 查询用户所在一级分部,从而关联用户所属渠道
	 * @param userId
	 * @return
	 * @Author : huanghui
	 */
	@Override
	public UserUtmInfoCustomizeVO getUserUtmInfo(Integer userId) {
		String url = userService + "/user/getUserUtmInfo/" + userId;
		UserUtmInfoResponse response = restTemplate.getForEntity(url, UserUtmInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserInfoVO findUserInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity(userService+"/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
	@Override
	public UserInfoVO fUserInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity(userService+"/userInfo/findMainById/" + userId, UserInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserVO updateUsersById(Integer userId) {
		String url = userService + "/user/updateById/" + userId;
		UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
		if (response != null) {
			if (Response.SUCCESS.equals(response.getRtn())) {
				UserVO userVO =  response.getResult();
				return userVO;
			}
			logger.info("response rtn is : {}", response.getRtn());
		} else {
			logger.info("response is null....");
		}
		return null;
	}

	@Override
	public void fddCertificate() {
		restTemplate.getForEntity("http://AM-USER/am-user/batch/fddCertificate", String.class);
	}

	@Override
	public void updateEntey() {
		restTemplate.getForEntity("http://AM-USER//am-user/batch/entryupdate", String.class);
	}

	@Override
	public void updateUserLeave() {
		restTemplate.getForEntity("http://AM-USER/am-user/batch/leaveupdate", String.class);
	}

	@Override
	public WebViewUserVO getWebViewUserByUserId(Integer userId,String channel) {
		WebViewUserResponse response = restTemplate
				.getForEntity(userService + "/user/getWebViewUserByUserId/"+ userId+"/"+channel, WebViewUserResponse.class).getBody();
		return response.getResult();
	}

	@Override
	public void updateUser(UserVO u, String ipAddr) {
		LoginUserRequest request = new LoginUserRequest(u.getUserId(), ipAddr,u);
		String url = userService+"/user/updateUser";
		logger.info("url:{}", url);
		restTemplate.postForEntity(url, request, BooleanResponse.class);
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
				.getForEntity("http://AM-USER/am-user/userManager/queryBankOpenAccountByUserId/" + userId,
						BankOpenAccountResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据sourceID 获取渠道信息
	 * @param sourceId
	 * @return
	 */
	@Override
	public UtmVO selectUtmBySourceId(String sourceId) {

		UtmVOResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/utm/selectUtmBySourceId/" + sourceId,
						UtmVOResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * pc1.1.3 新增 如果重置密码成功 就解锁帐号锁定
	 *
	 * @param userId
	 */
	@Override
	public void unlockUser(Integer userId) {
		String url = userService+"/user/unlockUser/"+userId;
		restTemplate.postForEntity(url, null, BooleanResponse.class);
	}

	/**
	 * 保存验证码
	 * @param mobile
	 * @param checkCode
	 * @param validCodeType
	 * @param status
	 * @param platform
	 * @return
	 */
	@Override
	public int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform) {
		logger.debug("短信验证码入库, mobile is:　{}", mobile);
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(checkCode);
		request.setVerificationType(validCodeType);
		request.setStatus(status);
		request.setPlatform(platform);
		SmsCodeResponse response = restTemplate
				.postForEntity(userService + "/smsCode/save", request, SmsCodeResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getCnt();
		} else {
			logger.warn("response is null, send fail....");
			throw new RuntimeException("发送验证码失败...");
		}
	}

	@Override
	public int checkMobileCode(String mobile, String verificationCode, String verificationType, String platform,
							   Integer searchStatus, Integer updateStatus,boolean isUpdate) {
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(verificationCode);
		request.setVerificationType(verificationType);
		request.setPlatform(platform);
		request.setStatus(searchStatus);
		request.setUpdateStatus(updateStatus);
		request.setUpdate(isUpdate);
		IntegerResponse result = restTemplate.postForEntity(userService+"/smsCode/check/", request, IntegerResponse.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result.getResultInt();
	}

	@Override
	public UserVO findUserByUserNameOrMobile(String loginUserName) {
		UserResponse response = restTemplate
				.getForEntity(userService+"/user/findByCondition/" + loginUserName, UserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserVO updateByCondition(String loginUserName) {
		UserResponse response = restTemplate
				.getForEntity(userService+"/user/updateByCondition/" + loginUserName, UserResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public void updateLoginUser(int userId, String ip) {
		LoginUserRequest request = new LoginUserRequest(userId, ip);

		String url = userService+"/user/updateLoginUser";
		logger.info("url:{}", url);
		restTemplate.postForEntity(url, request, BooleanResponse.class);
	}


	@Override
	public HjhUserAuthVO getHjhUserAuthByUserId(Integer userId) {
		HjhUserAuthResponse response = restTemplate
				.getForEntity(userService+"/user/getHjhUserAuthByUserId/"+userId, HjhUserAuthResponse.class)
				.getBody();
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

	@Override
	public void insertUserAuthLog(HjhUserAuthLogVO hjhUserAuthLog) {
		restTemplate.put(userService+"/user/insertLogSelective",hjhUserAuthLog);
	}

	/**
	 * 查询授权错误信息
	 * @param orderId
	 * @return
	 */
	@Override
	public HjhUserAuthLogVO selectByExample(String orderId) {
		HjhUserAuthLogResponse response = restTemplate
				.getForEntity(userService+"/user/selectByExample/"+orderId, HjhUserAuthLogResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public void updateUserAuthInves(BankRequest bean){
		restTemplate.put(userService+"/user/updateUserAuthInves", bean) ;
	}

	/**
	 * 根据user修改
	 * @param user
	 * @return
	 */
	@Override
	public Integer updateUserById(UserVO user) {
		if(user == null || user.getUserId() == null){
			return 0;
		}
		IntegerResponse result = restTemplate.postForEntity(userService+"/user/updateByUserId", user, IntegerResponse.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result.getResultInt();
	}

	/**
	 * 统计手机号
	 * @param mobile
	 * @return
	 */
	@Override
	public int countByMobile(String mobile){
		int checkFlg = restTemplate.
				getForEntity(userService+"/userManager/countByMobile/"+ mobile, Integer.class).
				getBody();
		return checkFlg;
	}

	/**
	 * 修改登陆密码
	 * @param userId
	 * @param oldPW
	 * @param newPW
	 * @return
	 */
	@Override
	public JSONObject updatePassWd(Integer userId, String oldPW, String newPW) {
		logger.info("AmUserClient.updatePassWd run...userId is :{}, oldPW is :{}, newPW is :{}",userId,oldPW,newPW);
		JSONObject result = new JSONObject();
		if(userId == null || StringUtils.isBlank(oldPW) || StringUtils.isBlank(newPW)){
			result.put("status", "1");
			result.put("statusDesc", "请求参数非法");
			return result;
		}
		String url = userService+"/user/updatePassWd/" + userId + "/" + oldPW + "/" + newPW;
		logger.info("url:{}", url);
		ResponseEntity<JSONObject> resp = restTemplate.getForEntity(url, JSONObject.class);
		result = resp.getBody();
		if (result == null) {
			result = new JSONObject();
			result.put("status", "1");
			result.put("statusDesc", "修改密码失败,未作任何操作");
		}
		return result;
	}


	@Override
	public AccountChinapnrVO getAccountChinapnr(Integer userId) {
		AccountChinapnrResponse response = restTemplate
				.getForEntity(userService+"/user/getAccountChinapnr/" + userId, AccountChinapnrResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public AccountPandectVO getAccount4Pandect(Integer userId) {
		String url = userService + "/user/getAccount4Pandect/" + userId ;
		AccountPandectResponse response = restTemplate.getForEntity(url,AccountPandectResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResult();
		}
		return null;
	}

	/**
	 * 校验邮箱是否存在
	 */
	@Override
	public boolean checkEmailUsed(String email) {
		BooleanResponse response = restTemplate
				.getForEntity(userService+"/user/checkEmailUsed/" + email, BooleanResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultBoolean();
		}
		return false;
	}

	/**
	 * 插入绑卡记录
	 * @param bean
	 * @return
	 */
	@Override
	public Integer insertBindEmailLog(BindEmailLogRequest bean) {
		IntegerResponse response = restTemplate.postForEntity(userService+"/user/insertBindEmailLog/",  bean, IntegerResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultInt();
		}
		return 0;
	}

	/**
	 * 获取绑定邮箱记录
	 * @param userId
	 * @return
	 */
	@Override
	public BindEmailLogVO getBindEmailLog(Integer userId) {
		BindEmailLogResponse response = restTemplate
				.getForEntity(userService+"/user/getBindEmailLog/" + userId, BindEmailLogResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 更新绑定邮箱
	 * @param bean
	 * @return
	 */
	@Override
	public Integer updateBindEmail(BindEmailLogRequest bean) {
		IntegerResponse response = restTemplate.postForEntity(userService+"/user/updateBindEmail/" + bean.getUserId() + "/" + bean.getUserEmail(),  bean, IntegerResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultInt();
		}
		return 0;
	}

	/**
	 * 保存、更新紧急联系人
	 * @param bean
	 * @return
	 */
	@Override
	public int updateUserContract(UsersContractRequest bean) {
		IntegerResponse response = restTemplate
				.postForEntity(userService+"/user/updateUserContract/",  bean, IntegerResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultInt();
		}
		return 0;
	}

	/**
	 * @Author: zhangqingqing
	 * @Desc : 查询紧急联系人
	 * @Param: * @param userId
	 * @Date: 14:25 2018/6/4
	 * @Return: UsersContactVO
	 */
	@Override
	public UsersContactVO selectUserContact(Integer userId) {
		UsersContactResponse response = restTemplate
				.getForEntity(userService+"/user/selectUserContact/" + userId, UsersContactResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据userId修改
	 * @param
	 * @return
	 */
	@Override
	public int updateUserNoticeSet(UserNoticeSetRequest requestBean) {
		if(requestBean == null || requestBean.getUserId() == null){
			return 0;
		}
		IntegerResponse response = restTemplate.postForEntity(userService+"/user/updateByUserId", requestBean, IntegerResponse.class)
				.getBody();
		if (Response.isSuccess(response)) {
			return response.getResultInt();
		}
		return 0;
	}
	/**
	 * @Description 根据身份证号查询用户
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/6 11:33
	 */
	@Override
	public UserInfoVO getUserByIdNo(String idNo) {
		UserInfoResponse response = restTemplate
				.getForEntity(userService+"/userInfo/findByIdNo/" + idNo, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}


	@Override
	public UserLoginLogVO getUserLoginById(Integer userId){
		UserLoginLogResponse response = restTemplate
				.getForEntity(userService+"/user/getUserLoginById/" + userId, UserLoginLogResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public BankOpenAccountVO selectById(int userId) {
		logger.info(ReflectUtils.getSuperiorClass(3));
		BankOpenAccountResponse response = restTemplate
				.getForEntity(userService+"/bankopen/selectById/" + userId, BankOpenAccountResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 *
	 * 更新绑卡验证码
	 */
	@Override
	public boolean updateBankSmsLog(BankSmsLogRequest request) {
		boolean result = restTemplate
				.postForEntity(userService+"/card/updateBankSmsLog", request, Boolean.class).getBody();
		return result;
	}


	@Override
	public String selectBankSmsLog(BankSmsLogRequest request) {
		String result = restTemplate
				.postForEntity(userService+"/card/selectBankSmsLog", request, String.class).getBody();
		return result;
	}

	@Override
	public List<EvalationCustomizeVO> getEvalationRecord() {
		EvalationCustomizeResponse response = restTemplate
				.getForEntity(userService+"/user/getEvalationRecord", EvalationCustomizeResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public EvalationVO getEvalationByEvalationType(String evalationType) {
		EvalationResponse response = restTemplate
				.getForEntity(userService+"/user/getEvalationByEvalationType/" + evalationType, EvalationResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
	@Override
	public UserEvalationResultVO insertUserEvalationResult(UserEvalationRequest userEvalationRequest) {
		UserEvalationResultResponse response = restTemplate.postForEntity(userService+"/user/insertUserEvalationResult",userEvalationRequest,UserEvalationResultResponse.class).getBody();
		if(null!=response){
			return   response.getResult();
		}
		return  null;
	}


	@Override
	public int updateUserAccountLog(BankOpenRequest request) {
		IntegerResponse result = restTemplate
				.postForEntity(userService+"/bankopen/updateUserAccountLog", request, IntegerResponse.class).getBody();
		if (result != null ) {
			return result.getResultInt();
		}
		return 0;
	}

	@Override
	public BankOpenAccountVO selectByAccountId(String accountId) {
		BankOpenAccountResponse response = restTemplate
				.getForEntity(userService+"/bankopen/selectByAccountId/" + accountId, BankOpenAccountResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId) {
		UserEvalationResultResponse response = restTemplate
				.getForEntity(userService+"/user/selectUserEvalationResultByUserId/" + userId, UserEvalationResultResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}


	/**
	 * 修改开户日志表的状态
	 * @param userId
	 * @param logOrderId
	 * @param state
	 * @param retCode
	 */
	@Override
	public Integer updateUserAccountLogState(int userId, String logOrderId, int state, String retCode, String retMsg) {
		BankOpenRequest request = new BankOpenRequest();
		request.setOrderId(logOrderId);
		request.setUserId(userId);
		request.setStatus(state);
		request.setRetCode(retCode);
		request.setRetMsg(retMsg);
		Integer result = restTemplate
				.postForEntity(userService+"/bankopen/updateUserAccountLogStatus", request, Integer.class).getBody();
		if (result != null) {
			return result;
		}
		return 0;
	}

	/**
	 * 保存用户的开户信息
	 * @param bean
	 * @return
	 */
	@Override
	public Integer saveUserAccount(BankCallBean bean) {
		BankOpenRequest request = new BankOpenRequest();
		request.setOrderId(bean.getLogOrderId());
		request.setUserId(Integer.parseInt(bean.getLogUserId()));
		request.setAccountId(bean.getAccountId());
		request.setBankAccountEsb(bean.getLogClient());
		request.setTrueName(bean.getName());
		request.setIdNo(bean.getIdNo());
		request.setMobile(bean.getMobile());
		// 设置角色
		/** 开户角色属性   1：出借角色2：借款角色3：代偿角色*/
		request.setRoleId(Integer.parseInt(bean.getIdentity()));
		request.setIsSetPassword(0);
		// 开户+设密的话   状态改为已设置交易密码
		if (BankCallConstant.TXCODE_ACCOUNT_OPEN_ENCRYPT_PAGE.equals(bean.getTxCode())
				&& "1".equals(bean.getStatus())) {
			request.setIsSetPassword(1);
		}
		Integer result = restTemplate
				.postForEntity(userService+"/bankopen/updateUserAccount", request, Integer.class).getBody();
		if (result != null) {
			return result;
		}
		return 0;
	}

	/**
	 * 开户成功保存银行卡信息
	 * @param request
	 * @return
	 */
	@Override
	public Integer saveCardNoToBank(BankCardRequest request) {
		Integer result = restTemplate
				.postForEntity(userService+"/bankopen/saveCardNoToBank", request, Integer.class).getBody();
		if (result != null) {
			return result;
		}
		return 0;
	}

	/**
	 * 查询用户已绑定的有效卡
	 * @param userId
	 * @param cardNo
	 * @return
	 */
	@Override
	public BankCardVO queryUserCardValid(String userId, String cardNo) {
		BankCardResponse response = restTemplate
				.getForEntity(userService+"/card/queryUserCardValid/" + userId + "/" + cardNo, BankCardResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 统计用户绑定的有效银行卡个数
	 * @param userId
	 * @return
	 */
	@Override
	public int countUserCardValid(String userId) {
		int count = restTemplate
				.getForEntity(userService+"/card/countUserCardValid/" + userId, Integer.class).getBody();
		return count;
	}

	/**
	 * 根据userId删除银行卡信息
	 * @param userId
	 * @return
	 */
	@Override
	public int deleteUserCardByUserId(String userId) {
		int result = restTemplate
				.getForEntity(userService+"/card/deleteUserCardByUserId/" + userId, Integer.class).getBody();
		return result;
	}

	/**
	 * 保存用户绑定的银行卡
	 * @param request
	 * @return
	 */
	@Override
	public int insertUserCard(BankCardRequest request) {
		logger.info("方法insertUserCard请求参数：" + request);
		int result = restTemplate
				.postForEntity(userService+"/card/insertUserCard", request, Integer.class).getBody();
		return result;
	}

	/**
	 * 更新用户绑定的银行卡
	 * @param request
	 * @return
	 */
	@Override
	public int updateUserCard(BankCardRequest request) {
		int result = restTemplate
				.postForEntity(userService+"/card/updateUserCard", request, Integer.class).getBody();
		return result;
	}

	/**
	 * 保存绑卡日志
	 */
	@Override
	public int insertBindCardLog(BankCardLogRequest request) {
		int result = restTemplate
				.postForEntity(userService+"/card/insertBindCardLog", request, Integer.class).getBody();
		return result;
	}

	@Override
	public CorpOpenAccountRecordVO getCorpOpenAccountRecord(int userId) {
		CorpOpenAccountRecordResponse response = restTemplate
				.getForEntity(userService+"/bankopen/getCorpOpenAccountRecord/" + userId, CorpOpenAccountRecordResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<BankCardVO> getTiedCardForBank(Integer userId) {
		BankCardResponse bankCardResponse = restTemplate
				.getForEntity(userService+"/callcenter/getTiedCardForBank/" + userId, BankCardResponse.class)
				.getBody();
		if (bankCardResponse != null) {
			return bankCardResponse.getResultList();
		}
		return null;
	}

	@Override
	public int isCompAccount(Integer userId) {
		IntegerResponse result = restTemplate
				.getForEntity(userService+"/user/isCompAccount/" + userId, IntegerResponse.class).getBody();
		return result.getResultInt();
	}

	/**
	 * @param logOrdId
	 * @Description 查询开户失败原因
	 * @Author sunss
	 * @Date 2018/6/21 15:45
	 */
	@Override
	public String getBankOpenAccountFiledMess(String logOrdId) {
		String mess = restTemplate
				.getForEntity(userService+"/bankopen/getBankOpenAccountFiledMess/" + logOrdId, String.class).getBody();
		return mess;
	}

	@Override
	public UtmPlatVO selectUtmPlatByUtmId(String utmId){
		UtmPlatResponse response = restTemplate
				.getForEntity(userService+"/user/selectUtmPlatByUtmId/" + utmId,UtmPlatResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 我的邀请列表
	 * @param requestBean
	 * @return
	 */
	@Override
	public List<MyInviteListCustomizeVO> selectMyInviteList(MyInviteListRequest requestBean){
		String url = userService+"/invite/myInviteList";
		MyInviteListResponse response = restTemplate.postForEntity(url,requestBean,MyInviteListResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 我的邀请记录数
	 * @param requestBean
	 * @return
	 */
	@Override
	public Integer selectMyInviteCount(MyInviteListRequest requestBean) {
		IntegerResponse response = restTemplate
				.postForEntity(userService+"/invite/myInviteCount", requestBean, IntegerResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResultInt();
		}
		return 0;
	}

	/**
	 * 查询同步银行卡号
	 * @param flag
	 * @return
	 */
	@Override
	public List<AccountMobileSynchVO> searchAccountMobileSynch(String flag){
		String url = userService+"/batch/searchAccountMobileSynch/" + flag;
		AccountMobileSynchResponse response = restTemplate.getForEntity(url,AccountMobileSynchResponse.class).getBody();
		if(response != null){
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 更新银行卡号手机号同步表
	 * @param accountMobileSynchRequest
	 * @return
	 */
	@Override
	public boolean updateAccountMobileSynch(AccountMobileSynchRequest accountMobileSynchRequest){
		String url = userService+"/batch/updateAccountMobileSynch";
		AccountMobileSynchResponse response =
				restTemplate.postForEntity(url,accountMobileSynchRequest, AccountMobileSynchResponse.class).getBody();
		if(Response.isSuccess(response)){
			return response.getUpdateFlag();
		}
		return false;
	}
	/**
	 * 集团组织结构查询
	 * @return List<OrganizationStructureVO> 组织结构list
	 * */
	@Override
	public List<OrganizationStructureVO> searchGroupInfo() {
		String url = userService+"/group/query_group_info";
		GroupInfoResponse response = restTemplate.getForEntity(url,GroupInfoResponse.class).getBody();
		if(response != null){
			return response.getResultList();
		}
		return null;
	}
	/**
	 * 更新用户画像 99:更新三个月的用户画像,else:更新昨日登录的用户画像
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public List<UserAndSpreadsUserVO> searchUserIdForUserPortrait(int flag) {
		String url = "http://AM-USER/user_batch/portrait/search_user_id_for_user_portrait/" + flag;
		UserAndSpreadsUserResponse response = restTemplate.getForEntity(url, UserAndSpreadsUserResponse.class).getBody();
		if(response != null){
			return response.getResultList();
		}
		return null;
	}
	/**
	 * 保存用户画像
	 * @param batchUserPortraitQueryRequest 从am-trade查询到的保存画像所需的信息
	 * */
	@Override
	public void saveUserPortrait(BatchUserPortraitQueryRequest batchUserPortraitQueryRequest) {
		String url = "http://AM-USER/user_batch/portrait/save_user_portrait";
		restTemplate.postForEntity(url,batchUserPortraitQueryRequest,String.class);
	}

	@Override
	public int saveUserEvaluation(UserEvalationResultVO userEvalationResult) {
		IntegerResponse count = restTemplate
				.postForEntity(userService+"/user/saveUserEvaluation", userEvalationResult, IntegerResponse.class).getBody();
		return count.getResultInt();
	}

	@Override
	public Integer insertUserEvalationBehavior(Integer userId, String behavior) {
		IntegerResponse id = restTemplate.getForEntity(userService+"/user/insertUserEvalationBehavior/"+userId+"/"+behavior, IntegerResponse.class).getBody();
		return id.getResultInt();
	}

	@Override
	public Integer updateUserEvaluationBehavior(UserEvalationBehaviorVO userEvalationBehavior) {
		int count = restTemplate
				.postForEntity(userService+"/user/updateUserEvalationBehavior", userEvalationBehavior, Integer.class).getBody();
		return count;
	}

	@Override
	public void clearMobileCode(Integer userId, String sign) {
		restTemplate.getForEntity(userService+"/user/insertUserEvalationBehavior/"+userId+"/"+sign, IntegerResponse.class);
	}

	@Override
	public UserVO surongRegister(RegisterUserRequest request) {
		UserResponse response = restTemplate
				.postForEntity(userService+"/user/surongRegister", request, UserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 用户删除银行卡后调用方法
	 * @auther: hesy
	 * @date: 2018/7/19
	 */
	@Override
	public Boolean updateAfterDeleteCard(BankCardUpdateRequest requestBean){
		Response<Boolean> response = restTemplate
				.postForEntity(userService+"/card/update_after_deletecard", requestBean, Response.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return false;
	}

	/**
	 * 根据用户id获取银行卡信息
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @return
	 */
	@Override
	public List<AccountBankVO> getAccountBankByUserId(Integer userId) {
		String url = userService + "/accountbank/getAccountBankByUserId/" + userId;
		AccountBankResponse response = restTemplate.getForEntity(url, AccountBankResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<AdminBankAccountCheckCustomizeVO> queryAllBankOpenAccount(Integer userId) {
		String url = userService+"/accountbank/queryAllBankOpenAccount/"+userId;
		AdminBankAccountCheckCustomizeResponse response = restTemplate.getForEntity(url,AdminBankAccountCheckCustomizeResponse.class).getBody();
		if (Validator.isNotNull(response)){
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据accounId获取开户信息
	 * @param accountId
	 * @return
	 */
	@Override
	public BankOpenAccountVO selectBankOpenAccountByAccountId(String accountId) {
		BankOpenAccountResponse response = restTemplate
				.getForEntity(userService+"/userManager/selectBankOpenAccountByAccountId/" + accountId, BankOpenAccountResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
	@Override
	public Integer getUserIdByBind(Integer bindUniqueId, Integer bindPlatformId) {
		Integer id = restTemplate.getForEntity(userService+"/userManager/getUserIdByBind/"+bindUniqueId+"/"+bindPlatformId, Integer.class).getBody();
		return id;
	}

	@Override
	public String getBindUniqueIdByUserId(int userId, int bindPlatformId) {
		return restTemplate.getForEntity(userService+"/userManager/getBindUniqueIdByUserId/"+userId+"/"+bindPlatformId, String.class).getBody();
	}

	/**
	 * 获取第三方绑定用户
	 * @param userId
	 * @param bindPlatformId
	 * @return
	 */
	@Override
	public BindUserVo getBindUser(int userId, int bindPlatformId) {
		BindUserResponse response = restTemplate.getForEntity(userService+"/userManager/getBindUser/"+userId+"/"+bindPlatformId, BindUserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public Boolean bindThirdUser(Integer userId, Integer bindUniqueId, Integer pid) {
		BooleanResponse response = restTemplate.getForEntity(userService+"/userManager/bindThirdUser/"+userId+"/"+bindUniqueId+"/"+pid, BooleanResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultBoolean();
		}
		return null;
	}

	/**
	 * 根据userId查询BankCard
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @return
	 */
	@Override
	public BankCardVO getBankCardByUserId(Integer userId) {
		String url = userService + "/bankCard/getBankCard/" + userId;
		BankCardResponse response = restTemplate
				.getForEntity(url, BankCardResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserEvalationResultVO skipEvaluate(Integer userId, int countScore) {
		UserEvalationResultResponse response = restTemplate.getForEntity(userService+"/user/skipEvaluate/"+userId+"/"+countScore,UserEvalationResultResponse.class).getBody();
		if(null!=response){
			return   response.getResult();
		}
		return  null;
	}
	/**
	 * 插入ht_hjh_user_auth表
	 * @param hjhUserAuthRequest
	 * @auth nxl
	 * @return
	 */
	@Override
	public int insertHjhUserAuth(HjhUserAuthRequest hjhUserAuthRequest){
		Integer response = restTemplate.postForEntity(userService+"/user/insertHjhUserAuth",hjhUserAuthRequest,Integer.class).getBody();
		return response;
	}
	/**
	 * 更新ht_hjh_user_auth表
	 * @param hjhUserAuthRequest
	 * @auth nxl
	 * @return
	 */
	@Override
	public int updateHjhUserAuth(HjhUserAuthRequest hjhUserAuthRequest){
		Integer response = restTemplate.postForEntity(userService+"/user/updateHjhUserAuth",hjhUserAuthRequest,Integer.class).getBody();
		return response;
	}
	/**
	 * 更新 ht_hjh_user_auth_log 表
	 * @param hjhUserAuthRequest
	 * @auth nxl
	 * @return
	 */
	@Override
	public int updateHjhUserAuthLog(HjhUserAuthLogRequest hjhUserAuthRequest){
		Integer response = restTemplate.postForEntity(userService+"/user/updateHjhUserAuthLog",hjhUserAuthRequest,Integer.class).getBody();
		return response;
	}

	@Override
	public boolean updateMobileSynch(AccountMobileSynchRequest accountMobileAynch) {
		String url = userService+"/batch/updateMobileSynch";
		AccountMobileSynchResponse response =
				restTemplate.postForEntity(url,accountMobileAynch, AccountMobileSynchResponse.class).getBody();
		if(Response.isSuccess(response)){
			return response.getUpdateFlag();
		}
		return false;
	}

	@Override
	public boolean updateByPrimaryKey(UserVO userVO) {
		IntegerResponse result = restTemplate
				.postForEntity("http://AM-USER/am-user/user/updateByUserId", userVO, IntegerResponse.class).getBody();
		if (result != null) {
			return result.getResultInt() == 0 ? false : true;
		}
		return false;
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
				.postForEntity("http://AM-USER/am-user/userManager/updateUserInfoByUserInfo", request, IntegerResponse.class)
				.getBody();
		if (result == null || !Response.isSuccess(result)) {
			return 0;
		}
		return result.getResultInt().intValue();
	}

	@Override
	public Integer insertUserAction(WrbRegisterRequest wrbRegisterRequest) {
		Integer body = restTemplate
				.postForEntity("http://AM-USER/am-user/wrb/register", wrbRegisterRequest, IntegerResponse.class)
				.getBody().getResultInt();
		return body;
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
		logger.info(ReflectUtils.getSuperiorClass(3));
		String url = "http://AM-USER/am-user/bankopen/selectById/" + userId;
		BankOpenAccountResponse response = restTemplate.getForEntity(url, BankOpenAccountResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
	/**
	 * 插入密码错误超限用户信息
	 * @param lockedUserInfoVO
	 * @return
	 */
	public void inserLockedUser(LockedUserInfoVO lockedUserInfoVO){
		int result = restTemplate
				.postForEntity(userService+"/user/insertLockedUser",lockedUserInfoVO,Integer.class).getBody();
	}

	@Override
	public void updateUserAuth(UserAuthRequest request) {
		restTemplate.postForEntity(userService+"/userauth/updateUserAuth", request,IntegerResponse.class) ;
	}

	@Override
	public void updateUserAuthLog(HjhUserAuthLogVO hjhUserAuthLog) {

		restTemplate.put(userService+"/userauth/updateUserAuthLog",hjhUserAuthLog);
	}

	/**
	 * 根据用户Id,银行卡Id查询用户银行卡信息
	 * @param userId
	 * @param cardId
	 * @auther: nxl
	 * @return
	 */
	@Override
	public BankCardVO getBankCardById(int userId, String cardId) {
		BankCardResponse response = restTemplate
				.getForEntity(userService+"/card/getBankCardById/" + userId + "/" + cardId, BankCardResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
	@Override
	public boolean insertAppChannelStatisticsDetail(WrbRegisterRequest wrbRegisterRequest) {
		boolean body = restTemplate
				.postForEntity("http://AM-USER/am-user/app_utm_reg/insertAppChannelStatisticsDetail", wrbRegisterRequest, BooleanResponse.class)
				.getBody().getResultBoolean();
		return body;
	}

	@Override
	public AccountVO getAccount(Integer userId) {
		return null;
	}

	/**
	 * @Author wangjun
	 */
	@Override
	public void getBankOpenAccountForCrmRepair() {
		String url = "http://AM-USER/am-user/bankopen/getBankOpenAccountForCrmRepair";
		restTemplate.getForEntity(url, String.class).getBody();
	}

	/**
	 * 获取前一天注册的用户
	 *
	 * @return
	 */
	@Override
	public List<UserVO> selectBeforeDayRegisterUserList() {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/selectBeforeDayRegisterUserList", UserResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据用户ID查询PC推广渠道
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UtmRegVO selectUtmRegByUserId(Integer userId) {
		UtmRegResponse response = restTemplate
				.getForEntity(userService + "/user/selectUtmRegByUserId/" + userId, UtmRegResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户Id查询APP推广渠道
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public AppUtmRegVO selectAppUtmRegByUserId(Integer userId) {
		AppUtmRegResponse response = restTemplate
				.getForEntity(userService + "/user/selectAppUtmRegByUserId/" + userId, AppUtmRegResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户ID查询用户推荐人信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public SpreadsUserVO selectSpreadsUserByUserId(Integer userId) {
		SpreadsUserResponse response = restTemplate
				.getForEntity(userService + "/user/selectSpreadsUserByUserId/" + userId, SpreadsUserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户ID查询用户画像
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserPortraitVO selectUserPortraitByUserId(Integer userId) {
		UserPortraitResponse response = restTemplate
				.getForEntity(userService + "/user/selectUserPortraitByUserId/" + userId, UserPortraitResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}


	/**
	 * 生成电销数据
	 *
	 * @param request
	 */
	@Override
	public void generateElectricitySalesData(ElectricitySalesDataPushListRequest request) {
		restTemplate
				.put(userService + "/electricitySalesDataPushList/generateElectricitySalesData", request);
	}
}
