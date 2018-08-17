 package com.hyjf.am.trade.service.front.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.service.BaseService;

 public interface SyncRUserService extends BaseService {

    void updateUserInfo(JSONObject jsonObj);

    void insertUser(JSONObject jsonObj);

    void updateSpreadUser(JSONObject jsonObj);

}
