/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.nifa.impl;

import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.service.consumer.hgdatareport.nifa.NifaRepayInfoMessageService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaTwoRepayInfoMessageServiceImpl, v0.1 2019/1/24 15:30
 */
@Service
public class NifaRepayInfoMessageServiceImpl extends BaseTradeServiceImpl implements NifaRepayInfoMessageService {

    /**
     * 获取18位社会信用代码
     */
    @Value("${hyjf.com.social.credit.code}")
    private String COM_SOCIAL_CREDIT_CODE;

    private String thisMessName = "互金标的相关还款信息上送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_NIFA + " " + thisMessName + "】";

    /**
     * 处理还款数据
     *
     * @param historyData
     * @param repayPeriod
     * @param borrow
     * @param borrowRepay
     * @param borrowRecoverList
     * @param recoverFee
     * @param lateCounts
     * @param nifaRepayInfoEntity
     * @return
     */
    @Override
    public boolean selectDualNifaRepayInfo(String historyData, Integer repayPeriod, BorrowAndInfoVO borrow, BorrowRepayVO borrowRepay, List<BorrowRecoverVO> borrowRecoverList, BigDecimal recoverFee, String lateCounts, NifaBorrowInfoVO nifaRepayInfoEntity) {
        // 业务处理时间记录
        nifaRepayInfoEntity.setHistoryData(historyData);
        // 项目唯一编号 系统内唯一编号，是社会信用代码、平台序号和报数机构项目编号的组合，长度128位
        nifaRepayInfoEntity.setProjectNo(COM_SOCIAL_CREDIT_CODE + "1" + borrow.getBorrowNid());
        // 社会信用代码
        nifaRepayInfoEntity.setSocialCreditCode(COM_SOCIAL_CREDIT_CODE);
        // 平台序号
        nifaRepayInfoEntity.setPlatformNo("1");
        // 项目编号 报数机构内部项目编号，长度64位
        nifaRepayInfoEntity.setOrganizationNo(borrow.getBorrowNid());
        // 项目类型 01互联网债权类融资-个体直接借贷，02互联网债权类融资-互联网小额贷款，03其他债权类融资业务，长度2位
        nifaRepayInfoEntity.setBorrowType("01");
        // 项目名称
        nifaRepayInfoEntity.setBorrowName(StringUtils.isNotBlank(borrow.getProjectName()) ? borrow.getProjectName() : borrow.getBorrowNid());
        // 项目成立日期 借款人和出借人债权债务关系的成立日期，格式为YYYYMMDD
        if (null == borrow.getRecoverLastTime()) {
            logger.error(logHeader + "最终放款日为空！！borrowNid:{}", borrow.getBorrowNid());
            return false;
        }
        try {
            nifaRepayInfoEntity.setRecoverTime(GetDate.timestamptoStrYYYYMMDD(borrow.getRecoverLastTime()).replaceAll("-", ""));
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(logHeader + "最终放款日格式化失败！！borrowNid:{}", borrow.getBorrowNid());
            return false;
        }
        // 借款金额
        nifaRepayInfoEntity.setAccount(borrow.getAccount() + "");
        // 借款币种 GB/T 12406-2008，表示资金和货币的代码，长度3位
        nifaRepayInfoEntity.setCurrency("CNY");
        // 借款起息日 借款合同约定的起息日期，格式为YYYYMMDD 生产库：即收益起始日，为项目认购完成日。
        nifaRepayInfoEntity.setBorrowInterestTime(nifaRepayInfoEntity.getRecoverTime());
        // 借款到期日期 借款合同约定的到期日期，格式为YYYYMMDD
        if (null == borrow.getBorrowEndTime()) {
            logger.error(logHeader + "借款到期日为空！！borrowNid:{},到期日:{}", borrow.getBorrowNid(), borrow.getBorrowEndTime());
            return false;
        }
        if (!Validator.isNumber(borrow.getBorrowEndTime())) {
            logger.error(logHeader + "借款到期日含非数字！！borrowNid:{},到期日:{}", borrow.getBorrowNid(), borrow.getBorrowEndTime());
            return false;
        }
        Integer borrowEndTime = Integer.parseInt(borrow.getBorrowEndTime());
        try {
            nifaRepayInfoEntity.setBorrowEndTime(GetDate.timestamptoStrYYYYMMDD(borrowEndTime).replaceAll("-", ""));
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(logHeader + "最终放款日格式化失败！！borrowNid:{}", borrow.getBorrowNid());
            return false;
        }
        // 是否分期还款
        boolean isMonth = false;
        // 最后一笔还款日
        Integer lasterRepayTime = 0;
        // 还款方式 去还款表取数据
        if (CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())) {
            // 等额本息
            isMonth = true;
            // 还款方式 01按月付息到期还本、02按季付息到期还本、03按月等额本息还款、04按季等额本息还款、05到期一次性还本付息、06按月等额本金还款、07按季等额本金还款、08按月等本等息还款、09半年付息到期还本、10随时提前还款、99个性化还款方式，长度2位
            nifaRepayInfoEntity.setBorrowStyle("03");
        } else if (CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle())) {
            // 按月计息，到期还本还息
            isMonth = false;
            nifaRepayInfoEntity.setBorrowStyle("05");
        } else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle())) {
            // 先息后本
            isMonth = true;
            nifaRepayInfoEntity.setBorrowStyle("01");
        } else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle())) {
            // 按天计息，到期还本息
            isMonth = false;
            nifaRepayInfoEntity.setBorrowStyle("05");
        } else if (CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle())) {
            // 等额本金
            isMonth = true;
            nifaRepayInfoEntity.setBorrowStyle("06");
        }
        if (isMonth) {
            // 根据借款订单号获取还款记录
            List<BorrowRepayPlanVO> borrowRepayPlanList = this.amTradeClient.getBorrowRepayPlansByBorrowNid(borrow.getBorrowNid());
            if (null == borrowRepayPlanList || borrowRepayPlanList.size() <= 0) {
                logger.error(logHeader + "为获取到还款计划（到期还款）！！borrowNid:{}", borrow.getBorrowNid());
                return false;
            }
            // 约定还款计划 每期的还款日期、本金、利息列表，用冒号分隔，用分号分段,结尾不写分号
            String repayPlanListStr = "";
            String repayDetilsStr = "";
            Integer laterCount = 0;
            // 累计偿还本金
            BigDecimal repayCY = BigDecimal.ZERO;
            // 累计偿还利息
            BigDecimal repayIY = BigDecimal.ZERO;
            // 累计未还本金
            BigDecimal repayCW = BigDecimal.ZERO;
            // 累计未还利息
            BigDecimal repayIW = BigDecimal.ZERO;
            for (BorrowRepayPlanVO borrowRepayPlan : borrowRepayPlanList) {
                Integer repayTime = borrowRepayPlan.getRepayTime();
                Integer repayActionTime;
                // 拼接还款计划
                // 字符串拼接
                repayPlanListStr = repayPlanListStr.concat(GetDate.timestamptoStrYYYYMMDD(repayTime)).concat(":")
                        .concat(borrowRepayPlan.getRepayCapital() + "").concat(":")
                        .concat(borrowRepayPlan.getRepayInterest() + "").concat(";");
                // 当期还款记录
                if (repayPeriod.equals(borrowRepayPlan.getRepayPeriod())) {
                    if (Validator.isNumber(borrowRepayPlan.getRepayActionTime())) {
                        repayActionTime = Integer.parseInt(borrowRepayPlan.getRepayActionTime());
                        repayDetilsStr = repayDetilsStr.concat(GetDate.timestamptoStrYYYYMMDD(repayActionTime)).concat(":")
                                .concat(borrowRepayPlan.getRepayCapitalYes() + "").concat(":")
                                .concat(borrowRepayPlan.getRepayInterestYes() + "");
                    } else {
                        logger.error(logHeader + "该期实际还款时间为非数字！！borrowNid:{}", borrow.getBorrowNid());
                        return false;
                    }
                }
                // 到该期为止已还和未还处理
                // borrowRepayPlan.getRepayPeriod()当前期数 > borrowPeriod还款期数 = 未还款
                if (repayPeriod < borrowRepayPlan.getRepayPeriod()) {
                    // 未还款期数
                    repayCW = repayCW.add(borrowRepayPlan.getRepayCapital());
                    repayIW = repayIW.add(borrowRepayPlan.getRepayInterest());
                } else {
                    // 已还款期数
                    if (borrowRepayPlan.getAdvanceStatus() == 3) {
                        // 统计当前标的逾期次数
                        laterCount = laterCount + 1;
                    }
                    repayCY = repayCY.add(borrowRepayPlan.getRepayCapitalYes());
                    repayIY = repayIY.add(borrowRepayPlan.getRepayInterestYes());
                }
            }
            // 实际累计本金偿还额
            nifaRepayInfoEntity.setRepayCaptialYes(repayCY + "");
            // 实际累计利息偿还额
            nifaRepayInfoEntity.setRepayInterestYes(repayIY + "");
            // 借款剩余本金余额
            nifaRepayInfoEntity.setRepayCaptitalWait(repayCW + "");
            // 借款剩余应付利息
            nifaRepayInfoEntity.setRepayInterestWait(repayIW + "");
            nifaRepayInfoEntity.setRepayPlan(repayPlanListStr.substring(0, repayPlanListStr.length() - 1));
            // 实际还款记录 项目刚成立尚无还款记录时，填写日期为项目成立日期，本金利息记为零 还款日期、本金、利息、还款来源列表
            // 还款来源 1、借款人还款，2、机构垫付，3、保证金垫付
//            if (borrowRepay.getRepayMoneySource() == 1) {
            nifaRepayInfoEntity.setRepayDetials(repayDetilsStr.concat(":01"));
//            } else {
//                nifaRepayInfoEntity.setRepayDetials(repayDetilsStr.concat(":03"));
//            }
            // 最后一期还款日
            lasterRepayTime = borrowRepayPlanList.get(borrowRepayPlanList.size() - 1).getRepayTime();
            // 逾期次数初始设置
            nifaRepayInfoEntity.setLateCounts("0");
            // 项目状态 01项目新成立、02还款中、03正常还款已结清、04提前还款已结清
            if (repayPeriod.equals(borrow.getBorrowPeriod())) {
                nifaRepayInfoEntity.setReverifyStatus("03");
                // 逾期原因 如项目状态为05、06、07、08、11、12、13，则需报送该项目的逾期原因。长度512位
                nifaRepayInfoEntity.setLateReason("");
                // 逾期次数 指已逾期的期数，如项目状态为05、06、07、08、11、12、13，则需报送该项目的逾期次数。
                nifaRepayInfoEntity.setLateCounts("0");
            } else {
                nifaRepayInfoEntity.setReverifyStatus("02");
                // 逾期原因 如项目状态为05、06、07、08、11、12、13，则需报送该项目的逾期原因。长度512位
                if (borrowRepayPlanList.get(0).getAdvanceStatus() == 3) {
                    nifaRepayInfoEntity.setReverifyStatus("05");
//                    String laterRepayReason = RedisUtils.get(RedisConstants.LATER_REPAY_REASON);
                    nifaRepayInfoEntity.setLateReason("借款人到期未提交银行还款");
                    // 逾期次数 指已逾期的期数，如项目状态为05、06、07、08、11、12、13，则需报送该项目的逾期次数。
                    nifaRepayInfoEntity.setLateCounts(laterCount + "");
                }
            }
            // 还款期数
            nifaRepayInfoEntity.setRepayPeriod(borrow.getBorrowPeriod() + "");
        } else {
            Integer repayActionTime;
            lasterRepayTime = borrowRepay.getRepayTime();
            repayActionTime = borrowRepay.getRepayActionTime();
            // 约定还款计划 每期的还款日期、本金、利息列表，用冒号分隔，用分号分段,结尾不写分号
            nifaRepayInfoEntity.setRepayPlan(GetDate.timestamptoStrYYYYMMDD(lasterRepayTime) + ":" + borrowRepay.getRepayCapital() + ":" + borrowRepay.getRepayInterest());
            // 实际还款记录 项目刚成立尚无还款记录时，填写日期为项目成立日期，本金利息记为零 还款日期、本金、利息、还款来源列表
            // 还款来源 1、借款人还款，2、机构垫付，3、保证金垫付
//            if (borrowRepay.getRepayMoneySource() == 1) {
            nifaRepayInfoEntity.setRepayDetials(GetDate.timestamptoStrYYYYMMDD(repayActionTime) + ":" + borrowRepay.getRepayCapitalYes() + "" + ":" + borrowRepay.getRepayInterestYes() + ":01");
//            } else {
//                nifaRepayInfoEntity.setRepayDetials(GetDate.timestamptoStrYYYYMMDD(repayActionTime) + ":" + borrowRepay.getRepayCapitalYes() + "" + ":" + borrowRepay.getRepayInterestYes() + ":03");
//            }
            // 逾期次数 指已逾期的期数，如项目状态为05、06、07、08、11、12、13，则需报送该项目的逾期次数。
            nifaRepayInfoEntity.setLateCounts("0");
            // 项目状态 01项目新成立、02还款中、03正常还款已结清、04提前还款已结清
            // 判断是否提前、逾期还款1：提前3：逾期
            if (borrowRepay.getAdvanceStatus() == 1) {
                nifaRepayInfoEntity.setReverifyStatus("04");
                // 逾期原因 如项目状态为05、06、07、08、11、12、13，则需报送该项目的逾期原因。长度512位
                nifaRepayInfoEntity.setLateReason("");
            } else if (borrowRepay.getAdvanceStatus() == 3) {
                nifaRepayInfoEntity.setReverifyStatus("13");
                // 逾期原因 如项目状态为05、06、07、08、11、12、13，则需报送该项目的逾期原因。长度512位
//                String laterRepayReason = RedisUtils.get(RedisConstants.LATER_REPAY_REASON);
//                if (StringUtils.isNotBlank(laterRepayReason)) {
//                    nifaRepayInfoEntity.setLateReason(laterRepayReason);
//                }
                nifaRepayInfoEntity.setLateReason("借款人到期未提交银行还款");
                // 逾期次数 指已逾期的期数，如项目状态为05、06、07、08、11、12、13，则需报送该项目的逾期次数。
                nifaRepayInfoEntity.setLateCounts("1");
            } else {
                nifaRepayInfoEntity.setReverifyStatus("03");
                nifaRepayInfoEntity.setLateReason("");
            }
            // 还款期数
            nifaRepayInfoEntity.setRepayPeriod("1");
            // 实际累计本金偿还额
            nifaRepayInfoEntity.setRepayCaptialYes(borrowRepay.getRepayCapitalYes() + "");
            // 实际累计利息偿还额
            nifaRepayInfoEntity.setRepayInterestYes(borrowRepay.getRepayInterestYes() + "");
            // 借款剩余本金余额
            nifaRepayInfoEntity.setRepayCaptitalWait(borrowRepay.getRepayCapitalWait() + "");
            // 借款剩余应付利息
            nifaRepayInfoEntity.setRepayInterestWait(borrowRepay.getRepayInterestWait() + "");
        }
        // 借款期限 借款合同约定的期限，单位日，按年，月等计算的，需转化为日
        try {
            nifaRepayInfoEntity.setBorrowDays("" + GetDate.daysBetween(GetDate.timestamptoStrYYYYMMDD(borrow.getRecoverLastTime()), GetDate.timestamptoStrYYYYMMDD(lasterRepayTime)));
        } catch (ParseException e) {
            logger.error(e.getMessage());
            logger.error(logHeader + "借款期限计算失败！！borrowNid:{}", borrow.getBorrowNid());
            return false;
        }
        // 出借利率 合同约定的出借利率（年化，利率固定时填写），8(8)有效数字8位，保留小数点后8位
        nifaRepayInfoEntity.setBorrowApr(borrow.getBorrowApr().divide(new BigDecimal(100), 8, BigDecimal.ROUND_DOWN).toPlainString());
        // 项目费用 平台向借款人收取的费用金额，15(4)有效数字15位，保留小数点后4位，以元为单位。(项目服务费+还款服务费)
        BigDecimal projectFee = recoverFee.add(borrowRepay.getRepayFee());
        nifaRepayInfoEntity.setProjectFee(projectFee + "");
        // 项目费率 平台向借款人收取的费率（按年化计算），项目费用与借款金额的比率，8(8)有效数字8位，保留小数点后8位。
        BigDecimal tmeBD1 = borrow.getAccount().multiply(new BigDecimal(borrow.getBorrowPeriod() + ""));
        if (tmeBD1.compareTo(BigDecimal.ZERO) <= 0) {
            logger.error(logHeader + "项目费率计算失败！！Account：" + borrow.getAccount());
            return false;
        }
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle())) {
            // 按天 项目费用/借款金额/项目天数*365
            nifaRepayInfoEntity.setProjectFeeRate(projectFee.multiply(new BigDecimal("365")).divide(tmeBD1, 8, BigDecimal.ROUND_DOWN).toPlainString());
        } else {
            // 按月 项目费用/借款金额/项目期限*12
            nifaRepayInfoEntity.setProjectFeeRate(projectFee.multiply(new BigDecimal("12")).divide(tmeBD1, 8, BigDecimal.ROUND_DOWN).toPlainString());
        }
        // 其他费用 除项目费用外的其他费用，15(4)有效数字15位，保留小数点后4位 报送0
        nifaRepayInfoEntity.setOtherFee("0");
        // 还款保证措施 01保证金（风险准备金）、02有担保公司担保、03无担保公司担保、04保险、05信用、06回购、07第三方收购。可多选，用冒号分隔 均报送03
        nifaRepayInfoEntity.setMeasures("03");
        // 抵质押详情有内容报送02 03 其他报送01
        // 资产属性 1:抵押标 2:质押标 3:信用标 4:债权转让标 5:净值标
        if (borrow.getAssetAttributes() == 1) {
            nifaRepayInfoEntity.setGuaranteeType("02");
        } else if (borrow.getAssetAttributes() == 2) {
            nifaRepayInfoEntity.setGuaranteeType("03");
        } else {
            nifaRepayInfoEntity.setGuaranteeType("01");
        }
        // 担保公司名称 不报送
        nifaRepayInfoEntity.setGuaranteeCompany("");
        // 是否支持转让 0否1是，长度1位
        nifaRepayInfoEntity.setIsCredit("1");
        // 借款用途
        nifaRepayInfoEntity.setFinancePurpose(borrow.getFinancePurpose());
        // 出借人个数
        Integer lenderCounts = borrowRecoverList.size();
        nifaRepayInfoEntity.setLenderCounts(lenderCounts + "");
        return true;
    }
}
