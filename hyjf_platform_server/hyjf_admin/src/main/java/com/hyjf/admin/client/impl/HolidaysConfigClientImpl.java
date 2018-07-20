package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.HolidaysConfigClient;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.am.resquest.admin.AdminHolidaysConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author by xiehuili on 2018/7/16.
 */
@Service
public class HolidaysConfigClientImpl implements HolidaysConfigClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    @Override
    public HolidaysConfigResponse initHolidaysConfig(AdminHolidaysConfigRequest adminRequest){
        String url = "http://AM-CONFIG/am-config/holidaysConfig/list";
        HolidaysConfigResponse response = restTemplate.postForEntity(url,adminRequest,HolidaysConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    /**
     * 查询节假日配置详情页面
     * @param id
     * @return
     */
    @Override
    public HolidaysConfigResponse getHolidaysConfigById(Integer id){
        String url = "http://AM-CONFIG/am-config/holidaysConfig/info";
        HolidaysConfigResponse response = restTemplate.postForEntity(url,id,HolidaysConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 编辑保存节假日配置
     * @return
     */
    @Override
    public HolidaysConfigResponse saveHolidaysConfig(AdminHolidaysConfigRequest req){
        String url = "http://AM-CONFIG/am-config/holidaysConfig/insert";
        HolidaysConfigResponse response = restTemplate.postForEntity(url,req,HolidaysConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 修改节假日配置
     * @return
     */
    @Override
    public HolidaysConfigResponse updateHolidaysConfig(AdminHolidaysConfigRequest req){
        String url = "http://AM-CONFIG/am-config/holidaysConfig/update";
        HolidaysConfigResponse response = restTemplate.postForEntity(url,req,HolidaysConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

}
