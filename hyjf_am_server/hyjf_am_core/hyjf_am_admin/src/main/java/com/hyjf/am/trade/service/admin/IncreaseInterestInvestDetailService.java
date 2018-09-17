package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.IncreaseInterestInvestDetailRequest;
import com.hyjf.am.vo.admin.IncreaseInterestInvestVO;

import java.util.List;

/**
 * @author：wenxin
 * @Date: 2018/8/30
 */
public interface IncreaseInterestInvestDetailService {

    int getIncreaseInterestInvestDetaiCount(IncreaseInterestInvestDetailRequest request);

    List<IncreaseInterestInvestVO> getIncreaseInterestInvestDetaiList(IncreaseInterestInvestDetailRequest request);

    String sumAccount(IncreaseInterestInvestDetailRequest form);
}
