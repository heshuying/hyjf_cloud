/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.datacollect;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hyjf.am.vo.BaseVO;

/**
 * @author fuqiang
 * @version SubEntityVO, v0.1 2018/7/18 9:59
 */
public class SubEntityVO extends BaseVO implements Serializable {
    @JsonIgnore
    public  int value;
    public String name;
    public String percent;
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty("value")
    public String getPercent() {
        return percent;
    }
    public void setPercent(String percent) {
        this.percent = percent;
    }
}
