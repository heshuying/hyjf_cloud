/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;

import javax.validation.Valid;
import java.util.List;

/**
 * @author dangzw
 * @version AdminHolidaysConfigService, v0.1 2018/11/13 17:08
 */
public interface AdminHolidaysConfigService {

    /**
     * 节假日配置-列表查询
     * @param holidaysConfig
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<HolidaysConfig> getRecordList(HolidaysConfig holidaysConfig, int limitStart, int limitEnd);

    /**
     * 节假日配置-画面迁移(含有id更新，不含有id添加)
     * @param id
     * @return
     */
    HolidaysConfig getRecord(Integer id);

    /**
     * 节假日配置-添加活动信息
     * @param request
     * @return
     */
    int insertHolidays(@Valid AdminHolidaysConfigRequest request);

    /**
     * 节假日配置-修改活动维护信息
     * @param request
     * @return
     */
    int updateHolidays(@Valid AdminHolidaysConfigRequest request);

    /**
     * 节假日配置-分页获得所有数据条数
     * @return
     */
    Integer getTotalCount();
}
