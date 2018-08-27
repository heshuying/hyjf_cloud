package com.hyjf.cs.trade.controller.api.wihdraw;

import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.wirhdraw.BankWithdrawService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author pangchengchao
 * @version BankWithdrawController, v0.1 2018/6/12 18:32
 */
@Api(tags = "api端-用户提现接口")
@Controller
@RequestMapping("/api/withdraw")
public class ApiBankWithdrawController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(ApiBankWithdrawController.class);
    @Autowired
    private BankWithdrawService bankWithdrawService;

}
