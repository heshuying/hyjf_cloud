/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.pointsshop.duiba.points;

import com.hyjf.am.market.dao.model.auto.DuibaPointsModify;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsModifyService, v0.1 2019/5/29 9:49
 */
public interface DuibaPointsModifyService {

    /**
     * 查询兑吧积分修改明细件数
     *
     * @param request
     * @return
     */
    Integer selectDuibaPointsModifyCount(DuibaPointsRequest request);

    /**
     * 查询兑吧积分修改明细
     *
     * @param request
     * @return
     */
    List<DuibaPointsModifyVO> selectDuibaPointsModifyList(DuibaPointsRequest request);

    /**
     * 插入积分审批表
     *
     * @param duibaPointsModifyVO
     * @return
     */
    boolean insertPointsModifyList(DuibaPointsModifyVO duibaPointsModifyVO);

    /**
     * 更新兑吧积分调整审批状态
     *
     * @param request
     * @return
     */
    boolean updatePointsModifyStatus(DuibaPointsRequest request);

    /**
     * 根据订单号获取订单详情
     *
     * @param ordId
     * @return
     */
    DuibaPointsModify selectDuibaPointsModifyByOrdid(String ordId);
}
