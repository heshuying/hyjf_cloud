package com.hyjf.am.market.service;

import com.hyjf.am.resquest.admin.ActivityUserGuessRequest;
import com.hyjf.am.vo.admin.AdminActivityUserGuessVO;

import java.util.List;

/**
 * @author xiasq
 * @version ActivityUserGuessService, v0.1 2019-04-17 17:20
 */
public interface ActivityUserGuessService {

    /**
     * 查询竞猜列表数据条数
     * @param request
     * @return
     */
    int getGuessListCount(ActivityUserGuessRequest request);

    /**
     * 查询竞猜列表
     * @param request
     * @param offset
     * @param limit
     * @return
     */
    List<AdminActivityUserGuessVO> getGuessList(ActivityUserGuessRequest request, int offset, int limit);
}
