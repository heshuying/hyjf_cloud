/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.pointsshop.duiba.points.impl;

import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsModifyService;
import com.hyjf.am.response.admin.DuibaPointsModifyResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;
import com.hyjf.am.vo.admin.DuibaPointsVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsModifyServiceImpl, v0.1 2019/5/29 9:50
 */
@Service
public class DuibaPointsModifyServiceImpl extends BaseServiceImpl implements DuibaPointsModifyService {

    @Autowired
    AmMarketClient amMarketClient;

    @Autowired
    AmUserClient amUserClient;

    /**
     * 兑吧积分账户修改明细
     *
     * @param requestBean
     * @return
     */
    @Override
    public DuibaPointsModifyResponse selectDuibaPointsModifyList(DuibaPointsRequest requestBean) {
        return amMarketClient.selectDuibaPointsModifyList(requestBean);
    }

    /**
     * 更新兑吧积分调整审批状态
     *
     * @param requestBean
     * @return
     */
    @Override
    public boolean updatePointsModifyStatus(DuibaPointsRequest requestBean) {
        return amMarketClient.updatePointsModifyStatus(requestBean);
    }

    /**
     * 审核后更新用户积分表
     *
     * @param requestBean
     * @return
     */
    @Override
    public boolean updateDuibaPoints(DuibaPointsRequest requestBean) {
        return amUserClient.updateDuibaPoints(requestBean);
    }

    /**
     * 插入兑吧交易明细表
     *
     * @param requestBean
     * @return
     */
    @Override
    public boolean insertDuibaPoints(DuibaPointsRequest requestBean) {
        Integer userId = requestBean.getUserIdList().get(0);
        UserVO user = this.searchUserByUserId(userId);
        if (null == user) {
            logger.error("插入积分明细表获取用户信息失败，userId：" + userId);
            return false;
        }

        UserInfoVO userInfo = this.findUsersInfoById(userId);
        if (null == userInfo) {
            logger.error("插入积分明细表获取用户详细信息失败，userId：" + userId);
            return false;
        }

        DuibaPointsVO duibaPoints = new DuibaPointsVO();
        duibaPoints.setUserId(userId);
        duibaPoints.setUserName(user.getUsername());
        duibaPoints.setTrueName(userInfo.getTruename());
        duibaPoints.setPoints(requestBean.getModifyPoints());
        duibaPoints.setTotal(user.getPointsCurrent());
        // 调增
        if (0 == requestBean.getModifyType()) {
            duibaPoints.setBusinessName(3);
            duibaPoints.setType(0);
        } else {
            duibaPoints.setBusinessName(4);
            duibaPoints.setType(1);
        }
        duibaPoints.setHyOrderId(requestBean.getOrderId());
        duibaPoints.setCreateBy(requestBean.getModifyId());
        duibaPoints.setCreateTime(new Date());
        duibaPoints.setUpdateBy(requestBean.getModifyId());
        duibaPoints.setUpdateTime(new Date());
        return amMarketClient.insertDuibaPoints(duibaPoints);
    }

    /**
     * 根据订单号获取订单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public DuibaPointsModifyVO selectDuibaPointsModifyByOrdid(String orderId) {
        return amMarketClient.selectDuibaPointsModifyByOrdid(orderId);
    }
}
