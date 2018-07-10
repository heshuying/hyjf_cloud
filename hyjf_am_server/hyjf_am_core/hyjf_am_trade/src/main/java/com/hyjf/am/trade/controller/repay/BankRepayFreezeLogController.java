package com.hyjf.am.trade.controller.repay;

import com.hyjf.am.response.trade.BankRepayFreezeLogResponse;
import com.hyjf.am.resquest.trade.BankRepayFreezeLogRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BankRepayFreezeLog;
import com.hyjf.am.trade.service.repay.BankRepayFreezeLogService;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 还款冻结表操作
 * @author hesy
 * @version BankRepayFreezeLogController, v0.1 2018/7/9 16:13
 */
@RestController
@RequestMapping("/am-trade/repayfreezelog")
public class BankRepayFreezeLogController extends BaseController {
    @Autowired
    BankRepayFreezeLogService bankRepayFreezeLogService;

    /**
     * 添加
     * @param requestBean
     * @return
     */
    @RequestMapping("/add")
    public Integer addFreezeLog(@RequestBody BankRepayFreezeLogRequest requestBean){
        return  bankRepayFreezeLogService.insertRepayFreezeLog(requestBean);
    }

    /**
     * 删除
     * @param orderId
     * @return
     */
    @RequestMapping("/deleteby_orderid/{orderId}")
    public Integer deleteFreezeLogByOrderId(@PathVariable String orderId){
        if(StringUtils.isBlank(orderId)){
            return 0;
        }

        return bankRepayFreezeLogService.deleteFreezeLogsByOrderId(orderId);
    }

    /**
     * 获取当前有效的冻结记录
     * @auther: hesy
     * @date: 2018/7/10
     */
    @RequestMapping("/get_logvalid/{userId}/{borrowNid}")
    public BankRepayFreezeLogResponse getFreezeLogValid(@PathVariable Integer userId, @PathVariable String borrowNid){
        BankRepayFreezeLogResponse response = new BankRepayFreezeLogResponse();
        BankRepayFreezeLog log = bankRepayFreezeLogService.getFreezeLog(userId, borrowNid);
        if (Validator.isNotNull(log)){
            response.setResult(CommonUtils.convertBean(log,BankRepayFreezeLogVO.class));
        }
        return response;
    }
}
