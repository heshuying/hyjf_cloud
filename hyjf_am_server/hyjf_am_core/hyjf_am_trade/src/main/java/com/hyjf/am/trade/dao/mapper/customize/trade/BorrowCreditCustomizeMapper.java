package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.resquest.admin.BorrowCreditAmRequest;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditCustomize;
import com.hyjf.am.vo.admin.BorrowCreditInfoSumVO;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditDetailVO;
import com.hyjf.am.vo.trade.borrow.ProjectUndertakeListVO;
import com.hyjf.am.vo.trade.hjh.AppCreditDetailCustomizeVO;

import java.util.List;
import java.util.Map;

public interface BorrowCreditCustomizeMapper {

    List<BorrowCreditDetailVO> getBorrowCreditDetail(Map<String,Object> map);

    AppCreditDetailCustomizeVO getAppCreditDetailByCreditNid(Map<String,Object> map);

    List<AdminBorrowCreditCustomize> getBorrowCreditList4Admin(Map<String,Object> map);

    Integer countBorrowCreditList4Admin(Map<String,Object> map);

    BorrowCreditSumVO getBorrowCreditTotalCount(Map<String,Object> map);

    Integer countBorrowCreditInfo4Admin(BorrowCreditAmRequest request);

    List<AdminBorrowCreditCustomize> searchBorrowCreditInfo4Admin(BorrowCreditAmRequest request);

    BorrowCreditInfoSumVO sumBorrowCreditInfoData(BorrowCreditAmRequest request);

    int sumUnderTakeAmount(String borrowNid);

    List<ProjectUndertakeListVO> selectProjectUndertakeList(Map<String,Object> map);
}
