/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.pointsshop.duiba.points;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.admin.DuibaPointsUserResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsService, v0.1 2019/5/29 9:48
 */
public interface DuibaPointsService extends BaseService {
    /**
     * 兑吧积分账户查询列表
     *
     * @param requestBean
     * @return
     */
    DuibaPointsUserResponse selectDuibaPointsUser(DuibaPointsRequest requestBean);

    /**
     * 批量查询用户剩余积分是否足够
     * @param requestBean
     * @return
     */
    boolean selectRemainPoints(DuibaPointsRequest requestBean);

    /**
     * 插入积分审批表
     *
     * @param requestBean
     * @return
     */
    boolean insertPointsModifyList(DuibaPointsRequest requestBean);
}
