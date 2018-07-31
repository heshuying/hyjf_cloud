package com.hyjf.cs.user.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.AdminBankAccountCheckCustomizeResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.trade.BatchUserPortraitQueryRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author xiasq
 * @version AmUserClientImpl, v0.1 2018/4/19 12:44
 */

@Service
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
				.getForEntity(userService+"/user/findReffer/" + reffer, UserResponse.class).getBody();
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



	@Override
	public List<BankCardVO> selectBankCardByUserIdAndStatus(Integer userId) {
		BankCardResponse response = restTemplate
				.getForEntity(userService+"/bankopen/selectBankCardByUserIdAndStatus/" + userId, BankCardResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
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
	public UserVO findUserById(int userId) {
		UserResponse response = restTemplate
				.getForEntity(userService+"/user/findById/" + userId, UserResponse.class).getBody();
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
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(checkCode);
		request.setVerificationType(validCodeType);
		request.setStatus(status);
		request.setPlatform(platform);
		SmsCodeResponse response = restTemplate
				.postForEntity(userService+"/smsCode/save", request, SmsCodeResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getCnt();
		} else {
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
		Integer result = restTemplate.postForEntity(userService+"/smsCode/check/", request, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
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
	public void updateLoginUser(int userId, String ip) {
		/**
		 * ip version等作为请求一部分的时候，用base64转码
		 */
		String args = "";
		try {
			args = new String(Base64.encodeBase64(ip.getBytes()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new ReturnMessageException(Response.FAIL_MSG);
		}
		String url = userService+"/user/updateLoginUser/" + userId + "/" + args;
		logger.info("url:{}", url);
		restTemplate.getForEntity(url, String.class);
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
		Integer result = restTemplate.postForEntity(userService+"/user/updateByUserId", user, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
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
	
	/**
	 * 校验邮箱是否存在
	 */
	@Override
	public boolean checkEmailUsed(String email) {
		boolean result = restTemplate
				.getForEntity(userService+"/user/checkEmailUsed/" + email, boolean.class).getBody();
		return result;
	}
	
	/**
	 * 插入绑卡记录
	 * @param bean
	 * @return
	 */
	@Override
	public void insertBindEmailLog(BindEmailLogRequest bean) {
		restTemplate.postForEntity(userService+"/user/insertBindEmailLog/",  bean, int.class).getBody();
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
	public void updateBindEmail(BindEmailLogRequest bean) {
		restTemplate.postForEntity(userService+"/user/updateBindEmail/" + bean.getUserId() + "/" + bean.getUserEmail(),  bean, int.class).getBody();
	}
	
	/**
	 * 保存、更新紧急联系人
	 * @param bean
	 * @return
	 */
	@Override
	public int updateUserContract(UsersContractRequest bean) {
		int result = restTemplate
				.postForEntity(userService+"/user/updateUserContract/",  bean, int.class).getBody();
		return result;
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
		Integer result = restTemplate.postForEntity(userService+"/user/updateByUserId", requestBean, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
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
	public EvalationVO getEvalationByCountScore(short countScore) {
		EvalationResponse response = restTemplate
				.getForEntity(userService+"/user/getEvalationByCountScore/" + countScore, EvalationResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<EvalationVO> getEvalationRecord() {
		EvalationResponse response = restTemplate
				.getForEntity(userService+"/user/getEvalationRecord", EvalationResponse.class).getBody();
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
	public UserInfoVO findUserInfoByCardNo(String cradNo) {
		UserInfoResponse response = restTemplate
				.getForEntity(userService+"/bankopen/findByCardId/" + cradNo, UserInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}


	@Override
	public int updateUserAccountLog(BankOpenRequest request) {
		Integer result = restTemplate
				.postForEntity(userService+"/bankopen/updateUserAccountLog", request, Integer.class).getBody();
		if (result != null ) {
			return result;
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


	@Override
	public void deleteUserEvalationResultByUserId(Integer userId) {
		restTemplate.put(userService+"/user/deleteUserEvalationResultByUserId/", userId);
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
	 * 根据cardNo删除银行卡
	 * @param cardNo
	 * @return
	 */
	@Override
	public int deleteUserCardByCardNo(String cardNo) {
		int result = restTemplate
				.getForEntity(userService+"/card/deleteUserCardByCardNo/" + cardNo, Integer.class).getBody();
		return result;
	}

	/**
	 * 保存用户绑定的银行卡
	 * @param request
	 * @return
	 */
	@Override
	public int insertUserCard(BankCardRequest request) {
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
	public List<BankCardVO> getBankOpenAccountById(Integer userId) {
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
		int result = restTemplate
				.getForEntity(userService+"/user/isCompAccount/" + userId, Integer.class).getBody();
		return result;
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
	public int selectMyInviteCount(MyInviteListRequest requestBean) {
		int count = restTemplate
				.postForEntity(userService+"/invite/myInviteCount", requestBean, Integer.class).getBody();
		return count;
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
		return restTemplate.postForEntity(url,accountMobileSynchRequest,boolean.class).getBody();
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
	 * 查询需要更新用户画像的userInfo
	 * @return userInfo
	 * */
	@Override
	public List<UserInfoVO> searchUserInfo() {
		String url = "http://AM-USER/user_batch/portrait/search_user_info_list";
		UserInfoResponse response = restTemplate.getForEntity(url, UserInfoResponse.class).getBody();
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
		int count = restTemplate
				.postForEntity(userService+"/user/saveUserEvaluation", userEvalationResult, Integer.class).getBody();
		return count;
	}

	@Override
	public Integer insertUserEvalationBehavior(Integer userId, String behavior) {
		Integer id = restTemplate.getForEntity(userService+"/user/insertUserEvalationBehavior/"+userId+"/"+behavior, Integer.class).getBody();
		return id;
	}

	@Override
	public Integer updateUserEvaluationBehavior(UserEvalationBehaviorVO userEvalationBehavior) {
		int count = restTemplate
				.postForEntity(userService+"/user/updateUserEvalationBehavior", userEvalationBehavior, Integer.class).getBody();
		return count;
	}

	@Override
	public void clearMobileCode(Integer userId, String sign) {
		restTemplate.getForEntity(userService+"/user/insertUserEvalationBehavior/"+userId+"/"+sign, Integer.class);
	}

	@Override
	public UserVO insertSurongUser(String mobile, String password, String ipAddr, String platform) {
		UserResponse response = restTemplate
				.getForEntity(userService+"/user/insertSurongUser/" + mobile, UserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
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
		String url = userService + "/accountbank/getBankCardByUserId/" + userId;
		AccountBankResponse response = restTemplate
				.getForEntity(url, AccountBankResponse.class).getBody();
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
				.getForEntity(userService+"/user/selectBankOpenAccountByAccountId/" + accountId, BankOpenAccountResponse.class).getBody();
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

	@Override
	public Boolean bindThirdUser(Integer userId, int bindUniqueId, Integer pid) {
		return restTemplate.getForEntity(userService+"/userManager/bindThirdUser/"+userId+"/"+bindUniqueId+"/"+pid, Boolean.class).getBody();
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

	/**
	 * 获取用户表总记录数
	 *
	 * @return
	 */
	@Override
	public Integer countAllUser(){
		UserResponse response = restTemplate.getForEntity("http://AM-USER/am-user/user/countAll",UserResponse.class).getBody();
		if (!Response.isSuccess(response)) {
			return 0;
		}
		return response.getCount();
	}
}
