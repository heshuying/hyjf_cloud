package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.admin.beans.request.STZHWhiteListRequestBean;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.admin.locked.LockedUserMgrResponse;
import com.hyjf.am.response.admin.promotion.ChannelReconciliationResponse;
import com.hyjf.am.response.admin.promotion.PlatformUserCountCustomizeResponse;
import com.hyjf.am.response.config.ParamNameResponse;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.response.trade.BorrowStyleResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.admin.locked.LockedeUserListRequest;
import com.hyjf.am.resquest.trade.DadaCenterCouponCustomizeRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.config.ParamNameVO;
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
}
