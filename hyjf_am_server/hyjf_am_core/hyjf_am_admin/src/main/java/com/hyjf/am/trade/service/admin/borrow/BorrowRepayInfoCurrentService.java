package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.resquest.admin.BorrowRepayInfoCurrentRequest;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * 当前债权还款明细
 * @author hesy
 */
public interface BorrowRepayInfoCurrentService {

    Integer getRepayInfoCurrentCount(BorrowRepayInfoCurrentRequest requestBean);

    List<BorrowRepayInfoCurrentCustomizeVO> getRepayInfoCurrentList(BorrowRepayInfoCurrentRequest requestBean, Integer offset, Integer limit);

    Map<String,Object> getRepayInfoCurrentSum(BorrowRepayInfoCurrentRequest requestBean);
}
