/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.mc.hgdatareport.bifa;

import java.io.Serializable;

/**
 * @author jun
 * @version InvestorEntity, v0.1 2019/01/15
 */
public class InvestorBean implements Serializable {

    private String invest_amt;

    private String investor_name_idcard_digest;

    public void setInvest_amt(String invest_amt) {
        this.invest_amt = invest_amt;
    }

    public String getInvest_amt() {
        return invest_amt;
    }

    public void setInvestor_name_idcard_digest(String investor_name_idcard_digest) {
        this.investor_name_idcard_digest = investor_name_idcard_digest;
    }

    public String getInvestor_name_idcard_digest() {
        return investor_name_idcard_digest;
    }
}
