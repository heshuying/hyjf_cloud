package com.hyjf.admin.service;

import com.hyjf.am.response.admin.ProtocolLogResponse;
import com.hyjf.am.resquest.admin.ProtocolLogRequest;

/**
 * @author：yinhui
 * @Date: 2018/8/10  13:56
 */
public interface ProtocolLogService {

    /**
     * 分页查询
     * @param request
     * @return
     */
    ProtocolLogResponse searchPage(ProtocolLogRequest request);
}
