/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;

import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version HjhAccedeResponse, v0.1 2018/6/25 10:05
 */
public class HjhAccedeResponse extends Response<HjhAccedeVO> {

    private Integer accedeCount;

    private Map<String,Object> totalData;

    public Integer getAccedeCount() {
        return accedeCount;
    }

    public void setAccedeCount(Integer accedeCount) {
        this.accedeCount = accedeCount;
    }

    public Map<String, Object> getTotalData() {
        return totalData;
    }

    public void setTotalData(Map<String, Object> totalData) {
        this.totalData = totalData;
    }
}
