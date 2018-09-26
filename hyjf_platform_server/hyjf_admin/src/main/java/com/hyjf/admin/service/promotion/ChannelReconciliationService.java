/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.promotion;

import com.hyjf.am.response.admin.promotion.ChannelReconciliationResponse;
import com.hyjf.am.resquest.admin.ChannelReconciliationRequest;
import com.hyjf.am.vo.admin.UtmVO;

import java.util.List;

/**
 * @author fq
 * @version ChannelReconciliationService, v0.1 2018/9/21 9:56
 */
public interface ChannelReconciliationService {
    /**
     * 获取渠道
     * @param sourceType
     * @return
     */
    List<UtmVO> searchUtmList(int sourceType);

    /**
     * 散标列表查询
     * @param request
     * @return
     */
    ChannelReconciliationResponse searchAction(ChannelReconciliationRequest request);

    /**
     * 计划列表查询
     * @param request
     * @return
     */
    ChannelReconciliationResponse searchHJHAction(ChannelReconciliationRequest request);

    /**
     * app散标列表查询
     * @param request
     * @return
     */
    ChannelReconciliationResponse searchAppAction(ChannelReconciliationRequest request);

    /**
     * 计划列表查询
     * @param request
     * @return
     */
    ChannelReconciliationResponse searchAppHJHAction(ChannelReconciliationRequest request);
}
