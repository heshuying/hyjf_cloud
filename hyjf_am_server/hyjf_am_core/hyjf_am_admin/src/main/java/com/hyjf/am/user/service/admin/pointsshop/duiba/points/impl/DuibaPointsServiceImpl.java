/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.pointsshop.duiba.points.impl;

import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.user.dao.model.auto.UserExample;
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

    /**
     * 批量查询用户剩余积分是否足够
     *
     * @param request
     * @return
     */
    @Override
    public boolean selectRemainPoints(DuibaPointsRequest request) {
        UserExample example = new UserExample();
        // 查询用户中剩余积分比调整积分少的
        example.createCriteria().andUserIdIn(request.getUserIdList()).andPointsCurrentLessThan(request.getModifyPoints());
        Integer count = this.userMapper.countByExample(example);
        if (count > 0) {
            return false;
        }
        return true;
    }

    /**
     * 审核后更新用户积分表
     *
     * @param request
     * @return
     */
    @Override
    public boolean updateDuibaPoints(DuibaPointsRequest request) {
        request.setUserId(request.getUserIdList().get(0));
        return this.duibaPointsCustomizeMapper.updateDuibaPoints(request) > 0 ? true : false;
    }
}
