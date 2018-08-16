package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.SmsNoticeConfigService;
import com.hyjf.am.response.config.SmsNoticeConfigResponse;
import com.hyjf.am.resquest.config.SmsNoticeConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2018/8/14.
 */
@Service
public class SmsNoticeConfigServiceImpl implements SmsNoticeConfigService {
    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 查询通知配置列表
     * @author xiehuili
     * @return
     */
    @Override
    public SmsNoticeConfigResponse initSmsNoticeConfig(){
        return amConfigClient.initSmsNoticeConfig();
    }

    /**
     * 查询通知配置详情
     * @param request
     * @author xiehuili
     * @return
     */
    @Override
    public SmsNoticeConfigResponse smsNoticeConfigInfo(SmsNoticeConfigRequest request){
        return amConfigClient.smsNoticeConfigInfo(request);
    }
    /**
     * 添加通知配置详情
     * @param request
     * @author xiehuili
     * @return
     */
    @Override
    public SmsNoticeConfigResponse insertSmsNoticeConfig(SmsNoticeConfigRequest request){
        return amConfigClient.insertSmsNoticeConfig(request);
    }

    /**
     * 修改通知配置详情
     * @param request
     * @author xiehuili
     * @return
     */
    @Override
    public SmsNoticeConfigResponse updateSmsNoticeConfig(SmsNoticeConfigRequest request){
        return amConfigClient.updateSmsNoticeConfig(request);
    }
    /**
     * 关闭通知配置详情
     * @param request
     * @author xiehuili
     * @return
     */
    @Override
    public SmsNoticeConfigResponse closeSmsNoticeConfig(SmsNoticeConfigRequest request){
        return amConfigClient.closeSmsNoticeConfig(request);
    }
    /**
     * 打开通知配置详情
     * @param request
     * @author xiehuili
     * @return
     */
    @Override
    public SmsNoticeConfigResponse openSmsNoticeConfig(SmsNoticeConfigRequest request){
        return amConfigClient.openSmsNoticeConfig(request);
    }

    /**
     * 唯一性验证
     * @param name
     * @author xiehuili
     * @return
     */
    @Override
    public Integer onlyName(String name){
        return amConfigClient.onlyName(name);
    }
}
