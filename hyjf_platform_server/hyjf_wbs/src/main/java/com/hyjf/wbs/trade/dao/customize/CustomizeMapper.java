package com.hyjf.wbs.trade.dao.customize;

import com.hyjf.wbs.trade.dao.auto.AutoMapper;
import com.hyjf.wbs.trade.dao.mapper.customize.BorrowCustomizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("wbsTradeCustomizeMapper")
public class CustomizeMapper extends AutoMapper {

    @Autowired
    protected BorrowCustomizeMapper borrowCustomizeMapper;
}
