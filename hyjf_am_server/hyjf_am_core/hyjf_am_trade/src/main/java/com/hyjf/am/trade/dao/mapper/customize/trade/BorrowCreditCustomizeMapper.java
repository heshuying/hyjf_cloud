package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditCustomize;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditDetailVO;
import com.hyjf.am.vo.trade.hjh.AppCreditDetailCustomizeVO;

import java.util.List;
import java.util.Map;

public interface BorrowCreditCustomizeMapper {

    List<BorrowCreditDetailVO> getBorrowCreditDetail(Map<String,Object> map);

    AppCreditDetailCustomizeVO getAppCreditDetailByCreditNid(Map<String,Object> map);

    List<AdminBorrowCreditCustomize> getBorrowCreditList4Admin(Map<String,Object> map);

    Integer countBorrowCreditList4Admin(Map<String,Object> map);

    BorrowCreditSumVO getBorrowCreditTotalCount(Map<String,Object> map);
}
