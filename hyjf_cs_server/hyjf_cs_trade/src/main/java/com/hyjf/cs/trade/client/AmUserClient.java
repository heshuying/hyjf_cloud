package com.hyjf.cs.trade.client;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.ScreenDataResponse;
import com.hyjf.am.response.user.UserCustomerTaskConfigResponse;
import com.hyjf.am.response.user.UserScreenConfigResponse;
import com.hyjf.am.resquest.admin.UserLargeScreenRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankSmsLogRequest;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;
import com.hyjf.am.resquest.user.LoanSubjectCertificateAuthorityRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.hgreportdata.cert.CertSendUserVO;
import com.hyjf.am.vo.hgreportdata.cert.CertUserVO;
import com.hyjf.am.vo.trade.bifa.BifaIndexUserInfoBeanVO;
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
	UserVO getUserByAccountId(String accountId);

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

	/**
	 * 判断是否51老客户
	 * @param userId
	 * @return
	 */
    boolean checkIs51UserCanInvest(Integer userId);


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


	UtmPlatVO selectUtmPlatByUserId(Integer userId);

	/**
	 * 获取用户信息
	 *
	 * @param truename
	 * @param idcard
	 * @return
	 */
	UserInfoVO selectUserInfoByNameAndCard(String truename, String idcard);

	/**
	 * 获取用户的数据
	 *
	 * @param userId
	 * @return
	 */
	UserVO selectUsersById(Integer userId);

	/**
	 * 通过用户名获得用户的详细信息
	 *
	 * @param userName
	 * @return
	 */
	UserVO selectUserInfoByUsername(String userName);

	/**
	 * 通过用户id获得用户真实姓名和身份证号
	 *
	 * @param userId
	 * @return
	 */
	UserInfoVO selectUserInfoByUserId(Integer userId);

	/**
	 * 查看用户对应的企业编号
	 *
	 * @param userName
	 * @return
	 */
	CorpOpenAccountRecordVO selectUserBusiNameByUsername(String userName);



	/**
	 * 根据用户信息获取部门信息
	 * @param userId
	 * @return
	 */
	List<UserInfoCustomizeVO> queryDepartmentInfoByUserId(Integer userId);

	List<AccountBankVO> getAccountBankByUserId(Integer userId);

	/**
	 * 获取用户的汇付客户号
	 * @param userId
	 * @return
	 */
    AccountChinapnrVO getAccountChinapnr(Integer userId);

	/**
	 * 取得银行卡号
	 * @param userId
	 * @param bankId
	 * @return
	 */
	AccountBankVO getBankInfo(Integer userId, int bankId);

	/**
	 * 根据汇付账户查询user_id
	 * @param chinapnrUsrcustid
	 * @return
	 */
	Integer selectUserIdByUsrcustid(Long chinapnrUsrcustid);

	AccountVO getAccount(Integer userId);

	List<VipAuthVO> getVipAuthList(int vipId);

	UserVO getUser(String userName);

	UserInfoVO getUserInfo(Integer userId);

	int updateBankCardPayAllianceCode(BankCardVO updateBankCard);

	BankCardVO queryUserCardValid(String userId, String cardNo);

    HjhUserAuthVO getHjhUserAuthByUserId(Integer userId);

	BankOpenAccountVO selectBankOpenAccountByAccountId(String accountId);

	UserInfoVO findUserInfoById(Integer userId);

	BankCardVO getBankCardByUserId(Integer userId);

	CorpOpenAccountRecordVO getCorpOpenAccountRecord(Integer userId);

	int updateBankCard(BankCardVO bankCardVO);

	BankCardVO getBankCardById(Integer bankId);

    BankCardVO selectBankCardByUserIdAndCardNo(BankCardRequest request);

	int updateUserCard(BankCardRequest bankCardRequest);

	String selectBankSmsLog(BankSmsLogRequest bankSmsLogRequest);

	boolean updateBankSmsLog(BankSmsLogRequest request);

	/**
	 * 保存短信信息
	 * @param mobile
	 * @param checkCode
	 * @param validCodeType
	 * @param status
	 * @param platform
	 * @return
	 */
	int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform);

	/**
	 * 根据UtmId查询推广渠道
	 *
	 * @param utmId
	 * @return
	 */
	UtmVO selectUtmByUtmId(Integer utmId);

	/**
	 * 根据sourceId查询UtmPlat
	 *
	 * @param sourceId
	 * @return
	 */
	UtmPlatVO selectUtmPlatBySourceId(Integer sourceId);


	/**
	 * 根据用户Id查询用户部门信息
	 *
	 * @param userId
	 * @return
	 */
	UserDepartmentInfoCustomizeVO queryUserDepartmentInfoByUserId(Integer userId);

	/**
	 * 根据用户ID查询用户登录信息
	 *
	 * @param userId
	 * @return
	 */
	UserLoginLogVO getUserLoginById(Integer userId);

	/**
	 * 返回用户测评信息
	 *
	 * @param userId
	 * @return
	 */
	public UserEvalationResultVO selectUserEvalationResultByUserId(Integer userId);


	/**
	 * 根据userId查询用户渠道信息
	 *
	 * @param userId
	 * @return
	 */
	AppUtmRegVO getAppChannelStatisticsDetailByUserId(Integer userId);

	/**
	 * 根据userId查询需要上报的用户信息
	 * @param userId
	 * @return
	 */
    CertSendUserVO getCertSendUserByUserId(int userId);

	/**
	 * 插入国家互联网应急中心已上送用户表
	 * @param certUser
	 */
	Integer insertCertUser(CertUserVO certUser);

	/**
	 * 修改国家互联网应急中心已上送用户表
	 * @param certUser
	 */
	Integer updateCertUser(CertUserVO certUser);

	/**
	 * 批量插入上报记录
	 * @param certUsers
	 */
	Integer insertCertUserByList(List<CertUserVO> certUsers);

	/**
	 * 根据borrowNid userId查询
	 * @param userId
	 * @param borrowNid
	 * @return
	 */
	CertUserVO getCertUserByUserIdBorrowNid(int userId, String borrowNid);

	/**
	 * 查询是否已经上送了
	 * @param userId
	 * @return
	 */
    CertUserVO getCertUserByUserId(Integer userId);

	/**
	 * 根据用户哈希值查询是否已经上报过了
	 * @param userIdcardHash
	 * @return
	 */
	CertUserVO getCertUserByUserIdcardHash(String userIdcardHash);

	/**
	 * 根据用户ID查询上报的用户
	 * @param userId
	 * @return
	 */
	List<CertUserVO> getCertUsersByUserId(int userId);

	/**
	 * 获取最近七天开户的用户
	 * @param startDate
	 * @param endDate
	 * @return
	 */
    List<BifaIndexUserInfoBeanVO> getBankOpenedAccountUsers(Integer startDate, Integer endDate);

	/**
	 * 获取借款人信息
	 * @param userId
	 * @return
	 */
    BifaIndexUserInfoBeanVO getBifaIndexUserInfo(Integer userId);


	/**
	 * 查询用户归属那个运营部
	 * @param screenDataBean
	 * @return
	 */
	ScreenDataResponse findUserGroup(ScreenDataBean screenDataBean);

	/**
	 * 大屏幕运营部配置获取
	 * @return
	 */
	UserScreenConfigResponse getScreenConfig(UserLargeScreenRequest request);
	/**
	 * 坐席月任务配置
	 * @return
	 */
	UserCustomerTaskConfigResponse getCustomerTaskConfig(UserLargeScreenRequest request);
}
