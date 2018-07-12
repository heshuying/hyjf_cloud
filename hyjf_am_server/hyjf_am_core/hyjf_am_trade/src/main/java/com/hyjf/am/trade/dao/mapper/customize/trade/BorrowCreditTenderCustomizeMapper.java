package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.vo.admin.BorrowCreditTenderSumVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;

import java.util.List;
import java.util.Map;

public interface BorrowCreditTenderCustomizeMapper {

	Integer countBorrowCreditTender(BorrowCreditRepayAmRequest request);

	List<AdminBorrowCreditTenderCustomize> searchBorrowCreditTender(BorrowCreditRepayAmRequest request);

	BorrowCreditTenderSumVO sumBorrowCreditTender(BorrowCreditRepayAmRequest request);

	List<BorrowCreditRepayInfoVO>  getCreditRepayList(BorrowCreditRepayAmRequest request);

	Map<String,Object> sumCreditRepay(BorrowCreditRepayAmRequest request);


}
