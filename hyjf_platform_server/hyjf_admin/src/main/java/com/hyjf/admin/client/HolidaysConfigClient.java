package com.hyjf.admin.client;

import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;

/**
 * @author by xiehuili on 2018/7/16.
 */
public interface HolidaysConfigClient {

    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    public HolidaysConfigResponse initHolidaysConfig(AdminHolidaysConfigRequest adminRequest);
    /**
     * 查询节假日配置详情页面
     * @param id
     * @return
     */
    public HolidaysConfigResponse getHolidaysConfigById(Integer id);

    /**
     * 编辑保存节假日配置
     * @return
     */
    public HolidaysConfigResponse saveHolidaysConfig(AdminHolidaysConfigRequest req);

    /**
     * 修改节假日配置
     * @return
     */
    public HolidaysConfigResponse updateHolidaysConfig(AdminHolidaysConfigRequest req);


}
