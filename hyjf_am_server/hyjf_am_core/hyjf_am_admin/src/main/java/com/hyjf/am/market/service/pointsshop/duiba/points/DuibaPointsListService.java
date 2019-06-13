/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.pointsshop.duiba.points;

import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsListService, v0.1 2019/5/29 9:48
 */
public interface DuibaPointsListService {
    /**
     * 查询兑吧积分明细件数
     *
     * @param request
     * @return
     */
    Integer selectDuibaPointsCount(DuibaPointsRequest request);

    /**
     * 查询兑吧积分明细列表
     *
     * @param request
     * @return
     */
    List<DuibaPointsVO> selectDuibaPointsList(DuibaPointsRequest request);

    /**
     * 插入明细表
     *
     * @param duibaPointsVO
     * @return
     */
    boolean insertDuibaPoints(DuibaPointsVO duibaPointsVO);

    /**
     * 根据兑吧订单获取相关积分明细
     *
     * @param ordId
     * @return
     */
    DuibaPointsVO getDuibaPointsByOrdId(String ordId,Integer businessName);
}
