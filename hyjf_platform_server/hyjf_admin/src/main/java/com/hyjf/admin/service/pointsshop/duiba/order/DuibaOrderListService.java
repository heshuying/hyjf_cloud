/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.pointsshop.duiba.order;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.admin.DuibaOrderResponse;
import com.hyjf.am.resquest.admin.DuibaOrderRequest;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;
import java.util.Map;

/**
 * @author WENXIN
 * @version DuibaOrderListService, v0.1 2019/5/29 9:48
 */
public interface DuibaOrderListService extends BaseService {

    /**
     * 初始化订单页面信息
     *
     * @param duibaOrderRequest
     * @return
     */
    DuibaOrderResponse findOrderList(DuibaOrderRequest duibaOrderRequest);

    /**
     * map转ParamNameVO
     *
     * @param map
     * @return
     */
    List<ParamNameVO> mapToParamNameVO(Map<String, String> map);

    /**
     * 同步处理中的订单信息（订单列表）
     *
     * @param orderId
     * @return
     */
    String synchronization(Integer orderId);
}
