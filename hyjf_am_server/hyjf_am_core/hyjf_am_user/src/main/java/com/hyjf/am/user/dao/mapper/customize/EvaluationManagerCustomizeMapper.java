/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.EvalationResultCustomize;
import com.hyjf.am.user.dao.model.customize.UserEvalationQuestionCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version UserManagerCustomizeMapper, v0.1 2018/6/20 10:01
 */
public interface EvaluationManagerCustomizeMapper {
    /**
     * 根据筛选条件查找测评信息
     * @param mapParam
     * @return
     */
    List<EvalationResultCustomize> selectUserEvalationResultList(Map<String,Object> mapParam);
    /**
     * 根据条件查询测评总数
     *
     * @return
     */
    int countEvalationResultRecord(Map<String, Object> mapParam);

    /**
     * 查找用户测评问题及答案
     * @param evalationId
     * @return
     */
    List<UserEvalationQuestionCustomize> getUserEvalation(Map<String, Object> mapParam);
}
