package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.resquest.admin.BorrowRepayInfoCurrentRequest;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentExportCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * 当前债权还款明细
 * @author hesy
 */
public interface BorrowRepayInfoCurrentService {

    Integer getRepayInfoCurrentCount(Map<String,Object> paraMap);

    List<BorrowRepayInfoCurrentCustomizeVO> getRepayInfoCurrentList(Map<String,Object> paraMap);

    Map<String,Object> getRepayInfoCurrentSum(Map<String,Object> paraMap);

    List<BorrowRepayInfoCurrentExportCustomizeVO> getRepayInfoCurrentListExport(Map<String,Object> paraMap);

    Integer getRepayInfoCurrentCountExport(Map<String,Object> paraMap);
}
