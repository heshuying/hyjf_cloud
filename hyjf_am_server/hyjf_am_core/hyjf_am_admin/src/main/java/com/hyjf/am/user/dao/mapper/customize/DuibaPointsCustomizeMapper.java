/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsUserVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsCustomizeMapper, v0.1 2019/5/29 14:34
 */
public interface DuibaPointsCustomizeMapper {
    /**
     * 查询用户表中兑吧积分详情
     *
     * @param request
     * @return
     */
    List<DuibaPointsUserVO> selectDuibaPointsUser(DuibaPointsRequest request);

    /**
     * 查询用户表总数
     *
     * @param request
     * @return
     */
    Integer selectDuibaPointsUserCount(DuibaPointsRequest request);
}
