/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.pointsshop.duiba.points.impl;

import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsService;
import com.hyjf.am.response.admin.DuibaPointsResponse;
import com.hyjf.am.resquest.admin.CertReportLogRequestBean;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsServiceImpl, v0.1 2019/5/29 9:48
 */
@Service
public class DuibaPointsServiceImpl extends BaseServiceImpl implements DuibaPointsService {
    @Autowired
    AmUserClient amUserClient;

    /**
     * 兑吧积分账户查询列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public DuibaPointsResponse selectDuibaPoints(DuibaPointsRequest requestBean) {
        DuibaPointsResponse response = amUserClient.selectDuibaPoints(requestBean);
        return response;
    }
}
