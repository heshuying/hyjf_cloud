/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.electricitysalesdata.impl;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.service.front.electricitysalesdata.ElectricitySalesDataService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 电销推送数据生成Service实现类
 *
 * @author liuyang
 * @version ElectricitySalesDataServiceImpl, v0.1 2019/5/28 17:14
 */
@Service
public class ElectricitySalesDataServiceImpl extends BaseServiceImpl implements ElectricitySalesDataService {

    private static final Logger logger = LoggerFactory.getLogger(ElectricitySalesDataServiceImpl.class);

    /**
     * 电销数数据生成
     */
    @Override
    public void generateElectricitySalesData() {
        // 查询前一天注册的投资人
        List<User> userList = this.selectBeforeDayRegistUserList();

        if (userList == null || userList.size() == 0) {
            logger.info("前一天注册用户数为0,不予处理");
            return;
        }
    }

    /**
     * 检索前一天注册的用户
     *
     * @return
     */
    private List<User> selectBeforeDayRegistUserList() {
        Date startDay = GetDate.getTodayBeforeOrAfter(-1);
        String beforeDayStart = GetDate.getDayStart(startDay);
        String beforeDayEnd = GetDate.getDayEnd(startDay);
        UserExample example = new UserExample();
        UserExample.Criteria cra = example.createCriteria();
        cra.andRegTimeGreaterThanOrEqualTo(GetDate.str2Date(beforeDayStart, GetDate.datetimeFormat));
        cra.andRegTimeLessThanOrEqualTo(GetDate.str2Date(beforeDayEnd, GetDate.datetimeFormat));
        List<User> userList = this.userMapper.selectByExample(example);
        return userList;
    }
}
