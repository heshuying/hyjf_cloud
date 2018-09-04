package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.trade.BankCardResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.trade.MyBestCouponListResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;
import com.hyjf.am.resquest.user.LoanSubjectCertificateAuthorityRequest;
import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.AmUserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
	public static final String urlBase = "http://AM-USER/am-user/";

	@Autowired
	private RestTemplate restTemplate;
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
				.getForEntity(urlBase + "corpOpenAccountRecord/findByUserId/" + userId, CorpOpenAccountRecordResponse.class).getBody();
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
	 * @Description 根据userId查询开户信息
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 15:32
	 */
	@Override
	public BankOpenAccountVO selectBankAccountById(Integer userId) {
		String url = urlBase + "bankopen/selectById/" + userId;
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
		String url = urlBase + "user/findReffer/" + userId;
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
		Integer response = restTemplate.postForEntity(url,requestBean,Integer.class).getBody();
		return response;
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

	/**
	 * 获取用户投资数量
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public int countNewUserTotal(Integer userId) {
		Integer result = restTemplate
				.getForEntity(urlBase + "user/countNewUserTotal/" + userId,  Integer.class).getBody();
		if (result != null) {
			return result;
		}
		return 0;
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

		Integer result = restTemplate.postForEntity("http://AM-USER/am-user/smsCode/check/", request, Integer.class)
				.getBody();
		if (result == null) {
			return 0;
		}
		return result;
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
    public BestCouponListVO selectBestCoupon(MyCouponListRequest request) {
        MyBestCouponListResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/coupon/myBestCouponList", request,MyBestCouponListResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public Integer countAvaliableCoupon(MyCouponListRequest request) {
        MyBestCouponListResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/coupon/countAvaliableCoupon",request, MyBestCouponListResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCouponCount();
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
		String url = "http://AM-USER/am-user/userInfo/queryDepartmentInfoByUserId/" + userId;
		UserInfoListCustomizeReponse response = restTemplate.getForEntity(url, UserInfoListCustomizeReponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}
}
