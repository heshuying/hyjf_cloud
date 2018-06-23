/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.CreditTenderResponse;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.service.BankCreditTenderService;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jun 20180621
 */
@Api(value = "债转投资")
@RestController
@RequestMapping("/am-trade/creditTender")
public class CreditTenderController {

    private static Logger logger = LoggerFactory.getLogger(CreditTenderController.class);

    @Autowired
    BankCreditTenderService bankCreditTenderService;


    @GetMapping("/selectByAssignNidAndUserId/{assignNid}/{userId}")
    public CreditTenderResponse selectByAssignNidAndUserId(@PathVariable String assignNid,@PathVariable Integer userId) {
        CreditTenderResponse response = new CreditTenderResponse();
        CreditTender creditTender = bankCreditTenderService.selectByAssignNidAndUserId(assignNid,userId);
        if (creditTender != null) {
            response.setResult(CommonUtils.convertBean(creditTender,CreditTenderVO.class));
        }
        return response;
    }

    
    
    

    @PostMapping("/updateTenderCreditInfo")
    public boolean updateTenderCreditInfo(@RequestBody CreditTenderRequest request){
        boolean ret;
        try {
             ret = bankCreditTenderService.updateTenderCreditInfo(request);
           }catch (Exception e){
            logger.info("updateTenderCreditInfo..."+e);
            ret = false;
           }
            return ret;
    }
}
