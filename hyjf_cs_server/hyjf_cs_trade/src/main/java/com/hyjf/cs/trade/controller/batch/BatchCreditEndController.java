/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.batch.BatchCreditEndService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 批次结束债权
 *
 * @author liubin
 * @version BatchCreditEndController, v0.1 2018/6/28 13:59
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/batch/creditend")
public class BatchCreditEndController extends BaseTradeController {
    @Autowired
    private BatchCreditEndService batchCreditEndService;

    @RequestMapping("/batchcreditend")
    public BooleanResponse batchCreditEnd() {
        logger.info("-----------------批次结束债权请求开始------------------");
        try {
            // 批次结束债权
            Boolean result = this.batchCreditEndService.batchCreditEnd();
            if (result){
                logger.info("-----------------批次结束债权请求成功---------------");
            }else{
                logger.error("-----------------批次结束债权请求失败---------------");
            }
            return new BooleanResponse(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("-----------------批次结束债权请求失败-------------------");
            return new BooleanResponse(false);
        }
    }

    /**
     * 批次结束债权合法性检查后处理（异步回调）
     * @param request
     * @param response
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping("/batch_credit_end_verify")
    public String batchCreditEndVerify(HttpServletRequest request, HttpServletResponse response, @RequestBody BankCallBean bean) throws Exception {

        BankCallResult result = new BankCallResult();
        logger.info("批次结束债权,合法性检查异步回调开始，bean：" + JSON.toJSONString(bean));
        logger.info("批次结束债权,合法性检查异步回调开始 "+bean.getRetCode() + "  "+ bean.getBatchNo());

        if (StringUtils.isBlank(bean.getRetCode()) || StringUtils.isBlank(bean.getBatchNo())) {
            logger.info(" 合法性校验 为空！");
            return JSONObject.toJSONString(result, true);
        }

        //合法性检查后，更新批次结束债权任务
        int resultCnt = this.batchCreditEndService.updateBatchCreditEndCheck(bean);

        logger.info(bean.getBatchNo()+" 批次结束债权,合法性检查的异步回调结束 : "+resultCnt);

        result.setStatus(true);
        return JSONObject.toJSONString(result, true);

    }

    /**
     * 批次结束债权银行完成后处理（异步回调）
     * @param request
     * @param response
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping("/batch_credit_end_finish")
    public String batchCreditEndFinish(HttpServletRequest request, HttpServletResponse response, @RequestBody BankCallBean bean) throws Exception {

        BankCallResult result = new BankCallResult();
        logger.info("批次结束债权,业务结果异步回调开始, bean: " + JSON.toJSONString(bean));
        logger.info("批次结束债权,业务结果异步回调开始 "+bean.getRetCode() + "  "+ bean.getBatchNo());

        if (StringUtils.isBlank(bean.getRetCode()) || StringUtils.isBlank(bean.getBatchNo())) {
            logger.info(" 业务结果异步回调 为空！");
            return JSONObject.toJSONString(result, true);
        }

        // 银行完成后，更新批次结束债权任务
        int resultCnt = this.batchCreditEndService.updateBatchCreditEndFinish(bean);

        logger.info(bean.getBatchNo()+" 批次结束债权,业务结果异步回调结束 : "+resultCnt);

        result.setStatus(true);
        return JSONObject.toJSONString(result, true);

    }
}
