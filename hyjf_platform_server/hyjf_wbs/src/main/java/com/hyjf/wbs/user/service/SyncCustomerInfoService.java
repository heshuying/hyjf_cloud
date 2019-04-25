package com.hyjf.wbs.user.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Auther: wxd
 * @Date: 2019-04-23 11:24
 * @Description:
 */
public interface SyncCustomerInfoService extends BaseService {
    public void sync(Map<String,String> mapData) throws UnsupportedEncodingException, IOException;
}
