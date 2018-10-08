package com.hyjf.cs.trade.service.home;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface AppHomeService {

    public JSONObject getAppHomeData(HttpServletRequest request, String userId);
}
