/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.pointsshop.duiba.points;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.admin.DuibaPointsModifyResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsModifyService, v0.1 2019/5/29 9:49
 */
public interface DuibaPointsModifyService extends BaseService {
    /**
     * 兑吧积分账户修改明细
     *
     * @param requestBean
     * @return
     */
    DuibaPointsModifyResponse selectDuibaPointsModifyList(DuibaPointsRequest requestBean);
}
