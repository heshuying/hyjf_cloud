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
    /**
     * 插入冻结记录
     * @param requestBean
     * @return
     */
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

    /**
     * 通过orderId删除（逻辑删）
     * @param orderId
     * @return
     */
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

    /**
     * 根据id删除(物理删除)
     * @param id
     * @return
     */
    @Override
    public Integer deleteFreezeLogById(Integer id) {
        return this.bankRepayFreezeLogMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取当前有效的冻结记录
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public BankRepayFreezeLog getFreezeLog(Integer userId, String borrowNid){
        BankRepayFreezeLogExample example = new BankRepayFreezeLogExample();
        example.createCriteria().andUserIdEqualTo(userId).andBorrowNidEqualTo(borrowNid).andDelFlagEqualTo(0);
        List<BankRepayFreezeLog> list = bankRepayFreezeLogMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据orderId获取冻结记录
     * @param orderId
     * @return
     */
    @Override
    public BankRepayFreezeLog getBankFreezeLogByOrderId(String orderId) {
        BankRepayFreezeLogExample example = new BankRepayFreezeLogExample();
        example.createCriteria().andOrderIdEqualTo(orderId).andDelFlagEqualTo(0);
        List<BankRepayFreezeLog> repayFreezeLogs = this.bankRepayFreezeLogMapper.selectByExample(example);
        if (repayFreezeLogs != null && repayFreezeLogs.size() > 0) {
            return repayFreezeLogs.get(0);
        }
        return null;
    }

    /**
     * 分页获取所有有效的冻结记录
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<BankRepayFreezeLog> getFreezeLogValidAll(Integer limitStart, Integer limitEnd){
        BankRepayFreezeLogExample example = new BankRepayFreezeLogExample();
        example.createCriteria().andDelFlagEqualTo(0);
        example.setLimitStart(limitStart);
        example.setLimitEnd(limitEnd);
        example.setOrderByClause(" id ASC ");
        List<BankRepayFreezeLog> freezeLogList = this.bankRepayFreezeLogMapper.selectByExample(example);
        return freezeLogList;
    }

    /**
     * 有效冻结记录总数
     * @return
     */
    @Override
    public Integer getFreezeLogValidAllCount() {
        BankRepayFreezeLogExample example = new BankRepayFreezeLogExample();
        example.createCriteria().andDelFlagEqualTo(0);
        int countByExample = this.bankRepayFreezeLogMapper.countByExample(example);
        return countByExample;
    }

}
