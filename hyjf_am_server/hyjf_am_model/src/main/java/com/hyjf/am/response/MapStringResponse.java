/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response;

import java.util.Map;

/**
 * 接口建议使用，手动转换String为需要的数据类型（防止Object强转数据值有问题）
 * @author liubin
 * @version MapResponse, v0.1 2018/7/10 18:22
 */
public class MapStringResponse extends Response{
    Map<String, String> resultMap;

    public MapStringResponse() {
    }

    public Map<String, String> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, String> resultMap) {
        this.resultMap = resultMap;
    }
}
