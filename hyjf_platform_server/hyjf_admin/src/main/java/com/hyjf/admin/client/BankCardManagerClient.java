/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.user.BankCardLogResponse;
import com.hyjf.am.response.user.BankCardManagerResponse;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.vo.trade.BanksConfigVO;

import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClient, v0.1 2018/6/20 15:37
 */
public interface BankCardManagerClient {

    /**
     * 获取银行列表
     * @author nixiaoling
     * @return
     */
    List<BanksConfigVO> selectBankConfigList();

    /**
     *  根据筛选条件查找汇付银行卡信息列表
     * @param request 筛选条件
     * @return
     */
    BankCardManagerResponse selectBankCardList(BankCardManagerRequest request);

    /**
     * 根据筛选条件查找江西银行卡信息列表
     * @return
     */
    BankCardManagerResponse selectNewBankCardList (BankCardManagerRequest request);
    /**
     * 查找用户银行卡操作记录表
     * @param request
     * @return
     */
    BankCardLogResponse selectBankCardLogByExample(BankCardLogRequest request);
}