/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.pointsshop.duiba.points.impl;

import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsListService;
import com.hyjf.am.response.admin.DuibaPointsResponse;
import com.hyjf.am.response.admin.DuibaPointsUserResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsListServiceImpl, v0.1 2019/5/29 9:48
 */
@Service
public class DuibaPointsListServiceImpl extends BaseServiceImpl implements DuibaPointsListService {

    @Autowired
    AmMarketClient amMarketClient;

    /**
     * 查询兑吧积分明细
     *
     * @param requestBean
     * @return
     */
    @Override
    public DuibaPointsResponse selectDuibaPointsList(DuibaPointsRequest requestBean) {
        DuibaPointsResponse response = amMarketClient.selectDuibaPointsList(requestBean);
        return response;
    }
}
