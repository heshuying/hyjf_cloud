/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.Utils.Page;
import com.hyjf.admin.beans.response.BorrowInvestResponseBean;
import com.hyjf.admin.client.BorrowInvestClient;
import com.hyjf.admin.service.BorrowInvestService;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestServiceImpl, v0.1 2018/7/10 9:18
 */
@Service
public class BorrowInvestServiceImpl implements BorrowInvestService {
    @Autowired
    BorrowInvestClient borrowInvestClient;
    /**
     * 投资明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public BorrowInvestResponseBean getBorrowInvestList(BorrowInvestRequest borrowInvestRequest){
        BorrowInvestResponseBean responseBean = new BorrowInvestResponseBean();
        //查询总条数
        Integer count = borrowInvestClient.countBorrowInvest(borrowInvestRequest);
        responseBean.setTotal(count);
        //分页参数
        Page page = Page.initPage(borrowInvestRequest.getCurrPage(), borrowInvestRequest.getPageSize());
        page.setTotal(count);
        borrowInvestRequest.setLimitStart(page.getOffset());
        borrowInvestRequest.setLimitEnd(page.getLimit());
        //查询列表 总计
        if (count > 0) {
            List<BorrowInvestCustomizeVO> list = borrowInvestClient.selectBorrowInvestList(borrowInvestRequest);
            String sumAccount = borrowInvestClient.selectBorrowInvestAccount(borrowInvestRequest);
            responseBean.setRecordList(list);
            responseBean.setSumAccount(sumAccount);
        }
        return responseBean;
    }
}
