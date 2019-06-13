/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.service.pointsshop.duiba.points.impl;

import com.hyjf.am.market.dao.mapper.auto.DuibaPointsMapper;
import com.hyjf.am.market.dao.mapper.customize.market.DuibaPointsListCustomizeMapper;
import com.hyjf.am.market.dao.model.auto.DuibaPoints;
import com.hyjf.am.market.dao.model.auto.DuibaPointsExample;
import com.hyjf.am.market.service.pointsshop.duiba.points.DuibaPointsListService;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.vo.admin.DuibaPointsVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param duibaPointsVO
     * @return
     */
    @Override
    public boolean insertDuibaPoints(DuibaPointsVO duibaPointsVO) {
        DuibaPoints duibaPoints = CommonUtils.convertBean(duibaPointsVO, DuibaPoints.class);
        return duibaPointsMapper.insertSelective(duibaPoints) > 0 ? true : false;
    }

    /**
     * 根据兑吧订单获取相关积分明细
     *
     * @param ordId
     * @return
     */
    @Override
    public DuibaPointsVO getDuibaPointsByOrdId(String ordId,Integer businessName) {
        DuibaPointsExample example = new DuibaPointsExample();
        example.createCriteria().andDuibaOrderIdEqualTo(ordId);
        example.createCriteria().andBusinessNameEqualTo(businessName);
        example.setOrderByClause("`create_time` DESC");
        List<DuibaPoints> duibaPoints = this.duibaPointsMapper.selectByExample(example);
        if (null != duibaPoints && duibaPoints.size() > 0) {
            return CommonUtils.convertBean(duibaPoints.get(0), DuibaPointsVO.class);
        }
        return null;
    }
}
