package com.hyjf.cs.market.service;

import com.hyjf.am.response.trade.DataSearchCustomizeResponse;
import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;

import java.util.List;

/**
 * @author lisheng
 * @version DataSearchService, v0.1 2018/8/21 9:38
 */

public interface DataSearchService {
    DataSearchCustomizeResponse findDataList(DataSearchRequest dataSearchRequest);
}
