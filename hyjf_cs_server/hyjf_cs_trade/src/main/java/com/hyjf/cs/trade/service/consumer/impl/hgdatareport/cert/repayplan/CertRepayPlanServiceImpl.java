package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.repayplan;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.hgreportdata.cert.CertUserVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.repayplan.CertRepayPlanService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author nxl
 */

@Service
public class CertRepayPlanServiceImpl extends BaseHgCertReportServiceImpl implements CertRepayPlanService {

    Logger logger = LoggerFactory.getLogger(CertRepayPlanServiceImpl.class);
    private String thisMessName = "还款计划信息推送";
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
     * @param borrowNid
     * @return
     */
    @Override
    public JSONArray getBorrowReyapPlan(String borrowNid, JSONArray json, boolean isOld) {
        //根据标的编号查找标的信息
        try {
            //获取还款信息
            BorrowRepayVO repay = amTradeClient.getBorrowRepay(borrowNid);
            if (null == repay) {
                throw new Exception(logHeader + "标的还款信息为空！！borrowNid:" + borrowNid);
            }
            //标的信息
            BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
            if (null == borrow) {
                throw new Exception(logHeader + "标的信息为空！！borrowNid:" + borrowNid);
            }

            String borrowStyle = borrow.getBorrowStyle();
            // 是否分期(true:分期, false:不分期)
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
            String userIdcardHash = "";

            //借款人信息
            List<CertUserVO> certUserVOList = amUserClient.getCertUsersByUserId(borrow.getUserId());
            if (null != certUserVOList && certUserVOList.size() > 0) {
                CertUserVO certUser = certUserVOList.get(0);
                //如果借款人未上报,则从用户信息表中获取用户idCard
                if (null != certUser) {
                    userIdcardHash = certUser.getUserIdCardHash();
                } else {
                    userIdcardHash = getUserHashValue(borrow);
                }
            }
            //如果标的信息不为空,而且标的状态为放款中,即标的放款成功
            if (borrow.getStatus() != 4) {
                throw new Exception(logHeader + "标的未放款成功！！borrowNid:" + borrowNid);
            }
            if (isMonth) {
                //分期的标的
                //根据标的编号查找还款信息
                List<BorrowRepayPlanVO> listRepay = amTradeClient.getBorrowRepayPlansByBorrowNid(borrowNid);
                if (null != listRepay && listRepay.size() > 0) {
                    //循环取值
                    for (int i = 0; i < listRepay.size(); i++) {
                        BorrowRepayPlanVO borrowRepayPlan = listRepay.get(i);
                        Map<String, Object> param = new LinkedHashMap<String, Object>();
                        //接口版本号
                        param.put("version", CertCallConstant.CERT_CALL_VERSION);
                        //平台编号
                        param.put("sourceCode", systemConfig.getCertSourceCode());
                        //原散标编号
                        param.put("sourceProductCode", borrowNid);
                        //借款用户标示hash
                        param.put("userIdcardHash", userIdcardHash);
                        //总期数 分期还款的总期数
                        param.put("totalIssue", borrow.getBorrowPeriod().toString());
                        //当前期数  当期还款期数，每一期一条数据。
                        param.put("issue", borrowRepayPlan.getRepayPeriod().toString());
                        // 还款计划编号
                        // 还款计划编号是指每一个项目的每一次还款计划的唯一编号，平台内所有还款计划中编号唯一。如果没有则填写“散标编号+当前期数”
                        //还款计划编号：报送标的号+“-”+当前期数
                        param.put("replanId", borrowNid + "-" + borrowRepayPlan.getRepayPeriod());
                        //当期应还本金（元）
                        BigDecimal bdAmount = borrowRepayPlan.getRepayCapital();
                        bdAmount = bdAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
                        param.put("curFund", bdAmount.toString());
                        //当期应还利息（元）
                        BigDecimal bdCurInterest = borrowRepayPlan.getRepayInterest();
                        bdCurInterest = bdCurInterest.setScale(2, BigDecimal.ROUND_HALF_UP);
                        param.put("curInterest", bdCurInterest.toString());
                        // 应急中心二期，新加应还服务费
                        //本条记录（当期）应还服务费（元）
                        BigDecimal bdRepayFee = borrowRepayPlan.getRepayFee();
                        bdRepayFee = bdRepayFee.setScale(2, BigDecimal.ROUND_HALF_UP);
                        param.put("curServiceCharge",bdRepayFee.toString());
                        //当期应还款时间点
                        //当期应还时间点：报送当期应还日期23:59:59
                        param.put("repayTime", GetDate.times10toStrYYYYMMDD(repay.getRepayTime()) + " 23:59:59");
                        json.add(param);
                    }
                }
            } else {
                Map<String, Object> param = new LinkedHashMap<String, Object>();
                //不分期的标的
                //接口版本号
                param.put("version", CertCallConstant.CERT_CALL_VERSION);
                //平台编号
                param.put("sourceCode", systemConfig.getCertSourceCode());
                //原散标编号
                param.put("sourceProductCode", borrowNid);
                //借款用户标示hash
                param.put("userIdcardHash", userIdcardHash);
                //总期数 分期还款的总期数
                param.put("totalIssue", "1");
                //当前期数  当期还款期数，每一期一条数据。
                param.put("issue", "1");
                // 还款计划编号
                // 还款计划编号是指每一个项目的每一次还款计划的唯一编号，平台内所有还款计划中编号唯一。如果没有则填写“散标编号+当前期数”
                //还款计划编号：报送标的号+“-”+当前期数
                param.put("replanId", borrowNid + "-" + "1");
                //当期应还本金（元）
                BigDecimal bdAmount = borrow.getBorrowAccountYes();
                bdAmount = bdAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
                param.put("curFund", bdAmount.toString());
                //当期应还利息（元）
                BigDecimal bdCurInterest = borrow.getRepayAccountInterest();
                bdCurInterest = bdCurInterest.setScale(2, BigDecimal.ROUND_HALF_UP);
                param.put("curInterest", bdCurInterest.toString());
                // 应急中心二期，新加应还服务费
                //本条记录（当期）应还服务费（元）
                BigDecimal bdRepayFee = repay.getRepayFee();
                bdRepayFee = bdRepayFee.setScale(2, BigDecimal.ROUND_HALF_UP);
                param.put("curServiceCharge",bdRepayFee.toString());
                //当期应还款时间点
                //当期应还时间点：报送当期应还日期23:59:59
                param.put("repayTime", GetDate.times10toStrYYYYMMDD(repay.getRepayTime()) + " 23:59:59");
                json.add(param);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return json;
    }

    /**
     * 获取标的的哈希值
     *
     * @param borrow
     * @return
     * @throws Exception
     */
    public String getUserHashValue(BorrowAndInfoVO borrow) throws Exception {
        String userIdcard = "";
        if (borrow != null) {
            if ("1".equals(borrow.getCompanyOrPersonal())) {
                // 公司
                BorrowUserVO borrowUsers = getBorrowUsers(borrow.getBorrowNid());
                // 统一社会信用代码
                userIdcard = borrowUsers.getSocialCreditCode();
                if (borrowUsers.getSocialCreditCode() == null || "".equals(borrowUsers.getSocialCreditCode())) {
                    // 注册号
                    userIdcard = borrowUsers.getRegistCode();
                }
            } else {
                // 个人
                BorrowManinfoVO borrowManinfo = getBorrowManInfo(borrow.getBorrowNid());
                // 身份证号
                userIdcard = borrowManinfo.getCardNo();
            }
        }
        return tool.idCardHash(userIdcard);
    }
}
