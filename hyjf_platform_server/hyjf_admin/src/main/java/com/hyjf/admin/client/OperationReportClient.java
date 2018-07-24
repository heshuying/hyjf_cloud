package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;

import java.util.List;
import java.util.Map;

/**
 * @author by tanyy on 2018/7/17.
 */
public interface OperationReportClient {
    /**
     * 获取全部列表
     *
     * @return
     */
    public OperationReportResponse getRecordList(OperationReportRequest request);
    /**根据id查询运营报告
     * @param id
     * @return
     */
    public OperationReportResponse selectOperationreportCommon(String id);

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
}
