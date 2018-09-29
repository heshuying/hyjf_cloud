package com.hyjf.cs.trade.bean;

import com.hyjf.cs.common.bean.result.WebResult;

/**
 * @author pangchengchao
 * @version TradeDetailBean, v0.1 2018/6/27 11:45
 */
public class TradeDetailBean extends WebResult {


    // 数组类型
    private String listType;


    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }
}
