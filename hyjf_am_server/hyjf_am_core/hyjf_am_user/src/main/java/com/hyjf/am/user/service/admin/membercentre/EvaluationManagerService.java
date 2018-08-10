/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.membercentre;

import com.hyjf.am.user.dao.model.auto.UserEvalationResult;
import com.hyjf.am.user.dao.model.customize.EvalationResultCustomize;
import com.hyjf.am.user.dao.model.customize.UserEvalationQuestionCustomize;
import com.hyjf.am.user.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerService, v0.1 2018/6/20 9:47
 *          后台管理系统：会员中心->会员管理
 */
public interface EvaluationManagerService extends BaseService{

    /**
     * 根据筛选条件查找测评信息
     * @param mapParam
     * @return
     */
    List<EvalationResultCustomize> selectUserEvalationResultList(Map<String,Object> mapParam, int limitStart, int limitEnd);

    /**
     * 根据条件查询测评总数
     *
     * @return
     */
    int countEvalationResultRecord(Map<String, Object> mapParam);
    /**
     * 根据用户Id获取用户测评结果
     */
    UserEvalationResult selectUserEvalationResultByUserId(int userId);
    /**
     * 根据id查找用户测评的问题与答案
     * @param evalationId
     * @return
     */
    List<UserEvalationQuestionCustomize> getUserQuestionInfoById(int evalationId);
}
