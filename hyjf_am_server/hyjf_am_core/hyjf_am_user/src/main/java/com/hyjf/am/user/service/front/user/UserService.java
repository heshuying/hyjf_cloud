package com.hyjf.am.user.service.front.user;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.bifa.BifaIndexUserInfoBean;
import com.hyjf.am.user.dao.model.customize.UserUtmInfoCustomize;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserDepartmentInfoCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version UserService, v0.1 2018/1/21 22:42
 */
public interface UserService extends BaseService {
	/**
	 * 注册
	 * @param userRequest
	 * @return
	 * @throws ServiceException
	 */
	User register(RegisterUserRequest userRequest) throws MQException;

	/**
	 * 生成唯一用户id
	 *
	 * @param mobile
	 * @return
	 */
	String generateUniqueUsername(String mobile);

	/**
	 * 根据手机号查找用户信息
	 *
	 * @param mobile
	 * @return
	 */
	User findUserByMobile(String mobile);

	/**
	 * 根据username或者mobiel查询用户
	 * @param condition
	 * @return
	 */
	User findUserByUsernameOrMobile(String condition);

    User updateUserByUsernameOrMobile(String condition);

    /**
     * 查询推荐人是否存在
     * @param reffer
     * @return
     */
    User findUserByRecommendName(String reffer);

    /**
	 * 根据userId 查询推荐人
	 *
	 * @param userId
	 * @return
	 */
	User findReffer(Integer userId);

	void updateLoginUser(int userId, String ip);

	UtmPlat selectUtmPlatByUtmId(String utmId);

	HjhUserAuth getHjhUserAuthByUserId(Integer userId);

    HjhUserAuth getHjhUserAuth(Integer userId);

    void insertSelective(HjhUserAuthLog hjhUserAuthLog);

	HjhUserAuthLog selectByExample(String orderId);

	int updateByPrimaryKeySelective(HjhUserAuthLog record);

	int insertSelective(HjhUserAuth record);

	int updateByPrimaryKeySelective(HjhUserAuth record);

    void updateUserAuthInves(BankRequest bean);

	/**
	 * 通过当前用户ID 查询用户所在一级分部,从而关联用户所属渠道
	 * @param userId
	 * @return
	 * @Author : huanghui
	 */
	UserUtmInfoCustomize getUserUtmInfo(Integer userId);

	/**
	 * 修改用户表By主键
	 * @param record
	 * @return int
	 */
	int updateUserById(User record);

	UserEvalationResult selectUserEvalationResultByUserId(Integer userId);

    void deleteUserEvalationResultByUserId(Integer userId);

    AccountChinapnr getAccountChinapnr(Integer userId);

	int updateUserContact(UsersContractRequest record);

	UserContact selectUserContact(Integer userId);

	boolean checkEmailUsed(String email);

	Integer insertEmailBindLog(UserBindEmailLog log);

	UserBindEmailLog getUserBindEmail(Integer userid);

	Integer updateBindEmail(Integer userId, String email);

    UserLoginLog selectByPrimaryKey(Integer userId);

	/**
	 * 根据垫付机构用户名检索垫付机构用户
	 * @param repayOrgName
	 * @return
	 */
	List<User> selectUserByUsername(String repayOrgName);

	Evalation getEvalationByCountScore(short countScore);

    UserEvalationResult insertUserEvalationResult(Integer userId,String userAnswer,Integer countScore,String behaviorId);

    UserEvalationBehavior insertUserEvalationBehavior(Integer userId, String behavior);

    Integer updateUserEvalationBehavior(UserEvalationBehavior userEvalationBehavior);

    List<Evalation> getEvalationRecord();

    Evalation getEvalationByEvalationType(String evalationType);

    int isCompAccount(Integer userId);


    /**
     * 根据userId查询推广链接注册
     * @param userId
     * @return
     */
    UtmReg findUtmRegByUserId(Integer userId);

    /**
     * 更新渠道用户首次出借信息
     * @param bean
     * @return
     */
    void updateFirstUtmReg(Map<String,Object> bean);

	/**
	 * 插入vip user
	 * @param para
	 */
	boolean insertVipUserTender(JSONObject para);

	/**
	 * 查询用户出借次数
	 * @param userId
	 * @return
	 */
	Integer selectTenderCount(Integer userId);

	/**
	 *
	 * @param request
	 * @return
	 */
	List<CertificateAuthority> getCertificateAuthorityList(CertificateAuthorityRequest request);

	/**
	 * 借款主体CA认证记录表
	 * @param request
	 * @return
	 */
	List<LoanSubjectCertificateAuthority> getLoanSubjectCertificateAuthorityList(LoanSubjectCertificateAuthorityRequest request);

	/**
	 * 通过userID获得CA认证的客户ID
	 * @param userId
	 * @param code
	 * @return
	 */
	String getCustomerIDByUserID(Integer userId, String code);

	/**
	 * 根据userId查询渠道
	 * @param userId
	 * @return
	 */
    UtmPlat selectUtmPlatByUserId(Integer userId);

    int saveUserEvaluation(UserEvalationResult userEvalationResult);

    /**
     * @Author walter.limeng
     * @Description  根据用户ID和推荐人查询用户
     * @Date 16:45 2018/7/14
     * @Param utmReferrer
     * @Param userId
     * @return
     */
    UserVO getUser(String utmReferrer, String userId);
	/**
	 * 融东风注册
	 * @param userRequest
	 * @return
	 * @throws ServiceException
	 */
    User surongRegister(RegisterUserRequest userRequest)throws MQException;

    /**
     * @Author walter.limeng
     * @Description  根据用户ID查询推荐人
     * @Date 14:00 2018/7/18
     * @Param userId
     * @return
     */
	List<SpreadsUserVO> selectByUserId(String userId);

	/**
	 * 获取用户表总记录数
	 * @return
	 */
	Integer countAll();

	/**
	 * app跳过测评查询信息
	 * @param userId
	 * @param countScore
	 * @return
	 */
    UserEvalationResult skipEvaluate(Integer userId, Integer countScore);

	/**
	 * 通过用户id获得借款人的开户电子账号
	 *
	 * @param userId
	 * @return
	 */
	BankOpenAccount selectBankAccountById(Integer userId);

	/**
	 * 查看用户对应的企业编号
	 *
	 * @param userName
	 * @return
	 */
	CorpOpenAccountRecord selectUserBusiNameByUsername(String userName);

	/**
	 * 修改短信与邮件是否开启状态
	 * @param userId
	 * @param smsOpenStatus
	 * @param emailOpenStatus
	 * @return
	 */
	Integer updateStatusByUserId(Integer userId, String smsOpenStatus, String emailOpenStatus);

	/**
	 * 查询千乐渠道用户
	 * @return
	 */
	List<Integer> getQianleUser(String sourceId);
	/**
	 * 更新ht_hjh_user_auth_log
	 * @param hjhUserAuthLog
	 * @return
	 */
	int updateHjhUserAuthLog(HjhUserAuthLog hjhUserAuthLog);

	int isExistsUser(String condition);

	/**
	 * 通过用户名获得用户的详细信息
	 *
	 * @param userName
	 * @return
	 */
    User selectUserInfoByUsername(String userName);
	/**
	 * 插入密码错误超限用户信息
	 * @author kdl 2018.10.29
	 * @param lockedUserInfoVO
	 * @return
	 */
	public int inserLockedUser(LockedUserInfoVO lockedUserInfoVO);

	/**
	 * 根据用户ID查询用户部门信息
	 *
	 * @param userId
	 * @return
	 */
	UserDepartmentInfoCustomizeVO queryUserDepartmentInfoByUserId(Integer userId);


	/**
	 * 主库查询user
	 * @param userId
	 * @return
	 */
    User updateUserByUserId(Integer userId);

	/**
	 * 获取最近七天开户的用户
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<BifaIndexUserInfoBean> getBankOpenedAccountUsers(Integer startDate, Integer endDate);

	/**
	 * 获取借款人信息
	 * @param userId
	 * @return
	 */
    BifaIndexUserInfoBean getBifaIndexUserInfo(Integer userId);

	/**
	 * 获取用户基本信息
	 * @return
	 * @param userId
	 */
	WebViewUserVO getWebViewUserByUserId(Integer userId);

	/**
	 * 更新用户信息
	 * @param userId
	 * @param ip
	 * @param user
	 */
	void updateUser(int userId, String ip, User user);
}
