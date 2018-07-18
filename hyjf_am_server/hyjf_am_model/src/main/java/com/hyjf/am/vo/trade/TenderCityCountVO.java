/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

/**
 * @author fuqiang
 * @version TenderCityCountVO, v0.1 2018/7/18 10:37
 */
public class TenderCityCountVO extends BaseVO {
    public int citycode;
    public String name;
    public int count;

    public int getCitycode() {
        return citycode;
    }
    public void setCitycode(int citycode) {
        this.citycode = citycode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
