/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config.impl;

import com.hyjf.am.resquest.admin.BailConfigAddRequest;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.resquest.admin.EvaluationCheckRequest;
import com.hyjf.am.resquest.admin.EvaluationMoneyRequest;
import com.hyjf.am.trade.dao.mapper.auto.EvaluationConfigMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.config.BailConfigService;
import com.hyjf.am.trade.service.admin.config.EvaluationConfigService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import com.hyjf.am.vo.admin.EvaluationConfigVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetSessionOrRequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 风险测评配置
 * @author Zha Daojian
 * @date 2018/12/24 10:28
 * @param
 * @return
 **/
@Service
public class EvaluationConfigServiceImpl extends BaseServiceImpl implements EvaluationConfigService {

    /**
     * 获取风险测评-限额配置总数
     * @param request
     * @return
     */
    @Override
    public Integer selectEvaluationMoneyCount(EvaluationMoneyRequest request) {
        EvaluationConfigExample example = new EvaluationConfigExample();
        return evaluationConfigMapper.countByExample(example);
    }

    /**
     * 获取风险测评-限额配置列表
     * @param request
     * @return
     */
    @Override
    public List<EvaluationConfig> selectEvaluationMoneyList(EvaluationMoneyRequest request) {
        EvaluationConfigExample example = new EvaluationConfigExample();
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        return evaluationConfigMapper.selectByExample(example);
    }

    /**
     * 获取风险测评-限额配置总数
     * @param request
     * @return
     */
    @Override
    public Integer selectEvaluationCheckCount(EvaluationCheckRequest request) {
        EvaluationConfigExample example = new EvaluationConfigExample();
        return evaluationConfigMapper.countByExample(example);
    }

    /**
     * 获取风险测评-限额配置列表
     * @param request
     * @return
     */
    @Override
    public List<EvaluationConfig> selectEvaluationCheckList(EvaluationCheckRequest request) {
        EvaluationConfigExample example = new EvaluationConfigExample();
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        return evaluationConfigMapper.selectByExample(example);
    }

    /**
     * 根据主键获取风险测评-开关配置
     *
     * @param id
     * @return
     */
    @Override
    public EvaluationConfig selectEvaluationCheckById(Integer id) {
        return evaluationConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键获取风险测评-限额配置
     *
     * @param id
     * @return
     */
    @Override
    public EvaluationConfig selectEvaluationMoneyById(Integer id) {
        return evaluationConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新风险测评-限额配置
     *
     * @param request
     * @return
     */
    @Override
    public Boolean updateEvaluationMoney(EvaluationMoneyRequest request) {
        EvaluationConfig evaluationConfig = new EvaluationConfig();
        BeanUtils.copyProperties(request,evaluationConfig);
        evaluationConfig.setUpdateUser("");
        evaluationConfig.setUpdateTime(new Date());
        if(evaluationConfigMapper.updateByPrimaryKeySelective(evaluationConfig)>0){

            /**********************Redis处理***********************************/
            //测评到期时间
            if (RedisUtils.exists(RedisConstants.REVALUATION_EXPIRED_DAY)) {
                //测评到期时间
                RedisUtils.set(RedisConstants.REVALUATION_EXPIRED_DAY, request.getValidityEvaluationDate()+"");
            }

            if (RedisUtils.exists(RedisConstants.REVALUATION_CONSERVATIVE)) {
                //保守型单笔投资限额金额
                RedisUtils.set(RedisConstants.REVALUATION_CONSERVATIVE, request.getConservativeEvaluationSingleMoney()+"");
            }
            if (RedisUtils.exists(RedisConstants.REVALUATION_ROBUSTNESS)) {
                //稳健型单笔投资限额金额
                RedisUtils.set(RedisConstants.REVALUATION_ROBUSTNESS, request.getSteadyEvaluationSingleMoney()+"");
            }
            if (RedisUtils.exists(RedisConstants.REVALUATION_GROWTH)) {
                //成长型单笔投资限额金额
                RedisUtils.set(RedisConstants.REVALUATION_GROWTH, request.getGrowupEvaluationSingleMoney()+"");
            }
            if (RedisUtils.exists(RedisConstants.REVALUATION_AGGRESSIVE)) {
                //保进取型单笔投资限额金额
                RedisUtils.set(RedisConstants.REVALUATION_AGGRESSIVE, request.getEnterprisingEvaluationSinglMoney()+"");
            }

           /* if (RedisUtils.exists(RedisConstants.REVALUATION_CONSERVATIVE_PRINCIPAL)) {
                //保守型代收本金限额金额
                RedisUtils.set(RedisConstants.REVALUATION_CONSERVATIVE_PRINCIPAL, request.getConservativeEvaluationPrincipalMoney()+"");
            }
            if (RedisUtils.exists(RedisConstants.REVALUATION_ROBUSTNESS_PRINCIPAL)) {
                //稳健型代收本金限额金额
                RedisUtils.set(RedisConstants.REVALUATION_ROBUSTNESS_PRINCIPAL, request.getSteadyEvaluationPrincipalMoney()+"");
            }
            if (RedisUtils.exists(RedisConstants.REVALUATION_GROWTH_PRINCIPAL)) {
                //成长型代收本金限额金额
                RedisUtils.set(RedisConstants.REVALUATION_GROWTH_PRINCIPAL, request.getGrowupEvaluationPrincipalMoney()+"");
            }
            if (RedisUtils.exists(RedisConstants.REVALUATION_AGGRESSIVE_PRINCIPAL)) {
                //保进取型代收本金限额金额
                RedisUtils.set(RedisConstants.REVALUATION_AGGRESSIVE_PRINCIPAL, request.getEnterprisingEvaluationPrincipalMoney()+"");
            }*/
            //新增日志表
            EvaluationConfigLog log =  new EvaluationConfigLog();
            BeanUtils.copyProperties(request,log);
            // IP地址
            String ip = GetCilentIP.getIpAddr(GetSessionOrRequestUtils.getRequest());
            // 当前登录用户id
            String username ="";
            //操作人
            log.setUpdateUser(username);
            log.setIp(ip);
            //1开关配置 2限额配置 3信用等级
            log.setStatus(2);
            log.setUpdateTime(new Date());
            evaluationConfigLogMapper.insert(log);
        }
        return true;
    }

    /**
     * 更新风险测评-限额配置
     *
     * @param request
     * @return
     */
    @Override
    public Boolean updateEvaluationCheck(EvaluationCheckRequest request) {
        EvaluationConfig evaluationConfig = new EvaluationConfig();
        BeanUtils.copyProperties(request,evaluationConfig);
        evaluationConfig.setUpdateUser("");
        evaluationConfig.setUpdateTime(new Date());
        if(evaluationConfigMapper.updateByPrimaryKeySelective(evaluationConfig)>0){
            //新增日志表
            EvaluationConfigLog log =  new EvaluationConfigLog();
            BeanUtils.copyProperties(request,log);
            // IP地址
            String ip = GetCilentIP.getIpAddr(GetSessionOrRequestUtils.getRequest());
            // 当前登录用户id
            String username ="";
            //操作人
            log.setUpdateUser(username);
            log.setIp(ip);
            //1开关配置 2限额配置 3信用等级
            log.setStatus(1);
            log.setUpdateTime(new Date());
            evaluationConfigLogMapper.insert(log);
        }
        return true;
    }

}
