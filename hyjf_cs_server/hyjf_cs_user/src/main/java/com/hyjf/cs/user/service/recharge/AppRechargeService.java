/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.recharge;

import com.hyjf.am.vo.app.recharge.AppRechargeLimitVO;
import com.hyjf.am.vo.app.recharge.AppRechargeRuleVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.cs.user.service.BaseUserService;

import javax.jws.WebResult;
import java.util.List;

/**
 * @author fq
 * @version AppRechargeService, v0.1 2018/7/30 9:45
 */
public interface AppRechargeService extends BaseUserService {

    /**
     * 根据userId查询银行卡信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    BankCardVO selectBankCardByUserId(Integer userId);

    /**
     * 根据id查询银行配置
     * @auth sunpeikai
     * @param bankId 主键id
     * @return
     */
    JxBankConfigVO getJxBankConfigByBankId(Integer bankId);

    /**
     * 获取充值规则
     * @return
     * @author wgx
     */
    List<AppRechargeRuleVO> getRechargeRule();

    /**
     * 获取充值限额说明
     * @return
     * @author wgx
     */
    List<AppRechargeLimitVO> getRechargeLimit();
}
