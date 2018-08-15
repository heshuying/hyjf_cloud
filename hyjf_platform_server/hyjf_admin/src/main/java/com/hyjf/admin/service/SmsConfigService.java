package com.hyjf.admin.service;

import com.hyjf.am.response.config.SmsConfigResponse;
import com.hyjf.am.resquest.admin.SmsConfigRequest;

/**
 * @author xiehuili on 2018/8/14.
 */
public interface SmsConfigService {

    /**
     * 查询数据
     * @param request
     * @author xiehuili
     * @return
     */
    SmsConfigResponse initSmsConfig(SmsConfigRequest request);
    /**
     * 添加短信加固数据
     * @param request
     * @author xiehuili
     * @return
     */
    public SmsConfigResponse insertSmsConfig(SmsConfigRequest request);
    /**
     * 修改短信加固数据
     * @param request
     * @author xiehuili
     * @return
     */
    public SmsConfigResponse updateSmsConfig(SmsConfigRequest request);
}
