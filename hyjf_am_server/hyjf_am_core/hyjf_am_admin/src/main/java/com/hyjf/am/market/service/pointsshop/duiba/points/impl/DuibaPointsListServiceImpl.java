/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.pointsshop.duiba.points.impl;

import com.hyjf.am.market.dao.mapper.auto.DuibaPointsMapper;
import com.hyjf.am.market.dao.mapper.customize.market.DuibaPointsListCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.DuibaPoints;
import com.hyjf.am.market.service.pointsshop.duiba.points.DuibaPointsListService;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.vo.admin.DuibaPointsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsListServiceImpl, v0.1 2019/5/29 9:48
 */
@Service
public class DuibaPointsListServiceImpl implements DuibaPointsListService {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DuibaPointsListCustomizeMapper duibaPointsListCustomizeMapper;

    @Autowired
    DuibaPointsMapper duibaPointsMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 查询兑吧积分明细件数
     *
     * @param request
     * @return
     */
    @Override
    public Integer selectDuibaPointsCount(DuibaPointsRequest request) {
        return duibaPointsListCustomizeMapper.selectDuibaPointsCount(request);
    }

    /**
     * 查询兑吧积分明细列表
     *
     * @param request
     * @return
     */
    @Override
    public List<DuibaPointsVO> selectDuibaPointsList(DuibaPointsRequest request) {
        return duibaPointsListCustomizeMapper.selectDuibaPointsList(request);
    }

    /**
     * 插入明细表
     *
     * @param request
     * @return
     */
    @Override
    public boolean insertDuibaPoints(DuibaPointsRequest request) {
        Integer userId = request.getUserIdList().get(0);
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            logger.error("插入积分明细表获取用户信息失败，userId：" + userId);
            return false;
        }

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        if (null == userInfo) {
            logger.error("插入积分明细表获取用户详细信息失败，userId：" + userId);
            return false;
        }

        DuibaPoints duibaPoints = new DuibaPoints();
        duibaPoints.setUserId(userId);
        duibaPoints.setUserName(user.getUsername());
        duibaPoints.setTrueName(userInfo.getTruename());
        duibaPoints.setPoints(request.getModifyPoints());
        duibaPoints.setTotal(user.getPointsCurrent());
        // 调增
        if (0 == request.getModifyType()) {
            duibaPoints.setType(2);
        } else {
            duibaPoints.setType(3);
        }
        duibaPoints.setHyOrderId(request.getOrderId());
        duibaPoints.setCreateBy(request.getModifyId());
        duibaPoints.setCreateTime(new Date());

        return duibaPointsMapper.insertSelective(duibaPoints) > 0 ? true : false;
    }
}
