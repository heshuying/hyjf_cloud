package com.hyjf.am.user.service.registrantchange;

import java.util.Date;

/**
 * 注册人变化统计
 * @Author : huanghui
 */
public interface RegistrantChangeStatisticsService {

    /**
     * 统计注册人数
     * @param startTime
     * @param endTime
     * @return
     */
    Integer queryRegistrantChangeStatisticsCount(Date startTime, Date endTime);
}
