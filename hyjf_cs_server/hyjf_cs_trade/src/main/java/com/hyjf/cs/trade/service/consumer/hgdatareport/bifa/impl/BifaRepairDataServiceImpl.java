/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.bifa.*;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowCarinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DigitalUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaRepairDataService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @author jun
 * @version BifaExceptionDataServiceImpl, v0.1 2019/1/18 9:41
 */
@Service
public class BifaRepairDataServiceImpl extends BaseHgDateReportServiceImpl implements BifaRepairDataService {

    public Logger logger = LoggerFactory.getLogger(BifaRepairDataServiceImpl.class);

    private static final String thisMessName = "定时任务处理上报失败数据";
    public static final String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_BIFA + " " + thisMessName + "】";

    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public void reportAgain() {
        //修复散标(放款,最后一期还款完成)异常
        this.bifaBorrowInfoReportAgain();
        //修复智投(新增)异常
        this.bifaHjhPlanInfoReportAgain();
        //修复债转(散标/智投)异常
        this.bifaBifaCreditInfoReportAgain();
        //修复产品状态更新(散标放款后,最后一期还款完成/智投下的标的放款后)异常
        this.bifaBorrowStatusReportAgain();
    }

    private void bifaBorrowStatusReportAgain() {
        List<BifaBorrowStatusEntityVO> list = csMessageClient.getReportFailBorrowStatusFromMongo();
        if (CollectionUtils.isNotEmpty(list)){
            for (BifaBorrowStatusEntityVO entity:list) {
                // -->判断当前产品状态更新对应的散标信息是否上报
                boolean isHjh = this.isHjh(entity.getSource_product_code());
                if (!isHjh) {
                    //非智投下面的标的报送散标信息
                    this.checkBifaBorrowInfoIsReport(entity.getSource_product_code());
                }else {
                    //智投下面的标的先报送新增智投信息
                    this.checkBifaHjhPlanInfoIsReport(entity.getSource_product_code());
                }
                //-->重新上报产品状态更新
                String methodName = "productStatusUpdate";
                BifaBorrowStatusEntityVO result = this.reportData(methodName,entity);
                csMessageClient.updateBorrowStatus(result);
                if ("1".equals(result.getReportStatus()) || "7".equals(result.getReportStatus())) {
                    logger.info(logHeader + "上报产品状态更新异常数据修复成功！！"+JSONObject.toJSONString(result));
                }else if ("9".equals(result.getReportStatus())){
                    logger.error(logHeader + "上报产品状态更新异常数据修复失败！！"+JSONObject.toJSONString(result));
                }
            }
        }
    }

    /**
     * 检查智投信息有无上报
     * @param planNid
     */
    private void checkBifaHjhPlanInfoIsReport(String planNid) {
        //先查看是否存在上报成功的记录
        //1上报成功 7重复上报
        BifaHjhPlanInfoEntityVO bifaHjhPlanInfoEntity=csMessageClient.getReportSuccessHjhPlanInfoFromMongo(planNid);
        if (bifaHjhPlanInfoEntity==null){
            bifaHjhPlanInfoEntity=csMessageClient.getReportFailHjhPlanInfoFromMongo(planNid);
            //当前智投数据没有上报过
            if (bifaHjhPlanInfoEntity == null){
                //等于null说明没有上报成功 拉取智投信息
                HjhPlanVO hjhplan = amTradeClient.getHjhPlan(planNid);
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
                //上报智投数据（实时上报）
                String methodName = "productRegistration";
                BifaHjhPlanInfoEntityVO reportResult = this.reportData(methodName,bifaHjhPlanInfoEntity);
                if ("1".equals(reportResult.getReportStatus()) || "7".equals(reportResult.getReportStatus())) {
                    //上报成功
                    logger.info(logHeader + "上报产品状态更新补偿上报新增智投信息成功！！！" + reportResult);
                }else if ("9".equals(reportResult.getReportStatus())){
                    //上报失败 已经上报过的视为本次上报失败
                    logger.error(logHeader + "上报产品状态更新补偿上报新增智投信息失败！！！"+JSONObject.toJSONString(reportResult));
                }
                this.insertHjhPlanInfoReportData(reportResult);
                logger.info(logHeader + "上报产品状态更新补偿上报新增智投信息保存本地！！！"+JSONObject.toJSONString(reportResult));

            }else {
                String methodName = "productRegistration";
                BifaHjhPlanInfoEntityVO reportResult = this.reportData(methodName,bifaHjhPlanInfoEntity);
                csMessageClient.updateHjhPlanInfo(reportResult);
                if ("1".equals(reportResult.getReportStatus()) || "7".equals(reportResult.getReportStatus())) {
                    logger.info(logHeader + "上报新增智投信息异常数据修复成功！！！"+JSONObject.toJSONString(reportResult));
                }else if ("9".equals(reportResult.getReportStatus())){
                    logger.error(logHeader + "上报新增智投信息异常数据修复失败！！！"+JSONObject.toJSONString(reportResult));
                }
            }
        }

    }

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
            bifaHjhPlanInfoEntity.setRate(this.convertRateHjhPlan(hjhplan.getExpectApr()));
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

    private String convertRateHjhPlan(BigDecimal expectApr) {
        BigDecimal bd12 = new BigDecimal("1200");
        BigDecimal divide =expectApr.divide(bd12,6,RoundingMode.DOWN);
        return divide.toString();
    }

    /**
     * 补偿上报散标信息
     * @param borrowNid
     */
    private void checkBifaBorrowInfoIsReport(String borrowNid) {
        //获取上报成功的记录
        BifaBorrowInfoEntityVO borrowInfo=csMessageClient.getReportSuccessBorrowInfoFromMongo(borrowNid);
        //没有上报成功的数据才执行修复操作
        if (borrowInfo == null){
            borrowInfo = csMessageClient.getReportFailBorrowInfoFromMongo(borrowNid);
            if (borrowInfo==null){
                //没有上报过该散标信息
                //从数据库中拉取散标信息
                // --> 拉数据
                // 散标信息
                BorrowAndInfoVO borrow = this.selectBorrowInfo(borrowNid);
                if (null == borrow) {
                    logger.error(logHeader + "未获取到散标信息！！"+"borrowNid:"+borrowNid);
                    return;
                }
                // 借款人信息
                BifaBorrowUserInfoVO borrowUserInfo = amTradeClient.getBorrowUserInfo(borrow.getBorrowNid(),borrow.getCompanyOrPersonal());
                if(null == borrowUserInfo) {
                    logger.error(logHeader + "未获取到标的借款人信息！！");
                    return;
                }
                //获取标的对应的还款信息
                BorrowRepayVO borrowRepay =amTradeClient.getBorrowRepay(borrowNid);

                //抵押車輛信息
                List<BorrowCarinfoVO> borrowCarsinfo = amTradeClient.getBorrowCarinfoByNid(borrowNid);

                //抵押房產信息
                List<BorrowHousesVO> borrowHouses = amTradeClient.getBorrowHousesByNid(borrowNid);


                BifaBorrowInfoEntityVO bifaBorrowInfoEntity = new BifaBorrowInfoEntityVO();
                // --> 数据变换

                boolean result = this.convertBifaBorrowInfo(borrow, borrowUserInfo,borrowRepay,borrowCarsinfo,borrowHouses,bifaBorrowInfoEntity);
                if (!result){
                    logger.error(logHeader + "新增的散标数据变换失败！！"+JSONObject.toJSONString(bifaBorrowInfoEntity));
                    return;
                }

                // --> 上报数据（实时上报）
                //上报数据失败时 将数据存放到mongoDB
                String methodName = "productRegistration";
                BifaBorrowInfoEntityVO reportResult = this.reportData(methodName,bifaBorrowInfoEntity);
                if ("1".equals(reportResult.getReportStatus()) || "7".equals(reportResult.getReportStatus())){
                    logger.info(logHeader + "上报产品状态更新补偿上报散标信息成功！！！"+JSONObject.toJSONString(reportResult));
                }else if ("9".equals(reportResult.getReportStatus())) {
                    logger.error(logHeader + "上报产品状态更新补偿上报散标信息失败！！！" + JSONObject.toJSONString(reportResult));
                }

                // --> 保存上报数据
                this.insertBorrowInfoReportData(reportResult);
                logger.info(logHeader + "上报产品状态更新补偿上报散标数据保存本地！！"+JSONObject.toJSONString(bifaBorrowInfoEntity));

            }else {
                //mongo中有上报历史数据,则再次上报历史数据
                String methodName = "productRegistration";
                BifaBorrowInfoEntityVO result = this.reportData(methodName, borrowInfo);
                csMessageClient.updateBorrowInfo(result);
                if ("1".equals(result.getReportStatus()) || "7".equals(result.getReportStatus())) {
                    logger.info(logHeader + "上报散标异常数据修复成功！！" + JSONObject.toJSONString(result));
                } else if ("9".equals(result.getReportStatus())){
                    logger.error(logHeader + "上报散标异常数据修复失败！！" + JSONObject.toJSONString(result));
                }
            }
        }
    }

    /**
     * 保存散标上报数据
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

    /**
     *
     * @param borrowStyle
     * @return
     */
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

    /**
     * 获取标的信息
     * @param borrowNid
     * @return
     */
    private BorrowAndInfoVO selectBorrowInfo(String borrowNid) {
        BorrowAndInfoVO borrowVO = amTradeClient.selectBorrowByNid(borrowNid);
        return borrowVO;
    }

    /**
     * 判断是否是汇计划下的标的
     * @param nid
     * @return
     */
    private boolean isHjh(String nid) {
        int count = amTradeClient.countHjhPlan(nid);
        if (count>0){
            return true;
        }else {
            return false;
        }
    }

    private void bifaBifaCreditInfoReportAgain() {
        List<BifaCreditTenderInfoEntityVO> list = csMessageClient.getReportFailCreditInfoFromMongo();
        if (CollectionUtils.isNotEmpty(list)) {
            for (BifaCreditTenderInfoEntityVO entity : list) {
                String methodName = "productRegistration";
                BifaCreditTenderInfoEntityVO withoutFlagObj = this.removeFlag(entity);
                BifaCreditTenderInfoEntityVO result = this.reportData(methodName,withoutFlagObj);
                //将flag补回
                result.setFlag(entity.getFlag());
                csMessageClient.updateCreditInfo(result);
                if ("1".equals(result.getReportStatus()) || "7".equals(result.getReportStatus())) {
                    logger.info(logHeader + "上报债转异常数据修复成功！！"+JSONObject.toJSONString(result));
                }else if ("9".equals(result.getReportStatus())){
                    logger.error(logHeader + "上报债转异常数据修复失败！！"+JSONObject.toJSONString(result));
                }
            }
        }
    }


    private BifaCreditTenderInfoEntityVO removeFlag(BifaCreditTenderInfoEntityVO entity) {
        BifaCreditTenderInfoEntityVO result = entity.cloneObj();
        result.setFlag(null);
        return result;
    }

    private void bifaHjhPlanInfoReportAgain() {
        List<BifaHjhPlanInfoEntityVO> list = csMessageClient.getReportFailHjhPlanInfoFromMongo();
        if (CollectionUtils.isNotEmpty(list)) {
            for (BifaHjhPlanInfoEntityVO entity : list) {
                String methodName = "productRegistration";
                BifaHjhPlanInfoEntityVO result = this.reportData(methodName,entity);
                csMessageClient.updateHjhPlanInfo(result);
                if ("1".equals(result.getReportStatus()) || "7".equals(result.getReportStatus())) {
                    logger.info(logHeader + "上报智投异常数据修复成功！！"+JSONObject.toJSONString(result));
                }else if ("9".equals(result.getReportStatus())){
                    logger.error(logHeader + "上报智投异常数据修复失败！！"+JSONObject.toJSONString(result));
                }
            }
        }
    }

    private void bifaBorrowInfoReportAgain() {
        List<BifaBorrowInfoEntityVO> list = csMessageClient.getReportFailBorrowInfoFromMongo();
        if (CollectionUtils.isNotEmpty(list)){
            for (BifaBorrowInfoEntityVO entity : list) {
                String methodName = "productRegistration";
                BifaBorrowInfoEntityVO result = this.reportData(methodName,entity);
                csMessageClient.updateBorrowInfo(result);
                //1上报成功  7重复上报
                if ("1".equals(result.getReportStatus()) || "7".equals(result.getReportStatus())) {
                    logger.info(logHeader + "上报散标异常数据修复成功！！"+JSONObject.toJSONString(result));
                }else if ("9".equals(result.getReportStatus())){
                    logger.error(logHeader + "上报散标异常数据修复失败！！"+JSONObject.toJSONString(result));
                }
            }
        }

    }
}
