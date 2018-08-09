/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.SubCommissionVO;

/**
 * @author: sunpeikai
 * @version: SubCommissionResponse, v0.1 2018/7/10 10:03
 */
public class SubCommissionResponse extends Response<SubCommissionVO> {
    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
