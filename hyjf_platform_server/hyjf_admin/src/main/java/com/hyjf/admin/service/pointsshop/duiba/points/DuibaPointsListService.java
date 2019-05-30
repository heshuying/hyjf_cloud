/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.pointsshop.duiba.points;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.admin.DuibaPointsResponse;
import com.hyjf.am.response.admin.DuibaPointsUserResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsListService, v0.1 2019/5/29 9:48
 */
public interface DuibaPointsListService extends BaseService {
    /**
     * 查询兑吧积分明细
     *
     * @param requestBean
     * @return
     */
    DuibaPointsResponse selectDuibaPointsList(DuibaPointsRequest requestBean);
}
