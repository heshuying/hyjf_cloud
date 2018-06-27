/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.customize.CustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowExample;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.BorrowInfoExample;
import com.hyjf.am.trade.service.BaseService;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 资金服务:BaseService实现类
 *
 * @author liuyang
 * @version BaseServiceImpl, v0.1 2018/6/27 9:33
 */
public class BaseServiceImpl extends CustomizeMapper implements BaseService {

    /**
     * 根据标的编号检索标的信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public Borrow getBorrow(String borrowNid) {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<Borrow> list = this.borrowMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据标的编号检索标的借款详情
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowInfo getBorrowInfoByNid(String borrowNid) {
        BorrowInfoExample example = new BorrowInfoExample();
        BorrowInfoExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        List<BorrowInfo> list=this.borrowInfoMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }
}
