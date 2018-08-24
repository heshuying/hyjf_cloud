/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.recharge;

import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.cs.trade.bean.WxRechargeDescResultBean;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @author wangjun
 * @version WechatRechargeRuleService, v0.1 2018/7/26 9:25
 */
public interface WechatRechargeRuleService extends BaseTradeService{
    /**
     * wechat端获取充值规则
     *
     * @return
     */
    WxRechargeDescResultBean getRechargeRule();

    /**
     * 获取银行卡配置信息
     * @param bankId
     * @return
     */
    JxBankConfigVO getBanksConfigByBankId(Integer bankId);

    /**
     * 根据用户ID查询企业用户账户信息
     * @param userId
     * @return
     */
    CorpOpenAccountRecordVO getCorpOpenAccountRecord(Integer userId);
}
