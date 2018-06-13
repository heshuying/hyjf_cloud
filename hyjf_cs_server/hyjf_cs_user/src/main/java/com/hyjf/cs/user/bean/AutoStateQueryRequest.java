package com.hyjf.cs.user.bean;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.util.ErrorCodeConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 受托支付授权页面请求参数
 */
public class AutoStateQueryRequest extends BaseBean{


    private String channel; //交易渠道
    private String accountId; //电子账户号

    @Override
    public String getChannel() {
        return channel;
    }
    @Override
    public void setChannel(String channel) {
        this.channel = channel;
    }
    @Override
    public String getAccountId() {
        return accountId;
    }
    @Override
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     *
     * 检查参数是否为空
     * @author
     * @param
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
        if (Validator.isNull(accountId)) {
            return true;
        }

        return result;
    }

    public Map<String, String> getErrorMap(String status, String statusDesc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId);
        params.put("status", status);
        params.put("statusDesc",statusDesc);
        params.put("acqRes",getAcqRes());
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        return params;
    }

    public JSONObject getErrorJson(String status ,String statusDesc) {
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

    // 这个需要新增参数
    public Map<String, String> getSuccessMap(String statusDesc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId);
        params.put("status", ErrorCodeConstant.SUCCESS);
        params.put("statusDesc",statusDesc);
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
        params.put("chkValue", resultBean.getChkValue());
        return params;
    }
}
