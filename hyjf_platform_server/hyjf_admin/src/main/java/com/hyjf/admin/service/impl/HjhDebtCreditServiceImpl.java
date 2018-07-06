package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.HjhDebtCreditClient;
import com.hyjf.admin.service.HjhDebtCreditService;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/4
 * @Description:
 */
@Service
public class HjhDebtCreditServiceImpl implements HjhDebtCreditService{

    @Autowired
    private HjhDebtCreditClient hjhDebtCreditClient;

    @Override
    public HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request) {

        HjhDebtCreditReponse reponse = hjhDebtCreditClient.queryHjhDebtCreditList(request);
        return reponse;
    }

    @Override
    public Integer queryHjhDebtCreditListTotal(HjhDebtCreditListRequest request) {
        Integer total = hjhDebtCreditClient.queryHjhDebtCreditListTotal(request);
        return total;
    }
}
