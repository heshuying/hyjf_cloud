package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.IncreaseInterestRepayRequest;
import com.hyjf.am.vo.admin.IncreaseInterestRepayVO;

import java.util.List;

/**
 * @author：wenxin
 * @Date: 2018/8/30
 */
public interface IncreaseInterestRepayService {

    int getIncreaseInterestRepayCount(IncreaseInterestRepayRequest request);

    List<IncreaseInterestRepayVO> getIncreaseInterestRepayList(IncreaseInterestRepayRequest request);

    String sumAccount(IncreaseInterestRepayRequest form);
}
