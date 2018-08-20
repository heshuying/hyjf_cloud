package com.hyjf.admin.service.finance.recharge;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.vo.trade.BanksConfigVO;

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
    List<BanksConfigVO> getBankcardList();

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
     AccountRechargeResponse queryRechargeList(AccountRechargeRequest request);

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
    boolean updateAccountAfterRecharge(AccountRechargeRequest request);
}
