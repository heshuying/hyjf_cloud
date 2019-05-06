package com.hyjf.am.trade.service.impl;

import com.hyjf.am.market.dao.mapper.customize.market.UserLargeScreenTwoCustomizeMapper;
import com.hyjf.am.trade.service.UserLargeScreenTwoCustomizeTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:dangzw
 * @Date:2019/5/6
 * @Description:
 */
@Service
public class UserLargeScreenTwoCustomizeTServiceImpl implements UserLargeScreenTwoCustomizeTService {

    @Autowired
    private UserLargeScreenTwoCustomizeMapper userLargeScreenTwoCustomizeMapper;

    /**
     * 坐席每日待回款金额表-数据批量删除
     * @param param
     */
    @Override
    public void deleteRepaymentPlan(List<String> param) {
        userLargeScreenTwoCustomizeMapper.delRepaymentPlan(param);
    }
}
