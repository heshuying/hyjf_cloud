package com.hyjf.wbs.trade.service;

import com.hyjf.wbs.trade.dao.model.auto.HjhPlan;

/**
 * @Auther: wxd
 * @Date: 2019-04-30 13:55
 * @Description:
 */
public interface HjhPlanInfoService extends BaseService  {
    public HjhPlan selectHjhPlanInfo(String nid);
}
