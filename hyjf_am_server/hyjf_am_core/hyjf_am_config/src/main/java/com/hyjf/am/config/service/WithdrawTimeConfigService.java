/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import java.util.Date;

/**
 * 提现时间配置
 *
 * @author liuyang
 * @version WithdrawTimeConfigService, v0.1 2019/4/19 9:48
 */
public interface WithdrawTimeConfigService extends BaseService {
    /**
     * 判断某一天是否是工作日
     *
     * @param somedate
     * @return
     */
    boolean isWorkdateOnSomeDay(Date somedate);
}
