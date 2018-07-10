/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.controller.finance.transfer.TransferCustomizeBean;
import com.hyjf.admin.service.TransferService;
import com.hyjf.am.response.admin.UserTransferResponse;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.resquest.admin.TransferListRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingqing
 * @version TransferServiceImpl, v0.1 2018/7/6 17:59
 */
@Service
public class TransferServiceImpl extends BaseAdminServiceImpl implements TransferService {

    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public UserTransferResponse getRecordList(TransferListRequest form) {
        return amTradeClient.getRecordList(form);
    }

    @Override
    public void checkTransfer(String outUserName) {
        UserVO user = getUserByUserName(outUserName);
        CheckUtil.check(null != user, MsgEnum.STATUS_CE000006);
        List<AccountChinapnrVO> chinapnrs = this.amUserClient.searchAccountChinapnrByUserId(user.getUserId());
        CheckUtil.check(chinapnrs != null && chinapnrs.size() == 1, MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        List<AccountVO> accounts = this.amTradeClient.searchAccountByUserId(user.getUserId());
        CheckUtil.check(accounts != null && accounts.size() == 1, MsgEnum.ERR_OBJECT_GET, "正确的余额信息");

    }

    @Override
    public String searchBalance(String outUserName) {
        UserVO user = getUserByUserName(outUserName);
        CheckUtil.check(null != user, MsgEnum.STATUS_CE000006);
        List<AccountChinapnrVO> chinapnrs = this.amUserClient.searchAccountChinapnrByUserId(user.getUserId());
        CheckUtil.check(chinapnrs != null && chinapnrs.size() == 1, MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        List<AccountVO> accounts = this.amTradeClient.searchAccountByUserId(user.getUserId());
        CheckUtil.check(accounts != null && accounts.size() == 1, MsgEnum.ERR_OBJECT_GET, "正确的余额信息");
        if (accounts != null && accounts.size() == 1) {
            AccountVO account = accounts.get(0);
            return account.getBalance().toString();
        }
        return null;
    }

    @Override
    public void checkTransferParam(TransferCustomizeBean form) {
        checkTransfer(form.getOutUserName());
        // TODO: 2018/7/6 zhangqingqing 说明 
       // ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "remark", form.getRemark(), 60, true);
    }

    @Override
    public boolean insertTransfer(TransferCustomizeBean form) {
        CustomerTransferRequest request = new CustomerTransferRequest();
        BeanUtils.copyProperties(form,request);
        return  amTradeClient.insertUserTransfer(request);
    }


}
