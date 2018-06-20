package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.BankCallBeanVO;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;

import java.util.List;

/**
 * 债转投资异常
 * @author jun
 * @since 20180619
 */
public interface BankCreditTenderClient {


    List<CreditTenderLogVO> selectCreditTenderLogs();

    List<CreditTenderVO> selectCreditTender(String assignNid);

}
