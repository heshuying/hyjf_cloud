package com.hyjf.am.trade.service.screen;

import com.hyjf.am.resquest.trade.ScreenDataBean;

/**
 * @author lisheng
 * @version ScreenDataService, v0.1 2019/3/18 14:16
 */

public interface ScreenDataService {
    /**
     * 添加一条运营部用户资金明细
     * @param screenDataBean
     * @return
     */
    Integer addUserOperateList(ScreenDataBean screenDataBean);
}
