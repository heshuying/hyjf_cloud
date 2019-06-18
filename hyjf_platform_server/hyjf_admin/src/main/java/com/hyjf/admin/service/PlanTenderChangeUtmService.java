/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.vo.trade.hjh.HjhAccedeCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAccedeCustomizeVO;

/**
 * @author cui
 * @version PlanTenderChangeUtmService, v0.1 2019/6/18 17:39
 */
public interface PlanTenderChangeUtmService {

    HjhPlanAccedeCustomizeVO getPlanTenderInfo(String planOrderId);


}
