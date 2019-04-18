package com.hyjf.am.user.service.admin.screendata.impl;

import com.hyjf.am.user.dao.mapper.customize.ScreenDataJobCustomizeMapper;
import com.hyjf.am.user.service.admin.screendata.ScreenDataJobService;
import com.hyjf.am.vo.trade.RepaymentPlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String dateString = getNowDateOfDay();
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("limitStart",limitStart);
        map.put("limitEnd",limitEnd);
        map.put("taskTime",dateString);
        return screenDataJobCustomizeMapper.findRepayUser(map);
    }
    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyyMM
     */
    private  String getNowDateOfDay() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    @Override
    public Integer countRepayUser(Integer startTime, Integer endTime) {
        return screenDataJobCustomizeMapper.countRepayUser(startTime,endTime);
    }


}
