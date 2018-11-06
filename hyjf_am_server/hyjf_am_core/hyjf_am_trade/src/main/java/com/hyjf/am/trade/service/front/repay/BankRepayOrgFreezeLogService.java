package com.hyjf.am.trade.service.front.repay;

import com.hyjf.am.resquest.trade.BankRepayOrgFreezeLogRequest;
import com.hyjf.am.trade.dao.model.auto.BankRepayOrgFreezeLog;

import java.util.List;

/**
 * @author wgx
 * @date 2018/10/13
 */
public interface BankRepayOrgFreezeLogService {

    /**
     * 插入垫付机构冻结日志信息
     * @param requestBean
     */
    Integer insertRepayOrgFreezeLog(BankRepayOrgFreezeLogRequest requestBean);

    /**
     * 根据条件查询垫付机构冻结日志
     * @param orderId
     * @param borrowNid
     * @return
     */
    List<BankRepayOrgFreezeLog> getBankRepayOrgFreezeLogList(String orderId, String borrowNid);

    /**
     * 删除垫付机构还款冻结日志
     * @param orderId
     */
    Integer deleteOrgFreezeTempLogs(String orderId, String borrowNid);
}
