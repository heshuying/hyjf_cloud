package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.am.bean.admin.LockedConfig;
import com.hyjf.am.response.*;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.admin.locked.LockedConfigResponse;
import com.hyjf.am.response.admin.locked.LockedUserMgrResponse;
import com.hyjf.am.response.admin.promotion.ChannelReconciliationResponse;
import com.hyjf.am.response.admin.promotion.PlatformUserCountCustomizeResponse;
import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.admin.locked.LockedeUserListRequest;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.resquest.config.VersionConfigBeanRequest;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.resquest.trade.DadaCenterCouponCustomizeRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.config.SubmissionsVO;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author tanyy
 * @version AmAdminClientImpl, v0.1 2018/8/23 20:01
 */
@Service
public class AmAdminClientImpl implements AmAdminClient {
    private static Logger logger = LoggerFactory.getLogger(AmAdminClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public IntegerResponse countList(ChannelStatisticsDetailRequest request){
        return restTemplate.
                postForEntity("http://AM-ADMIN/am-admin/extensioncenter/channelstatisticsdetail/count", request, IntegerResponse.class).getBody();
    }
    @Override
    public ChannelStatisticsDetailResponse searchAction(ChannelStatisticsDetailRequest request){
        return restTemplate.
                postForEntity("http://AM-ADMIN/am-admin/extensioncenter/channelstatisticsdetail/searchaction", request, ChannelStatisticsDetailResponse.class).getBody();
    }
    @Override
    public List<UtmPlatVO> getPCUtm(){
        UtmPlatResponse response =  restTemplate.
                postForEntity("http://AM-ADMIN/am-admin/extensioncenter/channelstatisticsdetail/pcutm_list", null, UtmPlatResponse.class).getBody();
        if (UtmPlatResponse.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<UtmPlatVO> getAppUtm(){
        UtmPlatResponse response =  restTemplate.
                postForEntity("http://AM-ADMIN/am-admin/extensioncenter/channelstatisticsdetail/app_utm_list", null, UtmPlatResponse.class).getBody();
        if (UtmPlatResponse.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取保证金配置总数
     *
     * @param request
     * @return
     */
    @Override
    public Integer selectBailConfigCount(BailConfigRequest request) {
        String url = "http://AM-ADMIN/am-trade/bail_config/select_bail_config_count";
        IntegerResponse response = restTemplate.postForEntity(url,request,IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 获取保证金配置列表
     *
     * @param request
     * @return
     */
    @Override
    public List<BailConfigCustomizeVO> selectBailConfigRecordList(BailConfigRequest request) {
        String url = "http://AM-ADMIN/am-trade/bail_config/select_bail_config_record_list";
        BailConfigCustomizeResponse response = restTemplate.postForEntity(url,request,BailConfigCustomizeResponse.class).getBody();
        if (BailConfigCustomizeResponse.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据主键获取保证金配置
     *
     * @param id
     * @return
     */
    @Override
    public BailConfigInfoCustomizeVO selectBailConfigById(Integer id) {
        String url = "http://AM-ADMIN/am-trade/bail_config/select_bail_config_by_id/" + id;
        BailConfigInfoCustomizeResponse response = restTemplate.getForEntity(url,BailConfigInfoCustomizeResponse.class).getBody();
        if (BailConfigInfoCustomizeResponse.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 未配置保证金的机构编号
     *
     * @return
     */
    @Override
    public List<HjhInstConfigVO> selectNoUsedInstConfigList() {
        String url = "http://AM-ADMIN/am-trade/bail_config/select_noused_inst_config_list";
        HjhInstConfigResponse response = restTemplate.getForEntity(url,HjhInstConfigResponse.class).getBody();
        if (HjhInstConfigResponse.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 添加保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    @Override
    public boolean insertBailConfig(BailConfigAddRequest bailConfigAddRequest) {
        String url = "http://AM-ADMIN/am-trade/bail_config/insert_bail_config";
        BooleanResponse response = restTemplate.postForEntity(url, bailConfigAddRequest, BooleanResponse.class).getBody();
        return response.getResultBoolean();
    }

    /**
     * 周期内发标已发额度
     *
     * @param bailConfigAddRequest
     * @return
     */
    @Override
    public String selectSendedAccountByCyc(BailConfigAddRequest bailConfigAddRequest) {
        String url = "http://AM-ADMIN/am-trade/bail_config/select_sended_account_by_cyc";
        StringResponse response = restTemplate.postForEntity(url,bailConfigAddRequest,StringResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultStr();
        }
        return null;
    }

    /**
     * 根据该机构可用还款方式更新可用授信方式
     *
     * @param instCode
     * @return
     */
    @Override
    public boolean updateBailInfoDelFlg(String instCode) {
        String url = "http://AM-ADMIN/am-trade/bail_config/update_bail_info_delflg/" + instCode;
        BooleanResponse response = restTemplate.getForEntity(url, BooleanResponse.class).getBody();
        return response.getResultBoolean();
    }

    /**
     * 更新保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    @Override
    public boolean updateBailConfig(BailConfigAddRequest bailConfigAddRequest) {
        String url = "http://AM-ADMIN/am-trade/bail_config/update_bail_config";
        BooleanResponse response = restTemplate.postForEntity(url, bailConfigAddRequest, BooleanResponse.class).getBody();
        return response.getResultBoolean();
    }

    /**
     * 删除保证金配置
     *
     * @param bailConfigAddRequest
     * @return
     */
    @Override
    public boolean deleteBailConfig(BailConfigAddRequest bailConfigAddRequest) {
        String url = "http://AM-ADMIN/am-trade/bail_config/delete_bail_config";
        BooleanResponse response = restTemplate.postForEntity(url, bailConfigAddRequest, BooleanResponse.class).getBody();
        return response.getResultBoolean();
    }

    /**
     * 获取当前机构可用还款方式
     *
     * @param instCode
     * @return
     */
    @Override
    public List<String> selectRepayMethod(String instCode) {
        String url = "http://AM-ADMIN/am-trade/bail_config/select_repay_method/" + instCode;
        Response response = restTemplate.getForEntity(url, BooleanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public STZHWhiteListResponse getUserByUserName(STZHWhiteListRequestBean requestBean) {
        String url = "http://AM-ADMIN/am-admin/stzfwhiteconfig/getUserByUserName";
        STZHWhiteListResponse response = restTemplate.postForEntity(url,requestBean,STZHWhiteListResponse.class).getBody();
        if (STZHWhiteListResponse.isSuccess(response)) {
            return response;
        }
        return null;
    }

    /**
     * 还款方式下拉列表
     *
     * @param
     * @return
     * @author wangjun
     * yangchangwei 迁移至amadminClient
     */
    @Override
    public List<BorrowStyleVO> selectCommonBorrowStyleList() {
        String url = "http://AM-ADMIN/am-trade/admin_common/select_borrow_style";
        BorrowStyleResponse response = restTemplate.getForEntity(url, BorrowStyleResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询汇计划转让列表
     *
     * @param request
     * @return
     * yangchangwei
     */
    @Override
    public HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request) {

        HjhDebtCreditReponse response = restTemplate.
                postForEntity("http://AM-ADMIN/am-trade/adminHjhDebtCredit/getList", request, HjhDebtCreditReponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查询手续费分账count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPoundageCount(PoundageListRequest request) {
        String url = "http://AM-ADMIN/am-admin/poundage/getPoundageCount";
        PoundageCustomizeResponse response = restTemplate.postForEntity(url, request, PoundageCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }
    /**
     * 查询手续费分账list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<PoundageCustomizeVO> searchPoundageList(PoundageListRequest request) {
        String url = "http://AM-ADMIN/am-admin/poundage/searchPoundageList";
        PoundageCustomizeResponse response = restTemplate.postForEntity(url, request, PoundageCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取手续费分账数额总计
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public PoundageCustomizeVO getPoundageSum(PoundageListRequest request) {
        String url = "http://AM-ADMIN/am-admin/poundage/getPoundageSum";
        PoundageCustomizeResponse response = restTemplate.postForEntity(url, request, PoundageCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据id查询手续费分账信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public PoundageCustomizeVO getPoundageById(Integer id) {
        String url = "http://AM-ADMIN/am-admin/poundage/getPoundageById/" + id;
        PoundageCustomizeResponse response = restTemplate.getForEntity(url,PoundageCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 审核-更新poundage表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public Integer updatePoundage(PoundageCustomizeVO poundageCustomizeVO) {
        String url = "http://AM-ADMIN/am-admin/poundage/updatePoundage";
        PoundageCustomizeResponse response = restTemplate.postForEntity(url, poundageCustomizeVO, PoundageCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 查询批次中心-批次放款列表
     * yangchangwei
     * @param request
     * @return
     */
    @Override
    public BatchBorrowRecoverReponse getBatchBorrowRecoverList(BatchBorrowRecoverRequest request) {
        BatchBorrowRecoverReponse response = restTemplate.
                postForEntity("http://AM-ADMIN/am-admin/adminBatchBorrowRecover/getList", request, BatchBorrowRecoverReponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查询批次中心的批次列表求和
     * yangchangwei
     * @param request
     * @return
     */
    @Override
    public BatchBorrowRecoverReponse getBatchBorrowCenterListSum(BatchBorrowRecoverRequest request) {
        BatchBorrowRecoverReponse response = restTemplate.
                postForEntity("http://AM-ADMIN/am-admin/adminBatchBorrowRecover/getListSum", request, BatchBorrowRecoverReponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * yangchangwei
     * 根据id 获取放款任务表
     *
     * @param apicronID
     * @return
     */
    @Override
    public BorrowApicronResponse getBorrowApicronByID(String apicronID) {
        String url = "http://AM-ADMIN/am-admin/adminBatchBorrowRecover/getRecoverApicronByID/" + apicronID;
        BorrowApicronResponse response = restTemplate.getForEntity(url,  BorrowApicronResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 资产来源
     * yangchangwei
     * @return
     */
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigList() {
        String url = "http://AM-ADMIN/am-trade/hjhInstConfig/selectInstConfigAll";
        HjhInstConfigResponse response = restTemplate.getForEntity(url, HjhInstConfigResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据用户名查询分账名单是否存在
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    @Override
    public AdminSubConfigResponse subconfig(AdminSubConfigRequest adminRequest){
        String url = "http://AM-ADMIN/am-admin/config/subconfig/isExist";
        AdminSubConfigResponse response = restTemplate.postForEntity(url,adminRequest, AdminSubConfigResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response;
        }
        return null;
    }

    /**
     * 查询权限数量
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPermissionsCount(AdminPermissionsRequest request) {
        String url = "http://AM-ADMIN/am-admin/permissions/getPermissionsCount";
        AdminPermissionsResponse response = restTemplate.postForEntity(url, request, AdminPermissionsResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 查询权限列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AdminPermissionsVO> searchPermissionsList(AdminPermissionsRequest request) {
        String url = "http://AM-ADMIN/am-admin/permissions/searchPermissionsList";
        AdminPermissionsResponse response = restTemplate.postForEntity(url,request, AdminPermissionsResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 检查数据库是否已存在该权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean isExistsPermission(AdminPermissionsVO adminPermissionsVO) {
        String url = "http://AM-ADMIN/am-admin/permissions/isExistsPermission";
        BooleanResponse response = restTemplate.postForEntity(url,adminPermissionsVO, BooleanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultBoolean();
        }
        return true;
    }

    /**
     * 插入权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int insertPermission(AdminPermissionsVO adminPermissionsVO) {
        String url = "http://AM-ADMIN/am-admin/permissions/insertPermission";
        AdminPermissionsResponse response = restTemplate.postForEntity(url,adminPermissionsVO, AdminPermissionsResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 修改权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int updatePermission(AdminPermissionsVO adminPermissionsVO) {
        String url = "http://AM-ADMIN/am-admin/permissions/updatePermission";
        AdminPermissionsResponse response = restTemplate.postForEntity(url,adminPermissionsVO, AdminPermissionsResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 根据uuid查询权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public AdminPermissionsVO searchPermissionByUuid(String uuid) {
        String url = "http://AM-ADMIN/am-admin/permissions/searchPermissionByUuid/" + uuid;
        AdminPermissionsResponse response = restTemplate.getForEntity(url,AdminPermissionsResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 删除权限
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int deletePermission(String uuid) {
        String url = "http://AM-ADMIN/am-admin/permissions/deletePermission/" + uuid;
        AdminPermissionsResponse response = restTemplate.getForEntity(url,AdminPermissionsResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 查询数据字典总数
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getParamNamesCount(AdminParamNameRequest request) {
        String url = "http://AM-ADMIN/am-admin/paramname/getParamNamesCount";
        ParamNameResponse response = restTemplate.postForEntity(url,request,ParamNameResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 查询数据字典列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<ParamNameVO> searchParamNamesList(AdminParamNameRequest request) {
        String url = "http://AM-ADMIN/am-admin/paramname/searchParamNamesList";
        ParamNameResponse response = restTemplate.postForEntity(url,request,ParamNameResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 检查paramName是否存在
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean isExistsParamName(ParamNameVO paramNameVO) {
        String url = "http://AM-ADMIN/am-admin/paramname/isExistsParamName";
        BooleanResponse response = restTemplate.postForEntity(url,paramNameVO, BooleanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultBoolean();
        }
        return true;
    }

    /**
     * 添加数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int insertParamName(ParamNameVO paramNameVO) {
        String url = "http://AM-ADMIN/am-admin/paramname/insertParamName";
        ParamNameResponse response = restTemplate.postForEntity(url,paramNameVO,ParamNameResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 更新数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int updateParamName(ParamNameVO paramNameVO) {
        String url = "http://AM-ADMIN/am-admin/paramname/updateParamName";
        ParamNameResponse response = restTemplate.postForEntity(url,paramNameVO,ParamNameResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 根据联合主键查询数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public ParamNameVO searchParamNameByKey(ParamNameVO paramNameVO) {
        String url = "http://AM-ADMIN/am-admin/paramname/searchParamNameByKey";
        ParamNameResponse response = restTemplate.postForEntity(url,paramNameVO,ParamNameResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 删除数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int deleteParamName(ParamNameVO paramNameVO) {
        String url = "http://AM-ADMIN/am-admin/paramname/deleteParamName";
        ParamNameResponse response = restTemplate.postForEntity(url,paramNameVO,ParamNameResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 同步数据字典至redis
     * @return
     */
    @Override
    public boolean syncParam() {
        String url = "http://AM-ADMIN/am-config/sync/synParam";
        return restTemplate.getForEntity(url,Boolean.class).getBody();
    }

    /**
     * 查询手续费分账配置
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public PoundageLedgerVO getPoundageLedgerById(int id) {
        String url = "http://AM-ADMIN/am-admin/poundage/getPoundageLedgerById/" + id;
        PoundageLedgerResponse response = restTemplate.getForEntity(url,PoundageLedgerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }
    /**
     * 手续费分账详细信息总数
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPoundageDetailCount(AdminPoundageDetailRequest request) {
        String url = "http://AM-ADMIN/am-admin/poundage/getPoundageDetailCount";
        AdminPoundageDetailResponse response = restTemplate.postForEntity(url,request,AdminPoundageDetailResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }
    /**
     * 手续费分账详细信息列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<PoundageDetailVO> searchPoundageDetailList(AdminPoundageDetailRequest request) {
        String url = "http://AM-ADMIN/am-admin/poundage/searchPoundageDetailList";
        AdminPoundageDetailResponse response = restTemplate.postForEntity(url,request,AdminPoundageDetailResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<DataCenterCouponCustomizeVO> getDataCenterCouponList(DadaCenterCouponRequestBean requestBean, String type) {
        if (requestBean != null) {
            requestBean.setType(type);
        }
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/datacenter/coupon/getdatacentercouponlist",
                requestBean, DataCenterCouponResponse.class).getResultList();
    }

    @Override
    public String getActivityTitle(Integer activityId) {
        CouponTenderResponse response = restTemplate
                .getForEntity("http://AM-ADMIN/am-admin/datacenter/coupon/hztgetactivitytitle/" + activityId,
                        CouponTenderResponse.class)
                .getBody();
        if (response != null) {
            return response.getAttrbute();
        }
        return null;
    }


    @Override
    public List<DataCenterCouponCustomizeVO> getRecordListDJ(DataCenterCouponCustomizeVO dataCenterCouponCustomize) {
        DataCenterCouponCustomizeResponse response = restTemplate.postForObject(
                "http://AM-ADMIN/am-admin/datacenter/coupon/get_record_list_dj", dataCenterCouponCustomize,
                DataCenterCouponCustomizeResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<DataCenterCouponCustomizeVO> getRecordListJX(DataCenterCouponCustomizeVO dataCenterCouponCustomize) {
        DadaCenterCouponCustomizeRequest request = new DadaCenterCouponCustomizeRequest();
        DataCenterCouponCustomizeResponse response = restTemplate.postForObject(
                "http://AM-ADMIN/am-admin/datacenter/coupon/get_record_list_jx", request,
                DataCenterCouponCustomizeResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public PlatformCountCustomizeResponse searchAction(PlatformCountRequestBean requestBean) {
        // 获取投资信息
        PlatformCountCustomizeResponse response = restTemplate.postForObject(
                "http://AM-ADMIN/am-admin/platform_count/search_action", requestBean,
                PlatformCountCustomizeResponse.class);
        if (response != null) {
            return response;
        }
        return null;
    }
    @Override
    public PlatformUserCountCustomizeResponse searchRegistAcount(PlatformCountRequestBean requestBean) {
        // 获取投资信息
        PlatformUserCountCustomizeResponse response = restTemplate.postForObject(
                "http://AM-ADMIN/am-user/platform_count/get_info", requestBean,
                PlatformUserCountCustomizeResponse.class);
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public LockedUserMgrResponse getLockedUserList(LockedeUserListRequest request, boolean isFront) {
        String url="http://AM-ADMIN/am-user/lockeduser/frontlist";
        if(!isFront){
            url="http://AM-ADMIN/am-user/lockeduser/adminlist";
        }
        return restTemplate.postForObject(url,request,LockedUserMgrResponse.class);
    }

    @Override
    public BooleanResponse unlock(LockedUserInfoVO vo, boolean isFront) {
        String url="http://AM-ADMIN/am-user/lockeduser/frontunlock";
        if(!isFront){
            url="http://AM-ADMIN/am-user/lockeduser/adminunlock";
        }
        return restTemplate.postForObject(url,vo,BooleanResponse.class);
    }

    @Override
	public ChannelReconciliationResponse selectPcChannelReconciliationRecord(ChannelReconciliationRequest request) {
		return restTemplate.postForObject(
				"http://AM-ADMIN/am-user/promotion/utm/select_pc_channel_reconciliation_record", request,
				ChannelReconciliationResponse.class);
	}

    @Override
    public ChannelReconciliationResponse selectPcChannelReconciliationRecordHjh(ChannelReconciliationRequest request) {
        return restTemplate.postForObject(
                "http://AM-ADMIN/am-user/promotion/utm/select_pc_channel_reconciliation_record_hjh", request,
                ChannelReconciliationResponse.class);
    }

    @Override
    public ChannelReconciliationResponse selectAppChannelReconciliationRecord(ChannelReconciliationRequest request) {
        return restTemplate.postForObject(
                "http://AM-ADMIN/am-user/promotion/utm/select_app_channel_reconciliation_record", request,
                ChannelReconciliationResponse.class);
    }

    @Override
    public ChannelReconciliationResponse selectAppChannelReconciliationRecordHjh(ChannelReconciliationRequest request) {
        return restTemplate.postForObject(
                "http://AM-ADMIN/am-user/promotion/utm/select_app_channel_reconciliation_record_hjh", request,
                ChannelReconciliationResponse.class);
    }

    @Override
    public SubmissionsVO getSubmissionsRecord(SubmissionsRequest request) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/submission/getSubmissionsRecord", request,
                SubmissionsVO.class);
    }
    /**
     * 查询列表数据
     *
     * @param form
     * @return
     */
    @Override
    public SubmissionsResponse findSubmissionsList(SubmissionsRequest form) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/submission/getRecordList", form,
                SubmissionsResponse.class);
    }

    /**
     * 查询导出数据
     *
     * @param form
     * @return
     */
    @Override
    public SubmissionsResponse exportSubmissionsList(SubmissionsRequest form) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/submission/getExportRecordList", form,
                SubmissionsResponse.class);
    }

    /**
     * 更新状态
     *
     * @param form
     * @return
     */
    @Override
    public SubmissionsResponse updateSubmissionsStatus(SubmissionsRequest form) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/submission/updateSubmissionsStatus", form,
                SubmissionsResponse.class);
    }



    @Override
    public VersionConfigBeanResponse searchList(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-config/appversion/getRecordList", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;
    }

    @Override
    public VersionConfigBeanResponse searchInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-config/appversion/infoAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;

    }

    @Override
    public VersionConfigBeanResponse insertInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-config/appversion/insertAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;

    }

    @Override
    public VersionConfigBeanResponse updateInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-config/appversion/updateAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;

    }

    @Override
    public VersionConfigBeanResponse deleteInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-config/appversion/deleteAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse searchList(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-config/appborrow/getRecordList", appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse searchInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-config/appborrow/infoAction", appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse insertInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-config/appborrow/insertAction", appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse updateInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-config/appborrow/updateAction", appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse deleteInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-config/appborrow/deleteAction", appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }
    @Override
    public AppBannerResponse getRecordById(AdsVO adsVO) {
        AppBannerResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-market/appconfig/getRecordById" ,adsVO,
                        AppBannerResponse.class)
                .getBody();

        return response;
    }
    @Override
    public AppBannerResponse findAppBannerList(AppBannerRequest request) {
        AppBannerResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-market/appconfig/getRecordList" ,request,
                        AppBannerResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBannerResponse insertAppBannerList(AdsVO adsVO) {
        AppBannerResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-market/appconfig/insertRecord" ,adsVO,
                        AppBannerResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBannerResponse updateAppBannerList(AdsVO adsVO) {
        AppBannerResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-market/appconfig/updateRecord" ,adsVO,
                        AppBannerResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBannerResponse updateAppBannerStatus(AdsVO adsVO) {
        AppBannerResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-market/appconfig/updateStatus" ,adsVO,
                        AppBannerResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBannerResponse deleteAppBanner(AdsVO adsVO) {
        AppBannerResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-market/appconfig/deleteAppBanner" ,adsVO,
                        AppBannerResponse.class)
                .getBody();
        return response;
    }

    @Override
    public LockedConfig.Config getFrontLockedCfg() {

        LockedConfigResponse response=restTemplate.getForObject("http://AM-ADMIN/am-admin/lockedconfig/webconfig",LockedConfigResponse.class);

        return response.getData();
    }

    @Override
    public LockedConfig.Config getAdminLockedCfg() {

        LockedConfigResponse response=restTemplate.getForObject("http://AM-ADMIN/am-admin/lockedconfig/adminconfig",LockedConfigResponse.class);

        return response.getData();
    }

    @Override
    public BooleanResponse saveFrontConfig(LockedConfig.Config webConfig) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/lockedconfig/savewebconfig",webConfig,BooleanResponse.class);
    }

    @Override
    public BooleanResponse saveAdminConfig(LockedConfig.Config adminConfig) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/lockedconfig/saveadminconfig",adminConfig,BooleanResponse.class);
    }

    /**
     * 获取保证金配置日志总数
     *
     * @param request
     * @return
     */
    @Override
    public Integer selectBailConfigLogCount(BailConfigLogRequest request) {
        String url = "http://AM-ADMIN/am-admin/bail_config_log/select_bail_config_log_count";
        IntegerResponse response = restTemplate.postForEntity(url,request,IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 获取保证金配置日志列表
     *
     * @param request
     * @return
     */
    @Override
    public List<BailConfigLogCustomizeVO> selectBailConfigLogList(BailConfigLogRequest request) {
        String url = "http://AM-ADMIN/am-admin/bail_config_log/select_bail_config_log_list";
        BailConfigLogCustomizeResponse response = restTemplate.postForEntity(url,request,BailConfigLogCustomizeResponse.class).getBody();
        if (BailConfigCustomizeResponse.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询异常标的总件数
     *
     * @param request
     * @return
     */
    @Override
    public Integer selectAssetExceptionCount(AssetExceptionRequest request) {
        String url = "http://AM-ADMIN/am-admin/asset_exception/select_asset_exception_count";
        IntegerResponse response = restTemplate.postForEntity(url,request,IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 查询异常标的列表
     *
     * @param request
     * @return
     */
    @Override
    public List<AssetExceptionCustomizeVO> selectAssetExceptionList(AssetExceptionRequest request) {
        String url = "http://AM-ADMIN/am-admin/asset_exception/select_asset_exception_list";
        AssetExceptionCustomizeResponse response = restTemplate.postForEntity(url,request,AssetExceptionCustomizeResponse.class).getBody();
        if (BailConfigCustomizeResponse.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 插入异常标的并更新保证金
     *
     * @param assetExceptionRequest
     * @return
     */
    @Override
    public boolean insertAssetException(AssetExceptionRequest assetExceptionRequest) {
        String url = "http://AM-ADMIN/am-admin/asset_exception/insert_asset_exception";
        BooleanResponse response = restTemplate.postForEntity(url, assetExceptionRequest, BooleanResponse.class).getBody();
        return response.getResultBoolean();
    }

    /**
     * 项目编号是否存在
     *
     * @param borrowNid
     * @return
     */
    @Override
    public String isExistsBorrow(String borrowNid) {
        String url = "http://AM-ADMIN/am-admin/asset_exception/is_exists_borrow/" + borrowNid;
        StringResponse response = restTemplate.getForEntity(url, StringResponse.class).getBody();
        return response.getResultStr();
    }

    /**
     * 删除异常标的
     *
     * @param assetExceptionRequest
     * @return
     */
    @Override
    public boolean deleteAssetException(AssetExceptionRequest assetExceptionRequest) {
        String url = "http://AM-ADMIN/am-admin/asset_exception/delete_asset_exception";
        BooleanResponse response = restTemplate.postForEntity(url, assetExceptionRequest, BooleanResponse.class).getBody();
        return response.getResultBoolean();
    }

    /**
     * 修改异常标的
     *
     * @param assetExceptionRequest
     * @return
     */
    @Override
    public boolean updateAssetException(AssetExceptionRequest assetExceptionRequest) {
        String url = "http://AM-ADMIN/am-admin/asset_exception/update_asset_exception";
        BooleanResponse response = restTemplate.postForEntity(url, assetExceptionRequest, BooleanResponse.class).getBody();
        return response.getResultBoolean();
    }

    /**
     * 处理平台转账
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int updateHandRechargeRecord(PlatformTransferRequest platformTransferRequest) {
        String url = "http://AM-ADMIN/am-trade/platformtransfer/updateHandRechargeRecord";
        PlatformTransferResponse response = restTemplate.postForEntity(url,platformTransferRequest,PlatformTransferResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    @Override
    public EmailRecipientResponse getRecordList(EmailRecipientRequest recipientRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/sell_daily_email/getRecordList", recipientRequest, EmailRecipientResponse.class).getBody();
    }

    @Override
    public EmailRecipientResponse getRecordById(EmailRecipientRequest recipientRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/sell_daily_email/getRecordById", recipientRequest, EmailRecipientResponse.class).getBody();
    }

    @Override
    public EmailRecipientResponse updateEmailRecipient(EmailRecipientRequest recipientRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/sell_daily_email/updateEmailRecipient", recipientRequest, EmailRecipientResponse.class).getBody();
    }

    @Override
    public EmailRecipientResponse forbiddenAction(EmailRecipientRequest recipientRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/sell_daily_email/forbiddenAction", recipientRequest, EmailRecipientResponse.class).getBody();
    }

    @Override
    public EmailRecipientResponse insertAction(EmailRecipientRequest recipientRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/sell_daily_email/insertAction", recipientRequest, EmailRecipientResponse.class).getBody();
    }

    /**
     * 江西银行卡异常count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getBindCardExceptionCount(BindCardExceptionRequest request) {
        String url = "http://AM-ADMIN/am-user/bindcardexception/getBindCardExceptionCount";
        AdminBindCardExceptionResponse response = restTemplate.postForEntity(url,request,AdminBindCardExceptionResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 江西银行卡异常列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<BindCardExceptionCustomizeVO> searchBindCardExceptionList(BindCardExceptionRequest request) {
        String url = "http://AM-ADMIN/am-user/bindcardexception/searchBindCardExceptionList";
        AdminBindCardExceptionResponse response = restTemplate.postForEntity(url,request,AdminBindCardExceptionResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 异常中心-江西银行卡异常-更新银行卡
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public void updateBindCard(BindCardExceptionRequest request) {
        String url = "http://AM-ADMIN/am-user/bindcardexception/updateBindCard";
        restTemplate.postForEntity(url,request,AdminBindCardExceptionResponse.class).getBody();
    }
}
