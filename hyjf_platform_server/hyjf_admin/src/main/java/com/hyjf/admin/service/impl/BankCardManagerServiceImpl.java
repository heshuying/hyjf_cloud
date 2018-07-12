/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.BankCardManagerClient;
import com.hyjf.admin.service.BankCardManagerService;
import com.hyjf.am.response.user.BankCardLogResponse;
import com.hyjf.am.response.user.BankCardManagerResponse;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.vo.trade.BanksConfigVO;
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
        List<BanksConfigVO> banksConfigVOList = bankCardManagerClient.selectBankConfigList();
        if(null!=banksConfigVOList&&banksConfigVOList.size()>0){
            for (BanksConfigVO banksConfigVO : banksConfigVOList) {
                if (bankcardManagerVO.getBank().equals(banksConfigVO.getBankCode())) {
                    bankcardManagerVO.setBank(banksConfigVO.getBankName());
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
        if(null!=response){
            if(null!=response.getResultList()&&response.getResultList().size()>0){
                for(BankCardLogVO bankCardLogVO:response.getResultList()){
//                    bankCardLogVO.setBankName(bankcardProperty.getOrDefault(bankCardLogVO.getBankCode(),null));
                }
            }
        }
        return bankCardManagerClient.selectBankCardLogByExample(request);
    }


}
