package com.hyjf.admin.service.finance.recharge;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.trade.account.AccountRechargeCustomizeResponse;
import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;

import java.util.List;

/**
 * 资金中心 -> 充值管理
 * @Author : huanghui
 */
public interface AccountRechargeService extends BaseService {

    /**
     * 获取银行列表
     * @return
     */
    List<JxBankConfigVO> getBankcardList();

    /**
     * 充值管理(账户数量)
     * @param rechargeCustomize
     * @return
     */
//    Integer queryRechargeCount(RechargeCustomize rechargeCustomize);

    /**
     * 充值管理列表
     * @param request
     * @return
     */
    AccountRechargeCustomizeResponse queryRechargeList(AccountRechargeRequest request);

    /**
     * 更新充值状态
     * @param userId
     * @param nid
     * @return
     * @throws Exception
     */
    boolean updateRechargeStatus(Integer userId, String nid);

    /**
     * 充值掉单后,更新用户的账户信息
     * @param request
     * @return
     */
    AccountRechargeCustomizeResponse updateAccountAfterRecharge(AccountRechargeRequest request);

    List<ParamNameVO> selectParamNameList(String nameClass);
}
