/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.pointsshop.duiba.points.impl;

import com.hyjf.am.market.dao.model.auto.DuibaPoints;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.user.service.admin.pointsshop.duiba.points.DuibaPointsService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsServiceImpl, v0.1 2019/5/29 9:48
 */
@Service
public class DuibaPointsServiceImpl extends BaseServiceImpl implements DuibaPointsService {


    /**
     * 查询条数
     *
     * @param request
     * @return
     */
    @Override
    public Integer selectDuibaPointsCount(DuibaPointsRequest request) {

//        // 账户名称
//        if (StringUtils.isNotEmpty(request.getUserNameSrch())) {
//
//        }
//        // 姓名
//        if (StringUtils.isNotEmpty(request.getTrueNameSrch())) {
//
//        }
//
        return 0;
    }

    /**
     * 查询记录
     *
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<DuibaPoints> selectDuibaPoints(DuibaPointsRequest request, int offset, int limit) {
        return null;
    }
}
