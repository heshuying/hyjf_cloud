package com.hyjf.cs.trade.bean;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.AemsErrorCodeConstant;
import com.hyjf.common.validator.Validator;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 受托支付授权页面请求参数
 * @author jijun 20180919
 */
public class AemsTrusteePayRequestBean extends BaseBean{

    @ApiModelProperty(value = "标的编号")
    private String productId;

    @ApiModelProperty(value = "借款人证件类型 01-身份证（18位）")
    private String idType;

    @ApiModelProperty(value = "借款人证件号码")
    private String idNo;

    @ApiModelProperty(value = "收款人电子帐户")
    private String receiptAccountId;

    @ApiModelProperty(value = "异步地址")
    private String notifyUrl;

    @ApiModelProperty(value = "授权状态")
    private String state;

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getIdType() {
        return idType;
    }
    public void setIdType(String idType) {
        this.idType = idType;
    }
    public String getIdNo() {
        return idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    public String getReceiptAccountId() {
        return receiptAccountId;
    }
    public void setReceiptAccountId(String receiptAccountId) {
        this.receiptAccountId = receiptAccountId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    /**
     * 
     * 检查参数是否为空
     * @author sss
     * @return
     */
    public boolean checkParmIsNull() {
        boolean result = false;
        if (Validator.isNull(getTimestamp())) {
            return true;
        }
        if (Validator.isNull(getInstCode())) {
            return true;
        }
        if (Validator.isNull(getChkValue())) {
            return true;
        }
        if (Validator.isNull(getRetUrl())) {
            return true;
        }
        if (Validator.isNull(getNotifyUrl())) {
            return true;
        }
        if (Validator.isNull(getChannel())) {
            return true;
        }
        if (Validator.isNull(getAccountId())) {
            return true;
        }
        if (Validator.isNull(getProductId())) {
            return true;
        }
        if (Validator.isNull(getIdType())) {
            return true;
        }
        if (Validator.isNull(getIdNo())) {
            return true;
        }
        if (Validator.isNull(getReceiptAccountId())) {
            return true;
        }
        if (Validator.isNull(getForgotPwdUrl())) {
            return true;
        }
        return result;
    }
    
    public void doNotify(Map<String, String> params) {
        if(Validator.isNotNull(notifyUrl)){
            CommonSoaUtils.noRetPostThree(notifyUrl, params);
        }
    }
    
    public Map<String, String> getErrorMap(String status, String statusDesc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", getAccountId());
        params.put("productId", productId);
        params.put("status", status);
        params.put("statusDesc",statusDesc);
        params.put("acqRes",getAcqRes());
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        return params;
    }
    
    // 检查查询接口参数是否为空
    public boolean checkParmQueryIsNull() {
        boolean result = false;
        if (Validator.isNull(getTimestamp())) {
            return true;
        }
        if (Validator.isNull(getInstCode())) {
            return true;
        }
        if (Validator.isNull(getChkValue())) {
            return true;
        }
        if (Validator.isNull(getChannel())) {
            return true;
        }
        if (Validator.isNull(getAccountId())) {
            return true;
        }
        if (Validator.isNull(productId)) {
            return true;
        }
        return result;
    }
    
    public JSONObject getErrorJson(String status , String statusDesc) {
        Map<String, String> error = getErrorMap(status,statusDesc);
        JSONObject result = new JSONObject();
        result.putAll(error);
        return result;
    }
    
    public JSONObject getSuccessJson(String statusDesc) {
        Map<String, String> error = getSuccessMap(statusDesc);
        JSONObject result = new JSONObject();
        result.putAll(error);
        return result;
    }
    
    public Map<String, String> getSuccessMap(String statusDesc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", getAccountId());
        params.put("productId", productId);
        params.put("status", AemsErrorCodeConstant.SUCCESS);
        params.put("state", state);
        params.put("statusDesc",statusDesc);
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
        params.put("chkValue", resultBean.getChkValue());
        return params;
    }
    
}
