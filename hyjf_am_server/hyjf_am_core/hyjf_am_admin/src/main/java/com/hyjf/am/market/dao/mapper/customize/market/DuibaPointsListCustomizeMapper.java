/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsListCustomizeMapper, v0.1 2019/5/30 10:54
 */
public interface DuibaPointsListCustomizeMapper {

    /**
     * 查询兑吧积分明细列表
     *
     * @param request
     * @return
     */
    List<DuibaPointsVO> selectDuibaPointsList(DuibaPointsRequest request);

    /**
     * 查询兑吧积分明细件数
     *
     * @param request
     * @return
     */
    Integer selectDuibaPointsCount(DuibaPointsRequest request);
}
