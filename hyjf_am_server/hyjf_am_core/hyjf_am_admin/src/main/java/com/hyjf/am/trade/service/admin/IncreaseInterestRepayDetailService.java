package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.IncreaseInterestRepayDetailRequest;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;

import java.util.List;

/**
 * @author：wenxin
 * @Date: 2018/8/30
 */
public interface IncreaseInterestRepayDetailService {

    int getIncreaseInterestRepayDetailCount(IncreaseInterestRepayDetailRequest request);

    List<AdminIncreaseInterestRepayCustomizeVO> getIncreaseInterestRepayDetailList(IncreaseInterestRepayDetailRequest request);

    AdminIncreaseInterestRepayCustomizeVO sumBorrowRepaymentInfo(IncreaseInterestRepayDetailRequest form);
}
