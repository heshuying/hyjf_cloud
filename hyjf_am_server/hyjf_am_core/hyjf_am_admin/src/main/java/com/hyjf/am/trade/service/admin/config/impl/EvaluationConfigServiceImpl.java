/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config.impl;

import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfig;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfigExample;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfigLog;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfigLogExample;
import com.hyjf.am.trade.service.admin.config.EvaluationConfigService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetSessionOrRequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.hyjf.common.util.GetDate.datetimeFormat;

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
     * 获取风险测评-限额配置总数(操作日志)
     * @param request
     * @return
     */
    @Override
    public Integer selectEvaluationMoneyLogCount(EvaluationMoneyLogRequest request) {
        EvaluationConfigLogExample example = new EvaluationConfigLogExample();
        EvaluationConfigLogExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(2);
        if (StringUtils.isNotBlank(request.getUpdateUserSrch())) {
            criteria.andUpdateUserEqualTo(request.getUpdateUserSrch());
        }
        if (StringUtils.isNotBlank(request.getStartTimeSrch()) && StringUtils.isNotBlank(request.getEndTimeSrch())) {
            criteria.andCreateTimeBetween(GetDate.str2Date(GetDate.getDayStart(request.getStartTimeSrch()),datetimeFormat), GetDate.str2Date(GetDate.getDayEnd(request.getEndTimeSrch()),datetimeFormat));
        }
        return evaluationConfigLogMapper.countByExample(example);
    }

    /**
     * 获取风险测评-限额配置列表(操作日志)
     * @param request
     * @return
     */
    @Override
    public List<EvaluationConfigLog> selectEvaluationMoneyLogList(EvaluationMoneyLogRequest request) {
        EvaluationConfigLogExample example = new EvaluationConfigLogExample();
        EvaluationConfigLogExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(2);
        if (request.getLimitStart()!= -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        if (StringUtils.isNotBlank(request.getUpdateUserSrch())) {
            criteria.andUpdateUserEqualTo(request.getUpdateUserSrch());
        }
        if (StringUtils.isNotBlank(request.getStartTimeSrch()) && StringUtils.isNotBlank(request.getEndTimeSrch())) {
            criteria.andCreateTimeBetween(GetDate.str2Date(GetDate.getDayStart(request.getStartTimeSrch()),datetimeFormat), GetDate.str2Date(GetDate.getDayEnd(request.getEndTimeSrch()),datetimeFormat));
        }
        example.setOrderByClause("create_time DESC");
        return evaluationConfigLogMapper.selectByExample(example);
    }

    /**
     * 获取风险测评-开关配置总数(操作日志)
     * @param request
     * @return
     */
    @Override
    public Integer selectEvaluationCheckLogCount(EvaluationCheckLogRequest request) {
        EvaluationConfigLogExample example = new EvaluationConfigLogExample();
        EvaluationConfigLogExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(1);
        if (request.getLimitStart()!= -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        if (StringUtils.isNotBlank(request.getUpdateUserSrch())) {
            criteria.andUpdateUserEqualTo(request.getUpdateUserSrch());
        }
        if (StringUtils.isNotBlank(request.getStartTimeSrch()) && StringUtils.isNotBlank(request.getEndTimeSrch())) {
            criteria.andCreateTimeBetween(GetDate.str2Date(GetDate.getDayStart(request.getStartTimeSrch()),datetimeFormat), GetDate.str2Date(GetDate.getDayEnd(request.getEndTimeSrch()),datetimeFormat));
        }
        return evaluationConfigLogMapper.countByExample(example);
    }

    /**
     * 获取风险测评-开关配置列表(操作日志)
     * @param request
     * @return
     */
    @Override
    public List<EvaluationConfigLog> selectEvaluationCheckLogList(EvaluationCheckLogRequest request) {
        EvaluationConfigLogExample example = new EvaluationConfigLogExample();
        EvaluationConfigLogExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(1);
        if (request.getLimitStart()!= -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        if (StringUtils.isNotBlank(request.getUpdateUserSrch())) {
            criteria.andUpdateUserEqualTo(request.getUpdateUserSrch());
        }
        if (StringUtils.isNotBlank(request.getStartTimeSrch()) && StringUtils.isNotBlank(request.getEndTimeSrch())) {
            criteria.andCreateTimeBetween(GetDate.str2Date(GetDate.getDayStart(request.getStartTimeSrch()),datetimeFormat), GetDate.str2Date(GetDate.getDayEnd(request.getEndTimeSrch()),datetimeFormat));
        }
        example.setOrderByClause("create_time DESC");
        return evaluationConfigLogMapper.selectByExample(example);
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

            if (RedisUtils.exists(RedisConstants.REVALUATION_CONSERVATIVE_PRINCIPAL)) {
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
            }
            //获取操作之后的测评配置，保存操作日志
            EvaluationConfig record = evaluationConfigMapper.selectByPrimaryKey(request.getId());
            //填充操作内容

            //测评到期时间
            record.setValidityEvaluationDate(request.getValidityEvaluationDate());
            //成长型单笔投资限额金额
            record.setGrowupEvaluationSingleMoney(request.getGrowupEvaluationSingleMoney());
            //稳健型单笔投资限额金额
            record.setSteadyEvaluationSingleMoney(request.getSteadyEvaluationSingleMoney());
            //进取型单笔投资限额金额
            record.setEnterprisingEvaluationSinglMoney(request.getEnterprisingEvaluationSinglMoney());
            //保守型单笔投资限额金额
            record.setConservativeEvaluationSingleMoney(request.getConservativeEvaluationSingleMoney());
            //成长型代收本金限额金额
            record.setGrowupEvaluationPrincipalMoney(request.getGrowupEvaluationPrincipalMoney());
            //稳健型代收本金限额金额
            record.setSteadyEvaluationPrincipalMoney(request.getSteadyEvaluationPrincipalMoney());
            //进取型代收本金限额金额
            record.setEnterprisingEvaluationPrincipalMoney(request.getEnterprisingEvaluationPrincipalMoney());
            //保守型代收本金限额金额
            record.setConservativeEvaluationPrincipalMoney(request.getConservativeEvaluationPrincipalMoney());
            record.setUpdateUser(request.getUpdateUser());
            //新增日志表
            EvaluationConfigLog log =  new EvaluationConfigLog();
            BeanUtils.copyProperties(record,log);
            // IP地址
            String ip = GetCilentIP.getIpAddr(GetSessionOrRequestUtils.getRequest());
            log.setIp(ip);
            //1开关配置 2限额配置 3信用等级
            log.setStatus(2);
            log.setUpdateTime(new Date());
            log.setCreateTime(new Date());
            evaluationConfigLogMapper.insertSelective(log);
        }
        return true;
    }

    /**
     * 更新风险测评-开关配置
     *
     * @param request
     * @return
     */
    @Override
    public Boolean updateEvaluationCheck(EvaluationCheckRequest request) {
        EvaluationConfig evaluationConfig = new EvaluationConfig();
        BeanUtils.copyProperties(request,evaluationConfig);
        if(evaluationConfigMapper.updateByPrimaryKeySelective(evaluationConfig)>0){
            //填充操作内容
            //获取操作之后的测评配置，保存操作日志
            EvaluationConfig record = evaluationConfigMapper.selectByPrimaryKey(request.getId());
            record.setDebtEvaluationTypeCheck(request.getDebtEvaluationTypeCheck());
            record.setIntellectualCollectionEvaluationCheck(request.getIntellectualCollectionEvaluationCheck());
            record.setDeptEvaluationMoneyCheck(request.getDeptEvaluationMoneyCheck());
            record.setIntellectualEvaluationMoneyCheck(request.getIntellectualEvaluationMoneyCheck());
            record.setDeptCollectionEvaluationCheck(request.getDeptCollectionEvaluationCheck());
            record.setIntellectualCollectionEvaluationCheck(request.getIntellectualCollectionEvaluationCheck());
            record.setInvestmentEvaluationCheck(request.getInvestmentEvaluationCheck());
            record.setUpdateUser(request.getUpdateUser());
            //新增日志表
            EvaluationConfigLog log =  new EvaluationConfigLog();
            BeanUtils.copyProperties(record,log);
            // IP地址
            String ip = GetCilentIP.getIpAddr(GetSessionOrRequestUtils.getRequest());
            log.setIp(ip);
            //1开关配置 2限额配置 3信用等级
            log.setStatus(1);
            log.setUpdateTime(new Date());
            log.setCreateTime(new Date());
            evaluationConfigLogMapper.insertSelective(log);
        }
        return true;
    }

    /**
     * 获取风险测评配置风险测评等级配置数量
     *
     * @param request
     * @return
     */
    @Override
    public Integer getEvaluationBorrowLevelConfigCount(EvaluationBorrowLevelConfigRequest request) {
        EvaluationConfigExample example = new EvaluationConfigExample();
        return evaluationConfigMapper.countByExample(example);
    }

    /**
     * 获取风险测评配置风险测评等级配置列表
     *
     * @param request
     * @return
     */
    @Override
    public List<EvaluationConfig> getEvaluationBorrowLevelConfigList(EvaluationBorrowLevelConfigRequest request){
        EvaluationConfigExample example = new EvaluationConfigExample();
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        return evaluationConfigMapper.selectByExample(example);
    }


    /**
     * 更新测评等级配置
     *
     * @param request
     * @return
     */
    @Override
    public boolean updateBorrowLevelConfig(EvaluationBorrowLevelConfigRequest request){
        EvaluationConfig evaluationConfig = this.evaluationConfigMapper.selectByPrimaryKey(request.getId());
        BeanUtils.copyProperties(request,evaluationConfig);
        if(evaluationConfigMapper.updateByPrimaryKeySelective(evaluationConfig)>0){
            //填充操作内容
            //获取操作之后的测评配置，保存操作日志
            EvaluationConfig record = evaluationConfigMapper.selectByPrimaryKey(request.getId());
            record.setBbbEvaluationProposal(request.getBbbEvaluationProposal());
            record.setAa0EvaluationProposal(request.getAa0EvaluationProposal());
            record.setAa1EvaluationProposal(request.getAa1EvaluationProposal());
            record.setAa2EvaluationProposal(request.getAa2EvaluationProposal());
            record.setAaaEvaluationProposal(request.getAaaEvaluationProposal());
            record.setUpdateUser(request.getUpdateUser());
            // 新增日志表
            EvaluationConfigLog log =  new EvaluationConfigLog();
            BeanUtils.copyProperties(record,log);
            // IP地址
            String ip = GetCilentIP.getIpAddr(GetSessionOrRequestUtils.getRequest());
            log.setIp(ip);
            //1开关配置 2限额配置 3信用等级
            log.setStatus(3);
            log.setUpdateTime(new Date());
            log.setCreateTime(new Date());
            evaluationConfigLogMapper.insertSelective(log);
        }
        return true;
    }

    /**
     * 查询风险测评配置日志件数
     *
     * @param request
     * @return
     */
    @Override
    public Integer getBorrowLevelConfigLogListCount(EvaluationBorrowLevelConfigLogRequest request){
        EvaluationConfigLogExample example = new EvaluationConfigLogExample();
        EvaluationConfigLogExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(3);
        if (StringUtils.isNotBlank(request.getUpdateUserSrch())) {
            cra.andUpdateUserEqualTo(request.getUpdateUserSrch());
        }
        if (StringUtils.isNotBlank(request.getStartTimeSrch()) && StringUtils.isNotBlank(request.getEndTimeSrch())) {
            cra.andCreateTimeBetween(GetDate.str2Date(GetDate.getDayStart(request.getStartTimeSrch()),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")), GetDate.str2Date(GetDate.getDayEnd(request.getEndTimeSrch()),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        }
        return evaluationConfigLogMapper.countByExample(example);
    }


    /**
     * 查询风险测评配置日志列表
     *
     * @param request
     * @return
     */
    @Override
    public List<EvaluationConfigLog> getBorrowLevelConfigLogList(EvaluationBorrowLevelConfigLogRequest request){
        EvaluationConfigLogExample example = new EvaluationConfigLogExample();
        EvaluationConfigLogExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(3);
        if (request.getLimitStart()!= -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        if (StringUtils.isNotBlank(request.getUpdateUserSrch())) {
            cra.andUpdateUserEqualTo(request.getUpdateUserSrch());
        }
        if (StringUtils.isNotBlank(request.getStartTimeSrch()) && StringUtils.isNotBlank(request.getEndTimeSrch())) {
            cra.andCreateTimeBetween(GetDate.str2Date(GetDate.getDayStart(request.getStartTimeSrch()),datetimeFormat), GetDate.str2Date(GetDate.getDayEnd(request.getEndTimeSrch()),datetimeFormat));
        }
        // 条件查询
        example.setOrderByClause("create_time desc");
        return evaluationConfigLogMapper.selectByExample(example);
    }
}
