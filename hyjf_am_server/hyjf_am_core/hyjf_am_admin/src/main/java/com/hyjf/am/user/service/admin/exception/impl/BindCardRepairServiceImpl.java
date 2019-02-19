/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception.impl;

import com.hyjf.am.resquest.admin.BindCardExceptionRequest;
import com.hyjf.am.user.dao.mapper.customize.BindCardExceptionCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.BankCardExample;
import com.hyjf.am.user.service.admin.exception.BindCardRepairService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BindCardExceptionCustomizeVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BindCardRepairServiceImpl, v0.1 2018/10/9 11:29
 */
@Service(value = "userBindCardRepairServiceImpl")
public class BindCardRepairServiceImpl extends BaseServiceImpl implements BindCardRepairService {

    private static final Logger logger = LoggerFactory.getLogger(BindCardRepairServiceImpl.class);

    @Autowired
    private BindCardExceptionCustomizeMapper bindCardExceptionCustomizeMapper;
    @Override
    public int getBindCardExceptionCount(BindCardExceptionRequest request) {
        return bindCardExceptionCustomizeMapper.countBankCardList(request);
    }

    @Override
    public List<BindCardExceptionCustomizeVO> searchBindCardExceptionList(BindCardExceptionRequest request) {
        return bindCardExceptionCustomizeMapper.selectBankCardList(request);
    }

    /**
     * 更新银行卡
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public void updateBindCard(BindCardExceptionRequest request){
        List<BankCardVO> bankCardList = request.getBankCardVOList();
        Integer userId = request.getUserId();
        BankCardExample bankCardExample = new BankCardExample();
        bankCardExample.createCriteria().andUserIdEqualTo(userId);
        try{
            int count = bankCardMapper.countByExample(bankCardExample);
            if (count > 0) {
                // 初始化状态即账户没卡时,不用操作数据库
                // 数据库操作
                boolean isDelFlag = bankCardMapper.deleteByExample(bankCardExample) > 0 ? true : false;
                if (!isDelFlag) {
                    throw new Exception("银行卡删除失败~!,userid is " + userId);
                }
            }
            for (BankCardVO bank : bankCardList) {
                BankCard bankCard = CommonUtils.convertBean(bank,BankCard.class);
                boolean isInsertFlag = bankCardMapper.insertSelective(bankCard) > 0 ? true : false;
                if (!isInsertFlag) {
                    throw new Exception("银行卡插入失败~!");
                }
            }
        }catch(Exception e){
            logger.info("更新银行卡操作失败,原因:[{}]",e.getMessage());
        }

    }
}
