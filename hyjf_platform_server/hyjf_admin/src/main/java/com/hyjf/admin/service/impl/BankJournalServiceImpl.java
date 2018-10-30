package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.BankJournalService;
import com.hyjf.am.resquest.admin.BankEveRequest;
import com.hyjf.am.vo.admin.BankEveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zdj on 2018/7/20.
 */
@Service
public class BankJournalServiceImpl implements BankJournalService {

    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * 根据筛选条件查询银行账务明细list
     * @param
     * @return
     */
    @Override
    public List<BankEveVO> queryBankEveList(BankEveRequest request) {
        return amTradeClient.queryBankEveList(request);
    }


    @Override
    public Integer queryBankEveCount(BankEveRequest request) {
        return  amTradeClient.queryBankEveCount(request);
    }
}
