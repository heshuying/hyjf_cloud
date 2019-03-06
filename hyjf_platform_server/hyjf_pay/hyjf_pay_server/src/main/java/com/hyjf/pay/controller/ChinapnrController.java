package com.hyjf.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.chinapnr.MerPriv;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.base.BaseController;
import com.hyjf.pay.config.SystemConfig;
import com.hyjf.pay.entity.ChinapnrExclusiveLog;
import com.hyjf.pay.entity.ChinapnrLog;
import com.hyjf.pay.lib.PnrApiBean;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.hyjf.pay.service.ChinapnrService;
import com.hyjf.pay.service.PnrApi;
import com.hyjf.pay.service.impl.ChinaPnrApiImpl;
import com.hyjf.pay.utils.ChinaPnrSignUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/chinapnr")
public class ChinapnrController extends BaseController {
    Logger logger = LoggerFactory.getLogger(ChinapnrController.class);

    @Autowired
    ChinapnrService chinapnrService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    PnrApi api;

    @Autowired
    ChinaPnrApiImpl chinaPnrApi;


    @Autowired
    private RestTemplate restTemplate;

    /**
     * 调用接口(页面)
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/callapi.json")
    @ResponseBody
    @HystrixCommand(commandKey="汇付页面调用-callApi", fallbackMethod = "fallBackApi",ignoreExceptions = CheckException.class, commandProperties = {
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
    public Map<String,Object> callApi(@RequestBody ChinapnrBean bean) throws Exception {
        String methodName = "callApi";
        logger.info("[调用接口开始]");
        Map<String,Object> result = new HashMap<>();
        if(null==bean){
            logger.info("bean值为空");
            return null;
        }
        try {
            // 参数转换成Map
            bean.convert();
            String cmdId = bean.getCmdId();
            if (Validator.isNull(cmdId)) {
                throw new Exception("消息类型不能为空");
            } else {
                cmdId = StringUtils.lowerCase(cmdId.substring(0, 1)).concat(cmdId.substring(1));
            }
            // 插入用户请求发送日志
            String id = this.chinapnrService.insertChinapnrExclusiveLog(bean, methodName);
            if (id != null) {
                // 设置返回URL
                if (Validator.isNotNull(bean.getRetUrl())) {
                    if (ChinaPnrConstant.CMDID_DIRECT_TRF_AUTH.equals(bean.get(ChinaPnrConstant.PARAM_CMDID))) {
                        //bean.setRetUrl(systemConfig.getChinapnrBindreturnUrl());
                    } else {
                       // bean.setRetUrl(systemConfig.getChinapnrReturnUrl());
                    }
                    bean.set(ChinaPnrConstant.PARAM_RETURL, bean.getRetUrl());
                }
                //定向绑定用户不设置私有域
                if (!ChinaPnrConstant.CMDID_DIRECT_TRF_AUTH.equals(bean.get(ChinaPnrConstant.PARAM_CMDID))) {
                    ChinaPnrSignUtils.setUUID(bean, id);
                }
                // 得到接口API对象
                Class<ChinaPnrApiImpl> c = ChinaPnrApiImpl.class;
                Object obj = api;
                // 取得该消息类型对应的bean
                Method method = c.getMethod(cmdId, PnrApiBean.class);
                Object retBean = method.invoke(obj, bean);
                if (retBean != null && retBean instanceof PnrApiBean) {
                    PnrApiBean pnrApiBean = (PnrApiBean) retBean;
                    try {
                        this.chinapnrService.insertChinapnrSendLog(bean, pnrApiBean);
                    } catch (Exception e) {
                        result.put("viewName",CommonConstant.JSP_CHINAPNR_RESULT);
                        result.put("content", "保存发送日志失败！");
                    }
                    // 跳转到汇付天下画面
                    pnrApiBean.setAction(systemConfig.getChinapnrUrl());
                    result.put(CommonConstant.CHINAPNR_FORM, pnrApiBean);
                }
            } else {
                result.put("viewName",CommonConstant.JSP_CHINAPNR_RESULT);
                result.put("content", "保存发送日志失败！");
            }
        } catch (Exception e) {
            logger.error("汇付异常", e);
            throw e;
        } finally {
            logger.info("[调用汇付接口结束, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        }
        return result;
    }

    public Map<String,Object> fallBackApi(ChinapnrBean bean) {
        logger.info("==================已进入 汇付页面调用 fallBackApi 方法================");
    	return new HashMap<String,Object>();
    }

    /**
     * 交易完成后，本平台系统把交易结果通过页面方式，发送到该地址上
     *
     * @return
     */
    @PostMapping(value = "/return")
    public ModelAndView result(@RequestBody ChinapnrBean bean) {
        // 验签成功时, 跳转到各功能模块的回调URL
        ModelAndView modelAndView = new ModelAndView(CommonConstant.JSP_CHINAPNR_SEND);
        String methodName = "result";
        logger.info("[汇付交易完成后,回调开始]");
        // 参数转换成Map
        if(null==bean){
            logger.info("bean不能为空");
            modelAndView.setViewName(CommonConstant.JSP_CHINAPNR_RESULT);
            modelAndView.addObject("content", "bean值为空<br>");
            return modelAndView;
        }
        bean.convert();

        try {
            this.chinapnrService.insertChinapnrLog(bean, 0);
        } catch (Exception e) {
            modelAndView.setViewName(CommonConstant.JSP_CHINAPNR_RESULT);
            modelAndView.addObject("content", "保存相应的日志失败!<br>");
        }
        // 发送状态(1:处理中)
        String status;
        String remark;
        String merPrivTemp;
        try {
            if (null != bean.getMerPriv()) {
                merPrivTemp = URLDecoder.decode(bean.getMerPriv(), CustomConstants.UTF8);
                if (!merPrivTemp.contains("{")) {
                    bean.getAllParams().put("MerPriv", merPrivTemp);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        // 验签
        ChinapnrBean result = api.verifyChinaPnr(bean);
        try {
            if (bean.getMerPriv() != null) {
                bean.setMerPriv(URLDecoder.decode(bean.getMerPriv(), CustomConstants.UTF8));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        bean.convert();
        // 检证失败
        if (Validator.isNull(result) || !result.isVerifyFlag()) {
            status = ChinaPnrConstant.STATUS_VERTIFY_NG;
            bean.set("chkValueStatus", status);
            remark = "验证签名失败";
            logger.debug("验证签名失败");
        } else {
            status = ChinaPnrConstant.STATUS_VERTIFY_OK;
            bean.set("chkValueStatus", status);
            remark = "验证签名成功";
            logger.debug("验证签名成功");
        }

        String uuid = null;
        if (bean.getMerPrivPo() != null) {
            uuid = bean.getMerPrivPo().getUuid();
        }
        // 真实的返回URL
        String callBackUrl;
        // 取得检证数据
        ChinapnrExclusiveLog record ;
        // 返回参数中含有uuid时, 更新状态记录
        if (Validator.isNotNull(uuid)) {
            // 取得检证数据
            record = chinapnrService.selectChinapnrExclusiveLog(uuid);
        } else {
            record = chinapnrService.selectChinapnrExclusiveLogByOrderId(bean.getOrdId());
        }
        if (record != null) {
            JSONObject jo = new JSONObject();
            try {
                jo = JSONObject.parseObject(record.getContent());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            callBackUrl = jo.getString("RetUrl");
            bean.setMerPriv(jo.getString("MerPriv"));
            bean.set(ChinaPnrConstant.PARAM_MERPRIV, jo.getString("MerPriv"));
            bean.setRetUrl(callBackUrl);
            // 如果检证数据状态为未发送
            if (ChinaPnrConstant.STATUS_SENDING.equals(record.getStatus()) || !bean.getRespCode().equals(record.getRespcode())) {
                try {
                    this.chinapnrService.updateChinapnrExclusiveLog(bean, record, methodName, status, remark);
                } catch (Exception e) {
                    modelAndView.setViewName(CommonConstant.JSP_CHINAPNR_RESULT);
                    modelAndView.addObject("content", "更新相应的日志失败!<br>");
                }
            }
            if (result.isVerifyFlag() && Validator.isNotNull(callBackUrl)) {
                bean.set("uuid", String.valueOf(record.getId()));
                bean.setAction(callBackUrl);
            } else {
                modelAndView.setViewName(CommonConstant.JSP_CHINAPNR_RESULT);
                modelAndView.addObject("content", "验签失败!<br>" + bean.getAllParams().toString());
            }
            if (null != bean.getAllParams().get("MerPriv")) {
                bean.getAllParams().put("MerPriv", bean.getAllParams().get("MerPriv"));
            }
            modelAndView.addObject(CommonConstant.CHINAPNR_FORM, bean);
        } else {
            modelAndView.setViewName(CommonConstant.JSP_CHINAPNR_RESULT);
            modelAndView.addObject("content", "未查询到相应的接口请求记录!<br>");
        }
        if (null != bean.getAllParams().get("MerPriv")) {
            bean.getAllParams().put("MerPriv", bean.getAllParams().get("MerPriv"));
        }
        modelAndView.addObject(CommonConstant.CHINAPNR_FORM, bean);

        logger.info("[交易完成后,回调结束]");
        return modelAndView;
    }

    /**
     * 接收异步返回的消息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/callback")
    public String callBack(@ModelAttribute ChinapnrBean bean) {
        logger.info("汇付异步回调开始");
        String methodName = "callBack";
        logger.info("[汇付接收异步返回的消息开始, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        if(null==bean){
            logger.info("bean不能为空");
            return null;
        }
        bean.convert();
        // 写入汇付天下接口接收记录
        try {
            this.chinapnrService.insertChinapnrLog(bean, 1);
        } catch (Exception e) {
            throw new RuntimeException("保存相应的日志失败!");
        }
        // 发送状态(1:处理中)
        String status;
        String remark = null;
        if (ChinaPnrConstant.CMDID_CORP_REGISTER.equals(bean.getCmdId())) {
            if ("Y".equals(bean.getAuditStat()) || "R".equals(bean.getAuditStat()) || "F".equals(bean.getAuditStat())) {
                try {
                    if (bean.getMerPriv() != null) {
                        bean.getAllParams().put("MerPriv", URLDecoder.decode(bean.getMerPriv(), CustomConstants.UTF8));
                    }
                    bean.getAllParams().put("UsrName", URLDecoder.decode(bean.getUsrName(), CustomConstants.UTF8));
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        // 验签
        ChinapnrBean result = api.verifyChinaPnr(bean);
        try {
            if (bean.getMerPriv() != null) {
                bean.setMerPriv(URLDecoder.decode(bean.getMerPriv(), CustomConstants.UTF8));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        bean.convert();
        // 检证失败
        if (Validator.isNull(result) || !result.isVerifyFlag()) {
            status = ChinaPnrConstant.STATUS_VERTIFY_NG;
            bean.set("chkValueStatus", status);
            remark = "验证签名失败";
            logger.debug("验证签名失败");
            System.out.println("callback验签失败");
        } else {
            status = ChinaPnrConstant.STATUS_VERTIFY_OK;
            bean.set("chkValueStatus", status);
            remark = "验证签名成功";
            logger.debug("验证签名成功");
        }
        String uuid = null;
        if (bean.getMerPrivPo() != null) {
            uuid = bean.getMerPrivPo().getUuid();
        }
        // 真实的返回URL
        String callBackUrl = "";
        ChinapnrExclusiveLog record = null;
        // 返回参数中含有uuid时, 更新状态记录
        if (Validator.isNotNull(uuid)) {
            // 取得检证数据
            record = chinapnrService.selectChinapnrExclusiveLog(uuid);
        } else {
            record = chinapnrService.selectChinapnrExclusiveLogByOrderId(bean.getOrdId());
        }
        if (record != null) {
            JSONObject jo = new JSONObject();
            try {
                jo = JSONObject.parseObject(record.getContent());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            callBackUrl = jo.getString("BgRetUrl");
            bean.setMerPriv(jo.getString("MerPriv"));
            bean.set(ChinaPnrConstant.PARAM_MERPRIV, jo.getString("MerPriv"));
            bean.setRetUrl(callBackUrl);
            // 如果检证数据状态为未发送
            if (ChinaPnrConstant.STATUS_SENDING.equals(record.getStatus()) || !bean.getRespCode().equals(record.getRespcode())) {
                try {
                    this.chinapnrService.updateChinapnrExclusiveLog(bean, record, methodName, status, remark);
                } catch (Exception e) {
                    throw new RuntimeException("更新相应的接口请求日志记录失败!");
                }
                if (null != bean.getAllParams().get("MerPriv")) {
                    bean.getAllParams().put("MerPriv", bean.getAllParams().get("MerPriv"));
                }
                // 验签成功时
                if (result.isVerifyFlag()) {
                    try {
                        bean.set("uuid", record.getUuid());
                        if (Validator.isNull(callBackUrl)) {
                            // 用户绑卡回调
                            if (ChinaPnrConstant.CMDID_USER_BIND_CARD.equals(bean.getCmdId())) {
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                        } else {
                            logger.info("callBackUrl："+callBackUrl);
                            // 用户绑卡回调
                            if (ChinaPnrConstant.CMDID_USER_BIND_CARD.equals(bean.getCmdId())) {
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                            // 充值异步回调
                            else if (ChinaPnrConstant.CMDID_NET_SAVE.equals(bean.getCmdId())) {
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                            // 提现异步回调
                            else if (ChinaPnrConstant.CMDID_CASH.equals(bean.getCmdId()) || ChinaPnrConstant.CMDID_CASH.equals(bean.getRespType())) {
                                restTemplate.postForEntity(callBackUrl, bean.getAllParams(), String.class).getBody();
                            }
                            // 开户异步回调
                            else if (ChinaPnrConstant.CMDID_USER_REGISTER.equals(bean.getCmdId())) {
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                            // 债转异步回调
                            else if (ChinaPnrConstant.CMDID_CREDIT_ASSIGN.equals(bean.getCmdId())) {
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                            // 企业开户异步回调
                            else if (ChinaPnrConstant.CMDID_CORP_REGISTER.equals(bean.getCmdId())) {
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                            // 用户转账
                            else if (ChinaPnrConstant.CMDID_USR_ACCT_PAY.equals(bean.getCmdId())) {
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                            // 用户出借
                            else if (ChinaPnrConstant.CMDID_INITIATIVE_TENDER.equals(bean.getCmdId())) {
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                            // 用户解绑快捷卡
                            else if (ChinaPnrConstant.CMDID_BG_UNBIND_CARD.equals(bean.getCmdId())) {
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }
        } else {
            throw new RuntimeException("未查询到相应的接口请求记录!");
        }
        return result.getVerifyResult();
    }

    /**
     * 企业账户绑定用户，本平台系统把交易结果通过页面方式，发送到该地址上
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/bindReturn")
    public ModelAndView bindResult(@RequestBody ChinapnrBean bean) {
        ModelAndView modelAndView = new ModelAndView();
        logger.info("[交易完成后,回调开始, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        // 参数转换成Map
        if(null==bean){
            logger.info("bean为空");
            modelAndView.addObject("content", "保存回调日志失败");
            return modelAndView;
        }
        bean.convert();
        // 写入汇付天下接口接收记录
        try {
            this.chinapnrService.insertChinapnrLog(bean, 1);
        } catch (Exception e) {
            modelAndView.setViewName(CommonConstant.JSP_CHINAPNR_RESULT);
            modelAndView.addObject("content", "保存回调日志失败");
        }
        // 发送状态(1:处理中)
        String status = ChinaPnrConstant.STATUS_SENDING;
        // 验签
        ChinapnrBean result = api.verifyChinaPnr(bean);
        bean.convert();
        // 检证失败
        if (Validator.isNull(result) || !result.isVerifyFlag()) {
            status = ChinaPnrConstant.STATUS_VERTIFY_NG;
            bean.set("chkValueStatus", status);
            logger.debug("验证签名失败");
        } else {
            status = ChinaPnrConstant.STATUS_VERTIFY_OK;
            bean.set("chkValueStatus", status);
            logger.debug("验证签名成功");
        }
        // 回调URL
        String callBackUrl = "";
        // 验签成功时, 跳转到各功能模块的回调URL
        modelAndView = new ModelAndView(CommonConstant.JSP_CHINAPNR_SEND);
        if (result.isVerifyFlag() && Validator.isNotNull(callBackUrl)) {
            bean.setAction(callBackUrl);
        } else {
            modelAndView.setViewName(CommonConstant.JSP_CHINAPNR_RESULT);
            modelAndView.addObject("content", "验签失败!<br>" + bean.getAllParams().toString());
        }
        modelAndView.addObject(CommonConstant.CHINAPNR_FORM, bean);

        logger.info("[交易完成后,回调结束]");
        return modelAndView;
    }

    /**
     * 调用接口(后台)
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/callapibg")
    @HystrixCommand(commandKey="汇付接口调用-callApiBg", fallbackMethod = "fallBackCallApiBg",ignoreExceptions = CheckException.class,commandProperties = {
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
    public String callApiBg(@RequestBody ChinapnrBean bean)  {
        String methodName = "callApiBg";
        logger.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        String ret = "";
        String nowTime = GetDate.getServerDateTime(9, new Date());
        if(null==bean){
            logger.info("bean值为空");
            return ret;
        }
        try {
            // 参数转换成Map
            bean.convert();
            // 消息类型
            String cmdId = bean.getCmdId();
            if (Validator.isNull(cmdId)) {
                return ret;
            } else {
                cmdId = StringUtils.lowerCase(cmdId.substring(0, 1)).concat(cmdId.substring(1));
            }
            // 保存MerPriv
            String merPriv = bean.getMerPriv();
            // 保存本地日志Key
            // 发送前插入状态记录
            String id = this.chinapnrService.insertChinapnrExclusiveLog(bean, methodName);
            if (id != null) {
                bean.setUuid(id);
                MerPriv merPrivPo = bean.getMerPrivPo();
                if (null == merPrivPo) {
                    merPrivPo = new MerPriv();
                }
                merPrivPo.setUuid(String.valueOf(id));
                try {
                    merPriv = URLEncoder.encode(JSON.toJSONString(merPrivPo), CustomConstants.UTF8);
                    bean.setMerPriv(merPriv);
                    bean.set(ChinaPnrConstant.PARAM_MERPRIV, merPriv);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                 merPriv = bean.getMerPriv();
                // 得到接口API对象
                Class<ChinaPnrApiImpl> c = ChinaPnrApiImpl.class;
                Object obj = api;
                // 取得该消息类型对应的bean
                Method method = c.getMethod(cmdId, PnrApiBean.class);
                Object retBean = method.invoke(obj, bean);
                if (retBean != null && retBean instanceof PnrApiBean) {
                    PnrApiBean pnrApiBean = (PnrApiBean) retBean;
                    // 发送前插入日志记录
                    try {
                        this.chinapnrService.insertChinapnrSendLog(bean, pnrApiBean);
                    } catch (Exception e) {
                        throw new RuntimeException("保存发送日志失败！");
                    }

                    // 调用汇付天下API接口
                    String result = api.callChinaPnrApi(pnrApiBean);
                    ChinapnrBean chinapnrBean = JSONObject.parseObject(result, ChinapnrBean.class);
                    chinapnrBean.convert();
                    // 回复Merpriv
                    chinapnrBean.setMerPriv(merPriv);
                    chinapnrBean.set(ChinaPnrConstant.PARAM_RETURL, bean.getRetUrl());
                    // 写入汇付天下接口接收记录(如果有返回URL,在回调函数中写入log)
                    ChinapnrLog chinapnrLog = new ChinapnrLog();
                    chinapnrLog.setIsbg(2);
                    chinapnrLog.setUserId(bean.getLogUserId());
                    chinapnrLog.setOrdid(bean.getOrdId());
                    chinapnrLog.setBorrowNid(bean.getLogBorrowNid());
                    chinapnrLog.setMsgType(bean.getCmdId());
                    chinapnrLog.setRespType(bean.getRespType());
                    chinapnrLog.setMsgdata(result);
                    if (chinapnrBean != null) {
                        chinapnrLog.setRespCode(chinapnrBean.getRespCode());
                        chinapnrLog.setRespDesc(chinapnrBean.getRespDesc());
                        chinapnrLog.setTrxid(chinapnrBean.getTrxId());
                    }
                    chinapnrLog.setAddtime(GetDate.getServerDateTime(8, new Date()));
                    chinapnrLog.setRemark(bean.getLogRemark());
                    chinapnrLog.setIp(bean.getLogIp());
                    chinapnrService.insertChinapnrLog(chinapnrLog);
                    // 返回参数中含有uuid时, 更新状态记录
                    if (Validator.isNotNull(id)) {
                        // 取得检证数据
                        ChinapnrExclusiveLog record = chinapnrService.selectChinapnrExclusiveLog(id);
                        // 如果检证数据状态为未发送
                        if (ChinaPnrConstant.STATUS_SENDING.equals(record.getStatus())) {
                            String respCode = chinapnrBean == null ? "" : chinapnrBean.getRespCode();
                            String status = ChinaPnrConstant.STATUS_SUCCESS;
                            // 失败
                            if (!ChinaPnrConstant.RESPCODE_SUCCESS.equals(respCode)) {
                                status = ChinaPnrConstant.STATUS_FAIL;
                            }
                            record.setStatus(status);
                            record.setRespcode(respCode);
                        }
                        // 更新状态记录
                        record.setResult(chinapnrBean.getJson());
                        record.setUpdatetime(nowTime);
                        record.setUpdateuser(methodName);
                        try {
                            chinapnrService.updateChinapnrExclusiveLog(record);
                        } catch (Exception e) {
                            throw new RuntimeException("更新日志失败！");
                        }
                    }
                    return result;
                }
            } else {
                throw new RuntimeException("保存日志失败！");
            }
        } catch (Exception e) {
            logger.error("汇付",e);
        } finally {
            logger.info("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        }
        return ret;
    }
    

    public String fallBackCallApiBg(ChinapnrBean bean) {
    	return "";
    }

    /**
     * 调用接口(AJAX)
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/callapiajax")
    public String callApiAjax(@RequestBody ChinapnrBean bean) throws Exception {
        String methodName = "callApiAjax";
        logger.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        String ret = "";
        if(bean == null){
            logger.info("bean不能为空");
            return ret;
        }
        try {
            // 参数转换成Map
            bean.convert();
            // 消息类型
            String cmdId = bean.getCmdId();
            if (Validator.isNull(cmdId)) {
                return ret;
            } else {
                cmdId = StringUtils.lowerCase(cmdId.substring(0, 1)).concat(cmdId.substring(1));
            }
            // 得到接口API对象
           // PnrApi api = new ChinaPnrApiImpl();
            Class<ChinaPnrApiImpl> c = ChinaPnrApiImpl.class;
            Object obj = api;
            // 取得该消息类型对应的bean
            Method method = c.getMethod(cmdId, PnrApiBean.class);
            Object retBean = method.invoke(obj, bean);
            if (retBean != null && retBean instanceof PnrApiBean) {
                PnrApiBean pnrApiBean = (PnrApiBean) retBean;
                // 调用汇付天下API接口
                String result = api.callChinaPnrApi(pnrApiBean);
                // 结果不为空时
                if (Validator.isNotNull(result)) {
                    JSONObject jo = JSONObject.parseObject(result);
                    return jo.toString();
                }
            }
        } catch (JSONException e1) {
            logger.debug("转换成JSON时失败");
        } catch (Exception e) {
            logger.error(ChinapnrController.class.getName(), methodName, e);
        } finally {
            logger.info("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        }
        return ret;
    }

}
