package com.hyjf.am.trade.service.api.impl;

import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.trade.service.api.UserInvest.UserInvestService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;

import java.util.List;

/**
 * @version UserInvestServiceImpl, v0.1 2018/9/1 15:02
 * @Author: Zha Daojian
 */
public class UserInvestServiceImpl  extends BaseServiceImpl implements UserInvestService {
    /**
     * 获取回款记录信息
     *
     * @param request
     * @return List<RepayListCustomize>
     * @author Zha Daojian
     * @date 2018/9/1 13:20
     **/
    @Override
    public List<ApiRepayListCustomizeVO> searchRepayList(ApiRepayListRequest request) {
        return apiRepayListCustomizeMapper.searchRepayList(request);
    }
}
