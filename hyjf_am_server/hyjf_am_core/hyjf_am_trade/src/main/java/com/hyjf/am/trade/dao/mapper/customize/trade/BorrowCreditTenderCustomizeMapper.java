package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.vo.admin.BorrowCreditRepaySumVO;
import com.hyjf.am.vo.admin.BorrowCreditTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;

import java.util.List;
import java.util.Map;

public interface BorrowCreditTenderCustomizeMapper {

	Integer countBorrowCreditRepay(BorrowCreditRepayAmRequest request);

	List<AdminBorrowCreditTenderCustomize> searchBorrowCreditRepay(BorrowCreditRepayAmRequest request);

	BorrowCreditRepaySumVO sumBorrowCreditRepay(BorrowCreditRepayAmRequest request);

	List<BorrowCreditRepayInfoVO>  getCreditRepayInfoList(BorrowCreditRepayAmRequest request);

	Map<String,Object> sumCreditRepayInfo(BorrowCreditRepayAmRequest request);

	Integer countBorrowCreditTender(BorrowCreditRepayAmRequest request);

	List<BorrowCreditTenderVO> searchBorrowCreditTender(BorrowCreditRepayAmRequest request);

	Map<String,Object> sumCreditTender(BorrowCreditRepayAmRequest request);


}
