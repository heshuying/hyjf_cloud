/**
 * 汇盈金服调用银行存管接口
 */
package com.hyjf.pay.call.impl;

import com.hyjf.common.http.HttpDealBank;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.call.BankCallApi;
import com.hyjf.pay.call.util.BankCallSignUtils_;
import com.hyjf.pay.config.SystemConfig;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallPnrApiBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BankCallApiImpl implements BankCallApi {
    private static Logger log = LoggerFactory.getLogger(BankCallApiImpl.class);
    
    @Autowired
    BankCallSignUtils_ bankCallSignUtils_;

    /**
     * 版本号
     */
    private String _version;

    /**
     * 银行编码
     */
    private String _bankcode;

    /**
     * 平台机构代码
     */
    private String _instCode;

    /**
     * 交易渠道
     */
    private String _coinstChannel;
    
    private SystemConfig _systemConfig;

    public BankCallApiImpl(@Qualifier("systemConfig") SystemConfig systemConfig) {

        // 银行代码
        if (Validator.isNull(_bankcode)) {
            _bankcode = systemConfig.getBankCode();
        }
        // 交易渠道
        if (Validator.isNull(_coinstChannel)) {
            _coinstChannel = systemConfig.getBankChannel();
        }
        // 接口默认版本号
        if (Validator.isNull(_version)) {
            _version = systemConfig.getBankVersion();
        }
        // 平台机构代码
        if (Validator.isNull(_instCode)) {
            _instCode = systemConfig.getBankInstCode();
        }
        
        _systemConfig = systemConfig;
    }

    /**
     * 调用API接口
     *
     * @param bean
     * @return
     */
    @Override
    public String callChinaPnrApi(BankCallPnrApiBean bean) {

        // 方法名
        log.info("[调用API接口开始]");
        log.debug("参数: " + bean == null ? "无" : bean.getAllParams() + "]");
        String result = null;
        // 未签名时,进行签名
        if (Validator.isNull(bean.get(BankCallConstant.PARAM_SIGN))) {
            bean.set(BankCallConstant.PARAM_SIGN, this.generateSignCall(bean));
        }
        // 签名为空时,返回空字符串
        if (Validator.isNull(bean.get(BankCallConstant.PARAM_SIGN))) {
            return result;
        }
        try {
            // 发送请求
            String HTTP_URL = _systemConfig.getBankOnlineUrl();
            result = HttpDealBank.post(HTTP_URL, bean.getAllParams());
            log.info( "[返回结果:" + result + "]");
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        log.debug("[调用API接口结束]");
        return result;
    }

    @Override
    public BankCallBean verifyChinaPnr(BankCallBean bean) {
        // 方法名
        log.info("[验证银行存管签名开始]");
        log.debug("参数: " + bean == null ? "无" : bean.getAllParams() + "]");
        BankCallBean ret = new BankCallBean();
        String result = BankCallConstant.RECEIVED_FAIL;
        try {
            String checkValue = bankCallSignUtils_.mergeMap(bean.getAllParams());
            // RSA方式验签
            boolean res = bankCallSignUtils_.verify(bean.get(BankCallConstant.PARAM_SIGN), checkValue);
            ret.setLogVerifyFlag(res);
            if (res) {
                result = BankCallConstant.RECEIVED_SUCCESS;
                ret.setLogVerifyResult(result);
            }
            log.debug("[RSA方式验签结果:" + result + "]");
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        log.debug("[验证签名结束]");
        return ret;
    }

    @Override
    public BankCallBean verifyChinaPnr(Map<String, String> mapParam) {
        // 方法名
        log.info("[验证银行存管签名开始]");
        log.debug("参数: " + mapParam == null ? "无" : mapParam + "]");
        BankCallBean ret = new BankCallBean();
        String result = BankCallConstant.RECEIVED_FAIL;
        try {
            String checkValue = bankCallSignUtils_.mergeMap(mapParam);
            // RSA方式验签
            boolean res = bankCallSignUtils_.verify(String.valueOf(mapParam.get(BankCallConstant.PARAM_SIGN)), checkValue);
            ret.setLogVerifyFlag(res);
            if (res) {
                result = BankCallConstant.RECEIVED_SUCCESS;
                ret.setLogVerifyResult(result);
            }
            log.debug("[RSA方式验签结果:" + result + "]");
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        log.debug("[验证签名结束]");
        return ret;
    }

    private String generateSignCall(BankCallPnrApiBean bean) {
        String mergedStr = bankCallSignUtils_.mergeMap(bean.getAllParams());
        return bankCallSignUtils_.sign(mergedStr);
    }


    /**
     * 设置共通项目(版本号,商户客户号)
     *
     * @param bean
     */
    private void setCommonItems(BankCallPnrApiBean bean) {
        // 版本号
        if (!bean.getAllParams().containsKey(BankCallConstant.PARAM_VERSION)) {
            bean.set(BankCallConstant.PARAM_VERSION, _version);
        }
        // 银行代码
        if (!bean.getAllParams().containsKey(BankCallConstant.PARAM_BANKCODE)) {
            bean.set(BankCallConstant.PARAM_BANKCODE, _bankcode);
        }
        // 平台机构代码
        if (!bean.getAllParams().containsKey(BankCallConstant.PARAM_INSTCODE)) {
            bean.set(BankCallConstant.PARAM_INSTCODE, _instCode);
        }
        // 交易渠道
        if (!bean.getAllParams().containsKey(BankCallConstant.PARAM_CHANNEL)) {
            bean.set(BankCallConstant.PARAM_CHANNEL, _coinstChannel);
        }
        // 交易日期
        if (!bean.getAllParams().containsKey(BankCallConstant.PARAM_TXDATE)) {
            bean.set(BankCallConstant.PARAM_TXDATE, GetOrderIdUtils.getTxDate());
        }
        // 交易时间
        if (!bean.getAllParams().containsKey(BankCallConstant.PARAM_TXTIME)) {
            bean.set(BankCallConstant.PARAM_TXTIME, GetOrderIdUtils.getTxTime());
        }
        // 交易流水号
        if (!bean.getAllParams().containsKey(BankCallConstant.PARAM_SEQNO)) {
            bean.set(BankCallConstant.PARAM_SEQNO, GetOrderIdUtils.getSeqNo(6));
        }
    }

    /**
     * 用户开户
     *
     * @param bean
     * @return
     */
    @Override
    public void rebuildBean(BankCallBean bean) {
        // 设置共通项目(版本号,商户客户号)
        setCommonItems(bean);
        // 加签
        String sign = generateSignCall(bean);
        bean.set(BankCallConstant.PARAM_SIGN, sign);
    }

}
