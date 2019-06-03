package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.vo.trade.BankCreditEndVO;

import java.util.List;

public interface BankCreditEndCustomizeMapper {
    int selectCreditEndCount(BankCreditEndListRequest requestBean);

    List<BankCreditEndVO> selectCreditEndList(BankCreditEndListRequest requestBean);
}
