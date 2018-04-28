package com.hyjf.pay.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.PropUtils;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.base.BaseController;
import com.hyjf.pay.bean.BankCallDefine;
import com.hyjf.pay.config.SystemConfig;
import com.hyjf.pay.entity.ChinapnrExclusiveLog;
import com.hyjf.pay.lib.PnrApiBean;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallPnrApiBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.call.BankCallApi;
import com.hyjf.pay.lib.bank.call.impl.BankCallApiImpl;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.pay.service.BankPayLogService;

@Controller
@RequestMapping(value = "/bankcall")
public class BankCallController extends BaseController {
    Logger _log = LoggerFactory.getLogger(BankCallController.class);

    @Autowired
    BankPayLogService payLogService;

    @Autowired
    private BankCallApi api;
    
    @Autowired
    SystemConfig systemConfig;

    /**
     * 调用接口(页面)
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @PostMapping(value = "callApiPage.json")
    @ResponseBody
    public Map<String,Object> callPageApi(@ModelAttribute BankCallBean bean) throws Exception {

        String methodName = "callPageApi";
        _log.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
//        ModelAndView modelAndView = new ModelAndView(BankCallDefine.JSP_BANK_SEND);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            // 参数转换成Map
            bean.convert();
            String txCode = bean.getTxCode();
            if (Validator.isNull(txCode)) {
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
            if (Validator.isNotNull(bean.getRetUrl())) {
                String retUrl =  systemConfig.getReturnUrl() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId();
                bean.setRetUrl(retUrl);
                bean.set(BankCallConstant.PARAM_RETURL, retUrl);
            }
            if (Validator.isNotNull(bean.getNotifyUrl())) {
                String notifyUrl = systemConfig.callbackUrl + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId();
                bean.setNotifyUrl(notifyUrl);
                bean.set(BankCallConstant.PARAM_NOTIFYURL, notifyUrl);
            }
            if (Validator.isNotNull(bean.getSuccessfulUrl())) {
                String successfulUrl = systemConfig.getReturnUrl() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
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
                String sendLogFlag = payLogService.insertChinapnrSendLog(bean, bean);
                if (StringUtils.isNotBlank(sendLogFlag)) {
                    // 跳转到汇付天下画面
                    bean.setAction(PropUtils.getSystem(BankCallConstant.BANK_PAGE_URL) + bean.getLogBankDetailUrl());
                    resultMap.put(BankCallDefine.BANK_FORM, bean);
                } else {
                    throw new RuntimeException("页面调用前,保存请求数据失败！订单号：" + bean.getLogOrderId());
                }
            }
        } catch (Exception e) {
            _log.error(String.valueOf(e));
            throw e;
        } finally {
           _log.info("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
        }
        return resultMap;
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
            _log.info("交易完成后,回调开始,消息类型:[" + (bean == null ? "" : bean.getTxCode()) + "],订单号:[" + logOrderId + "],用户ID:[" + logUserId + "].");
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
//			boolean chinapnrLogFlag = this.bankCallService.insertChinapnrLog(bean, 0);
//			if (!chinapnrLogFlag) {
//				throw new RuntimeException("同步回调后,保存回调数据失败！订单号：" + bean.getLogOrderId());
//			}
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
            BankCallApi api = new BankCallApiImpl();
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
                _log.debug("验证签名失败");
                _log.info("验签失败,订单号:[" + bean.getLogOrderId() + "]");
            } else {
                status = BankCallConstant.STATUS_VERTIFY_OK;
                bean.setLogOrderStatus(status);
                _log.debug("验证签名成功");
            }
            // 查询相应的订单记录
            if (Validator.isNotNull(logOrderId)) {
                // 获取主键
                ChinapnrExclusiveLog record = this.payLogService.selectChinapnrExclusiveLogByOrderId(logOrderId);
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
                    if (isSuccess != null && isSuccess.equals("1")) {
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
                        _log.info("同步回调跳转,callBeckUrl:[" + bean.getAction() + "].");
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
                ChinapnrExclusiveLog record = payLogService.selectChinapnrExclusiveLogByOrderId(logOrderId);
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
                    if (isSuccess != null && isSuccess.equals("1")) {
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
                    _log.info("同步回调,callBackUrl:[" + bean.getAction() + "]");
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
        _log.info("[交易完成后,回调结束]");
        return modelAndView;
    }

    /**
     * 接收页面异步返回的消息
     *
     * @return
     */
    @PostMapping(value = "callPageBack")
    public void callPageBack(HttpServletRequest request, HttpServletResponse response, @ModelAttribute BankCallBean bean) {

        String methodName = "callPageBack";
        String bgData = request.getParameter("bgData");
        _log.info("接收异步返回的消息开始,订单号:【" + bean.getLogOrderId() + "】,用户ID:【" + bean.getLogUserId() + "】.");
        _log.debug("消息内容=【" + bgData + "】");

        Map<String, String> mapParam;
        try {
            mapParam = new ObjectMapper().readValue(bgData, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        //先验签
        BankCallApi api = new BankCallApiImpl();
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
//			BankCallApi api = new BankCallApiImpl();
//			BankCallBean result = api.verifyChinaPnr(bean);
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
                _log.debug("验证签名失败");
                _log.info("验签失败,订单号:[" + bean.getLogOrderId() + "].");
            } else {
                status = BankCallConstant.STATUS_VERTIFY_OK;
                bean.setLogOrderStatus(status);
                _log.debug("验证签名成功");
            }
            // 真实的返回URL
            String notifyUrl = "";
            String retUrl = "";
            // 返回参数中含有logOrderId时, 更新状态记录
            if (Validator.isNotNull(logOrderId)) {
                // 取得检证数据
                ChinapnrExclusiveLog record = payLogService.selectChinapnrExclusiveLogByOrderId(logOrderId);
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
                                content = HttpDeal.post(notifyUrl, bean.getAllParams());
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
                    _log.info("[接收异步返回的消息结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
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
    public String callApiBg(HttpServletRequest request, @ModelAttribute BankCallBean bean) throws Exception {

        String methodName = "callApiBg";
        _log.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
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
                String notifyURL = systemConfig.getCallbackUrl() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId() + StringPool.AMPERSAND + BankCallConstant.PARAM_LOGNOTIFYURLTYPE + StringPool.EQUAL
                        + BankCallConstant.PARAM_LOGNOTIFYURL;
                bean.setNotifyURL(notifyURL);
                bean.set(BankCallConstant.PARAM_NOTIFY_URL, notifyURL);
            }
            if (Validator.isNotNull(bean.getRetNotifyURL())) {
                String retNotifyURL = systemConfig.getCallbackUrl() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId() + StringPool.AMPERSAND + BankCallConstant.PARAM_LOGNOTIFYURLTYPE + StringPool.EQUAL
                        + BankCallConstant.PARAM_LOGRETNOTIFYURL;
                bean.setRetNotifyURL(retNotifyURL);
                bean.set(BankCallConstant.PARAM_RETNOTIFY_URL, retNotifyURL);
            }

            // 填充bean对象
            api.rebuildBean(bean);

            {
                // 发送前插入日志记录
                this.payLogService.insertChinapnrSendLog(bean, bean);
                // 调用汇付天下API接口
                String result = api.callChinaPnrApi(bean);

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
//				BankCallBean verifyResult = api.verifyChinaPnr(backBean);
                backBean.convert();
                // 检证失败
                if (Validator.isNull(verifyResult) || !verifyResult.isLogVerifyFlag()) {
                    status = BankCallConstant.STATUS_VERTIFY_NG;
                    bean.setLogOrderStatus(status);
                    _log.debug("验证签名失败");
                } else {
                    status = BankCallConstant.STATUS_VERTIFY_OK;
                    bean.setLogOrderStatus(status);
                    _log.debug("验证签名成功");

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
            _log.error("订单号：" + logOrderId, e);
        } finally {
            _log.info("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
        }
        return ret;
    }

    /**
     * 调用接口(AJAX)
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping(value = "callApiAjax")
    public String callApiAjax(@RequestBody BankCallBean bean) throws Exception {
        String methodName = "callApiAjax";
        _log.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
        String ret = "";
        try {
            // 参数转换成Map
            bean.convert();
            // 消息类型
            String txCode = bean.getTxCode();
            if (Validator.isNull(txCode)) {
                throw new RuntimeException("消息类型不能为空");
            }
            // 设置返回URL
            if (Validator.isNotNull(bean.getNotifyURL())) {
                bean.setNotifyURL(BankCallUtils.getApiNotifyURL() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGNOTIFYURLTYPE + StringPool.EQUAL + BankCallConstant.PARAM_LOGNOTIFYURL);
                bean.set(BankCallConstant.PARAM_NOTIFY_URL, bean.getNotifyURL());
            }
            if (Validator.isNotNull(bean.getRetNotifyURL())) {
                bean.setRetNotifyURL(BankCallUtils.getApiNotifyURL() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGNOTIFYURLTYPE + StringPool.EQUAL + BankCallConstant.PARAM_LOGRETNOTIFYURL);
                bean.set(BankCallConstant.PARAM_RETNOTIFY_URL, bean.getRetNotifyURL());
            }
            // 得到接口API对象
            BankCallApi api = new BankCallApiImpl();
            Class<BankCallApiImpl> c = BankCallApiImpl.class;
            Object obj = api;
            // 取得该消息类型对应的bean
            Method method = c.getMethod(txCode, BankCallPnrApiBean.class);
            Object retBean = method.invoke(obj, bean);
            if (retBean != null && retBean instanceof PnrApiBean) {
                BankCallPnrApiBean pnrApiBean = (BankCallPnrApiBean) retBean;
                // 调用汇付天下API接口
                String result = api.callChinaPnrApi(pnrApiBean);
                // 结果不为空时
                if (Validator.isNotNull(result)) {
                    JSONObject jo = JSONObject.parseObject(result);
                    return jo.toString();
                }
            }
        } catch (JSONException e1) {
            _log.debug("转换成JSON时失败");
        } catch (Exception e) {
            _log.error(String.valueOf(e));
        } finally {
            _log.info("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
        }
        return ret;
    }

    /**
     * 接收联机异步返回的消息
     *
     * @return
     */
    @PostMapping(value = "callApiReturn")
    public void callBack(HttpServletRequest request, HttpServletResponse response, @ModelAttribute BankCallBean bean) {

        String methodName = "callBack";
        String bgData = request.getParameter("bgData");
        String logOrderId = request.getParameter(BankCallConstant.PARAM_LOGORDERID);
        String logUserId = request.getParameter(BankCallConstant.PARAM_LOGUSERID);
        String logNotifyType = request.getParameter(BankCallConstant.PARAM_LOGNOTIFYURLTYPE);
        String content = "";
        if (StringUtils.isNotBlank(bgData)) {
            bean = JSONObject.parseObject(bgData, BankCallBean.class);
            _log.info("[接收异步返回的消息开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
            _log.info("-接收异步返回的消息开始, 消息类型:[" + (bean == null ? "" : bean.getTxCode()) + "],订单号:[" + logOrderId + "],用户ID:[" + logUserId + "].");
            // 判断返回参数是否为空
            if (Validator.isNull(bean)) {
                throw new RuntimeException("接收的同步返回参数为空，调用银行接口失败");
            } else if (StringUtils.isBlank(bean.getTxCode())) {
                throw new RuntimeException("接收的同步返回消息类型为空，调用银行接口失败");
            }
            // 参数转换成Map
            bean.convert();
            // 输出debug日志
            _log.debug("参数: " + bean == null ? "无" : bean.getAllParams() + "]");
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
            BankCallApi api = new BankCallApiImpl();
            BankCallBean result = api.verifyChinaPnr(bean);
            bean.convert();
            bean.setLogOrderId(logOrderId);
            bean.setLogUserId(logUserId);
            // 检证失败
            if (Validator.isNull(result) || !result.isLogVerifyFlag()) {
                status = BankCallConstant.STATUS_VERTIFY_NG;
                bean.setLogOrderStatus(status);
                _log.debug("验证签名失败");
                _log.info("验签失败,订单号:" + bean.getLogOrderId() + "]");
            } else {
                status = BankCallConstant.STATUS_VERTIFY_OK;
                bean.setLogOrderStatus(status);
                _log.debug("验证签名成功");
            }
            // 真实的返回URL
            String notifyUrl = "";
            // 返回参数中含有logOrderId时, 更新状态记录
            if (Validator.isNotNull(logOrderId)) {
                // 取得检证数据
                ChinapnrExclusiveLog record = payLogService.selectChinapnrExclusiveLogByOrderId(logOrderId);
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
                                content = HttpDeal.post(notifyUrl, bean.getAllParams());
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
                    _log.info("[接收异步返回的消息结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "]");
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
     * 页面接收同步返回消息
     *
     * @return
     */
    @PostMapping(value = "pageForgotPWD")
    public ModelAndView pageForgotPWD(HttpServletRequest request, @ModelAttribute BankCallBean bean) {

        String methodName = "pageForgotPWD";
        String logOrderId = request.getParameter("logOrderId");
        // 真实的返回URL
        String retUrl = "";
        // 验签成功时, 跳转到各功能模块的回调URL
        ModelAndView modelAndView = new ModelAndView(BankCallDefine.JSP_BANK_SEND);
        if (StringUtils.isNotBlank(logOrderId)) {
            ChinapnrExclusiveLog record = this.payLogService.selectChinapnrExclusiveLogByOrderId(logOrderId);
            if (record != null) {
                bean.setLogOrderId(logOrderId);
                bean.setLogClient(record.getClient());
                bean.setLogUserId(record.getCreateuser());
                JSONObject jo = new JSONObject();
                try {
                    jo = JSONObject.parseObject(record.getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                retUrl = jo.getString(BankCallConstant.PARAM_FORGOTPWDURL);
                bean.setRetUrl(retUrl);
                bean.setAction(retUrl);
                if (null != bean.getAllParams().get(BankCallConstant.PARAM_ACQRES)) {
                    bean.getAllParams().put(BankCallConstant.PARAM_ACQRES, bean.getAllParams().get(BankCallConstant.PARAM_ACQRES));
                }
                _log.info("同步回调跳转,callBackUrl:[" + bean.getAction() + "].");
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
        _log.info("[交易完成后,回调结束]");
        return modelAndView;
    }

    /**
     * 调用接口(后台)给查询用，去掉日志表
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "callApiBgForQuery")
    public String callApiBgForQuery(HttpServletRequest request, @ModelAttribute BankCallBean bean) throws Exception {

        _log.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "] 订单号: " + bean.getLogOrderId());
        String ret = "";
        String logOrderId = bean.getLogOrderId();
//		_log.info("订单号: "+bean.getLogOrderId()+" ,请求参数：" + JSONObject.toJSONString(bean));
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
//			Long exclusiveId = bankCallService.insertChinapnrExclusiveLog(bean);
//			if (exclusiveId == null || exclusiveId <= 0) {
//				throw new RuntimeException("接口调用前,保存请求数据失败！订单号：" + bean.getLogOrderId());
//			}
            if (Validator.isNotNull(bean.getNotifyURL())) {
                String notifyURL = BankCallUtils.getApiNotifyURL() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId() + StringPool.AMPERSAND + BankCallConstant.PARAM_LOGNOTIFYURLTYPE + StringPool.EQUAL
                        + BankCallConstant.PARAM_LOGNOTIFYURL;
                bean.setNotifyURL(notifyURL);
                bean.set(BankCallConstant.PARAM_NOTIFY_URL, notifyURL);
            }
            if (Validator.isNotNull(bean.getRetNotifyURL())) {
                String retNotifyURL = BankCallUtils.getApiNotifyURL() + StringPool.QUESTION + BankCallConstant.PARAM_LOGORDERID + StringPool.EQUAL + bean.getLogOrderId() + StringPool.AMPERSAND
                        + BankCallConstant.PARAM_LOGUSERID + StringPool.EQUAL + bean.getLogUserId() + StringPool.AMPERSAND + BankCallConstant.PARAM_LOGNOTIFYURLTYPE + StringPool.EQUAL
                        + BankCallConstant.PARAM_LOGRETNOTIFYURL;
                bean.setRetNotifyURL(retNotifyURL);
                bean.set(BankCallConstant.PARAM_RETNOTIFY_URL, retNotifyURL);
            }
            // 得到接口API对象
            BankCallApi api = new BankCallApiImpl();
            Class<BankCallApiImpl> c = BankCallApiImpl.class;
            Object obj = api;
            // 取得该消息类型对应的bean
            Method method = c.getMethod(txCode, BankCallPnrApiBean.class);
            Object retBean = method.invoke(obj, bean);
            if (retBean != null && retBean instanceof BankCallPnrApiBean) {
                BankCallPnrApiBean pnrApiBean = (BankCallPnrApiBean) retBean;
                // 发送前插入日志记录
//				boolean sendLogFlag = bankCallService.insertChinapnrSendLog(pnrApiBean, bean);
//				if (!sendLogFlag) {
//					throw new RuntimeException("接口调用前,保存请求数据失败！订单号：" + bean.getLogOrderId());
//				}
//				_log.info("订单号: "+bean.getLogOrderId()+" ,发送请求参数:  "+pnrApiBean.getJson());
                // 调用汇付天下API接口
                String result = api.callChinaPnrApi(pnrApiBean);
                BankCallBean backBean = (BankCallBean) JSONObject.parseObject(result, BankCallBean.class);
                // 实体转化
                backBean.convert();
                backBean.setLogOrderId(bean.getLogOrderId());
                backBean.setLogUserId(bean.getLogUserId());
                backBean.setTxDate(StringUtil.isBlank(backBean.getTxDate()) ? bean.getTxDate() : backBean.getTxDate());
                backBean.setTxTime(StringUtil.isBlank(backBean.getTxTime()) ? bean.getTxTime() : backBean.getTxTime());
                backBean.setSeqNo(StringUtil.isBlank(backBean.getSeqNo()) ? bean.getSeqNo() : backBean.getSeqNo());
                backBean.setTxCode(StringUtil.isBlank(backBean.getTxCode()) ? bean.getTxCode() : backBean.getTxCode());
                // 插入返回值
//				boolean chinapnrLogFlag = bankCallService.insertChinapnrLog(backBean, 2);
//				if (!chinapnrLogFlag) {
//					throw new RuntimeException("接口调用后,保存回调数据失败！订单号：" + backBean.getLogOrderId());
//				}
                _log.info("订单号: " + bean.getLogOrderId() + " ,返回 :  " + backBean.getRetCode());

                // 发送状态(1:处理中)
                String status = BankCallConstant.STATUS_SENDING;
                BankCallBean verifyResult = api.verifyChinaPnr(backBean);
                backBean.convert();
                // 检证失败
                if (Validator.isNull(verifyResult) || !verifyResult.isLogVerifyFlag()) {
                    status = BankCallConstant.STATUS_VERTIFY_NG;
                    bean.setLogOrderStatus(status);
                    _log.info("result验签失败,订单号：" + bean.getLogOrderId());
                } else {
                    status = BankCallConstant.STATUS_VERTIFY_OK;
                    bean.setLogOrderStatus(status);
//					LogUtil.debugLog(BankCallDefine.CONTROLLOR_CLASS_NAME, methodName, "验证签名成功");
                }
                // 返回参数中含有logOrderId时, 更新状态记录
                if (Validator.isNotNull(logOrderId)) {
                    // 取得检证数据
//					ChinapnrExclusiveLogWithBLOBs record = bankCallService.selectChinapnrExclusiveLogByOrderId(logOrderId);
//					if(null == record){
//						_log.info("ChinapnrExclusiveLog表查询不到数据，logOrderId："+logOrderId+"，即信接口："+bean.getTxCode()+",电子账号："+bean.getAccountId());
//					}
//					backBean.setLogOrderId(bean.getLogOrderId());
//					backBean.setLogUserId(bean.getLogUserId());
//					backBean.setLogClient(bean.getLogClient());
//					boolean exclusiveLogUpdateFlag = bankCallService.updateChinapnrExclusiveLog(exclusiveId, backBean, nowTime);
//					if (!exclusiveLogUpdateFlag) {
//						throw new RuntimeException("接口调用后,更新exclusivelog数据失败！订单号：" + bean.getLogOrderId());
//					}
                    // 验签成功时
                    if (verifyResult.isLogVerifyFlag()) {
                        bean.getAllParams().remove(BankCallConstant.PARAM_SIGN);
                        bean.getAllParams().remove(BankCallConstant.PARAM_VERSION);
                        bean.setSign(null);
                        bean.setVersion(null);
                        ret = JSONObject.toJSONString(backBean, true);
                        return ret;
                    } else {
                        // 更新数据状态为验签失败
//						boolean updateExclusiveLogFlag = this.bankCallService.updateChinapnrExclusiveLog(exclusiveId, 3);
//						if (!updateExclusiveLogFlag) {
//							throw new RuntimeException("异步回调成功后,验签失败失败！订单号：" + bean.getLogOrderId());
//						}

                        _log.info("异步回调成功后,验签失败失败！订单号：" + bean.getLogOrderId());
                    }
                }
            }
        } catch (Exception e) {
            _log.error("订单号：" + logOrderId, e);
        } finally {
            _log.info("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getTxCode()) + "] 订单号: " + bean.getLogOrderId());
        }
        return ret;
    }

}