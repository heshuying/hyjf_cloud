/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.UserPortraitScoreResponse;
import com.hyjf.am.resquest.admin.UserPortraitScoreRequest;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author yaoyong
 * @version UserPortraitScoreSerivce, v0.1 2018/8/9 11:39
 */
public interface UserPortraitScoreSerivce {
    /**
     * 获取用户画像评分列表
     * @param request
     * @return
     */
    UserPortraitScoreResponse selectRecordList(UserPortraitScoreRequest request);

    /**
     * 获取字典表数据
     * @param other1
     * @return
     */
    List<ParamNameVO> getParamNameList(String other1);

    /**
     * 查询用户充值记录
     * @param userId
     * @return
     */
    List<AccountRechargeVO> getAccountRecharge(int userId);
}
