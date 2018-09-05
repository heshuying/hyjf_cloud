/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.task.LateAndCreditService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version LateAndCreditController, v0.1 2018/9/5 11:48
 */
@Api(value = "拉取逾期和完全债转数据更新合同状态")
@RestController
@RequestMapping("/am-trade/late_and_credit")
public class LateAndCreditController extends BaseController {

    private String thisMessName = "【更新合同状态】";

    /**
     * 获取18位社会信用代码
     */
    @Value("${hyjf.com.social.credit.code}")
    private String COM_SOCIAL_CREDIT_CODE;

    @Autowired
    private LateAndCreditService lateAndCreditService;

    /**
     * 互金拉取逾期和完全债转数据更新合同状态
     *
     * @return
     */
    @RequestMapping("/update_repay_info")
    public boolean updateRepayInfo() {
        try {
            // ---------------------这里开始处理逾期的数据---------------------------
            List<BorrowRepay> borrowRepayList = lateAndCreditService.selectOverdueBorrowList();
            if (borrowRepayList == null || borrowRepayList.isEmpty()) {
                logger.info(thisMessName + "当前没有逾期的标的信息");
            } else {
                for (BorrowRepay record : borrowRepayList) {
                    logger.info(thisMessName + "逾期未还款标的开始处理：" + record.getBorrowNid());
                    String borrowNid = record.getBorrowNid();

                    // 获取用户投资信息
                    List<BorrowTender> borrowTenderList = this.lateAndCreditService.selectBorrowTenderListByBorrowNid(borrowNid);
                    if (null == borrowTenderList || borrowTenderList.size() <= 0) {
                        logger.error(thisMessName + "未获取到用户投资信息，borrowNid:" + borrowNid);
                        continue;
                    }

                    // 获取标的还款计划数据
                    BorrowRepay borrowRepay = this.lateAndCreditService.selectBorrowRepay(borrowNid);
                    if (null == borrowRepay) {
                        logger.error(thisMessName + "未获取到标的还款计划，borrowNid:" + borrowNid);
                        continue;
                    }

                    for (BorrowTender borrowTender : borrowTenderList) {
                        // 互金合同状态表获取合同状态信息
                        NifaContractStatus nifaContractStatusOld = this.lateAndCreditService.selectNifaContractStatusByNid(borrowTender.getNid());
                        if (null == nifaContractStatusOld) {
                            // 插入数据
                            if (!lateAndCreditService.insertNifaContractStatusRecord(thisMessName,COM_SOCIAL_CREDIT_CODE,borrowNid,2,borrowTender,borrowRepay)) {
                                logger.error(thisMessName + "合同状态变更数据生成失败，borrowNid:" + borrowRepay.getBorrowNid());
                            }
                        } else {
                            // 合同状态有变更重新上报
                            if (nifaContractStatusOld.getContractStatus() != 2) {
                                if (!lateAndCreditService.updateNifaContractStatusRecord(thisMessName,2,borrowRepay,nifaContractStatusOld)) {
                                    logger.error(thisMessName + "合同状态变更数据更新失败，borrowNid:" + borrowRepay.getBorrowNid());
                                }
                            } else {
                                logger.info(thisMessName + "合同状态未变更，borrowNid:" + borrowRepay.getBorrowNid());
                            }
                        }
                    }
                }
            }

            // ---------------------这里开始处理完全债转的数据---------------------------
            List<BorrowRecover> borrowRecoverList = lateAndCreditService.selectBorrowRecoverCredit();
            if (null == borrowRecoverList && borrowRecoverList.size()<=0) {
                logger.info("【拉取逾期未还款标的】当前没有完全债转的标的信息");
            } else {
                for (BorrowRecover record : borrowRecoverList) {
                    logger.info("【拉取逾期未还款标的】完全债转的标的开始处理：" + record.getNid());
                    String nid = record.getNid();
                    BorrowTender borrowTender = this.lateAndCreditService.selectBorrowTenderByNid(nid);
                    if (null == borrowTender) {
                        logger.error(thisMessName + "未获取到用户投资详情，nid:" + nid);
                        continue;
                    }
                    String borrowNid = borrowTender.getBorrowNid();

                    // 获取标的还款计划数据
                    BorrowRepay borrowRepay = this.lateAndCreditService.selectBorrowRepay(borrowNid);
                    if (null == borrowRepay) {
                        logger.error(thisMessName + "未获取到标的还款计划，borrowNid:" + borrowNid);
                        continue;
                    }

                    NifaContractStatus nifaContractStatusOld = this.lateAndCreditService.selectNifaContractStatusByNid(nid);
                    if (null == nifaContractStatusOld) {
                        // 插入数据
                        if (!lateAndCreditService.insertNifaContractStatusRecord(thisMessName,COM_SOCIAL_CREDIT_CODE,borrowNid,6,borrowTender,borrowRepay)) {
                            logger.error(thisMessName + "合同状态变更数据生成失败，borrowNid:" + borrowRepay.getBorrowNid());
                        }
                    } else {
                        // 合同状态有变更重新上报
                        if (nifaContractStatusOld.getContractStatus() != 6) {
                            if (!lateAndCreditService.updateNifaContractStatusRecord(thisMessName,6,borrowRepay,nifaContractStatusOld)) {
                                logger.error(thisMessName + "合同状态变更数据更新失败，borrowNid:" + borrowRepay.getBorrowNid());
                            }
                        } else {
                            logger.info(thisMessName + "合同状态未变更，borrowNid:" + borrowRepay.getBorrowNid());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("【拉取逾期未还款标的】逾期未还款标的统计任务执行失败", e);
            return false;
        }
        return true;
    }
}
