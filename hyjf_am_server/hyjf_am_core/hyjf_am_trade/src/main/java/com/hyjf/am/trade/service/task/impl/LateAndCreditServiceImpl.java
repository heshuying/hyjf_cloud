/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.LateAndCreditService;
import com.hyjf.common.util.GetDate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version LateAndCreditServiceImpl, v0.1 2018/9/5 11:50
 */
@Service
public class LateAndCreditServiceImpl extends BaseServiceImpl implements LateAndCreditService {

    /**
     * 检索逾期的还款标的
     *
     * @return
     * @author lsy
     */
    @Override
    public List<BorrowRepay> selectOverdueBorrowList() {
        BorrowRepayExample example = new BorrowRepayExample();
        example.createCriteria().andRepayTimeLessThanOrEqualTo(GetDate.getDayEnd10(GetDate.getTodayBeforeOrAfter(-1))).andRepayTypeEqualTo("wait");
        List<BorrowRepay> borrowRepayList = borrowRepayMapper.selectByExample(example);

        if (null != borrowRepayList && borrowRepayList.size() > 0) {
            return borrowRepayList;
        }
        return null;
    }

    /**
     * 获取互金表未更新为其他方式还款的完全债转的标的
     *
     * @return
     */
    @Override
    public List<BorrowRecover> selectBorrowRecoverCredit() {
        List<BorrowRecover> borrowRecoverList = this.nifaContractStatusCustomizeMapper.selectBorrowRecoverCredit();
        if (null != borrowRecoverList && borrowRecoverList.size() > 0) {
            return borrowRecoverList;
        }
        return null;
    }

    /**
     * 插入合同信息
     *
     * @param thisMessName
     * @param com_social_credit_code
     * @param borrowNid
     * @param contractStatus
     * @param borrowTender
     * @param borrowRepay
     * @return
     */
    @Override
    public boolean insertNifaContractStatusRecord(String thisMessName, String com_social_credit_code, String borrowNid, Integer contractStatus, BorrowTender borrowTender, BorrowRepay borrowRepay) {

        NifaContractStatus nifaContractStatus = new NifaContractStatus();
        // 统一社会信用代码
        nifaContractStatus.setPlatformNo(com_social_credit_code);
        // 项目编号
        nifaContractStatus.setProjectNo(borrowNid);
        // 合同编号
        nifaContractStatus.setContractNo(borrowTender.getNid());
        // 合同状态
        nifaContractStatus.setContractStatus(contractStatus);
        // 创建时间为当前时间
        nifaContractStatus.setCreateTime(new Date());
        // 更新时间为当前时间
        nifaContractStatus.setUpdateTime(new Date());

        // 更新日期 YYYY-MM-DD HH:MM:SS
        if(borrowRepay.getRepayActionTime() != null) {
            try {
                nifaContractStatus.setChangeDate(GetDate.getDateTimeMyTime(borrowRepay.getRepayActionTime()));
            } catch (NumberFormatException e) {
                logger.error(thisMessName + "还款日格式化失败，borrowNid:" + borrowRepay.getBorrowNid());
                logger.error(e.getMessage());
                return false;
            }
        } else {
            // 没有还款时间的情况进到此处属于逾期还款的场景
            nifaContractStatus.setChangeDate(GetDate.getDateTimeMyTime(GetDate.getNowTime10()));
        }
        return this.nifaContractStatusMapper.insert(nifaContractStatus) > 0 ? true : false;
    }

    /**
     * 互金合同状态更新
     *
     * @param contractStatus
     * @param borrowRepay
     * @param nifaContractStatusOld
     * @return
     */
    @Override
    public boolean updateNifaContractStatusRecord(String thisMessName, Integer contractStatus, BorrowRepay borrowRepay, NifaContractStatus nifaContractStatusOld) {
        nifaContractStatusOld.setContractStatus(contractStatus);
        nifaContractStatusOld.setUpdateTime(new Date());
        // 更新日期 YYYY-MM-DD HH:MM:SS
        if(null != borrowRepay.getRepayActionTime()) {
                try {
                    nifaContractStatusOld.setChangeDate(GetDate.getDateTimeMyTime(borrowRepay.getRepayActionTime()));
                } catch (NumberFormatException e) {
                    logger.error(thisMessName + "还款日格式化失败，borrowNid:" + borrowRepay.getBorrowNid());
                    logger.error(e.getMessage());
                    return false;
                }
        } else {
            // 没有还款时间的情况进到此处属于逾期还款的场景
            nifaContractStatusOld.setChangeDate(GetDate.getDateTimeMyTime(GetDate.getNowTime10()));
        }
        return this.nifaContractStatusMapper.updateByPrimaryKeySelective(nifaContractStatusOld) > 0 ? true : false;
    }

    /**
     * 根据nid获取出借信息
     *
     * @param nid
     * @return
     */
    @Override
    public BorrowTender selectBorrowTenderByNid (String nid) {
        BorrowTenderExample example = new BorrowTenderExample();
        example.createCriteria().andNidEqualTo(nid);
        List<BorrowTender> borrowTenderList = this.borrowTenderMapper.selectByExample(example);
        if(null!=borrowTenderList&&borrowTenderList.size()>0){
            return borrowTenderList.get(0);
        }
        return null;
    }
}
