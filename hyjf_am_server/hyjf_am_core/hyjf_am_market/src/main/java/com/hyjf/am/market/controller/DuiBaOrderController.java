/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller;

import com.hyjf.am.market.service.DuiBaOrderService;
import com.hyjf.am.response.market.DuiBaPointsDetailResponse;
import com.hyjf.am.resquest.market.DuiBaPointsDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version DuiBaOrderController, v0.1 2019/6/11 11:14
 */
@RestController
@RequestMapping("/am-market/duiba")
public class DuiBaOrderController {
    @Autowired
    DuiBaOrderService duiBaOrderService;

    /**
     * 获取用户积分明细
     * @param request
     * @return
     */
    @PostMapping("/getpointsdetail")
    public DuiBaPointsDetailResponse getPointsDetail(@RequestBody DuiBaPointsDetailRequest request){
        return duiBaOrderService.getPointsDetail(request);
    }
}
