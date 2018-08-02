package com.hyjf.am.trade.controller.admin.productcenter.plancenter.plancapital;

import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import com.hyjf.am.trade.service.admin.HjhPlanCapitalService;
import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalCustomizeVO;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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

    //复投原始标的列表(通过planNid)
    @RequestMapping(value = "/hjhPlanCapitalReinvestInfo", method = RequestMethod.POST)
    public HjhPlanCapitalResponse hjhPlanCapitalReinvestInfo(HjhReInvestDetailRequest request){
//        HjhPlanCapitalResponse response = new HjhPlanCapitalResponse();
//
//        List<HjhPlanCapitalCustomizeVO> responseList = hjhPlanCapitalService.getReinvestInfo(request);
//        response.setResultList(responseList);
//        return response;
        return null;
    }

    //复投承接债权(通过planNid)

    //转让详情列表(planNid,dateFromSrch,dateToSrch)

}
