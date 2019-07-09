/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.model.auto.WithdrawTimeConfig;
import com.hyjf.am.config.dao.model.auto.WithdrawTimeConfigExample;
import com.hyjf.am.config.service.WithdrawTimeConfigService;
import com.hyjf.common.util.GetDate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 提现时间配置Service实现类
 *
 * @author liuyang
 * @version WithdrawTimeConfigServiceImpl, v0.1 2019/4/19 9:49
 */
@Service
public class WithdrawTimeConfigServiceImpl extends BaseServiceImpl implements WithdrawTimeConfigService {
    /**
     * 判断某一天是否是工作日
     *
     * @return
     */
    @Override
    public boolean isWorkdateOnSomeDay() {
        Date somedate = GetDate.getDate();
        WithdrawTimeConfigExample example = new WithdrawTimeConfigExample();
        WithdrawTimeConfigExample.Criteria cra = example.createCriteria();
        cra.andStartDateLessThanOrEqualTo(somedate);
        cra.andEndDateGreaterThanOrEqualTo(somedate);
        cra.andDelFlagEqualTo(0);
        // 假日类型 1补休 2假期
        cra.andHolidayTypeEqualTo(2);
        // 查询接
        List<WithdrawTimeConfig> list = this.withdrawTimeConfigMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            // 如果检索到,说明节假日
            return false;
        }
        // 判断是否是周末
        Calendar cal = Calendar.getInstance();
        cal.setTime(somedate);
        // 如果是周六或周日
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            // 再去查询是否是补休
            // 假日类型 1补休 2假期
            cra.andHolidayTypeEqualTo(1);
            List<WithdrawTimeConfig> resultList = this.withdrawTimeConfigMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(resultList)) {
                // 如果周六周日是补休,则为工作日,返回true
                return true;
            }
            // 如果未查询到是补休,则周六周日为节假日,返回false
            return false;
        }
        // 正常工作日,返回true
        return true;
    }
}
