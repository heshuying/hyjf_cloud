/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.electricitysalesdata.impl;

import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushList;
import com.hyjf.am.user.service.front.electricitysalesdata.ElectricitySalesDataService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 电销推送数据生成Service实现类
 *
 * @author liuyang
 * @version ElectricitySalesDataServiceImpl, v0.1 2019/5/28 17:14
 */
@Service
public class ElectricitySalesDataServiceImpl extends BaseServiceImpl implements ElectricitySalesDataService {

    /**
     * 电销数数据生成
     */
    @Override
    public void generateElectricitySalesData(List<ElectricitySalesDataPushList> result) {
        for (int i = 0; i < result.size(); i++) {
            this.electricitySalesDataPushListMapper.insertSelective(result.get(i));
        }
    }
}
