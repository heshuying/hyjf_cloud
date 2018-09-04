package com.hyjf.am.trade.dao.mapper.customize;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.trade.dao.model.customize.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.vo.admin.BorrowCreditRepaySumVO;
import com.hyjf.am.vo.admin.BorrowCreditTenderVO;
import com.hyjf.am.vo.trade.CreditTenderListCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;

public interface BorrowCreditTenderCustomizeMapper {

	Integer countBorrowCreditRepay(BorrowCreditRepayAmRequest request);

	List<AdminBorrowCreditTenderCustomize> searchBorrowCreditRepay(BorrowCreditRepayAmRequest request);

	BorrowCreditRepaySumVO sumBorrowCreditRepay(BorrowCreditRepayAmRequest request);

	List<BorrowCreditRepayInfoVO>  getCreditRepayInfoList(BorrowCreditRepayAmRequest request);

	Map<String,Object> sumCreditRepayInfo(BorrowCreditRepayAmRequest request);

	Integer countBorrowCreditTender(BorrowCreditRepayAmRequest request);

	List<BorrowCreditTenderVO> searchBorrowCreditTender(BorrowCreditRepayAmRequest request);

	Map<String,Object> sumCreditTender(BorrowCreditRepayAmRequest request);

	BigDecimal getHzrTenderPrice(@Param("list") List<Integer> list, @Param("dayStart") String dayStart,
			@Param("dayEnd") String dayEnd);


	Integer getCreditListCount(Map<String,Object> params);

	List<CreditTenderListCustomizeVO> getCreditTenderList(Map<String,Object> params);

	String getServiceFee(String creditNid);

	String getCreditTenderAssignPay(Map<String,String> params);
}
