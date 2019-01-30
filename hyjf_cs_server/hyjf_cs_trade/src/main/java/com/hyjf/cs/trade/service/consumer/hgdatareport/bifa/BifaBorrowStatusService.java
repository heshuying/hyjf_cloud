/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月15日 上午9:43:49
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa;

import com.hyjf.am.vo.trade.bifa.BifaBorrowStatusEntityVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;

import java.util.List;


/**
 * @author jijun
 */

public interface BifaBorrowStatusService extends BaseHgDateReportService {

    BorrowAndInfoVO getBorrowAndInfo(String borrowNid);

    void checkBorrowInfoIsReported(BorrowAndInfoVO borrowAndInfoVO);

    void checkHjhPlanInfoIsReported(String planNid);

    BifaBorrowStatusEntityVO getBifaBorrowStatusFromMongoDB(BorrowAndInfoVO borrowAndInfoVO);

    List<BorrowTenderVO> selectBorrowTenders(String borrowNid);

    boolean convertBorrowStatus(BorrowAndInfoVO borrowAndInfoVO, List<BorrowTenderVO> borrowTenders, BifaBorrowStatusEntityVO bifaBorrowStatusEntity);

    void insertBorrowStatusReportData(BifaBorrowStatusEntityVO reportResult);
}