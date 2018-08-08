package com.hyjf.admin.service;

import com.hyjf.am.response.admin.HjhInfoAccountBalanceResponse;
import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  10:29
 */
public interface AccountBalanceService {

    HjhInfoAccountBalanceResponse getSearchListByMonth(HjhAccountBalanceRequest request);

    HjhInfoAccountBalanceResponse getSearchListByDay(HjhAccountBalanceRequest request);

    List<HjhAccountBalanceVO> getHjhAccountBalanceList(HjhAccountBalanceRequest request);

    List<HjhAccountBalanceVO> getHjhAccountBalanceMonthList(HjhAccountBalanceRequest request);

}
