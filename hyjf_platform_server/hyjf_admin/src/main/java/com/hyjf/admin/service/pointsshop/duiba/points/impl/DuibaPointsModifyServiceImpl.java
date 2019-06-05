/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.pointsshop.duiba.points.impl;

import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsModifyService;
import com.hyjf.am.response.admin.DuibaPointsModifyResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsModifyServiceImpl, v0.1 2019/5/29 9:50
 */
@Service
public class DuibaPointsModifyServiceImpl extends BaseServiceImpl implements DuibaPointsModifyService {

    @Autowired
    AmMarketClient amMarketClient;

    @Autowired
    AmUserClient amUserClient;

    /**
     * 兑吧积分账户修改明细
     *
     * @param requestBean
     * @return
     */
    @Override
    public DuibaPointsModifyResponse selectDuibaPointsModifyList(DuibaPointsRequest requestBean) {
        return amMarketClient.selectDuibaPointsModifyList(requestBean);
    }

    /**
     * 更新兑吧积分调整审批状态
     *
     * @param requestBean
     * @return
     */
    @Override
    public boolean updatePointsModifyStatus(DuibaPointsRequest requestBean) {
        return amMarketClient.updatePointsModifyStatus(requestBean);
    }

    /**
     * 审核后更新用户积分表
     *
     * @param requestBean
     * @return
     */
    @Override
    public boolean updateDuibaPoints(DuibaPointsRequest requestBean) {
        return amUserClient.updateDuibaPoints(requestBean);
    }

    /**
     * 插入兑吧交易明细表
     *
     * @param requestBean
     * @return
     */
    @Override
    public boolean insertDuibaPoints(DuibaPointsRequest requestBean) {
        return amMarketClient.insertDuibaPoints(requestBean);
    }
}
