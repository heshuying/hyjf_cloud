/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.resquest.market.DuiBaPointsDetailRequest;
import com.hyjf.am.vo.market.DuiBaPointsListDetailVO;

import java.util.List;

/**
 * @author wangjun
 * @version DuiBaOrderCustomizeMapper, v0.1 2019/6/11 14:00
 */
public interface DuiBaOrderCustomizeMapper {
    /**
     * 根据条件查询积分明细条数
     * @param request
     * @return
     */
    Integer countPointsDetail(DuiBaPointsDetailRequest request);

    /**
     * 根据条件查询积分明细列表
     * @param request
     * @return
     */
    List<DuiBaPointsListDetailVO> selectPointsDetail(DuiBaPointsDetailRequest request);

    /**
     * 根据条件查询月度/年度积分总计
     * @param request
     * @return
     */
    Integer selectPointsTotal(DuiBaPointsDetailRequest request);
}
