/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.app;

import com.hyjf.am.vo.BasePage;

/**
 * @author jun
 * @version AppProjectInvestBeanRequest, v0.1 2018/7/26 11:00
 */
public class AppProjectInvestBeanRequest extends BasePage {

    /*项目编号*/
    private String borrowNid;

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }
}
