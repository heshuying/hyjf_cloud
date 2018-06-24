/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.BankcardManagerVO;

import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClient, v0.1 2018/6/20 15:37
 */
public interface BankCardManagerClient {

    /**
     * 获取银行列表
     *
     * @param request
     * @return
     */
    List<BanksConfigVO> selectBankConfigList();

    /**
     *  根据筛选条件查找汇付银行卡信息列表
     * @param request 筛选条件
     * @return
     */
    List<BankcardManagerVO> selectBankCardList(BankCardManagerRequest request);

    /**
     * 根据筛选条件查找江西银行卡信息列表
     * @return
     */
    List<BankcardManagerVO> selectNewBankCardList (BankCardManagerRequest request);
}