/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response;

import java.util.Map;

/**
 * @author liubin
 * @version MapResponse, v0.1 2018/7/10 18:22
 */
public class MapResponse extends Response{
    Map<String, Object> resultMap;

    public MapResponse() {
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
}
