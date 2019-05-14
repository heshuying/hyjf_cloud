package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentExportCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * 当前债权还款明细
 * @author hesy
 */
public interface AdminBorrowRepayInfoCurrentCustomizeMapper {
    int getRepayInfoCurrentCount(Map<String, Object> param);

    List<BorrowRepayInfoCurrentCustomizeVO> getRepayInfoCurrentList(Map<String, Object> param);

    Map<String,Object> getRepayInfoCurrentSum(Map<String, Object> param);

    List<BorrowRepayInfoCurrentExportCustomizeVO> getRepayInfoCurrentListExport(Map<String, Object> param);

    int getRepayInfoCurrentCountExport(Map<String, Object> param);
}
