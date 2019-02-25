/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.trade.EvaluationConfigVO;
import com.hyjf.am.vo.user.EvalationCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.StringUtil;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version HomePageServiceImpl, v0.1 2018/7/26 10:22
 */
@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private AmMarketClient amMarketClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 查询首页banner
     * @param ads
     * @return
     */
    @Override
    public List<AppAdsCustomizeVO> searchBannerList(Map<String, Object> ads) {
        return amMarketClient.searchBannerList(ads);
    }

    /**
     * 获取评分标准列表
     * @return
     * @author Michael
     */
    @Override
    public List<EvalationCustomizeVO> getEvalationRecord() {
        List<EvalationCustomizeVO> evalationVOList = amUserClient.getEvalationRecord();
        //查询测评配置
        EvaluationConfigVO evalConfig = new EvaluationConfigVO();
        List<EvaluationConfigVO> evalConfigList = amTradeClient.selectEvaluationConfig(evalConfig);
        if(evalConfigList != null && evalConfigList.size() > 0){
            evalConfig = evalConfigList.get(0);
        }
        for(EvalationCustomizeVO evalStr : evalationVOList){
            //初始化金额返回值
            String revaluation_money;
            switch (evalStr.getEvalType()) {
                case "保守型":
                    //从redis获取金额（单笔）
                    revaluation_money = StringUtil.getTenThousandOfANumber(Double.valueOf(
                            RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE)).intValue());
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getConservativeEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getConservativeEvaluationSingleMoney());
                    }
                    evalStr.setRevaluationMoney(revaluation_money);
                    break;
                case "稳健型":
                    //从redis获取金额（单笔）
                    revaluation_money = StringUtil.getTenThousandOfANumber(Double.valueOf(
                            RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS)).intValue());
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getSteadyEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getSteadyEvaluationSingleMoney());
                    }
                    evalStr.setRevaluationMoney(revaluation_money);
                    break;
                case "成长型":
                    //从redis获取金额（单笔）
                    revaluation_money = StringUtil.getTenThousandOfANumber(Double.valueOf(
                            RedisUtils.get(RedisConstants.REVALUATION_GROWTH) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_GROWTH)).intValue());
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getGrowupEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getGrowupEvaluationSingleMoney());
                    }
                    evalStr.setRevaluationMoney(revaluation_money);
                    break;
                case "进取型":
                    //从redis获取金额（单笔）
                    revaluation_money = StringUtil.getTenThousandOfANumber(Double.valueOf(
                            RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE) == null ? "0": RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE)).intValue());
                    //如果reids不存在或者为零尝试获取数据库（单笔）
                    if ("0".equals(revaluation_money)) {
                        revaluation_money = evalConfig.getEnterprisingEvaluationSinglMoney() == null ? "0" : String.valueOf(evalConfig.getEnterprisingEvaluationSinglMoney());
                    }
                    evalStr.setRevaluationMoney(revaluation_money);
                    break;
                default:
                    evalStr.setRevaluationMoney("0");
            }
        }
        return evalationVOList;
    }
}
