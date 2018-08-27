package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.SmsConfig;
import com.hyjf.am.resquest.admin.SmsConfigRequest;
import com.hyjf.am.vo.config.SmsConfigVO;

/**
 * @author xiasq
 * @version SmsConfigService, v0.1 2018/4/24 18:18
 */
public interface SmsConfigService {
    SmsConfig findOne();
    /**
     * 查询数据
     * @param request
     * @author xiehuili
     * @return
     */
    SmsConfigVO initSmsConfig(SmsConfigRequest request);
    /**
     * 添加短信加固数据
     * @param request
     * @author xiehuili
     * @return
     */
    public int insertSmsConfig(SmsConfigRequest request);
    /**
     * 修改短信加固数据
     * @param request
     * @author xiehuili
     * @return
     */
    public int updateSmsConfig(SmsConfigRequest request);
}
