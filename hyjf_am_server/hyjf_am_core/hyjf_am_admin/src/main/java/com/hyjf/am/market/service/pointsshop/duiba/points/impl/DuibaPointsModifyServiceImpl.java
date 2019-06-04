/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.pointsshop.duiba.points.impl;

import com.hyjf.am.market.dao.mapper.auto.DuibaPointsModifyMapper;
import com.hyjf.am.market.dao.mapper.customize.market.DuibaPointsModifyCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.DuibaPointsModify;
import com.hyjf.am.market.dao.model.auto.DuibaPointsModifyExample;
import com.hyjf.am.market.service.pointsshop.duiba.points.DuibaPointsModifyService;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;
import com.hyjf.common.util.GetOrderIdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsModifyServiceImpl, v0.1 2019/5/29 9:50
 */
@Service
public class DuibaPointsModifyServiceImpl implements DuibaPointsModifyService {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DuibaPointsModifyCustomizeMapper duibaPointsModifyCustomizeMapper;

    @Autowired
    DuibaPointsModifyMapper duibaPointsModifyMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

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

    /**
     * 插入积分审批表
     *
     * @param request
     * @return
     */
    @Override
    public boolean insertPointsModifyList(DuibaPointsRequest request) throws Exception{
        List<Integer> userIdList = request.getUserIdList();
        for(Integer userId: userIdList) {
            User user = userMapper.selectByPrimaryKey(userId);
            if(null == user) {
                logger.error("获取用户信息失败，userId：" + userId);
                continue;
            }

            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
            if(null == userInfo) {
                logger.error("获取用户详细信息失败，userId：" + userId);
                continue;
            }

            // 计算用户积分调整后剩余
            Integer remainPoints = 0;
            if(0 == request.getModifyType()) {
                remainPoints = request.getModifyPoints() + user.getPointsCurrent();
            } else {
                if(user.getPointsCurrent() < request.getModifyPoints()) {
                    logger.error("数据插入过程中发现用户积分不足，userId：" + userId + ",调减积分数：" + request.getModifyPoints() + ",当前用户积分数：" + user.getPointsCurrent());
                    throw new Exception("数据插入过程中发现用户积分不足！");
                }
                remainPoints = user.getPointsCurrent() - request.getModifyPoints();
            }

            // 生成一笔订单号
            String modifyOrderId = GetOrderIdUtils.getOrderId2(userId);

            DuibaPointsModify duibaPointsModify = new DuibaPointsModify();
            duibaPointsModify.setUserId(userId);
            duibaPointsModify.setUserName(user.getUsername());
            duibaPointsModify.setTrueName(userInfo.getTruename());
            duibaPointsModify.setModifyOrderId(modifyOrderId);
            duibaPointsModify.setPoints(request.getModifyPoints());
            duibaPointsModify.setTotal(remainPoints);
            duibaPointsModify.setPointsType(request.getModifyType());
            duibaPointsModify.setModifyName(request.getModifyName());
            duibaPointsModify.setModifyReason(request.getReason());
            // 当前审批节点、默认0
            duibaPointsModify.setFlowOrder(0);
            // 审批状态：0待审批
            duibaPointsModify.setStatus(0);
            duibaPointsModify.setCreateBy(request.getModifyId());
            duibaPointsModify.setCreateTime(new Date());
            duibaPointsModifyMapper.insertSelective(duibaPointsModify);
        }

        return true;
    }
}
