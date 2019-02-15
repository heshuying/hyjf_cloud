package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.NaMiMarketingVO;
import com.hyjf.am.vo.admin.PerformanceReturnDetailVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version NaMiMarketingResponse, v0.1 2018/12/26 15:00
 */

public class NaMiMarketingResponse extends Response<NaMiMarketingVO> {
    Integer count;
    Integer active;
    BigDecimal totalAmount = BigDecimal.ZERO;
    Map<String,PerformanceReturnDetailVO> map=new HashMap<String,PerformanceReturnDetailVO>();
    List<String> monthList = new ArrayList<String>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Map<String, PerformanceReturnDetailVO> getMap() {
        return map;
    }

    public void setMap(Map<String, PerformanceReturnDetailVO> map) {
        this.map = map;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<String> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<String> monthList) {
        this.monthList = monthList;
    }
}
