/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.common.exception.ReturnMessageException;

import java.util.List;

/**
 * @author yaoy
 * @version HolidaysConfigService, v0.1 2018/6/22 10:14
 */
public interface HolidaysConfigService {

    /**
     * 更新节假日配置表
     * @return
     * @throws ReturnMessageException
     */
    boolean updateHolidaysConfig() throws ReturnMessageException;
    /**
     * 倒序查询假期配置表
     * @param orderByClause
     * @return
     */
    List<HolidaysConfig> selectHolidaysConfig(String orderByClause);
}
