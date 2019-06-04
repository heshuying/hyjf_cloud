/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.pointsshop.duiba.points.impl;

import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.DuibaPointsUserResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsServiceImpl, v0.1 2019/5/29 9:48
 */
@Service
public class DuibaPointsServiceImpl extends BaseServiceImpl implements DuibaPointsService {

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    AmMarketClient amMarketClient;

    /**
     * 兑吧积分账户查询列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public DuibaPointsUserResponse selectDuibaPointsUser(DuibaPointsRequest requestBean) {
        DuibaPointsUserResponse response = amUserClient.selectDuibaPointsUser(requestBean);
        return response;
    }

    /**
     * 批量查询用户剩余积分是否足够
     *
     * @param requestBean
     * @return
     */
    @Override
    public boolean selectRemainPoints(DuibaPointsRequest requestBean) {
        boolean result = amUserClient.selectRemainPoints(requestBean);
        return result;
    }

    /**
     * 插入积分审批表
     *
     * @param requestBean
     * @return
     */
    @Override
    public boolean insertPointsModifyList(DuibaPointsRequest requestBean) {
        boolean result = amMarketClient.insertPointsModifyList(requestBean);
        return result;
    }
}
