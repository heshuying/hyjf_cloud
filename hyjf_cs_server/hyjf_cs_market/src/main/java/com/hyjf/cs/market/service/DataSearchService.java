package com.hyjf.cs.market.service;

import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;

import java.util.List;

/**
 * @author lisheng
 * @version DataSearchService, v0.1 2018/8/21 9:38
 */

public interface DataSearchService {
    List<DataSearchCustomizeVO> findDataList(DataSearchRequest dataSearchRequest);
}
