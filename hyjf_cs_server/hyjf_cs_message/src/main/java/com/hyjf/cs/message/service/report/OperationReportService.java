package com.hyjf.cs.message.service.report;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.vo.datacollect.OperationReportVO;
import com.hyjf.cs.message.bean.mc.OperationReportColumnEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
    public List<OperationReportVO> getRecordList(Map<String, Object> record);

	/**
	 * 获取发布的列表接口
	 *
	 * @return
	 */
	public JSONObject getRecordListByReleaseJson(OperationReportRequest request);

    /**
     * 统计全部个数
     *
     * @return
     */
    public Integer countRecord(Map<String, Object> record);

    /**
     * 按月度统计列表
     *
     * @return
     */
    List<OperationReportVO> getRecordListByMonth(Map<String, Object> record);
    /**
     * 按月度统计个数
     *
     * @return
     */
    Integer countRecordByMonth(Map<String, Object> record);

    /**
     * 按季度统计列表
     *
     * @return
     */
    List<OperationReportVO> getRecordListByQuarter(Map<String, Object> record);

    /**
     * 按季度统计个数
     *
     * @return
     */
    Integer countRecordByQuarter(Map<String, Object> record);
    /**
     * 按半年统计列表
     *
     * @return
     */
    List<OperationReportVO> getRecordListByHalfYear(Map<String, Object> record);
    /**
     * 按半年统计个数
     *
     * @return
     */
    Integer countRecordByHalfYear(Map<String, Object> record);
    /**
     * 按年统计列表
     *
     * @return
     */
    List<OperationReportVO> getRecordListByYear(Map<String, Object> record);
    /**
     * 按年统计个数
     *
     * @return
     */
    Integer countRecordByYear(Map<String, Object> record);


    int updateByPrimaryKeySelective(OperationReportVO record);
    /**
     * 添加或修改
     *
     * @return
     */
//	public Integer insertOperationReportCommon(OperationReportRequest form);

	/**根据id查询运营报告
	 * @param id
	 * @return
	 */
	public OperationReportResponse selectOperationreportCommon(String id);

	/**根据id查询获取报表明细
	 * @param id
	 * @return
	 */
	public JSONObject reportInfo(String id);


	/**季度运营报告插入
	 * @param form
	 * @return
	 */
	public OperationReportResponse insertQuarterOperationReport(OperationReportRequest form);

	/**季度运营报告插入预览
	 * @param form
	 * @return
	 */
	public OperationReportResponse insertQuarterOperationReportPreview(OperationReportRequest form, Integer type);


	/**季度运营报告修改
	 * @param form
	 * @return
	 */
	public OperationReportResponse updateQuarterOperationReport(OperationReportRequest form);
	/**季度运营报告修改预览
	 * @param form
	 * @return
	 */
	public OperationReportResponse updateQuarterOperationReportPreview(OperationReportRequest form);

	/**月度运营报告插入
	 * @param form
	 * @return
	 */
	public OperationReportResponse insertMonthlyOperationReport(OperationReportRequest form);
	/**月度运营报告插入预览功能
	 * @param form
	 * @return
	 */
	public OperationReportResponse insertMonthlyOperationReportPreview(OperationReportRequest form) ;
	/**月度运营报告修改
	 * @param form
	 * @return
	 */
	public OperationReportResponse updateMonthOperationReport(OperationReportRequest form);

	/**月度运营报告修改预览
	 * @param form
	 * @return
	 */
	public OperationReportResponse updateMonthOperationReportPreview(OperationReportRequest form);

	/**上半年度运营报告插入
	 * @param form
	 * @return
	 */
	public OperationReportResponse insertHalfYearOperationReport(OperationReportRequest form);

    /**上半年度运营报告预览功能
     * @param form
     * @return
     */
    public OperationReportResponse insertHalfYearOperationReportPreview(OperationReportRequest form) ;
	/**上半年度运营报告修改
	 * @param form
	 * @return
	 */
	public OperationReportResponse updateHalfYearOperationReport(OperationReportRequest form);
    /**上半年度运营报告修改预览
     * @param form
     * @return
     */
    public OperationReportResponse updateHalfYearOperationReportPreview(OperationReportRequest form);
	/**年度运营报告插入
	 * @param form
	 * @return
	 */
	public OperationReportResponse insertYearOperationReport(OperationReportRequest form);

	/**年度运营报告修改
	 * @param form
	 * @return
	 */
	public OperationReportResponse updateYearOperationReport(OperationReportRequest form);


	/**年度运营报告新增预览
	 * @param form
	 * @return
	 */
	public OperationReportResponse insertYearlyOperationReportPreview(OperationReportRequest form);

	/**年度运营报告修改预览
	 * @param form
	 * @return
	 */
	public OperationReportResponse updateYearOperationReportPreview(OperationReportRequest form);
    /**
     * 获取运营报告主表
     *
     * @param id
     * @return OperationReportColumnEntity
     */
    OperationReportColumnEntity selectByPrimaryKey(String id);
}
