package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.undertake;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallUtil;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.undertake.CertCreditInfoService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author nxl
 */

@Service
public class CertCreditInfoServiceImpl extends BaseHgCertReportServiceImpl implements CertCreditInfoService {

    Logger logger = LoggerFactory.getLogger(CertCreditInfoServiceImpl.class);
    private String thisMessName = "承接订单信息推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 获取标的的还款信息
     *
     * @param creditTenderNid
     * @return
     */
    @Override
    public JSONArray getBorrowTender(String creditTenderNid, String flag) {
        JSONArray json = new JSONArray();
        if (flag.equals("1")) {
            //代表散标
            List<CreditTenderVO> creditTenderList = amTradeClient.selectCreditTender(creditTenderNid);
            json = getBorrowCreditTenderInfo(creditTenderList, new JSONArray(), false);
        } else if (flag.equals("2")) {
            //智投服务
            List<HjhDebtCreditTenderVO> hjhDebtCreditTenderList = amTradeClient.selectHjhCreditTenderListByAssignOrderId(creditTenderNid);
            json = getHjhDebtCreditInfo(hjhDebtCreditTenderList, new JSONArray(), false);
        }
        return json;
    }

    /**
     * 日期转换
     *
     * @param repayTime
     * @return
     */
    public String dateFormatTransformationDate(Date repayTime, String flg) {
        if (flg.equals("H")) {
            //代表获取有时分秒
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(repayTime);
            return dateStr;
        }
        if (flg.equals("Y")) {
            //代表只有年与日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(repayTime);
            return dateStr;
        }
        return null;
    }
    /**
     * 查询散标的承接信息
     *
     * @param creditTenderList
     * @return
     */
    @Override
    public JSONArray getBorrowCreditTenderInfo(List<CreditTenderVO> creditTenderList, JSONArray json, boolean isOld) {
        try {
            if (null != creditTenderList && creditTenderList.size() > 0) {
                for (CreditTenderVO creditTender : creditTenderList) {
                    Map<String, Object> param = new LinkedHashMap<String, Object>();
                    int intCredit = Integer.parseInt(creditTender.getCreditNid());
                    //查找汇转让标的表
                    BorrowCreditVO borrowCredit = amTradeClient.getBorrowCreditByCreditNid(String.valueOf(intCredit));
                    if (null == borrowCredit) {
                        throw new Exception("承接(散标)记录推送,标的转让信息为空！！债转编号:" + intCredit);
                    }
                    BigDecimal creditD = borrowCredit.getCreditDiscount().divide(new BigDecimal("100"));
                    //承接人用户标示 Hash
                    String idHash = getUserIdcardHash(creditTender.getUserId());
                    //6.承接浮动金额：散标转让算法 承接本金*折让率 智投的转让报送0  智投的转让报送0
                    BigDecimal bigDecimalCredit = creditTender.getAssignCapital().multiply(creditD);
                    bigDecimalCredit = bigDecimalCredit.setScale(2, BigDecimal.ROUND_DOWN);
                    //7.承接预期年华收益率：原项目预期年化收益率  智投报送智投产品年化收益率
                    BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(creditTender.getBidNid());
                    // 投资年化收益率
                    String rate = CertCallUtil.convertLoanRate(borrow.getBorrowApr(), borrow.getBorrowPeriod(), borrow.getBorrowStyle());
                    //8.承接时间：系统记录的承接时间
                    //代表获取有时分秒
                    String tenderDate = dateFormatTransformationDate(creditTender.getCreateTime(),"H");
                    //9.投资红包：抵扣券报送 红包面值 加息券报送加息券到期收益  没使用券报送0
                    //获取优惠券信息
                    BigDecimal bigDecimalCouponQuota = amTradeClient.getRedPackageSum(creditTender.getAssignNid());

                    //接口版本号
                    param.put("version", CertCallConstant.CERT_CALL_VERSION);
                    //平台编号
                    param.put("sourceCode", systemConfig.getCertSourceCode());
                    //1.承接信息编号：承接订单号
                    param.put("unFinClaimId", creditTender.getAssignNid());
                    //2.转让编号：债转编号
                    param.put("transferId", creditTender.getCreditNid());
                    //3.债权信息编号：该转让对应的原始投资订单编号
                    param.put("finClaimId", creditTender.getCreditTenderNid());
                    //承接人用户标示 Hash
                    param.put("userIdcardHash", idHash);
                    //4.承接人投资资金额：承接本金金额
                    DecimalFormat dfAmount = new DecimalFormat("0.00");
                    String strAmount = dfAmount.format(creditTender.getAssignCapital());
                    param.put("takeAmount",strAmount);
                    //5.承接利息金额：承接人承接本金对应的待收利息金额
                    String strInterest = dfAmount.format(creditTender.getAssignInterest());
                    param.put("takeInterest",strInterest);
                    //6.承接浮动金额：散标转让算法承接本金*折让率 智投的转让报送0  智投的转让报送0
                    //承接本金*折让率  数值前加负
                    param.put("floatMoney", "-" + bigDecimalCredit.toString());
                    //7.承接预期年华收益率：原项目预期年化收益率  智投报送智投产品年化收益率
                    param.put("takeRate", rate);
                    //8.承接时间：系统记录的承接时间
                    param.put("takeTime", tenderDate);
                    //9.投资红包：抵扣券报送 红包面值 加息券报送加息券到期收益  没使用券报送0
                    param.put("redpackage", bigDecimalCouponQuota.toString());
                    //10.封闭截至时间：散标报送 到期日  智投报送承接日
                    param.put("lockTime", GetDate.times10toStrYYYYMMDD(borrow.getRepayLastTime()));
                    json.add(param);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return json;
    }

    /**
     * 获取智投承接信息
     *
     * @param hjhDebtCreditTenderList
     * @return
     */
    @Override
    public JSONArray getHjhDebtCreditInfo(List<HjhDebtCreditTenderVO> hjhDebtCreditTenderList, JSONArray json, boolean isOld) {
        try {
            if (null != hjhDebtCreditTenderList && hjhDebtCreditTenderList.size() > 0) {
                for (HjhDebtCreditTenderVO hjhDebtCreditTender : hjhDebtCreditTenderList) {
                    Map<String, Object> param = new LinkedHashMap<String, Object>();
                    //查找计划信息
                    HjhPlanVO hjhPlan = amTradeClient.getHjhPlan(hjhDebtCreditTender.getAssignPlanNid());
                    if (null == hjhPlan) {
                        throw new Exception("承接(计划)记录推送,计划的信息为空！！承接的计划编号:" + hjhDebtCreditTender.getAssignPlanNid());
                    }
                    String userIdcardHash = getUserIdcardHash(hjhDebtCreditTender.getUserId());
                    //8.承接时间：系统记录的承接时间
                    String takeTime = dateFormatTransformationDate(hjhDebtCreditTender.getCreateTime(), "H");
                    //10.封闭截至时间：散标报送 到期日  智投报送承接日
                    String lockTime = dateFormatTransformationDate(hjhDebtCreditTender.getCreateTime(), "Y");

                    //接口版本号
                    param.put("version", CertCallConstant.CERT_CALL_VERSION);
                    //平台编号
                    param.put("sourceCode", systemConfig.getCertSourceCode());
                    //1.承接信息编号：承接订单号
                    param.put("unFinClaimId", hjhDebtCreditTender.getAssignOrderId());
                    //2.转让编号：债转编号
                    param.put("transferId", hjhDebtCreditTender.getCreditNid());
                    //3.债权信息编号：该转让对应的原始投资订单编号
                    param.put("finClaimId", hjhDebtCreditTender.getInvestOrderId());
                    //承接人用户标示 Hash
                    param.put("userIdcardHash", userIdcardHash);
                    //4.承接人投资资金额：承接本金金额
                    DecimalFormat dfAmount = new DecimalFormat("0.00");
                    String strAmount = dfAmount.format(hjhDebtCreditTender.getAssignCapital());
                    param.put("takeAmount",strAmount);
                    //5.承接利息金额：承接人承接本金对应的待收利息金额
                    String strInterest = dfAmount.format(hjhDebtCreditTender.getAssignInterest());
                    param.put("takeInterest",strInterest);
                    //6.承接浮动金额：散标转让算法承接本金*折让率 智投的转让报送0  智投的转让报送0
                    param.put("floatMoney", "0");
                    //7.承接预期年华收益率：原项目预期年化收益率  智投报送智投产品年化收益率
                    String takeRate = CertCallUtil.convertLoanRate(hjhPlan.getExpectApr(), 0, null);
                    param.put("takeRate", takeRate);
                    //8.承接时间：系统记录的承接时间
                    param.put("takeTime", takeTime);
                    //9.投资红包：抵扣券报送 红包面值 加息券报送加息券到期收益  没使用券报送0
                    param.put("redpackage", "0");
                    //10.封闭截至时间：散标报送 到期日  智投报送承接日
                    param.put("lockTime", lockTime);
                    json.add(param);
                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return json;
    }

    /**
     * 用户身份证号hash值
     *
     * @param userId
     * @return
     */
    private String getUserIdcardHash(int userId) {
        UserInfoVO users = amUserClient.findUsersInfoById(userId);
        String idHash = "";
        if (null != users) {
            try {
                idHash = tool.idCardHash(users.getIdcard());
            } catch (Exception e) {
                logger.error(logHeader + " 用户标示哈希出错！", e);
            }
        }
        return idHash;
    }

}
