/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.customize.trade.AutoReqRepayBorrowCustomize;

import java.text.ParseException;
import java.util.List;

/**
 * @author liubin
 * @version AutoReqRepayService, v0.1 2018/7/13 10:56
 */
public interface AutoReqRepayService extends BaseService{
    /**
     * 取得本日应还款标的列表
     * @return
     */
    public List<AutoReqRepayBorrowCustomize> getAutoReqRepayBorrow();

    /**
     * 取得本日应还款标的列表
     */
    public boolean repayUserBorrowProject(AutoReqRepayBorrowCustomize autoReqRepayBorrow)  throws ParseException;

}
