package com.hyjf.admin.service;

import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.message.OperationReportRequest;

/**
 * @author tanyy
 * @version 2.0
 */
public interface OperationReportService {
    /**
     * 获取全部列表
     *
     * @return
     */
     OperationReportResponse getRecordList(OperationReportRequest request);

    /**
     * 获取已发布列表--web端和app
     *
     * @return
     */
    OperationReportResponse listByRelease(OperationReportRequest request);
    /**
     * 通过ID获取详情
     *
     * @return
     */
	 OperationReportResponse selectOperationreportCommon(String id);
    /**
     * 获取报表明细--web端和app
     *
     * @return
     */
    OperationReportResponse reportInfo(String id);

    /**
     * 通过id更新
     *
     * @return
     */
    OperationReportResponse delete(String id);
    /**
     * 发布
     *
     * @return
     */
    OperationReportResponse publish(OperationReportRequest request);

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

}
