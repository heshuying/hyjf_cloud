/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.recharge.impl;

import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.cs.trade.bean.app.AppRechargeDescResultBean;
import com.hyjf.cs.trade.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.service.recharge.AppRechargeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangjun
 * @version AppRechargeRuleServiceImpl, v0.1 2018/7/25 15:05
 */
@Service
public class AppRechargeRuleServiceImpl implements AppRechargeRuleService {
    @Autowired
    AmConfigClient amConfigClient;

    /**
     * app端获取充值规则
     *
     * @return
     */
    @Override
    public AppRechargeDescResultBean getRechargeRule(){
        AppRechargeDescResultBean result = new AppRechargeDescResultBean();
        //获取江西银行配置(快捷支付)
        List<JxBankConfigVO> jxBankConfigVOList = amConfigClient.getQuickPaymentJxBankConfig();
        if (!CollectionUtils.isEmpty(jxBankConfigVOList)) {
            this.conventBanksConfigToResult(result, jxBankConfigVOList);
        } else {
            result.setStatus(BaseResultBeanFrontEnd.FAIL);
            result.setStatusDesc("获取充值规则失败。");
        }
        return result;
    }

    /**
     * 限额
     * @param result
     * @param jxBankConfigVOList
     */
    private void conventBanksConfigToResult(AppRechargeDescResultBean result, List<JxBankConfigVO> jxBankConfigVOList) {
        List<AppRechargeDescResultBean.RechargeLimitAmountDesc> list = result.getList();
        AppRechargeDescResultBean.RechargeLimitAmountDesc bean = null;
        for (JxBankConfigVO config : jxBankConfigVOList) {
            bean = new AppRechargeDescResultBean.RechargeLimitAmountDesc();
            //银行名称
            bean.setBankName(config.getBankName());
            //单笔限额
            bean.setOnce((BigDecimal.ZERO.compareTo(config.getSingleQuota())==0)?"不限":String.valueOf(config.getSingleQuota().divide(new BigDecimal(10000))) + "万");
            //单卡单日限额
            bean.setDay((BigDecimal.ZERO.compareTo(config.getSingleCardQuota())==0)?"不限":String.valueOf(config.getSingleCardQuota().divide(new BigDecimal(10000))) + "万");
            //单月限额
            BigDecimal month = config.getMonthCardQuota()==null?BigDecimal.ZERO:config.getMonthCardQuota().divide(new BigDecimal(10000));
            bean.setMonth((BigDecimal.ZERO.compareTo(month)==0)?"不限":String.valueOf(month) + "万");
            // app4.0添加银行ICON  Add by wgx
            if (config.getBankIcon() != null){
                bean.setBankIcon(config.getBankIcon());
            }
            list.add(bean);
        }
    }
}
