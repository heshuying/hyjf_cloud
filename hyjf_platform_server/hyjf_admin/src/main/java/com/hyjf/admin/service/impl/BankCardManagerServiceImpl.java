/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.BankCardManagerService;
import com.hyjf.am.response.user.BankCardLogResponse;
import com.hyjf.am.response.user.BankCardManagerResponse;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.user.BankCardLogVO;
import com.hyjf.am.vo.user.BankcardManagerVO;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author nixiaoling
 * @version RegistRecordServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class BankCardManagerServiceImpl implements BankCardManagerService {

    @Autowired
    private AmUserClient bankCardManagerClient;
    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 获取银行列表
     *
     * @return
     */
    @Override
    public List<BankConfigVO> selectBankConfigList() {
        return amConfigClient.selectBankConfigList();
    }


    /**
     * 根据筛选条件查找汇付银行卡信息列表
     *
     * @param request 筛选条件
     * @return
     */
    @Override
    public BankCardManagerResponse selectBankCardList(BankCardManagerRequest request) {
        BankCardManagerResponse bankCardManagerResponse = bankCardManagerClient.selectBankCardList(request);
        if(null!=bankCardManagerResponse){
            List<BankcardManagerVO> bankcardManagerVOList = bankCardManagerResponse.getResultList();
            if(null!=bankcardManagerVOList&&bankcardManagerVOList.size()>0){
                for (BankcardManagerVO bankcardManagerVO : bankcardManagerVOList) {
                    setBankCardName(bankcardManagerVO);
                }
            }
        }
        return bankCardManagerResponse;
    }

    /**
     * 设置汇付银行卡的银行名称
     * @param bankcardManagerVO
     */
    private void setBankCardName(BankcardManagerVO bankcardManagerVO){
        List<BankConfigVO> banksConfigVOList = amConfigClient.selectBankConfigList();
        if(null!=banksConfigVOList&&banksConfigVOList.size()>0){
            for (BankConfigVO banksConfigVO : banksConfigVOList) {
                if (bankcardManagerVO.getBank().equals(banksConfigVO.getCode())) {
                    bankcardManagerVO.setBank(banksConfigVO.getName());
                }
            }
        }
    }

    /**
     * 根据筛选条件查找江西银行卡信息列表
     *
     * @return
     */
    @Override
    public BankCardManagerResponse selectNewBankCardList(BankCardManagerRequest request) {
        return bankCardManagerClient.selectNewBankCardList(request);
    }

    /**
     * 查找用户银行卡操作记录表
     * @param request
     * @return
     */
    @Override
    public BankCardLogResponse selectBankCardLogByExample(BankCardLogRequest request){
        Map<String, String> bankcardProperty = CacheUtil.getParamNameMap("BANKCARD_PROPERTY");
        BankCardLogResponse response =  bankCardManagerClient.selectBankCardLogByExample(request);
        return response;
    }


}
