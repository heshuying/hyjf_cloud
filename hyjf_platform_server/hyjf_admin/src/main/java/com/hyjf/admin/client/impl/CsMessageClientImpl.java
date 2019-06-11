/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.SmsLogRequestBean;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.admin.promotion.PcChannelStatisticsResponse;
import com.hyjf.am.response.app.AppChannelStatisticsResponse;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.response.message.UserOperationLogResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;
import com.hyjf.am.resquest.message.MessagePushMsgRequest;
import com.hyjf.am.resquest.message.MessagePushTemplateStaticsRequest;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.am.vo.admin.AssociatedRecordListVO;
import com.hyjf.am.vo.admin.BindLogVO;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import com.hyjf.am.vo.admin.MessagePushMsgVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhangqingqing
 * @version AmDataCollect, v0.1 2018/6/25 10:26
 * <p>
 */
@Service
public class CsMessageClientImpl implements CsMessageClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList) {
        AccountWebListResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/search/queryWebCount", accountWebList, AccountWebListResponse.class).getBody();
        return response;
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
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getAssociatedRecordsCount(AssociatedRecordListRequest request) {
        AssociatedRecordListResponse response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/search/getAssociatedRecordsCount", request, AssociatedRecordListResponse.class)
                .getBody();
        if (Response.isSuccess(response)) {
            String count = String.valueOf(response.getCount());
            return Integer.valueOf(count);
        }
        return 0;
    }

    /**
     * 根据筛选条件查询关联记录list
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<AssociatedRecordListVO> getAssociatedRecordList(AssociatedRecordListRequest request) {
        AssociatedRecordListResponse response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/search/searchAssociatedRecordList", request, AssociatedRecordListResponse.class)
                .getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据筛选参数从mongo中查询DirectionalTransferAssociatedLog的count
     *
     * @param request 前端给传的筛选参数
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getDirectionalTransferLogCount(BindLogListRequest request) {
        BindLogResponse response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/search/getAssociatedLogCount", request, BindLogResponse.class)
                .getBody();
        if (Response.isSuccess(response)) {
            String count = String.valueOf(response.getCount());
            return Integer.valueOf(count);
        }
        return 0;
    }

    /**
     * 根据筛选参数从mongo中查询DirectionalTransferAssociatedLog的list
     *
     * @param request 前端给传的筛选参数
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<BindLogVO> searchDirectionalTransferLogList(BindLogListRequest request) {
        BindLogResponse response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/search/searchAssociatedLogList", request, BindLogResponse.class)
                .getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public String getRetCode(String logOrdId) {
        StringResponse response = restTemplate.getForEntity("http://CS-MESSAGE/cs-message/search/getRetCode/" + logOrdId, StringResponse.class).getBody();
        return response.getResultStr();
    }

    @Override
    public OperationReportResponse getRecordList(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operationReport/list", request, OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse getRecordCount(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operationReport/count", request, OperationReportResponse.class).getBody();
        return response;
    }
    @Override
    public OperationReportResponse listByRelease(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operation_report/listbyrelease", request, OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse selectOperationreportCommon(String id) {
        OperationReportResponse response = restTemplate.getForObject("http://CS-MESSAGE/cs-message/operationReport/selectDetail/" + id, OperationReportResponse.class);
        return response;
    }

    @Override
    public OperationReportResponse reportInfo(String id) {
        OperationReportResponse response = restTemplate.getForObject("http://CS-MESSAGE/cs-message/operation_report/reportinfo/" + id, OperationReportResponse.class);
        return response;
    }

    @Override
    public OperationReportResponse delete(String id) {
        OperationReportResponse response = restTemplate.getForObject("http://CS-MESSAGE/cs-message/operationReport/delete/" + id, OperationReportResponse.class);
        return response;
    }

    @Override
    public OperationReportResponse publish(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operationReport/publish", request, OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse insertOrUpdateMonthAction(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operationReport/insertMonthAction", request, OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse insertOrUpdateQuarterAction(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operationReport/insertQuarterAction", request, OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse insertOrUpdateHalfYearAction(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operationReport/insertHalfYearAction", request, OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse insertOrUpdateYearAction(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operationReport/insertYearAction", request, OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse monthPreview(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operationReport/monthPreview", request, OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse yearPreview(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operationReport/yearPreview", request, OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse quarterPreview(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operationReport/quarterPreview", request, OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public OperationReportResponse halfPreview(OperationReportRequest request) {
        OperationReportResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/operationReport/halfPreview", request, OperationReportResponse.class).getBody();
        return response;
    }

    @Override
    public SmsLogResponse smsLogList() {
        return restTemplate.getForEntity("http://CS-MESSAGE/cs-message/smsLog/list", SmsLogResponse.class).getBody();
    }

    @Override
    public SmsLogResponse findSmsLog(SmsLogRequest request) {
        return restTemplate.postForEntity("http://CS-MESSAGE/cs-message/smsLog/find", request, SmsLogResponse.class)
                .getBody();
    }

    /**
     * 获取汇计划--计划资金列表(从MongoDB读取数据)
     *
     * @param hjhPlanCapitalRequest
     * @return
     * @Author : huanghui
     */
    @Override
    public HjhPlanCapitalResponse getPlanCapitalList(HjhPlanCapitalRequest hjhPlanCapitalRequest) {
        HjhPlanCapitalResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/hjhPlanCapital/getPlanCapitalList",
                hjhPlanCapitalRequest, HjhPlanCapitalResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTemplateStaticsResponse selectTemplateStatics(MessagePushTemplateStaticsRequest request) {
        MessagePushTemplateStaticsResponse response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/messagePushTemplateStatics/selectTemplateStatics",
                        request, MessagePushTemplateStaticsResponse.class)
                .getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushPlatStaticsResponse selectPushPlatTemplateStatics(MessagePushPlatStaticsRequest request) {
        MessagePushPlatStaticsResponse response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/messagePlatStatics/selectPlatPtatics",
                        request, MessagePushPlatStaticsResponse.class)
                .getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 获取列表记录数
     *
     * @return
     */
    @Override
    public Integer getRecordCount(MessagePushErrorRequest request) {
        MessagePushHistoryResponse response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/msgpush/error/getRecordCount",
                        request, MessagePushHistoryResponse.class).getBody();
        if (response != null) {
            return response.getRecordTotal();
        }
        return null;
    }

    /**
     * (条件)查询 APP消息推送 异常处理 列表
     *
     * @return
     */
    @Override
    public List<MessagePushMsgHistoryVO> getRecordListT(MessagePushErrorRequest request, int limitStart, int limitEnd) {
        request.setLimitStart(limitStart);
        request.setLimitEnd(limitEnd);
        MessagePushHistoryResponse response = restTemplate
                .postForObject("http://CS-MESSAGE/cs-message/msgpush/error/getRecordListT/", request,
                        MessagePushHistoryResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取手动发放短信列表
     *
     * @param request
     * @return
     */
    @Override
    public MessagePushMsgResponse selectMessagePushMsg(MessagePushMsgRequest request) {
        MessagePushMsgResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/appMessage/selectMessagePushMsg",
                request, MessagePushMsgResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushMsgResponse getMessagePushMsgById(String id) {
        MessagePushMsgResponse response = restTemplate.getForEntity("http://CS-MESSAGE/cs-message/appMessage/getMessagePushMsgById/" + id, MessagePushMsgResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushMsgResponse insertMessagePushMsg(MessagePushMsgVO templateVO) {
        MessagePushMsgResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/appMessage/insertMessagePushMsg",
                templateVO, MessagePushMsgResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushMsgResponse updateMessagePushMsg(MessagePushMsgRequest templateRequest) {
        MessagePushMsgResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/appMessage/updateMessagePushMsg",
                templateRequest, MessagePushMsgResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushMsgResponse deleteMessagePushMsg(MessagePushMsgRequest request) {
        MessagePushMsgResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/appMessage/deleteMessagePushMsg", request,
                MessagePushMsgResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public AppChannelStatisticsResponse searchList(AppChannelStatisticsRequest statisticsRequest) {
        AppChannelStatisticsResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/app/channelStatistics", statisticsRequest, AppChannelStatisticsResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public AppChannelStatisticsResponse exportList(AppChannelStatisticsRequest statisticsRequest) {
        AppChannelStatisticsResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/app/export", statisticsRequest, AppChannelStatisticsResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }



    @Override
    public PcChannelStatisticsResponse searchPcChannelStatistics(PcChannelStatisticsRequest request) {
        PcChannelStatisticsResponse response = restTemplate.postForEntity("http://CS-MESSAGE/cs-message/pcChannelStatistics/search", request, PcChannelStatisticsResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public AppUtmRegResponse exportStatisticsList(AppChannelStatisticsDetailRequest request) {
        AppUtmRegResponse response = restTemplate.postForEntity("http://AM-USER//am-user/app_utm_reg/exportStatisticsList", request, AppUtmRegResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;

    }
    @Override
    public SmsOntimeResponse queryTime(SmsLogRequest request) {
        SmsOntimeResponse response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/smsLog/queryTime",
                        request, SmsOntimeResponse.class)
                .getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public Integer queryLogCount(SmsLogRequestBean requestBean) {
        SmsLogResponse response = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/smsLog/queryLogCount",
                        requestBean, SmsLogResponse.class)
                .getBody();
        if (response != null) {
            return response.getLogCount();
        }
        return 0;
    }


    @Override
    public UserOperationLogResponse  getOperationLogList(UserOperationLogRequest request) {
        UserOperationLogResponse response  = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/manager/operationLog/init", request, UserOperationLogResponse.class).getBody();
        return response;
    }

    @Override
    public HjhInfoAccountBalanceResponse  getHjhAccountBalanceMonthCount(HjhAccountBalanceRequest request) {
        HjhInfoAccountBalanceResponse response  = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/manager/statis/getHjhAccountBalanceMonthCount", request, HjhInfoAccountBalanceResponse.class).getBody();
        return response;
    }

    @Override
    public HjhInfoAccountBalanceResponse  getHjhAccountBalanceMonth(HjhAccountBalanceRequest request) {
        HjhInfoAccountBalanceResponse response  = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/manager/statis/getHjhAccountBalanceMonth", request, HjhInfoAccountBalanceResponse.class).getBody();
        return response;
    }

    @Override
    public HjhInfoAccountBalanceResponse  getHjhAccountBalanceDayCount(HjhAccountBalanceRequest request) {
        HjhInfoAccountBalanceResponse response  = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/manager/statis/getHjhAccountBalanceDayCount", request, HjhInfoAccountBalanceResponse.class).getBody();
        return response;
    }

    @Override
    public HjhInfoAccountBalanceResponse  getHjhAccountBalanceDay(HjhAccountBalanceRequest request) {
        HjhInfoAccountBalanceResponse response  = restTemplate
                .postForEntity("http://CS-MESSAGE/cs-message/manager/statis/getHjhAccountBalanceDay", request, HjhInfoAccountBalanceResponse.class).getBody();
        return response;
    }

    @Override
    public CaiJingLogResponse queryCaiJingLog(CaiJingLogRequest request) {
        String url = "http://CS-MESSAGE/cs-message/dataCenter/caiJingLog";
        CaiJingLogResponse response = restTemplate.postForEntity(url, request, CaiJingLogResponse.class).getBody();
        return response;
    }
}
