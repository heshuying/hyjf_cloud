/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;

import java.util.List;

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
     * 倒序查询假期配置表
     * @param orderByClause
     * @return
     */
    List<HolidaysConfig> selectHolidaysConfig(String orderByClause);

    /**
     * 分页查询节假日配置
     * @return
     */
    List<HolidaysConfig>  selectHolidaysConfigListByPage(HolidaysConfig holidaysConfig, int limitStart, int limitEnd);

    /**
     * 查询节假日配置详情页面
     * @return
     */
    HolidaysConfig  selectHolidaysConfigInfo(Integer id);

    /**
     * 添加节假日配置详情页面
     * @return
     */
    Integer  insertHolidaysConfigInfo(AdminHolidaysConfigRequest adminRequest);

    /**
     * 修改节假日配置详情页面
     * @return
     */
    Integer  updateHolidaysConfigInfo(AdminHolidaysConfigRequest adminRequest);



    /**
     * 初始化年度配置
     * @param year
     */
    void initCurrentYearConfig(int year);
}
