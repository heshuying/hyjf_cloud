package com.hyjf.cs.trade.service.aems.borrow.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.borrow.BorrowCarinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.calculate.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.bean.AemsBorrowDetailRequestBean;
import com.hyjf.cs.trade.bean.AemsBorrowListRequestBean;
import com.hyjf.cs.trade.bean.api.ApiBorrowDetail;
import com.hyjf.cs.trade.bean.api.ApiBorrowReqBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.aems.borrow.AemsBorrowService;
import com.hyjf.cs.trade.service.projectlist.ApiProjectListService;
import com.hyjf.cs.trade.service.svrcheck.CommonSvrChkService;
import com.hyjf.cs.trade.util.ProjectConstant;
import com.hyjf.cs.trade.util.SignUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AEMS标的信息接口
 * @author Zha Daojian
 * @date 2018/12/17 9:41
 * @param
 * @return
 **/
@Service
public class AemsBorrowServiceImpl implements AemsBorrowService {


    @Autowired
    private CommonSvrChkService commonSvrChkService;

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 查询标的列表
     */
    @Override
    public ApiResult getBorrowList(AemsBorrowListRequestBean reqBean) {
        ApiResult result = new ApiResult();
        // 验证必填参数和分页参数
        commonSvrChkService.checkRequired(reqBean);
        commonSvrChkService.checkLimit(reqBean.getLimitStart(), reqBean.getLimitEnd());
        // 标的状态验证
        CheckUtil.check(Validator.isNotNull(reqBean.getBorrowStatus()), MsgEnum.STATUS_CE000001);
        // 标的状态=2投资中 验证
        CheckUtil.check(reqBean.getBorrowStatus().equals("2"), MsgEnum.ERR_OBJECT_VALUE, "borrowStatus");

        // 验签
        CheckUtil.check(SignUtil.AEMSVerifyRequestSign(reqBean, "/aems/borrowList/borrowList"),
                MsgEnum.ERR_SIGN);

        List<ApiProjectListCustomize> list = searchProjectListNew(reqBean);
        result.setData(list);
        return result;
    }

    /**
     * 查询逻辑
     */
    private  List<ApiProjectListCustomize> searchProjectListNew(AemsBorrowListRequestBean bean){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("projectType", "HZT");
        params.put("borrowClass", "");
        params.put("status", bean.getBorrowStatus());

        // add by xiashuqing 20171130 begin
        // 定向标过滤
        params.put("publishInstCode", bean.getInstCode());
        // add by xiashuqing 20171130 end

        params.put("limitStart", bean.getLimitStart());
        params.put("limitEnd", bean.getLimitEnd());
        List<ApiProjectListCustomize> list = amTradeClient.getApiProjectList(params);
        return list;
    }


    /**
     * 查询标的详情
     */
    @Override
    public ApiResult getBorrowDetail(AemsBorrowDetailRequestBean reqBean) {
        ApiResult result = new ApiResult();
        // 公用参数校验
        commonSvrChkService.checkRequired(reqBean);
        //获取项目编号
        String borrowNid = reqBean.getBorrowNid();
        CheckUtil.check(Validator.isNotNull(borrowNid), MsgEnum.STATUS_CE000001);
        // 验签
        CheckUtil.check(SignUtil.AEMSVerifyRequestSign(reqBean, "/aems/borrowDetail/getBorrowDetail"),
                MsgEnum.ERR_SIGN);
        Map<String,Object> map  = new HashMap<>();
        map.put("borrowNid",borrowNid);
        ProjectCustomeDetailVO projectCustomeDetailVO = amTradeClient.searchProjectDetail(map);
        ApiBorrowDetail borrow = CommonUtils.convertBean(projectCustomeDetailVO,ApiBorrowDetail.class);
        //项目不存在
        CheckUtil.check(Validator.isNotNull(borrow), MsgEnum.STATUS_ZT000009);

        borrow.setBorrowStatus(projectCustomeDetailVO.getStatus());
        if (!"1".equals(projectCustomeDetailVO.getIncreaseInterestFlag())){
            borrow.setIncreaseInterestFlag(0);
            borrow.setBorrowExtraYield("");
        }else{
            borrow.setIncreaseInterestFlag(1);
            borrow.setBorrowExtraYield(projectCustomeDetailVO.getBorrowExtraYield());
        }
        // 设置项目加息收益
        BigDecimal borrowExtraYield = new BigDecimal(StringUtils.isBlank(borrow.getBorrowExtraYield()) ?"0":borrow.getBorrowExtraYield());
        if(Validator.isIncrease(borrow.getIncreaseInterestFlag(),borrowExtraYield)){
            String increaseInterest = this.getIncreaseInterest(borrow.getBorrowAccount(),
                    borrow.getBorrowStyle(),Integer.parseInt(borrow.getBorrowPeriod()),borrowExtraYield);
            borrow.setIncreaseInterest(increaseInterest);
        }


        //授信额度如果为0 返回空
        if("0".equals(borrow.getUserCredit())){
            borrow.setUserCredit("");
        }

        //借款人企业信息
        BorrowUserVO borrowUsers = amTradeClient.getBorrowUser(borrowNid);
        //借款人信息
        BorrowManinfoVO borrowManinfo = amTradeClient.getBorrowManinfo(borrowNid);
        //房产抵押信息
        List<BorrowHousesVO> borrowHousesList = amTradeClient.getBorrowHousesByNid(borrowNid);
        //车辆抵押信息
        List<BorrowCarinfoVO> borrowCarinfoList = amTradeClient.getBorrowCarinfoByNid(borrowNid);

        //资产列表
        JSONArray json = new JSONArray();
        //基础信息
        String baseTableData = "";
        //资产信息
        String assetsTableData = "";
        //项目介绍
        String intrTableData = "";
        //信用状况
        String credTableData = "";
        //审核信息
        String reviewTableData = "";
        //借款类型  1.企业    2.个人
        int borrowType = Integer.parseInt(borrow.getComOrPer());

        if (borrowType == 1 && borrowUsers != null) {
            //基础信息
            baseTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 1, borrowType, borrow.getBorrowLevel()));
            borrow.setBaseTableData(baseTableData);
            //信用状况
            credTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 4, borrowType, borrow.getBorrowLevel()));
            borrow.setCredTableData(credTableData);
            //审核信息
            reviewTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowUsers, 5, borrowType, borrow.getBorrowLevel()));
            borrow.setReviewTableData(reviewTableData);
        } else {
            if (borrowManinfo != null) {
                //基础信息
                baseTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 1, borrowType, borrow.getBorrowLevel()));
                borrow.setBaseTableData(baseTableData);
                //信用状况
                credTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 4, borrowType, borrow.getBorrowLevel()));
                borrow.setCredTableData(credTableData);
                //审核信息
                reviewTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrowManinfo, 5, borrowType, borrow.getBorrowLevel()));
                borrow.setReviewTableData(reviewTableData);
            }
        }

        //资产信息
        if (borrowHousesList != null && borrowHousesList.size() > 0) {
            //房产抵押信息
            for (BorrowHousesVO borrowHouses : borrowHousesList) {
                json.add(ProjectConstant.packDetail(borrowHouses, 2, borrowType, borrow.getBorrowLevel()));
            }
        }
        if (borrowCarinfoList != null && borrowCarinfoList.size() > 0) {
            //车辆抵押信息
            for (BorrowCarinfoVO borrowCarinfo : borrowCarinfoList) {
                json.add(ProjectConstant.packDetail(borrowCarinfo, 2, borrowType, borrow.getBorrowLevel()));
            }
        }
        assetsTableData = json.toString();
        borrow.setAssetsTableData(assetsTableData);
        //项目介绍
        intrTableData = JSONObject.toJSONString(ProjectConstant.packDetail(borrow, 3, borrowType, borrow.getBorrowLevel()));
        borrow.setIntrTableData(intrTableData);
        CommonUtils.convertNullToEmptyString(borrow);
        result.setData(borrow);
        return result;
    }

    /**
     *
     * 计算加息预期收益
     */
    private String getIncreaseInterest(String money , String borrowStyle,Integer borrowPeriod,BigDecimal borrowApr) {
        BigDecimal earnings = new BigDecimal("0");
        String version = "2.1.0";
        if (!org.apache.commons.lang.StringUtils.isBlank(money) && Double.parseDouble(money) >= 0) {
            // 计算本金投资预期收益
            switch (borrowStyle) {
                case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“: 预期收益=投资金额*年化收益÷12*月数；
                    earnings = DuePrincipalAndInterestUtils.getMonthInterest(new BigDecimal(money),
                            borrowApr.divide(new BigDecimal("100")), borrowPeriod)
                            .setScale(2, BigDecimal.ROUND_DOWN);
                    break;
                case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“: 预期收益=投资金额*年化收益÷360*天数；
                    earnings = DuePrincipalAndInterestUtils.getDayInterest(new BigDecimal(money),
                            borrowApr.divide(new BigDecimal("100")), borrowPeriod)
                            .setScale(2, BigDecimal.ROUND_DOWN);
                    break;
                case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“: 预期收益=投资金额*年化收益÷12*月数；
                    earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(new BigDecimal(money),
                            borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod)
                            .setScale(2, BigDecimal.ROUND_DOWN);
                    break;
                case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“: 预期收益=投资金额*年化收益÷12*月数；
                    earnings = AverageCapitalPlusInterestUtils.getInterestCount(new BigDecimal(money),
                            borrowApr.divide(new BigDecimal("100")), borrowPeriod)
                            .setScale(2, BigDecimal.ROUND_DOWN);
                    break;
                case CalculatesUtil.STYLE_PRINCIPAL:// 还款方式为”等额本金“: 预期收益=投资金额*年化收益÷12*月数；
                    earnings = AverageCapitalUtils.getInterestCount(new BigDecimal(money),
                            borrowApr.divide(new BigDecimal("100")), borrowPeriod)
                            .setScale(2, BigDecimal.ROUND_DOWN);
                    break;
                default:
                    break;
            }
        }
        return CommonUtils.formatAmount(version, earnings);
    }
}



