package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.*;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.admin.promotion.AppChannelReconciliationResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.response.trade.BankInterfaceResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.response.user.MspResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.config.*;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.resquest.user.MspRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.BankInterfaceVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zhangqingqing
 * @version AmConfigClient, v0.1 2018/4/23 20:00
 */
public interface AmConfigClient {
    /**
     * 用loginUserId去am-config查询登录的用户信息
     * @auth sunpeikai
     * @param loginUserId 登录用户id
     * @return
     */
    AdminSystemVO getUserInfoById(Integer loginUserId);

    /**
     * 获取数据字典表的下拉列表
     * @param nameClass
     * @return
     */
    List<ParamNameVO> getParamNameList(String nameClass);

    /**
     * 查询邮件配置
     * @auth sunpeikai
     * @param
     * @return
     */
    SiteSettingsVO findSiteSetting();

    /**
     * 查询邮件模板
     * @auth sunpeikai
     * @param
     * @return
     */
    SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode);

    /**
     * 根据银行错误码查询错误信息
     * @auth sunpeikai
     * @param retCode 错误码
     * @return
     */
    String getBankRetMsg(String retCode);

    /**
     * 获取银行返回码
     *
     * @param retCode
     * @return
     */
    BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode);
    /**
     * 获取银行列表
     * @author nixiaoling
     * @return
     */
    List<BankConfigVO> selectBankConfigList();

    /**
     * 根据银行卡号获取bankId
     * @auth nxl
     * @param cardNo
     * @return
     */
    String queryBankIdByCardNo(String cardNo);
    /**
     * 根据bankId查找江西银行的银行卡配置表
     * @auther: nxl
     * @param bankId
     * @return
     */
    JxBankConfigResponse getJXbankConfigByBankId(int bankId);
    /**
   	 * 合作机构
   	 * @return
   	 */
   	LinkResponse getLinks();
	/**
	 * 子账户类型 查询
	 */
	ParamNameResponse getNameCd(String code);

   	AdminSystemResponse isExistsApplicant(String applicant);
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

	public AdminUtmReadPermissionsResponse searchAction(AdminUtmReadPermissionsRequest request);
	/**
	 * 添加
	 *
	 * @param requestBean
	 * @return
	 */
	public AdminUtmReadPermissionsResponse insertAction(AdminUtmReadPermissionsRequest requestBean);

	/**
	 * 修改
	 *
	 * @param requestBean
	 * @return
	 */
	public AdminUtmReadPermissionsResponse updateAction(AdminUtmReadPermissionsRequest requestBean);

	/**
	 * 根据id删除
	 *
	 * @param id
	 * @return
	 */
	public AdminUtmReadPermissionsResponse deleteById(Integer id);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  根据用户Id查询渠道账号管理
	 * @Date 16:54 2018/7/24
	 * @Param userId
	 * @return
	 */
	AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(Integer userId);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  分页查询APP渠道
	 * @Date 17:23 2018/7/24
	 * @Param AppChannelReconciliationRequest
	 * @return
	 */
	AppChannelReconciliationResponse getReconciliationPage(AppChannelReconciliationRequest form);

	public AppBorrowImageResponse searchList(AppBorrowImageRequest appBorrowImageRequest);

	public AppBorrowImageResponse searchInfo(AppBorrowImageRequest appBorrowImageRequest);

	public AppBorrowImageResponse insertInfo(AppBorrowImageRequest appBorrowImageRequest);

	public AppBorrowImageResponse updateInfo(AppBorrowImageRequest appBorrowImageRequest);

	public AppBorrowImageResponse deleteInfo(AppBorrowImageRequest appBorrowImageRequest);

	/**
	 * 查询银行配置列表
	 * @param adminRequest
	 * @return
	 */
	public AdminBankConfigResponse bankConfigInit(AdminBankConfigRequest adminRequest);
	/**
	 * 根据id查询银行配置
	 * @param bankId
	 * @return
	 */
	public AdminBankConfigResponse selectBankConfigById(Integer bankId);
	/**
	 * 根据银行名称查询银行配置
	 * @return
	 */
	public List<BankConfigVO> getBankConfigRecordList(String bankName);

	/**
	 * 添加银行配置
	 * @param adminRequest
	 * @return
	 */
	public AdminBankConfigResponse insertBankConfigRecord(AdminBankConfigRequest adminRequest);

	/**
	 * 修改银行配置
	 * @param adminRequest
	 * @return
	 */
	public AdminBankConfigResponse updateBankConfigRecord(AdminBankConfigRequest adminRequest);
	/**
	 * 删除银行配置
	 * @param id
	 * @return
	 */
	public AdminBankConfigResponse deleteBankConfigById(Integer id);

	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @return
	 */
	public AdminBankConfigResponse uploadFile(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 保存之前的去重校验
	 * @param adminBankConfigRequest
	 * @return
	 */
	public AdminBankConfigResponse validateBeforeAction(AdminBankConfigRequest adminBankConfigRequest);

	/**
	 * 查询配置中心接口切换列表
	 * @param adminRequest
	 * @return
	 */
	public BankInterfaceResponse bankInterfaceInit(BankInterfaceRequest adminRequest);
	/**
	 * 查询配置中心接口切换详情页面
	 * @param adminRequest
	 * @return
	 */
	public BankInterfaceResponse bankInterfaceInfo(BankInterfaceRequest adminRequest);
	/**
	 * 修改 接口切换
	 * @return
	 */
	public BankInterfaceResponse updateBankIntefaceAction(BankInterfaceVO req);

	/**
	 * 删除快捷充值限额
	 * @return
	 */
	public BankInterfaceResponse deleteBankInterfaceConfig(BankInterfaceVO req);

	/**
	 * 查询快捷充值限额列表
	 * @param adminRequest
	 * @return
	 */
	public AdminBankRechargeConfigResponse bankRechargeInit(AdminBankRechargeConfigRequest adminRequest);
	/**
	 * 查询快捷充值限额详情页面
	 * @param adminRequest
	 * @return
	 */
	public AdminBankRechargeConfigResponse selectBankRechargeConfigInfo(AdminBankRechargeConfigRequest adminRequest);

	/**
	 * 编辑保存快捷充值限额
	 * @return
	 */
	public AdminBankRechargeConfigResponse saveBankRechargeConfig(AdminBankRechargeConfigRequest req);

	/**
	 * 修改快捷充值限额
	 * @return
	 */
	public AdminBankRechargeConfigResponse updateBankRechargeConfig(AdminBankRechargeConfigRequest req);

	/**
	 * 删除快捷充值限额
	 * @return
	 */
	public AdminBankRechargeConfigResponse deleteBankRechargeConfig(AdminBankRechargeConfigRequest req);
	/**
	 * 查询快捷充值限额列表--导出
	 * @param adminRequest
	 * @return
	 */
	public List<BankRechargeLimitConfigVO> exportRecordList(BankRechargeLimitConfigVO adminRequest);

	/**
	 * 获取银行列表(快捷支付卡)
	 * @return
	 */
	public List<BankConfigVO> getBankRecordList();

	/**
	 * 查询返回码配置列表
	 * @param adminRequest
	 * @return
	 */
	public BankReturnCodeConfigResponse selectBankRetcodeListByPage(AdminBankRetcodeConfigRequest adminRequest);
	/**
	 * 查询返回码配置详情页面
	 * @param adminRequest
	 * @return
	 */
	public BankReturnCodeConfigResponse selectBankRetcodeConfigInfo(AdminBankRetcodeConfigRequest adminRequest);

	/**
	 * 编辑保存返回码配置
	 * @return
	 */
	public BankReturnCodeConfigResponse insertBankReturnCodeConfig(AdminBankRetcodeConfigRequest req);

	/**
	 * 修改返回码配置
	 * @return
	 */
	public BankReturnCodeConfigResponse updateBankReturnCodeConfig(AdminBankRetcodeConfigRequest req);

	/**
	 * 根据主键判断维护中数据是否存在
	 *
	 * @return
	 */
	boolean isExistsReturnCode(AdminBankRetcodeConfigRequest record);
	/**
	 * 根据条件判断该条数据在数据库中是否存在
	 * @param adminRequest
	 * @return
	 */
	public boolean isExistsRecord(AdminBankRetcodeConfigRequest adminRequest);

	/**
	 *（条件）列表查询
	 * @param request
	 * @return
	 */
	AdminBankSettingResponse selectBankSettingList(AdminBankSettingRequest request);

	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 * @param request
	 * @return
	 */
	AdminBankSettingResponse getRecord(AdminBankSettingRequest request);

	/**
	 * （条件）数据查询
	 * @param bank
	 * @param limitStart
	 * @param limitEnd
	 * @return
	 */
	List<JxBankConfigVO> getRecordList(JxBankConfigVO bank, int limitStart, int limitEnd);

	/**
	 * 添加
	 * @param request
	 * @return
	 */
	AdminBankSettingResponse insertRecord(AdminBankSettingRequest request);

	/**
	 * 修改 江西银行 银行卡配置
	 * @param request
	 * @return
	 */
	AdminBankSettingResponse updateRecord(AdminBankSettingRequest request);

	/**
	 * 删除 江西银行 银行卡配置
	 * @param request
	 * @return
	 */
	AdminBankSettingResponse deleteRecord(AdminBankSettingRequest request);

	/**
	 * 江西银行 资料上传
	 * @param request
	 * @return
	 */
	AdminBankSettingResponse uploadFile(HttpServletRequest request);

	/**
	 *（条件）列表查询--其他相关字段
	 * @param
	 * @return
	 */
	List<ParamNameVO> selectProjectTypeParamList();

	ContentArticleResponse searchAction(ContentArticleRequest contentArticleRequestBean);

	ContentArticleResponse inserAction(ContentArticleRequest contentArticleRequestBean);

	ContentArticleResponse updateAction(ContentArticleRequest contentArticleRequestBean);

	ContentArticleResponse deleteContentArticleById(Integer id);

	/**
	 * @Author walter.limeng
	 * @Description  获取帮助中心数据
	 * @Date 16:23 2018/7/20
	 * @Param categoryBeanRequest
	 * @return
	 */
	CategoryResponse getCategoryPage(CategoryBeanRequest categoryBeanRequest);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  二级菜单联动
	 * @Date 19:22 2018/7/23
	 * @Param categoryBeanRequest
	 * @return
	 */
	CategoryResponse changeSubTypeAction(CategoryBeanRequest categoryBeanRequest);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  根据主键ID获取数据
	 * @Date 9:21 2018/7/24
	 * @Param id
	 * @return
	 */
	CategoryResponse infoTypeAction(Integer id);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  跳转到添加子分类的页面
	 * @Date 9:28 2018/7/24
	 * @Param categoryBeanRequest
	 * @return
	 */
	CategoryResponse infoSubTypeAction(CategoryBeanRequest categoryBeanRequest);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  添加分类
	 * @Date 9:40 2018/7/24
	 * @Param categoryVO
	 * @return Integer
	 */
	Integer insertCategory(CategoryVO categoryVO);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  修改分类
	 * @Date 9:54 2018/7/24
	 * @Param categoryVO
	 * @return Integer
	 */
	Integer updateAction(CategoryVO categoryVO);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  根据父级菜单查询子级菜单
	 * @Date 10:12 2018/7/24
	 * @Param categoryVO
	 * @return
	 */
	CategoryResponse getCategoryCount(CategoryVO categoryVO);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  根据pCateId和cateId查询总数
	 * @Date 10:23 2018/7/24
	 * @Param pid
	 * @Param cid
	 * @return
	 */
	Integer getCountByPcateIdAndcateId(Integer pid, Integer cid);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  根据pCateId和cateId查询列表
	 * @Date 10:45 2018/7/24
	 * @Param pid
	 * @Param cid
	 * @return
	 */
	List<ContentHelpVO> getListByPcateIdAndcateId(Integer pid, Integer cid);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  删除ContentHelp
	 * @Date 10:57 2018/7/24
	 * @Param id
	 * @return
	 */
	Integer delContentHelp(Integer id);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  删除Category
	 * @Date 10:58 2018/7/24
	 * @Param id
	 * @return
	 */
	Integer delCategory(Integer id);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  修改contentHelp
	 * @Date 10:58 2018/7/24
	 * @Param help
	 * @return
	 */
	Integer updateContentHelp(ContentHelpVO contentHelpVO);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  问题列表查询数据
	 * @Date 11:25 2018/7/24
	 * @Param categoryBeanRequest
	 * @return
	 */
	CategoryResponse getHelpPage(ContentHelpBeanRequest contentHelpBeanRequest);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  列表页跳转详情页(含有id更新，不含有id添加)
	 * @Date 14:07 2018/7/24
	 * @Param contentHelpBeanRequest
	 * @return
	 */
	CategoryResponse getHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  添加问题
	 * @Date 14:15 2018/7/24
	 * @Param contentHelpBeanRequest
	 * @return
	 */
	CategoryResponse insertHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  修改问题
	 * @Date 14:22 2018/7/24
	 * @Param contentHelpBeanRequest
	 * @return
	 */
	CategoryResponse updateHelpAction(ContentHelpBeanRequest contentHelpBeanRequest);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  修改问题状态
	 * @Date 14:34 2018/7/24
	 * @Param status  0:关闭   1：开启
	 * @Param zhichistatus  0:关闭   1：开启
	 * @return
	 */
	Integer chanContentHelp(Integer contentId, Integer status, Integer zhiChiStatus);

	/**
	 * @Author walter.limeng
	 * @user walter.limeng
	 * @Description  查询常见问题分页数据
	 * @Date 14:57 2018/7/24
	 * @Param contentHelpBeanRequest
	 * @return
	 */
	CategoryResponse getOftenInitPage(ContentHelpBeanRequest contentHelpBeanRequest);

	/**
	 * 根据条件查询内容管理-办公环境
	 *
	 * @param requestBean
	 * @return
	 */
	ContentEnvironmentResponse searchAction(ContentEnvironmentRequestBean requestBean);

	/**
	 * 添加公司内容管理-办公环境
	 *
	 * @param requestBean
	 * @return
	 */
	ContentEnvironmentResponse insertAction(ContentEnvironmentRequestBean requestBean);

	/**
	 * 修改内容管理-办公环境
	 *
	 * @param requestBean
	 * @return
	 */
	ContentEnvironmentResponse updateAction(ContentEnvironmentRequestBean requestBean);

	/**
	 * 根据id查询内容管理-办公环境
	 *
	 * @param id
	 * @return
	 */
	ContentEnvironmentVO getRecord(Integer id);

	/**
	 * 删除内容管理-办公环境状态
	 *
	 * @param id
	 * @return
	 */
	ContentEnvironmentResponse deleteContentEnvironmentById(Integer id);

	/**
	 * 根据条件查询着路页管理列表
	 *
	 * @param requestBean
	 * @return
	 */
	LandingPageResponse searchAction(ContentLandingPageRequestBean requestBean);

	/**
	 * 添加着路页管理
	 *
	 * @param requestBean
	 * @return
	 */
	LandingPageResponse insertAction(ContentLandingPageRequestBean requestBean);

	/**
	 * 修改着路页管理
	 *
	 * @param requestBean
	 * @return
	 */
	LandingPageResponse updateAction(ContentLandingPageRequestBean requestBean);

	/**
	 * 根据id删除着路页管理
	 *
	 * @param id
	 * @return
	 */
	LandingPageResponse deleteLandingPageById(Integer id);

	/**
	 * 根据条件查询公司管理-合作伙伴
	 *
	 * @param requestBean
	 * @return
	 */
	LinkResponse searchAction(ContentPartnerRequestBean requestBean);

	/**
	 * 添加公司管理-合作伙伴
	 *
	 * @param requestBean
	 * @return
	 */
	LinkResponse insertAction(ContentPartnerRequestBean requestBean);

	/**
	 * 修改公司管理-合作伙伴
	 *
	 * @param requestBean
	 * @return
	 */
	LinkResponse updateAction(ContentPartnerRequestBean requestBean);

	/**
	 * 根据id获取公司管理-合作伙伴
	 *
	 * @param id
	 * @return
	 */
	LinkVO getLinkRecord(Integer id);

	/**
	 * 根据id删除公司管理-合作伙伴
	 *
	 * @param id
	 * @return
	 */
	LinkResponse deleteLinkById(Integer id);

	/**
	 * 公司管理-资质荣誉列表查询
	 *
	 * @param requestBean
	 * @return
	 */
	ContentQualifyResponse searchAction(ContentQualifyRequestBean requestBean);

	/**
	 * 添加公司管理-资质荣誉
	 *
	 * @param requestBean
	 * @return
	 */
	ContentQualifyResponse insertAction(ContentQualifyRequestBean requestBean);

	/**
	 * 修改公司管理-资质荣誉
	 *
	 * @param requestBean
	 * @return
	 */
	ContentQualifyResponse updateAction(ContentQualifyRequestBean requestBean);

	/**
	 * 修改公司管理-资质荣誉状态
	 *
	 * @param id
	 * @return
	 */
	ContentQualifyVO getContentQualifyRecord(Integer id);

	/**
	 * 删除公司管理-资质荣誉状态
	 *
	 * @param id
	 * @return
	 */
	ContentQualifyResponse deleteContentQualifyById(Integer id);

	/**
	 * 获取优惠券列表
	 * @param adminCouponCheckRequest
	 * @return
	 */
	CouponCheckResponse getCouponList(AdminCouponCheckRequest adminCouponCheckRequest);

	/**
	 * 删除优惠券信息
	 * @param acr
	 * @return
	 */
	CouponCheckResponse deleteCouponList(AdminCouponCheckRequest acr);

	/**
	 * 插入优惠券信息
	 * @param accr
	 * @return
	 */
	CouponCheckResponse insert(AdminCouponCheckRequest accr);

	/**
	 * 根据id查询优惠券
	 * @param id
	 * @return
	 */
	CouponCheckVO selectCoupon(Integer id);

	/**
	 * 修改审核状态
	 * @param request
	 * @return
	 */
	CouponCheckResponse updateCoupon(AdminCouponCheckRequest request);

	/**
	 * 根据用户ID获取admin中用户名
	 * @param userId 用户ID
	 * @return String admimusername
	 */
	CouponTenderResponse getAdminUserByUserId(String userId);

	/**
	 * 根据条件查询公司管理-公司记事
	 *
	 * @param requestBean
	 * @return
	 */
	EventResponse searchAction(EventRequestBean requestBean);

	/**
	 * 添加公司管理-公司记事
	 *
	 * @param requestBean
	 * @return
	 */
	EventResponse insertAction(EventRequestBean requestBean);

	/**
	 * 修改公司管理-公司记事
	 *
	 * @param requestBean
	 * @return
	 */
	EventResponse updateAction(EventRequestBean requestBean);

	/**
	 * 根据id查找公司管理-公司记事
	 *
	 * @param id
	 * @return
	 */
	EventVO getEventRecord(Integer id);

	/**
	 * 根据id删除公司管理-公司记事状态
	 *
	 * @param id
	 * @return
	 */
	EventResponse deleteEventById(Integer id);

	/**
	 * 查询手续费配置列表
	 * @param request
	 * @return
	 */
	public AdminFeeConfigResponse selectFeeConfigList(AdminFeeConfigRequest request);

	/**
	 * 查询银行列表
	 * @param bank
	 * @return
	 */
	public List<BankConfigVO> getBankConfigList(BankConfigVO bank);
	/**
	 * 获取手续费列表列表
	 * @param bank
	 * @return
	 */
	public List<BankConfigVO> getBankConfigRecordList(BankConfigVO bank,int limitStart,int limitEnd);
	/**
	 * 查询手续费配置详情页面
	 * @param adminRequest
	 * @return
	 */
	public AdminFeeConfigResponse selectFeeConfigInfo(AdminFeeConfigRequest adminRequest);

	/**
	 * 编辑保存手续费配置
	 * @return
	 */
	public AdminFeeConfigResponse insertBankConfigRecord(AdminFeeConfigRequest req);

	/**
	 * 修改手续费配置
	 * @return
	 */
	public AdminFeeConfigResponse updateBankConfigRecord(AdminFeeConfigRequest req);

	/**
	 * 删除手续费配置
	 * @return
	 */
	public AdminFeeConfigResponse deleteFeeConfig(AdminFeeConfigRequest req);

	/**
	 * 查询列表
	 * @param adminRequest
	 * @return
	 */
	public HolidaysConfigResponse initHolidaysConfig(AdminHolidaysConfigRequest adminRequest);
	/**
	 * 查询节假日配置详情页面
	 * @param id
	 * @return
	 */
	public HolidaysConfigResponse getHolidaysConfigById(Integer id);

	/**
	 * 编辑保存节假日配置
	 * @return
	 */
	public HolidaysConfigResponse saveHolidaysConfig(AdminHolidaysConfigRequest req);

	/**
	 * 修改节假日配置
	 * @return
	 */
	public HolidaysConfigResponse updateHolidaysConfig(AdminHolidaysConfigRequest req);

	/**查询用户权限
	 *
	 * @param userName
	 * @return
	 */
	public List<AdminSystemVO> getUserPermission(String userName);

	/**查询用户信息
	 *
	 * @param adminSystemRequest
	 * @return
	 */
	public AdminSystemResponse getUserInfo(AdminSystemRequest adminSystemRequest);

	/**查询菜单权限
	 *
	 * @param id
	 * @return
	 */
	public List<TreeVO> selectLeftMenuTree2(String id);

	/**
	 * 查询所有邮件模板
	 *
	 * @return
	 */
	List<SmsMailTemplateVO> findMailAll();

	/**
	 * 根据条件查询邮件模板
	 *
	 * @param request
	 * @return
	 */
	List<SmsMailTemplateVO> findMailTemplate(MailTemplateRequest request);

	/**
	 * 新增邮件模板
	 *
	 * @param request
	 * @return
	 */
	void insertMailTemplate(MailTemplateRequest request);

	/**
	 * 查询所有消息推送模板
	 *
	 * @return
	 */
	List<MessagePushTemplateVO> findAll();

	/**
	 * 根据条件查询消息推送模板
	 *
	 * @param request
	 * @return
	 */
	List<MessagePushTemplateVO> findMsgPushTemplate(MsgPushTemplateRequest request);

	/**
	 * 新增消息推送模板
	 *
	 * @param request
	 */
	void insertMsgPushTemplate(MsgPushTemplateRequest request);

	/**
	 * 根据tagName获取tagCode
	 *
	 * @param tagName
	 * @return
	 */
	MessagePushTagVO findMsgTagByTagName(String tagName);

	/**
	 * 获取网站配置
	 *
	 * @return
	 */
	SiteSettingsResponse selectSiteSetting();

	/**
	 * 修改网站配置
	 *
	 * @param requestBean
	 * @return
	 */
	SiteSettingsResponse updateAction(SiteSettingRequestBean requestBean);

	/**
	 * 查询所有短信模版
	 *
	 * @return
	 */
	List<SmsTemplateVO> findSmsAll();

	/**
	 * 根据条件查询所有短信模版
	 *
	 * @param request
	 * @return
	 */
	List<SmsTemplateVO> findSmsTemplate(SmsTemplateRequest request);

	/**
	 * 新增短信模版
	 *
	 * @param request
	 * @return
	 */
	void insertSmsTemplate(SmsTemplateRequest request);

	public SubmissionsResponse findSubmissionsList(SubmissionsRequest form);

	public SubmissionsResponse updateSubmissionsStatus(SubmissionsRequest form);

	public SubmissionsResponse exportSubmissionsList(SubmissionsRequest form);

	/**
	 * 根据条件查询公司管理-团队介绍列表
	 *
	 * @param requestBean
	 * @return
	 */
	TeamResponse searchAction(TeamRequestBean requestBean);

	/**
	 * 保存公司管理-团队介绍
	 *
	 * @param requestBean
	 * @return
	 */
	TeamResponse insertAction(TeamRequestBean requestBean);

	/**
	 * 修改公司管理-团队介绍
	 *
	 * @param requestBean
	 * @return
	 */
	TeamResponse updateAction(TeamRequestBean requestBean);

	/**
	 * 根据id获取公司管理-团队介绍
	 *
	 * @param id
	 * @return
	 */
	TeamVO getTeamRecord(Integer id);

	/**
	 * 删除公司管理-团队介绍状态
	 *
	 * @param id
	 * @return
	 */
	TeamResponse deleteTeamById(Integer id);

	/**
	 * 查询版本设置列表
	 * @param adminRequest
	 * @return
	 */
	public AdminVersionResponse versionConfigInit(AdminVersionRequest adminRequest);
	/**
	 * 查询详情页面
	 * @param adminRequest
	 * @return
	 */
	public AdminVersionResponse searchVersionConfigInfo(AdminVersionRequest adminRequest);

	/**
	 * 编辑保存版本设置
	 * @return
	 */
	public AdminVersionResponse saveVersionConfig(AdminVersionRequest req);

	/**
	 * 修改版本设置
	 * @return
	 */
	public AdminVersionResponse updateVersionConfig(AdminVersionRequest req);

	/**
	 * 删除保证金配置
	 * @return
	 */
	public AdminVersionResponse deleteVersionConfig(Integer id);
	/**
	 * 校验版本号是否唯一
	 * @return
	 */
	public VersionVO getVersionByCode(Integer vid, Integer type, String version);

	public VersionConfigBeanResponse searchList(VersionConfigBeanRequest request);

	public VersionConfigBeanResponse searchInfo(VersionConfigBeanRequest request);

	public VersionConfigBeanResponse insertInfo(VersionConfigBeanRequest request);

	public VersionConfigBeanResponse updateInfo(VersionConfigBeanRequest request);

	public VersionConfigBeanResponse deleteInfo(VersionConfigBeanRequest request);

}
