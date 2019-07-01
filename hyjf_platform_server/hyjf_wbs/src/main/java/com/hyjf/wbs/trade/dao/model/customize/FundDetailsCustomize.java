/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.trade.dao.model.customize;

import com.google.common.collect.Lists;
import com.hyjf.wbs.qvo.FundDetailsQO;

import java.util.List;

/**
 * @author cui
 * @version FundDetailsCustomize, v0.1 2019/7/1 14:58
 */
public class FundDetailsCustomize extends FundDetailsQO {

    //资产端客户ID
    private Integer assetCustomerIdInt;

    //资产端财富ID
    private List<Integer> assetEntId;

    public Integer getAssetCustomerIdInt() {
        return assetCustomerIdInt;
    }

    public void setAssetCustomerIdInt(Integer assetCustomerIdInt) {
        this.assetCustomerIdInt = assetCustomerIdInt;
    }

    public List<Integer> getAssetEntId() {
        if(assetEntId==null){
            assetEntId= Lists.newArrayList();
        }
        return assetEntId;
    }

    public void setAssetEntId(List<Integer> assetEntId) {
        this.assetEntId = assetEntId;
    }
}
