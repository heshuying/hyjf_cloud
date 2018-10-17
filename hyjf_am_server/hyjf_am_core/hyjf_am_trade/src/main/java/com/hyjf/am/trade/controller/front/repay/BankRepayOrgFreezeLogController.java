package com.hyjf.am.trade.controller.front.repay;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.BankRepayFreezeLogResponse;
import com.hyjf.am.resquest.trade.BankRepayOrgFreezeLogRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BankRepayOrgFreezeLog;
import com.hyjf.am.trade.service.front.repay.BankRepayOrgFreezeLogService;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wgx
 * @date 2018/10/13
 */
@RestController
@RequestMapping("/am-trade/repayOrgFreezeLog")
public class BankRepayOrgFreezeLogController extends BaseController {
    @Autowired
    BankRepayOrgFreezeLogService bankRepayOrgFreezeLogService;

    /**
     * 添加
     *
     * @param requestBean
     * @return
     */
    @RequestMapping("/add")
    public IntegerResponse addFreezeLog(@RequestBody BankRepayOrgFreezeLogRequest requestBean) {
        return new IntegerResponse(bankRepayOrgFreezeLogService.insertRepayOrgFreezeLog(requestBean));
    }

    /**
     * 根据orderId和borrowNid删除（逻辑删）
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/delete/{orderId}/{borrowNid}")
    public IntegerResponse deleteOrgFreezeLog(@PathVariable String orderId, @PathVariable String borrowNid) {
        if (StringUtils.isBlank(orderId)) {
            return new IntegerResponse(0);
        }
        return new IntegerResponse(bankRepayOrgFreezeLogService.deleteOrgFreezeTempLogs(orderId, borrowNid));
    }

    /**
     * 根据orderId删除（逻辑删）
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/delete/{orderId}")
    public IntegerResponse deleteOrgFreezeLog(@PathVariable String orderId) {
        return new IntegerResponse(bankRepayOrgFreezeLogService.deleteOrgFreezeTempLogs(orderId, null));
    }

    /**
     * 获取当前有效的冻结记录
     */
    @RequestMapping("/getValid/{orderId}")
    public BankRepayFreezeLogResponse getFreezeLogValid(@PathVariable String orderId) {
        BankRepayFreezeLogResponse response = new BankRepayFreezeLogResponse();
        List<BankRepayOrgFreezeLog> logList = bankRepayOrgFreezeLogService.getBankRepayOrgFreezeLogList(orderId, null);
        if (Validator.isNotNull(logList)) {
            response.setResultList(CommonUtils.convertBeanList(logList, BankRepayFreezeLogVO.class));
        }
        return response;
    }

    /**
     * 获取当前有效的冻结记录
     */
    @RequestMapping("/getValid/{orderId}/{borrowNid}")
    public BankRepayFreezeLogResponse getFreezeLogValid(@PathVariable String orderId, @PathVariable String borrowNid) {
        BankRepayFreezeLogResponse response = new BankRepayFreezeLogResponse();
        List<BankRepayOrgFreezeLog> logList = bankRepayOrgFreezeLogService.getBankRepayOrgFreezeLogList(orderId, borrowNid);
        if (Validator.isNotNull(logList)) {
            response.setResultList(CommonUtils.convertBeanList(logList, BankRepayFreezeLogVO.class));
        }
        return response;
    }
}
