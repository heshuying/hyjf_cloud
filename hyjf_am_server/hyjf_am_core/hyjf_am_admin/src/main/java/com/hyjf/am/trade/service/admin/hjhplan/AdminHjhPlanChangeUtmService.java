/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan;

import com.hyjf.am.trade.dao.model.auto.TenderUtmChangeLog;
import com.hyjf.am.vo.trade.hjh.HjhPlanAccedeCustomizeVO;

/**
 * @author cui
 * @version AdminHjhPlanChangeUtmService, v0.1 2019/6/18 16:53
 */
public interface AdminHjhPlanChangeUtmService {

    HjhPlanAccedeCustomizeVO getHjhPlanTender(String planOrderId);

    int updateTenderUtm(TenderUtmChangeLog log);
}
