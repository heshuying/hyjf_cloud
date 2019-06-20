/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.hjhplan;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.TenderUpdateUtmHistoryResponse;
import com.hyjf.am.response.trade.HjhPlanAccedeCustomizeResponse;
import com.hyjf.am.resquest.trade.UpdateTenderUtmExtRequest;
import com.hyjf.am.trade.dao.model.auto.TenderUtmChangeLog;
import com.hyjf.am.trade.service.admin.borrow.TenderUtmChangeLogService;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhPlanChangeUtmService;
import com.hyjf.am.vo.trade.borrow.TenderUpdateUtmHistoryVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAccedeCustomizeVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cui
 * @version AdminHjhPlanChangeUtmController, v0.1 2019/6/18 15:35
 */
@ApiOperation(value = "计划订单修改渠道")
@RestController
@RequestMapping(value = "/am-trade/planutm")
public class AdminHjhPlanChangeUtmController {

    @Autowired
    private TenderUtmChangeLogService tenderUtmChangeLogService;

    @Autowired
    private AdminHjhPlanChangeUtmService adminHjhPlanChangeUtmService;

    @GetMapping(value = "/plan_tender_info/{planOrderId}")
    public HjhPlanAccedeCustomizeResponse getPlanTenderInfo(@PathVariable(value = "planOrderId") String planOrderId) {

        HjhPlanAccedeCustomizeResponse response=new HjhPlanAccedeCustomizeResponse();

        HjhPlanAccedeCustomizeVO vo=adminHjhPlanChangeUtmService.getHjhPlanTender(planOrderId);

        response.setResult(vo);

        return response;

    }

    @PostMapping(value = "updateTenderUtm")
    public IntegerResponse updateTenderUtm(@RequestBody UpdateTenderUtmExtRequest request){

        TenderUtmChangeLog log=new TenderUtmChangeLog();
        BeanUtils.copyProperties(request,log);
        int rt=adminHjhPlanChangeUtmService.updateTenderUtm(log);
        return new IntegerResponse(rt);

    }

    @RequestMapping("/update_tender_utm_history/{accede_order_id}")
    public TenderUpdateUtmHistoryResponse getTenderUtmChangeLog(@PathVariable(name = "accede_order_id") String planOrderId){
        TenderUpdateUtmHistoryResponse response=new TenderUpdateUtmHistoryResponse();
        List<TenderUpdateUtmHistoryVO> lstVO=tenderUtmChangeLogService.getPlanTenderChangeLog(planOrderId);
        response.setResultList(lstVO);
        return response;
    }

}
