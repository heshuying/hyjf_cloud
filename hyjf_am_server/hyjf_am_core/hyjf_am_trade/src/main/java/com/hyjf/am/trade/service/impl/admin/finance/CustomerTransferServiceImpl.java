/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.finance;

import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountMapper;
import com.hyjf.am.trade.dao.mapper.auto.UserTransferMapper;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountExample;
import com.hyjf.am.trade.dao.model.auto.UserTransfer;
import com.hyjf.am.trade.service.admin.finance.CustomerTransferService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.ThreeDESUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: CustomerTransferServiceImpl, v0.1 2018/7/6 10:17
 */
@Service
public class CustomerTransferServiceImpl extends BaseServiceImpl implements CustomerTransferService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserTransferMapper userTransferMapper;

    @Value("${hyjf.web.transfer.url}")
    private String URL;
    @Value("${hyjf.transfer.3des.key}")
    private String KEY;
    /**
     * 根据userId查询账户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<Account> searchAccountByUserId(Integer userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Account> accountList = accountMapper.selectByExample(example);
        return accountList;
    }
    /**
     * 向ht_user_transfer表中插入数据
     * @auth sunpeikai
     * @param request 发起转账的参数
     * @return
     */
    @Override
    public Boolean insertUserTransfer(CustomerTransferRequest request) {
        Date nowTime = new Date();
        UserTransfer userTransfer = new UserTransfer();
        // 生成订单
        String orderId = GetOrderIdUtils.getOrderId2(request.getOutUserId());
        userTransfer.setOrderId(orderId);
        userTransfer.setOutUserId(request.getOutUserId());
        userTransfer.setOutUserName(request.getOutUserName());
        userTransfer.setTransferAmount(request.getTransferAmount());
        userTransfer.setTransferType(0);
        userTransfer.setStatus(0);
        userTransfer.setRemark(request.getRemark());
        userTransfer.setCreateUserId(request.getCreateUserId());
        userTransfer.setCreateTime(nowTime);
        userTransfer.setCreateUserName(request.getCreateUserName());
        // 转账url
        String transferUrl = "";
        try {
            transferUrl = URL + "?orderId="
                    + ThreeDESUtils.Encrypt3DES(KEY, userTransfer.getOrderId());
            userTransfer.setTransferUrl(transferUrl);
            boolean flag = this.userTransferMapper.insertSelective(userTransfer) > 0;
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
