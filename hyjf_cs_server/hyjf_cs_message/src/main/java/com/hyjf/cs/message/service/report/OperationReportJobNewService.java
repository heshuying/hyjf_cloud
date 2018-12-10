package com.hyjf.cs.message.service.report;

import com.hyjf.am.vo.message.OperationReportJobBean;
import com.hyjf.cs.message.bean.ic.BorrowUserStatistic;

import java.util.Calendar;

/**
 * @author tanyy
 * @version 2.0
 */
public interface OperationReportJobNewService {


    /**
     * 获取 性别，区域，年龄的统计数据
     * @param bean 统计日期
     * @return
     */
    public void insertOperationGroupData(OperationReportJobBean bean);

    /**
     * 插入出借类的信息
     * @param bean
     */
    public Calendar insertOperationData(OperationReportJobBean bean) ;

    /**
     * 检索运营统计数据
     * @return
     */
    public BorrowUserStatistic selectBorrowUserStatistic();


    //保存月度运营报告
    public void setMonthReport(String year, String month,OperationReportJobBean bean) throws Exception;

    //保存季度运营报告
    public void setQuarterReport(String year, String month,OperationReportJobBean bean) throws Exception;

    //保存半年运营报告
    public void setHalfYearReport(String year, String month,OperationReportJobBean bean) throws Exception;

    //保存全年度运营报告
    public void setYearReport(String year, String month,OperationReportJobBean bean) throws Exception;
}
