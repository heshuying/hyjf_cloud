package com.hyjf.admin.service;

import com.hyjf.am.resquest.admin.BankEveRequest;
import com.hyjf.am.vo.admin.BankEveVO;

import java.util.List;

/**
 * @author zdj
 * @version BankAccountManageServiceImpl, v0.1 2018/6/29 11:54
 */
public interface BankJournalService {

    /**
     * 查询银行账务明细
     * @param request
     */
    List<BankEveVO> queryBankEveList(BankEveRequest request);

}
