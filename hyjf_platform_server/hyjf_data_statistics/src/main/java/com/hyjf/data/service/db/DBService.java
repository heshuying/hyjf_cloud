package com.hyjf.data.service.db;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/28
 * @Description: 从数据库获取数据
 */
public interface DBService {

    /**
     * 更新每月用户受益（每日更新一次）
     */
    void getUserInterestMonth();

    /**
     * 更新最近6个月的性别分布
     */
    void getSixMonthSexDistributed();
}
