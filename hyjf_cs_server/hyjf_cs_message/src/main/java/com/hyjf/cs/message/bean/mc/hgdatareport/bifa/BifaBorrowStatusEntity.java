/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.mc.hgdatareport.bifa;

import com.hyjf.cs.message.bean.mc.hgdatareport.base.BaseHgDataReportEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 产品状态更新
 * @author jun
 */
@Document(collection = "ht_bifa_borrow_status")
public class BifaBorrowStatusEntity extends BaseHgDataReportEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String product_status;
    private String product_status_desc;
    private String begindate;
    private String enddate;
    private String product_date;
    private String invest_amt;
    private String investor_name_idcard_digest;

    private List<InvestorBean> investorlist;

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }

    public String getProduct_status_desc() {
        return product_status_desc;
    }

    public void setProduct_status_desc(String product_status_desc) {
        this.product_status_desc = product_status_desc;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getProduct_date() {
        return product_date;
    }

    public void setProduct_date(String product_date) {
        this.product_date = product_date;
    }

    public String getInvest_amt() {
        return invest_amt;
    }

    public void setInvest_amt(String invest_amt) {
        this.invest_amt = invest_amt;
    }

    public String getInvestor_name_idcard_digest() {
        return investor_name_idcard_digest;
    }

    public void setInvestor_name_idcard_digest(String investor_name_idcard_digest) {
        this.investor_name_idcard_digest = investor_name_idcard_digest;
    }

    public List<InvestorBean> getInvestorlist() {
        return investorlist;
    }

    public void setInvestorlist(List<InvestorBean> investorlist) {
        this.investorlist = investorlist;
    }
}
