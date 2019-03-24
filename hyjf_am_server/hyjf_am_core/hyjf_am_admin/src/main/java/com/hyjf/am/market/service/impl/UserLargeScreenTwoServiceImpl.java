package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.customize.market.UserLargeScreenTwoCustomizeMapper;
import com.hyjf.am.market.service.UserLargeScreenTwoService;
import com.hyjf.am.vo.api.OperMonthPerformanceDataVO;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLargeScreenTwoServiceImpl implements UserLargeScreenTwoService {

    @Autowired
    private UserLargeScreenTwoCustomizeMapper userLargeScreenTwoCustomizeMapper;

    /**
     * 得到运营部用户月初站岗资金
     * @return
     */
    @Override
    public List<OperMonthPerformanceDataVO> getOperMonthStartBalance() {
        return userLargeScreenTwoCustomizeMapper.getOperMonthStartBalance(GetDate.getFirstDayOfMonthDate());
    }

    /**
     * 得到运营部用户当前站岗资金
     * @return
     */
    @Override
    public List<OperMonthPerformanceDataVO> getOperMonthEndBalance() {
        return userLargeScreenTwoCustomizeMapper.getOperMonthEndBalance(GetDate.getNowTime());
    }
}
