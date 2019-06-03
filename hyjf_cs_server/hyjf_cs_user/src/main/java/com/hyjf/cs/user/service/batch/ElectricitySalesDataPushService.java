/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch;

import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.cs.user.service.BaseUserService;

import java.util.List;

/**
 * 电销数据推送Service
 *
 * @author liuyang
 * @version ElectricitySalesDataPushService, v0.1 2019/6/3 13:57
 */
public interface ElectricitySalesDataPushService extends BaseUserService {
    /**
     * 获取需要推送的电销数据
     *
     * @return
     */
    List<ElectricitySalesDataPushListVO> selectElectricitySalesDataPushDataList();
}
