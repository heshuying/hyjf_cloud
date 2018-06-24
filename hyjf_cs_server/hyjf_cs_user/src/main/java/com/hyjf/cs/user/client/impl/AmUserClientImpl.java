package com.hyjf.cs.user.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author xiasq
 * @version AmUserClientImpl, v0.1 2018/4/19 12:44
 */

@Service
public class AmUserClientImpl implements AmUserClient {
	private static Logger logger = LoggerFactory.getLogger(AmUserClient.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserVO findUserByMobile(String mobile) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findByMobile/" + mobile, UserResponse.class).getBody();
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
				.getForEntity("http://AM-USER/am-user/user/findReffer/" + reffer, UserResponse.class).getBody();
		if (response != null  && response.getResult() != null) {
			return 1;
		}
		return 0;
	}

	@Override
	public UserVO register(RegisterUserRequest request) {
		UserResponse response = restTemplate
				.postForEntity("http://AM-USER/am-user/user/register", request, UserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

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
	public UserInfoVO findUserInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
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
				.postForEntity("http://AM-USER/am-user/smsCode/save", request, SmsCodeResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getCnt();
		} else {
			throw new RuntimeException("发送验证码失败...");
		}
	}

	@Override
	public int checkMobileCode(String mobile, String verificationCode, String verificationType, String platform,
			Integer searchStatus, Integer updateStatus) {
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(verificationCode);
		request.setVerificationType(verificationType);
		request.setPlatform(platform);
		request.setStatus(searchStatus);
		request.setUpdateStatus(updateStatus);

		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/smsCode/check/", request, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
	}

	@Override
	public UserVO findUserByUserNameOrMobile(String loginUserName) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/findByCondition/" + loginUserName, UserResponse.class)
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
		String url = "http://AM-USER/am-user/user/updateLoginUser/" + userId + "/" + args;
		logger.info("url:{}", url);
		restTemplate.getForEntity(url, String.class);
	}


	@Override
	public HjhUserAuthVO getHjhUserAuthByUserId(Integer userId) {
		HjhUserAuthResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/getHjhUserAuthByUserId/"+userId, HjhUserAuthResponse.class)
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
		restTemplate.put("http://AM-USER/am-user/user/insertLogSelective",hjhUserAuthLog);
	}

	@Override
	public HjhUserAuthLogVO selectByExample(String orderId) {
		HjhUserAuthLogResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/selectByExample/"+orderId, HjhUserAuthLogResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
    public void updateUserAuthInves(BankRequest bean){
		 restTemplate.put("http://AM-USER/am-user/user/updateUserAuthInves", bean) ;
	}

	/**
	 * 根据userId修改
	 * @param user
	 * @return
	 */
	@Override
	public int updateUserById(UserVO user) {
		if(user == null || user.getUserId() == null){
			return 0;
		}
		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/user/updateByUserId", user, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
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
		String url = "http://AM-USER/am-user/user/updatePassWd/" + userId + "/" + oldPW + "/" + newPW;
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
	public UserInfoVO findUsersInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public AccountChinapnrVO getAccountChinapnr(Integer userId) {
		AccountChinapnrResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/getAccountChinapnr/" + userId, AccountChinapnrResponse.class).getBody();
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
				.getForEntity("http://AM-USER/am-user/user/checkEmailUsed/" + email, boolean.class).getBody();
		return result;
	}
	
	/**
	 * 插入绑卡记录
	 * @param bean
	 * @return
	 */
	@Override
	public void insertBindEmailLog(BindEmailLogRequest bean) {
		restTemplate.postForEntity("http://AM-USER/am-user/user/insertBindEmailLog/",  bean, int.class).getBody();
	}
	
	/**
	 * 获取绑定邮箱记录
	 * @param userId
	 * @return
	 */
	@Override
	public BindEmailLogVO getBindEmailLog(Integer userId) {
		BindEmailLogResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/getBindEmailLog/" + userId, BindEmailLogResponse.class).getBody();
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
		restTemplate.postForEntity("http://AM-USER/am-user/user/updateBindEmail/" + bean.getUserId() + "/" + bean.getUserEmail(),  bean, int.class).getBody();
	}
	
	/**
	 * 保存、更新紧急联系人
	 * @param bean
	 * @return
	 */
	@Override
	public int updateUserContract(UsersContractRequest bean) {
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/user/updateUserContract/",  bean, int.class).getBody();
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
				.getForEntity("http://AM-USER/am-user/user/selectUserContact/" + userId, UsersContactResponse.class).getBody();
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
		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/user/updateByUserId", requestBean, Integer.class)
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
				.getForEntity("http://AM-USER/am-user/userInfo/findByIdNo/" + idNo, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}


	@Override
	public UserLoginLogVO getUserLoginById(Integer userId){
		UserLoginLogResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/getUserLoginById/" + userId, UserLoginLogResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public BankOpenAccountVO selectById(int userId) {
		BankOpenAccountResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/bankopen/selectById/" + userId, BankOpenAccountResponse.class).getBody();
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
				.postForEntity("http://AM-USER/am-user/card/updateBankSmsLog", request, Boolean.class).getBody();
		return result;
	}


	@Override
	public String selectBankSmsLog(BankSmsLogRequest request) {
		String result = restTemplate
				.postForEntity("http://AM-USER/am-user/card/selectBankSmsLog", request, String.class).getBody();
		return result;
	}

	@Override
	public EvalationVO getEvalationByCountScore(short countScore) {
		EvalationResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/getEvalationByCountScore/" + countScore, EvalationResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<EvalationVO> getEvalationRecord() {
		EvalationResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/getEvalationRecord", EvalationResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}


	@Override
	public UserEvalationResultVO insertUserEvalationResult(UserEvalationRequest userEvalationRequest) {
		UserEvalationResultResponse response = restTemplate.postForEntity("http://AM-USER/am-user/user/insertUserEvalationResult",userEvalationRequest,UserEvalationResultResponse.class).getBody();
		if(null!=response){
			return   response.getResult();
		}
		return  null;
	}

	@Override
	public UserInfoVO findUserInfoByCardNo(String cradNo) {
		UserInfoResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/bankopen/findByCardId/" + cradNo, UserInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}


	@Override
	public int updateUserAccountLog(BankOpenRequest request) {
		Integer result = restTemplate
				.postForEntity("http://AM-USER/am-user/bankopen/updateUserAccountLog", request, Integer.class).getBody();
		if (result != null ) {
			return result;
		}
		return 0;
	}

	@Override
	public BankOpenAccountVO selectByAccountId(String accountId) {
		BankOpenAccountResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/bankopen/selectByAccountId/" + accountId, BankOpenAccountResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId) {
		UserEvalationResultResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/selectUserEvalationResultByUserId/" + userId, UserEvalationResultResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}


	@Override
	public void deleteUserEvalationResultByUserId(Integer userId) {
		restTemplate.put("http://AM-USER/am-user/user/deleteUserEvalationResultByUserId/", userId);
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
				.postForEntity("http://AM-USER/am-user/bankopen/updateUserAccountLogStatus", request, Integer.class).getBody();
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

		Integer result = restTemplate
				.postForEntity("http://AM-USER/am-user/bankopen/updateUserAccount", request, Integer.class).getBody();
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
				.postForEntity("http://AM-USER/am-user/bankopen/saveCardNoToBank", request, Integer.class).getBody();
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
				.getForEntity("http://AM-USER//am-user/card/queryUserCardValid/" + userId + "/" + cardNo, BankCardResponse.class).getBody();
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
				.getForEntity("http://AM-USER//am-user/card/countUserCardValid/" + userId, Integer.class).getBody();
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
				.getForEntity("http://AM-USER//am-user/card/deleteUserCardByUserId/" + userId, Integer.class).getBody();
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
				.getForEntity("http://AM-USER//am-user/card/deleteUserCardByCardNo/" + cardNo, Integer.class).getBody();
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
				.postForEntity("http://AM-USER/am-user/card/insertUserCard", request, Integer.class).getBody();
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
				.postForEntity("http://AM-USER/am-user/card/updateUserCard", request, Integer.class).getBody();
		return result;
	}

	/**
	 * 保存绑卡日志
	 */
	@Override
	public int insertBindCardLog(BankCardLogRequest request) {
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/card/insertBindCardLog", request, Integer.class).getBody();
		return result;
	}

	@Override
	public CorpOpenAccountRecordVO getCorpOpenAccountRecord(int userId) {
		CorpOpenAccountRecordResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/bankopen/getCorpOpenAccountRecord/" + userId, CorpOpenAccountRecordResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<BankCardVO> getBankOpenAccountById(UserVO user) {
		BankCardResponse bankCardResponse = restTemplate
				.getForEntity("http://AM-USER/am-user/callcenter/getTiedCardForBank/" + user.getUserId(), BankCardResponse.class)
				.getBody();
		if (bankCardResponse != null) {
			return bankCardResponse.getResultList();
		}
		return null;
	}

	@Override
	public int isCompAccount(Integer userId) {
		int result = restTemplate
				.getForEntity("http://AM-USER//am-user/user/isCompAccount/" + userId, Integer.class).getBody();
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
				.getForEntity("http://AM-USER//am-user/bankopen/getBankOpenAccountFiledMess/" + logOrdId, String.class).getBody();
		return mess;
	}

	@Override
	public UtmPlatVO selectUtmPlatByUtmId(String utmId){
		UtmPlatResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/selectUtmPlatByUtmId/" + utmId,UtmPlatResponse.class)
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
		String url = "http://AM-USER//am-user/invite/myInviteList";
		MyInviteListResponse response = restTemplate.postForEntity(url,requestBean,MyInviteListResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
}
