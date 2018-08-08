package com.hyjf.admin.service;

import com.hyjf.am.response.admin.HjhAccountBalanceResponse;
import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  10:29
 */
public interface AccountBalanceService {

    HjhAccountBalanceResponse getSearchListByMonth(HjhAccountBalanceRequest request);

    HjhAccountBalanceResponse getSearchListByDay(HjhAccountBalanceRequest request);

    List<HjhAccountBalanceVO> getHjhAccountBalanceList(HjhAccountBalanceRequest request);

    List<HjhAccountBalanceVO> getHjhAccountBalanceMonthList(HjhAccountBalanceRequest request);

}
