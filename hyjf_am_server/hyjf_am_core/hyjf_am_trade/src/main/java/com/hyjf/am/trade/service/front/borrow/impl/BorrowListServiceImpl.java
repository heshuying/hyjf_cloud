package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.trade.dao.model.customize.InvestListCustomize;
import com.hyjf.am.trade.service.front.borrow.BorrowListService;
import org.springframework.beans.factory.annotation.Autowired;
import com.hyjf.am.trade.dao.mapper.customize.ApiProjectListCustomizeMapper;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BorrowListServiceImpl extends BaseServiceImpl implements BorrowListService {

    @Autowired
    ApiProjectListCustomizeMapper apiProjectListCustomizeMapper;

    @Override
    public List<InvestListCustomize> InvestRepaysList(Map<String, Object> params) {
        List<InvestListCustomize> repayList = apiProjectListCustomizeMapper.searchInvestListNew(params);
        return repayList;
    }
}
