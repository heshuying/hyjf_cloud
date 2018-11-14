/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.holidaysconfig;

import com.hyjf.am.response.admin.AdminHolidaysConfigResponse;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;

/**
 * @author dangzw
 * @version AccountBalanceService, v0.1 2018/11/13 16:12
 */
public interface HolidaysConfigService {

    /**
     *节假日配置-列表查询
     * @param request
     * @return
     */
    AdminHolidaysConfigResponse getRecordList(AdminHolidaysConfigRequest request);

    /**
     * 节假日配置-画面迁移(含有id更新，不含有id添加)
     * @param request
     * @return
     */
    AdminHolidaysConfigResponse getInfoList(AdminHolidaysConfigRequest request);

    /**
     *节假日配置-添加活动信息
     * @param request
     * @return
     */
    AdminHolidaysConfigResponse insert(AdminHolidaysConfigRequest request);

    /**
     *节假日配置-修改活动维护信息
     * @param request
     * @return
     */
    AdminHolidaysConfigResponse update(AdminHolidaysConfigRequest request);
}
