 package com.hyjf.am.trade.service.front.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.service.BaseService;

 public interface SyncRUserService extends BaseService {

    void updateUserInfo(JSONObject jsonObj) throws RuntimeException;

    void insertUser(JSONObject jsonObj);

    void updateUser(JSONObject jsonObj) throws RuntimeException;

    void updateSpreadUser(JSONObject jsonObj) throws RuntimeException;

    void updateUserInfoByReferrer(JSONObject jsonObj);

     void deleteRUser(JSONObject jsonObj);
}
