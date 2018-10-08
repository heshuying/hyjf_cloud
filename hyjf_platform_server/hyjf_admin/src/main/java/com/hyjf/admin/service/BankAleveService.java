package com.hyjf.admin.service;

import com.hyjf.am.resquest.admin.BankAleveRequest;
import com.hyjf.am.vo.admin.BankAleveVO;

import java.util.List;

/**
 * @author zdj
 * @version BankAccountManageServiceImpl, v0.1 2018/6/29 11:54
 */
public interface BankAleveService {

    /**
     * 查询银行账务明细
     * @param request
     */
    List<BankAleveVO> queryBankAleveList(BankAleveRequest request);

}
