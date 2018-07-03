package com.hyjf.admin.service.promotion;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
public interface UtmService {
    /**
     * 分页获取数据
     * @param map 参数
     * @param currPage 页数
     * @param pageSize 条数
     * @return PageList
     */
    JSONObject getByPageList(Map<String,Object> map, Integer currPage, Integer pageSize);
}
