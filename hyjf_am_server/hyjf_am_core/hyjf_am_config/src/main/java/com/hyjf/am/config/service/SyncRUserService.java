 package com.hyjf.am.config.service;

import com.alibaba.fastjson.JSONObject;

public interface SyncRUserService{

    void updateUserInfo(JSONObject jsonObj);

    void insertUser(JSONObject jsonObj);

    void updateUser(JSONObject jsonObj);

    void updateSpreadUser(JSONObject jsonObj);

}
