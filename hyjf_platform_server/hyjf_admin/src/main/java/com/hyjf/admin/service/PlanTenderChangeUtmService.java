/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.response.admin.TenderUpdateUtmHistoryResponse;
import com.hyjf.am.resquest.trade.UpdateTenderUtmExtRequest;
import com.hyjf.am.vo.trade.hjh.HjhAccedeCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAccedeCustomizeVO;

/**
 * @author cui
 * @version PlanTenderChangeUtmService, v0.1 2019/6/18 17:39
 */
public interface PlanTenderChangeUtmService {

    HjhPlanAccedeCustomizeVO getPlanTenderInfo(String planOrderId);


    AdminResult updateTenderUtm(UpdateTenderUtmExtRequest extRequest);

    TenderUpdateUtmHistoryResponse getPlanTenderChangeLog(String nid);
}
