package com.hyjf.am.trade.dao.auto;


import com.hyjf.am.trade.dao.mapper.auto.BorrowInfoMapper;
import com.hyjf.am.trade.dao.mapper.auto.BorrowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoMapper {

    @Autowired
    protected BorrowMapper borrowMapper;

    @Autowired
    protected BorrowInfoMapper borrowInfoMapper;
}

