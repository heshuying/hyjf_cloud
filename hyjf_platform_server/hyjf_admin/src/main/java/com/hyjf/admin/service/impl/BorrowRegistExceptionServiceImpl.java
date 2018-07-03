/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.BorrowRegistExceptionClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.BorrowRegistExceptionService;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionServiceImpl, v0.1 2018/7/3 14:52
 */
@Service
public class BorrowRegistExceptionServiceImpl extends BaseServiceImpl implements BorrowRegistExceptionService {

    @Autowired
    private BorrowRegistExceptionClient borrowRegistExceptionClient;

    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectList(){
        return borrowRegistExceptionClient.selectBorrowProjectList();
    }

    @Override
    public List<BorrowStyleVO> selectBorrowStyleList(){
        return borrowRegistExceptionClient.selectBorrowStyleList();
    }


    @Override
    public Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest){
        return borrowRegistExceptionClient.getRegistCount(borrowRegistListRequest);
    }

    @Override
    public List <BorrowRegistCustomizeVO> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest){
        return borrowRegistExceptionClient.selectBorrowRegistList(borrowRegistListRequest);
    }
}
