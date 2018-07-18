/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service;

import java.util.Calendar;

/**
 * @author fuqiang
 * @version StatisticsOperationReportService, v0.1 2018/7/18 10:18
 */
public interface StatisticsOperationReportService {
    /**
     * 插入性别，性别 ，区域的统计信息
     * @param cal
     */
    void insertOperationGroupData(Calendar cal);

    /**
     * 插入投资类的信息
     * @param cal
     */
    void insertOperationData(Calendar cal);
}
