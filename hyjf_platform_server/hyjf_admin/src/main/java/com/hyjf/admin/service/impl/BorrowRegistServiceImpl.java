/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.response.BorrowRegistResponseBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.BorrowRegistService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowRegistServiceImpl, v0.1 2018/6/29 15:34
 */
@Service
public class BorrowRegistServiceImpl implements BorrowRegistService {
    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectList() {
        return amTradeClient.selectBorrowProjectList();
    }

    @Override
    public Map<String, String> getParamNameMap(String param) {
        Map<String, String> resultMap = CacheUtil.getParamNameMap(param);
        return resultMap;
    }

    /**
     * 获取标的备案列表
     *
     * @param borrowRegistListRequest
     * @return
     */
    @Override
    public BorrowRegistResponseBean getRegistList(BorrowRegistListRequest borrowRegistListRequest) {
        BorrowRegistResponseBean borrowRegistResponseBean = new BorrowRegistResponseBean();
        //查询总条数
        Integer count = amTradeClient.getRegistCount(borrowRegistListRequest);
        borrowRegistResponseBean.setTotal(count);
        //分页参数
        Page page = Page.initPage(borrowRegistListRequest.getCurrPage(), borrowRegistListRequest.getPageSize());
        page.setTotal(count);
        borrowRegistListRequest.setLimitStart(page.getOffset());
        borrowRegistListRequest.setLimitEnd(page.getLimit());
        //查询列表 总计
        if (count > 0) {
            List<BorrowRegistCustomizeVO> list = amTradeClient.selectRegistList(borrowRegistListRequest);
            String sumAccount = amTradeClient.sumBorrowRegistAccount(borrowRegistListRequest);
            borrowRegistResponseBean.setRecordList(list);
            borrowRegistResponseBean.setSumAccount(sumAccount);
        }
        return borrowRegistResponseBean;
    }

    /**
     * 标的备案
     *
     * @param borrowNid
     * @return
     */
    @Override
    public AdminResult updateBorrowRegist(String borrowNid, String currUserId, String currUserName) {
        return amTradeClient.updateBorrowRegist(borrowNid, currUserId, currUserName);
    }
}
