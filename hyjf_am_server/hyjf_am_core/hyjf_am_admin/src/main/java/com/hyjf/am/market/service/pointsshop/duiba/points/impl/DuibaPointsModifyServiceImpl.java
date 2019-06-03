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
    public boolean insertPointsModifyList(DuibaPointsRequest request) {
        List<Integer> userIdList = request.getUserIdList();
        for(Integer userId: userIdList) {
            // 生成一笔订单号
            String modifyOrderId = "";

            User user = userMapper.selectByPrimaryKey(userId);
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
            DuibaPointsModify duibaPointsModify = new DuibaPointsModify();
            duibaPointsModify.setUserId(userId);
            duibaPointsModify.setUserName(user.getUsername());
            duibaPointsModify.setTrueName(userInfo.getTruename());
            duibaPointsModify.setModifyOrderId(modifyOrderId);
//            duibaPointsModify.setPoints(request.getModifyPoints());
            duibaPointsModify.setModifyName(request.getModifyName());
            duibaPointsModify.setPointsType(request.getModifyType());

            duibaPointsModifyMapper.insertSelective(duibaPointsModify);




        }

        return false;
    }
}
