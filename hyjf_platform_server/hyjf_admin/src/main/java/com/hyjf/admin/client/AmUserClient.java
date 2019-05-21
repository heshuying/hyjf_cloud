package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean;
import com.hyjf.admin.beans.request.SmsCodeRequestBean;
import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.trade.CorpOpenAccountRecordRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AmUserClient, v0.1 2018/4/19 12:44
 */
public interface AmUserClient {

    /**
     * 根据userName查询Account
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    List<UserVO> searchUserByUsername(String userName);

    /**
     * 根据userId查询accountChinapnr开户信息
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    List<AccountChinapnrVO> searchAccountChinapnrByUserId(Integer userId);
    /**
     * 根据userId查询用户信息
     *
     * @param userId 用户id
     * @return
     * @auth sunpeikai
     */
    UserVO searchUserByUserId(Integer userId);

    UserVO getUserByAccountId(String accountId);

    /**
     * 根据手机号获取用户信息
     *
     * @param userName
     * @return
     */
    UserVO getUserByUserName(String userName);

    /**
     * 根据userId查询用户信息
     *
     * @param userId 用户id
     * @return
     * @auth sunpeikai
     */
    UserInfoVO findUsersInfoById(int userId);

    /**
     * 根据userId查询用户
     *
     * @param userId 用户id
     * @return
     * @auth sunpeikai
     */
    UserVO findUserById(final int userId);

    /**
     * 根据userId列表查询user列表
     *
     * @param userIds 用户id列表
     * @return
     * @auth sunpeikai
     */
    List<UserVO> findUserListByUserIds(String userIds);

    /**
     * 利用borrowNid查询出来的异常标的借款人userId查询银行账户
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    BankOpenAccountVO searchBankOpenAccount(Integer userId);

    /**
     * 根据用户名查询自定义用户信息
     *
     * @param userName 用户名
     * @return
     * @auth sunpeikai
     */
    UserInfoCustomizeVO getUserInfoCustomizeByUserName(String userName);

    /**
     * 根据用户id查询自定义用户信息
     *
     * @param userId 用户名
     * @return
     * @auth sunpeikai
     */
    UserInfoCustomizeVO getUserInfoCustomizeByUserId(Integer userId);

    /**
     * 根据用户id查询推荐人表信息
     *
     * @param userId 用户id
     * @return
     * @auth sunpeikai
     */
    SpreadsUserVO searchSpreadsUserByUserId(Integer userId);


    /**
     * 根据用户id查询employee
     *
     * @param userId 用户id
     * @return
     * @auth sunpeikai
     */
    EmployeeCustomizeVO searchEmployeeBuUserId(Integer userId);

    /**
     * 查询自动出借债转异常列表
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    AdminUserAuthListResponse userAuthList(AdminUserAuthListRequest adminUserAuthListRequest);

    /**
     * 同步用户授权状态
     *
     * @param type 1自动出借授权  2债转授权
     * @return
     * @auth sunpeikai
     */
    JSONObject synUserAuth(Integer userId, Integer type);

    BankOpenAccountVO queryBankOpenAccountByUserName(String userName);


    /**
     * 查找用户信息
     *
     * @param request
     * @return
     * @auth nxl
     */
    UserManagerResponse selectUserMemberList(UserManagerRequest request);

    /**
     * 根据筛选条件查找用户总数
     *
     * @param request
     * @return
     * @auth nxl
     */
    int countRecordTotal(UserManagerRequest request);

    /**
     * 根据用户id获取用户详情
     *
     * @param userId
     * @return
     * @auth nxl
     */
    UserManagerDetailVO selectUserDetailById(String userId);

    /**
     * 根据用户id获取测评信息
     *
     * @param userId
     * @return
     * @auth nxl
     */
    UserEvalationResultVO getUserEvalationResult(String userId);

    /**
     * 根据用户id获取开户信息
     *
     * @param userId
     * @return
     * @auth nxl
     */
    UserBankOpenAccountVO selectBankOpenAccountByUserId(String userId);

    /**
     * 根据用户id获取企业用户开户信息
     *
     * @param userId
     * @return
     * @auth nxl
     */
    CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(String userId);

    /**
     * 根据用户id获取第三方平台绑定信息
     *
     * @param userId
     * @return
     * @auth nxl
     */
    BindUserVo selectBindeUserByUserId(String userId);

    /**
     * 根据用户id获取用户CA认证记录表
     *
     * @param userId
     * @return
     * @auth nxl
     */
    CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId);

    /**
     * 根据用户id获取用户修改信息
     *
     * @param userId
     * @return
     * @auth nxl
     */
    UserManagerUpdateVO selectUserUpdateInfoByUserId(String userId);

    /**
     * 更新用户信息
     *
     * @param request
     * @return
     * @auth nxl
     */
    int updataUserInfo(UserManagerUpdateRequest request);

    /**
     * 根据用户id获取推荐人信息
     *
     * @param userId
     * @return
     * @auth nxl
     */
    UserRecommendCustomizeVO selectUserRecommendByUserId(String userId);

    /**
     * 校验手机号
     *
     * @param userId
     * @param mobile
     * @return
     * @auth nxl
     */
    int countUserByMobile(int userId, String mobile);

    /**
     * 校验推荐人
     *
     * @param userId
     * @param recommendName
     * @return
     * @auth nxl
     */
    int checkRecommend(String userId, String recommendName);

    /**
     * 根据用户id查找用户表
     *
     * @param userId
     * @param userId
     * @return
     * @auth nxl
     */
    UserVO selectUserByUserId(int userId);

    /**
     * 根据用户List id查找用户表
     *
     * @param userId
     * @param userId
     * @return
     * @auth nxl
     */
    List<UserVO> selectUserByListUserId(List userId);

    /**
     * 根据用户id查找用户信息
     *
     * @param userId
     * @return
     * @auth nxl
     */
    List<BankCardVO> selectBankCardByUserId(int userId);

    /**
     * 更新企业用户开户记录
     *
     * @param request
     * @return
     * @auth nxl
     */
    int updateCorpOpenAccountRecord(CorpOpenAccountRecordRequest request);

    /**
     * 插入企业用户开户记录
     *
     * @param request
     * @return
     * @auth nxl
     */
    int insertCorpOpenAccountRecord(CorpOpenAccountRecordRequest request);

    /**
     * 更新用户绑定的银行卡
     *
     * @param request
     * @return
     * @auth nxl
     */
    int updateUserCard(BankCardRequest request);

    /***
     * 开户掉单，保存用户绑定的银行卡
     * @author Zha Daojian
     * @date 2019/3/15 10:13
     * @param request
     * @return com.hyjf.am.vo.admin.OpenAccountEnquiryDefineResultBeanVO
     **/
    int insertUserCard(BankCardRequest request);

    /**
     * 单表查询开户信息
     *
     * @return
     * @auth nxl
     */
    BankOpenAccountVO queryBankOpenAccountByUserId(int userId);

    /**
     * 更新开户信息
     *
     * @param request
     * @return
     * @auth nxl
     */
    int updateBankOpenAccount(BankOpenAccountRequest request);

    /**
     * 插入开户信息
     *
     * @param request
     * @return
     * @auth nxl
     */
    int insertBankOpenAccount(BankOpenAccountRequest request);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     * @auth nxl
     */
    UserInfoVO findUserInfoById(int userId);

    /**
     * 更新用户表
     *
     * @param userInfoVO
     * @return
     * @auth nxl
     */
    int updateUserInfoByUserInfo(UserInfoVO userInfoVO);

    /**
     * 开户掉单更新用户信息
     * @param userInfoVO
     * @return
     */
    int updateUserInfoByUserInfoSelective(UserInfoVO userInfoVO);

    /**
     * 更新用户表
     *
     * @param userVO
     * @return
     * @auth nxl
     */
    int updateUser(UserVO userVO);

    /**
     * 更新用户表--开户掉单
     *
     * @param userVO
     * @return
     * @auth nxl
     */
    int updateUserSelective(UserVO userVO);

    /**
     * 获取某一用户的信息修改列表
     *
     * @param request
     * @return
     * @auth nxl
     */
    List<UserChangeLogVO> selectUserChageLog(UserChangeLogRequest request);

    /**
     * 根据推荐人姓名查找用户
     *
     * @param recommendName
     * @return
     * @auth nxl
     */
    UserVO selectUserByRecommendName(String recommendName);

    SpreadsUserVO selectSpreadsUsersByUserId(String userId);
      /**
     * 修改推荐人信息
     *
     * @param request
     * @return
     * @auth nxl
     */
    int updateUserRecommend(AdminUserRecommendRequest request);

    /**
     * 修改用户身份证
     *
     * @param request
     * @return
     * @auth nxl
     */
    int updateUserIdCard(AdminUserRecommendRequest request);

    /**
     * 保存企业开户信息
     *
     * @param updCompanyRequest
     * @return
     * @auth nxl
     */
    Response saveCompanyInfo(UpdCompanyRequest updCompanyRequest);

    /**
     * 根据参数查询用户画像信息
     *
     * @param request
     * @return
     * @author nxl
     */
    UserPortraitResponse selectRecordList(UserPortraitRequest request);

    /**
     * 导出根据参数查询用户画像信息
     *
     * @param userPortraitRequest
     * @return
     */
    UserPortraitResponse exportRecordList(UserPortraitRequest userPortraitRequest);

    /**
     * 根据用户id查找用户画像
     *
     * @param userId
     * @return
     * @author nxl
     */
    UserPortraitVO selectUsersPortraitByUserId(Integer userId);

    /**
     * 修改用户画像
     *
     * @author nxl
     */
    int updateUserPortrait(UserPortraitRequest request);


    /**
     * 根据UserID查询开户信息
     *
     * @param userId
     * @return
     */
    BankOpenAccountVO getBankOpenAccountByUserId(Integer userId);

    /*加入明细 start*/

    /**
     * 通过userid获取user
     *
     * @param
     * @return
     * @Title selectAccedeRecordList
     */
    UserVO getUserByUserId(int userId);

    /**
     * 通过
     *
     * @param
     * @return
     * @Title selectAccedeRecordList
     */
    UserInfoVO selectUsersInfoByUserId(int userid);
    /*加入明细 end*/

    /**
     * 查找注册记录列表
     *
     * @param request
     * @return
     * @author nxl
     */
    RegistRecordResponse findRegistRecordList(RegistRcordRequest request);

    /**
     * 查找借款盖章用户信息
     *
     * @param request
     * @return
     * @author nxl
     */
    LoanCoverUserResponse selectUserMemberList(LoanCoverUserRequest request);

    /**
     * 保存借款盖章用户信息
     *
     * @param request
     * @return
     * @author nxl
     */
    int insertLoanCoverUser(LoanCoverUserRequest request);

    /**
     * 根据证件号码查找借款主体CA认证记录表
     *
     * @param strIdNo
     * @return
     * @author nxl
     */
    LoanCoverUserVO selectIsExistsRecordByIdNo(String strIdNo,String userName);

    /**
     * 更新记录
     */
    int updateLoanCoverUserRecord(LoanCoverUserRequest request);

    /**
     * 根据id查找借款主体CA认证记录表
     *
     * @param strId
     * @return
     * @author nxl
     */
    LoanCoverUserResponse selectIsExistsRecordById(String strId);

    /**
     * 根据筛选条件查找(用户测评列表显示)
     *
     * @param request
     * @return
     * @author nxl
     */
    EvalationResultResponse selectUserEvalationResultList(EvalationRequest request);

    /**
     * 根据id查找用户测评结果
     *
     * @param userId
     * @return
     * @author nxl
     */
    UserEvalationResultVO selectEvaluationDetailById(String userId);

    /**
     * 查找汇付银行开户记录列表
     *
     * @param request
     * @return
     * @author nixiaoling
     */
    BankAccountRecordResponse findAccountRecordList(AccountRecordRequest request);

    /**
     * 查找江西银行开户记录列表
     *
     * @param request
     * @return
     * @author nixiaoling
     */
    BankAccountRecordResponse findBankAccountRecordList(BankAccountRecordRequest request);

    /**
     * 根据筛选条件查找汇付银行卡信息列表
     *
     * @param request 筛选条件
     * @return
     * @author nixiaoling
     */
    BankCardManagerResponse selectBankCardList(BankCardManagerRequest request);

    /**
     * 根据筛选条件查找江西银行卡信息列表
     *
     * @param request
     * @return
     * @author nixiaoling
     */
    BankCardManagerResponse selectNewBankCardList(BankCardManagerRequest request);

    /**
     * 查找用户银行卡操作记录表
     *
     * @param request
     * @return
     * @author nixiaoling
     */
    BankCardLogResponse selectBankCardLogByExample(BankCardLogRequest request);

    /**
     * 根据证件号码和姓名查找用户CA认证记录表
     *
     * @param tureName
     * @return
     */
    CertificateAuthorityResponse selectCertificateAuthorityByIdNoName(String tureName);

    CertificateAuthorityResponse isCAIdNoCheck(String param, String name);

    public AdminUserAuthListResponse userauthlist(AdminUserAuthListRequest adminUserAuthListRequest);

    public AdminUserAuthListResponse cancelInvestAuth(int userId);

    public AdminUserAuthListResponse cancelCreditAuth(int userId);

    public AdminUserAuthLogListResponse userauthLoglist(AdminUserAuthLogListRequest adminUserAuthListRequest);

    CertificateAuthorityResponse getRecordList(CertificateAuthorityExceptionRequest aprlr);

    CertificateAuthorityResponse updateUserCAMQ(String userId);

    /**
     * 获取预注册数据列表
     *
     * @return
     */
    public AdminPreRegistListResponse getRecordList(AdminPreRegistListRequest adminPreRegistListRequest);

    /**
     * 获取预注册页面信息
     *
     * @return
     */
    public AdminPreRegistListResponse getPreRegist(AdminPreRegistListRequest adminPreRegistListRequest);

    /**
     * 编辑保存预注册页面信息
     *
     * @return
     */
    public AdminPreRegistListResponse savePreRegist(AdminPreRegistListRequest adminPreRegistListRequest);

    /**
     * 根据条件查询移动端着陆页管理
     *
     * @param requestBean
     * @return
     */
    WhereaboutsPageResponse searchAction(WhereaboutsPageRequestBean requestBean);

    /**
     * 添加移动端着陆页管理
     *
     * @param requestBean
     * @return
     */
    WhereaboutsPageResponse insertAction(WhereaboutsPageRequestBean requestBean);

    /**
     * 修改移动端着陆页管理
     *
     * @param requestBean
     * @return
     */
    WhereaboutsPageResponse updateAction(WhereaboutsPageRequestBean requestBean);

    /**
     * 修改移动端着陆页管理状态
     *
     * @param requestBean
     * @return
     */
    WhereaboutsPageResponse updateStatus(WhereaboutsPageRequestBean requestBean);

    /**
     * 根据id删除移动端着陆页管理
     *
     * @param id
     * @return
     */
    WhereaboutsPageResponse deleteById(Integer id);

    /**
     * 根据条件查询PC统计明细
     *
     * @param request
     * @return
     */
    ChannelStatisticsDetailResponse searchChannelStatisticsDetail(ChannelStatisticsDetailRequest request);

    /**
     * 获取用户账户信息
     *
     * @param userId
     * @return
     */
    BankOpenAccountVO getBankOpenAccount(Integer userId);

    public ChangeLogResponse getChangeLogList(ChangeLogRequest clr);

    public KeyCountResponse searchAction(KeyCountRequest request);


    public UserResponse findUserByCondition(String userName);

    public UserResponse findUserByUserId(Integer userId);

    /**
     * 获取数据
     *
     * @param map 查询参数
     * @return UtmResultResponse
     */
    UtmResponse getByPageList(Map<String, Object> map);

    /**
     * 获取数据总条数
     *
     * @param map 查询参数
     * @return UtmResultResponse
     */
    UtmResponse getCountByParam(Map<String, Object> map);

    /**
     * @return Integer
     * @Author walter.limeng
     * @Description 根据条件查询推广总数
     * @Date 11:16 2018/7/14
     * @Param channelCustomizeVO
     */
    Integer getChannelCount(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @return List<ChannelCustomizeVO>
     * @Author walter.limeng
     * @Description 分页查询推广数据
     * @Date 11:16 2018/7/14
     * @Param channelCustomizeVO
     */
    List<ChannelCustomizeVO> getChannelList(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @return
     * @Author walter.limeng
     * @Description 取pc渠道
     * @Date 15:14 2018/7/14
     * @Param
     */
    List<UtmPlatVO> getUtmPlat();

    /**
     * @return
     * @Author walter.limeng
     * @Description 获取Utm对象
     * @Date 15:14 2018/7/14
     * @Param utmId
     */
    UtmChannelVO getRecord(String utmId);

    /**
     * @return
     * @Author walter.limeng
     * @Description 根据userId获取用户对象
     * @Date 15:15 2018/7/14
     * @Param empty
     * @Param utmReferrer
     */
    UserVO getUser(String utmReferrer, String userId);

    /**
     * @return
     * @Author walter.limeng
     * @Description 新增或者更新对象
     * @Date 10:04 2018/7/16
     * @Param channelCustomizeVO
     */
    boolean insertOrUpdateUtm(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @return
     * @Author walter.limeng
     * @Description 删除对象
     * @Date 10:28 2018/7/16
     * @Param channelCustomizeVO
     */
    boolean deleteAction(ChannelCustomizeVO channelCustomizeVO);

    /**
     * @return
     * @Author walter.limeng
     * @Description 根据主键ID获取Utm对象
     * @Date 10:48 2018/7/16
     * @Param id
     */
    UtmPlatVO getDataById(Integer id);

    /**
     * @return
     * @Author walter.limeng
     * @Description 根据sourceName和sourceId验证是否重复
     * @Date 11:15 2018/7/16
     * @Param sourceName
     * @Param sourceId
     */
    int sourceNameIsExists(String sourceName, Integer sourceId);

    /**
     * @return
     * @Author walter.limeng
     * @Description 新增或者修改对象
     * @Date 11:24 2018/7/16
     * @Param utmPlatVO
     */
    boolean insertOrUpdateUtmPlat(UtmPlatVO utmPlatVO);

    /**
     * @return
     * @Author walter.limeng
     * @Description 删除utmplat对象
     * @Date 11:46 2018/7/16
     * @Param utmPlatVO
     */
    boolean utmClientdeleteUtmPlatAction(UtmPlatVO utmPlatVO);

    /**
     * 获取vip用户管理列表
     * @param vipManageRequest
     * @return
     */
    VipManageResponse searchList(VipManageRequest vipManageRequest);

    /**
     * 获取vip用户详情列表
     * @param detailListRequest
     * @return
     */
    VipDetailListResponse searchDetailList(VipDetailListRequest detailListRequest);

    /**
     * 获取vip用户升级列表
     * @param vgl
     * @return
     */
    VipUpdateGradeListResponse searchUpdateGradeList(VipUpdateGradeListRequest vgl);
    /**
     * 根据id查找用户测评的问题与答案
     * @param evalationId
     * @author nxl
     * @return
     */
    List<UserEvalationQuestionVO> getUserQuestionInfoById(int evalationId);

    /**
     *  分账名单配置添加  查询用户名信息
     * @param request
     * @return
     */
    UserInfoCustomizeResponse queryUserInfoByUserName(AdminSubConfigRequest request);

    /**
     * 查询用户画像评分列表
     * @param request
     * @return
     */
    UserPortraitScoreResponse selectScoreRecordList(UserPortraitScoreRequest request);

   	public MspApplytResponse getRecordList(MspApplytRequest mspApplytRequest);
	public MspApplytResponse infoAction();
	public MspApplytResponse insertAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse updateAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse deleteRecordAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse validateBeforeAction(MspApplytRequest mspApplytRequest);
	public MspApplytResponse applyInfo(MspApplytRequest mspApplytRequest);
	public MspApplytResponse shareUser(MspApplytRequest mspApplytRequest);
	public MspApplytResponse download(MspApplytRequest mspApplytRequest);
	public MspResponse searchAction(MspRequest mspRequest);
	public MspResponse infoAction(MspRequest mspRequest);
	public MspResponse insertAction(MspRequest mspRequest);
	public MspResponse updateAction(MspRequest mspRequest);
	public MspResponse configureNameError(MspRequest mspRequest);
	public MspResponse deleteAction(MspRequest mspRequest);
	public MspResponse checkAction(MspRequest mspRequest);

    UtmResponse getChannelNameByUserId(Integer userId);

    /**
     * 查询手机号同步数量  用于前端分页显示
     * @auth sunpeikai
     * @param
     * @return
     */
    int countBankOpenAccountUser(MobileSynchronizeRequest request);

    /**
     * 查询手机号同步列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<MobileSynchronizeCustomizeVO> selectBankOpenAccountUserList(MobileSynchronizeRequest request);

    /**
     * 同步手机号
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean updateMobile(MobileSynchronizeRequest request);

    /**
     * 银行卡异常count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getBankCardExceptionCount(BankCardExceptionRequest request);

    /**
     * 银行卡异常列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AdminBankCardExceptionCustomizeVO> searchBankCardExceptionList(BankCardExceptionRequest request);

    /**
     * 更新银行卡(admin后台异常中心-银行卡异常用)
     * @auth sunpeikai
     * @param
     * @return
     */
    String updateAccountBankByUserId(BankCardExceptionRequest request);

    /**
     * 线下修改信息同步查询列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getModifyInfoCount(AccountMobileSynchRequest request);

    /**
     * 线下修改信息同步查询列表list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AccountMobileSynchVO> searchModifyInfoList(AccountMobileSynchRequest request);

    /**
     * 添加信息
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer insertAccountMobileSynch(AccountMobileSynchRequest request);

    /**
     * 根据主键id删除一条信息
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer deleteAccountMobileSynch(AccountMobileSynchRequest request);

    /**
     * 根据号码查询用户
     * @param mobile
     * @return
     */
    UserVO getUserByMobile(String mobile);

    /**
     * 获取CA认证异常列表
     * @param aprlr
     * @return
     */
    CertificateAuthorityResponse getExceptionRecordList(CertificateAuthorityExceptionRequest aprlr);

    /**
     * 查询短信统计，通过分公司显示
     * @param request
     * @return
     */
    SmsCountCustomizeResponse querySmsCountList(SmsCountRequest request);

    /**
     * 查询短信总条数+总费用
     * @param request
     * @return
     */
    Integer querySmsCountNumberTotal(SmsCountCustomizeVO request);

    /**
     * 获取部门列表
     * @param o
     * @return
     */
    List<OADepartmentCustomizeVO> queryDepartmentInfo(Object o);

    /**
     * 在筛选条件下查询出用户
     * @param requestBean
     * @return
     */
    List<String> queryUser(SmsCodeRequestBean requestBean);

	/**
     * 获取用户账户信息byaccountId
     * @auth libin
     * @param accountId
     * @return
     */
    BankOpenAccountVO getBankOpenAccountByAccountId(String accountId);
    /**
     * 根据关联关系查询OA表的内容,得到部门的线上线下属性
     * @param userId
     * @auth nxl
     * @return
     */
    UserUpdateParamCustomizeResponse queryUserAndDepartment(Integer userId);
    /**
     * 获取所有用户信息
     * @auth nxl
     * @return
     */
    UserResponse selectAllUser();

    /**
     * 查询此段时间的用户推荐人的修改记录
     * @param userId
     * @param repairStartDate
     * @param repairEndDate
     * @auth nxl
     * @return
     */
    SpreadsUserLogResponse searchSpreadUsersLogByDate(Integer userId, String repairStartDate, String repairEndDate);

    /**
     * 查找员工信息
     * @param userId
     * @auth nxl
     * @return
     */
    EmployeeCustomizeResponse selectEmployeeInfoByUserId(Integer userId);
    /**
     * 根据用户id获取离职信息
     * @param userId
     * @auth nxl
     * @return
     */
    AdminEmployeeLeaveCustomizeResponse selectUserLeaveByUserId(Integer userId);

    /**
     * 通过手机号和身份证查询掉单信息
     * @author Zha Daojian
     * @date 2018/8/21 13:54
     * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
     **/
    List<BankOpenAccountLogVO> bankOpenAccountLogSelect(BankOpenAccountLogRequest request);

    /**
     * 通过手机号和身份证查询用户信息
     * @author Zha Daojian
     * @date 2018/8/21 13:54
     * @param request
     * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
     **/
    OpenAccountEnquiryCustomizeVO searchAccountEnquiry(BankOpenAccountLogRequest request);

    /**
     * 根据订单号查询用户的开户记录
     * @author Zha Daojian
     * @date 2018/8/21 13:54
     * @param orderId
     * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
     **/
    BankOpenAccountLogVO selectBankOpenAccountLogByOrderId(String  orderId);

    /**
     * 删除用户开户日志
    * @author Zha Daojian
    * @date 2018/8/22 11:30
    * @param userId
    * @return java.lang.Boolean
    **/
    Boolean deleteBankOpenAccountLogByUserId(Integer  userId);

    /**
     * 查询返回的电子账号是否已开户
    * @author Zha Daojian
    * @date 2018/8/22 13:38
    * @param accountId
    * @return java.lang.Boolean
    **/
    Boolean checkAccountByAccountId(String accountId);

    /**
     * 查询开户渠道
     * @author Zha Daojian
     * @date 2018/8/22 13:38
     * @param userId
     * @return java.lang.Boolean
     **/
    AppUtmRegVO getAppChannelStatisticsDetailByUserId(Integer userId);

    /**
     * 开户更新开户渠道统计开户时间
     * @author Zha Daojian
     * @date 2018/8/22 13:38
     * @param appUtmRegVO
     * @return java.lang.Boolean
     **/
    Boolean updateByPrimaryKeySelective(AppUtmRegVO appUtmRegVO);

	int isExistsUser(String userId);
    /**
     * 根据推荐人id查找用信息
     * @param userId
     * @auther: nxl
     * @return
     */
    List<SpreadsUserVO> selectSpreadsUserBySpreadUserId(int userId);

    /**
     * 校验手机号
     * @param mobile
     * @return
     * @auth nxl
     */
    int countByMobile(String mobile);

    /**
     * 着落页配置查询
     * @param form
     * @return
     * @auth
     */
    WhereaboutsPageResponse getWhereaboutsPageConfigById(WhereaboutsPageRequestBean form);

    /**
     * 检查渠道
     *
     *
     */
    public StringResponse checkUtmId(Integer utmId);

    /**
     * 检查渠道
     *
     *
     */
    public StringResponse checkReferrer(String referrer);

    /**
     * 根据部门id查找是否有自级菜单
     * @param deptId
     * @return
     * @auther: nxl
     */
    List<OADepartmentCustomizeVO> getDeptInfoByDeptId(int deptId);

    /**
     * @Author walter.limeng
     * @Description  获取所有得UtmPlat
     * @Date 18:38 2018/10/9
     * @Param map
     * @return
     */
    UtmPlatResponse getAllUtmPlat(Map<String,Object> map);

    /**
     * 新增操作
     * @param voList
     */
    void insertUtmList(List<ChannelCustomizeVO> voList);
    /**
     * 根据用户id获取开户信息
     *
     * @auther: nxl
     * @param userId
     * @return
     */
    BankCardVO getBankCardByUserId(int userId);
    /**
     * 更新用户信息(基本信息,手机号,邮箱,用户角色)
     *
     * @param request
     * @auther: nxl
     * @return
     */
    int updateUserBaseInfo(UserInfosUpdCustomizeRequest request);
    /**
     * 更新银行卡信息
     *
     * @param request
     * @auther: nxl
     * @return
     */
    int updateUserBankInfo(UserInfosUpdCustomizeRequest request);

    void updateUserAuth(UserAuthRequest request);

    HjhUserAuthLogVO selectByExample(String orderId);
    /**
     * 根据用户的查询条件查询用户缴费授权列表
     * @param request
     * @auther: nxl
     * @return
     */
    UserPayAuthResponse selectUserPayAuthList(UserPayAuthRequest request);
    /**
     * 根据用户id查询用户签约授权信息
     * @param userId
     * @auther: nxl
     * @return
     */
    HjhUserAuthResponse selectUserPayAuthByUserId(int userId);
    /**
     * 查看该用户在出借表和标的放款记录中是否存在
     * @param userId
     * @auther: nxl
     * @return
     */
    int isDismissPay(int userId);
    /**
     * 查看该用户在投标的还款记录中是否存在
     * @param userId
     * @auther: nxl
     * @return
     */
    int isDismissRePay(int userId);
    /**
     * 缴费授权解约
     * @param userId
     * @return
     * @auther: nxl
     */
   boolean updateCancelPayAuth(int userId);
    /**
     * 插入授权记录表
     * @param hjhUserAuthLogRequest
     * @return
     * @auther: nxl
     */
    boolean insertUserAuthLog2(HjhUserAuthLogRequest hjhUserAuthLogRequest);
    /**
     * 根据用户的查询条件查询用户还款授权列表
     * @param request
     * @auther: nxl
     * @return
     */
    UserPayAuthResponse selectRecordListRePay(UserPayAuthRequest request);
    /**
     * 还款授权解约
     * @param userId
     * @return
     * @auther: nxl
     */
    int updateCancelRePayAuth(int userId);

	CertificateAuthorityResponse selectCertificateAuthorityByCAName(String tureName);

    /**
     * 渠道管理检查编号唯一性
     * @Author cwyang 2018-10-22
     * @param sourceId
     * @return
     */
    int sourceIdIsExists(Integer sourceId);

    HjhUserAuthVO getHjhUserAuthByUserId(Integer userId);


    /**
     * 根据用户Id查询开户信息
     * @param userId
     * @return
     */
    List<BankOpenAccountLogVO>  getBankOpenAccountLogVOByUserId(Integer userId);




    int onlyCheckMobileCode(String mobile, String code);

    /**
     * @Author walter.limeng
     * @Description  根据渠道和关键字查询总条数
     * @Date 15:17 2018/11/14
     * @Param sourceId
     * @Param utmTerm
     * @return
     */
    Integer getBySourceIdAndTerm(String utmId,String sourceId, String utmTerm);

    /**
     * 查询短信统计导出条数
     * @param request
     * @return
     */
    Integer getSmsCountForExport(SmsCountRequest request);

    /**
     * 查询短信统计导出列表
     * @param request
     * @return
     */
    List<SmsCountCustomizeVO> getSmsListForExport(SmsCountRequest request);

    /**
     * 查询满足条件的服务费授权的条数
     * @param userPayAuthRequest
     * @return
     */
    int selectUserMemberCount(UserPayAuthRequest userPayAuthRequest);

    /**
     * 在筛选条件下查询出用户数量
     * @param requestBean
     * @return
     */
    int countUser(SmsCodeRequestBean requestBean);

    /**
     * 通过当前用户ID 查询用户所在一级分部,从而关联用户所属渠道
     * @param userId
     * @return
     * @Author : huanghui
     */
    UserUtmInfoCustomizeVO getUserUtmInfo(Integer userId);

    /***
     * 开户掉单，保存开户User数据
     * @author Zha Daojian
     * @date 2019/1/22 9:48
     * @param requestBean
     * @return OpenAccountEnquiryDefineRequestBeanVO
     **/
    OpenAccountEnquiryDefineResultBeanVO updateUser(OpenAccountEnquiryDefineResultBean requestBean);

    /***
     * 开户掉单，保存开户Account数据
     * @author Zha Daojian
     * @date 2019/1/22 9:48
     * @param requestBean
     * @return OpenAccountEnquiryDefineRequestBeanVO
     **/
    OpenAccountEnquiryDefineResultBeanVO updateAccount(OpenAccountEnquiryDefineResultBean requestBean);

    /**
     * 查询用户名不在表中数量
     * @param request
     * @return
     */
    int countUserNames(UserPortraitCustomizeRequest request);

    /**
     * 批量插入用户画像表
     * @param request
     * @return
     */
    int updateBatch(UserPortraitCustomizeRequest request);

    /**
     * 企业信息补录时查询，根据对公账号查找银行信息
     *
     * @param updCompanyRequest
     * @auther: nxl
     * @return
     */
    BankCardResponse getBankInfoByAccount(UpdCompanyRequest updCompanyRequest);

    /**
     * 用户销户操作
     *
     * @param userId
     * @param bankOpenAccount
     * @return
     */
    int cancellationAccountAction(String userId, Integer bankOpenAccount);

    int saveSmsCode(String mobile, String checkCode, String verificationType, Integer ckcodeNew, int clientPc);

    /**
     * 验证验证码
     * @param mobile
     * @param code
     * @param validCodeType
     * @param clientPc
     * @param ckcodeYiyan
     * @param ckcodeYiyan1
     * @return
     */
    int checkMobileCode(String mobile, String code, String validCodeType, String clientPc, Integer ckcodeYiyan, Integer ckcodeYiyan1,boolean isUpdate);
    /**
     * 用户销户成功后,保存销户记录表
     *
     * @param bankCancellationAccountRequest
     * @return
     */
    int saveCancellationAccountRecordAction(BankCancellationAccountRequest bankCancellationAccountRequest);

    /**
     * 查询用户销户记录列表
     *
     * @param bankCancellationAccountRequest
     * @return
     */
    BankCancellationAccountResponse getBankCancellationAccountList(BankCancellationAccountRequest bankCancellationAccountRequest);
}
