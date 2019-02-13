/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa;

import com.hyjf.am.vo.trade.bifa.BifaHjhPlanInfoEntityVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

/**
 * @author jun
 * @version BifaHjhPlanInfoService, v0.1 2019/1/31 15:35
 */
public interface BifaHjhPlanInfoService extends BaseHgDateReportService {

    BifaHjhPlanInfoEntityVO getBifaHjhPlanInfoFromMongoDB(String planNid);

    HjhPlanVO getHjhPlan(String planNid);

    boolean convertBifaHjhPlanInfo(HjhPlanVO hjhplan, BifaHjhPlanInfoEntityVO bifaHjhPlanInfoEntity);

    void insertHjhPlanInfoReportData(BifaHjhPlanInfoEntityVO reportResult);
}
