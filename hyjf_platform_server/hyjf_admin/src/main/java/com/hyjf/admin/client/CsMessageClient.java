/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.MessagePushErrorResponse;
import com.hyjf.am.response.admin.MessagePushPlatStaticsResponse;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;
import com.hyjf.am.resquest.message.MessagePushTemplateStaticsRequest;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.response.admin.MessagePushTemplateStaticsResponse;
import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.resquest.message.SmsLogRequest;
import com.hyjf.am.vo.admin.AssociatedRecordListVo;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;

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
    List<AssociatedRecordListVo> getAssociatedRecordList(AssociatedRecordListRequest request);

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
    JSONObject smsLogList();

    /**
     * 根据条件查询消息中心短信发送记录
     *
     * @param request
     * @return
     */
    JSONObject findSmsLog(SmsLogRequest request);

    /**
     * 获取汇计划 - 资金计划 条数
     * @param request
     * @return
     * @Author : huanghui
     */
    Integer getPlanCapitalCount(HjhPlanCapitalRequest request);

    /**
     * 获取汇计划--计划资金列表
     * @param hjhPlanCapitalRequest
     * @return
     * @Author : huanghui
     */
    List<HjhPlanCapitalVO> getPlanCapitalList(HjhPlanCapitalRequest hjhPlanCapitalRequest);

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
     * (条件)查询 APP消息推送 异常处理 列表
     * @param request
     * @return
     */
    MessagePushErrorResponse getListByConditions(MessagePushErrorRequest request);

    /**
     * 数据修改 APP消息推送 异常处理
     * @param request
     * @return
     */
    MessagePushErrorResponse update(MessagePushErrorRequest request);
}
