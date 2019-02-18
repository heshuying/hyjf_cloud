package com.hyjf.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.base.BaseController;
import com.hyjf.pay.bean.BankCallDefine;
import com.hyjf.pay.call.BankCallApi;
import com.hyjf.pay.config.SystemConfig;
import com.hyjf.pay.entity.BankExclusiveLog;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.service.BankPayLogService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/bankcall")
//@DefaultProperties(defaultFallback = "defaultFallback")
public class BankCallController extends BaseController {
    Logger logger = LoggerFactory.getLogger(BankCallController.class);

    @Autowired
    BankPayLogService payLogService;

    @Autowired
    private BankCallApi api;
    
    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private  RestTemplate restTemplate;

    /**
     * 调用接口(页面)
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @PostMapping(value = "callApiPage.json")
    @ResponseBody
    @HystrixCommand(commandKey="银行页面调用-callPageApi", fallbackMethod = "fallBackBankPage",ignoreExceptions = CheckException.class,commandProperties = {
            //设置断路器生效
          @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),        
            //一个统计窗口内熔断触发的最小个数3/10s
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            //熔断5秒后去尝试请求
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
          @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
          // 超时时间
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "40000")},threadPoolProperties = {
          @HystrixProperty(name="coreSize", value="200"), @HystrixProperty(name="maxQueueSize", value="50")})
    public Map<String,Object> callPageApi(@RequestBody BankCallBean bean) throws Exception {

        logger.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            // 参数转换成Map
            bean.convert();
            String txCode = bean.getTxCode();
            if (Validator.isNull(txCode)) {
                logger.warn("消息类型不能为空, bean is : {}", bean);
                throw new RuntimeException("消息类型不能为空");
            }
            // 操作时间
            int nowTime = GetDate.getNowTime10();
            bean.setLogTime(String.valueOf(nowTime));
            String exclusiveId = payLogService.insertChinapnrExclusiveLog(bean);
            if (StringUtils.isBlank(exclusiveId)) {
                throw new RuntimeException("页面调用前,保存请求数据失败！订单号：" + bean.getLogOrderId());
            }
            // 设置返回URL
          /*  if (Validator.isNotNull(bean.getRetUrl())) {
                String retUrl = systemConfig.getCallbackSuccessUrl() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId();
                bean.setRetUrl(retUrl);
                bean.set(BankCallConstant.PARAM_RETURL, retUrl);
            }*/
            if (Validator.isNotNull(bean.getNotifyUrl())) {
                String notifyUrl = systemConfig.getCallbackUrl() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId();
                bean.setNotifyUrl(notifyUrl);
                bean.set(BankCallConstant.PARAM_NOTIFYURL, notifyUrl);
            }
           /* if (Validator.isNotNull(bean.getSuccessfulUrl())) {
                String successfulUrl = systemConfig.getReturnUrl() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId()+ StringPool.AMPERSAND + BankCallConstant.PARAM_ISSUCCESS + StringPool.EQUAL+"1";
                bean.setSuccessfulUrl(successfulUrl);
                bean.set(BankCallConstant.PARAM_SUCCESSFUL_URL, successfulUrl);
            }*/

            if (Validator.isNotNull(bean.getSuccessfulUrl())) {
                String successfulUrl = systemConfig.getCallbackSuccessUrl() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId()+ StringPool.AMPERSAND + BankCallConstant.PARAM_ISSUCCESS + StringPool.EQUAL+"1";
                bean.setSuccessfulUrl(successfulUrl);
                bean.set(BankCallConstant.PARAM_SUCCESSFUL_URL, successfulUrl);
            }
            if (Validator.isNotNull(bean.getForgotPwdUrl())) {
                String forgotPwdUrl = systemConfig.getForgotpwdUrl() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId();
                bean.setForgotPwdUrl(forgotPwdUrl);
                bean.set(BankCallConstant.PARAM_FORGOTPWDURL, forgotPwdUrl);
            }

            // 填充bean对象
            api.rebuildBean(bean);

            {
                // 发送前插入日志记录
                String sendLogFlag = payLogService.insertChinapnrSendLog(bean);
                if (StringUtils.isNotBlank(sendLogFlag)) {
                    bean.setAction(systemConfig.getBankPageUrl() + bean.getLogBankDetailUrl());
                    resultMap.put(BankCallDefine.BANK_FORM, bean);
                } else {
                    throw new RuntimeException("页面调用前,保存请求数据失败！订单号：" + bean.getLogOrderId());
                }
            }
        } catch (Exception e) {
            logger.error("调用pay异常", e);
            throw e;
        } finally {
            logger.info("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
        }
        return resultMap;
    }
    

    public Map<String,Object> fallBackBankPage(BankCallBean bean) throws Exception {
        logger.info("==================已进入 银行页面调用 fallBackBankPage 方法================");
    	return new HashMap<String,Object>();
    }


    /**
     * 成功页面接收同步返回消息
     *
     * @return
     */
    @RequestMapping(value = "callPageSuccessReturns")
    public ModelAndView callPageSuccessReturns(HttpServletRequest request) {

        String logOrderId = request.getParameter(BankCallConstant.PARAM_LOGORDERID);
        logger.info("[调用接口结束, 消息类型:" + (logOrderId == null ? "" : logOrderId) + "]");
        String retUrl="";
        String redirectUrl="";
        // 真实的返回URL
        // 获取主键
        BankExclusiveLog record = this.payLogService.selectChinapnrExclusiveLogByOrderId(logOrderId);
        if (record != null) {
            JSONObject jo = new JSONObject();
            try {
                jo = JSONObject.parseObject(record.getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            retUrl = jo.getString(BankCallConstant.PARAM_SUCCESSFULURL);

        }
        if(StringUtils.isNotEmpty(retUrl)){
        	redirectUrl="redirect:"+retUrl;
        }
        /*logger.info("[调用接口结束, 消息类型:" + (redirectUrl == null ? "" : redirectUrl) + "]");*/
        logger.info("[调用接口结束, 消息类型:" +  redirectUrl + "]");
        return new ModelAndView(redirectUrl);
    }

    /**
     * 页面接收同步返回消息
     *
     * @return
     */
    @RequestMapping(value = "callPageReturn")
    public ModelAndView callPageReturn(HttpServletRequest request, @ModelAttribute BankCallBean bean) {

        String logOrderId = request.getParameter(BankCallConstant.PARAM_LOGORDERID);
        String logUserId = request.getParameter(BankCallConstant.PARAM_LOGUSERID);
        String frontParams = request.getParameter("FRONTPARAMS");
        // 真实的返回URL
        String notifyUrl = "";
        String retUrl = "";
        // 验签成功时, 跳转到各功能模块的回调URL
        ModelAndView modelAndView = new ModelAndView(BankCallDefine.JSP_BANK_SEND);
        // 部分接口调用有返回结果
        if (Validator.isNotNull(bean) && StringUtils.isNotBlank(bean.getTxCode())) {
            logger.info("交易完成后,回调开始,消息类型:[" + (bean == null ? "" : bean.getTxCode()) + "],订单号:[" + logOrderId + "],用户ID:[" + logUserId + "].");
            // 判断返回参数是否为空
            if (Validator.isNull(bean)) {
                throw new RuntimeException("接收的同步返回参数为空，调用银行接口失败");
            } else if (StringUtils.isBlank(bean.getTxCode())) {
                throw new RuntimeException("接收的同步返回消息类型为空，调用银行接口失败");
            }
            // 参数转换成Map
            bean.convert();
            // 输出debug日志
            bean.setLogOrderId(logOrderId);
            bean.setLogUserId(logUserId);
            // 写入银行接口接收记录
            this.payLogService.saveChinapnrLog(bean, 0);

            // 发送状态(1:处理中)
            String status = BankCallConstant.STATUS_SENDING;
            String acqRes = null;
            try {
                if (StringUtils.isNotBlank(bean.getAcqRes())) {
                    acqRes = URLDecoder.decode(bean.getAcqRes(), CustomConstants.UTF8);
                    if (!acqRes.contains("{")) {
                        bean.getAllParams().put(BankCallConstant.PARAM_ACQRES, acqRes);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 验签
            BankCallBean result = api.verifyChinaPnr(bean);
            try {
                if (StringUtils.isNotBlank(bean.getAcqRes())) {
                    bean.setAcqRes(URLDecoder.decode(bean.getAcqRes(), CustomConstants.UTF8));
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            bean.convert();
            // 检证失败
            if (Validator.isNull(result) || !result.isLogVerifyFlag()) {
                status = BankCallConstant.STATUS_VERTIFY_NG;
                bean.setLogOrderStatus(status);
                logger.debug("验证签名失败");
                logger.info("验签失败,订单号:[" + bean.getLogOrderId() + "]");
            } else {
                status = BankCallConstant.STATUS_VERTIFY_OK;
                bean.setLogOrderStatus(status);
                logger.debug("验证签名成功");
            }
            // 查询相应的订单记录
            if (Validator.isNotNull(logOrderId)) {
                // 获取主键
                BankExclusiveLog record = this.payLogService.selectChinapnrExclusiveLogByOrderId(logOrderId);
                if (record != null) {
                    bean.setLogOrderId(logOrderId);
                    bean.setLogClient(record.getClient());
                    bean.setLogUserId(logUserId);
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGORDERID, logOrderId);
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGCLIENT, String.valueOf(record.getClient()));
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGUSERID, logUserId);
                    JSONObject jo = new JSONObject();
                    try {
                        jo = JSONObject.parseObject(record.getContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    notifyUrl = jo.getString(BankCallConstant.PARAM_NOTIFYURL);
                    String isSuccess = request.getParameter("isSuccess");
                    // 交易成功了
                    if (isSuccess != null && "1".equals(isSuccess)) {
                        retUrl = jo.getString(BankCallConstant.PARAM_SUCCESSFULURL);
                    }else{
                        retUrl = jo.getString(BankCallConstant.PARAM_RETURL);
                    }

                    if (StringUtils.isNotBlank(jo.getString(BankCallConstant.PARAM_ACQRES))) {
                        bean.getAllParams().put(BankCallConstant.PARAM_ACQRES, jo.getString(BankCallConstant.PARAM_ACQRES));
                    }
                    bean.setRetUrl(retUrl);
                    bean.setNotifyUrl(notifyUrl);
                    // 如果检证数据状态为未发送
                    // 更新状态记录
                    int nowTime = GetDate.getNowTime10();

                    this.payLogService.updateChinapnrExclusiveLog(logOrderId, bean, nowTime);

                    if (result.isLogVerifyFlag() && Validator.isNotNull(retUrl)) {
                        bean.setAction(retUrl);
                        logger.info("同步回调跳转,callBeckUrl:[" + bean.getAction() + "].");
                        bean.getAllParams().remove(BankCallConstant.PARAM_SIGN);
                        bean.getAllParams().remove(BankCallConstant.PARAM_VERSION);
                        bean.setSign(null);
                        bean.setVersion(null);
                        modelAndView.addObject(BankCallDefine.BANK_FORM, bean);
                    } else {
                        modelAndView.setViewName(BankCallDefine.JSP_BANK_RESULT);
                        modelAndView.addObject("content", "验签失败!<br>" + bean.getAllParams().toString());
                    }
                } else {
                    modelAndView = new ModelAndView(BankCallDefine.JSP_BANK_RESULT);
                    modelAndView.addObject("content", "未查询到相应的订单信息");
                }
            } else {
                modelAndView = new ModelAndView(BankCallDefine.JSP_BANK_RESULT);
                modelAndView.addObject("content", "订单信息错误");
            }
        } else {
            if (StringUtils.isNotBlank(logOrderId)) {
                BankExclusiveLog record = payLogService.selectChinapnrExclusiveLogByOrderId(logOrderId);
                if (record != null) {
                    bean.setLogOrderId(logOrderId);
                    bean.setLogClient(record.getClient());
                    bean.setLogUserId(logUserId);
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGORDERID, logOrderId);
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGCLIENT, String.valueOf(record.getClient()));
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGUSERID, logUserId);
                    bean.getAllParams().put(BankCallConstant.PARAM_FRONTPARAMS, frontParams);
                    JSONObject jo = new JSONObject();
                    try {
                        jo = JSONObject.parseObject(record.getContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    notifyUrl = jo.getString(BankCallConstant.PARAM_NOTIFYURL);
                    String isSuccess = request.getParameter("isSuccess");
                    // 交易成功了
                    if (isSuccess != null && "1".equals(isSuccess)) {
                        retUrl = jo.getString(BankCallConstant.PARAM_SUCCESSFULURL);
                    }else{
                        retUrl = jo.getString(BankCallConstant.PARAM_RETURL);
                    }

                    if (StringUtils.isNotBlank(jo.getString(BankCallConstant.PARAM_ACQRES))) {
                        bean.getAllParams().put(BankCallConstant.PARAM_ACQRES, jo.getString(BankCallConstant.PARAM_ACQRES));
                    }
                    bean.setRetUrl(retUrl);
                    bean.setNotifyUrl(notifyUrl);
                    bean.setAction(retUrl);
                    logger.info("同步回调,callBackUrl:[" + bean.getAction() + "]");
                    bean.getAllParams().remove(BankCallConstant.PARAM_SIGN);
                    bean.getAllParams().remove(BankCallConstant.PARAM_VERSION);
                    bean.setSign(null);
                    bean.setVersion(null);
                    modelAndView.addObject(BankCallDefine.BANK_FORM, bean);
                } else {
                    modelAndView = new ModelAndView(BankCallDefine.JSP_BANK_RESULT);
                    modelAndView.addObject("content", "未查询到相应的订单信息");
                }
            } else {
                modelAndView = new ModelAndView(BankCallDefine.JSP_BANK_RESULT);
                modelAndView.addObject("content", "订单信息错误");
            }
        }
        logger.info("[交易完成后,回调结束]");
        return modelAndView;
    }

    /**
     * 接收页面异步返回的消息
     *
     * @return
     */
    @RequestMapping(value = "callPageBack")
    @HystrixCommand(commandKey="银行异步返回-callPageBack",commandProperties = {
            //设置断路器生效
          @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),        
            //一个统计窗口内熔断触发的最小个数3/10s
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            //熔断5秒后去尝试请求
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
          @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
          // 超时时间
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "40000")},threadPoolProperties = {
          @HystrixProperty(name="coreSize", value="200"), @HystrixProperty(name="maxQueueSize", value="50")})
    public void callPageBack(HttpServletRequest request, HttpServletResponse response, @ModelAttribute BankCallBean bean) {
        String bgData = request.getParameter("bgData");
        logger.info("接收异步返回的消息开始,订单号:【" + bean.getLogOrderId() + "】,用户ID:【" + bean.getLogUserId() + "】.");
        logger.info("消息内容=【" + bean + "】");
        logger.info("消息内容=【" + bgData + "】");

        Map<String, String> mapParam;
        try {
            mapParam = new ObjectMapper().readValue(bgData, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        //先验签
        BankCallBean result = api.verifyChinaPnr(mapParam);

        String logOrderId = request.getParameter(BankCallConstant.PARAM_LOGORDERID);
        String logUserId = request.getParameter(BankCallConstant.PARAM_LOGUSERID);
        String content = "";
        if (StringUtils.isNotBlank(bgData)) {
            bean = JSONObject.parseObject(bgData, BankCallBean.class);
            // 判断返回参数是否为空
            if (Validator.isNull(bean)) {
                throw new RuntimeException("接收的同步返回参数为空，调用银行接口失败");
            } else if (StringUtils.isBlank(bean.getTxCode())) {
                throw new RuntimeException("接收的同步返回消息类型为空，调用银行接口失败");
            }
            // 参数转换成Map
            bean.convert();
            // 输出debug日志
            bean.setLogOrderId(logOrderId);
            bean.setLogUserId(logUserId);
            // 写入银行接口接收记录
            this.payLogService.insertChinapnrLog(bean, mapParam, 1);
            // 发送状态(1:处理中)
            String status = BankCallConstant.STATUS_SENDING;
            // 验签
            try {
                if (StringUtils.isNotBlank(bean.getAcqRes())) {
                    bean.setAcqRes(URLDecoder.decode(bean.getAcqRes(), CustomConstants.UTF8));
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            bean.convert();
            // 检证失败
            if (Validator.isNull(result) || !result.isLogVerifyFlag()) {
                status = BankCallConstant.STATUS_VERTIFY_NG;
                bean.setLogOrderStatus(status);
                logger.debug("验证签名失败");
                logger.info("验签失败,订单号:[" + bean.getLogOrderId() + "].");
            } else {
                status = BankCallConstant.STATUS_VERTIFY_OK;
                bean.setLogOrderStatus(status);
                logger.debug("验证签名成功");
            }
            // 真实的返回URL
            String notifyUrl = "";
            String retUrl = "";
            // 返回参数中含有logOrderId时, 更新状态记录
            if (Validator.isNotNull(logOrderId)) {
                // 取得检证数据
                BankExclusiveLog record = payLogService.selectChinapnrExclusiveLogByOrderId(logOrderId);
                if (record != null) {
                    bean.setLogOrderId(logOrderId);
                    bean.setLogClient(record.getClient());
                    bean.setLogUserId(logUserId);
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGORDERID, logOrderId);
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGCLIENT, String.valueOf(record.getClient()));
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGUSERID, logUserId);
                    JSONObject jo = new JSONObject();
                    try {
                        jo = JSONObject.parseObject(record.getContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    notifyUrl = jo.getString(BankCallConstant.PARAM_NOTIFYURL);
                    retUrl = jo.getString(BankCallConstant.PARAM_RETURL);
                    if (StringUtils.isNotBlank(jo.getString(BankCallConstant.PARAM_ACQRES))) {
                        bean.getAllParams().put(BankCallConstant.PARAM_ACQRES, jo.getString(BankCallConstant.PARAM_ACQRES));
                    }
                    bean.setRetUrl(retUrl);
                    bean.setNotifyUrl(notifyUrl);
                    // 如果检证数据状态为未发送
                    // 更新状态记录
                    int nowTime = GetDate.getNowTime10();
                    this.payLogService.updateChinapnrExclusiveLog(logOrderId, bean, nowTime);
                    // 验签成功时
                    if (result.isLogVerifyFlag()) {
                        try {
                            if (Validator.isNotNull(notifyUrl)) {
                                bean.getAllParams().remove(BankCallConstant.PARAM_SIGN);
                                bean.getAllParams().remove(BankCallConstant.PARAM_VERSION);
                                bean.setSign(null);
                                bean.setVersion(null);
                                notifyUrl = StringEscapeUtils.unescapeHtml(notifyUrl);
//                                content = HttpDeal.post(notifyUrl, bean.getAllParams());
                                logger.info("开始请求回调地址：" + notifyUrl);
                                logger.info("参数：" + JSON.toJSONString(bean.getAllParams()));
                                content = restTemplate
                                        .postForEntity(notifyUrl, bean.getAllParams(), String.class).getBody();
                                logger.debug("返回值content：" + content);
                                if (StringUtils.isNotBlank(content)) {
                                    BankCallResult callResult = JSONObject.parseObject(content, BankCallResult.class);
                                    if (callResult.isStatus()) {
                                        // 更新数据为发送成功
                                        content = result.getLogVerifyResult();
                                        this.payLogService.updateChinapnrExclusiveLog(logOrderId, 1);
                                    } else {
                                        // 更新数据为已发送
                                        this.payLogService.updateChinapnrExclusiveLog(logOrderId, 2);
                                    }
                                }
                            } else {
                                throw new RuntimeException("异步回调后,回调异步url为空！订单号：" + bean.getLogOrderId());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 更新数据状态为验签失败
                        this.payLogService.updateChinapnrExclusiveLog(logOrderId, 3);
                    }
                    logger.info("[接收异步返回的消息结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
                }
            }
        }
        // 当业务请求认为本次回调成功
        if (StringUtils.isNotBlank(content) && content.equals(BankCallConstant.RECEIVED_SUCCESS)) {
            // 银行后台应答输出流
            PrintWriter out = null;
            try {
                response.setCharacterEncoding("UTF-8");// 设置将字符以"UTF-8"编码输出到客户端浏览器
                out = response.getWriter();// 获取PrintWriter输出流
                out.write(content);//输出结果给银行
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
        return;
    }

    /**
     * 调用接口(后台)
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping(value = "callApiBg.json")
    @HystrixCommand(commandKey="银行接口调用-callApiBg", fallbackMethod = "fallBackBankBgApi",ignoreExceptions = CheckException.class,commandProperties = {
            //设置断路器生效
          @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //一个统计窗口内熔断触发的最小个数3/10s
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            //熔断5秒后去尝试请求
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
          @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
          // 超时时间
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "40000")},threadPoolProperties = {
          @HystrixProperty(name="coreSize", value="200"), @HystrixProperty(name="maxQueueSize", value="50")})
    public String callApiBg( @RequestBody BankCallBean bean) {
        logger.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
        String ret = "";
        String logOrderId = bean.getLogOrderId();
        try {
            // 参数转换成Map
            bean.convert();
            // 消息类型
            String txCode = bean.getTxCode();
            if (Validator.isNull(txCode)) {
                throw new RuntimeException("消息类型不能为空");
            }
            // 操作时间
            int nowTime = GetDate.getNowTime10();
            bean.setLogTime(String.valueOf(nowTime));
            // 发送前插入状态记录
            this.payLogService.insertChinapnrExclusiveLog(bean);
            if (Validator.isNotNull(bean.getNotifyURL())) {
                String notifyURL = systemConfig.getApiReturnUrl() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId() + StringPool.AMPERSAND + BankCallConstant.PARAM_LOGNOTIFYURLTYPE + StringPool.EQUAL
                        + BankCallConstant.PARAM_LOGNOTIFYURL;
                bean.setNotifyURL(notifyURL);
                bean.set(BankCallConstant.PARAM_NOTIFY_URL, notifyURL);
            }
            if (Validator.isNotNull(bean.getRetNotifyURL())) {
                String retNotifyURL = systemConfig.getApiReturnUrl() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId() + StringPool.AMPERSAND + BankCallConstant.PARAM_LOGNOTIFYURLTYPE + StringPool.EQUAL
                        + BankCallConstant.PARAM_LOGRETNOTIFYURL;
                bean.setRetNotifyURL(retNotifyURL);
                bean.set(BankCallConstant.PARAM_RETNOTIFY_URL, retNotifyURL);
            }

            // 填充bean对象
            api.rebuildBean(bean);

            {
                // 发送前插入日志记录
                this.payLogService.insertChinapnrSendLog(bean);
                String result = api.callChinaPnrApi(bean);
                // mod by nixiaoling 20140128 优化pay工程出现504 时候不报空指针，打出error级别日志 start
                if(StringUtils.isEmpty(result)) {
                    logger.error("txtCode：" + bean.getTxCode()+",订单号："+logOrderId);
                    return ret;
                }
                //mod by nixiaoling 20140128 优化pay工程出现504 时候不报空指针，打出error级别日志 end
                Map<String, String> mapParam;
                try {
                    mapParam = new ObjectMapper().readValue(result, new TypeReference<Map<String, String>>() {
                    });
                } catch (IOException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
                //先验签
                BankCallBean verifyResult = api.verifyChinaPnr(mapParam);
                BankCallBean backBean = JSONObject.parseObject(result, BankCallBean.class);
                // 实体转化
                backBean.convert();
                backBean.setLogOrderId(bean.getLogOrderId());
                backBean.setLogUserId(bean.getLogUserId());
                backBean.setTxDate(StringUtil.isBlank(backBean.getTxDate()) ? bean.getTxDate() : backBean.getTxDate());
                backBean.setTxTime(StringUtil.isBlank(backBean.getTxTime()) ? bean.getTxTime() : backBean.getTxTime());
                backBean.setSeqNo(StringUtil.isBlank(backBean.getSeqNo()) ? bean.getSeqNo() : backBean.getSeqNo());
                backBean.setTxCode(StringUtil.isBlank(backBean.getTxCode()) ? bean.getTxCode() : backBean.getTxCode());
                // 插入返回值
                payLogService.insertChinapnrLog(backBean, mapParam, 2);
                // 发送状态(1:处理中)
                String status = BankCallConstant.STATUS_SENDING;
                backBean.convert();
                // 检证失败
                if (Validator.isNull(verifyResult) || !verifyResult.isLogVerifyFlag()) {
                    status = BankCallConstant.STATUS_VERTIFY_NG;
                    bean.setLogOrderStatus(status);
                    logger.debug("验证签名失败");
                } else {
                    status = BankCallConstant.STATUS_VERTIFY_OK;
                    bean.setLogOrderStatus(status);
                    logger.debug("验证签名成功");

                    bean.getAllParams().remove(BankCallConstant.PARAM_SIGN);
                    bean.getAllParams().remove(BankCallConstant.PARAM_VERSION);
                    bean.setSign(null);
                    bean.setVersion(null);
                    ret = JSONObject.toJSONString(backBean, true);
                }
                // 返回参数中含有logOrderId时, 更新状态记录
                if (Validator.isNotNull(logOrderId)) {
                    // 取得检证数据
                    backBean.setLogOrderId(bean.getLogOrderId());
                    backBean.setLogUserId(bean.getLogUserId());
                    backBean.setLogClient(bean.getLogClient());
                    backBean.setLogOrderStatus(bean.getLogOrderStatus());
                    payLogService.updateChinapnrExclusiveLog(logOrderId, backBean, nowTime);
                    // 验签成功时
                    if (verifyResult.isLogVerifyFlag()) {

                    } else {
                        // 更新数据状态为验签失败
                        this.payLogService.updateChinapnrExclusiveLog(logOrderId, 3);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("订单号：" + logOrderId, e);
        } finally {
            logger.info("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
        }
        return ret;
    }
    

    public String fallBackBankBgApi(BankCallBean bean) {
    	return "";
    }

    /**
     * 接收联机异步返回的消息
     *
     * @return
     */
    @RequestMapping(value = "callApiReturn")
    @HystrixCommand(commandKey="银行联机异步返回-callBack",commandProperties = {
            //设置断路器生效
          @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),        
            //一个统计窗口内熔断触发的最小个数3/10s
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            //熔断5秒后去尝试请求
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
          @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
          // 超时时间
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "40000")},threadPoolProperties = {
          @HystrixProperty(name="coreSize", value="200"), @HystrixProperty(name="maxQueueSize", value="50")})
    public void callBack(HttpServletRequest request, HttpServletResponse response, @ModelAttribute BankCallBean bean) {
        String bgData = request.getParameter("bgData");
        String logOrderId = request.getParameter(BankCallConstant.PARAM_LOGORDERID);
        String logUserId = request.getParameter(BankCallConstant.PARAM_LOGUSERID);
        String logNotifyType = request.getParameter(BankCallConstant.PARAM_LOGNOTIFYURLTYPE);
        String content = "";
        if (StringUtils.isNotBlank(bgData)) {
            bean = JSONObject.parseObject(bgData, BankCallBean.class);
            logger.info("-接收异步返回的消息开始, 消息类型:[" + (bean == null ? "" : bean.getTxCode()) + "],订单号:[" + logOrderId + "],用户ID:[" + logUserId + "]. logNotifyType: " + logNotifyType);
            // 判断返回参数是否为空
            if (Validator.isNull(bean)) {
                throw new RuntimeException("接收的同步返回参数为空，调用银行接口失败");
            } else if (StringUtils.isBlank(bean.getTxCode())) {
                throw new RuntimeException("接收的同步返回消息类型为空，调用银行接口失败");
            }
            // 参数转换成Map
            bean.convert();
            // 输出debug日志
            logger.debug("参数: " + bean == null ? "无" : bean.getAllParams() + "]");
            bean.setLogOrderId(logOrderId);
            bean.setLogUserId(logUserId);
            // 写入银行接口接收记录
            this.payLogService.insertChinapnrLog(bean, 1);
            // 发送状态(1:处理中)
            String status = BankCallConstant.STATUS_SENDING;
            if (StringUtils.isNotBlank(logNotifyType)) {
                bean.setLogNotifyType(logNotifyType);
            }
            // 验签
            BankCallBean result = api.verifyChinaPnr(bean);
            bean.convert();
            bean.setLogOrderId(logOrderId);
            bean.setLogUserId(logUserId);
            // 检证失败
            if (Validator.isNull(result) || !result.isLogVerifyFlag()) {
                status = BankCallConstant.STATUS_VERTIFY_NG;
                bean.setLogOrderStatus(status);
                logger.debug("验证签名失败");
                logger.info("验签失败,订单号:" + bean.getLogOrderId() + "]");
            } else {
                status = BankCallConstant.STATUS_VERTIFY_OK;
                bean.setLogOrderStatus(status);
                logger.debug("验证签名成功");
            }
            // 真实的返回URL
            String notifyUrl = "";
            // 返回参数中含有logOrderId时, 更新状态记录
            if (Validator.isNotNull(logOrderId)) {
                // 取得检证数据
                BankExclusiveLog record = payLogService.selectChinapnrExclusiveLogByOrderId(logOrderId);
                if (record != null) {
                    bean.setLogOrderId(logOrderId);
                    bean.setLogClient(record.getClient());
                    bean.setLogUserId(record.getCreateuser());
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGORDERID, logOrderId);
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGCLIENT, String.valueOf(record.getClient()));
                    bean.getAllParams().put(BankCallConstant.PARAM_LOGUSERID, logUserId);
                    JSONObject jo = new JSONObject();
                    try {
                        jo = JSONObject.parseObject(record.getContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 如果相应的回调类型不为空
                    if (StringUtils.isNotBlank(logNotifyType)) {
                        if (logNotifyType.equals(BankCallConstant.PARAM_LOGNOTIFYURL)) {
                            notifyUrl = jo.getString(BankCallConstant.PARAM_NOTIFY_URL);
                        } else if (logNotifyType.equals(BankCallConstant.PARAM_LOGRETNOTIFYURL)) {
                            notifyUrl = jo.getString(BankCallConstant.PARAM_RETNOTIFY_URL);
                        }
                    } else {
                        notifyUrl = jo.getString(BankCallConstant.PARAM_NOTIFYURL);
                    }
                    if (StringUtils.isNotBlank(jo.getString(BankCallConstant.PARAM_ACQRES))) {
                        bean.getAllParams().put(BankCallConstant.PARAM_ACQRES, jo.getString(BankCallConstant.PARAM_ACQRES));
                    }
                    bean.setNotifyUrl(notifyUrl);
                    // 如果检证数据状态为未发送
                    // 更新状态记录
                    int nowTime = GetDate.getNowTime10();
                    this.payLogService.updateChinapnrExclusiveLog(logOrderId, bean, nowTime);
                    // 验签成功时
                    if (result.isLogVerifyFlag()) {
                        try {
                            if (Validator.isNotNull(notifyUrl)) {
                                bean.getAllParams().remove(BankCallConstant.PARAM_SIGN);
                                bean.getAllParams().remove(BankCallConstant.PARAM_VERSION);
                                bean.setSign(null);
                                bean.setVersion(null);
                                notifyUrl = StringEscapeUtils.unescapeHtml(notifyUrl);
//                                content = HttpDeal.post(notifyUrl, bean.getAllParams());
                                content = restTemplate
                                        .postForEntity(notifyUrl, bean.getAllParams(), String.class).getBody();
                                logger.info("联机异步调用notifyUrl: "+notifyUrl+"   返回结果为: "+content);
                                if (StringUtils.isNotBlank(content)) {
                                    BankCallResult callResult = JSONObject.parseObject(content, BankCallResult.class);
                                    if (callResult.isStatus()) {
                                        // 更新相应的记录为成功
                                        content = result.getLogVerifyResult();
                                        this.payLogService.updateChinapnrExclusiveLog(logOrderId, 1);
                                    } else {
                                        // 更新相应的记录为已发送
                                        this.payLogService.updateChinapnrExclusiveLog(logOrderId, 2);
                                    }
                                }
                            } else {
                                throw new RuntimeException("异步回调后,回调异步url为空！订单号：" + bean.getLogOrderId());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 更新数据状态为验签失败
                        this.payLogService.updateChinapnrExclusiveLog(logOrderId, 3);
                    }
                    logger.info("[接收异步返回的消息结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
                }
            }
        }
        // 当业务请求认为本次回调成功
        if (StringUtils.isNotBlank(content) && content.equals(BankCallConstant.RECEIVED_SUCCESS)) {
            // 银行后台应答输出流
            PrintWriter out = null;
            try {
                response.setCharacterEncoding("UTF-8");// 设置将字符以"UTF-8"编码输出到客户端浏览器
                out = response.getWriter();// 获取PrintWriter输出流
                out.write(content);//输出结果给银行
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
        return;
    }

    /**
     * 忘记密码调用接口
     *
     * @return
     */
    @RequestMapping(value = "pageForgotPWD")
    public ModelAndView pageForgotPWD(HttpServletRequest request) {

        String logOrderId = request.getParameter(BankCallConstant.PARAM_LOGORDERID);
        logger.info("[忘记密码调用接口开始, logOrderId:" + (logOrderId == null ? "" : logOrderId) + "]");
        String retUrl="";
        // 真实的返回URL
        // 获取主键
        BankExclusiveLog record = this.payLogService.selectChinapnrExclusiveLogByOrderId(logOrderId);
        if (record != null) {
            JSONObject jo = new JSONObject();
            try {
                jo = JSONObject.parseObject(record.getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            retUrl = jo.getString(BankCallConstant.PARAM_FORGOTPWDURL);
        }
        String redirectUrl="redirect:"+retUrl;
        logger.info("[忘记密码调用接口结束, retUrl:" + (redirectUrl == null ? "" : redirectUrl) + "]");
        return new ModelAndView(redirectUrl);
    }
}