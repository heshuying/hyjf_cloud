package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.SmsConfigService;
import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2018/8/14.
 */
@Service
public class SmsConfigServiceImpl implements SmsConfigService {
    @Autowired
    private AmConfigClient amConfigClient;
    /**
     * 查询数据
     * @param request
     * @author xiehuili
     * @return
     */
    @Override
    public SmsConfigResponse initSmsConfig(SmsConfigRequest request){
        return amConfigClient.initSmsConfig(request);
    }
    /**
     * 添加短信加固数据
     * @param request
     * @author xiehuili
     * @return
     */
    @Override
    public SmsConfigResponse insertSmsConfig(SmsConfigRequest request){
        return amConfigClient.insertSmsConfig(request);
    }
    /**
     * 修改短信加固数据
     * @param request
     * @author xiehuili
     * @return
     */
    @Override
    public SmsConfigResponse updateSmsConfig(SmsConfigRequest request){
        return amConfigClient.updateSmsConfig(request);
    }
}
