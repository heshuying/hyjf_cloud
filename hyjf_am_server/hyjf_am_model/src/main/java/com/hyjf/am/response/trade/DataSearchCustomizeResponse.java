package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;

import java.util.Map;

/**
 * @author lisheng
 * @version DataSearchCustomizeResponse, v0.1 2018/8/23 14:58
 */

public class DataSearchCustomizeResponse extends Response<DataSearchCustomizeVO> {
    private Map<String,Object> money;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map<String, Object> getMoney() {
        return money;
    }

    public void setMoney(Map<String, Object> money) {
        this.money = money;
    }
}
