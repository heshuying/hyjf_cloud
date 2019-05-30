/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.pointsshop.duiba.points.impl;

import com.hyjf.am.market.service.pointsshop.duiba.points.DuibaPointsListService;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsListServiceImpl, v0.1 2019/5/29 9:48
 */
@Service
public class DuibaPointsListServiceImpl implements DuibaPointsListService {

    /**
     * 查询兑吧积分明细件数
     *
     * @param request
     * @return
     */
    @Override
    public Integer selectDuibaPointsCount(DuibaPointsRequest request) {
        return null;
    }


    /**
     * 查询兑吧积分明细列表
     *
     * @param request
     * @return
     */
    @Override
    public List<DuibaPointsVO> selectDuibaPointsList(DuibaPointsRequest request) {
        return null;
    }
}
