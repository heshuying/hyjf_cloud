package com.hyjf.am.trade.service.front.repay.impl;

import com.hyjf.am.resquest.trade.BankRepayOrgFreezeLogRequest;
import com.hyjf.am.trade.dao.model.auto.BankRepayOrgFreezeLog;
import com.hyjf.am.trade.dao.model.auto.BankRepayOrgFreezeLogExample;
import com.hyjf.am.trade.service.front.repay.BankRepayOrgFreezeLogService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wgx
 * @date 2018/10/13
 */
@Service
public class BankRepayOrgFreezeLogServiceImpl extends BaseServiceImpl implements BankRepayOrgFreezeLogService {
    /**
     * 插入垫付机构冻结表日志
     */
    @Override
    public Integer insertRepayOrgFreezeLog(BankRepayOrgFreezeLogRequest requestBean) {
        BankRepayOrgFreezeLog log = new BankRepayOrgFreezeLog();
        log.setRepayUserId(requestBean.getRepayUserId());// 还款人用户userId
        log.setRepayUserName(requestBean.getRepayUserName());// 还款人用户名
        log.setBorrowUserId(requestBean.getBorrowUserId());// 借款人userId
        log.setBorrowUserName(requestBean.getBorrowUserName());// 借款人用户名
        log.setAccount(requestBean.getAccount());// 电子账号
        log.setOrderId(requestBean.getOrderId());// 订单号:txDate+txTime+seqNo
        log.setBorrowNid(requestBean.getBorrowNid());// 借款编号
        log.setPlanNid(requestBean.getPlanNid());// 计划编号
        log.setInstCode(requestBean.getInstCode());// 资产来源
        log.setAmount(requestBean.getAmount());// 借款金额
        log.setAmountFreeze(requestBean.getAmountFreeze());// 冻结金额
        log.setRepayAccount(requestBean.getRepayAccount());// 应还本息
        log.setRepayFee(requestBean.getRepayFee());// 还款服务费
        log.setLowerInterest(requestBean.getLowerInterest());// 减息金额
        log.setPenaltyAmount(requestBean.getPenaltyAmount());// 违约金
        log.setDefaultInterest(requestBean.getDefaultInterest());// 逾期罚息
        log.setBorrowPeriod(requestBean.getBorrowPeriod());// 借款期限
        log.setTotalPeriod(requestBean.getTotalPeriod());// 总期数
        log.setCurrentPeriod(requestBean.getCurrentPeriod());// 当前期数
        log.setAllRepayFlag(requestBean.getAllRepayFlag());// 是否全部还款 0 否 1 是
        log.setDelFlag(0);// 0 有效 1 无效
        log.setCreateTime(GetDate.getDate());
        log.setCreateUserId(requestBean.getRepayUserId());
        log.setCreateUserName(requestBean.getRepayUserName());
        int flag = this.bankRepayOrgFreezeLogMapper.insertSelective(log);
        logger.info("【还款垫付】借款编号：{}，插入垫付机构冻结表日志{}。", requestBean.getBorrowNid(), flag > 0 ? "成功" : "失败");
        return flag;
    }

    /**
     * 查询垫付机构冻结列表
     */
    @Override
    public List<BankRepayOrgFreezeLog> getBankRepayOrgFreezeLogList(String orderId, String borrowNid){
        BankRepayOrgFreezeLogExample orgExample = new BankRepayOrgFreezeLogExample();
        BankRepayOrgFreezeLogExample.Criteria criteria = orgExample.createCriteria();
        if(StringUtils.isNotBlank(orderId)){
            criteria.andOrderIdEqualTo(orderId);
        }
        if(StringUtils.isNotBlank(borrowNid)) {
            criteria.andBorrowNidEqualTo(borrowNid);
        }
        criteria.andDelFlagEqualTo(0);
        return bankRepayOrgFreezeLogMapper.selectByExample(orgExample);
    }

    /**
     * 删除垫付机构临时日志,外部调用
     */
    @Override
    public Integer deleteOrgFreezeTempLogs(String orderId, String borrowNid) {
        BankRepayOrgFreezeLogExample example = new BankRepayOrgFreezeLogExample();
        BankRepayOrgFreezeLogExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        if(StringUtils.isNotBlank(borrowNid)){
            criteria.andBorrowNidEqualTo(borrowNid);
        }
        List<BankRepayOrgFreezeLog> log = this.bankRepayOrgFreezeLogMapper.selectByExample(example);
        int result = 0;
        if (log != null && log.size() > 0) {
            for (int i = 0; i < log.size(); i++) {
                BankRepayOrgFreezeLog record = log.get(i);
                record.setDelFlag(1);// 0 有效 1无效
                int flag = this.bankRepayOrgFreezeLogMapper.updateByPrimaryKey(record);
                result += flag;
                logger.info("【代偿冻结处理】借款编号：{}，删除担保机构冻结临时日志{}。", record.getBorrowNid(), flag > 0 ? "成功" : "失败");
            }
        }
        return result;
    }
}
