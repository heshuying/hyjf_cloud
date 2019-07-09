/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.batch.impl;

import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.dao.model.auto.HjhPlanExample;
import com.hyjf.am.trade.service.front.batch.WbsHjhPlanInfoService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * WBS系统智投信息推送Service实现类
 *
 * @author liuyang
 * @version WbsHjhPlanInfoServiceImpl, v0.1 2019/4/16 9:37
 */
@Service
public class WbsHjhPlanInfoServiceImpl extends BaseServiceImpl implements WbsHjhPlanInfoService {
    /**
     * 获取开放额度 > 0 的智投服务
     *
     * @return
     */
    @Override
    public List<HjhPlan> selectWbsSendHjhPlanList() {
        HjhPlanExample example = new HjhPlanExample();
        HjhPlanExample.Criteria cra = example.createCriteria();
        return this.hjhPlanMapper.selectByExample(example);
    }
}
