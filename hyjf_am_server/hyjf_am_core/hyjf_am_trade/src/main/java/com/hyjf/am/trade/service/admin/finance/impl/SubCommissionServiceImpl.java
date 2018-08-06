/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.resquest.admin.SubCommissionRequest;
import com.hyjf.am.trade.dao.mapper.auto.SubCommissionListConfigMapper;
import com.hyjf.am.trade.dao.mapper.auto.SubCommissionMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.finance.SubCommissionService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.SubCommissionVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: SubCommissionServiceImpl, v0.1 2018/7/10 10:15
 */
@Service
public class SubCommissionServiceImpl extends BaseServiceImpl implements SubCommissionService {

    @Autowired
    private SubCommissionListConfigMapper subCommissionListConfigMapper;

    @Autowired
    private SubCommissionMapper subCommissionMapper;

    /**
     * 查询发起账户分佣所需的detail信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<SubCommissionListConfig> searchSubCommissionListConfig() {
        SubCommissionListConfigExample example = new SubCommissionListConfigExample();
        SubCommissionListConfigExample.Criteria criteria = example.createCriteria();
        return subCommissionListConfigMapper.selectByExample(example);
    }

    /**
     * 插入发起账户分佣数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSubCommission(SubCommissionVO subCommissionVO) {
        SubCommission subCommission = CommonUtils.convertBean(subCommissionVO,SubCommission.class);
        return subCommissionMapper.insertSelective(subCommission)>0;
    }

    /**
     * 根据订单号查询分佣信息
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    @Override
    public List<SubCommission> searchSubCommissionByOrderId(String orderId) {
        SubCommissionExample example = new SubCommissionExample();
        SubCommissionExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<SubCommission> subCommissionList = subCommissionMapper.selectByExample(example);

        return subCommissionList;
    }

    /**
     * 更新分佣数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSubCommission(SubCommissionVO subCommissionVO) {
        SubCommission subCommission = CommonUtils.convertBean(subCommissionVO,SubCommission.class);
        return subCommissionMapper.updateByPrimaryKeySelective(subCommission);
    }

    /**
     * 根据筛选条件查询分佣数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getSubCommissionCount(SubCommissionRequest request) {
        SubCommissionExample example = convertExample(request);
        Integer count = subCommissionMapper.countByExample(example);
        return count;
    }

    /**
     * 根据筛选条件查询分佣数据list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<SubCommission> searchSubCommissionList(SubCommissionRequest request) {
        SubCommissionExample example = convertExample(request);
        List<SubCommission> subCommissionList = subCommissionMapper.selectByExample(example);
        return subCommissionList;
    }

    private SubCommissionExample convertExample(SubCommissionRequest request){
        SubCommissionExample example = new SubCommissionExample();
        SubCommissionExample.Criteria criteria = example.createCriteria();
        // 订单号
        if(StringUtils.isNotEmpty(request.getOrderIdSrch())){
            criteria.andOrderIdLike("%"+request.getOrderIdSrch()+"%");
        }
        // 转账状态
        if(StringUtils.isNotEmpty(request.getReceiveUserNameSrch())){
            criteria.andReceiveUserNameLike("%"+request.getReceiveUserNameSrch()+"%");
        }
        // 转账状态
        if(StringUtils.isNotEmpty(request.getTradeStatusSrch())){
            criteria.andTradeStatusEqualTo(Integer.parseInt(request.getTradeStatusSrch()));
        }
        // 添加时间开始
        if (StringUtils.isNotEmpty(request.getTimeStartSrch())) {
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getTimeStartSrch())));
        }
        // 添加时间结束
        if (StringUtils.isNotEmpty(request.getTimeEndSrch())) {
            criteria.andCreateTimeLessThan(GetDate.stringToDate(GetDate.getDayStart(request.getTimeEndSrch())));
        }
        example.setOrderByClause("create_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }

        return example;
    }
}
