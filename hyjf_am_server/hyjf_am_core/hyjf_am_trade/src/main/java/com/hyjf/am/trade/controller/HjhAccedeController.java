/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.trade.dao.model.auto.HjhAccede;
import com.hyjf.am.trade.service.HjhAccedeService;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhAccedeController, v0.1 2018/6/25 10:09
 */
@Api(value = "汇计划加入明细表")
@RestController
@RequestMapping("/am-trade/hjhAccede")
public class HjhAccedeController {

    @Autowired
    HjhAccedeService hjhAccedeService;

    /**
     * 查询准备退出计划和准备进入锁定期的标的
     * @return
     */
    @RequestMapping
    public HjhAccedeResponse selectWaitQuitHjhList() {
        HjhAccedeResponse response = new HjhAccedeResponse();
        List<HjhAccede> hjhAccedeResponse = hjhAccedeService.selectWaitQuitHjhList();
        if (hjhAccedeResponse != null) {
            response.setResult(CommonUtils.convertBean(hjhAccedeResponse,HjhAccedeVO.class));
        }
        return response;
    }

}
