package com.hyjf.am.trade.service.api.wrb;

import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;

/**
 * @author lisheng
 * @version UserRegisterService, v0.1 2018/9/19 15:25
 */

public interface UserRegisterService {
    /**
     * 根据机构编号检索机构信息
     *
     * @param instCode
     * @return
     */
    HjhInstConfig selectInstConfigByInstCode(String instCode);
}
