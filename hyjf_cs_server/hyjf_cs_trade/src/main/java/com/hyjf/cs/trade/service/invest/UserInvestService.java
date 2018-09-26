package com.hyjf.cs.trade.service.invest;

import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.cs.common.service.BaseService;

import java.util.List;

/**
 * 第三方查询投资信息
 * @version UserInvestService, v0.1 2018/9/1 13:18
 * @Author: Zha Daojian
 */
public interface UserInvestService extends BaseService {

    /**
     * 获取回款记录信息
    * @author Zha Daojian
    * @date 2018/9/1 13:20
    * @param request
    * @return List<RepayListCustomize>
    **/
    List<ApiRepayListCustomizeVO> searchRepayList(ApiRepayListRequest request);
}
