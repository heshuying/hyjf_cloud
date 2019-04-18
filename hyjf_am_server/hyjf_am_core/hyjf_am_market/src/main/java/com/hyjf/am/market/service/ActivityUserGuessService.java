package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.ActivityUserGuess;
import com.hyjf.am.resquest.admin.ActivityUserGuessRequest;
import com.hyjf.am.vo.admin.ActivityUserGuessVO;

import java.util.List;

/**
 * @author xiasq
 * @version ActivityUserGuessService, v0.1 2019-04-17 17:20
 */
public interface ActivityUserGuessService {

    /**
     * 保存
     * @param userId
     * @param guess
     * @return
     */
    int insertActivityUserGuess(int userId, int guess);

    /**
     * 查询用户是否已经竞猜
     * @param userId
     * @return
     */
    ActivityUserGuess selectByUserId(int userId);

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
    List<ActivityUserGuessVO> getGuessList(ActivityUserGuessRequest request, int offset, int limit);
}
