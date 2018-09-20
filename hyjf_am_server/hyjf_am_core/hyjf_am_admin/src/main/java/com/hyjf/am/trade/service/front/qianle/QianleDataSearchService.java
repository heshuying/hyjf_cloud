package com.hyjf.am.trade.service.front.qianle;

import com.hyjf.am.resquest.trade.DataSearchRequest;
import com.hyjf.am.vo.trade.DataSearchCustomizeVO;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version QianleDataSearchService, v0.1 2018/8/24 9:37
 */

public interface QianleDataSearchService {
    List<DataSearchCustomizeVO> querySanList(DataSearchRequest dataSearchRequest,Integer offset,Integer limit);
    List<DataSearchCustomizeVO> queryPlanList(DataSearchRequest dataSearchRequest,Integer offset,Integer limit);
    List<DataSearchCustomizeVO> queryList(DataSearchRequest dataSearchRequest,Integer offset,Integer limit);
    Integer queryPlanCount(DataSearchRequest dataSearchRequest);
    Integer querySanCount(DataSearchRequest dataSearchRequest);
    Integer queryCount(DataSearchRequest dataSearchRequest);

    Map<String, Object> querySanMoney(DataSearchRequest dataSearchRequest);
    Map<String, Object> queryPlanMoney(DataSearchRequest dataSearchRequest);
    Map<String, Object> queryFirstTender(Integer userId);
}
