/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BorrowTenderTmpRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderTmp;

import java.util.List;

/**
 * 投资撤销异常
 * @author jijun
 * @since 20180625
 */
public interface BankTenderCancelService {

    List<BorrowTenderTmp> getBorrowTenderTmpsForTenderCancel();

    boolean updateBidCancelRecord(JSONObject para);

    int updateTenderCancelExceptionData(BorrowTenderTmp borrowTenderTmp);
}
