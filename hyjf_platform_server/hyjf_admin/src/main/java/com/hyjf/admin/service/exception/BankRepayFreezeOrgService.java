package com.hyjf.admin.service.exception;

import com.hyjf.admin.beans.repaybean.RepayBean;
import com.hyjf.admin.service.BaseAdminService;
import com.hyjf.am.resquest.admin.RepayFreezeOrgRequest;
import com.hyjf.am.vo.admin.BankRepayFreezeOrgCustomizeVO;
import com.hyjf.am.vo.trade.repay.BankRepayOrgFreezeLogVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;

/**
 * @author hesy
 * @version BankRepayFreezeOrgService, v0.1 2018/10/19 12:07
 */
public interface BankRepayFreezeOrgService extends BaseAdminService {
    List<BankRepayFreezeOrgCustomizeVO> selectList(RepayFreezeOrgRequest requestBean);

    Integer selectCount(RepayFreezeOrgRequest requestBean);

    BankRepayOrgFreezeLogVO getBankRepayOrgFreezeLogList(String orderId,String borrowNid, Integer currentPeriod);

    Integer deleteFreezeLogById(Integer id);

    Integer deleteOrgFreezeTempLogs(String orderId);

    RepayBean getRepayBean(Integer userId, String roleId, String borrowNid, boolean isAllRepay);

    Boolean updateForRepayRequest(RepayBean repayBean, BankCallBean bankCallBean, boolean isAllRepay);

    Boolean updateBorrowCreditStautus(String borrowNid);
}
