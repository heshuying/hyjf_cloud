/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.response.admin.AssociatedRecordListResponse;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.am.vo.admin.AssociatedRecordListVo;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmDataCollect, v0.1 2018/6/25 10:26
 *
 * todo 这个类全部错了，都要改 ！！！！！！！！！！！！！！！！！！！！！！！！
 */
@Service
public class  CsMessageClientImpl  implements CsMessageClient {
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList) {
        AccountWebListResponse response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/search/queryAccountWebList", accountWebList, AccountWebListResponse.class)
                .getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public String selectBorrowInvestAccount(AccountWebListVO accountWebList) {
        String response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/search/selectBorrowInvestAccount", accountWebList, String.class)
                .getBody();
        if (response != null) {
            return response;
        }
        return null;
    }


    /**
     * 查询关联记录列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public Integer getAssociatedRecordsCount(AssociatedRecordListRequest request) {
        Integer count = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/search/getassociatedrecordscount", request, Integer.class)
                .getBody();

        return count;
    }

    /**
     * 根据筛选条件查询关联记录list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AssociatedRecordListVo> getAssociatedRecordList(AssociatedRecordListRequest request) {
        AssociatedRecordListResponse response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/search/searchassociatedrecordlist", request, AssociatedRecordListResponse.class)
                .getBody();
        if(Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public String getRetCode(String logOrdId) {
        String response = restTemplate.getForEntity("http://CS-MESSAGE/cs-message/search/getRetCode/" + logOrdId,String.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse getRecordList(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/list",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse listByRelease(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/listbyrelease",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse selectOperationreportCommon(String id){
        OperationReportResponse response = restTemplate.getForObject("http://CS-MESSAGE/cs-message/operation_report/selectdetail/"+id,OperationReportResponse.class);
        return response;
    }
    @Override
    public OperationReportResponse reportInfo(String id){
        OperationReportResponse response = restTemplate.getForObject("http://CS-MESSAGE/cs-message/operation_report/reportinfo/"+id,OperationReportResponse.class);
        return response;
    }
    @Override
    public OperationReportResponse delete(String id){
        OperationReportResponse response = restTemplate.getForObject("http://CS-MESSAGE/cs-message/operation_report/delete/"+id,OperationReportResponse.class);
        return response;
    }
    @Override
    public OperationReportResponse publish(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/publish",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse insertOrUpdateMonthAction(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/insertmonthaction",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse insertOrUpdateQuarterAction(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/insertquarteraction",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse insertOrUpdateHalfYearAction(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/inserthalfyearaction",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse insertOrUpdateYearAction(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/insertyearaction",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse monthPreview(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/monthpreview",request,OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse yearPreview(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/yearpreview",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse quarterPreview(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/quarterpreview",request,OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse halfPreview(OperationReportRequest request){
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/halfpreview",request,OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public JSONObject smsLogList() {
        return restTemplate.getForEntity("http://CS-MESSAGE/cs-message/sms_log/list", JSONObject.class).getBody();
    }

    @Override
    public JSONObject findSmsLog(SmsLogRequest request) {
        return restTemplate.postForEntity("http://CS-MESSAGE/cs-message/sms_log/find", request, JSONObject.class)
                .getBody();
    }

    /**
     * 获取汇计划--计划资金列表
     * @param request
     * @return
     */
    @Override
    public Integer getPlanCapitalCount(HjhPlanCapitalRequest request){
        return restTemplate.postForEntity("http://CS-MESSAGE/cs-message/hjh_plan_capital/getPlanCapitalCount", request, Integer.class).getBody();
    }

    /**
     * 获取汇计划--计划资金列表(从MongoDB读取数据)
     * @param hjhPlanCapitalRequest
     * @return
     * @Author : huanghui
     */
    @Override
    public List<HjhPlanCapitalVO> getPlanCapitalList(HjhPlanCapitalRequest hjhPlanCapitalRequest) {
        HjhPlanCapitalResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/hjh_plan_capital/getPlanCapitalList",
                hjhPlanCapitalRequest, HjhPlanCapitalResponse.class).getBody();
        if (response != null){
            return response.getResultList();
        }
        return null;
    }

}
