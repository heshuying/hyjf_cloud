package com.hyjf.cs.trade.service.home;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public interface AppHomeService {

    public JSONObject getAppHomeData(HttpServletRequest request, String userId);
}
