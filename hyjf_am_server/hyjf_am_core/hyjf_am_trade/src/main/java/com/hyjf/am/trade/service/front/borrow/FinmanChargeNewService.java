package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.am.vo.trade.borrow.BorrowFinmanNewChargeVO;

import java.util.List;

/**
 * @author xiehuili on 2018/8/14.
 */
public interface FinmanChargeNewService {

    int countRecordTotal(FinmanChargeNewRequest adminRequest);

    List<BorrowFinmanNewChargeVO> getRecordList(FinmanChargeNewRequest adminRequest, int limitStart, int limitEnd);

}
