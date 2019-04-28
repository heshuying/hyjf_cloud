package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.AppPushManageRequestBean;
import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.am.bean.admin.LockedConfig;
import com.hyjf.am.response.*;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.admin.locked.LockedConfigResponse;
import com.hyjf.am.response.admin.locked.LockedUserMgrResponse;
import com.hyjf.am.response.admin.promotion.ChannelReconciliationResponse;
import com.hyjf.am.response.admin.promotion.PlatformUserCountCustomizeResponse;
import com.hyjf.am.response.admin.vip.content.CustomerTaskConfigVOResponse;
import com.hyjf.am.response.admin.vip.content.ScreenConfigVOResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.admin.locked.LockedeUserListRequest;
import com.hyjf.am.resquest.config.*;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.resquest.trade.DadaCenterCouponCustomizeRequest;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.config.SubmissionsVO;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.repay.BankRepayOrgFreezeLogVO;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.ScreenConfigVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

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
    public List<OperationReportJobVO> getTenderCityGroupByList(Date date){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setDate(date);
        OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/tendercitygroupbylist",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<OperationReportJobVO> getPerformanceSum(){
        OperationReportJobResponse response =  restTemplate.getForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/performancesum", OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public  List<OperationReportJobVO> getTenderSexGroupByList(Date date) {
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setDate(date);
        OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/tendersexgroupbylist",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<OperationReportJobVO> getRechargeMoneyAndSum(int intervalMonth){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setIntervalMonth(intervalMonth);
        OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/rechargemoneyandsum",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<OperationReportJobVO> getCompleteCount(int intervalMonth){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setIntervalMonth(intervalMonth);
        OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/completecount",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<OperationReportJobVO> getSexDistribute( int intervalMonth){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setIntervalMonth(intervalMonth);
        OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/sexdistribute",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<OperationReportJobVO> getOneInvestMost(int intervalMonth){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setIntervalMonth(intervalMonth);
        OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/oneinvestmost",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<OperationReportJobVO> getBorrowPeriod(int intervalMonth){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setIntervalMonth(intervalMonth);
        OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/borrowperiod",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<OperationReportJobVO> getAgeDistribute( int intervalMonth){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setIntervalMonth(intervalMonth);
        OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/agedistribute",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<OperationReportJobVO> getOneInterestsMost(int intervalMonth){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setIntervalMonth(intervalMonth);
        OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/oneinterestsmost",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<OperationReportJobVO> getTenMostMoney( int intervalMonth){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setIntervalMonth(intervalMonth);
        OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/tenmostmoney",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<OperationReportJobVO> getMoneyDistribute( int intervalMonth){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setIntervalMonth(intervalMonth);
        OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/moneydistribute",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<OperationReportJobVO>  getTenderAgeByRangeList(Date date){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setDate(date);
        OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/tenderagebyrangelist",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public float getFullBillAverageTime(Date date){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setDate(date);
        OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/fullbillaveragetime",request, OperationReportJobResponse.class).getBody();
        return response.getFullBillAverage();
    }
    @Override
    public BigDecimal getRepayTotal(Date date) {
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setDate(date);
        OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/repaytotal",request, OperationReportJobResponse.class).getBody();
        return response.getTotalAccount();
    }
    @Override
    public List<OperationReportJobVO> getMonthDealMoney(int startMonth,int endMonth) {
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setStartMonth(startMonth);
        request.setEndMonth(endMonth);
        OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/monthdealmoney",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<OperationReportJobVO> getRevenueAndYield(int intervalMonth,int startMonth,int endMonth){
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setIntervalMonth(intervalMonth);
        request.setStartMonth(startMonth);
        request.setEndMonth(endMonth);
        OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/revenueandyield",request, OperationReportJobResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public  int getTradeCountByMonth(Date beginDate,Date endDate) {
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setBeginDate(beginDate);
        request.setEndDate(endDate);
        OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/tradecountbymonth",request, OperationReportJobResponse.class).getBody();
        return response.getCount();
    }
    @Override
    public  int getTenderCount(Date date) {
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setDate(date);
        OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/tendercount",request, OperationReportJobResponse.class).getBody();
        return response.getCount();
    }

    @Override
    public  double getInvestLastDate(Date date) {
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setDate(date);
        OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/investlastdate",request, OperationReportJobResponse.class).getBody();
        return response.getAccount();
    }
    @Override
    public  int getLoanNum(Date date) {
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setDate(date);
        OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/loannum",request, OperationReportJobResponse.class).getBody();
        return response.getCount();
    }
    @Override
    public BigDecimal getAccountByMonth(Date beginDate, Date endDate) {
        OperationReportJobRequest request = new OperationReportJobRequest();
        request.setBeginDate(beginDate);
        request.setEndDate(endDate);
        OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/accountbymonth",request, OperationReportJobResponse.class).getBody();
        return response.getTotalAccount();
    }
    @Override
    public IntegerResponse countList(ChannelStatisticsDetailRequest request){
        return restTemplate.
                postForEntity("http://AM-ADMIN/am-admin/extensioncenter/channelstatisticsdetail/count", request, IntegerResponse.class).getBody();
    }

    @Override
    public List<UtmVO> selectUtmPlatList(String type) {
        Map<String, Object> params = new HashMap<>();
        if ("pc".equals(type)) {
            params.put("sourceType", 0);// 渠道0 PC
            params.put("delFlag", 0);// 未删除
        } else if ("app".equals(type)) {
            params.put("sourceType", 1);// 渠道1 APP
            params.put("delFlag", 0);// 未删除
        }
        List<UtmVO> getResultListS = new ArrayList<>();
        HttpEntity httpEntity = new HttpEntity(params);
        ResponseEntity<UtmResponse<UtmVO>> response =
                restTemplate.exchange("http://AM-ADMIN/am-user/promotion/utm/getbypagelist",
                        HttpMethod.POST, httpEntity, new ParameterizedTypeReference<UtmResponse<UtmVO>>() {});

        if (response.getBody() != null) {
            return response.getBody().getResultListS();
        }
        return getResultListS;
    }
    @Override
    public Integer getAccessNumber(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/getaccessnumber/" + sourceId,
                UtmResponse.class);
        if (response != null) {
            return response.getAccessNumber();
        }
        return null;
    }
    @Override
    public Integer getRegistNumber(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/getregistnumber/" + sourceId,
                UtmResponse.class);
        if (response != null) {
            return response.getRegistNumber();
        }
        return null;
    }

    @Override
    public Integer getOpenAccountNumber(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/getopenaccountnumber/" + sourceId+"/"+type,
                UtmResponse.class);
        if (response != null) {
            return response.getOpenAccountNumber();
        }
        return null;
    }

    @Override
    public Integer getTenderNumber(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gettendernumber/" + sourceId+"/"+type,
                UtmResponse.class);
        if (response != null) {
            return response.getTenderNumber();
        }
        return null;
    }

    @Override
    public BigDecimal getCumulativeRecharge(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/getcumulativerecharge/" + sourceId+"/"+type,
                UtmResponse.class);
        if (response != null) {
            return response.getCumulativeRecharge();
        }
        return null;
    }

    @Override
    public BigDecimal getHztTenderPrice(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gethzttenderprice/" + sourceId+"/"+type,
                UtmResponse.class);
        if (response != null) {
            return response.getHztTenderPrice();
        }
        return null;
    }

    @Override
    public BigDecimal getHxfTenderPrice(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gethxftenderprice/" + sourceId+"/"+type,
                UtmResponse.class);
        if (response != null) {
            return response.getHxfTenderPrice();
        }
        return null;
    }

    @Override
    public BigDecimal getHtlTenderPrice(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gethtltenderprice/" + sourceId+"/"+type,
                UtmResponse.class);
        if (response != null) {
            return response.getHtlTenderPrice();
        }
        return null;
    }

    @Override
    public BigDecimal getHtjTenderPrice(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gethtjtenderprice/" + sourceId+"/"+type,
                UtmResponse.class);
        if (response != null) {
            return response.getHtjTenderPrice();
        }
        return null;
    }
    @Override
    public BigDecimal getRtbTenderPrice(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/getrtbtenderprice/" + sourceId+"/"+type,
                UtmResponse.class);
        if (response != null) {
            return response.getRtbTenderPrice();
        }
        return null;
    }

    @Override
    public BigDecimal getHzrTenderPrice(Integer sourceId, String type) {
        UtmResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/promotion/utm/gethzrtenderprice/" + sourceId+"/"+type,
                UtmResponse.class);
        if (response != null) {
            return response.getHzrTenderPrice();
        }
        return null;
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
    public IntegerResponse countBailConfigRecordList(BailConfigRequest request) {
        String url = "http://AM-ADMIN/am-trade/bail_config/select_bail_config_count";
        IntegerResponse response = restTemplate.postForEntity(url,request,IntegerResponse.class).getBody();
        return response;
    }


    /**
     * 更新当前机构可用的还款方式并返回最新保证金详情
     *
     * @param id
     * @return
     */
    @Override
    public BailConfigInfoCustomizeVO updateSelectBailConfigById(Integer id) {
        String url = "http://AM-ADMIN/am-trade/bail_config/update_select_bail_config_by_id/" + id;
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
     * 批次中心-批次放款导出记录总数
     * @param request
     * @return
     */
    @Override
    public int getBatchBorrowRecoverCount(BatchBorrowRecoverRequest request) {
        IntegerResponse response =
                restTemplate.postForEntity("http://AM-ADMIN/am-admin/adminBatchBorrowRecover/getListCount", request, IntegerResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultInt();
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
        DadaCenterCouponCustomizeRequest request = new DadaCenterCouponCustomizeRequest();
        request.setLimitStart(dataCenterCouponCustomize.getLimitStart());
        request.setLimitEnd(dataCenterCouponCustomize.getLimitEnd());
        DataCenterCouponCustomizeResponse response = restTemplate.postForObject(
                "http://AM-ADMIN/am-admin/datacenter/coupon/get_record_list_dj", request,
                DataCenterCouponCustomizeResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public int getCountDJ() {
        DadaCenterCouponCustomizeRequest request = new DadaCenterCouponCustomizeRequest();
        DataCenterCouponCustomizeResponse response = restTemplate.postForObject(
                "http://AM-ADMIN/am-admin/datacenter/coupon/get_count_list_dj", request,
                DataCenterCouponCustomizeResponse.class);
        return response.getCount();
    }
    @Override
    public int getCountJX() {
        DadaCenterCouponCustomizeRequest request = new DadaCenterCouponCustomizeRequest();
        DataCenterCouponCustomizeResponse response = restTemplate.postForObject(
                "http://AM-ADMIN/am-admin/datacenter/coupon/get_count_list_jx", request,
                DataCenterCouponCustomizeResponse.class);
        return response.getCount();
    }
    @Override
    public List<DataCenterCouponCustomizeVO> getRecordListJX(DataCenterCouponCustomizeVO dataCenterCouponCustomize) {
        DadaCenterCouponCustomizeRequest request = new DadaCenterCouponCustomizeRequest();
        request.setLimitStart(dataCenterCouponCustomize.getLimitStart());
        request.setLimitEnd(dataCenterCouponCustomize.getLimitEnd());
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
        // 获取出借信息
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
        // 获取出借信息
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
    public ChannelReconciliationResponse selectAppChannelReconciliationCount(ChannelReconciliationRequest request) {
        return restTemplate.postForObject(
                "http://AM-ADMIN/am-user/promotion/utm/select_app_channel_reconciliation_count", request,
                ChannelReconciliationResponse.class);
    }
    @Override
    public ChannelReconciliationResponse selectAppChannelReconciliationRecordHjh(ChannelReconciliationRequest request) {
        return restTemplate.postForObject(
                "http://AM-ADMIN/am-user/promotion/utm/select_app_channel_reconciliation_record_hjh", request,
                ChannelReconciliationResponse.class);
    }
    @Override
    public ChannelReconciliationResponse selectAppChannelReconciliationRecordHjhCount(ChannelReconciliationRequest request) {
        return restTemplate.postForObject(
                "http://AM-ADMIN/am-user/promotion/utm/select_app_channel_reconciliation_record_hjh_count", request,
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
     * 导出异常标的列表
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
     * 查询异常标的列表
     *
     * @param request
     * @return
     */
    @Override
    public List<AssetExceptionCustomizeVO> exportAssetExceptionList(AssetExceptionRequest request) {
        String url = "http://AM-ADMIN/am-admin/asset_exception/export_asset_exception_list";
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
        String url = "http://AM-ADMIN/am-user/bindcardrepair/getBindCardRepairCount";
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
        String url = "http://AM-ADMIN/am-user/bindcardrepair/searchBindCardRepairList";
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
        String url = "http://AM-ADMIN/am-user/bindcardrepair/updateBindCard";
        restTemplate.postForEntity(url,request,AdminBindCardExceptionResponse.class).getBody();
    }

    /**
     * 异常中心-还款冻结异常列表数据
     * @param request
     * @return
     */
    @Override
    public List<BankRepayFreezeOrgCustomizeVO> getBankRepayFreezeOrgList(RepayFreezeOrgRequest request) {
        String url = "http://AM-ADMIN/am-admin/exception/bankRepayFreezeOrg/list_data";
        BankRepayFreezeOrgResponse response = restTemplate.postForEntity(url,request,BankRepayFreezeOrgResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 异常中心-还款冻结异常列表记录数
     * @param request
     * @return
     */
    @Override
    public Integer getBankRepayFreezeOrgCount(RepayFreezeOrgRequest request) {
        String url = "http://AM-ADMIN/am-admin/exception/bankRepayFreezeOrg/list_count";
        IntegerResponse response = restTemplate.postForEntity(url,request,IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public Integer deleteOrgFreezeLogById(Integer id) {
        StringBuilder url = new StringBuilder("http://AM-ADMIN/am-admin/exception/bankRepayFreezeOrg/deleteById/");
        url.append(id);
        IntegerResponse response = restTemplate.getForEntity(url.toString(), IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 根据orderId删除
     */
    @Override
    public Integer deleteOrgFreezeLog(String orderId) {
        StringBuilder url = new StringBuilder("http://AM-ADMIN/am-admin/exception/bankRepayFreezeOrg/delete/");
        url.append(orderId);
        IntegerResponse response = restTemplate.getForEntity(url.toString(), IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 根据条件查询担保机构冻结日志
     */
    @Override
    public List<BankRepayOrgFreezeLogVO> getBankRepayOrgFreezeLogList(String orderId, String borrowNid) {
        StringBuilder url = new StringBuilder("http://AM-ADMIN/am-admin/exception/bankRepayFreezeOrg/getValid/");
        url.append(orderId);
        if(StringUtils.isNotBlank(borrowNid)){
            url.append("/").append(borrowNid);
        }
        BankRepayOrgFreezeLogResponse response = restTemplate.getForEntity(url.toString(), BankRepayOrgFreezeLogResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 移动客户端 - App 推送管理 列表
     * @param requestBean
     * @return
     * @Author : huanghui
     */
    @Override
    public AppPushManageResponse getPushManageList(AppPushManageRequestBean requestBean) {
        AppPushManageResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-admin/appPushManage/selectPushManageList", requestBean, AppPushManageResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())){
            return response;
        }
        return null;
    }

    /**
     * 移动客户端 - App 推送管理 添加
     * @param requestBean
     * @return
     * @Author : huanghui
     */
    @Override
    public AppPushManageResponse insterPushManage(AppPushManageRequestBean requestBean){
        AppPushManageResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-admin/appPushManage/insertPushManage/", requestBean, AppPushManageResponse.class).getBody();
        if (response != null){
            return response;
        }
        return null;
    }

    /**
     * 移动客户端 - App 推送管理 更新
     * @param requestBean
     * @return
     * @Author : huanghui
     */
    @Override
    public boolean updatePushManage(AppPushManageRequestBean requestBean) {
        BooleanResponse booleanResponse = restTemplate.postForEntity("http://AM-ADMIN/am-admin/appPushManage/updatePushManage/", requestBean, BooleanResponse.class).getBody();
        return booleanResponse.getResultBoolean();
    }

    /**
     * 移动客户端 - App 推送管理 删除
     * @param id
     * @return
     * @Author : huanghui
     */
    @Override
    public boolean deletePushManage(Integer id) {
        BooleanResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-admin/appPushManage/deletePushManage/" + id, BooleanResponse.class).getBody();
        return response.getResultBoolean();
    }

    /**
     * 根据ID获取单条记录详细新
     * @param id
     * @return
     * @Author : huanghui
     */
    @Override
    public AppPushManageResponse getAppPushManageInfoById(Integer id) {
        AppPushManageResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-admin/appPushManage/getAppPushManageInfoById/" + id, AppPushManageResponse.class).getBody();
        if (response != null){
            return response;
        }
        return null;
    }

    /**
     * 根据记录更新单条记录的状态
     * @param requestBean
     * @return
     * @Author : huanghui
     */
    @Override
    public boolean updatePushManageStatusById(AppPushManageRequestBean requestBean) {
        BooleanResponse booleanResponse = restTemplate.postForEntity("http://AM-ADMIN/am-admin/appPushManage/updatePushManageStatusById/", requestBean, BooleanResponse.class).getBody();
        return booleanResponse.getResultBoolean();
    }

    /**
     * 节假日配置-列表查询
     * @param request
     * @return
     */
    @Override
    public AdminHolidaysConfigResponse getHolidaysConfigRecordList(AdminHolidaysConfigRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/adminHolidaysConfig/getRecordList",
                request, AdminHolidaysConfigResponse.class).getBody();
    }

    /**
     * 节假日配置-画面迁移(含有id更新，不含有id添加)
     * @param request
     * @return
     */
    @Override
    public AdminHolidaysConfigResponse getInfoList(AdminHolidaysConfigRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/adminHolidaysConfig/getInfoList",
                request, AdminHolidaysConfigResponse.class).getBody();
    }

    /**
     * 节假日配置-添加活动信息
     * @param request
     * @return
     */
    @Override
    public AdminHolidaysConfigResponse insertHolidays(AdminHolidaysConfigRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/adminHolidaysConfig/insertHolidays",
                request, AdminHolidaysConfigResponse.class).getBody();
    }

    /**
     * 节假日配置-修改活动维护信息
     * @param request
     * @return
     */
    @Override
    public AdminHolidaysConfigResponse updateHolidays(AdminHolidaysConfigRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/adminHolidaysConfig/updateHolidays",
                request, AdminHolidaysConfigResponse.class).getBody();
    }

    @Override
    public int onlyCheckMobileCode(String mobile, String code) {
        SmsCodeRequest request = new SmsCodeRequest();
        request.setMobile(mobile);
        request.setVerificationCode(code);
        Integer result = restTemplate.postForEntity("http://AM-ADMIN/am-trade/sms_code/qianle_check/", request, IntegerResponse.class)
                .getBody().getResultInt();
        if (result == null) {
            return 0;
        }
        return result;
    }

    /**
     * 查询千乐散标数据
     * @param dataSearchRequest
     * @return
     */
    @Override
    public DataSearchCustomizeResponse querySanList(DataSearchRequest dataSearchRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/qianle/querysanlist", dataSearchRequest, DataSearchCustomizeResponse.class).getBody();
    }
    /**
     * 查询千乐计划数据
     * @param dataSearchRequest
     * @return
     */
    @Override
    public DataSearchCustomizeResponse queryPlanList(DataSearchRequest dataSearchRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/qianle/queryPlanList", dataSearchRequest, DataSearchCustomizeResponse.class).getBody();

    }
    /**
     * 查询千乐全部数据
     * @param dataSearchRequest
     * @return
     */
    @Override
    public DataSearchCustomizeResponse queryQianleList(DataSearchRequest dataSearchRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/qianle/queryList", dataSearchRequest, DataSearchCustomizeResponse.class).getBody();

    }

    /**
     * 查询短信加固数据
     *
     * @param request
     * @return
     * @author xiehuili
     */
    @Override
    public SmsConfigResponse initSmsConfig(SmsConfigRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/smsConfig/initSmsConfig", request, SmsConfigResponse.class).getBody();
    }


    /**
     * 保存验证码
     * @param mobile
     * @param checkCode
     * @param validCodeType
     * @param status
     * @param platform
     * @return
     */
    @Override
    public int saveSmsCode(String mobile, String checkCode, String validCodeType, Integer status, String platform) {
        SmsCodeRequest request = new SmsCodeRequest();
        request.setMobile(mobile);
        request.setVerificationCode(checkCode);
        request.setVerificationType(validCodeType);
        request.setStatus(status);
        request.setPlatform(platform);
        SmsCodeResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-trade/sms_code/save", request, SmsCodeResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getCnt();
        } else {
            throw new RuntimeException("发送验证码失败...");
        }
    }

    @Override
    public AppUtmRegResponse getstatisticsList(AppChannelStatisticsDetailRequest request) {
        AppUtmRegResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-admin/app_utm_reg/getstatisticsList", request, AppUtmRegResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;

    }
    @Override
    public AppUtmRegResponse exportStatisticsList(AppChannelStatisticsDetailRequest request) {
        AppUtmRegResponse response = restTemplate
                .postForEntity("http://AM-ADMIN/am-admin/app_utm_reg/exportStatisticsList", request,
                        AppUtmRegResponse.class)
                .getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public NaMiMarketingResponse getNaMiMarketingList(NaMiMarketingRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-market/namimarketing/getNaMiMarketingList",
                request,NaMiMarketingResponse.class).getBody();
    }

    @Override
    public NaMiMarketingResponse getPerformanceList(NaMiMarketingRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-market/namimarketing/performanceInit",
                request,NaMiMarketingResponse.class).getBody();
    }

    @Override
    public NaMiMarketingResponse getPerformancInfo(NaMiMarketingRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-market/namimarketing/performanceInfo",
                request,NaMiMarketingResponse.class).getBody();
    }

    @Override
    public NaMiMarketingResponse selectNaMiMarketingRefferList(NaMiMarketingRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-market/namimarketing/selectNaMiMarketingRefferList",
                request,NaMiMarketingResponse.class).getBody();
    }
    @Override
    public  IntegerResponse selectNaMiMarketingRefferCount(NaMiMarketingRequest request){
        return restTemplate.postForEntity("http://AM-ADMIN/am-market/namimarketing/selectNaMiMarketingRefferCount",
                request,IntegerResponse.class).getBody();
    }
    @Override
    public  IntegerResponse selectNaMiMarketingRefferTotalCount(NaMiMarketingRequest request){
        return restTemplate.postForEntity("http://AM-ADMIN/am-market/namimarketing/selectNaMiMarketingRefferTotalCount",
                request,IntegerResponse.class).getBody();
    }

    @Override
    public NaMiMarketingResponse selectNaMiMarketingRefferTotalList(NaMiMarketingRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-market/namimarketing/selectNaMiMarketingRefferTotalList",
                request,NaMiMarketingResponse.class).getBody();
    }

    @Override
    public List<Integer> searchUserIdList(int sourceType) {
        Response response = restTemplate.getForObject("http://AM-ADMIN/am-user/promotion/utm/searchUserIdList/" + sourceType, Response.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public  NaMiMarketingResponse selectMonthList(){
        NaMiMarketingResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-market/namimarketing/selectMonthList", NaMiMarketingResponse.class).getBody();
        return response;
    }

    @Override
    public void  updateJoinTime(String borrowNid,Integer nowTime){
        String url = "http://AM-ADMIN/am-market/returncash/updatejointime/"+borrowNid+"/"+nowTime;
        restTemplate.getForEntity(url,String.class);

    }

    @Override
    public StringResponse checkActivityIfAvailable(Integer activityId){
        String url = "http://AM-ADMIN/am-market/activity/checkActivityIfAvailable/"+activityId;
        StringResponse response = restTemplate.getForEntity(url,StringResponse.class).getBody();
        return response;
    }

    @Override
    public IntegerResponse saveReturnCash(ReturnCashRequest returnCashRequest){
        String url = "http://AM-ADMIN/am-market/returncash/saveReturnCash";
       return restTemplate.postForEntity(url,returnCashRequest,IntegerResponse.class).getBody();
    }

    /**
     * 查询汇计划装让列表的求和
     * add by cwyang 2018-01-24
     * @param request
     * @return
     */
    @Override
    public MapResponse queryHjhDebtCreditTotal(HjhDebtCreditListRequest request) {
        MapResponse response = restTemplate.
                postForEntity("http://AM-ADMIN/am-trade/adminHjhDebtCredit/getListSum", request, MapResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 根据创建日期查询该天导入aleve的条数
     *
     * @param dualDate
     * @return
     */
    @Override
    public Integer countAleveByDualDate(String dualDate) {
        String url = "http://AM-ADMIN/am-trade/bankaleve/countAleveByDualDate/" + dualDate;
        IntegerResponse response = restTemplate.getForEntity(url,IntegerResponse.class).getBody();
        if(response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultInt();
        }
        return -1;
    }

    /**
     * 根据创建日期查询该天导入eve的条数
     *
     * @param dualDate
     * @return
     */
    @Override
    public Integer countEveByDualDate(String dualDate) {
        String url = "http://AM-ADMIN/am-trade/bankaleve/countEveByDualDate/" + dualDate;
        IntegerResponse response = restTemplate.getForEntity(url,IntegerResponse.class).getBody();
        if(response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultInt();
        }
        return -1;
    }

    /**
     * 大屏运营部数据配置列表查询
     * @param request
     * @return
     */
    @Override
    public ScreenConfigVOResponse getScreenConfigList(ScreenConfigRequest request) {
        String url = "http://AM-ADMIN/am-user/vip/content/oper/list";
        ScreenConfigVOResponse response = restTemplate.postForEntity(url, request, ScreenConfigVOResponse.class).getBody();
        return response;
    }

    /**
     * 大屏运营部数据配置数据新增
     * @param screenConfigVO
     * @return
     */
    @Override
    public int addScreenConfig(ScreenConfigVO screenConfigVO) {
        String url = "http://AM-ADMIN/am-user/vip/content/oper/add";
        IntegerResponse response = restTemplate.postForEntity(url, screenConfigVO, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 大屏运营部数据配置数据详情
     * @param screenConfigVO
     * @return
     */
    @Override
    public ScreenConfigVO screenConfigInfo(ScreenConfigVO screenConfigVO) {
        String url = "http://AM-ADMIN/am-user/vip/content/oper/info";
        ScreenConfigVOResponse response = restTemplate.postForEntity(url, screenConfigVO, ScreenConfigVOResponse.class).getBody();
        return response.getResult();
    }

    /**
     * 大屏运营部数据配置数据编辑/启用/禁用
     * @param screenConfigVO
     * @return
     */
    @Override
    public int updateScreenConfig(ScreenConfigVO screenConfigVO) {
        String url = "http://AM-ADMIN/am-user/vip/content/oper/update";
        IntegerResponse response = restTemplate.postForEntity(url, screenConfigVO, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultInt();
        }
        return 0;
    }

    @Override
    public RepayResponse findRepayUser(Integer startTime, Integer endTime,Integer currPage, Integer pageSize) {
        String url = "http://AM-ADMIN/am-admin/screen/data/find_repay_user/"+startTime+"/"+endTime+"/"+currPage+"/"+pageSize;
        RepayResponse response = restTemplate.getForEntity(url, RepayResponse.class).getBody();
        return response;
    }

    /**
     * 坐席月任务配置列表查询
     * @param request
     * @return
     */
    @Override
    public CustomerTaskConfigVOResponse getCustomerTaskConfigList(CustomerTaskConfigRequest request) {
        String url = "http://AM-ADMIN/am-user/vip/content/task/list";
        CustomerTaskConfigVOResponse response = restTemplate.postForEntity(url, request, CustomerTaskConfigVOResponse.class).getBody();
        return response;
    }

    /**
     * 坐席月任务配置数据新增
     * @return
     */
    @Override
    public int addCustomerTaskConfig(CustomerTaskConfigVO customerTaskConfigVO) {
        String url = "http://AM-ADMIN/am-user/vip/content/task/add";
        IntegerResponse response = restTemplate.postForEntity(url, customerTaskConfigVO, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 坐席月任务配置数据详情
     * @param customerTaskConfigVO
     * @return
     */
    @Override
    public CustomerTaskConfigVO customerTaskConfigInfo(CustomerTaskConfigVO customerTaskConfigVO) {
        String url = "http://AM-ADMIN/am-user/vip/content/task/info";
        CustomerTaskConfigVOResponse response = restTemplate.postForEntity(url, customerTaskConfigVO, CustomerTaskConfigVOResponse.class).getBody();
        return response.getResult();
    }

    /**
     * 坐席月任务配置数据编辑/启用/禁用
     * @param customerTaskConfigVO
     * @return
     */
    @Override
    public int updateCustomerTaskConfig(CustomerTaskConfigVO customerTaskConfigVO) {
        String url = "http://AM-ADMIN/am-user/vip/content/task/update";
        IntegerResponse response = restTemplate.postForEntity(url, customerTaskConfigVO, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultInt();
        }
        return 0;
    }

    @Override
    public void addRepayUserList(List<RepaymentPlanVO> resultList) {
        ScreenDataBean screenDataBean = new ScreenDataBean();
        screenDataBean.setRepaymentPlanVOS(resultList);
        restTemplate.postForObject("http://AM-ADMIN/am-trade/screen_data/add_repay_userList",
                screenDataBean, IntegerResponse.class);
    }
    @Override
    public IntegerResponse countRepayUserList(){
        String url = "http://AM-ADMIN/am-trade/screen_data/count_repay_userList";
        return restTemplate.getForEntity(url, IntegerResponse.class).getBody();
    }

    /**
     * 查询工作流配置
     * @param adminRequest
     * @return
     */
    @Override
    public WorkFlowConfigResponse selectWorkFlowConfigList(WorkFlowConfigRequest adminRequest){
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/workflow/bussinessflow/init",adminRequest, WorkFlowConfigResponse.class).getBody();
    }
    /**
     * 添加工作流配置
     * @param workFlowVO
     * @return
     */
    @Override
    public BooleanResponse insertWorkFlowConfig(WorkFlowVO workFlowVO){
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/workflow/bussinessflow/insert",workFlowVO, BooleanResponse.class).getBody();
    }
    /**
     * 查询业务流程详情页面
     * @param id
     * @return
     */
    @Override
    public WorkFlowConfigResponse selectWorkFlowConfigInfo(int id){
        return restTemplate.getForEntity("http://AM-ADMIN/am-admin/workflow/bussinessflow/info/"+id, WorkFlowConfigResponse.class).getBody();
    }
    /**
     * 校验业务id是否存在
     * @param request
     * @return
     */
    @Override
    public BooleanResponse selectWorkFlowConfigByBussinessId(WorkFlowConfigRequest request){
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/workflow/bussinessflow/exist",request, BooleanResponse.class).getBody();
    }
    /**
     * 修改工作流配置业务流程
     * @param workFlowVO
     * @return
     */
    @Override
    public BooleanResponse updateWorkFlowConfig(WorkFlowVO workFlowVO){
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/workflow/bussinessflow/update",workFlowVO, BooleanResponse.class).getBody();
    }
    /**
     * 删除工作流配置业务流程
     * @param id
     * @return
     */
    @Override
    public BooleanResponse deleteWorkFlowConfigById(int id){
        return restTemplate.getForEntity("http://AM-ADMIN/am-admin/workflow/bussinessflow/delete/"+id, BooleanResponse.class).getBody();
    }
    /**
     *  查询邮件预警通知人
     * @param workFlowUserVO
     * @return
     */
    @Override
    public WorkFlowUserResponse selectUser(WorkFlowUserVO workFlowUserVO){
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/workflow/bussinessflow/selectUser",workFlowUserVO, WorkFlowUserResponse.class).getBody();
    }

    @Override
    public List<WorkFlowVO> updateStatusBusinessName() {
        String url = "http://AM-ADMIN/am-admin/workflow/bussinessflow/findWorkFlowAll";
        WorkFlowConfigResponse response = restTemplate
                .getForEntity(url, WorkFlowConfigResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<ScreenDataBean> getRechargeList(Integer startIndex, Integer endIndex) {
        ScreenDataResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-trade/screen_data/getrechargelist/"+startIndex + "/" + endIndex, ScreenDataResponse.class).getBody();
        if(null != response){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<ScreenDataBean> getPlanTenderList(Integer startIndex, Integer endIndex) {
        ScreenDataResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-trade/screen_data/getplantenderlist/"+startIndex + "/" + endIndex, ScreenDataResponse.class).getBody();
        if(null != response){
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<ScreenDataBean> getPlanRepayList(Integer startIndex, Integer endIndex) {
        ScreenDataResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-trade/screen_data/getplanrepaylist/"+startIndex + "/" + endIndex, ScreenDataResponse.class).getBody();
        if(null != response){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<ScreenDataBean> getCreditTenderList(Integer startIndex, Integer endIndex) {
        ScreenDataResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-trade/screen_data/getcredittenderlist/"+startIndex + "/" + endIndex, ScreenDataResponse.class).getBody();
        if(null != response){
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<ScreenDataBean> getWithdrawList(Integer startIndex, Integer endIndex) {
        ScreenDataResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-trade/screen_data/getwithdrawlist/"+startIndex + "/" + endIndex, ScreenDataResponse.class).getBody();
        if(null != response){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<ScreenDataBean> getBorrowRecoverList(Integer startIndex, Integer endIndex) {
        ScreenDataResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-trade/screen_data/getborrowrecoverlist/"+startIndex + "/" + endIndex, ScreenDataResponse.class).getBody();
        if(null != response){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public boolean updateFlowStatus(Integer businessId) {
        String url = "http://AM-ADMIN/am-admin/workflow/bussinessflow/updateFlowStatus/"+businessId;
        BooleanResponse response = restTemplate
                .getForEntity(url, BooleanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultBoolean();
        }
        return false;
    }
    /**
     * 工作流查询所有用户角色
     * @return
     */
    @Override
    public AdminRoleResponse selectWorkFlowRoleList(){
        return restTemplate
                .getForEntity( "http://AM-ADMIN/am-admin/workflow/bussinessflow/selectWorkFlowRoleList", AdminRoleResponse.class).getBody();
    }

    @Override
    public List<WorkFlowUserVO> findWorkFlowNodeUserEmailAll() {
        String url = "http://AM-ADMIN/am-admin/workflow/bussinessflow/findWorkFlowNodeUserEmailAll";
        WorkFlowUserResponse response = restTemplate
                .getForEntity(url, WorkFlowUserResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    public List<ScreenDataBean> getBorrowTenderList(Integer startIndex, Integer endIndex) {
        ScreenDataResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-trade/screen_data/getborrowtenderlist/"+startIndex + "/" + endIndex, ScreenDataResponse.class).getBody();
        if(null != response){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public ActivityUserGuessResponse getGuessList(ActivityUserGuessRequest request) {
        ActivityUserGuessResponse response = restTemplate.postForObject("http://AM-ADMIN/am-market/mayDay/guessUserList", request, ActivityUserGuessResponse.class);
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public ActivityUserRewardResponse getRewardList(ActivityUserRewardRequest rewardRequest) {
        ActivityUserRewardResponse response = restTemplate.postForObject("http://AM-ADMIN/am-market/mayDay/rewardList", rewardRequest, ActivityUserRewardResponse.class);
        if (null != response) {
            return response;
        }
        return null;
    }

    /**
     * 查询累计年华投资
     * @param newYearNineteenRequestBean
     * @return
     */
    @Override
    public NewYearActivityResponse selectInvestList(NewYearNineteenRequestBean newYearNineteenRequestBean){
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/newYearNineteen/selectInvestList",newYearNineteenRequestBean,NewYearActivityResponse.class).getBody();
    }

}
