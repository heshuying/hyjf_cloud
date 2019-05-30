/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.pointsshop.duiba.points.impl;

import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.user.service.admin.pointsshop.duiba.points.DuibaPointsService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.DuibaPointsUserVO;
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
    public Integer selectDuibaPointsUserCount(DuibaPointsRequest request) {
        return this.duibaPointsCustomizeMapper.selectDuibaPointsUserCount(request);
    }

    /**
     * 查询记录
     *
     * @param request
     * @return
     */
    @Override
    public List<DuibaPointsUserVO> selectDuibaPointsUser(DuibaPointsRequest request) {
        return this.duibaPointsCustomizeMapper.selectDuibaPointsUser(request);
    }
}
