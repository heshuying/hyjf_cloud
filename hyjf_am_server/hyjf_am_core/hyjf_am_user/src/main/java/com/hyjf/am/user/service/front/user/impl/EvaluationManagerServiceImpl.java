/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.model.auto.UserEvalationResult;
import com.hyjf.am.user.dao.model.auto.UserEvalationResultExample;
import com.hyjf.am.user.dao.model.customize.EvalationResultCustomize;
import com.hyjf.am.user.dao.model.customize.UserEvalationQuestionCustomize;
import com.hyjf.am.user.service.front.user.EvaluationManagerService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 *          后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class EvaluationManagerServiceImpl extends BaseServiceImpl implements EvaluationManagerService {

    /**
     * 根据筛选条件查找会员列表
     *
     * @param mapParam 筛选条件
     * @return
     */
    @Override
    public List<EvalationResultCustomize> selectUserEvalationResultList(Map<String,Object> mapParam, int limitStart, int limitEnd){
        // 封装查询条件
        if (limitStart == 0 || limitStart > 0) {
            mapParam.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            mapParam.put("limitEnd", limitEnd);
        }
        List<EvalationResultCustomize> listUser = evaluationManagerCustomizeMapper.selectUserEvalationResultList(mapParam);
        if (CollectionUtils.isNotEmpty(listUser)) {
            //
            Map<String, String> userProperty = CacheUtil.getParamNameMap("USER_PROPERTY");
            Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
            if(null!=listUser&&listUser.size()>0){
                for (EvalationResultCustomize  evalationResultCustomize : listUser) {
                    evalationResultCustomize.setAccountStatus(accountStatus.getOrDefault(evalationResultCustomize.getAccountStatus(), null));
                    evalationResultCustomize.setUserProperty(userProperty.getOrDefault(evalationResultCustomize.getUserProperty(), null));
                }
            }
        }
        return listUser;
    }
    /**
     * 根据条件查询测评总数
     *
     * @return
     */
    @Override
    public int countEvalationResultRecord(Map<String, Object> mapParam){
        return evaluationManagerCustomizeMapper.countEvalationResultRecord(mapParam);
    }

    /**
     * 根据用户Id获取用户测评结果
     */
    @Override
    public UserEvalationResult selectUserEvalationResultByUserId(int userId){
        UserEvalationResultExample example = new UserEvalationResultExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserEvalationResult> userEvalationResult = userEvalationResultMapper.selectByExample(example);
        if (userEvalationResult != null && userEvalationResult.size() > 0) {
            return userEvalationResult.get(0);
        } else {
            return null;
        }
    }
    /**
     * 根据id查找用户测评的问题与答案
     * @param evalationId
     * @return
     */
    @Override
    public List<UserEvalationQuestionCustomize> getUserQuestionInfoById(int evalationId){
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("id",evalationId);
        List<UserEvalationQuestionCustomize>  userEvalationQuestionCustomizeList = evaluationManagerCustomizeMapper.getUserEvalation(mapParam);
        return userEvalationQuestionCustomizeList;
    }
}
