package com.hyjf.wbs.trade.service;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther: wxd
 * @Date: 2019-04-18 10:11
 * @Description:
 */
public interface SynProductInfoService extends BaseService {
    public void sync(Map<String,String> map) throws IOException;
}
