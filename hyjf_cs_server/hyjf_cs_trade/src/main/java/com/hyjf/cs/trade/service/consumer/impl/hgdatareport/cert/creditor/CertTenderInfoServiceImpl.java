package com.hyjf.cs.trade.service.consumer.impl.hgdatareport.cert.creditor;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallUtil;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.creditor.CertTenderInfoService;
import com.hyjf.cs.trade.service.consumer.impl.BaseHgCertReportServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author nxl
 */

@Service
public class CertTenderInfoServiceImpl extends BaseHgCertReportServiceImpl implements CertTenderInfoService {

   /* @Autowired
    CertCreditInfoService certCreditInfoService;
    @Autowired
    CertBorrowStatusService certBorrowStatusService;*/
   @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    SystemConfig systemConfig;

    Logger logger = LoggerFactory.getLogger(CertTenderInfoServiceImpl.class);
    private String thisMessName = "债权信息信息推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    /**
     * 获取标的的债权信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public JSONArray getBorrowTender(String borrowNid, JSONArray json,boolean isOld) {
        //标的信息
        try {
            BorrowAndInfoVO borrowVO = amTradeClient.selectBorrowByNid(borrowNid);
            if (null == borrowVO) {
                throw new Exception(logHeader + "标的信息为空！！borrowNid:" + borrowNid);
            }
            //如果标的信息不为空,而且标的状态为放款中,即标的放款成功
            if(borrowVO.getStatus()!=4){
                throw new Exception(logHeader + "标的未放款成功！！borrowNid:" + borrowNid);
            }
            //原产品信息编号
            String sourceFinancingCode = "-1";
            BigDecimal sumRedPackage = new BigDecimal("0");
            List<BorrowTenderVO> borrowTenderList =amTradeClient.getBorrowTenderListByBorrowNid(borrowNid);
            if (null != borrowTenderList && borrowTenderList.size() > 0) {
                for (BorrowTenderVO borrowTender : borrowTenderList) {
                    //投资计息时间
                    String invTime = "";
                    //封闭截止时间
                    String lockTime = "";
                    Map<String, Object> param = new LinkedHashMap<String, Object>();
                    //投资预期年化收益率
                    String rate = CertCallUtil.convertLoanRate(borrowVO.getBorrowApr(), borrowVO.getBorrowPeriod(), borrowVO.getBorrowStyle());
                    //
                    //获取用户信息
                    UserInfoVO usersInfo = amUserClient.findUserInfoById(borrowTender.getUserId());
                    //投资人用户标示 Hash
                    String userIdcardHash = tool.idCardHash(usersInfo.getIdcard());
                    //
                    BorrowRecoverVO borrowRecoverRequest = new BorrowRecoverVO();
                    if (StringUtils.isNotBlank(borrowTender.getAccedeOrderId())) {
                        //计划
                        //根据智投编号查找智投信息
                        HjhAccedeVO hjhAccede = amTradeClient.getHjhAccede(borrowTender.getAccedeOrderId());
                        if (null != hjhAccede) {
                            borrowRecoverRequest.setNid(borrowTender.getNid());
                            borrowRecoverRequest.setBorrowNid(borrowNid);
                            borrowRecoverRequest.setAccedeOrderId(borrowTender.getAccedeOrderId());
                            BorrowRecoverVO borrowRecoverVO = amTradeClient.getRecoverDateByTenderNid(borrowRecoverRequest);
                            //投资计息时间
                            invTime = dateFormatTransformation(borrowRecoverVO.getCreateTime(),"H");
                            //封闭截止时间：智投报送计息日
                            lockTime = dateFormatTransformation(hjhAccede.getCreateTime(), "Y");
                            //红包
                            sumRedPackage= amTradeClient.getRedPackageSum(hjhAccede.getAccedeOrderId());
                        }
                    } else {
                        borrowRecoverRequest.setNid(borrowTender.getNid());
                        borrowRecoverRequest.setBorrowNid(borrowNid);
                        BorrowRecoverVO borrowRecoverVO = amTradeClient.getRecoverDateByTenderNid(borrowRecoverRequest);

                        //投资计息时间
                        invTime = dateFormatTransformation(borrowRecoverVO.getCreateTime(),"H");
                        //红包
                        sumRedPackage= amTradeClient.getRedPackageSum(borrowTender.getNid());
                        //封闭截止时间：散标报送计息日+30天
                        lockTime = getlockTime(invTime);
                    }
                    //截至日期
                    //接口版本号
                    param.put("version", CertCallConstant.CERT_CALL_VERSION);
                    //债权信息编号
                    param.put("finClaimId", borrowTender.getNid());
                    //平台编号
                    param.put("sourceCode", systemConfig.getCertSourceCode());
                    //原散标编号
                    param.put("sourceProductCode", borrowNid);
                    //原产品信息编号
                    param.put("sourceFinancingCode", sourceFinancingCode);
                    //投资人用户标示 Hash
                    param.put("userIdcardHash", userIdcardHash);
                    //投资 金额 (元)
                    DecimalFormat dfAmount = new DecimalFormat("0.00");
                    String strAmount = dfAmount.format(borrowTender.getAccount());
                    param.put("invAmount", strAmount);
                    //投资预期年化收益率
                    param.put("invRate", rate);
                    //  投资计息时间
                    param.put("invTime", invTime);
                    //投资红包
                    param.put("redpackage", sumRedPackage.toString());
                    // 封闭截止时间
                    param.put("lockTime", lockTime);
                    //是否是历史数据
                    /*if (isOld) {
                       // BorrowRecoverVO borrowRecover = certBorrowStatusService.selectBorrowRecover(borrowNid);
                        // groupByDate  旧数据上报排序 按月用
                       *//* String groupByDateStr = dateFormatTransformation(borrowRecover.getCreateTime());
                        String groupByDate = groupByDateStr.split("-")[0] + "-" + groupByDateStr.split("-")[1];
                        param.put("groupByDate", groupByDate);*//*
                    }*/
                    json.add(param);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 日期转换
     *
     * @param dateRapay
     * @return
     */
    public String dateFormatTransformation(Date dateRapay, String flg) {
        if (flg.equals("H")) {
            //代表获取有时分秒
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(dateRapay);
            return dateStr;
        }
        if (flg.equals("Y")) {
            //代表只有年与日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(dateRapay);
            return dateStr;
        }
        return null;
    }

    /**
     * 计息日+30天
     *
     * @param timeStr
     * @return
     */
    private String getlockTime(String timeStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = "";
        try {
            Date currdate = format.parse(timeStr);
            Calendar ca = Calendar.getInstance();
            ca.setTime(currdate);
            ca.add(Calendar.DATE, 30);// num为增加的天数，可以改变的
            currdate = ca.getTime();
            enddate = format.format(currdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return enddate;
    }

}
