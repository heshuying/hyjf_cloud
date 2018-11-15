package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.AppPushManageRequestBean;
import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.am.bean.admin.LockedConfig;
import com.hyjf.am.response.AppPushManageResponse;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.EmailRecipientResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.admin.locked.LockedUserMgrResponse;
import com.hyjf.am.response.admin.promotion.ChannelReconciliationResponse;
import com.hyjf.am.response.admin.promotion.PlatformUserCountCustomizeResponse;
import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.admin.locked.LockedeUserListRequest;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.resquest.config.VersionConfigBeanRequest;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.config.SubmissionsVO;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.repay.BankRepayOrgFreezeLogVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UtmPlatVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tanyy
 * @version AmAdminClient, v0.1 2018/8/19 12:44
 */
public interface AmAdminClient {

    /**
     * count PC统计明细列表
     *
     * @return
     */
     IntegerResponse countList(ChannelStatisticsDetailRequest request);

    /**
     * 获取所有渠道
     * @param type 类型:app,pc
     * @return
     */
    List<UtmVO> selectUtmPlatList(String type);
    /**
     * 访问数
     * @param sourceId 账户推广平台
     * @param type 类型: pc,app
     * @return
     */
    Integer getAccessNumber(Integer sourceId, String type);
    /**
     * 注册数
     * @param sourceId 账户推广平台
     * @param type 类型: pc,app
     * @return
     */
    Integer getRegistNumber(Integer sourceId, String type);

    /**
     * 开户数
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    Integer getOpenAccountNumber(Integer sourceId, String type);

    /**
     * 投资人数
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    Integer getTenderNumber(Integer sourceId, String type);

    /**
     * 累计充值
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getCumulativeRecharge(Integer sourceId, String type);

    /**
     * 汇直投投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getHztTenderPrice(Integer sourceId, String type);

    /**
     * 汇消费投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getHxfTenderPrice(Integer sourceId, String type);

    /**
     * 汇天利投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getHtlTenderPrice(Integer sourceId, String type);

    /**
     * 汇添金投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getHtjTenderPrice(Integer sourceId, String type);

    /**
     * 汇金理财投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getRtbTenderPrice(Integer sourceId, String type);

    /**
     * 汇转让投资金额
     * @param sourceId
     * @param type 类型: pc,app
     * @return
     */
    BigDecimal getHzrTenderPrice(Integer sourceId, String type);

    /**
     * 根据条件查询PC统计明细
     *
     * @param request
     * @return
     */
    ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request);

    /**
     * 获取app渠道列表
     */
    public List<UtmPlatVO> getPCUtm();

    /**
     * 获取用户/机构信息
     * @param requestBean
     * @return
     */
    STZHWhiteListResponse getUserByUserName(STZHWhiteListRequestBean requestBean);

    /**
     * 还款方式下拉列表
     *
     * @param
     * @return
     * @author wangjun
     * yangchangwei 迁移至amadminClient
     */
    List<BorrowStyleVO> selectCommonBorrowStyleList();


    /**
     * 查询汇计划转让列表
     *
     * @param request
     * @return
     * yangchangwei
     */
    HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request);

    /**
     * 查询手续费分账count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPoundageCount(PoundageListRequest request);
    /**
     * 查询手续费分账list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<PoundageCustomizeVO> searchPoundageList(PoundageListRequest request);

    /**
     * 根据用户名查询分账名单是否存在
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    public AdminSubConfigResponse subconfig(AdminSubConfigRequest adminRequest);

    /**
     * 获取手续费分账数额总计
     * @auth sunpeikai
     * @param
     * @return
     */
    PoundageCustomizeVO getPoundageSum(PoundageListRequest request);

    /**
     * 根据id查询手续费分账信息
     * @auth sunpeikai
     * @param
     * @return
     */
    PoundageCustomizeVO getPoundageById(Integer id);

    /**
     * 审核-更新poundage表
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer updatePoundage(PoundageCustomizeVO poundageCustomizeVO);


    /**
     * 查询批次中心-批次放款列表
     * @param request
     * @return
     */
    BatchBorrowRecoverReponse getBatchBorrowRecoverList(BatchBorrowRecoverRequest request);

    /**
     * 查询批次中心的批次列表求和
     *
     * @param request
     * @return
     */
    BatchBorrowRecoverReponse getBatchBorrowCenterListSum(BatchBorrowRecoverRequest request);

    /**
     * yangchangwei
     * 根据id 获取放款任务表
     *
     * @param apicronID
     * @return
     */
    BorrowApicronResponse getBorrowApicronByID(String apicronID);

    /**
     * 查询权限数量
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPermissionsCount(AdminPermissionsRequest request);

    /**
     * 查询权限列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AdminPermissionsVO> searchPermissionsList(AdminPermissionsRequest request);

    /**
     * 检查数据库是否已存在该权限
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean isExistsPermission(AdminPermissionsVO adminPermissionsVO);

    /**
     * 插入权限
     * @auth sunpeikai
     * @param
     * @return
     */
    int insertPermission(AdminPermissionsVO adminPermissionsVO);

    /**
     * 修改权限
     * @auth sunpeikai
     * @param
     * @return
     */
    int updatePermission(AdminPermissionsVO adminPermissionsVO);

    /**
     * 根据uuid查询权限
     * @auth sunpeikai
     * @param
     * @return
     */
    AdminPermissionsVO searchPermissionByUuid(String uuid);

    /**
     * 删除权限
     * @auth sunpeikai
     * @param
     * @return
     */
    int deletePermission(String uuid);

    /**
     * 资产来源
     * yangchangwei
     * @return
     */
    List<HjhInstConfigVO> selectHjhInstConfigList();

    /**
     * 查询数据字典总数
     * @auth sunpeikai
     * @param
     * @return
     */
    int getParamNamesCount(AdminParamNameRequest request);

    /**
     * 查询数据字典列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<ParamNameVO> searchParamNamesList(AdminParamNameRequest request);

    /**
     * 检查paramName是否存在
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean isExistsParamName(ParamNameVO paramNameVO);

    /**
     * 添加数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    int insertParamName(ParamNameVO paramNameVO);

    /**
     * 更新数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    int updateParamName(ParamNameVO paramNameVO);

    /**
     * 根据联合主键查询数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    ParamNameVO searchParamNameByKey(ParamNameVO paramNameVO);

    /**
     * 删除数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    int deleteParamName(ParamNameVO paramNameVO);

    /**
     * 同步数据字典至redis
     * @auth wgx
     * @return
     */
    boolean syncParam();

    /**
     * 查询手续费分账配置
     * @auth sunpeikai
     * @param
     * @return
     */
    PoundageLedgerVO getPoundageLedgerById(int id);

    /**
     * 手续费分账详细信息总数
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPoundageDetailCount(AdminPoundageDetailRequest request);

    /**
     * 手续费分账详细信息列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<PoundageDetailVO> searchPoundageDetailList(AdminPoundageDetailRequest request);


	List<DataCenterCouponCustomizeVO> getRecordListDJ(DataCenterCouponCustomizeVO dataCenterCouponCustomize);


	List<DataCenterCouponCustomizeVO> getRecordListJX(DataCenterCouponCustomizeVO dataCenterCouponCustomize);


	String getActivityTitle(Integer activityId);


	List<DataCenterCouponCustomizeVO> getDataCenterCouponList(DadaCenterCouponRequestBean requestBean, String type);


	PlatformCountCustomizeResponse searchAction(PlatformCountRequestBean requestBean);


    PlatformUserCountCustomizeResponse searchRegistAcount(PlatformCountRequestBean requestBean);

    /**
     * PC统计明细散标列表查询
     * @param request
     * @return
     */
    ChannelReconciliationResponse selectPcChannelReconciliationRecord(ChannelReconciliationRequest request);

    /**
     * PC统计明细计划列表查询
     * @param request
     * @return
     */
    ChannelReconciliationResponse selectPcChannelReconciliationRecordHjh(ChannelReconciliationRequest request);

    /**
     * APP统计明细散标列表查询
     * @param request
     * @return
     */
    ChannelReconciliationResponse selectAppChannelReconciliationRecord(ChannelReconciliationRequest request);

    /**
     * APP统计明细计划列表查询
     * @param request
     * @return
     */
    ChannelReconciliationResponse selectAppChannelReconciliationRecordHjh(ChannelReconciliationRequest request);

    /**
     * 获取app渠道列表
     * @return
     */
    List<UtmPlatVO> getAppUtm();

    /**
     * 获取版本管理详情
     * @param request
     * @return
     */
    SubmissionsVO getSubmissionsRecord(SubmissionsRequest request);

     SubmissionsResponse findSubmissionsList(SubmissionsRequest form);

     SubmissionsResponse updateSubmissionsStatus(SubmissionsRequest form);

     SubmissionsResponse exportSubmissionsList(SubmissionsRequest form);


     VersionConfigBeanResponse searchList(VersionConfigBeanRequest request);

     VersionConfigBeanResponse searchInfo(VersionConfigBeanRequest request);

     VersionConfigBeanResponse insertInfo(VersionConfigBeanRequest request);

     VersionConfigBeanResponse updateInfo(VersionConfigBeanRequest request);

     VersionConfigBeanResponse deleteInfo(VersionConfigBeanRequest request);
    /**
     * 获取广告管理列表数据
     * @param appBorrowImageRequest
     * @return
     */
    AppBorrowImageResponse searchList(AppBorrowImageRequest appBorrowImageRequest);
    /**
     *获取广告管理列表获取详情
     * @param appBorrowImageRequest
     * @return
     */
    AppBorrowImageResponse searchInfo(AppBorrowImageRequest appBorrowImageRequest);
    /**
     *插入广告管理列表
     * @param appBorrowImageRequest
     * @return
     */
    AppBorrowImageResponse insertInfo(AppBorrowImageRequest appBorrowImageRequest);
    /**
     *修改广告管理列表
     * @param appBorrowImageRequest
     * @return
     */
    AppBorrowImageResponse updateInfo(AppBorrowImageRequest appBorrowImageRequest);
    /**
     *删除广告管理列表
     * @param appBorrowImageRequest
     * @return
     */
    AppBorrowImageResponse deleteInfo(AppBorrowImageRequest appBorrowImageRequest);

    AppBannerResponse findAppBannerList(AppBannerRequest request);

    AppBannerResponse insertAppBannerList(AdsVO adsVO);

    AppBannerResponse updateAppBannerList(AdsVO adsVO);

    AppBannerResponse updateAppBannerStatus(AdsVO adsVO);

    AppBannerResponse deleteAppBanner(AdsVO adsVO);
    /**
     * 根据id获取广告
     * @param adsVO
     * @return
     */
    AppBannerResponse getRecordById(AdsVO adsVO);


    /**
     * 获取保证金配置总数
     *
     * @param request
     * @return
     */
    Integer selectBailConfigCount(BailConfigRequest request);

    /**
     * 获取保证金配置列表
     *
     * @param request
     * @return
     */
    List<BailConfigCustomizeVO> selectBailConfigRecordList(BailConfigRequest request);

    /**
     * 获取锁定账户列表
     * @auth cuiguangqiang
     * @param request
     * @param isFront：是否前台
     * @return
     */
    LockedUserMgrResponse getLockedUserList(LockedeUserListRequest request, boolean isFront);

    /**
     * 解锁账户
     * @param vo
     * @param isFront
     * @return
     */
    BooleanResponse unlock(LockedUserInfoVO vo, boolean isFront);

    /**
     * 前台用户锁定配置
     * @return
     */
    LockedConfig.Config getFrontLockedCfg();

    /**
     * 后台用户锁定配置
     * @return
     */
    LockedConfig.Config getAdminLockedCfg();

    /**
     * 保存前台用户锁定配置
     * @param webConfig
     * @return
     */
    BooleanResponse saveFrontConfig(LockedConfig.Config webConfig);


    /**
     * 保存后台用户锁定配置
     * @param adminConfig
     * @return
     */
    BooleanResponse saveAdminConfig(LockedConfig.Config adminConfig);

    /**
     * 更新当前机构可用的还款方式并返回最新保证金详情
     *
     * @param id
     * @return
     */
    BailConfigInfoCustomizeVO updateSelectBailConfigById(Integer id);

    /**
     * 未配置保证金的机构编号
     *
     * @return
     */
    List<HjhInstConfigVO> selectNoUsedInstConfigList();

    /**
     * 添加保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    boolean insertBailConfig(BailConfigAddRequest bailConfigAddRequest);

    /**
     * 周期内发标已发额度
     *
     * @param bailConfigAddRequest
     * @return
     */
    String selectSendedAccountByCyc(BailConfigAddRequest bailConfigAddRequest);

    /**
     * 根据该机构可用还款方式更新可用授信方式
     *
     * @param instCode
     * @return
     */
    boolean updateBailInfoDelFlg(String instCode);

    /**
     * 更新保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    boolean updateBailConfig(BailConfigAddRequest bailConfigAddRequest);

    /**
     * 删除保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    boolean deleteBailConfig(BailConfigAddRequest bailConfigAddRequest);

    /**
     * 获取当前机构可用还款方式
     * 
     * @param instCode
     * @return
     */
    List<String> selectRepayMethod(String instCode);

    /**
     * 获取保证金配置日志总数
     *
     * @param request
     * @return
     */
    Integer selectBailConfigLogCount(BailConfigLogRequest request);

    /**
     * 获取保证金配置日志列表
     *
     * @param request
     * @return
     */
    List<BailConfigLogCustomizeVO> selectBailConfigLogList(BailConfigLogRequest request);

    /**
     * 查询异常标的总件数
     *
     * @param request
     * @return
     */
    Integer selectAssetExceptionCount(AssetExceptionRequest request);

    /**
     * 查询异常标的列表
     *
     * @param request
     * @return
     */
    List<AssetExceptionCustomizeVO> selectAssetExceptionList(AssetExceptionRequest request);

    /**
     * 导出异常标的列表
     *
     * @param request
     * @return
     */
    List<AssetExceptionCustomizeVO> exportAssetExceptionList(AssetExceptionRequest request);

    /**
     * 插入异常标的并更新保证金
     *
     * @param assetExceptionRequest
     * @return
     */
    boolean insertAssetException(AssetExceptionRequest assetExceptionRequest);

    /**
     * 项目编号是否存在
     *
     * @param borrowNid
     * @return
     */
    String isExistsBorrow(String borrowNid);

    /**
     * 删除异常标的
     *
     * @param assetExceptionRequest
     * @return
     */
    boolean deleteAssetException(AssetExceptionRequest assetExceptionRequest);

    /**
     * 修改异常标的
     *
     * @param assetExceptionRequest
     * @return
     */
    boolean updateAssetException(AssetExceptionRequest assetExceptionRequest);

    /**
     * 处理平台转账
     * @auth sunpeikai
     * @param
     * @return
     */
    int updateHandRechargeRecord(PlatformTransferRequest platformTransferRequest);

    /**
     * 查询邮件配置列表数据
     *
     * @return
     */
    EmailRecipientResponse getRecordList(EmailRecipientRequest recipientRequest);
    /**
     * 查询邮件配置列表数据详情
     *
     * @return
     */
    EmailRecipientResponse getRecordById(EmailRecipientRequest recipientRequest);
    /**
     * 修改邮件配置
     * @return
     */
    EmailRecipientResponse updateEmailRecipient(EmailRecipientRequest recipientRequest);
    /**
     * 禁用邮件配置状态
     * @return
     */
    EmailRecipientResponse forbiddenAction(EmailRecipientRequest recipientRequest);
    /**
     * 添加邮件配置
     * @return
     */
    EmailRecipientResponse insertAction(EmailRecipientRequest recipientRequest);


    /**
     * 江西银行卡异常count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getBindCardExceptionCount(BindCardExceptionRequest request);

    /**
     * 江西银行卡异常列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<BindCardExceptionCustomizeVO> searchBindCardExceptionList(BindCardExceptionRequest request);

    /**
     * 异常中心-江西银行卡异常-更新银行卡
     * @auth sunpeikai
     * @param
     * @return
     */
    void updateBindCard(BindCardExceptionRequest request);

    List<BankRepayFreezeOrgCustomizeVO> getBankReapyFreezeOrgList(RepayFreezeOrgRequest request);

    Integer getBankReapyFreezeOrgCount(RepayFreezeOrgRequest request);

    Integer deleteOrgFreezeLog(String orderId);

    List<BankRepayOrgFreezeLogVO> getBankRepayOrgFreezeLogList(String orderId);

    /**
     * 移动客户端 - App 推送管理 列表
     * @param requestBean
     * @return
     * @Author : huanghui
     */
    AppPushManageResponse getPushManageList(AppPushManageRequestBean requestBean);

    /**
     * 移动客户端 - App 推送管理 添加
     * @param requestBean
     * @return
     * @Author : huanghui
     */
    AppPushManageResponse insterPushManage(AppPushManageRequestBean requestBean);

    /**
     * 移动客户端 - App 推送管理 更新
     * @param requestBean
     * @return
     * @Author : huanghui
     */
    boolean updatePushManage(AppPushManageRequestBean requestBean);

    /**
     * 移动客户端 - App 推送管理 删除
     * @param id
     * @return
     * @Author : huanghui
     */
    boolean deletePushManage(Integer id);

    /**
     * 查询千乐渠道散标数据
     * @return
     */
    DataSearchCustomizeResponse querySanList(DataSearchRequest dataSearchRequest);

    /**
     * 查询千乐渠道计划数据
     * @return
     */
    DataSearchCustomizeResponse queryPlanList(DataSearchRequest dataSearchRequest);
    /**
     * 查询千乐渠道全部数据
     * @return
     */
    DataSearchCustomizeResponse queryQianleList(DataSearchRequest dataSearchRequest);


    /**
     * 查询短信加固数据
     *
     * @param request
     * @return
     * @author xiehuili
     */
    SmsConfigResponse initSmsConfig(SmsConfigRequest request);


    /**
     * 节假日配置-列表查询
     * @param request
     * @return
     */
    AdminHolidaysConfigResponse getHolidaysConfigRecordList(AdminHolidaysConfigRequest request);

    /**
     * 节假日配置-画面迁移(含有id更新，不含有id添加)
     * @param request
     * @return
     */
    AdminHolidaysConfigResponse getInfoList(AdminHolidaysConfigRequest request);

    /**
     *节假日配置-添加活动信息
     * @param request
     * @return
     */
    AdminHolidaysConfigResponse insertHolidays(AdminHolidaysConfigRequest request);

    /**
     *节假日配置-修改活动维护信息
     * @param request
     * @return
     */
    AdminHolidaysConfigResponse updateHolidays(AdminHolidaysConfigRequest request);


    /**
     * 校验千乐验证码
     * @param mobile
     * @param code
 return
     */
    int onlyCheckMobileCode(String mobile, String code);


    int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform);

    /**
     * 查询app渠道统计数据
     * @param request
     * @return
     */
    AppUtmRegResponse getstatisticsList(AppChannelStatisticsDetailRequest request);
    /**
     * 导出app渠道统计数据
     * @param request
     * @return
     */
    AppUtmRegResponse exportStatisticsList(AppChannelStatisticsDetailRequest request);
}
