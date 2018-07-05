package com.hyjf.admin.client;

import com.hyjf.am.response.admin.UtmResponse;

import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
public interface UtmClient {
    /**
     * 获取数据
     * @param map 查询参数
     * @return UtmResponse
     */
    UtmResponse getByPageList(Map<String,Object> map);
    /**
     * 获取数据总条数
     * @param map 查询参数
     * @return UtmResponse
     */
    UtmResponse getCountByParam(Map<String,Object> map);
}
