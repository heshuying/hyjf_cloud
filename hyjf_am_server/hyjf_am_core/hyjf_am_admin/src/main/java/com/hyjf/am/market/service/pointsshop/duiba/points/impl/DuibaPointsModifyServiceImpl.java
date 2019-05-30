/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.pointsshop.duiba.points.impl;

import com.hyjf.am.market.dao.mapper.customize.market.DuibaPointsModifyCustomizeMapper;
import com.hyjf.am.market.service.pointsshop.duiba.points.DuibaPointsModifyService;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsModifyServiceImpl, v0.1 2019/5/29 9:50
 */
@Service
public class DuibaPointsModifyServiceImpl implements DuibaPointsModifyService {

    @Autowired
    DuibaPointsModifyCustomizeMapper duibaPointsModifyCustomizeMapper;

    /**
     * 查询兑吧积分修改明细件数
     *
     * @param request
     * @return
     */
    @Override
    public Integer selectDuibaPointsModifyCount(DuibaPointsRequest request) {
        return duibaPointsModifyCustomizeMapper.selectDuibaPointsModifyCount(request);
    }

    /**
     * 查询兑吧积分修改明细
     *
     * @param request
     * @return
     */
    @Override
    public List<DuibaPointsModifyVO> selectDuibaPointsModifyList(DuibaPointsRequest request) {
        return duibaPointsModifyCustomizeMapper.selectDuibaPointsModifyList(request);
    }
}
