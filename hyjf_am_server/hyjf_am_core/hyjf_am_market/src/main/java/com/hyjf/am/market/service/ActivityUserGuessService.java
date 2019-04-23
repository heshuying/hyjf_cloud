package com.hyjf.am.market.service;

import com.hyjf.am.market.dao.model.auto.ActivityUserGuess;

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
}
