package com.hyjf.am.trade.service.front.asset.impl;

import com.hyjf.am.response.trade.MyCreditDetailResponse;
import com.hyjf.am.response.trade.RepayPlanResponse;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.WechatMyProjectRequest;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowCredit;
import com.hyjf.am.trade.dao.model.auto.BorrowCreditExample;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.trade.service.front.asset.AssetManageService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.assetmanage.*;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.FormatRateUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.ThreeDESUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version AssetManageServiceImpl, v0.1 2018/6/24 14:50
 */
@Service
public class AssetManageServiceImpl extends BaseServiceImpl implements AssetManageService {
    @Autowired
    SystemConfig systemConfig;

    @Override
    public List<CurrentHoldObligatoryRightListCustomize> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectCurrentHoldObligatoryRightList(params);
    }

    @Override
    public int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectCurrentHoldObligatoryRightListTotal(params);
    }

    @Override
    public int countAlreadyRepayListRecordTotal(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.countAlreadyRepayListRecordTotal(params);
    }

    @Override
    public int selectRepaymentListTotal(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectRepaymentListTotal(params);
    }

    @Override
    public int selectRepaymentListTotalWeb(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectRepaymentListTotalWeb(params);
    }

    @Override
    public int countCreditRecordTotal(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.countCreditRecordTotal(params);
    }

    @Override
    public List<RepayMentListCustomize> selectRepaymentList(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectRepaymentList(params);
    }

    @Override
    public List<TenderCreditDetailCustomize> selectCreditRecordList(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectCreditRecordList(params);
    }

    @Override
    public List<CurrentHoldPlanListCustomize> selectCurrentHoldPlanList(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectCurrentHoldPlanList(params);
    }

    @Override
    public int countCurrentHoldPlanTotal(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.countCurrentHoldPlanTotal(params);
    }

    @Override
    public List<RepayMentPlanListCustomize> selectRepayMentPlanList(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.selectRepayMentPlanList(params);
    }

    @Override
    public int countRepayMentPlanTotal(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return assetManageCustomizeMapper.countRepayMentPlanTotal(params);
    }

    @Override
    public QueryMyProjectVO selectWechatCurrentHoldObligatoryRightList(WechatMyProjectRequest request) {
        QueryMyProjectVO vo=new QueryMyProjectVO();
        Map<String, Object> mapParameter = createWechatParame(request);
        int total = assetManageCustomizeMapper.selectCurrentHoldObligatoryRightListTotal(mapParameter);
        if (total > 0) {
            //update by jijun 按照出借时间排序
            List<CurrentHoldObligatoryRightListCustomize> lst = assetManageCustomizeMapper.selectCurrentHoldObligatoryRightList(mapParameter);
            for (CurrentHoldObligatoryRightListCustomize customize: lst) {
                String capital = customize.getCapital();
                customize.setCapital(CommonUtils.formatAmount(capital));
                String couponType = customize.getCouponType();
                switch (couponType) {
                    case "1":
                        customize.setData("体验金");
                        break;
                    case "2":
                        customize.setData("加息券");
                        break;
                    case "3":
                        customize.setData("代金券");
                        break;
                    default:
                        if("2".equals(customize.getType())){
                            customize.setData("承接债转");
                        } else {
                            customize.setData("");
                        }
                }
                //判断type是否为4（4加息），将couponType设置为4
                if("4".equals(customize.getType())){
                    customize.setCouponType("4");
                    //将"borrowExtraYield": 替换为 "data":
                    customize.setData(customize.getBorrowExtraYield());
                }
                customize.setBorrowApr(FormatRateUtil.formatBorrowApr(customize.getBorrowApr()));
                customize.setBorrowExtraYield(FormatRateUtil.formatBorrowApr(customize.getBorrowExtraYield()));
                customize.setData(FormatRateUtil.formatBorrowApr(customize.getData()));
            }
            vo.getLstProject().addAll(lst);
        }
        boolean isEnd = request.getCurrentPage() * request.getPageSize() >= total;
        vo.setEnd(isEnd);
        return vo;
    }



    @Override
    public QueryMyProjectVO selectWechatRepaymentList(WechatMyProjectRequest request) {
        QueryMyProjectVO vo=new QueryMyProjectVO();
        Map<String, Object> mapParameter = createWechatParame(request);
        int total = assetManageCustomizeMapper.selectRepaymentListTotal(mapParameter);
        if (total > 0) {
            List<WechatRepayMentListCustomize> lst = assetManageCustomizeMapper.selectWechatRepaymentList(mapParameter);
            for (WechatRepayMentListCustomize customize: lst) {
                String account = customize.getAccount().replaceAll(",", "");
                String interest = customize.getInterest().replaceAll(",", "");
                customize.setAccount(CommonUtils.formatAmount(account));
                customize.setInterest(CommonUtils.formatAmount(interest));
                customize.setRecoverTime( GetDate.times10toStrYYYYMMDD(Integer.valueOf(customize.getRecoverTime())));
                //判断invest_type是否为3（3加息），将couponType设置为4
                if("3".equals(customize.getInvestType())){
                    customize.setCouponType("4");
                    customize.setLabel(customize.getBorrowExtraYield());
                }
                customize.setLabel(FormatRateUtil.formatBorrowApr(customize.getLabel()));
                customize.setBorrowApr(FormatRateUtil.formatBorrowApr(customize.getBorrowApr()));
                customize.setBorrowExtraYield(FormatRateUtil.formatBorrowApr(customize.getBorrowExtraYield()));
            }
            vo.getLstProject().addAll(lst);

        }
        boolean isEnd = request.getCurrentPage() * request.getPageSize() >= total;
        vo.setEnd(isEnd);
        return vo;
    }

    @Override
    public QueryMyProjectVO selectWechatCreditRecordList(WechatMyProjectRequest request) {
        QueryMyProjectVO vo=new QueryMyProjectVO();
        Map<String, Object> mapParameter = createWechatParame(request);
        int total=assetManageCustomizeMapper.countCreditRecordTotal(mapParameter);
        if(total>0){
            List<WechatTenderCreditListCustomize> lst= assetManageCustomizeMapper.selectWechatCreditRecordList(mapParameter);
            for (WechatTenderCreditListCustomize customize: lst) {
                String creditCapital = customize.getCreditCapital();
                //update by jijun 20180427
                creditCapital = creditCapital.replaceAll(",", "");
                String assigned = customize.getCreditCapitalAssigned().replaceAll(",", "");
                customize.setCreditCapital(CommonUtils.formatAmount(creditCapital));
                customize.setCreditCapitalAssigned(CommonUtils.formatAmount(assigned));
            }
            vo.getLstProject().addAll(lst);
        }
        boolean isEnd = request.getCurrentPage() * request.getPageSize() >= total;
        vo.setEnd(isEnd);
        return vo;
    }

    @Override
    public QueryMyProjectVO selectWechatCurrentHoldPlanList(WechatMyProjectRequest request) {
        QueryMyProjectVO vo=new QueryMyProjectVO();
        Map<String, Object> mapParameter = createWechatParame(request);
        int total=assetManageCustomizeMapper.countCurrentHoldPlanTotal(mapParameter);
        if(total>0){
            List<CurrentHoldPlanListCustomize> lst=assetManageCustomizeMapper.selectCurrentHoldPlanList(mapParameter);
            vo.getLstProject().addAll(lst);
        }

        boolean isEnd = request.getCurrentPage() * request.getPageSize() >= total;
        vo.setEnd(isEnd);
        return vo;
    }

    @Override
    public QueryMyProjectVO selectWechatRepayMentPlanList(WechatMyProjectRequest request) {
        QueryMyProjectVO vo=new QueryMyProjectVO();
        Map<String, Object> mapParameter = createWechatParame(request);
        int total=assetManageCustomizeMapper.countRepayMentPlanTotal(mapParameter);
        if(total>0){
            List<RepayMentPlanListCustomize> recordList = assetManageCustomizeMapper.selectRepayMentPlanList(mapParameter);
            //计算实际收益
            for (RepayMentPlanListCustomize record : recordList) {
                if (!"2".equals(record.getType()) && record.getRepayAccountYes() != null && record.getAccedeAccount() != null) {
                    BigDecimal receivedTotal = new BigDecimal(record.getRepayAccountYes().replaceAll(",", "").trim());
                    BigDecimal accedeAccount = new BigDecimal(record.getAccedeAccount().replaceAll(",", "").trim());
                    BigDecimal userHjhInvistDetail = receivedTotal.subtract(accedeAccount);
                    int account = userHjhInvistDetail.compareTo(BigDecimal.ZERO);
                    if (account == -1) {
                        record.setRepayInterestYes(BigDecimal.ZERO.toString());
                    } else {
                        record.setRepayInterestYes(userHjhInvistDetail.toString());
                    }
                }
            }

            vo.getLstProject().addAll(recordList);
        }

        boolean isEnd = request.getCurrentPage() * request.getPageSize() >= total;
        vo.setEnd(isEnd);
        return vo;
    }


    @Override
    public List<AppAlreadyRepayListCustomize> selectAppAlreadyRepayList(AssetManageBeanRequest request) {
        return assetManageCustomizeMapper.selectAppAlreadyRepayList(request);
    }

    @Override
    public List<AppTenderCreditRecordListCustomize> searchAppCreditRecordList(AssetManageBeanRequest request) {
        return assetManageCustomizeMapper.searchAppCreditRecordList(request);
    }

    @Override
    public int selectTenderToCreditListCount(AssetManageBeanRequest request) {
        return assetManageCustomizeMapper.selectTenderToCreditListCount(request);
    }

    @Override
    public List<AppMyPlanCustomize> selectAppMyPlanList(AssetManageBeanRequest request) {
        return assetManageCustomizeMapper.selectAppMyPlanList(request);
    }

    @Override
    public int countAppMyPlan(AssetManageBeanRequest request) {
        return assetManageCustomizeMapper.countAppMyPlan(request);
    }

    @Override
    public List<AppAlreadyRepayListCustomize> selectAlreadyRepayList(AssetManageBeanRequest request) {
        Map<String, Object> params=createParame(request);
        return appUserInvestCustomizeMapper.selectAlreadyRepayList(params);
    }

    @Override
    public List<AppTenderToCreditListCustomize> selectTenderToCreditList(Map<String, Object> params) {
        return appTenderCreditCustomizeMapper.selectTenderToCreditList(params);
    }

    private Map<String,Object> createParame(AssetManageBeanRequest request) {
        Map<String,Object> params=new HashMap<String,Object>();

        params.put("userId", request.getUserId());
        params.put("startDate", request.getStartDate());
        params.put("endDate", request.getEndDate());
        params.put("orderByFlag", request.getOrderByFlag());
        params.put("sortBy", request.getSortBy());
        params.put("limitStart", request.getLimitStart());
        params.put("limitEnd", request.getLimitEnd());

        return params;
    }

    private Map<String,Object> createWechatParame(WechatMyProjectRequest request) {
        Map<String,Object> params=new HashMap<String,Object>();

        params.put("userId", request.getUserId());
        params.put("orderByFlag", request.getOrderByFlag());
        params.put("sortBy", request.getSortBy());
        params.put("limitStart", request.getLimitStart());
        params.put("limitEnd", request.getLimitEnd());
        return params;
    }

    /**
     * 获取用户还款计划
     * @param borrowNid
     * @param nid
     * @param type
     * @return
     */
    @Override
    public RepayPlanResponse getRepayPlanInfo(String borrowNid, String nid, String type){
        //type 出借记录类型  0现金出借，1部分债转，2债权承接，3优惠券出借，4 融通宝产品加息
        RepayPlanResponse response = new RepayPlanResponse();
        Borrow borrow = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowNid", borrowNid);
        params.put("nid", nid);
        switch (type) {
            case "0":
                borrow= this.getBorrowByNid(borrowNid);
                if(borrow == null){
                    logger.error("未查询到标的信息，标的编号:{}", borrowNid);
                    break;
                }
                if("endday".equals(borrow.getBorrowStyle())||"end".equals(borrow.getBorrowStyle())){
                    //真实出借不分期还款计划查询
                    response = this.realInvestmentRepaymentPlanNoStages(params);
                } else {
                    //真实出借分期还款计划查询
                    response = this.realInvestmentRepaymentPlanStages(params);
                }
                break;
            case "1":
                borrow = this.getBorrowByNid(borrowNid);
                if(borrow == null){
                    logger.error("未查询到标的信息，标的编号:{}", borrowNid);
                    break;
                }
                if("endday".equals(borrow.getBorrowStyle())||"end".equals(borrow.getBorrowStyle())){
                    //部分债转不分期还款计划查询
                    response = this.assignRepaymentPlanNoStages(params);
                } else {
                    //部分债转分期还款计划查询
                    response = this.assignRepaymentPlanStages(params);
                }

                break;
            case "2":
                //债权承接还款计划查询
                response = this.creditRepaymentPlan(params);
                break;
            case "3":
                //优惠券还款计划查询
                response = this.couponRepaymentPlan(params);
                break;
            case "4":
                borrow=this.getBorrowByNid(borrowNid);
                if(borrow == null){
                    logger.error("未查询到标的信息，标的编号:{}", borrowNid);
                    break;
                }
                if("endday".equals(borrow.getBorrowStyle())||"end".equals(borrow.getBorrowStyle())){
                    //融通宝不分期还款计划查询
                    response = this.rtbRepaymentPlanNoStages(params);
                } else {
                    //融通宝分期还款计划查询
                    response = this.rtbRepaymentPlanStages(params);
                }
                break;
        }
        return response;
    }

    /**
     * 真实出借不分期还款计划查询
     * @param params
     * @return
     */
    private RepayPlanResponse realInvestmentRepaymentPlanNoStages(Map<String, Object> params){
        // 获取当前持有现金出借不分期还款计划列表
        List<CurrentHoldRepayMentPlanListCustomize> list = assetManageCustomizeMapper.realInvestmentRepaymentPlanNoStagesList(params);
        //获取当前持有现金出借不分期还款计划详情
        CurrentHoldRepayMentPlanDetailsCustomize details = assetManageCustomizeMapper.realInvestmentRepaymentPlanNoStagesDetails(params);
        return setResponse(list, details);
    }

    /**
     * 真实出借分期还款计划查询
     * @param params
     * @return
     */
    private RepayPlanResponse realInvestmentRepaymentPlanStages(Map<String, Object> params){
        //获取当前持有现金出借分期还款计划列表
        List<CurrentHoldRepayMentPlanListCustomize> list = assetManageCustomizeMapper.realInvestmentRepaymentPlanStagesList(params);
        //获取当前持有现金出借分期还款计划详情
        CurrentHoldRepayMentPlanDetailsCustomize details = assetManageCustomizeMapper.realInvestmentRepaymentPlanStagesDetails(params);
        return setResponse(list, details);
    }

    /**
     * 部分债转不分期还款计划查询
     * @param params
     * @return
     */
    private RepayPlanResponse assignRepaymentPlanNoStages(Map<String, Object> params){
        //获取当前持有债权转让不分期还款计划列表
        List<CurrentHoldRepayMentPlanListCustomize> list = assetManageCustomizeMapper.assignRepaymentPlanNoStagesList(params);
        //获取当前持有债权转让不分期还款计划详情
        CurrentHoldRepayMentPlanDetailsCustomize details = assetManageCustomizeMapper.assignRepaymentPlanNoStagesDetails(params);
        return setResponse(list, details);
    }

    /**
     * 部分转让分期还款计划查询
     * @param params
     * @return
     */
    private RepayPlanResponse assignRepaymentPlanStages(Map<String, Object> params){
        //获取当前持有部分债转分期还款计划列表
        List<CurrentHoldRepayMentPlanListCustomize> list = assetManageCustomizeMapper.assignRepaymentPlanStagesList(params);
        //获取当前持有债权转让分期还款计划详情
        CurrentHoldRepayMentPlanDetailsCustomize details = assetManageCustomizeMapper.assignRepaymentPlanStagesDetails(params);
        return setResponse(list, details);
    }

    /**
     * 债权承接还款计划查询
     * @param params
     * @return
     */
    private RepayPlanResponse creditRepaymentPlan(Map<String, Object> params){
        //债权承接还款计划列表
        List<CurrentHoldRepayMentPlanListCustomize> list = assetManageCustomizeMapper.creditRepaymentPlanList(params);
        //获取当前持有债权转让还款计划详情
        CurrentHoldRepayMentPlanDetailsCustomize details = assetManageCustomizeMapper.creditRepaymentPlanDetails(params);
        return setResponse(list, details);
    }

    /**
     * 优惠券还款计划查询
     * @param params
     * @return
     */
    private RepayPlanResponse couponRepaymentPlan(Map<String, Object> params){
        //获取当前持有优惠券还款计划列表
        List<CurrentHoldRepayMentPlanListCustomize> list = assetManageCustomizeMapper.couponRepaymentPlanList(params);
        //获取当前持有优惠券还款计划详情
        CurrentHoldRepayMentPlanDetailsCustomize details = assetManageCustomizeMapper.couponRepaymentPlanDetails(params);
        return setResponse(list, details);
    }

    /**
     * 融通宝不分期还款计划查询
     * @param params
     * @return
     */
    private RepayPlanResponse rtbRepaymentPlanNoStages(Map<String, Object> params){
        //获取当前持有融通宝不分期还款计划列表
        List<CurrentHoldRepayMentPlanListCustomize> list=assetManageCustomizeMapper.rtbRepaymentPlanNoStagesList(params);
        //获取当前持有融通宝不分期还款计划详情
        CurrentHoldRepayMentPlanDetailsCustomize details=assetManageCustomizeMapper.rtbRepaymentPlanNoStagesDetails(params);
        return setResponse(list, details);
    }

    /**
     * 融通宝分期还款计划查询
     * @param params
     * @return
     */
    private RepayPlanResponse rtbRepaymentPlanStages(Map<String, Object> params){
        //获取当前持有融通宝分期还款计划列表
        List<CurrentHoldRepayMentPlanListCustomize> list = assetManageCustomizeMapper.rtbRepaymentPlanStagesList(params);
        //获取当前持有融通宝分期还款计划详情
        CurrentHoldRepayMentPlanDetailsCustomize details = assetManageCustomizeMapper.rtbRepaymentPlanStagesDetails(params);
        return setResponse(list, details);
    }

    /**
     * 返回类封装
     * @param list
     * @param details
     * @return
     */
    private RepayPlanResponse setResponse(List<CurrentHoldRepayMentPlanListCustomize> list, CurrentHoldRepayMentPlanDetailsCustomize details){
        RepayPlanResponse response = new RepayPlanResponse();
        //列表
        if(!CollectionUtils.isEmpty(list)){
            List<CurrentHoldRepayMentPlanListCustomizeVO> voList = CommonUtils.convertBeanList(list, CurrentHoldRepayMentPlanListCustomizeVO.class);
            response.setCurrentHoldRepayMentPlanList(voList);
        }
        //明细
        if(details != null){
            CurrentHoldRepayMentPlanDetailsCustomizeVO vo = new CurrentHoldRepayMentPlanDetailsCustomizeVO();
            BeanUtils.copyProperties(details, vo);
            response.setCurrentHoldRepayMentPlanDetails(vo);
        }
        return response;
    }

    /**
     * 获取用户散标转让记录查看详情
     * @param creditNid
     * @return
     */
    @Override
    public MyCreditDetailResponse getMyCreditAssignDetail(String creditNid){
        MyCreditDetailResponse response = new MyCreditDetailResponse();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params.put("creditNid", creditNid);
            int recordTotal = tenderCreditCustomizeMapper.countCreditTenderAssigned(params);
            if (recordTotal > 0) {
                // 查询汇消费列表数据
                long timestamp = System.currentTimeMillis() / 1000;
                params.put("timestamp", String.valueOf(timestamp));
                List<TenderCreditAssignedCustomize> recordList = this.selectCreditAssigned(params);
                List<TenderCreditAssignedCustomizeVO> voList = CommonUtils.convertBeanList(recordList, TenderCreditAssignedCustomizeVO.class);
                response.setRecordList(voList);
            } else {
                response.setRecordList(new ArrayList<TenderCreditAssignedCustomizeVO>());
            }

            // 债转承接记录统计
            List<TenderCreditAssignedStatisticCustomize> statisticList = tenderCreditCustomizeMapper.selectCreditTenderAssignedStatistic(params);
            if (!CollectionUtils.isEmpty(statisticList)) {
                TenderCreditAssignedStatisticCustomizeVO vo = new TenderCreditAssignedStatisticCustomizeVO();
                BeanUtils.copyProperties(statisticList.get(0), vo);
                response.setAssignedStatistic(vo);
            }

            //转让标的信息
            BorrowCredit borrowCredit = this.getBorrowCredit(creditNid);
            BorrowCreditVO borrowCreditVO = new BorrowCreditVO();
            BeanUtils.copyProperties(borrowCredit, borrowCreditVO);
            response.setBorrowCredit(borrowCreditVO);

            return response;
        } catch (Exception e){
            logger.error("获取用户散标转让记录查看详情异常:", e);
            return new MyCreditDetailResponse();
        }
    }

    /**
     * 已承接或购买债转详情查询
     * @param params
     * @return
     */
    private List<TenderCreditAssignedCustomize> selectCreditAssigned(Map<String, Object> params){
        List<TenderCreditAssignedCustomize> list = tenderCreditCustomizeMapper.selectCreditTenderAssigned(params);
        try {
            if (list != null && list.size() > 0) {
                // 对重要参数进行MD5加密
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setUserId(this.getEncrypt(String.valueOf(params.get("timestamp")), String.valueOf(list.get(i).getUserId())));
                    list.get(i).setAssignNidMD5(this.getEncrypt(String.valueOf(params.get("timestamp")), String.valueOf(list.get(i).getAssignNid())));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    /**
     * 数据加密 原代码在TreeDESUtils工具类中
     * @param timestamp
     * @param data
     * @return
     */
    private String getEncrypt(String timestamp, String data){
        String key = systemConfig.getDesKey();
        String timeKey = key + timestamp;
        String encodeData = "";
        try {
            encodeData = ThreeDESUtils.Encrypt3DES(timeKey, data);
            encodeData = URLEncoder.encode(encodeData, "UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return encodeData;
    }

    /**
     * 获取转让标的信息
     * @param creditNid
     * @return
     */
    private BorrowCredit getBorrowCredit(String creditNid) {
        BorrowCredit borrowCredit = null;
        BorrowCreditExample example = new BorrowCreditExample();
        BorrowCreditExample.Criteria cra = example.createCriteria();
        cra.andCreditNidEqualTo(Integer.valueOf(creditNid));
        List<BorrowCredit> list = this.borrowCreditMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            borrowCredit = list.get(0);
        }else {
            borrowCredit = new BorrowCredit();
        }
        return borrowCredit;
    }
}
