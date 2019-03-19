package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.trade.ScreenDataBean;

/**
 * @author lisheng
 * @version ScreenDataResponse, v0.1 2019/3/18 16:00
 */

public class ScreenDataResponse extends Response<ScreenDataBean> {
    /**
     * 0:其他,1:新客组,2:老客组,3:惠众  4：不属于运营部
     */
    Integer group;
    /**
     * 坐席姓名
     */
    String currentOwner;
    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }
}
