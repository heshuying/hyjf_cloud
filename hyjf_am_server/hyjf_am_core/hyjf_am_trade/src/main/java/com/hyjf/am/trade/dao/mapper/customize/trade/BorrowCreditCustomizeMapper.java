package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.vo.trade.borrow.BorrowCreditDetailVO;
import com.hyjf.am.vo.trade.hjh.AppCreditDetailCustomizeVO;

import java.util.List;
import java.util.Map;

public interface BorrowCreditCustomizeMapper {

    List<BorrowCreditDetailVO> getBorrowCreditDetail(Map<String,Object> map);

    AppCreditDetailCustomizeVO getAppCreditDetailByCreditNid(Map<String,Object> map);
}
