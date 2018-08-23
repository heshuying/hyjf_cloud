/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.common.exception.ReturnMessageException;

import java.util.Date;

/**
 * @author yaoy
 * @version HolidaysConfigService, v0.1 2018/6/22 10:14
 */
public interface HolidaysConfigService {

    /**
     * 更新节假日配置表
     * @param year
     * @return
     * @throws ReturnMessageException
     */
    boolean updateHolidaysConfig(int year) throws ReturnMessageException;

    /**
     * 初始化年度配置
     * @param year
     */
    void initCurrentYearConfig(int year);

    /**
     * 判断某天是否是工作日
     * @param date
     * @return
     */
    boolean isWorkdateOnSomeDay(Date date);

    /**
     * 取从某天开始推后的第一个工作日开始时间
     * @param somedate
     * @return
     */
    Date getFirstWorkdateAfterSomedate(Date somedate);
}
