/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.plancapital;

import com.hyjf.am.resquest.admin.HjhPlanCapitalActualRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalActualVO;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.message.bean.ic.HjhPlanCapitalActual;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalActualService, v0.1 2019/4/23 9:55
 */
public interface HjhPlanCapitalActualService extends BaseService {
    /**
     * 实际结果插入mongo
     *
     * @param list
     * @return
     */
    Boolean insertPlanCaptialActual(List<HjhPlanCapitalActualVO> list);

    /**
     * 实际结果查询总件数
     *
     * @param request
     * @return
     */
    Integer getPlanCapitalActualCount(HjhPlanCapitalActualRequest request);

    /**
     * 实际结果查询列表
     *
     * @param request
     * @return
     */
    List<HjhPlanCapitalActual> getPlanCapitalActualList(HjhPlanCapitalActualRequest request);
}
