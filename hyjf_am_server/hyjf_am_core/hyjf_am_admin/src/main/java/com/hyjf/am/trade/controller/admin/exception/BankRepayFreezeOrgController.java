package com.hyjf.am.trade.controller.admin.exception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.BankRepayOrgFreezeLogResponse;
import com.hyjf.am.response.user.BankRepayFreezeOrgResponse;
import com.hyjf.am.resquest.admin.RepayFreezeOrgRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BankRepayOrgFreezeLog;
import com.hyjf.am.trade.dao.model.customize.BankRepayFreezeOrgCustomize;
import com.hyjf.am.trade.service.admin.exception.BankRepayFreezeOrgService;
import com.hyjf.am.vo.admin.BankRepayFreezeOrgCustomizeVO;
import com.hyjf.am.vo.trade.repay.BankRepayOrgFreezeLogVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author hesy
 * @version BankRepayFreezeOrgController, v0.1 2018/10/19 10:19
 */
@RestController
@RequestMapping("/am-admin/exception/bankRepayFreezeOrg")
public class BankRepayFreezeOrgController extends BaseController {
    @Autowired
    BankRepayFreezeOrgService bankRepayFreezeOrgService;

    /**
     * 还款冻结异常列表数据
     */
    @PostMapping("/list_data")
    public BankRepayFreezeOrgResponse getFreezeOrgList(@RequestBody @Valid RepayFreezeOrgRequest requestBean) {
        logger.info("请求参数：" + JSONObject.toJSON(requestBean));
        BankRepayFreezeOrgResponse response = new BankRepayFreezeOrgResponse();
        List<BankRepayFreezeOrgCustomize> resultList = bankRepayFreezeOrgService.selectList(requestBean);
        if (!CollectionUtils.isEmpty(resultList)) {
            List<BankRepayFreezeOrgCustomizeVO> resultListVO = CommonUtils.convertBeanList(resultList, BankRepayFreezeOrgCustomizeVO.class);
            response.setResultList(resultListVO);
        }
        return response;
    }

    /**
     * 还款冻结异常列表总记录数
     */
    @PostMapping("/list_count")
    public IntegerResponse getFreezeOrgCount(@RequestBody @Valid RepayFreezeOrgRequest requestBean) {
        logger.info("请求参数：" + JSONObject.toJSON(requestBean));
        IntegerResponse response = new IntegerResponse();
        Integer count = bankRepayFreezeOrgService.selectCount(requestBean);
        response.setResultInt(count);
        return response;
    }

    /**
     * 根据id删除（逻辑删）
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteById/{id}")
    public IntegerResponse deleteOrgFreezeLog(@PathVariable Integer id) {
        return new IntegerResponse(bankRepayFreezeOrgService.deleteFreezeLogById(id));
    }

    /**
     * 根据orderId删除（逻辑删）
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/delete/{orderId}")
    public IntegerResponse deleteOrgFreezeLog(@PathVariable String orderId) {
        return new IntegerResponse(bankRepayFreezeOrgService.deleteOrgFreezeTempLogs(orderId, null));
    }

    /**
     * 根据orderId获取当前有效的冻结记录
     */
    @RequestMapping("/getValid/{orderId}")
    public BankRepayOrgFreezeLogResponse getFreezeLogValid(@PathVariable String orderId) {
        BankRepayOrgFreezeLogResponse response = new BankRepayOrgFreezeLogResponse();
        List<BankRepayOrgFreezeLog> logList = bankRepayFreezeOrgService.getBankRepayOrgFreezeLogList(orderId, null);
        if (Validator.isNotNull(logList)) {
            response.setResultList(CommonUtils.convertBeanList(logList, BankRepayOrgFreezeLogVO.class));
        }
        return response;
    }

    /**
     * 根据orderId和borrowNid获取当前有效的冻结记录
     */
    @RequestMapping("/getValid/{orderId}/{borrowNid}")
    public BankRepayOrgFreezeLogResponse getFreezeLogValid(@PathVariable String orderId, @PathVariable String borrowNid) {
        BankRepayOrgFreezeLogResponse response = new BankRepayOrgFreezeLogResponse();
        List<BankRepayOrgFreezeLog> logList = bankRepayFreezeOrgService.getBankRepayOrgFreezeLogList(orderId, borrowNid);
        if (Validator.isNotNull(logList)) {
            response.setResultList(CommonUtils.convertBeanList(logList, BankRepayOrgFreezeLogVO.class));
        }
        return response;
    }

}
