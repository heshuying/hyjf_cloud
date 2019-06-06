/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.pointsshop.duiba.points;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.admin.DuibaPointsModifyResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;

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

    /**
     * 更新兑吧积分调整审批状态
     *
     * @param requestBean
     * @return
     */
    boolean updatePointsModifyStatus(DuibaPointsRequest requestBean);

    /**
     * 审核后更新用户积分表
     *
     * @param requestBean
     * @return
     */
    boolean updateDuibaPoints(DuibaPointsRequest requestBean);

    /**
     * 插入兑吧明细表
     *
     * @param requestBean
     * @return
     */
    boolean insertDuibaPoints(DuibaPointsRequest requestBean);

    /**
     * 根据订单号获取订单详情
     *
     * @param orderId
     * @return
     */
    DuibaPointsModifyVO selectDuibaPointsModifyByOrdid(String orderId);
}
