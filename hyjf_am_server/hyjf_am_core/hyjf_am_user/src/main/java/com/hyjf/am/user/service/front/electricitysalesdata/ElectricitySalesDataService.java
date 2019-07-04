/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.electricitysalesdata;

import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushList;
import com.hyjf.am.user.service.BaseService;

import javax.validation.Valid;
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
    Integer generateElectricitySalesData(List<ElectricitySalesDataPushList> result);


    /**
     * 获取需要推送的电销数据
     *
     * @return
     */
    List<ElectricitySalesDataPushList> selectElectricitySalesDataPushDataList();


    /**
     * 电销数据上送后,更新电销数据
     *
     * @param result
     * @return
     */
    Integer updateElectricitySalesDataPushList(List<ElectricitySalesDataPushList> result);

    /**
     * 根据用户ID查询电销数据是否存在
     *
     * @param userId
     * @return
     */
    ElectricitySalesDataPushList selectElectricitySalesDataPushListByUserId(Integer userId);
}
