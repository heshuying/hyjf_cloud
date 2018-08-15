package com.hyjf.admin.client;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UserPortraitScoreResponse;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.admin.VipDetailListResponse;
import com.hyjf.am.response.admin.VipManageResponse;
import com.hyjf.am.response.admin.VipUpdateGradeListResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.response.user.AdminPreRegistListResponse;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.response.user.AdminUserAuthLogListResponse;
import com.hyjf.am.response.user.BankAccountRecordResponse;
import com.hyjf.am.response.user.BankCardLogResponse;
import com.hyjf.am.response.user.BankCardManagerResponse;
import com.hyjf.am.response.user.CertificateAuthorityResponse;
import com.hyjf.am.response.user.ChangeLogResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.response.user.EvalationResultResponse;
import com.hyjf.am.response.user.KeyCountResponse;
import com.hyjf.am.response.user.LoanCoverUserResponse;
import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.response.user.MspResponse;
import com.hyjf.am.response.user.RegistRecordResponse;
import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.trade.CorpOpenAccountRecordRequest;
import com.hyjf.am.resquest.user.AccountRecordRequest;
import com.hyjf.am.resquest.user.AdminPreRegistListRequest;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.resquest.user.AdminUserAuthLogListRequest;
import com.hyjf.am.resquest.user.AdminUserRecommendRequest;
import com.hyjf.am.resquest.user.BankAccountRecordRequest;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankOpenAccountRequest;
import com.hyjf.am.resquest.user.CertificateAuthorityExceptionRequest;
import com.hyjf.am.resquest.user.ChangeLogRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.resquest.user.EvalationRequest;
import com.hyjf.am.resquest.user.KeyCountRequest;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.resquest.user.MspRequest;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.am.resquest.user.UpdCompanyRequest;
import com.hyjf.am.resquest.user.UserChangeLogRequest;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.resquest.user.UserManagerUpdateRequest;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.vo.admin.AdminBankCardExceptionCustomizeVO;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.am.vo.admin.MobileSynchronizeCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.BindUserVo;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.EmployeeCustomizeVO;
import com.hyjf.am.vo.user.LoanCoverUserVO;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserBankOpenAccountVO;
import com.hyjf.am.vo.user.UserChangeLogVO;
import com.hyjf.am.vo.user.UserEvalationQuestionVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserManagerDetailVO;
import com.hyjf.am.vo.user.UserManagerUpdateVO;
import com.hyjf.am.vo.user.UserPortraitVO;
import com.hyjf.am.vo.user.UserRecommendCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;

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
     * 查询自动投资债转异常列表
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    AdminUserAuthListResponse userAuthList(AdminUserAuthListRequest adminUserAuthListRequest);

    /**
     * 同步用户授权状态
     *
     * @param type 1自动投资授权  2债转授权
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
     * 获取银行卡配置信息
     * @auth nxl
     * @param bankId
     * @return
     */
//    BanksConfigVO getBanksConfigByBankId(int bankId);

    /**
     * 更新用户绑定的银行卡
     *
     * @param request
     * @return
     * @auth nxl
     */
    int updateUserCard(BankCardRequest request);

    /**
     * 保存用户绑定的银行卡
     *
     * @param request
     * @return
     * @auth nxl
     */
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
     * 更新用户表
     *
     * @param userVO
     * @return
     * @auth nxl
     */
    int updateUser(UserVO userVO);

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
    LoanCoverUserVO selectIsExistsRecordByIdNo(String strIdNo);

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
     * @param strIdNo
     * @param tureName
     * @return
     */
    CertificateAuthorityResponse selectCertificateAuthorityByIdNoName(String strIdNo, String tureName);

    CertificateAuthorityResponse isCAIdNoCheck(String param, String name);

    public AdminUserAuthListResponse userauthlist(AdminUserAuthListRequest adminUserAuthListRequest);

    public AdminUserAuthListResponse cancelInvestAuth(int userId);

    public AdminUserAuthListResponse cancelCreditAuth(int userId);

    public AdminUserAuthLogListResponse userauthLoglist(AdminUserAuthLogListRequest adminUserAuthListRequest);

    CertificateAuthorityResponse getRecordList(CertificateAuthorityExceptionRequest aprlr);

    CertificateAuthorityResponse updateUserCAMQ(int userId);

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
     * 行账户管理页面查询件数
     *
     * @param bankAccountManageRequest
     * @return
     */
    Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest);

    /**
     * 账户管理页面查询列表
     *
     * @param bankAccountManageRequest
     * @return
     */
    List<BankAccountManageCustomizeVO> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest);

    /**
     * 资金明细（列表）
     *
     * @param bankAccountManageRequest
     * @return
     */
    List<BankAccountManageCustomizeVO> queryAccountDetails(BankAccountManageRequest bankAccountManageRequest);

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
    List<UtmPlatVO> getUtmPlat(String sourceId);

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
}
