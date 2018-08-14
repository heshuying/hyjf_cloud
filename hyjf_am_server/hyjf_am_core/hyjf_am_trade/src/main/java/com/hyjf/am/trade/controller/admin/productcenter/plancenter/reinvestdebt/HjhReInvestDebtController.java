package com.hyjf.am.trade.controller.admin.productcenter.plancenter.reinvestdebt;

import com.hyjf.am.response.admin.HjhReInvestDebtResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import com.hyjf.am.trade.service.admin.hjhplan.HjhReInvestDebtService;
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
 * 汇计划-资金计划-复投承接债权
 * @Author : huanghui
 */
@Api(value = "汇计划-资金计划")
@RestController
@RequestMapping("/am-trade/hjhReInvestDebt")
public class HjhReInvestDebtController {

    @Autowired
    private HjhReInvestDebtService hjhReInvestDebtService;

    private static Logger logger = LoggerFactory.getLogger(HjhReInvestDebtController.class);

    //复投债权列表(通过planNid)
    @RequestMapping(value = "/hjhReInvestDebtList", method = RequestMethod.POST)
    public HjhReInvestDebtResponse hjhReInvestDebtList(HjhReInvestDebtRequest request){
        HjhReInvestDebtResponse response = new HjhReInvestDebtResponse();

        List<HjhPlanCapitalCustomizeVO> responseList = this.hjhReInvestDebtService.getReinvestDebtList(request);
        response.setResultList(responseList);
        return response;
    }

}
