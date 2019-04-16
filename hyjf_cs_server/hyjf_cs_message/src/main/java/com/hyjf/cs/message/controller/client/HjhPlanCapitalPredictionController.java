/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.plancapital.HjhPlanCapitalPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 资金计划-预计新增复投/债转额相关mongo操作
 *
 * @author PC-LIUSHOUYI
 * @version HjhPlanCapitalPredictionController, v0.1 2019/4/16 14:19
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/hjhPlanCapitalPrediction")
public class HjhPlanCapitalPredictionController extends BaseController {

    @Autowired
    private HjhPlanCapitalPredictionService hjhPlanCapitalPredictionService;

    /**
     * 汇计划 -- 资金计划 -- 预计相关字段 insert
     *
     * @param list
     * @return
     * @Author : liushouyi
     */
    @PostMapping(value = "/insertPlanCaptialPrediction")
    public BooleanResponse insertPlanCaptialPrediction(@RequestBody List<HjhPlanCapitalPredictionVO> list) {
        return new BooleanResponse(this.hjhPlanCapitalPredictionService.insertPlanCaptialPrediction(list));
    }
}
