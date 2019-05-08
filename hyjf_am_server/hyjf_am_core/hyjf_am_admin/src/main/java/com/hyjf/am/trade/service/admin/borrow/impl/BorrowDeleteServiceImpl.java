/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.trade.service.admin.borrow.BorrowDeleteService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BorrowDeleteConfirmCustomizeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 标的删除
 * @author hesy
 */
@Service
public class BorrowDeleteServiceImpl extends BaseServiceImpl implements BorrowDeleteService {

    @Resource
    private CommonProducer commonProducer;

    /**
     * 标的删除确认页面数据获取
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowDeleteConfirmCustomizeVO selectDeleteConfirm(String borrowNid){
        return borrowDeleteCustomizeMapper.selectDeleteConfirm(borrowNid);
    }

}
