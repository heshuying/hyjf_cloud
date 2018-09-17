package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.IncreaseInterestRepayInfoListRequest;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;

import java.util.List;

/**
 * @author：wenxin
 * @Date: 2018/8/30
 */
public interface IncreaseInterestRepayInfoListService {

    int getIncreaseInterestRepayInfoListCount(IncreaseInterestRepayInfoListRequest request);

    List<AdminIncreaseInterestRepayCustomizeVO> getIncreaseInterestRepayInfoListList(IncreaseInterestRepayInfoListRequest request);

    AdminIncreaseInterestRepayCustomizeVO sumBorrowLoanmentInfo(IncreaseInterestRepayInfoListRequest form);
}
