/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口不建议使用，请使用MapStringResponse
 * @author liubin
 * @version MapResponse, v0.1 2018/7/10 18:22
 */
public class MapResponse extends Response{
    private static Logger logger = LoggerFactory.getLogger(MapResponse.class);

    Map<String, Object> resultMap;

    public MapResponse() {
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    /**
     * MapResponse的resultMap的类型转换String(不能是Double会失精度)→BigDecimal
     * 适用范围：resultMap的元素类型为String且是数字
     * @return Map<String, Object>
     */
    public Map<String, Object> resultMapToBigDecimalAll() {
        if(this.resultMap == null){
            return null;
        }
        Map<String, Object> bigDecimalMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : this.resultMap.entrySet()) {
            logger.info("sssss"+entry.toString());
            if (entry.getValue() == null){
                bigDecimalMap.put(entry.getKey(), null);
            }else if (entry.getValue().getClass().getName().equals("java.lang.String")){
                logger.info("sssss"+entry.getValue().toString());
                bigDecimalMap.put(entry.getKey(), new BigDecimal(entry.getValue().toString()));
            }else{
                throw new RuntimeException("resultMap的元素类型必须是String且是数字，不然会失精度。");
            }
        }
        return bigDecimalMap;
    }
}
