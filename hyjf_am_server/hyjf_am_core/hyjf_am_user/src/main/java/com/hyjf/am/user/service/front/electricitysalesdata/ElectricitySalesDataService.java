/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.electricitysalesdata;

import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushList;
import com.hyjf.am.user.service.BaseService;

import java.util.List;

/**
 * 电销推送数据生成Service
 *
 * @author liuyang
 * @version ElectricitySalesDataService, v0.1 2019/5/28 17:13
 */
public interface ElectricitySalesDataService extends BaseService {
    /**
     * 电销推送数据生成
     */
    void generateElectricitySalesData(  List<ElectricitySalesDataPushList> result);
}
