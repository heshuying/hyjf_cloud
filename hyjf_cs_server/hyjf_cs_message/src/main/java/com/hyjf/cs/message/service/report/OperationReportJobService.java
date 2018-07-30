package com.hyjf.cs.message.service.report;

import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.vo.datacollect.OperationReportVO;
import com.hyjf.cs.message.bean.ic.BorrowUserStatistic;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author tanyy
 * @version 2.0
 */
public interface OperationReportJobService {


    /**
     * 获取 性别，区域，年龄的统计数据
     * @param cal 统计日期
     * @return
     */
    public void insertOperationGroupData(Calendar cal);

    /**
     * 插入投资类的信息
     * @param cal
     */
    public Calendar insertOperationData(Calendar cal) ;

    /**
     * 检索运营统计数据
     * @return
     */
    public BorrowUserStatistic selectBorrowUserStatistic();


    //保存月度运营报告
    public void setMonthReport(String year, String month) throws Exception;

    //保存季度运营报告
    public void setQuarterReport(String year, String month) throws Exception;

    //保存半年运营报告
    public void setHalfYearReport(String year, String month) throws Exception;

    //保存全年度运营报告
    public void setYearReport(String year, String month) throws Exception;
}
