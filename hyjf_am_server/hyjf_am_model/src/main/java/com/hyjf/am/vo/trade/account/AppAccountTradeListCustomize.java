/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.account;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author jun
 * @version AppAccountTradeListCustomize, v0.1 2018/7/25 14:37
 */
public class AppAccountTradeListCustomize extends BaseVO implements Serializable {

    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
