package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  11:00
 */
public interface AccountBalanceClient {

    int getHjhAccountBalanceMonthCountNew(HjhAccountBalanceRequest request);

    int getHjhAccountBalanceMonthCount(HjhAccountBalanceRequest request);

    List<HjhAccountBalanceVO> getHjhAccountBalanceMonthList(HjhAccountBalanceRequest request);

    int getHjhAccountBalancecountByDay (HjhAccountBalanceRequest request);

    List<HjhAccountBalanceVO> getHjhAccountBalanceListByDay(HjhAccountBalanceRequest request);
}