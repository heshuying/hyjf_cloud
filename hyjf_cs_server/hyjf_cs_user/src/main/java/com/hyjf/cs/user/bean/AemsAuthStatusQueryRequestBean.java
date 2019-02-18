package com.hyjf.cs.user.bean;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.AemsErrorCodeConstant;
import com.hyjf.common.validator.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @version AemsAuthStatusQueryRequestBean, v0.1 2018/12/6 10:30
 * @Author: Zha Daojian
 */
public class AemsAuthStatusQueryRequestBean extends BaseBean {

    /**
     *检查参数是否为空
     * @author Zha Daojian
     * @date 2018/12/6 10:31
     * @param
     * @return boolean
     **/
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
        if (Validator.isNull(getAccountId())) {
            return true;
        }

        return result;
    }

    public Map<String, String> getErrorMap(String status, String statusDesc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", getAccountId());
        params.put("status", status);
        params.put("statusDesc",statusDesc);
        params.put("acqRes",getAcqRes());
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        return params;
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

    // 这个需要新增参数
    public Map<String, String> getSuccessMap(String statusDesc) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", getAccountId());
        params.put("status", AemsErrorCodeConstant.SUCCESS);
        params.put("statusDesc",statusDesc);
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
        params.put("chkValue", resultBean.getChkValue());
        return params;
    }
}
