package com.hyjf.admin.service;

import com.hyjf.am.response.config.SmsNoticeConfigResponse;
import com.hyjf.am.resquest.config.SmsNoticeConfigRequest;

/**
 * @author xiehuili on 2018/8/14.
 */
public interface SmsNoticeConfigService {


    /**
     * 查询通知配置列表
     * @author xiehuili
     * @return
     */
    public SmsNoticeConfigResponse initSmsNoticeConfig();
    /**
     * 查询通知配置详情
     * @param request
     * @author xiehuili
     * @return
     */
    public SmsNoticeConfigResponse smsNoticeConfigInfo(SmsNoticeConfigRequest request);
    /**
     * 添加通知配置详情
     * @param request
     * @author xiehuili
     * @return
     */
    public SmsNoticeConfigResponse insertSmsNoticeConfig(SmsNoticeConfigRequest request);

    /**
     * 修改通知配置详情
     * @param request
     * @author xiehuili
     * @return
     */
    public SmsNoticeConfigResponse updateSmsNoticeConfig(SmsNoticeConfigRequest request);
    /**
     * 关闭通知配置详情
     * @param request
     * @author xiehuili
     * @return
     */
    public SmsNoticeConfigResponse closeSmsNoticeConfig(SmsNoticeConfigRequest request);
    /**
     * 打开通知配置详情
     * @param request
     * @author xiehuili
     * @return
     */
    public SmsNoticeConfigResponse openSmsNoticeConfig(SmsNoticeConfigRequest request);

    /**
     * 唯一性验证
     * @param name
     * @author xiehuili
     * @return
     */
    public Integer onlyName(String name);
}
