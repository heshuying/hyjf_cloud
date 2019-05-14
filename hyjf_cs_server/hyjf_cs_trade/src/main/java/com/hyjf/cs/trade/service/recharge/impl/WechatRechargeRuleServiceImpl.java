/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.recharge.impl;

import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.cs.trade.bean.WxRechargeDescResultBean;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.recharge.WechatRechargeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangjun
 * @version WechatRechargeRuleServiceImpl, v0.1 2018/7/26 9:25
 */
@Service
public class WechatRechargeRuleServiceImpl extends BaseTradeServiceImpl implements WechatRechargeRuleService {
    @Autowired
    AmConfigClient amConfigClient;

    /**
     * wechat端获取充值规则
     *
     * @return
     */
    @Override
    public WxRechargeDescResultBean getRechargeRule(){
        WxRechargeDescResultBean result = new WxRechargeDescResultBean();
        //获取江西银行配置(快捷支付)
        List<JxBankConfigVO> jxBankConfigVOList = amConfigClient.getQuickPaymentJxBankConfig();
        if (!CollectionUtils.isEmpty(jxBankConfigVOList)) {
            this.conventBanksConfigToResult(result, jxBankConfigVOList);
        }
        return result;
    }

    @Override
    public JxBankConfigVO getBanksConfigByBankId(Integer bankId) {
        return amConfigClient.getBankNameByBankId(bankId+"");
    }

    @Override
    public CorpOpenAccountRecordVO getCorpOpenAccountRecord(Integer userId) {
        return amUserClient.selectCorpOpenAccountRecordByUserId(userId);
    }

    /**
     * 限额编辑
     * @param result
     * @param jxBankConfigVOList
     */
    private void conventBanksConfigToResult(WxRechargeDescResultBean result, List<JxBankConfigVO> jxBankConfigVOList) {
        WxRechargeDescResultBean.RechargeLimitAmountDesc bean = null;
        List<WxRechargeDescResultBean.RechargeLimitAmountDesc> list = result.getList();
        for (JxBankConfigVO config : jxBankConfigVOList) {
            bean = new WxRechargeDescResultBean.RechargeLimitAmountDesc();
            //银行名称
            bean.setBankName(config.getBankName());
            //单卡单日限额
            if(config.getSingleCardQuota().compareTo(BigDecimal.ZERO)==0){
                bean.setDay("不限");
            }else{
                bean.setDay(String.valueOf(config.getSingleCardQuota().divide(new BigDecimal(10000)))+"万");
            }
            //单笔限额
            if(config.getSingleQuota().compareTo(BigDecimal.ZERO)==0){
                bean.setOnce("不限");
            }else{
                bean.setOnce(String.valueOf(config.getSingleQuota().divide(new BigDecimal(10000)))+"万");
            }
            //单月限额
            if(config.getMonthCardQuota().compareTo(BigDecimal.ZERO)==0){
                bean.setMonth("不限");
            }else{
                bean.setMonth(String.valueOf(config.getMonthCardQuota().divide(new BigDecimal(10000)))+"万");
            }

            // AB测试添加银行ICON  Add by huanghui
            if (config.getBankIcon() != null){
                bean.setBankIcon(config.getBankIcon());
            }
            list.add(bean);
        }
    }
}
