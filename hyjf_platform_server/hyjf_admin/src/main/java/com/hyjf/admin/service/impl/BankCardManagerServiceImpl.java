/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.BankCardManagerClient;
import com.hyjf.admin.service.BankCardManagerService;
import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.user.BankcardManagerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class BankCardManagerServiceImpl implements BankCardManagerService {

    @Autowired
    private BankCardManagerClient bankCardManagerClient;


    /**
     * 获取银行列表
     *
     * @return
     */
    @Override
    public List<BanksConfigVO> selectBankConfigList() {
        return bankCardManagerClient.selectBankConfigList();
    }

    /**
     * 根据筛选条件查找汇付银行卡信息列表
     *
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<BankcardManagerVO> selectBankCardList(BankCardManagerRequest request) {
        List<BankcardManagerVO> bankcardManagerVOList = bankCardManagerClient.selectBankCardList(request);
        List<BanksConfigVO> banksConfigVOList = bankCardManagerClient.selectBankConfigList();
        for (BankcardManagerVO bankcardManagerVO : bankcardManagerVOList) {
            for (BanksConfigVO banksConfigVO : banksConfigVOList) {
                if (bankcardManagerVO.getBank().equals(banksConfigVO.getBankCode())) {
                    bankcardManagerVO.setBank(banksConfigVO.getBankName());
                }
            }
        }
        return bankcardManagerVOList;
    }

    /**
     * 根据筛选条件查找江西银行卡信息列表
     *
     * @return
     */
    @Override
    public List<BankcardManagerVO> selectNewBankCardList(BankCardManagerRequest request) {
        return bankCardManagerClient.selectNewBankCardList(request);
    }
}
