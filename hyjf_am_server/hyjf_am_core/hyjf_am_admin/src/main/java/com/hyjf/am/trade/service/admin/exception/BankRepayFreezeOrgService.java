package com.hyjf.am.trade.service.admin.exception;

import com.hyjf.am.resquest.admin.RepayFreezeOrgRequest;
import com.hyjf.am.trade.dao.model.auto.BankRepayOrgFreezeLog;
import com.hyjf.am.trade.dao.model.customize.BankRepayFreezeOrgCustomize;

import java.util.List;

/**
 * @author hesy
 * @version BankRepayFreezeOrgService, v0.1 2018/10/19 11:41
 */
public interface BankRepayFreezeOrgService {
    Integer selectCount(RepayFreezeOrgRequest requestBean);

    List<BankRepayFreezeOrgCustomize> selectList(RepayFreezeOrgRequest requestBean);

    Integer deleteFreezeLogById(Integer id);

    Integer deleteOrgFreezeTempLogs(String orderId, String borrowNid);

    List<BankRepayOrgFreezeLog> getBankRepayOrgFreezeLogList(String orderId, String borrowNid);
}
