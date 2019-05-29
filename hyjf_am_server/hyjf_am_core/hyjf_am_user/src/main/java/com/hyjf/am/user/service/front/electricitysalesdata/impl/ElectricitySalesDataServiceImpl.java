/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.electricitysalesdata.impl;

import com.hyjf.am.user.service.front.electricitysalesdata.ElectricitySalesDataService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        // TODO
    }
}
