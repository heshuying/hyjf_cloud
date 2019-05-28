/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch;

import com.hyjf.cs.user.service.BaseUserService;

/**
 * 电销数据生成Service
 *
 * @author liuyang
 * @version ElectricitySalesDataService, v0.1 2019/5/28 15:31
 */
public interface ElectricitySalesDataService extends BaseUserService {
    /**
     * 生成电销数据
     */
    void generateElectricitySalesData();
}
