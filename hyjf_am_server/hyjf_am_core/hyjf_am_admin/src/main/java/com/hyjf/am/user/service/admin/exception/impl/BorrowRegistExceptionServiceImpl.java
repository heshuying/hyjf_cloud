/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception.impl;

import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.admin.exception.BorrowRegistExceptionService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionServiceImpl, v0.1 2018/7/3 18:04
 */
@Service(value = "userBorrowRegistExceptionServiceImpl")
public class BorrowRegistExceptionServiceImpl extends BaseServiceImpl implements BorrowRegistExceptionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 根据userId获取BankOpenAccount
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public BankOpenAccountVO searchBankOpenAccount(Integer userId) {
        logger.info("searchBankOpenAccount:::::::::userId=[{}]",userId);
        // 借款人用户
        User user = this.findUserByUserId(userId);
        if (Validator.isNotNull(user)) {
            BankOpenAccountExample bankOpenAccountExample = new BankOpenAccountExample();
            BankOpenAccountExample.Criteria cra = bankOpenAccountExample.createCriteria();
            cra.andUserIdEqualTo(userId);
            List<BankOpenAccount> bankOpenAccountList = this.bankOpenAccountMapper.selectByExample(bankOpenAccountExample);
            if(null != bankOpenAccountList && bankOpenAccountList.size() > 0){
                BankOpenAccount bankOpenAccount = bankOpenAccountList.get(0);
                if (Validator.isNotNull(bankOpenAccount)) {
                    BankOpenAccountVO bankOpenAccountVO = CommonUtils.convertBean(bankOpenAccount,BankOpenAccountVO.class);
                    return bankOpenAccountVO;
                }
            }
        }
        return null;
    }
}
