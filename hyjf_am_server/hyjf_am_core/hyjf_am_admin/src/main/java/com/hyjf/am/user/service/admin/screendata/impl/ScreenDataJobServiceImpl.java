package com.hyjf.am.user.service.admin.screendata.impl;

import com.hyjf.am.user.dao.mapper.customize.ScreenDataJobCustomizeMapper;
import com.hyjf.am.user.service.admin.screendata.ScreenDataJobService;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version ScreenDataJobServiceImpl, v0.1 2019/3/20 17:37
 */
@Service
public class ScreenDataJobServiceImpl implements ScreenDataJobService {
    @Autowired
    ScreenDataJobCustomizeMapper screenDataJobCustomizeMapper;
    @Override
    public List<RepaymentPlanVO> findRepayUser(Integer startTime, Integer endTime, Integer limitStart, Integer limitEnd) {
        return screenDataJobCustomizeMapper.findRepayUser(startTime,endTime,limitStart,limitEnd);
    }

    @Override
    public Integer countRepayUser(Integer startTime, Integer endTime) {
        return screenDataJobCustomizeMapper.countRepayUser(startTime,endTime);
    }


}
