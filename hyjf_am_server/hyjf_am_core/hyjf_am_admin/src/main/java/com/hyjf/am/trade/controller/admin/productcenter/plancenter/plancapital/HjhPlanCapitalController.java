package com.hyjf.am.trade.controller.admin.productcenter.plancenter.plancapital;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.admin.HjhReInvestDetailResponse;
import com.hyjf.am.trade.service.admin.hjhplan.HjhPlanCapitalService;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;

import io.swagger.annotations.Api;


/**
 * 汇计划-订单退出
 * @Author : huanghui
 */
@Api(value = "汇计划-资金计划")
@RestController
@RequestMapping("/am-trade/hjhPlanCapital")
public class HjhPlanCapitalController {

    @Autowired
    private HjhPlanCapitalService hjhPlanCapitalService;

    private static Logger logger = LoggerFactory.getLogger(HjhPlanCapitalController.class);


    @GetMapping(value = "/hjhPlanCapitalReinvestCount/{data}/{planNid}")
    public Integer hjhPlanCapitalReinvestCount(@PathVariable String data, @PathVariable String planNid){
        return this.hjhPlanCapitalService.queryReInvestDetailCount(data, planNid);
    }

    /**
     * 资金计划 - 复投原始标的
     * @param data
     * @param planNid
     * @return
     */
    @GetMapping(value = "/hjhPlanCapitalReinvestInfo/{data}/{planNid}")
    public HjhReInvestDetailResponse hjhPlanCapitalReinvestInfo(@PathVariable String data, @PathVariable String planNid){
        HjhReInvestDetailResponse response = new HjhReInvestDetailResponse();

        List<HjhReInvestDetailVO> hjhReInvestDetailCustomizeList = this.hjhPlanCapitalService.getReinvestInfo(data, planNid);
        response.setResultList(hjhReInvestDetailCustomizeList);
        return response;
    }

}
