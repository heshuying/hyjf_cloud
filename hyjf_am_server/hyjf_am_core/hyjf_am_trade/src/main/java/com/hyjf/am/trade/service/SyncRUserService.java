 package com.hyjf.am.trade.service;

import com.alibaba.fastjson.JSONObject;

public interface SyncRUserService extends BaseService {

    void updateUserInfo(JSONObject jsonObj);

    void insertUser(JSONObject jsonObj);

    void updateSpreadUser(JSONObject jsonObj);

}
