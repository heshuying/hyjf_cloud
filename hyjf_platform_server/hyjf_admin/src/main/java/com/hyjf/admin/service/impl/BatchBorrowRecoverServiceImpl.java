package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.BatchBorrowRecoverService;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description:
 */
@Service
public class BatchBorrowRecoverServiceImpl extends BaseServiceImpl implements BatchBorrowRecoverService{


    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public BatchBorrowRecoverReponse queryBatchBorrowRecoverList(BatchBorrowRecoverRequest request) {

        BatchBorrowRecoverReponse reponse = amTradeClient.getBatchBorrowRecoverList(request);
        return reponse;
    }
}
