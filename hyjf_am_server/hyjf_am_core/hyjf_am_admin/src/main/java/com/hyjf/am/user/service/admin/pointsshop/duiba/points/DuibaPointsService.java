/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.pointsshop.duiba.points;

import com.hyjf.am.market.dao.model.auto.DuibaPoints;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.user.service.BaseService;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsService, v0.1 2019/5/29 9:48
 */
public interface DuibaPointsService extends BaseService {

    /**
     * 查询条数
     *
     * @param request
     * @return
     */
    Integer selectDuibaPointsCount(DuibaPointsRequest request);

    /**
     * 查询记录
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    List<DuibaPoints> selectDuibaPoints(DuibaPointsRequest request, int offset, int limit);
}
