/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa;

import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.bifa.BifaCreditTenderInfoEntityVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;

/**
 * @author jun
 * @version BifaCreditTenderInfoService, v0.1 2019/1/17 10:38
 */
public interface BifaCreditTenderInfoService extends BaseHgDateReportService {

    BorrowCreditVO selectBorrowCreditInfo(String creditNid);

    BifaCreditTenderInfoEntityVO getBifaBorrowCreditInfoFromMongDB(String sourceProductCode);

    UserInfoVO getUsersInfoByUserId(Integer creditUserId);

    BorrowAndInfoVO getBorrowByBorrowNid(String bidNid);

    boolean convertBifaBorrowCreditInfo(BorrowCreditVO borrowCredit, BorrowAndInfoVO borrow, UserInfoVO creditUserInfo, BifaCreditTenderInfoEntityVO bifaCreditInfoEntity);

    void insertCreditTenderInfoReportData(BifaCreditTenderInfoEntityVO reportResult);

    HjhDebtCreditVO selectHjhDebtCreditInfo(String creditNid);

    boolean convertBifaHjhCreditInfo(HjhDebtCreditVO hjhDebtCredit, BorrowAndInfoVO borrow, UserInfoVO creditUserInfo, BifaCreditTenderInfoEntityVO bifaCreditInfoEntity);
}
