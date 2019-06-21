/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.SmsLogRequestBean;
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

import java.util.List;

/**
 * @author zhangqingqing
 * @version CsMessageClient, v0.1 2018/6/25 10:27
 */
public interface CsMessageClient {

    AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList);

    String selectBorrowInvestAccount(AccountWebListVO accountWebList);

    /**
     * 查询关联记录列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer getAssociatedRecordsCount(AssociatedRecordListRequest request);
    /**
     * 根据筛选条件查询关联记录list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AssociatedRecordListVO> getAssociatedRecordList(AssociatedRecordListRequest request);

    /**
     * 根据筛选参数从mongo中查询DirectionalTransferAssociatedLog的count
     * @auth sunpeikai
     * @param request 前端给传的筛选参数
     * @return
     */
    Integer getDirectionalTransferLogCount(BindLogListRequest request);

    /**
     * 根据筛选参数从mongo中查询DirectionalTransferAssociatedLog的list
     * @auth sunpeikai
     * @param request 前端给传的筛选参数
     * @return
     */
    List<BindLogVO> searchDirectionalTransferLogList(BindLogListRequest request);

    /**
     * 获取错误码
     * @param logOrdId
     * @return
     */
    String getRetCode(String logOrdId);


    /**
     * 获取全部列表
     *
     * @return
     */
    public OperationReportResponse getRecordList(OperationReportRequest request);
    /**
     * 获取全部数量
     *
     * @return
     */
    OperationReportResponse getRecordCount(OperationReportRequest request);

    public OperationReportResponse listByRelease(OperationReportRequest request);
    /**根据id查询运营报告
     * @param id
     * @return
     */
    public OperationReportResponse selectOperationreportCommon(String id);

    public OperationReportResponse reportInfo(String id);

    /**逻辑删除
     * @param id
     * @return
     */
    public OperationReportResponse delete(String id);

    /** 发布
     * @param id
     * @return
     */
    public OperationReportResponse publish(OperationReportRequest id);

    /**月度运营报告插入或者修改
     * @param request
     * @return
     */
    public OperationReportResponse insertOrUpdateMonthAction(OperationReportRequest request);

    /**季度运营报告插入或者修改
     * @param request
     * @return
     */
    public OperationReportResponse insertOrUpdateQuarterAction(OperationReportRequest request);

    /**上半年度运营报告新增修改
     * @param request
     * @return
     */
    public OperationReportResponse insertOrUpdateHalfYearAction(OperationReportRequest request);

    /**年度运营报告新增修改
     * @param request
     * @return
     */
    public OperationReportResponse insertOrUpdateYearAction(OperationReportRequest request);

    /**月度新增修改页面预览
     * @param request
     * @return
     */
    public OperationReportResponse monthPreview(OperationReportRequest request);

    /**年度新增修改页面预览
     * @param request
     * @return
     */
    public OperationReportResponse yearPreview(OperationReportRequest request);

    /**季度新增修改页面预览
     * @param request
     * @return
     */
    public OperationReportResponse quarterPreview(OperationReportRequest request);

    /**半年新增修改页面预览
     * @param request
     * @return
     */
    public OperationReportResponse halfPreview(OperationReportRequest request);

    /**
     * 查询消息中心短信发送记录
     *
     * @return
     */
    SmsLogResponse smsLogList();

    /**
     * 根据条件查询消息中心短信发送记录
     *
     * @param request
     * @return
     */
    SmsLogResponse findSmsLog(SmsLogRequest request);

    /**
     * 获取汇计划--计划资金列表
     * @param hjhPlanCapitalRequest
     * @return
     * @Author : huanghui
     */
    HjhPlanCapitalResponse getPlanCapitalList(HjhPlanCapitalRequest hjhPlanCapitalRequest);

    /**
     * 获取消息模板统计报表
     * @param request
     * @return
     */
    MessagePushTemplateStaticsResponse selectTemplateStatics(MessagePushTemplateStaticsRequest request);

    /**
     * 获取消息平台统计报表
     * @param request
     * @return
     */
    MessagePushPlatStaticsResponse selectPushPlatTemplateStatics(MessagePushPlatStaticsRequest request);

    /**
     * 获取列表记录数
     *
     * @return
     */
    Integer getRecordCount(MessagePushErrorRequest request);

    /**
     * 获取列表
     *
     * @return
     */
    List<MessagePushMsgHistoryVO> getRecordListT(MessagePushErrorRequest request, int limitStart, int limitEnd);

    /**
     *查询定时发送短信列表
     * @param request
     * @return
     */
    SmsOntimeResponse queryTime(SmsLogRequest request);

    /**
     * 条件查询短信记录列表
     * @param requestBean
     * @return
     */
    Integer queryLogCount(SmsLogRequestBean requestBean);

    /**
     * 获取手动发放消息列表
     * @param request
     * @return
     */
    MessagePushMsgResponse selectMessagePushMsg(MessagePushMsgRequest request);

    /**
     * 根据id获取手动发放消息
     * @param id
     * @return
     */
    MessagePushMsgResponse getMessagePushMsgById(String id);

    /**
     * 添加手动发送短信
     * @param templateVO
     * @return
     */
    MessagePushMsgResponse insertMessagePushMsg(MessagePushMsgVO templateVO);

    /**
     * 修改手动发送短信
     * @param templateRequest
     * @return
     */
    MessagePushMsgResponse updateMessagePushMsg(MessagePushMsgRequest templateRequest);

    /**
     * 删除手动发送短信
     * @param request
     * @return
     */
    MessagePushMsgResponse deleteMessagePushMsg(MessagePushMsgRequest request);

    /**
     * 获取app渠道统计列表
     * @param statisticsRequest
     * @return
     */
    AppChannelStatisticsResponse searchList(AppChannelStatisticsRequest statisticsRequest);

    /**
     * 导出app渠道统计报表
     * @param statisticsRequest
     * @return
     */
    AppChannelStatisticsResponse exportList(AppChannelStatisticsRequest statisticsRequest);



    /**
     * 查找pc渠道统计
     * @param request
     * @return
     */
    PcChannelStatisticsResponse searchPcChannelStatistics(PcChannelStatisticsRequest request);

    /**
     *导出app渠道统计明细
     * @param request
     * @return
     */
    AppUtmRegResponse exportStatisticsList(AppChannelStatisticsDetailRequest request);



    /**
     * 查询会员操作日志列表

     * @return
     */
    UserOperationLogResponse  getOperationLogList(UserOperationLogRequest request);

    HjhInfoAccountBalanceResponse  getHjhAccountBalanceMonthCount(HjhAccountBalanceRequest request);

    HjhInfoAccountBalanceResponse  getHjhAccountBalanceDayCount(HjhAccountBalanceRequest request);

    HjhInfoAccountBalanceResponse  getHjhAccountBalanceMonth(HjhAccountBalanceRequest request);

    HjhInfoAccountBalanceResponse  getHjhAccountBalanceDay(HjhAccountBalanceRequest request);

    /**
     * 获取汇计划--计划资金3.3.0列表（预计）
     * @param hjhPlanCapitalPredictionRequest
     * @return
     * @Author : wenxin
     */
    HjhPlanCapitalPredictionResponse getPlanCapitalPredictionList(HjhPlanCapitalPredictionRequest hjhPlanCapitalPredictionRequest);

    /**
     * 获取汇计划--计划资金3.3.0列表（实际）
     * @param hjhPlanCapitalActualRequest
     * @return
     * @Author : wenxin
     */
    HjhPlanCapitalActualResponse getPlanCapitalActualInfo(HjhPlanCapitalActualRequest hjhPlanCapitalActualRequest);
}
