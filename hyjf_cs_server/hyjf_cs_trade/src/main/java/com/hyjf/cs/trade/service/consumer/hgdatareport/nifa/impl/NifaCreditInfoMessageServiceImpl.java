/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.nifa.impl;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.hgreportdata.nifa.NifaBorrowInfoResponse;
import com.hyjf.am.response.hgreportdata.nifa.NifaCreditInfoResponse;
import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaCreditInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaCreditTransferVO;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.trade.service.consumer.hgdatareport.nifa.NifaCreditInfoMessageService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * @author PC-LIUSHOUYI
 * @version NifaCreditInfoMessageServiceImpl, v0.1 2019/1/24 16:39
 */
@Service
public class NifaCreditInfoMessageServiceImpl extends BaseTradeServiceImpl implements NifaCreditInfoMessageService {

    /**
     * 获取18位社会信用代码
     */
    @Value("${hyjf.com.social.credit.code}")
    private String COM_SOCIAL_CREDIT_CODE;

    private String thisMessName = "互金标的相关债转信息上送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_NIFA + " " + thisMessName + "】";
    private String baseUrl = "http://CS-MESSAGE/cs-message/nifaStatistical/";

    @Autowired
    private BaseClient baseClient;

    /**
     * 散标债转出让人数据处理
     *
     * @param assignPay
     * @param creditNid
     * @param usersInfo
     * @param nifaCreditTransferEntity
     * @return
     */
    @Override
    public boolean selectDualNifaCreditTransfer(BigDecimal assignPay, String creditNid, UserInfoVO usersInfo, NifaCreditTransferVO nifaCreditTransferEntity) {
        // 项目唯一编号    系统内唯一编号，是社会信用代码、平台序号和报数机构项目编号的组合，长度128位，与项目信息项目唯一编号相对应。
        nifaCreditTransferEntity.setProjectNo(COM_SOCIAL_CREDIT_CODE + "1" + creditNid);
        // 受让人（出借人）类型    01自然人,02法人
        nifaCreditTransferEntity.setUserType("01");
        // 受让人（出借人）ID    全平台唯一的ID号，用以识别该用户身份，32位  承接人userid
        nifaCreditTransferEntity.setUserId(usersInfo.getUserId() + "");
        // 证件类型    01身份证、
        nifaCreditTransferEntity.setCardType("01");
        // 证件号码    32位    ANC    M
        nifaCreditTransferEntity.setIdCard(usersInfo.getIdcard());
        // 职业类型        不报送
        nifaCreditTransferEntity.setProfessionType("");
        // 所属地区     不报送
        nifaCreditTransferEntity.setArea("");
        // 所属行业    不报送
        nifaCreditTransferEntity.setIndustry("");
        // 受让（出借）金额    该受让人所受让的债权实际交易成交价格，以元为单位，15(4)有效数字15位，保留小数点后4位。 出借人支付金额
        nifaCreditTransferEntity.setAssignPay(assignPay + "");
        // 受让（出借）状态    01互联网债权类融资-个体直接借贷、02互联网金融产品及收益权转让融资-客户持有的债权受让、03互联网金融产品及收益权转让融资-客户持有的债权出让、04互联网金融产品及收益权转让融资-客户持有的债权部分出让长度2位。报数机构报送状态为02，其余状态由协会系统在状态改变时自动处理完成，无需报数机构报送。    AN(字典表)    M    2
        nifaCreditTransferEntity.setAssignStatus("02");
        return true;
    }

    /**
     * 处理散标债转数据
     *
     * @param assignRepayAccount
     * @param assignPay
     * @param creditFee
     * @param count
     * @param borrowCredit
     * @param borrow
     * @param nifaCreditInfoEntity
     * @return
     */
    @Override
    public boolean selectDualNifaCreditInfo(BigDecimal assignRepayAccount, BigDecimal assignPay, BigDecimal creditFee, Integer count, String historyData, BorrowCreditVO borrowCredit, BorrowAndInfoVO borrow, NifaCreditInfoVO nifaCreditInfoEntity) {

        // 项目成立日期 借款人和出借人债权债务关系的成立日期，格式为YYYYMMDD
        nifaCreditInfoEntity.setHistoryData(historyData);
        // 项目唯一编号    系统内唯一编号，是社会信用代码、平台序号和报数机构项目编号的组合，长度128位
        nifaCreditInfoEntity.setProjectNo(COM_SOCIAL_CREDIT_CODE + "1" + borrowCredit.getCreditNid());
        // 社会信用代码    企业基本信息中的社会信用代码，长度18位91110108078587665C
        nifaCreditInfoEntity.setSocialCreditCode(COM_SOCIAL_CREDIT_CODE);
        // 平台序号    企业基本信息中平台名称中包含的平台序号,长度1位，取值为1-9。 1
        nifaCreditInfoEntity.setPlantformNo("1");
        // 项目编号    报数机构内部项目编号，长度64位 债转编号
        nifaCreditInfoEntity.setCreditNid(borrowCredit.getCreditNid() + "");
        // 项目名称    平台命名的项目名称，长度64位 标的项目名称
        nifaCreditInfoEntity.setName(StringUtils.isNotBlank(borrow.getProjectName()) ? borrow.getProjectName() : borrow.getBorrowNid());
        // 项目转让类型    01债权转让、02其他金融产品转让、03收益权转让，长度2位       1
        nifaCreditInfoEntity.setCreditType("01");
        // 项目来源    01 来自平台自身、02 来自外部机构，2位     1
        nifaCreditInfoEntity.setCreditFrom("01");
        // 平台自身债权转让原项目编号    项目来源是平台自身债权转让的，为原项目的项目唯一编号,项目来源为01时填写   原始债权项目编号
        nifaCreditInfoEntity.setBorrowNid(borrowCredit.getBidNid());
        // 平台自身债权转让原出借人ID（出让人）    项目来源是平台自身债权转让的，为原出借人的出借人ID，项目来源为01时填写   原始债权出让人ID
        nifaCreditInfoEntity.setCreditUserId(borrowCredit.getCreditUserId() + "");
        // 来自外部机构转让的机构名称 不报送
        nifaCreditInfoEntity.setOutOrgName("");
        // 来自外部机构转让的机构证件类型 不报送
        nifaCreditInfoEntity.setOutOrgType("");
        // 来自外部机构转让的机构证件号码  不报送
        nifaCreditInfoEntity.setOutOrgCradNo("");

        // 是否分期还款
        boolean isMonth = false;
        // 还款方式 去还款表取数据
        if (CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())) {
            // 等额本息
            isMonth = true;
            // 原借款还款方式    01按月付息到期还本、02按季付息到期还本、03按月等额本息还款、04按季等额本息还款、05到期一次性还本付息、06按月等额本金还款、07按季等额本金还款、08按月等本等息还款、09半年付息到期还本、10随时提前还款、99个性化还款方式，长度2位
            nifaCreditInfoEntity.setBorrowType("03");
        } else if (CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle())) {
            // 按月计息，到期还本还息
            isMonth = false;
            nifaCreditInfoEntity.setBorrowType("05");
        } else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle())) {
            // 先息后本
            isMonth = true;
            nifaCreditInfoEntity.setBorrowType("01");
        } else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle())) {
            // 按天计息，到期还本息
            isMonth = false;
            nifaCreditInfoEntity.setBorrowType("05");
        } else if (CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle())) {
            // 等额本金
            isMonth = true;
            nifaCreditInfoEntity.setBorrowType("06");
        }
        // 分期和不分期 原借款剩余期数    原借款项目剩余期数整数 剩余期数 总期数减去债转期数+1
        if (isMonth) {
            Integer remaindPeriod = borrow.getBorrowPeriod() + 1 - borrowCredit.getCreditPeriod();
            nifaCreditInfoEntity.setRepayPeriod(remaindPeriod + "");
        } else {
            nifaCreditInfoEntity.setRepayPeriod("1");
        }
        // 原借款剩余本金    转让项目成立时，原借款项目的剩余本金余额，以元为单位，15(4)有效数字15位，保留小数点后4位     剩余本金
        BigDecimal tmpBD = borrowCredit.getCreditCapital().subtract(borrowCredit.getCreditCapitalAssigned());
        if (tmpBD.compareTo(BigDecimal.ZERO) <= 0) {
            tmpBD = new BigDecimal("0.0001");
        }
        nifaCreditInfoEntity.setAssignCapital(tmpBD + "");
        // 原借款剩余收益    转让项目成立时，原借款项目的剩余应付利息，以元为单位，15(4)有效数字15位，保留小数点后4位     应垫付利息
        nifaCreditInfoEntity.setAssignInterestAdvance(borrowCredit.getCreditInterest().subtract(borrowCredit.getCreditInterestAssigned()) + "");
        // 原借款用途    01个人消费、02个人经营、03个人资金周转、04房贷、05企业经营、06企业周转、99其他，长度2位    借款用途
        nifaCreditInfoEntity.setBorrowUse(borrow.getFinancePurpose());
        // 原借款项目状态 02 还款中
        nifaCreditInfoEntity.setOldBorrowStatus("02");
        // 原还款保证措施    01保证金（风险准备金）、02有担保公司担保、03无担保公司担保、04保险、05信用、06回购、07第三方收购。可多选，用冒号分隔    同原标的
        nifaCreditInfoEntity.setOldBorrowType("03");
        // 担保方式    在还款保证措施里选择02或03时才需要选择：担保方式为：01保证、02抵押、03质押、04留置、05定金，单选    同原标的
        // 资产属性 1:抵押标 2:质押标 3:信用标 4:债权转让标 5:净值标
        if (borrow.getAssetAttributes() == 1) {
            nifaCreditInfoEntity.setGuaranteeType("02");
        } else if (borrow.getAssetAttributes() == 2) {
            nifaCreditInfoEntity.setGuaranteeType("03");
        } else {
            nifaCreditInfoEntity.setGuaranteeType("01");
        }
        // 担保公司名称    选择还款保证措施有02时填写，为工商注册名称，长度100位    不报送
        nifaCreditInfoEntity.setGuaranteeCompany("");
        // 转让金额    交易双方实际债权转让交易的价格，以元为单位，15(4)有效数字15位，保留小数点后4位     转让总金额(承接人付款的钱)
        nifaCreditInfoEntity.setAssignAccount(assignPay + "");
        // 转让币种    GB/T 12406-2008，表示资金和货币的代码，长度3位    CNY
        nifaCreditInfoEntity.setCurrency("CNY");
        // 转让利率计算用
        BigDecimal tmpBD1 = (assignRepayAccount.subtract(assignPay)).multiply(new BigDecimal("365"));
        // 转让利率计算用
        BigDecimal tmpBD2 = assignPay.multiply(new BigDecimal(borrowCredit.getCreditTerm()));
        if (tmpBD2.compareTo(BigDecimal.ZERO) <= 0) {
            logger.error(logHeader + "转让利率计算失败！！assignRepayAccount：" + assignRepayAccount + "，assignPay：" + assignPay + "，CreditTerm：" + borrowCredit.getCreditTerm());
            return false;
        }
        // 转让利率    计算方式为：转让利率=（待收本息-转让价格）∕转让价格∕剩余天数*365。8(8)有效数字8位，保留小数点后8位     承接人的已承接部分进行计算
//        nifaCreditInfoEntity.setAssignServiceRate(assignRepayAccount.subtract(assignPay).multiply(new BigDecimal("365")).divide(assignPay.multiply(new BigDecimal(borrowCredit.getCreditTerm())), 8,BigDecimal.ROUND_HALF_UP) + "");
        nifaCreditInfoEntity.setAssignServiceRate(tmpBD1.divide(tmpBD2, 8, BigDecimal.ROUND_DOWN).toPlainString());
        // 转让费率    按年化计算，计算方式为：转让费率 =转让手续费/转让金额 / 剩余天数 * 365。8(8)有效数字8位，保留小数点后8位。
        BigDecimal tmpBD3 = creditFee.multiply(new BigDecimal("365"));
        BigDecimal tmpBD4 = assignPay.multiply(new BigDecimal(borrowCredit.getCreditTerm()));
        if (tmpBD4.compareTo(BigDecimal.ZERO) <= 0) {
            logger.error(logHeader + "转让费率计算失败！！assignPay：" + assignPay + "，CreditTerm：" + borrowCredit.getCreditTerm());
            return false;
        }
        nifaCreditInfoEntity.setAssignServiceApr(tmpBD3.divide(tmpBD4, 8, BigDecimal.ROUND_DOWN).toPlainString());
//        nifaCreditInfoEntity.setAssignServiceApr(creditFee.multiply(new BigDecimal("365")).divide(borrowCredit.getCreditAccount().multiply(new BigDecimal(borrowCredit.getCreditTerm())), 8, BigDecimal.ROUND_DOWN) + "");
        // 转让费用    项目转让时发生的费用，以元为单位，15(4)有效数字15位，保留小数点后4位。
        // 承接人表中转让费用累计
        nifaCreditInfoEntity.setAssignServiceFee(creditFee + "");
        // 转让时间    项目转让成立的时间，格式为YYYYMMDD     转让发起时间
        nifaCreditInfoEntity.setAssignServiceTime(GetDate.date2Str(borrowCredit.getCreateTime(),new SimpleDateFormat("yyyy-MM-dd")).replaceAll("-", "") + "");
        // 受让人个数    项目转让成立时受让的人数
        nifaCreditInfoEntity.setUserCount(count + "");
        return true;
    }


    /**
     * 处理计划债转上送信息
     *
     * @param assignRepayAccount
     * @param creditFee
     * @param assignPay
     * @param tenderCapital
     * @param tenderInterest
     * @param borrowNid            原项目编号
     * @param historyData
     * @param hjhDebtCredit
     * @param borrow
     * @param nifaCreditInfoEntity
     * @return
     */
    @Override
    public boolean selectDualNifaHjhFirstCreditInfo(BigDecimal assignRepayAccount, BigDecimal creditFee, BigDecimal assignPay, BigDecimal tenderCapital, BigDecimal tenderInterest, Integer count, String borrowNid, String historyData, HjhDebtCreditVO hjhDebtCredit, BorrowAndInfoVO borrow, NifaCreditInfoVO nifaCreditInfoEntity) {
        // 项目成立日期 借款人和出借人债权债务关系的成立日期，格式为YYYYMMDD
        nifaCreditInfoEntity.setHistoryData(historyData);
        // 项目唯一编号    系统内唯一编号，是社会信用代码、平台序号和报数机构项目编号的组合，长度128位
        nifaCreditInfoEntity.setProjectNo(COM_SOCIAL_CREDIT_CODE + "1" + hjhDebtCredit.getCreditNid());
        // 社会信用代码    企业基本信息中的社会信用代码，长度18位91110108078587665C
        nifaCreditInfoEntity.setSocialCreditCode(COM_SOCIAL_CREDIT_CODE);
        // 平台序号    企业基本信息中平台名称中包含的平台序号,长度1位，取值为1-9。 1
        nifaCreditInfoEntity.setPlantformNo("1");
        // 项目编号    报数机构内部项目编号，长度64位 债转编号
        nifaCreditInfoEntity.setCreditNid(hjhDebtCredit.getCreditNid() + "");
        // 项目名称    平台命名的项目名称，长度64位 标的项目名称
        nifaCreditInfoEntity.setName(StringUtils.isNotBlank(borrow.getProjectName()) ? borrow.getProjectName() : borrow.getBorrowNid());
        // 项目转让类型    01债权转让、02其他金融产品转让、03收益权转让，长度2位       1
        nifaCreditInfoEntity.setCreditType("01");
        // 项目来源    01 来自平台自身、02 来自外部机构，2位     1
        nifaCreditInfoEntity.setCreditFrom("01");
        // 平台自身债权转让原项目编号    项目来源是平台自身债权转让的，为原项目的项目唯一编号,项目来源为01时填写   原始债权项目编号
        nifaCreditInfoEntity.setBorrowNid(borrowNid);
        // 平台自身债权转让原出借人ID（出让人）    项目来源是平台自身债权转让的，为原出借人的出借人ID，项目来源为01时填写   原始债权出让人ID
        nifaCreditInfoEntity.setCreditUserId(hjhDebtCredit.getUserId() + "");
        // 来自外部机构转让的机构名称 不报送
        nifaCreditInfoEntity.setOutOrgName("");
        // 来自外部机构转让的机构证件类型 不报送
        nifaCreditInfoEntity.setOutOrgType("");
        // 来自外部机构转让的机构证件号码  不报送
        nifaCreditInfoEntity.setOutOrgCradNo("");

        // 是否分期还款
        boolean isMonth = false;
        // 还款方式 去还款表取数据
        if (CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())) {
            // 等额本息
            isMonth = true;
            // 原借款还款方式    01按月付息到期还本、02按季付息到期还本、03按月等额本息还款、04按季等额本息还款、05到期一次性还本付息、06按月等额本金还款、07按季等额本金还款、08按月等本等息还款、09半年付息到期还本、10随时提前还款、99个性化还款方式，长度2位
            nifaCreditInfoEntity.setBorrowType("03");
        } else if (CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle())) {
            // 按月计息，到期还本还息
            isMonth = false;
            nifaCreditInfoEntity.setBorrowType("05");
        } else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle())) {
            // 先息后本
            isMonth = true;
            nifaCreditInfoEntity.setBorrowType("01");
        } else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle())) {
            // 按天计息，到期还本息
            isMonth = false;
            nifaCreditInfoEntity.setBorrowType("05");
        } else if (CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle())) {
            // 等额本金
            isMonth = true;
            nifaCreditInfoEntity.setBorrowType("06");
        }
        if (isMonth) {
            // 分期和不分期 原借款剩余期数    原借款项目剩余期数整数 剩余期数 总期数减去债转期数+1
            Integer remaindPeriod = borrow.getBorrowPeriod() + 1 - hjhDebtCredit.getLiquidatesPeriod();
            nifaCreditInfoEntity.setRepayPeriod(remaindPeriod + "");
        } else {
            nifaCreditInfoEntity.setRepayPeriod("1");
        }
        // 原借款剩余本金    转让项目成立时，原借款项目的剩余本金余额，以元为单位，15(4)有效数字15位，保留小数点后4位     剩余本金
        BigDecimal tmpBD = hjhDebtCredit.getCreditCapital().subtract(tenderCapital);
        if (tmpBD.compareTo(BigDecimal.ZERO) <= 0) {
            tmpBD = new BigDecimal("0.0001");
        }
        nifaCreditInfoEntity.setAssignCapital(tmpBD + "");
//        nifaCreditInfoEntity.setAssignCapital(hjhDebtCredit.getCreditCapital().subtract(tenderCapital) + "");
        // 原借款剩余收益    转让项目成立时，原借款项目的剩余应付利息，以元为单位，15(4)有效数字15位，保留小数点后4位     应垫付利息
        nifaCreditInfoEntity.setAssignInterestAdvance(hjhDebtCredit.getCreditInterest().subtract(tenderInterest) + "");
        // 原借款用途    01个人消费、02个人经营、03个人资金周转、04房贷、05企业经营、06企业周转、99其他，长度2位    借款用途
        nifaCreditInfoEntity.setBorrowUse(borrow.getFinancePurpose());
        // 原借款项目状态 02 还款中
        nifaCreditInfoEntity.setOldBorrowStatus("02");
        // 原还款保证措施    01保证金（风险准备金）、02有担保公司担保、03无担保公司担保、04保险、05信用、06回购、07第三方收购。可多选，用冒号分隔    同原标的
        nifaCreditInfoEntity.setOldBorrowType("03");
        // 担保方式    在还款保证措施里选择02或03时才需要选择：担保方式为：01保证、02抵押、03质押、04留置、05定金，单选    同原标的
        // 资产属性 1:抵押标 2:质押标 3:信用标 4:债权转让标 5:净值标
        if (borrow.getAssetAttributes() == 1) {
            nifaCreditInfoEntity.setGuaranteeType("02");
        } else if (borrow.getAssetAttributes() == 2) {
            nifaCreditInfoEntity.setGuaranteeType("03");
        } else {
            nifaCreditInfoEntity.setGuaranteeType("01");
        }
        // 担保公司名称    选择还款保证措施有02时填写，为工商注册名称，长度100位    不报送
        nifaCreditInfoEntity.setGuaranteeCompany("");
        // 转让金额    交易双方实际债权转让交易的价格，以元为单位，15(4)有效数字15位，保留小数点后4位     转让总金额(承接人付款的钱)
        nifaCreditInfoEntity.setAssignAccount(assignPay + "");
        // 转让币种    GB/T 12406-2008，表示资金和货币的代码，长度3位    CNY
        nifaCreditInfoEntity.setCurrency("CNY");
        if (hjhDebtCredit.getRemainDays() <= 0) {
            // 逾期债转的remaindays是0的情况设定固定值
            nifaCreditInfoEntity.setAssignServiceRate("0.21900000");
            nifaCreditInfoEntity.setAssignServiceApr("0.00000001");
        } else {
            // 转让利率    计算方式为：转让利率=（待收本息-转让价格）∕转让价格∕剩余天数*365。8(8)有效数字8位，保留小数点后8位     承接人的已承接部分进行计算
            BigDecimal tmpBD1 = (assignRepayAccount.subtract(assignPay)).multiply(new BigDecimal("365"));
            if (tmpBD1.compareTo(BigDecimal.ZERO) <= 0) {
                nifaCreditInfoEntity.setAssignServiceRate("0.00000001");
            } else {
                BigDecimal tmpBD2 = assignPay.multiply(new BigDecimal(hjhDebtCredit.getRemainDays()));
                if (tmpBD2.compareTo(BigDecimal.ZERO) <= 0) {
                    logger.error(logHeader + "智投转让利率计算失败！！assignRepayAccount：" + assignRepayAccount + "，CreditPrice：" + hjhDebtCredit.getCreditPrice() + "，RemainDays：" + hjhDebtCredit.getRemainDays());
                    return false;
                }
                nifaCreditInfoEntity.setAssignServiceRate(tmpBD1.divide(tmpBD2, 8, BigDecimal.ROUND_DOWN).toPlainString());
            }
            //        nifaCreditInfoEntity.setAssignServiceRate(assignRepayAccount.subtract(hjhDebtCredit.getCreditPrice()).multiply(new BigDecimal("365")).divide(hjhDebtCredit.getCreditPrice().multiply(new BigDecimal(hjhDebtCredit.getRemainDays())), 8, BigDecimal.ROUND_DOWN) + "");
            // 转让费率    按年化计算，计算方式为：转让费率 =转让手续费/转让金额 / 剩余天数 * 365。8(8)有效数字8位，保留小数点后8位。
            BigDecimal tmpBD3 = creditFee.multiply(new BigDecimal("365"));
            BigDecimal tmpBD4 = assignPay.multiply(new BigDecimal(hjhDebtCredit.getRemainDays()));
            if (tmpBD4.compareTo(BigDecimal.ZERO) <= 0) {
                logger.error(logHeader + "智投转让利率计算失败！！CreditServiceFee：" + hjhDebtCredit.getCreditServiceFee() + "，CreditAccountAssigned：" + hjhDebtCredit.getCreditAccountAssigned() + "，RemainDays：" + hjhDebtCredit.getRemainDays());
                return false;
            }
            nifaCreditInfoEntity.setAssignServiceApr(tmpBD3.divide(tmpBD4, 8, BigDecimal.ROUND_DOWN).toPlainString());
//        nifaCreditInfoEntity.setAssignServiceApr(hjhDebtCredit.getCreditServiceFee().multiply(new BigDecimal("365")).divide(hjhDebtCredit.getCreditAccountAssigned().multiply(new BigDecimal(hjhDebtCredit.getRemainDays())), 8, BigDecimal.ROUND_DOWN) + "");
        }
        // 转让费用    项目转让时发生的费用，以元为单位，15(4)有效数字15位，保留小数点后4位。
        nifaCreditInfoEntity.setAssignServiceFee(hjhDebtCredit.getCreditServiceFee() + "");
        // 转让时间    项目转让成立的时间，格式为YYYYMMDD     转让发起时间
        nifaCreditInfoEntity.setAssignServiceTime(GetDate.date2Str(hjhDebtCredit.getCreateTime(),new SimpleDateFormat("yyyy-MM-dd")).replaceAll("-", "") + "");
        // 受让人个数    项目转让成立时受让的人数
        nifaCreditInfoEntity.setUserCount(count + "");
        return true;
    }

    /**
     * 散标债转数据拉取
     *
     * @param creditNid
     * @return
     */
    @Override
    public NifaCreditInfoVO selectNifaCreditInfoByCreditNid(String creditNid) {
        NifaCreditInfoVO request = new NifaCreditInfoVO();
        request.setProjectNo(COM_SOCIAL_CREDIT_CODE + "1" + creditNid);
        String url = baseUrl + "selectNifaCreditInfoByCreditNid";
        NifaCreditInfoResponse nifaBorrowInfoResponse = this.baseClient.postExe(url, request, NifaCreditInfoResponse.class);
        NifaCreditInfoVO nifaCreditInfoVO = nifaBorrowInfoResponse.getResult();
        if (null != nifaCreditInfoVO) {
            return nifaCreditInfoVO;
        }
        return null;
    }

    /**
     * 保存散标债转承接人信息
     *
     * @param nifaCreditTransferEntity
     * @return
     */
    @Override
    public boolean insertNifaCreditTransfer(NifaCreditTransferVO nifaCreditTransferEntity) {
        String url = baseUrl + "insertNifaCreditTransfer";
        BooleanResponse response = this.baseClient.postExe(url, nifaCreditTransferEntity, BooleanResponse.class);
        return response.getResultBoolean();
    }

    /**
     * 保存散标债转信息
     *
     * @param nifaCreditInfoEntity
     * @return
     */
    @Override
    public boolean insertNifaCreditInfo(NifaCreditInfoVO nifaCreditInfoEntity) {
        String url = baseUrl + "insertNifaCreditInfo";
        BooleanResponse response = this.baseClient.postExe(url, nifaCreditInfoEntity, BooleanResponse.class);
        return response.getResultBoolean();
    }
}
