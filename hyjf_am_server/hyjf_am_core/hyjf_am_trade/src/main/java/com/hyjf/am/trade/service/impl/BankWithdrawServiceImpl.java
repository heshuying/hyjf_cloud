package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.AccountwithdrawMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.Accountwithdraw;
import com.hyjf.am.trade.dao.model.auto.AccountwithdrawExample;
import com.hyjf.am.trade.service.BankWithdrawService;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 江西银行提现掉单异常处理Service实现类
 * create by jijun 20180614
 */
@Service
public class BankWithdrawServiceImpl implements BankWithdrawService {

    private static final Logger logger = LoggerFactory.getLogger(BankWithdrawServiceImpl.class);

    @Autowired
    private AccountwithdrawMapper accountwithdrawMapper;
    @Autowired
    private AdminAccountCustomizeMapper adminAccountCustomizeMapper;
    
    /**
     * 索处理中的充值订单
     * add by jijun 20180615
     * @return
     */
    @Override
    public List<Accountwithdraw> selectBankWithdrawList() {
        AccountwithdrawExample example = new AccountwithdrawExample();
        AccountwithdrawExample.Criteria cra = example.createCriteria();
        List<Integer> status = new ArrayList<Integer>();
        //mod by nixiaoling 状态为0的不查找 20180428
        status.add(0);
        status.add(1);
        //status.add(3);
        cra.andStatusIn(status);// 提现状态为提现中, 审核中（处理中）, 提现失败
        cra.andBankFlagEqualTo(1);// 提现平台:江西银行
        // 当前时间
        cra.andAddTimeGreaterThanOrEqualTo(GetDate.countDate(5,2));// TODO T-1天之前
        cra.andAddTimeLessThanOrEqualTo(GetDate.getMinutesAfter(GetDate.getDate(),-30));// 30分钟之前的充值订单TODO
        return this.accountwithdrawMapper.selectByExample(example);
    }

    /**
     * 更新账户信息
     * add by jijun 20180616
     */
	@Override
	public int updateBankWithdraw(AccountVO accountVO) {
		return this.adminAccountCustomizeMapper.updateBankWithdrawSuccess(CommonUtils.convertBean(accountVO, Account.class));
	}


}
