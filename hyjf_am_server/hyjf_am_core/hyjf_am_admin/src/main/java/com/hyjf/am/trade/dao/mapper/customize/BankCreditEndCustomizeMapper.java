package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.vo.trade.BankCreditEndVO;

import java.util.List;
import java.util.Map;

public interface BankCreditEndCustomizeMapper {
    int selectCreditEndCount(BankCreditEndListRequest requestBean);

    List<BankCreditEndVO> selectCreditEndList(BankCreditEndListRequest requestBean);

    List<BankCreditEndVO> selectCreditEndCallBackFail(Map<String,Object> paraMap);
}
