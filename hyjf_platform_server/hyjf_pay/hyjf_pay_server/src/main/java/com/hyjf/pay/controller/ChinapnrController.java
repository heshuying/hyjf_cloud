package com.hyjf.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.chinapnr.MerPriv;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.base.BaseController;
import com.hyjf.pay.config.SystemConfig;
import com.hyjf.pay.entity.ChinapnrExclusiveLog;
import com.hyjf.pay.entity.ChinapnrLog;
import com.hyjf.pay.lib.PnrApi;
import com.hyjf.pay.lib.PnrApiBean;
import com.hyjf.pay.lib.chinapnr.ChinaPnrApiImpl;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.hyjf.pay.lib.chinapnr.util.ChinapnrUtil;
import com.hyjf.pay.service.ChinapnrService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

@Controller
@RequestMapping(value = "/chinapnr")
public class ChinapnrController extends BaseController {
    Logger logger = LoggerFactory.getLogger(ChinapnrController.class);
    /**
     * THIS_CLASS
     */
    private static final String THIS_CLASS = ChinapnrController.class.getName();

    @Autowired
    ChinapnrService chinapnrService;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 调用接口(页面)
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/callapi")
    public ModelAndView callApi(@ModelAttribute ChinapnrBean bean) throws Exception {
        String methodName = "callApi";
        logger.info(THIS_CLASS, methodName, "[调用接口开始]");
        ModelAndView modelAndView = new ModelAndView("/chinapnr/chinapnr_send");
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
                        bean.setRetUrl(ChinapnrUtil.getBindRetUrl());
                    } else {
                        bean.setRetUrl(ChinapnrUtil.getRetUrl());
                    }
                    bean.set(ChinaPnrConstant.PARAM_RETURL, bean.getRetUrl());
                }
                //定向绑定用户不设置私有域
                if (!ChinaPnrConstant.CMDID_DIRECT_TRF_AUTH.equals(bean.get(ChinaPnrConstant.PARAM_CMDID))) {
                    ChinapnrUtil.setUUID(bean, id);
                }
                // 得到接口API对象
                PnrApi api = new ChinaPnrApiImpl();
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
                        modelAndView.setViewName(CommonConstant.JSP_CHINAPNR_RESULT);
                        modelAndView.addObject("content", "保存发送日志失败！");
                    }
                    // 跳转到汇付天下画面
                    pnrApiBean.setAction(systemConfig.getChinapnrUrl());
                    modelAndView.addObject(CommonConstant.CHINAPNR_FORM, pnrApiBean);
                }
            } else {
                modelAndView.setViewName(CommonConstant.JSP_CHINAPNR_RESULT);
                modelAndView.addObject("content", "保存发送日志失败！");
            }
        } catch (Exception e) {
            logger.error(THIS_CLASS, methodName, e);
            throw e;
        } finally {
            logger.info(THIS_CLASS, methodName, "[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        }
        return modelAndView;
    }

    /**
     * 交易完成后，本平台系统把交易结果通过页面方式，发送到该地址上
     *
     * @return
     */
    @PostMapping(value = "/return")
    public ModelAndView result(@ModelAttribute ChinapnrBean bean) {

        String methodName = "result";
        logger.info(THIS_CLASS, methodName, "[交易完成后,回调开始]");
        // 参数转换成Map
        bean.convert();
        // 验签成功时, 跳转到各功能模块的回调URL
        ModelAndView modelAndView = new ModelAndView(CommonConstant.JSP_CHINAPNR_SEND);
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
            e.printStackTrace();
        }
        // 验签
        PnrApi api = new ChinaPnrApiImpl();
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
            logger.debug(THIS_CLASS, methodName, "验证签名失败");
        } else {
            status = ChinaPnrConstant.STATUS_VERTIFY_OK;
            bean.set("chkValueStatus", status);
            remark = "验证签名成功";
            logger.debug(THIS_CLASS, methodName, "验证签名成功");
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
                e.printStackTrace();
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

        logger.info(THIS_CLASS, methodName, "[交易完成后,回调结束]");
        return modelAndView;
    }

    /**
     * 接收异步返回的消息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/callback")
    public String callBack(HttpServletRequest request, @ModelAttribute ChinapnrBean bean) {
        String methodName = "callBack";
        logger.info(THIS_CLASS, methodName, "[接收异步返回的消息开始, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
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
                    e.printStackTrace();
                }
            }
        }
        // 验签
        PnrApi api = new ChinaPnrApiImpl();
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
            logger.debug(THIS_CLASS, methodName, "验证签名失败");
            System.out.println("callback验签失败");
        } else {
            status = ChinaPnrConstant.STATUS_VERTIFY_OK;
            bean.set("chkValueStatus", status);
            remark = "验证签名成功";
            logger.debug(THIS_CLASS, methodName, "验证签名成功");
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
                e.printStackTrace();
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
                            // TODO: 2018/7/12 暂时没有，不处理
                            // 用户绑卡回调
                            if (ChinaPnrConstant.CMDID_USER_BIND_CARD.equals(bean.getCmdId())) {
                               // callBackUrl = ChinaPnrPropUtils.getSystem(CustomConstants.HYJF_WEB_URL) + "/bindCard/return";
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                        } else {
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
                                HttpDeal.post(callBackUrl, bean.getAllParams());
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
                            // 用户投资
                            else if (ChinaPnrConstant.CMDID_INITIATIVE_TENDER.equals(bean.getCmdId())) {
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                            // 用户解绑快捷卡
                            else if (ChinaPnrConstant.CMDID_BG_UNBIND_CARD.equals(bean.getCmdId())) {
                                HttpDeal.post(callBackUrl, bean.getAllParams());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
    public ModelAndView bindResult(@ModelAttribute ChinapnrBean bean) {
        // TODO: 2018/7/12 暂时没有，不处理
        String methodName = "result";
        logger.info(THIS_CLASS, methodName, "[交易完成后,回调开始, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        // 参数转换成Map
        bean.convert();
        ModelAndView modelAndView = new ModelAndView();
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
        PnrApi api = new ChinaPnrApiImpl();
        ChinapnrBean result = api.verifyChinaPnr(bean);
        bean.convert();
        // 检证失败
        if (Validator.isNull(result) || !result.isVerifyFlag()) {
            status = ChinaPnrConstant.STATUS_VERTIFY_NG;
            bean.set("chkValueStatus", status);
            logger.debug(THIS_CLASS, methodName, "验证签名失败");
        } else {
            status = ChinaPnrConstant.STATUS_VERTIFY_OK;
            bean.set("chkValueStatus", status);
            logger.debug(THIS_CLASS, methodName, "验证签名成功");
        }
        // 回调URL
        String callBackUrl = "";
      //  String callBackUrl = ChinaPnrPropUtils.getSystem(ChinaPnrConstant.PROP_WEB_HOST) + "/direct/bindUserReturn.do";
        // 验签成功时, 跳转到各功能模块的回调URL
        modelAndView = new ModelAndView(CommonConstant.JSP_CHINAPNR_SEND);
        if (result.isVerifyFlag() && Validator.isNotNull(callBackUrl)) {
            bean.setAction(callBackUrl);
        } else {
            modelAndView.setViewName(CommonConstant.JSP_CHINAPNR_RESULT);
            modelAndView.addObject("content", "验签失败!<br>" + bean.getAllParams().toString());
        }
        modelAndView.addObject(CommonConstant.CHINAPNR_FORM, bean);

        logger.info(THIS_CLASS, methodName, "[交易完成后,回调结束]");
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
    public String callApiBg(@RequestBody ChinapnrBean bean)  {
        String methodName = "callApiBg";
        logger.info(THIS_CLASS, methodName, "[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        String ret = "";
        String nowTime = GetDate.getServerDateTime(9, new Date());
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
              //  ChinapnrUtil.setUUID(bean, id);
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
                    e.printStackTrace();
                }
                 merPriv = bean.getMerPriv();
                // 得到接口API对象
                PnrApi api = new ChinaPnrApiImpl();
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
            logger.error(THIS_CLASS, methodName, e);
        } finally {
            logger.info(THIS_CLASS, methodName, "[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
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
    @RequestMapping(method = RequestMethod.POST, value = "/callapiajax")
    public String callApiAjax(@RequestBody ChinapnrBean bean) throws Exception {
        String methodName = "callApiAjax";
        logger.info(THIS_CLASS, methodName, "[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        String ret = "";
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
            PnrApi api = new ChinaPnrApiImpl();
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
            logger.debug(THIS_CLASS, methodName, "转换成JSON时失败");
        } catch (Exception e) {
            logger.error(ChinapnrController.class.getName(), methodName, e);
        } finally {
            logger.info(THIS_CLASS, methodName, "[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        }
        return ret;
    }

}
