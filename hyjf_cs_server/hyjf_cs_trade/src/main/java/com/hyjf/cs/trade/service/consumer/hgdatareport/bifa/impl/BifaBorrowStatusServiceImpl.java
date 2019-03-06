/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.bifa.*;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DigitalUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaBorrowStatusService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jun
 * @version BifaBorrowStatusServiceImpl, v0.1 2019/1/15 18:46
 */
@Service
public class BifaBorrowStatusServiceImpl extends BaseHgDateReportServiceImpl implements BifaBorrowStatusService {

    Logger logger = LoggerFactory.getLogger(BifaBorrowStatusServiceImpl.class);
    private String thisMessName = "产品状态更新信息上报";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_BIFA + " " + thisMessName + "】";

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    @Override
    public BorrowAndInfoVO getBorrowAndInfo(String borrowNid) {
        BorrowAndInfoVO borrowVO = amTradeClient.selectBorrowByNid(borrowNid);
        return borrowVO;
    }

    @Override
    public void checkBorrowInfoIsReported(BorrowAndInfoVO borrowAndInfoVO) {
        //先去查看该散标是否上报
        BifaBorrowInfoEntityVO bifaBorrowInfoEntity=csMessageClient.getBifaBorrowInfoFromMongoDB(borrowAndInfoVO.getBorrowNid());
        if (bifaBorrowInfoEntity==null){
            // 借款人信息
            BifaBorrowUserInfoVO borrowUserInfo = amTradeClient.getBorrowUserInfo(borrowAndInfoVO.getBorrowNid(),borrowAndInfoVO.getCompanyOrPersonal());
            if(null == borrowUserInfo){
                logger.error(logHeader+"获取标的借款人信息失败！！"+JSONObject.toJSONString(borrowAndInfoVO));
                return;
            }
            //获取标的对应的还款信息
            BorrowRepayVO borrowRepay = amTradeClient.getBorrowRepay(borrowAndInfoVO.getBorrowNid());
            //抵押車輛信息
            List<BorrowCarinfoVO> borrowCarsinfo = amTradeClient.getBorrowCarinfoByNid(borrowAndInfoVO.getBorrowNid());
            //抵押房產信息
            List<BorrowHousesVO> borrowHouses = amTradeClient.getBorrowHousesByNid(borrowAndInfoVO.getBorrowNid());
            //
            bifaBorrowInfoEntity = new BifaBorrowInfoEntityVO();
            //-->数据变换
            boolean result = this.convertBifaBorrowInfo(
                    borrowAndInfoVO, borrowUserInfo, borrowRepay, borrowCarsinfo, borrowHouses, bifaBorrowInfoEntity);
            if (!result) {
                logger.error(logHeader + "散标数据变换失败！！" + JSONObject.toJSONString(bifaBorrowInfoEntity));
                return;
            }
            // --> 上报数据（实时上报）
            String methodName = "productRegistration";
            BifaBorrowInfoEntityVO reportResult = this.reportData(methodName, bifaBorrowInfoEntity);
            if ("1".equals(reportResult.getReportStatus()) || "7".equals(reportResult.getReportStatus())) {
                logger.info(logHeader + "上报散标数据成功！！" + JSONObject.toJSONString(reportResult));
            } else if ("9".equals(reportResult.getReportStatus())) {
                logger.error(logHeader + "上报散标数据失败！！" + JSONObject.toJSONString(reportResult));
            }
            // --> 保存上报数据
            this.insertBorrowInfoReportData(reportResult);
            logger.info(logHeader + "上报散标数据保存本地！！" + JSONObject.toJSONString(reportResult));
        }

    }

    /**
     * 校验智投是否上报
     * @param planNid
     */
    @Override
    public void checkHjhPlanInfoIsReported(String planNid) {
        //先去查看该智投是否上报
        BifaHjhPlanInfoEntityVO bifaHjhPlanInfoEntity = csMessageClient.getBifaHjhPlanInfoFromMongoDB(planNid);
        if (bifaHjhPlanInfoEntity==null){
            HjhPlanVO hjhplan = this.amTradeClient.getHjhPlan(planNid);
            if (null == hjhplan) {
                logger.error(logHeader + "未获取到新增的智投信息！！"+"planNid:"+planNid);
                return;
            }
            // --> 数据变换
            bifaHjhPlanInfoEntity = new BifaHjhPlanInfoEntityVO();
            boolean result = this.convertBifaHjhPlanInfo(hjhplan,bifaHjhPlanInfoEntity);
            if (!result){
                logger.error(logHeader + "新增的智投数据变换失败！！"+JSONObject.toJSONString(bifaHjhPlanInfoEntity));
                return;
            }
            // --> 上报数据（实时上报）
            //上报数据失败时 将数据存放到mongoDB
            String methodName = "productRegistration";
            BifaHjhPlanInfoEntityVO reportResult = this.reportData(methodName,bifaHjhPlanInfoEntity);
            if ("1".equals(reportResult.getReportStatus()) || "7".equals(reportResult.getReportStatus())){
                logger.info(logHeader + "上报新增的智投数据成功。" + JSONObject.toJSONString(reportResult));
            } else if ("9".equals(reportResult.getReportStatus())) {
                logger.error(logHeader + "上报新增的智投数据失败！！"+JSONObject.toJSONString(reportResult));
            }
            // --> 保存上报数据
            this.insertHjhPlanInfoReportData(reportResult);
            logger.info(logHeader + "新增的智投数据保存本地成功！！"+JSONObject.toJSONString(reportResult));
        }
    }

    @Override
    public BifaBorrowStatusEntityVO getBifaBorrowStatusFromMongoDB(BorrowAndInfoVO borrowAndInfoVO) {
        //这里只校验散标放款和最后一笔还款结束是否上报，不校验智投下的标的放款和最后一笔还款结束是否上报
        return csMessageClient.getBifaBorrowStatusFromMongoDB(borrowAndInfoVO.getBorrowNid(),borrowAndInfoVO.getStatus());
    }

    @Override
    public List<BorrowTenderVO> selectBorrowTenders(String borrowNid) {
        return amTradeClient.getBorrowTenderListByBorrowNid(borrowNid);
    }

    @Override
    public boolean convertBorrowStatus(BorrowAndInfoVO borrowAndInfoVO, List<BorrowTenderVO> borrowTenders, BifaBorrowStatusEntityVO bifaBorrowStatusEntity) {
        try {
            if (4==borrowAndInfoVO.getStatus()){
                //标的放款后
                bifaBorrowStatusEntity.setSource_code(SOURCE_CODE);
                //智投下的散标报送智投编号
                if (StringUtils.isNotEmpty(borrowAndInfoVO.getPlanNid())){
                    bifaBorrowStatusEntity.setSource_product_code(borrowAndInfoVO.getPlanNid());
                }else {
                    bifaBorrowStatusEntity.setSource_product_code(borrowAndInfoVO.getBorrowNid());
                }
                bifaBorrowStatusEntity.setProduct_status("1");
                bifaBorrowStatusEntity.setProduct_status_desc("满标");
                String recoverLastTime = "";
                if (borrowAndInfoVO.getRecoverLastTime() != null){
                    recoverLastTime = GetDate.times10toStrYYYYMMDD(borrowAndInfoVO.getRecoverLastTime());
                }
                bifaBorrowStatusEntity.setBegindate(recoverLastTime);

                //最后一期还款后 包含分期和不分期两种业务场景
                String lastRepayTime=this.getLastRepayTime(borrowAndInfoVO);

                bifaBorrowStatusEntity.setEnddate(lastRepayTime);
                bifaBorrowStatusEntity.setProduct_date(GetDate.formatDate());

                List<InvestorBeanVO> list = new ArrayList<InvestorBeanVO>();
                for (BorrowTenderVO borrowTender : borrowTenders) {
                    UserInfoVO ui=amUserClient.findUsersInfoById(borrowTender.getUserId());
                    InvestorBeanVO entity = new InvestorBeanVO();
                    entity.setInvest_amt(borrowTender.getAccount().toString());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userId",ui.getUserId());
                    jsonObject.put("trueName",ui.getTruename());
                    jsonObject.put("idCard",ui.getIdcard());
                    entity.setInvestor_name_idcard_digest(selectUserIdToSHA256(jsonObject).getSha256());
                    list.add(entity);
                }
                bifaBorrowStatusEntity.setInvestorlist(list);

            }else if (5==borrowAndInfoVO.getStatus()){
                //最后一期还款后 包含分期和不分期两种业务场景
                bifaBorrowStatusEntity.setSource_code(SOURCE_CODE);
                //智投下的散标报送智投编号
                if (StringUtils.isNotEmpty(borrowAndInfoVO.getPlanNid())){
                    bifaBorrowStatusEntity.setSource_product_code(borrowAndInfoVO.getPlanNid());
                }else {
                    bifaBorrowStatusEntity.setSource_product_code(borrowAndInfoVO.getBorrowNid());
                }

                bifaBorrowStatusEntity.setProduct_status("3");
                bifaBorrowStatusEntity.setProduct_status_desc("还款结束");
                String recoverLastTime = "";
                if (borrowAndInfoVO.getRecoverLastTime() != null){
                    recoverLastTime = GetDate.times10toStrYYYYMMDD(borrowAndInfoVO.getRecoverLastTime());
                }
                bifaBorrowStatusEntity.setBegindate(recoverLastTime);
                String lastRepayTime=this.getLastRepayTime(borrowAndInfoVO);
                bifaBorrowStatusEntity.setEnddate(lastRepayTime);
                bifaBorrowStatusEntity.setProduct_date(GetDate.formatDate());

                List<InvestorBeanVO> list = new ArrayList<InvestorBeanVO>();
                for (BorrowTenderVO borrowTender : borrowTenders) {
                    UserInfoVO ui=amUserClient.findUsersInfoById(borrowTender.getUserId());
                    InvestorBeanVO entity = new InvestorBeanVO();
                    entity.setInvest_amt(borrowTender.getAccount().toString());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userId",ui.getUserId());
                    jsonObject.put("trueName",ui.getTruename());
                    jsonObject.put("idCard",ui.getIdcard());
                    entity.setInvestor_name_idcard_digest(selectUserIdToSHA256(jsonObject).getSha256());
                    list.add(entity);
                }
                bifaBorrowStatusEntity.setInvestorlist(list);
            }


            Date currDate =GetDate.getDate();
            bifaBorrowStatusEntity.setCreateTime(currDate);
            bifaBorrowStatusEntity.setUpdateTime(currDate);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void insertBorrowStatusReportData(BifaBorrowStatusEntityVO reportResult) {
        csMessageClient.insertBorrowStatusReportData(reportResult);
    }

    private String getLastRepayTime(BorrowAndInfoVO borrow) {
        boolean isMonth = false;
        if (CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle())
                || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle())) {
            isMonth = true;
        } else if (CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle())) {
            isMonth = false;
        }
        Integer lastRepayTime = null;
        if (isMonth){
            //分期
            List<BorrowRepayPlanVO> borrowRepayPlans = amTradeClient.getBorrowRepayPlansByBorrowNid(borrow.getBorrowNid());
            if (CollectionUtils.isNotEmpty(borrowRepayPlans)){
                lastRepayTime=borrowRepayPlans.get(borrowRepayPlans.size()-1).getRepayTime();
            }
        }else {
            //不分期
            BorrowRepayVO borrowRepay = amTradeClient.getBorrowRepay(borrow.getBorrowNid());
            lastRepayTime = borrowRepay.getRepayTime();
        }
        String lastRepayTimeStr = GetDate.times10toStrYYYYMMDD(lastRepayTime);
        return lastRepayTimeStr;
    }

    /**
     * 智投上报数据保存本地mongo
     * @param reportResult
     */
    private void insertHjhPlanInfoReportData(BifaHjhPlanInfoEntityVO reportResult) {
        csMessageClient.insertHjhPlanInfoReportData(reportResult);
    }

    /**
     * 智投数据转换
     * @param hjhplan
     * @param bifaHjhPlanInfoEntity
     * @return
     */
    private boolean convertBifaHjhPlanInfo(HjhPlanVO hjhplan, BifaHjhPlanInfoEntityVO bifaHjhPlanInfoEntity) {
        try {
            bifaHjhPlanInfoEntity.setProduct_reg_type("02");
            bifaHjhPlanInfoEntity.setProduct_name(hjhplan.getPlanName());
            bifaHjhPlanInfoEntity.setProduct_mark("智投服务");
            bifaHjhPlanInfoEntity.setSource_code(SOURCE_CODE);
            bifaHjhPlanInfoEntity.setSource_product_code(hjhplan.getPlanNid());
            bifaHjhPlanInfoEntity.setPlan_raise_amount("1000000");
            bifaHjhPlanInfoEntity.setRate(this.convertRateHjhPlan(hjhplan.getBorrowStyle(),hjhplan.getExpectApr()));
            bifaHjhPlanInfoEntity.setTerm_type(this.convertTermType(hjhplan.getBorrowStyle()));
            bifaHjhPlanInfoEntity.setTerm(String.valueOf(hjhplan.getLockPeriod()));
            bifaHjhPlanInfoEntity.setIsshow("1");
            bifaHjhPlanInfoEntity.setRemark("无");
            bifaHjhPlanInfoEntity.setAmount_limmts(hjhplan.getMinInvestment().toString());
            bifaHjhPlanInfoEntity.setAmount_limmtl(DigitalUtils.min(new BigDecimal(RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE)), hjhplan.getMaxInvestment()).toString());
            bifaHjhPlanInfoEntity.setRed_rate("超出参考回报部分作为服务费用");
            bifaHjhPlanInfoEntity.setSource_product_url(MessageFormat.format(SOURCE_PRODUCT_URL_HJHPLAN,hjhplan.getPlanNid()));
            Date currDate =GetDate.getDate();
            bifaHjhPlanInfoEntity.setCreateTime(currDate);
            bifaHjhPlanInfoEntity.setUpdateTime(currDate);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 天标和月标的利率分开处理
     * @param borrowStyle
     * @param expectApr
     * @return
     */
    private String convertRateHjhPlan(String borrowStyle, BigDecimal expectApr) {
        if (CalculatesUtil.STYLE_ENDDAY.equals(borrowStyle)) {
            //天智投利率＝年利率*30/365
            BigDecimal bd12 = new BigDecimal("36500");
            BigDecimal divide =expectApr.multiply(new BigDecimal(30)).divide(bd12,6,RoundingMode.DOWN);
            return divide.toString();
        } else {
            //月智投利率＝年利率/12
            BigDecimal bd12 = new BigDecimal("1200");
            BigDecimal divide =expectApr.divide(bd12,6,RoundingMode.DOWN);
            return divide.toString();
        }
    }

    /**
     * 散标上报数据保存本地mongo
     * @param reportResult
     */
    private void insertBorrowInfoReportData(BifaBorrowInfoEntityVO reportResult) {
        csMessageClient.insertBorrowInfoReportData(reportResult);
    }

    /**
     * 散标数据转换
     * @param borrowAndInfoVO
     * @param borrowUserInfo
     * @param borrowRepay
     * @param borrowCarsinfo
     * @param borrowHouses
     * @param bifaBorrowInfoEntity
     * @return
     */
    private boolean convertBifaBorrowInfo(BorrowAndInfoVO borrowAndInfoVO, BifaBorrowUserInfoVO borrowUserInfo, BorrowRepayVO borrowRepay, List<BorrowCarinfoVO> borrowCarsinfo, List<BorrowHousesVO> borrowHouses, BifaBorrowInfoEntityVO bifaBorrowInfoEntity) {
        try {
            bifaBorrowInfoEntity.setProduct_reg_type("01");
            bifaBorrowInfoEntity.setProduct_name(borrowAndInfoVO.getProjectName());
            bifaBorrowInfoEntity.setProduct_mark(this.convertProductMark(borrowAndInfoVO.getProjectType()));
            bifaBorrowInfoEntity.setSource_code(SOURCE_CODE);
            bifaBorrowInfoEntity.setSource_product_code(borrowAndInfoVO.getBorrowNid());
            bifaBorrowInfoEntity.setBorrow_sex(borrowUserInfo.getSex());
            bifaBorrowInfoEntity.setAmount(String.valueOf(borrowAndInfoVO.getAccount()));
            bifaBorrowInfoEntity.setRate(this.convertRateBorrow(borrowAndInfoVO.getBorrowApr()));
            bifaBorrowInfoEntity.setTerm_type(this.convertTermType(borrowAndInfoVO.getBorrowStyle()));
            bifaBorrowInfoEntity.setTerm(String.valueOf(borrowAndInfoVO.getBorrowPeriod()));
            bifaBorrowInfoEntity.setPay_type(this.convertPayType(borrowAndInfoVO.getBorrowStyle()));
            //服务费=放款服务费+还款服务费
            bifaBorrowInfoEntity.setService_cost(this.getServiceCost(borrowAndInfoVO.getBorrowNid()));
            bifaBorrowInfoEntity.setRisk_margin("0");
            bifaBorrowInfoEntity.setLoan_type(this.convertLoanType(borrowAndInfoVO.getAssetAttributes()));
            bifaBorrowInfoEntity.setLoan_credit_rating(borrowAndInfoVO.getBorrowLevel());
            bifaBorrowInfoEntity.setSecurity_info("");//留空不报送
            bifaBorrowInfoEntity.setCollateral_desc(this.convertCollateralDesc(borrowCarsinfo,borrowHouses));
            bifaBorrowInfoEntity.setCollateral_info(this.convertCollateralInfo(borrowCarsinfo,borrowHouses));
            bifaBorrowInfoEntity.setOverdue_limmit("到期还款日当天24点未提交还款的标的");
            bifaBorrowInfoEntity.setBad_debt_limmit("3月");
            bifaBorrowInfoEntity.setAmount_limmts(String.valueOf(borrowAndInfoVO.getTenderAccountMin()));
            bifaBorrowInfoEntity.setAmount_limmtl(String.valueOf(borrowAndInfoVO.getTenderAccountMax()));
            bifaBorrowInfoEntity.setAllow_transfer("0");
            bifaBorrowInfoEntity.setClose_limmit("0");
            bifaBorrowInfoEntity.setSecurity_type(this.convertSecurityType(borrowAndInfoVO.getAssetAttributes()));
            bifaBorrowInfoEntity.setProject_source("合作机构推荐");
            bifaBorrowInfoEntity.setSource_product_url(MessageFormat.format(SOURCE_PRODUCT_URL_BORROW,borrowAndInfoVO.getBorrowNid()));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId",null);
            jsonObject.put("trueName",borrowUserInfo.getTrueName());
            jsonObject.put("idCard",borrowUserInfo.getIdCard());
            bifaBorrowInfoEntity.setBorrow_name_idcard_digest(selectUserIdToSHA256(jsonObject).getSha256());
            Date currDate =GetDate.getDate();
            bifaBorrowInfoEntity.setCreateTime(currDate);
            bifaBorrowInfoEntity.setUpdateTime(currDate);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 抵押标:报送抵押
     * 质押标:报送质押
     * 无抵押内容/质押内容 或者其他值填保证
     * @return
     */
    private String convertSecurityType(Integer assetAttributes) {
        if (assetAttributes ==null || assetAttributes==0 || assetAttributes==1){
            return "抵押";
        } else if (assetAttributes==2){
            return "质押";
        } else {
            return "保证";
        }
    }


    private String convertCollateralInfo(List<BorrowCarinfoVO> borrowCarsinfo, List<BorrowHousesVO> borrowHouses) {
        BigDecimal carsTotalPrice = new BigDecimal("0");
        BigDecimal housesTotalPrice = new BigDecimal("0");
        StringBuffer result = new StringBuffer("");
        if (CollectionUtils.isNotEmpty(borrowCarsinfo)){
            for (BorrowCarinfoVO carinfo : borrowCarsinfo) {
                carsTotalPrice = carsTotalPrice.add(carinfo.getToprice());
            }
        }

        if (CollectionUtils.isNotEmpty(borrowHouses)){
            for (BorrowHousesVO house:borrowHouses) {
                housesTotalPrice = housesTotalPrice.add(new BigDecimal(house.getHousesToprice()));
            }
        }

        if (!carsTotalPrice.equals(BigDecimal.ZERO)){
            result.append("车辆评估价(元):"+carsTotalPrice.toString());
        }
        if (!housesTotalPrice.equals(BigDecimal.ZERO)){
            if (result.length()==0){
                result.append("房产抵押价值(元):"+housesTotalPrice.toString());
            }else {
                result.append(",房产抵押价值(元):"+housesTotalPrice.toString());
            }
        }
        return result.toString();
    }

    /**
     * 抵押/质押物描述转换
     * @param borrowCarsinfo
     * @param borrowHouses
     * @return
     */
    private String convertCollateralDesc(List<BorrowCarinfoVO> borrowCarsinfo, List<BorrowHousesVO> borrowHouses) {
        StringBuffer sb = new StringBuffer();
        //车辆
        if (CollectionUtils.isNotEmpty(borrowCarsinfo)){
            for (int i = 0; i < borrowCarsinfo.size() ; i++) {
                //最后一个
                if (i==borrowCarsinfo.size()-1){
                    sb.append(borrowCarsinfo.get(i).getBrand()+borrowCarsinfo.get(i).getModel()+"车一辆");
                }else {
                    //非最后一个
                    sb.append(borrowCarsinfo.get(i).getBrand()+borrowCarsinfo.get(i).getModel()+"车一辆, ");
                }

            }
        }

        //房屋
        if (CollectionUtils.isNotEmpty(borrowHouses)){
            if (sb.length()>0){
                sb.append(", ");
            }
            for (int i = 0; i <borrowHouses.size() ; i++) {
                //最后一个
                if (i==borrowHouses.size()-1){
                    sb.append(borrowHouses.get(i).getHousesArea()+"㎡"+this.convertHousesType(borrowHouses.get(i).getHousesType())+"房产一处");
                }else {
                    //非最后一个
                    sb.append(borrowHouses.get(i).getHousesArea()+"㎡"+this.convertHousesType(borrowHouses.get(i).getHousesType())+"房产一处, ");
                }
            }

        }

        return sb.toString();

    }

    /**
     * 房产抵押转换
     *
     * @param housesType
     * @return
     */
    private String convertHousesType(String housesType) {
        switch (housesType) {
            case "1":
                return "住宅";
            case "2":
                return "商业用房";
            case "3":
                return "商业用地";
            case "4":
                return "工业用房";
            case "5":
                return "工业用地";
            default:
                return "";
        }

    }

    /**
     * 担保方式转换
     * @param assetAttributes
     * @return
     */
    private String convertLoanType(Integer assetAttributes) {
        String result = "";
        if (assetAttributes ==null || assetAttributes==0 || assetAttributes==1 || assetAttributes==2){
            result = "抵质押";
        }else if (assetAttributes ==3){
            result = "信用";
        }
        return result;
    }

    private String getServiceCost(String borrowNid) {
        return amTradeClient.selectServiceCostSum(borrowNid);
    }

    private String convertPayType(String borrowStyle) {
        switch (borrowStyle) {
            case CalculatesUtil.STYLE_END:
                return "4";
            case CalculatesUtil.STYLE_ENDDAY:
                return "4";
            case CalculatesUtil.STYLE_MONTH:
                return "1";
            case CalculatesUtil.STYLE_PRINCIPAL:
                return "2";
            case CalculatesUtil.STYLE_ENDMONTH:
                return "3";
            default:
                return "5";
        }
    }

    /**
     * 期限类型(还款方式)转换
     * @param borrowStyle
     * @return
     */
    private String convertTermType(String borrowStyle) {
        if (CalculatesUtil.STYLE_ENDDAY.equals(borrowStyle)) {
            return "天";
        } else {
            return "月";
        }
    }

    /**
     * 月利率
     * @param borrowApr
     * @return
     */
    private String convertRateBorrow(BigDecimal borrowApr) {
        //12期 百分号转小数
        BigDecimal bd12 = new BigDecimal("1200");
        BigDecimal divide =borrowApr.divide(bd12,6,RoundingMode.DOWN);
        return divide.toString();
    }


}
