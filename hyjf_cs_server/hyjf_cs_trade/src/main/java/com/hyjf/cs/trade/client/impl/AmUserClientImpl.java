package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.response.bifa.BifaIndexUserInfoBeanResponse;
import com.hyjf.am.response.trade.BankCardResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.trade.ScreenDataResponse;
import com.hyjf.am.response.trade.account.AccountResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.hgreportdata.cert.CertSendUserVO;
import com.hyjf.am.vo.hgreportdata.cert.CertUserVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.bifa.BifaIndexUserInfoBeanVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.util.ReflectUtils;
import com.hyjf.cs.trade.client.AmUserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date  
 */

@Cilent
public class AmUserClientImpl implements AmUserClient {
	private static Logger logger = LoggerFactory.getLogger(AmUserClient.class);
	public static final String urlBase = "http://AM-USER/am-user/";

	@Autowired
	private RestTemplate restTemplate;
	@Value("${am.user.service.name}")
	private String userService;
	@Override
	public UserVO findUserById(int userId) {
		UserResponse response = restTemplate
				.getForEntity(urlBase + "user/findById/" + userId, UserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
	@Override
	public UserVO getUserByAccountId(String accountId){
		UserResponse response = restTemplate.getForEntity(urlBase+"user/getby_accountid/" + accountId, UserResponse.class).getBody();
		if (Response.isSuccess(response)) {
			return response.getResult();
		}
		return null;
	}
	@Override
	public UserInfoVO findUsersInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity(urlBase + "userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(Integer userId) {
		CorpOpenAccountRecordResponse response = restTemplate
				.getForEntity(urlBase + "bankopen/getCorpOpenAccountRecord/" + userId, CorpOpenAccountRecordResponse.class).getBody();
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
				.getForEntity(urlBase + "user/getHjhUserAuthByUserId/" + userId, HjhUserAuthResponse.class).getBody();
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
		String url = urlBase + "user/findReffer/" + userId;
		UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
		if (Response.isSuccess(response)) {
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
		String url = urlBase + "userInfo/findUserCrmInfoByUserId/" + userId;
		UserInfoCrmResponse response = restTemplate.getForEntity(url, UserInfoCrmResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}


	@Override
	public Integer selectMyInviteCount(MyInviteListRequest requestBean){
		String url = urlBase + "invite/myInviteCount";
		IntegerResponse response = restTemplate.postForEntity(url,requestBean,IntegerResponse.class).getBody();
		return response.getResultInt();
	}

	/**
	 * 判断是否51老客户
	 * @param userId
	 * @return
	 */
	@Override
	public boolean checkIs51UserCanInvest(Integer userId) {
		UserInfoResponse response = restTemplate
				.getForEntity(urlBase + "userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null) {
			Integer is51 = response.getResult().getIs51();
			if (is51 != null && is51 == 1) {
				return true;
			}
		}

		return false;
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
		IntegerResponse result = restTemplate
				.postForEntity("http://AM-USER/am-user/user/updateByUserId", user, IntegerResponse.class).getBody();
		if (result != null) {
			return result.getResultInt() == 0 ? false : true;
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

	@Override
	public List<CertificateAuthorityVO> getCertificateAuthorityList(CertificateAuthorityRequest request) {
		String url ="http://AM-USER/am-user/user/getCertificateAuthorityList";
		CertificateAuthorityResponse response = restTemplate.postForEntity(url,request,CertificateAuthorityResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<LoanSubjectCertificateAuthorityVO> getLoanSubjectCertificateAuthorityList(
			LoanSubjectCertificateAuthorityRequest request1) {
		String url = "http://AM-USER/am-user/user/getLoanSubjectCertificateAuthorityList";
		LoanSubjectCertificateAuthorityResponse response = restTemplate.postForEntity(url,request1,LoanSubjectCertificateAuthorityResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 通过userID获得CA认证的客户ID
	 */
	@Override
	public String getCustomerIDByUserID(Integer userId, String code) {
		String url ="http://AM-USER/am-user/user/getCustomerIDByUserID/"+userId+"/"+code;
		return restTemplate.getForEntity(url, String.class).getBody();
	}

	/**
	 * 获取渠道信息
	 *
	 * @param utmId
	 * @return
	 */
	@Override
	public UtmPlatVO selectUtmPlatByUtmId(Integer utmId) {
		UtmPlatResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/selectUtmPlatByUtmId/" + utmId,UtmPlatResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 只校验验证码  不修改状态
	 *
	 * @param mobile
	 * @param code
	 * @param verificationType
	 * @param platform
	 * @param ckcodeYiyan
	 * @param ckcodeYiyan1
	 * @return
	 */
	@Override
	public int onlyCheckMobileCode(String mobile, String code, String verificationType, Integer platform, Integer ckcodeYiyan, Integer ckcodeYiyan1) {
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(code);
		request.setVerificationType(verificationType);
		request.setPlatform(platform+"");
		request.setStatus(ckcodeYiyan);
		request.setUpdateStatus(ckcodeYiyan1);

		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/smsCode/only_check/", request, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
	}

	/**
	 * 校验验证码是否正确  并修改状态
	 *
	 * @param mobile
	 * @param telcode
	 * @param paramTplZhuce
	 * @param platform
	 * @param ckcodeYiyan
	 * @param ckcodeYiyan1
	 * @return
	 */
	@Override
	public int checkMobileCode(String mobile, String telcode, String paramTplZhuce, Integer platform, Integer ckcodeYiyan, Integer ckcodeYiyan1) {
		SmsCodeRequest request = new SmsCodeRequest();
		request.setMobile(mobile);
		request.setVerificationCode(telcode);
		request.setVerificationType(paramTplZhuce);
		request.setPlatform(platform+"");
		request.setStatus(ckcodeYiyan);
		request.setUpdateStatus(ckcodeYiyan1);

		IntegerResponse response = restTemplate.postForEntity("http://AM-USER/am-user/smsCode/check/", request, IntegerResponse.class)
				.getBody();
		if (Response.isSuccess(response)) {
			return response.getResultInt();
		}
		return 0;
	}

	/**
	 * 更新CertificateAuthorityVO
	 * @auth sunpeikai
	 * @param certificateAuthorityVO 更新参数
	 * @return
	 */
	@Override
	public int updateCertificateAuthority(CertificateAuthorityVO certificateAuthorityVO) {
		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/certificateauthority/updatecertificateauthority", certificateAuthorityVO, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
	}

    @Override
    public String getChannelNameByUserId(int userId) {
		UtmResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/channel/getchannelnamebyuserd/" + userId,UtmResponse.class)
				.getBody();
		if (response != null) {
			return response.getChannelName();
		}
        return null;
    }

	/**
	 * 插入certificateAuthorityVO数据
	 * @auth sunpeikai
	 * @param certificateAuthorityVO 参数
	 * @return
	 */
	@Override
	public int insertCertificateAuthority(CertificateAuthorityVO certificateAuthorityVO) {
		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/certificateauthority/insertcertificateauthority", certificateAuthorityVO, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
	}
	/**
	 * 根据用户Id,银行卡号检索用户银行卡信息
	 * @param
	 * @param userId
	 * @return
	 */
	@Override
	public BankCardVO selectBankCardByUserId(Integer userId) {
		com.hyjf.am.response.trade.BankCardResponse response = restTemplate
				.getForEntity(urlBase +"bankCard/getBankCard/" + userId, BankCardResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}


	@Override
	public BankCardVO getBankCardByCardNo(Integer userId, String cardNo){
		BankCardResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/bankCard/getBankCard/" + userId+"/"+cardNo, BankCardResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}


    @Override
    public List<SpreadsUserVO> selectByUserId(Integer userId) {
        SpreadsUserResponse response = restTemplate
                .getForEntity("http://AM-USER//am-user/user/selectspreadsuserbyuserid/" + userId,SpreadsUserResponse.class)
                .getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

	@Override
	public UserVO findUserByMobile(String mobile) {
		UserResponse response = restTemplate
				.getForEntity("http://AM-USER//am-user/user/findByMobile/" + mobile, UserResponse.class).getBody();
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
        com.hyjf.am.response.user.BankCardResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/bankopen/selectBankCardByUserIdAndStatus/" + userId+"/"+status, com.hyjf.am.response.user.BankCardResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

	@Override
	public UtmPlatVO selectUtmPlatByUserId(Integer userId) {
		UtmPlatResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/user/selectUtmPlatByUserId/" + userId,UtmPlatResponse.class)
				.getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查询用户信息（user表）
	 *
	 * @param truename
	 * @param idcard
	 * @return
	 */
	@Override
	public UserInfoVO selectUserInfoByNameAndCard(String truename, String idcard) {
		String url = "http://AM-USER/am-user/userInfo/selectUserInfoByNameAndCard/" + truename + "/" + idcard;
		UserInfoResponse response = restTemplate.getForEntity(url, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查询用户信息（userinfo表）
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserVO selectUsersById(Integer userId) {
		String url = "http://AM-USER/am-user/user/findById/" + userId;
		UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 通过用户名获得用户的详细信息
	 *
	 * @param userName
	 * @return
	 */
	@Override
	public UserVO selectUserInfoByUsername(String userName) {
		String url = "http://AM-USER/am-user/user/selectUserInfoByUsername/" + userName;
		UserResponse response = restTemplate.getForEntity(url, UserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 通过用户id获得用户真实姓名和身份证号
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfoVO selectUserInfoByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/userInfo/selectUserInfoByUserId/" + userId;
		UserInfoResponse response = restTemplate.getForEntity(url, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查看用户对应的企业编号
	 *
	 * @param userName
	 * @return
	 */
	@Override
	public CorpOpenAccountRecordVO selectUserBusiNameByUsername(String userName) {
		String url = "http://AM-USER/am-user/user/selectUserBusiNameByUsername/" + userName;
		CorpOpenAccountRecordResponse response = restTemplate.getForEntity(url, CorpOpenAccountRecordResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<UserInfoCustomizeVO> queryDepartmentInfoByUserId(Integer userId) {
		if(userId != null){
			String url = "http://AM-USER/am-user/userInfo/queryDepartmentInfoByUserId/" + userId;
			UserInfoListCustomizeReponse response = restTemplate.getForEntity(url, UserInfoListCustomizeReponse.class).getBody();
			if (response != null) {
				return response.getResult();
			}
		}
		return null;
	}
	/**
	 * 根据用户id获取银行卡信息
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @return
	 */
	@Override
	public List<AccountBankVO> getAccountBankByUserId(Integer userId) {
		String url = "http://AM-USER/am-user/accountbank/getAccountBankByUserId/" + userId;
		AccountBankResponse response = restTemplate.getForEntity(url, AccountBankResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
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

	@Override
	public AccountBankVO getBankInfo(Integer userId, int bankId) {
		String url = "http://AM-USER/am-user/accountbank/getBankInfo/" + userId+"/"+bankId;
		AccountBankResponse response = restTemplate.getForEntity(url, AccountBankResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public Integer selectUserIdByUsrcustid(Long chinapnrUsrcustid) {
		IntegerResponse response = restTemplate.getForEntity("http://AM-USER/am-user/chinapnr/selectUserIdByUsrcustid/"+chinapnrUsrcustid, IntegerResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultInt();
		}
		return 0;
	}

	/**
	 * 获取用户account信息
	 * @param userId
	 * @return
	 */
	@Override
	public AccountVO getAccount(Integer userId) {
        AccountResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/trade/getAccount/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<VipAuthVO> getVipAuthList(int vipId) {
        String url = urlBase + "vipauth/getvipauthlist/" + vipId;
        VipAuthResponse response = restTemplate.getForEntity(url, VipAuthResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
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
		logger.info(ReflectUtils.getSuperiorClass(3));
		String url = urlBase + "bankopen/selectById/" + userId;
		BankOpenAccountResponse response = restTemplate.getForEntity(url, BankOpenAccountResponse.class).getBody();
		if (response != null) {
			return response.getResult();
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
    public UserVO getUser(String userName) {
        UserResponse response = restTemplate.getForEntity("http://AM-USER/am-user/user/findByCondition/"+userName,UserResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public UserInfoVO getUserInfo(Integer userId) {
        UserInfoResponse response = restTemplate.getForEntity("http://AM-USER/am-user/userInfo/findById/"+userId,UserInfoResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

	@Override
	public int updateBankCardPayAllianceCode(BankCardVO updateBankCard) {
		int result = restTemplate
				.postForEntity("http://AM-USER/am-user/card/updateBankCardPayAllianceCode", updateBankCard, Integer.class).getBody();
		return result;
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
	public UserInfoVO findUserInfoById(Integer userId) {
		UserInfoResponse response = restTemplate
				.getForEntity(userService+"/userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
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
		com.hyjf.am.response.user.BankCardResponse response = restTemplate
				.getForEntity(url, com.hyjf.am.response.user.BankCardResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public CorpOpenAccountRecordVO getCorpOpenAccountRecord(Integer userId) {
		CorpOpenAccountRecordResponse response = restTemplate
				.getForEntity(userService+"/bankopen/getCorpOpenAccountRecord/" + userId, CorpOpenAccountRecordResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 更新银行卡信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public int updateBankCard(BankCardVO bankCardVO) {
		String url = userService + "/bankCard/updateBankCard";
		IntegerResponse response = restTemplate.postForEntity(url,bankCardVO,IntegerResponse.class).getBody();
		if(Response.isSuccess(response)){
			response.getResultInt();
		}
		return 0;
	}

	/**
	 * 根据主键查询银行卡信息
	 * @auth sunpeikai
	 * @param id 主键
	 * @return
	 */
	@Override
	public BankCardVO getBankCardById(Integer id) {
		String url = userService + "/bankCard/getBankCardById/" + id;
		com.hyjf.am.response.user.BankCardResponse response = restTemplate.getForEntity(url, com.hyjf.am.response.user.BankCardResponse.class).getBody();
		if(Response.isSuccess(response)){
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户id和银行卡号查询银行卡信息
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public BankCardVO selectBankCardByUserIdAndCardNo(BankCardRequest request) {
		String url = userService + "/bankCard/selectBankCardByUserIdAndCardNo";
		com.hyjf.am.response.user.BankCardResponse response = restTemplate.postForEntity(url,request, com.hyjf.am.response.user.BankCardResponse.class).getBody();
		if(Response.isSuccess(response)){
			return response.getResult();
		}
		return null;
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

	@Override
	public String selectBankSmsLog(BankSmsLogRequest request) {
		String result = restTemplate
				.postForEntity(userService+"/card/selectBankSmsLog", request, String.class).getBody();
		return result;
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

	/**
	 * 根据UtmId查询推广渠道
	 *
	 * @param utmId
	 * @return
	 */
	@Override
	public UtmVO selectUtmByUtmId(Integer utmId) {
		UtmRequest request = new UtmRequest();
		request.setUtmId(utmId);
		UtmVOResponse response = restTemplate
				.postForEntity(userService + "/utm/selectUtmByUtmId", request, UtmVOResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据sourceId查询UtmPlat
	 *
	 * @param sourceId
	 * @return
	 */
	@Override
	public UtmPlatVO selectUtmPlatBySourceId(Integer sourceId) {
		UtmPlatRequest request = new UtmPlatRequest();
		request.setSourceId(sourceId);
		UtmPlatResponse response = restTemplate
				.postForEntity(userService + "/utm/selectUtmPlatBySourceId", request, UtmPlatResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}


	/**
	 * 根据用户ID查询用户部门信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserDepartmentInfoCustomizeVO queryUserDepartmentInfoByUserId(Integer userId) {
		UserDepartmentInfoCustomizeRequest request = new UserDepartmentInfoCustomizeRequest();
		request.setUserId(userId);
		UserDepartmentInfoCustomizeResponse response = restTemplate
				.postForEntity(userService + "/user/queryUserDepartmentInfoByUserId", request, UserDepartmentInfoCustomizeResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户ID查询用户登录信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserLoginLogVO getUserLoginById(Integer userId) {
		UserLoginLogResponse response = restTemplate
				.getForEntity(userService + "/user/getUserLoginById/" + userId, UserLoginLogResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 返回用户测评信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId) {
		UserEvalationResultResponse response = restTemplate
				.getForEntity(userService + "/evaluationManager/selectEvaluationDetailById/" + userId, UserEvalationResultResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public AppUtmRegVO getAppChannelStatisticsDetailByUserId(Integer userId) {
		AppUtmRegResponse response = restTemplate.getForEntity(
				"http://AM-USER/am-user/app_utm_reg/findByUserId/" + userId,
				AppUtmRegResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据userId查询需要上报的用户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public CertSendUserVO getCertSendUserByUserId(int userId) {
		CertSendUserResponse response = restTemplate.getForEntity(
				userService+"/certUser/getCertSendUserByUserId/" + userId,
				CertSendUserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 插入国家互联网应急中心已上送用户表
	 *
	 * @param certUser
	 */
	@Override
	public Integer insertCertUser(CertUserVO certUser) {
		IntegerResponse response = restTemplate
				.postForEntity(userService+"/certUser/insertCertUser", certUser, IntegerResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getResultInt())) {
			return response.getResultInt();
		}
		return null;
	}

	/**
	 * 修改国家互联网应急中心已上送用户表
	 *
	 * @param certUser
	 */
	@Override
	public Integer updateCertUser(CertUserVO certUser) {
		IntegerResponse response = restTemplate
				.postForEntity(userService+"/certUser/updateCertUser", certUser, IntegerResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getResultInt())) {
			return response.getResultInt();
		}
		return null;
	}

	/**
	 * 批量插入上报记录  先不迁移
	 *
	 * @param certUsers
	 */
	@Override
	public Integer insertCertUserByList(List<CertUserVO> certUsers) {
		IntegerResponse response = restTemplate
				.postForEntity(userService+"/certUser/insertCertUserByList", certUsers, IntegerResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getResultInt())) {
			return response.getResultInt();
		}
		return null;
	}

	/**
	 * 根据borrowNid userId查询
	 *
	 * @param userId
	 * @param borrowNid
	 * @return
	 */
	@Override
	public CertUserVO getCertUserByUserIdBorrowNid(int userId, String borrowNid) {
		CertUserResponse response = restTemplate.getForEntity(
				userService+"/certUser/getCertUserByUserIdBorrowNid/" + userId+"/"+borrowNid,
				CertUserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 查询是否已经上送了
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public CertUserVO getCertUserByUserId(Integer userId) {
		CertUserResponse response = restTemplate.getForEntity(
				userService+"/certUser/getCertUserByUserId/" + userId ,
				CertUserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户哈希值查询是否已经上报过了
	 *
	 * @param userIdcardHash
	 * @return
	 */
	@Override
	public CertUserVO getCertUserByUserIdcardHash(String userIdcardHash) {
		CertUserResponse response = restTemplate.getForEntity(
				userService+"/certUser/getCertUserByUserIdcardHash/" + userIdcardHash ,
				CertUserResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 根据用户ID查询上报的用户
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<CertUserVO> getCertUsersByUserId(int userId) {
		CertUserResponse response = restTemplate.getForEntity(
				userService+"/certUser/getCertUsersByUserId/" + userId ,
				CertUserResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 获取最近七天开户的用户
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public List<BifaIndexUserInfoBeanVO> getBankOpenedAccountUsers(Integer startDate, Integer endDate) {
		String url = "http://AM-USER/am-user/bifaDataReport/getBankOpenedAccountUsers/"+startDate+"/"+endDate;
		BifaIndexUserInfoBeanResponse response = restTemplate.getForEntity(url,BifaIndexUserInfoBeanResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 获取借款人信息
	 * @param userId
	 * @return
	 */
	@Override
	public BifaIndexUserInfoBeanVO getBifaIndexUserInfo(Integer userId) {
		String url = "http://AM-USER/am-user/bifaDataReport/getBifaIndexUserInfo/"+userId;
		BifaIndexUserInfoBeanResponse response= restTemplate.getForEntity(url,BifaIndexUserInfoBeanResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResult();
		}
		return null;
	}

	@Override
	public ScreenDataResponse findUserGroup(ScreenDataBean screenDataBean) {
		return restTemplate.postForObject("http://AM-USER/am-user/user/getGroup",
				screenDataBean, ScreenDataResponse.class);

	}
}
