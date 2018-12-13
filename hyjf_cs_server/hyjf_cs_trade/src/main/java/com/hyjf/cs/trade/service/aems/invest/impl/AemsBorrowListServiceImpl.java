/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.aems.invest.impl;

import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;
import com.hyjf.cs.trade.service.aems.invest.AemsBorrowListService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AEMS系统:第三方查询投资记录Service实现类
 *
 * @author liuyang
 * @version AemsBorrowListServiceImpl, v0.1 2018/12/13 17:05
 */
@Service
public class AemsBorrowListServiceImpl extends BaseTradeServiceImpl implements AemsBorrowListService {
    @Override
    public List<ApiRepayListCustomizeVO> searchRepayList(ApiRepayListRequest request) {
        return amTradeClient.searchRepayList(request);
    }
}
