/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.bifa.BifaCreditTenderInfoEntityVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaCommonConstants;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaCreditTenderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Date;

/**
 * @author jun
 * @version BifaCreditTenderInfoService, v0.1 2019/1/17 10:39
 */
@Service
public class BifaCreditTenderInfoServiceImpl extends BaseHgDateReportServiceImpl implements BifaCreditTenderInfoService {

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    @Override
    public BorrowCreditVO selectBorrowCreditInfo(String creditNid) {
       return amTradeClient.getBorrowCreditByCreditNid(creditNid);
    }

    @Override
    public BifaCreditTenderInfoEntityVO getBifaBorrowCreditInfoFromMongDB(String sourceProductCode) {
        return csMessageClient.getBifaBorrowCreditInfoFromMongDB(sourceProductCode);
    }

    @Override
    public UserInfoVO getUsersInfoByUserId(Integer creditUserId) {
        return amUserClient.findUsersInfoById(creditUserId);
    }

    @Override
    public BorrowAndInfoVO getBorrowByBorrowNid(String borrowNid) {
        return amTradeClient.selectBorrowByNid(borrowNid);
    }

    @Override
    public boolean convertBifaBorrowCreditInfo(BorrowCreditVO borrowCredit, BorrowAndInfoVO borrow, UserInfoVO creditUserInfo, BifaCreditTenderInfoEntityVO bifaCreditInfoEntity) {
        try {
            bifaCreditInfoEntity.setProduct_reg_type("03");
            bifaCreditInfoEntity.setProduct_name(borrow.getProjectName());
            bifaCreditInfoEntity.setProduct_mark(this.convertProductMark(borrow.getProjectType()));
            bifaCreditInfoEntity.setSource_code(SOURCE_CODE);
            bifaCreditInfoEntity.setSource_product_code(BifaCommonConstants.HZR+borrowCredit.getCreditNid());
            bifaCreditInfoEntity.setTransfer_sex(this.convertSex(creditUserInfo.getSex()));
            bifaCreditInfoEntity.setHold_time(this.getHoldTime(borrow.getBorrowPeriod(),borrow.getBorrowStyle()));
            bifaCreditInfoEntity.setOverplus_time(String.valueOf(borrowCredit.getCreditTerm())+"天");
            bifaCreditInfoEntity.setAmt(borrowCredit.getCreditCapital().toString());
            bifaCreditInfoEntity.setTransfer_rate(this.convertTransferRate(borrowCredit.getBidApr()));
            bifaCreditInfoEntity.setTransfer_fee(this.getBorrowTransferFee(borrowCredit.getCreditNid()));
            bifaCreditInfoEntity.setRemark("无");
            bifaCreditInfoEntity.setSource_product_url(MessageFormat.format(SOURCE_PRODUCT_URL_BORROW_CREDIT,BifaCommonConstants.HZR + borrowCredit.getCreditNid()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId",creditUserInfo.getUserId());
            jsonObject.put("trueName",creditUserInfo.getTruename());
            jsonObject.put("idCard",creditUserInfo.getIdcard());
            bifaCreditInfoEntity.setTransfer_name_idcard_digest(selectUserIdToSHA256(jsonObject).getSha256());
            Date currDate =GetDate.getDate();
            bifaCreditInfoEntity.setCreateTime(currDate);
            bifaCreditInfoEntity.setUpdateTime(currDate);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void insertCreditTenderInfoReportData(BifaCreditTenderInfoEntityVO reportResult) {
        csMessageClient.insertCreditTenderInfoReportData(reportResult);
    }

    /**
     * 获取智投转让信息
     * @param creditNid
     * @return
     */
    @Override
    public HjhDebtCreditVO selectHjhDebtCreditInfo(String creditNid) {
        return amTradeClient.selectHjhDebtCreditByCreditNid(creditNid);
    }

    @Override
    public boolean convertBifaHjhCreditInfo(HjhDebtCreditVO hjhDebtCredit, BorrowAndInfoVO borrow, UserInfoVO creditUserInfo, BifaCreditTenderInfoEntityVO bifaCreditInfoEntity) {
        try {
            bifaCreditInfoEntity.setProduct_reg_type("03");
            bifaCreditInfoEntity.setProduct_name(borrow.getProjectName());
            bifaCreditInfoEntity.setProduct_mark(this.convertProductMark(borrow.getProjectType()));
            bifaCreditInfoEntity.setSource_code(SOURCE_CODE);
            bifaCreditInfoEntity.setSource_product_code(BifaCommonConstants.HZR+hjhDebtCredit.getCreditNid());
            bifaCreditInfoEntity.setTransfer_sex(this.convertSex(creditUserInfo.getSex()));
            bifaCreditInfoEntity.setHold_time(this.getHoldTime(borrow.getBorrowPeriod(),borrow.getBorrowStyle()));
            bifaCreditInfoEntity.setOverplus_time(String.valueOf(hjhDebtCredit.getRemainDays())+"天");
            bifaCreditInfoEntity.setAmt(hjhDebtCredit.getCreditCapital().toString());
            bifaCreditInfoEntity.setTransfer_rate(this.convertTransferRate(hjhDebtCredit.getBorrowApr()));
            bifaCreditInfoEntity.setTransfer_fee(this.getHjhTransferFee(hjhDebtCredit.getCreditNid()));
            bifaCreditInfoEntity.setRemark("无");
            bifaCreditInfoEntity.setSource_product_url(MessageFormat.format(SOURCE_PRODUCT_URL_HJH_CREDIT,hjhDebtCredit.getPlanNid()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId",creditUserInfo.getUserId());
            jsonObject.put("trueName",creditUserInfo.getTruename());
            jsonObject.put("idCard",creditUserInfo.getIdcard());
            bifaCreditInfoEntity.setTransfer_name_idcard_digest(selectUserIdToSHA256(jsonObject).getSha256());
            Date currDate =GetDate.getDate();
            bifaCreditInfoEntity.setCreateTime(currDate);
            bifaCreditInfoEntity.setUpdateTime(currDate);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 智投转让服务费
     * @param creditNid
     * @return
     */
    private String getHjhTransferFee(String creditNid) {
        return amTradeClient.getHjhCreditFeeSumByCreditNid(String.valueOf(creditNid)).toString();
    }

    /**
     * 散标转让服务费
     * @param creditNid
     * @return
     */
    private String getBorrowTransferFee(Integer creditNid) {
        return amTradeClient.getBorrowCreditFeeSumByCreditNid(String.valueOf(creditNid)).toString();
    }

    /**
     * 转让月利率
     * @param bidApr
     * @return
     */
    private String convertTransferRate(BigDecimal bidApr) {
        //12期 百分号转小数
        BigDecimal bd12 = new BigDecimal("1200");
        BigDecimal divide =bidApr.divide(bd12,6,RoundingMode.DOWN);
        return divide.toString();
    }

    private String getHoldTime(Integer borrowPeriod, String borrowStyle) {
        // 是否天标(true:天标, false:月标)
        boolean isDay = false;
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
            isDay = true;
        } else {
            isDay = false;
        }
        if (isDay){
            return borrowPeriod+"天";
        }else {
            return borrowPeriod+"月";
        }
    }

    /**
     * 借款人性别转换
     * @param sex
     * @return
     */
    private String convertSex(Integer sex) {
        switch (sex) {
            case 0:
                return "未知";
            case 1:
                return "男";
            case 2:
                return "女";
            default:
                return "";
        }
    }
}
