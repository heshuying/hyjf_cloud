/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.pointsshop.duiba.points;

import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.admin.DuibaPointsUserVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsService, v0.1 2019/5/29 9:48
 */
public interface DuibaPointsService extends BaseService {

    /**
     * 查询条数
     *
     * @param request
     * @return
     */
    Integer selectDuibaPointsUserCount(DuibaPointsRequest request);

    /**
     * 查询记录
     *
     * @param request
     * @return
     */
    List<DuibaPointsUserVO> selectDuibaPointsUser(DuibaPointsRequest request);
}
