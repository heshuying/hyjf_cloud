package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.BankAleveService;
import com.hyjf.am.resquest.admin.BankAleveRequest;
import com.hyjf.am.vo.admin.BankAleveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zdj on 2018/7/20.
 */
@Service
public class BankAleveServiceImpl implements BankAleveService {

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 根据筛选条件查询银行账务明细list
     * @param
     * @return
     */
    @Override
    public List<BankAleveVO> queryBankAleveList(BankAleveRequest request) {
        return amTradeClient.queryBankAleveList(request);
    }

}
