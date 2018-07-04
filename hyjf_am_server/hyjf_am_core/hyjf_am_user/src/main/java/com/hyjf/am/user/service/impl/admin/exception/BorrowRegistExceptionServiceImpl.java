/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl.admin.exception;

import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.admin.exception.BorrowRegistExceptionService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.BankCallBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRegistExceptionVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: BorrowRegistExceptionServiceImpl, v0.1 2018/7/3 18:04
 */
@Service
public class BorrowRegistExceptionServiceImpl extends BaseServiceImpl implements BorrowRegistExceptionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public BankOpenAccountVO searchBankOpenAccount(Integer userId) {
        logger.info("searchBankOpenAccount:::::::::userId=[{}]",userId);
        User user = this.findUserByUserId(userId);// 借款人用户
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
