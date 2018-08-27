package com.hyjf.am.user.service.registrantchange.impl;

import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.user.service.registrantchange.RegistrantChangeStatisticsService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 注册人数变化统计
 * @Author : huanghui
 */
@Service
public class RegistrantChangeStatisticsServiceImpl extends BaseServiceImpl implements RegistrantChangeStatisticsService {



    @Override
    public Integer queryRegistrantChangeStatisticsCount(Date startTime, Date endTime) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        criteria.andRegTimeBetween(startTime, endTime);
        return userMapper.countByExample(userExample);
    }
}
