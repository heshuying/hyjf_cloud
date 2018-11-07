package com.hyjf.cs.message.service.message;

import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/11/6  10:49
 */
public interface AccountBalanceService {

    List<HjhAccountBalanceVO> getHjhAccountBalanceVOByMonth(HjhAccountBalanceRequest request, Boolean flag);

    List<HjhAccountBalanceVO> getHjhAccountBalanceVOByDay(HjhAccountBalanceRequest request,Boolean flag);
}
