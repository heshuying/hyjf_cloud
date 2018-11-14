/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.holidaysconfig;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.holidaysconfig.HolidaysConfigService;
import com.hyjf.am.response.admin.AdminHolidaysConfigResponse;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dangzw
 * @version AccountBalanceServiceImpl, v0.1 2018/11/13 16:13
 */
@Service
public class HolidaysConfigServiceImpl implements HolidaysConfigService {

    @Autowired
    private AmAdminClient amAdminClient;

    /**
     * 节假日配置-列表查询
     * @param request
     * @return
     */
    @Override
    public AdminHolidaysConfigResponse getRecordList(AdminHolidaysConfigRequest request) {
        return amAdminClient.getHolidaysConfigRecordList(request);
    }

    /**
     * 节假日配置-画面迁移(含有id更新，不含有id添加)
     * @param request
     * @return
     */
    @Override
    public AdminHolidaysConfigResponse getInfoList(AdminHolidaysConfigRequest request) {
        return amAdminClient.getInfoList(request);
    }

    /**
     *节假日配置-添加活动信息
     * @param request
     * @return
     */
    @Override
    public AdminHolidaysConfigResponse insert(AdminHolidaysConfigRequest request) {
        return amAdminClient.insertHolidays(request);
    }

    /**
     *节假日配置-修改活动维护信息
     * @param request
     * @return
     */
    @Override
    public AdminHolidaysConfigResponse update(AdminHolidaysConfigRequest request) {
        return amAdminClient.updateHolidays(request);
    }
}
