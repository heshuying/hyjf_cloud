package com.hyjf.cs.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.common.bean.result.AppResult;

import javax.servlet.http.HttpServletRequest;

public interface AppHomeService {

    public JSONObject getAppHomeData(HttpServletRequest request, String userId);
}
