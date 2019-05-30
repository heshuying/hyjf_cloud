/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsModifyCustomizeMapper, v0.1 2019/5/30 14:01
 */
public interface DuibaPointsModifyCustomizeMapper {
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
}
