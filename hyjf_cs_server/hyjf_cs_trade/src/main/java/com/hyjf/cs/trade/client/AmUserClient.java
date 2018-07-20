package com.hyjf.cs.trade.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;
import com.hyjf.am.resquest.user.LoanSubjectCertificateAuthorityRequest;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.user.*;

import java.util.List;
import java.util.Map;

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

    Integer selectMyInviteCount(MyInviteListRequest requestBean);

    /**
	 *	用户详细信息 add by jijun 20180622
	 * @param userId
	 * @return
	 */
	UserInfoCustomizeVO queryUserInfoCustomizeByUserId(Integer userId);

	/**
	 *
	 * @param userId
	 * @return
	 */
	SpreadsUserVO querySpreadsUsersByUserId(Integer userId);

	/**
	 * 获取员工信息
	 * @param userId
	 * @return
	 */
	EmployeeCustomizeVO selectEmployeeByUserId(Integer userId);

	/**
	 * 修改用户对象
	 * @param user
	 * @return
	 */
    boolean updateByPrimaryKeySelective(UserVO user);

    /**
     * 根据userId查询用户推广链接注册
     * @param userId
     * @return
     */
    UtmRegVO findUtmRegByUserId(Integer userId);

    /**
     * 更新huiyingdai_utm_reg的首投信息
     * @param params
     * @return
     */
    boolean updateFirstUtmReg(Map<String,Object> params);

	List<MyInviteListCustomizeVO> selectMyInviteList(MyInviteListRequest requestBean);

	/**
	 * 判断是否51老客户
	 * @param userId
	 * @return
	 */
    boolean checkIs51UserCanInvest(Integer userId);

    /**
     * 插入VIPuser
     * @param para
     */
	boolean insertVipUserTender(JSONObject para);

	/**
	 * 获取用户投资数量
	 * @param userId
	 * @return
	 */
    int countNewUserTotal(Integer userId);

    /**
     * 根据用户id获取用户CA认证记录表
     * @return
     */
	CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId);

	/**
	 * 用户CA认证记录表
	 * @param request
	 * @return
	 */
	List<CertificateAuthorityVO> getCertificateAuthorityList(CertificateAuthorityRequest request);

	/**
	 * 借款主体CA认证记录表
	 * @param request1
	 * @return
	 */
	List<LoanSubjectCertificateAuthorityVO> getLoanSubjectCertificateAuthorityList(
			LoanSubjectCertificateAuthorityRequest request1);

	/**
	 * 通过userID获得CA认证的客户ID
	 * @param userId
	 * @param code
	 * @return
	 */
	String getCustomerIDByUserID(Integer userId, String code);

	/**
	 * 获取渠道信息
	 * @param utmId
	 * @return
	 */
	UtmPlatVO selectUtmPlatByUtmId(Integer utmId);


	/**
	 * 只校验验证码  不修改状态
	 * @param mobile
	 * @param code
	 * @param verificationType
	 * @param platform
	 * @param ckcodeYiyan
	 * @param ckcodeYiyan1
	 * @return
	 */
    int onlyCheckMobileCode(String mobile, String code, String verificationType, Integer platform, Integer ckcodeYiyan, Integer ckcodeYiyan1);

	/**
	 * 校验验证码是否正确  并修改状态
	 * @param mobile
	 * @param telcode
	 * @param paramTplZhuce
	 * @param platform
	 * @param ckcodeYiyan
	 * @param ckcodeYiyan1
	 * @return
	 */
	int checkMobileCode(String mobile, String telcode, String paramTplZhuce, Integer platform, Integer ckcodeYiyan, Integer ckcodeYiyan1);

	/**
	 * 更新CertificateAuthorityVO
	 * @auth sunpeikai
	 * @param certificateAuthorityVO 更新参数
	 * @return
	 */
	int updateCertificateAuthority(CertificateAuthorityVO certificateAuthorityVO);

	/**
	 * @Author walter.limeng
	 * @Description  根据用户ID获取用户注册时的渠道名称
	 * @Date 16:13 2018/7/16
	 * @Param userId
	 * @return
	 */
    String getChannelNameByUserId(int userId);

    /**
     * 插入certificateAuthorityVO数据
     * @auth sunpeikai
     * @param certificateAuthorityVO 参数
     * @return
     */
    int insertCertificateAuthority(CertificateAuthorityVO certificateAuthorityVO);

	/**
	 * 根据用户Id,银行卡号检索用户银行卡信息
	 * @param
	 * @param userId
	 * @return
	 */
	BankCardVO selectBankCardByUserId(Integer userId);
	/**
	 * 根据用户Id,银行卡号检索用户银行卡信息
	 * @param
	 * @param userId
	 * @return
	 */
	BankCardVO getBankCardByCardNo(Integer userId, String cardNo);

    CouponConfigVO selectCouponConfig(String couponCode);


    BestCouponListVO selectBestCoupon(MyCouponListRequest request);


    Integer countAvaliableCoupon(MyCouponListRequest request);

    /**
     * 查询汇计划最优优惠券
     * @param request
     * @return
     */
    BestCouponListVO selectHJHBestCoupon(MyCouponListRequest request);

    /**
     *
     * @param couponCode
     * @return
     */
    Integer checkCouponSendExcess(String couponCode);
    /**
     * 查询HJH可用优惠券数量
     * @param request
     * @return
     */
    Integer countHJHAvaliableCoupon(MyCouponListRequest request);


    /**
     * @Author walter.limeng
     * @Description  根据用户ID获取推荐人
     * @Date 11:56 2018/7/18
     * @Param userId
     * @return
     */
    List<SpreadsUserVO> selectByUserId(Integer userId);
	/**
	 * 根据手机号查询User
	 * @param mobile
	 * @return
	 */
	UserVO findUserByMobile(String mobile);
    /**
     * 获取银行卡信息
     * @param userId
     * @param status
     * @return
     */
    List<BankCardVO> selectBankCardByUserIdAndStatus(Integer userId,Integer status);
}
