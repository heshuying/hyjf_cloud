package com.hyjf.am.trade.service.impl.repay;

import com.hyjf.am.resquest.trade.BankRepayFreezeLogRequest;
import com.hyjf.am.trade.dao.model.auto.BankRepayFreezeLog;
import com.hyjf.am.trade.dao.model.auto.BankRepayFreezeLogExample;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 还款冻结表
 * @author hesy
 * @version BankRepayFreezeLogServiceImpl, v0.1 2018/7/9 15:36
 */
@Service
public class BankRepayFreezeLogServiceImpl extends BaseServiceImpl implements com.hyjf.am.trade.service.repay.BankRepayFreezeLogService {
    @Override
    public Integer insertRepayFreezeLog(BankRepayFreezeLogRequest requestBean) {
        BankRepayFreezeLog log = new BankRepayFreezeLog();
        log.setBorrowNid(requestBean.getBorrowNid());
        log.setAccount(requestBean.getAccount());
        log.setAmount(requestBean.getAmount());
        log.setDelFlag(0);// 0 有效 1 无效
        log.setOrderId(requestBean.getOrderId());
        log.setUserId(requestBean.getUserId());
        log.setUserName(requestBean.getUserName());
        log.setCreateTime(GetDate.getDate());
        log.setCreateUserId(requestBean.getUserId());
        log.setCreateUserName(requestBean.getUserName());
        return this.bankRepayFreezeLogMapper.insertSelective(log);
    }

    @Override
    public Integer deleteFreezeLogsByOrderId(String orderId) {
        int deleteCount = 0;
        BankRepayFreezeLogExample example = new BankRepayFreezeLogExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<BankRepayFreezeLog> log = this.bankRepayFreezeLogMapper.selectByExample(example);
        if (log != null && log.size() > 0) {
            for (int i = 0; i < log.size(); i++) {
                BankRepayFreezeLog record = log.get(i);
                record.setDelFlag(1);// 0 有效 1无效
                this.bankRepayFreezeLogMapper.updateByPrimaryKey(record);
                deleteCount++;
            }
        }
        return  deleteCount;
    }

}
