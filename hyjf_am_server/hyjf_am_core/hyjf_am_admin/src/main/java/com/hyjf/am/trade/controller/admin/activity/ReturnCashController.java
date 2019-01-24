package com.hyjf.am.trade.controller.admin.activity;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.market.dao.model.auto.InviterReturnDetail;
import com.hyjf.am.market.dao.model.auto.NmUser;
import com.hyjf.am.market.dao.model.auto.PerformanceReturnDetail;
import com.hyjf.am.market.service.NmUserService;
import com.hyjf.am.resquest.admin.ReturnCashRequest;
import com.hyjf.am.resquest.market.InviterReturnCashCustomize;
import com.hyjf.am.trade.service.admin.ReturnCashActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tyy
 * @version ReturnCashController, v0.1 2018/12/26 15:38
 */

@RestController
@RequestMapping("/am-market/returncash")
public class ReturnCashController {
    private static final Logger logger = LoggerFactory.getLogger(ReturnCashController.class);
    @Autowired
    private ReturnCashActivityService returnCashActivityService;

    @Autowired
    private NmUserService nmUserService;

    @GetMapping("/updatejointime/{borrowNid}/{nowTime}")
    public void getInfoById(@PathVariable String borrowNid,@PathVariable Integer nowTime) {
        List<InviterReturnDetail> inviterReturnDetailList = returnCashActivityService.selectInviterReturnDetailList(borrowNid);
        List<PerformanceReturnDetail> performanceReturnDetailList = returnCashActivityService.selectPerformanceReturnDetailList(borrowNid);
        if(!CollectionUtils.isEmpty(inviterReturnDetailList)&&!CollectionUtils.isEmpty(performanceReturnDetailList)) {
            returnCashActivityService.updateJoinTime(nowTime, inviterReturnDetailList, performanceReturnDetailList);
        }
    }
    @PostMapping("/saveReturnCash")
    public void saveReturnCash(@RequestBody ReturnCashRequest request) {
        logger.info("ReturnCashRequest==="+JSONObject.toJSONString(request));
        //查询用户提出来
        InviterReturnCashCustomize inviterReturnCashCustomize = returnCashActivityService.selectReturnCashList(request.getUserId());
        logger.info("inviterReturnCashCustomize==="+JSONObject.toJSONString(inviterReturnCashCustomize));

        List<NmUser> nmUserList = nmUserService.selectNmUserList(null);
        logger.info("nmUserList==="+JSONObject.toJSONString(nmUserList));

        returnCashActivityService.selectReturnCash(request.getUserId(),request.getOrderId(),
                request.getProductType(),request.getInvestMoney(),inviterReturnCashCustomize,nmUserList);
    }
}
