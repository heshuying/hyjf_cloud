/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.finance;

import com.hyjf.am.trade.dao.mapper.auto.AccountWebListMapper;
import com.hyjf.am.trade.dao.mapper.auto.SubCommissionListConfigMapper;
import com.hyjf.am.trade.dao.mapper.auto.SubCommissionMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.finance.SubCommissionService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.SubCommissionVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private AccountWebListMapper accountWebListMapper;

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
    public Integer updateSubCommission(SubCommissionVO subCommissionVO) {
        SubCommission subCommission = CommonUtils.convertBean(subCommissionVO,SubCommission.class);
        return subCommissionMapper.updateByPrimaryKeySelective(subCommission);
    }

    /**
     * 根据订单号查询是否存在重复的AccountWebList数据
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    @Override
    public Integer accountWebListByOrderId(String orderId) {
        AccountWebListExample example = new AccountWebListExample();
        AccountWebListExample.Criteria cra = example.createCriteria();
        cra.andOrdidEqualTo(orderId);
        cra.andTradeEqualTo("fee_share_out");
        return this.accountWebListMapper.countByExample(example);
    }
}
