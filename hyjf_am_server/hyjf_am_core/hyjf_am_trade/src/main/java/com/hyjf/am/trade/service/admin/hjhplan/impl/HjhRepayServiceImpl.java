package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.trade.dao.mapper.auto.HjhRepayMapper;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.trade.dao.model.auto.HjhRepayExample;
import com.hyjf.am.trade.service.admin.hjhplan.HjhRepayService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 汇计划订单退出实现
 * @Author : huanghui
 */
@Service
public class HjhRepayServiceImpl extends BaseServiceImpl implements HjhRepayService {

    @Autowired
    private HjhRepayMapper hjhRepayMapper;

    /**
     * 订单退出超过两天邮件预警list
     * @author zhangyk
     * @date 2018/8/15 15:48
     */
    @Override
    public List<HjhRepay> getPlanExitCheck() {
        HjhRepayExample hjhRepayExample = new HjhRepayExample();
        //获取当前的时间减去两天的时间就是前天的时间
        int nowTime1 = GetDate.getNowTime10()-172800;
        HjhRepayExample.Criteria createCriteria = hjhRepayExample.createCriteria();
        createCriteria.andOrderStatusEqualTo(5);
        createCriteria.andRepayShouldTimeLessThanOrEqualTo(nowTime1);
        List<HjhRepay> repayList = hjhRepayMapper.selectByExample(hjhRepayExample);
        return repayList;
    }
}
