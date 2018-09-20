package com.hyjf.am.trade.service.api.UserInvest;

import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;

import java.util.List;

/**
 * @version UserInvestService, v0.1 2018/9/1 15:02
 * @Author: Zha Daojian
 */
public interface UserInvestService {
    /**
     * 获取回款记录信息
     * @author Zha Daojian
     * @date 2018/9/1 13:20
     * @param bean
     * @return List<RepayListCustomize>
     **/
    List<ApiRepayListCustomizeVO> searchRepayList(ApiRepayListRequest bean);
}
