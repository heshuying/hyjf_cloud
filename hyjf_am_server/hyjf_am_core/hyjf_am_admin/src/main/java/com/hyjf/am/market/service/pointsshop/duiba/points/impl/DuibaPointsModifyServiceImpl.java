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
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param duibaPointsModifyVO
     * @return
     */
    @Override
    public boolean insertPointsModifyList(DuibaPointsModifyVO duibaPointsModifyVO) {
        DuibaPointsModify duibaPointsModify = CommonUtils.convertBean(duibaPointsModifyVO, DuibaPointsModify.class);
        return duibaPointsModifyMapper.insertSelective(duibaPointsModify) > 0 ? true : false;
    }

    /**
     * 更新兑吧积分调整审批状态
     *
     * @param request
     * @return
     */
    @Override
    public boolean updatePointsModifyStatus(DuibaPointsRequest request) {
        DuibaPointsModifyExample example = new DuibaPointsModifyExample();
        example.createCriteria().andModifyOrderIdEqualTo(request.getOrderId());
        DuibaPointsModify duibaPointsModify = new DuibaPointsModify();
        duibaPointsModify.setStatus(request.getAuditStatus());
        // 审核不通过暂放到remark里
        if(2 == request.getAuditStatus()) {
            duibaPointsModify.setRemark(request.getRefuseReason());
        }
        return this.duibaPointsModifyMapper.updateByExampleSelective(duibaPointsModify, example) > 0 ? true : false;
    }

    /**
     * 根据订单号获取订单详情
     *
     * @param ordId
     * @return
     */
    @Override
    public DuibaPointsModify selectDuibaPointsModifyByOrdid(String ordId) {
        DuibaPointsModifyExample example = new DuibaPointsModifyExample();
        example.createCriteria().andModifyOrderIdEqualTo(ordId);
        List<DuibaPointsModify> duibaPointsModify = this.duibaPointsModifyMapper.selectByExample(example);
        if (null != duibaPointsModify && duibaPointsModify.size() > 0) {
            return duibaPointsModify.get(0);
        }
        return null;
    }
}
