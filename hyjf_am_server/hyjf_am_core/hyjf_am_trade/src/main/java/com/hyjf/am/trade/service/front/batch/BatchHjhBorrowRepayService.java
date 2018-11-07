/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.batch;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayService, v0.1 2018/6/27 9:21
 */
public interface BatchHjhBorrowRepayService extends BaseService{

    List<BorrowTender> selectBorrowTenderListByAccedeOrderId(String accedeOrderId);

    List<HjhAccede> selectHjhAccedeListByOrderId(String accedeOrderId);

    Integer updateHjhBorrowRepayInterest(HjhAccedeVO hjhAccedeVO);

    List<HjhPlan> selectHjhPlanListByPlanNid(String planNid);

    List<HjhAccede> selectHjhAccedeListById(Integer id);

    Integer updateHjhAccedeByPrimaryKey(HjhAccedeVO hjhAccedeVO);

    List<BorrowRecover> selectBorrowRecoverListByAccedeOrderId(String accedeOrderId);

    List<HjhRepay> selectHjhRepayListByAccedeOrderId(String accedeOrderId);

    Integer insertHjhBorrowRepay(HjhRepayVO hjhRepayVO);

    Integer updateBankTotalForLockPlan(AccountVO accountVO);

    Integer updateHjhRepayByPrimaryKey(HjhRepayVO hjhRepayVO);

    HjhRepay selectHjhRepayListById(Integer id);

}
