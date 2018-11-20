package com.hyjf.cs.user.bean;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class TrusteePayRequestBean extends BaseBean {
    @ApiModelProperty(value = "交易渠道")
    private String channel;
    @ApiModelProperty(value = "电子账户号")
    private String accountId;
    @ApiModelProperty(value = "标的编号")
    private String productId;
    @ApiModelProperty(value = "借款人证件类型 01-身份证（18位）")
    private String idType;
    @ApiModelProperty(value = "借款人证件号码")
    private String idNo;
    @ApiModelProperty(value = "收款人电子帐户")
    private String receiptAccountId;
    @ApiModelProperty(value = "忘记交易密码链接")
    private String forgotPwdUrl;
    @ApiModelProperty(value = "同步地址")
    private String retUrl;
    @ApiModelProperty(value = "异步地址")
    private String notifyUrl;
    @ApiModelProperty(value = "授权状态")
    private String state;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

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

    public String getForgotPwdUrl() {
        return forgotPwdUrl;
    }

    public void setForgotPwdUrl(String forgotPwdUrl) {
        this.forgotPwdUrl = forgotPwdUrl;
    }

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
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
     * 检查参数是否为空
     *
     * @param modelAndView
     * @return
     * @author sss
     */
    public boolean checkParmIsNull(ModelAndView modelAndView) {
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
        if (Validator.isNull(retUrl)) {
            return true;
        }
        if (Validator.isNull(notifyUrl)) {
            return true;
        }
        if (Validator.isNull(channel)) {
            return true;
        }
        if (Validator.isNull(accountId)) {
            return true;
        }
        if (Validator.isNull(productId)) {
            return true;
        }
        if (Validator.isNull(idType)) {
            return true;
        }
        if (Validator.isNull(idNo)) {
            return true;
        }
        if (Validator.isNull(receiptAccountId)) {
            return true;
        }
        if (Validator.isNull(forgotPwdUrl)) {
            return true;
        }
        if (Validator.isNull(retUrl)) {
            return true;
        }
        if (Validator.isNull(notifyUrl)) {
            return true;
        }

        return result;
    }

    public void doNotify(Map<String, String> params) {
        if (Validator.isNotNull(notifyUrl)) {
            CommonSoaUtils.noRetPostThree(notifyUrl, params);
        }
    }

    public Map<String, String> getErrorMap(String status, String statusDesc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId);
        params.put("productId", productId);
        params.put("status", status);
        params.put("statusDesc", statusDesc);
        params.put("acqRes", getAcqRes());
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
        if (Validator.isNull(channel)) {
            return true;
        }
        if (Validator.isNull(accountId)) {
            return true;
        }
        if (Validator.isNull(productId)) {
            return true;
        }
        return result;
    }

    public JSONObject getErrorJson(String status, String statusDesc) {
        Map<String, String> error = getErrorMap(status, statusDesc);
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
        params.put("accountId", accountId);
        params.put("productId", productId);
        params.put("status", ErrorCodeConstant.SUCCESS);
        params.put("state", state);
        params.put("statusDesc", statusDesc);
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
        params.put("chkValue", resultBean.getChkValue());
        return params;
    }

}
