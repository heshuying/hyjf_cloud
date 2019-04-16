/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.batch;

import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * WBS系统智投信息推送Service
 *
 * @author liuyang
 * @version WbsHjhPlanInfoService, v0.1 2019/4/16 9:36
 */
public interface WbsHjhPlanInfoService extends BaseService {
    /**
     * 获取开放额度 > 0 的智投服务
     *
     * @return
     */
    List<HjhPlan> selectWbsSendHjhPlanList();
}
