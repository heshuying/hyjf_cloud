/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.withdraw.impl;

import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.withdraw.RdfWithdrawService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nxl
 * @version RdfWithdrawServiceImpl, v0.1 2018/7/19 14:06
 */
@Service
public class RdfWithdrawServiceImpl extends BaseTradeServiceImpl implements RdfWithdrawService {
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AmConfigClient amConfigClient;
    /**
     * 获取银行卡信息
     * @param userId
     * @param status
     * @return
     */
    @Override
    public List<BankCardVO> selectBankCardByUserIdAndStatus(Integer userId, Integer status) {
        List<BankCardVO> list = amUserClient.selectBankCardByUserIdAndStatus(userId,1);
        return list;
    }

    @Override
    public String getBankLogByBankName(String bankName){
        if(StringUtils.isBlank(bankName)){
            return null;
        }
        if(bankName.contains("农业银行")){
            bankName = "中国农业银行";
        }
        if(bankName.contains("工商银行")){
            bankName = "中国工商银行";
        }
        if(bankName.contains("建设银行")){
            bankName = "中国建设银行";
        }
        if(bankName.contains("招商银行")){
            bankName = "招商银行";
        }
        if(bankName.contains("邮政储蓄银行")){
            bankName = "中国邮政储蓄";
        }
        if(bankName.contains("平安银行")){
            bankName = "平安银行";
        }
        if(bankName.contains("民生银行")){
            bankName = "中国民生银行";
        }
        if(bankName.contains("光大银行")){
            bankName = "中国光大银行";
        }
        List<BankConfigVO> bankConfigRecordList = amConfigClient.getBankConfigRecordList(bankName);
        if(null!=bankConfigRecordList&&bankConfigRecordList.size()>0){
            String strLog = bankConfigRecordList.get(0).getAppLogo();
            return strLog;
        }
        return null;
    }
}
