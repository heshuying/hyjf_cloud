package com.hyjf.cs.user.bean;

import com.hyjf.common.validator.Validator;
import com.hyjf.soa.apiweb.CommonSoaUtils;

import java.util.HashMap;
import java.util.Map;

public class BindCardPageRequestBean extends BaseBean{

    private String retUrl;
    
    private String forgotPwdUrl;
    
    private String notifyUrl;

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }

    public String getForgotPwdUrl() {
        return forgotPwdUrl;
    }

    public void setForgotPwdUrl(String forgotPwdUrl) {
        this.forgotPwdUrl = forgotPwdUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
    
    public void doNotify(Map<String, String> params) {
        if(Validator.isNotNull(notifyUrl)){
            CommonSoaUtils.noRetPostThree(notifyUrl, params);
        }
    }
    
    public Map<String, String> getErrorMap(String status, String statusDesc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", this.getAccountId());
        params.put("status", status);
        params.put("statusDesc",statusDesc);
        params.put("acqRes",getAcqRes());
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        return params;
    }
}
