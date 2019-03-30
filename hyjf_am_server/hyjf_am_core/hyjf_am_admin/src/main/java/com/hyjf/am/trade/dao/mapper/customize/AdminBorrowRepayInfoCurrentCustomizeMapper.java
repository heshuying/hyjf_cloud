package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 */
public interface AdminBorrowRepayInfoCurrentCustomizeMapper {
    int getRepayInfoCurrentCount(Map<String, Object> param);

    List<BorrowRepayInfoCurrentCustomizeVO> getRepayInfoCurrentList(Map<String, Object> param);

    Map<String,Object> getRepayInfoCurrentSum(Map<String, Object> param);
}
