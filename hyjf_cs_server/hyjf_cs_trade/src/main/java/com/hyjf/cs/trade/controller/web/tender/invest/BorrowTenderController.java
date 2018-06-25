/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.tender.invest;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.BorrowTenderService;
import com.hyjf.cs.trade.service.HjhTenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Description web端加入计划
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:32
 */
@Api(value = "Web端散标投资")
@RestController
@RequestMapping("/web/borrow")
public class BorrowTenderController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(BorrowTenderController.class);

    @Autowired
    private BorrowTenderService borrowTenderService;

    @ApiOperation(value = "web端散标投资", notes = "web端散标投资")
    @PostMapping(value = "/tender", produces = "application/json; charset=utf-8")
    public Object borrowTender(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        logger.info("web端请求投资接口");
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setToken(token);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));
        borrowTenderService.borrowTender(tender);
        return null;
    }

}
