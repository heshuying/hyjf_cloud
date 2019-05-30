package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.resquest.admin.DuibaOrderRequest;
import com.hyjf.am.vo.admin.DuibaOrderVO;

import java.util.List;

public interface DuibaOrdersCustomizeMapper {
    /**
     * 查询兑吧积分明细列表
     *
     * @param request
     * @return
     */
    List<DuibaOrderVO> selectDuibaOrderList(DuibaOrderRequest request);

    /**
     * 查询兑吧积分明细件数
     *
     * @param request
     * @return
     */
    Integer selectDuibaOrderCount(DuibaOrderRequest request);
}