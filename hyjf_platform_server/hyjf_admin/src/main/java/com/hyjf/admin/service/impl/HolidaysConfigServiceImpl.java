package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.HolidaysConfigClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.HolidaysConfigService;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by xiehuili on 2018/7/16.
 */
@Service
public class HolidaysConfigServiceImpl extends BaseServiceImpl implements HolidaysConfigService {

    @Autowired
    private AmConfigClient amConfigClient;
    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    @Override
    public HolidaysConfigResponse initHolidaysConfig(AdminHolidaysConfigRequest adminRequest){
        return amConfigClient.initHolidaysConfig(adminRequest);
    }
    /**
     * 查询节假日配置详情页面
     * @param id
     * @return
     */
    @Override
    public HolidaysConfigResponse getHolidaysConfigById(Integer id){
        return amConfigClient.getHolidaysConfigById(id);
    }

    /**
     * 编辑保存节假日配置
     * @return
     */
    @Override
    public HolidaysConfigResponse saveHolidaysConfig(AdminHolidaysConfigRequest req){
        return amConfigClient.saveHolidaysConfig(req);
    }

    /**
     * 修改节假日配置
     * @return
     */
    @Override
    public HolidaysConfigResponse updateHolidaysConfig(AdminHolidaysConfigRequest req){
        return amConfigClient.updateHolidaysConfig(req);
    }




}
