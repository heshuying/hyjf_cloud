/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.aems.repayplan;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.AemsBorrowRepayPlanCustomizeVO;
import com.hyjf.am.vo.trade.ProjectBeanVO;
import com.hyjf.am.vo.trade.ProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.common.constants.AemsErrorCodeConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.AemsBorrowRepayPlanRequestBean;
import com.hyjf.cs.trade.bean.AemsBorrowRepayPlanResultBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.aems.repayplan.AemsBorrowRepayPlanService;
import com.hyjf.cs.trade.service.repay.RepayService;
import com.hyjf.cs.trade.service.svrcheck.CommonSvrChkService;
import com.hyjf.cs.trade.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * AEMS系统:还款计划查询
 *
 * @author liuyang
 * @version AemsBorrowRepayPlanController, v0.1 2018/12/12 10:33
 */
@Api(value = "api端AEMS系统还款计划查询接口", tags = "api端-AEMS系统还款计划查询")
@RestController
@RequestMapping("/hyjf-api/aems/repayplan")
public class AemsBorrowRepayPlanController extends BaseTradeController {

    @Autowired
    private CommonSvrChkService commonSvrChkService;

    @Autowired
    private AemsBorrowRepayPlanService aemsBorrowRepayPlanService;

    @Autowired
    private RepayService repayService;
    /**
     * 查询还款计划
     *
     * @param requestBean
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "AEMS系统还款计划查询" , notes = "AEMS系统还款计划查询")
    @RequestMapping("/getRepayPlan")
    public AemsBorrowRepayPlanResultBean getRepayPlanInfo(@RequestBody AemsBorrowRepayPlanRequestBean requestBean) {
        logger.info("查询还款计划, 请求参数requestBean is :{}", JSONObject.toJSONString(requestBean));
        AemsBorrowRepayPlanResultBean resultBean = new AemsBorrowRepayPlanResultBean();
        try {
            // 机构编号
            String instCode = requestBean.getInstCode();
            // 机构编号
            if (Validator.isNull(instCode)) {
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_ZC000002);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_ZC000002);
                resultBean.setStatusDesc("机构编号不能为空");
                return resultBean;
            }
            // 查询类型
            String repayType = requestBean.getRepayType();
            if (Validator.isNull(repayType)) {
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000004);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000004);
                resultBean.setStatusDesc("查询类型不能为空");
                return resultBean;
            }
            // 分页验证
            commonSvrChkService.checkLimit(requestBean.getLimitStart(), requestBean.getLimitEnd());

            // 验签
            if (!SignUtil.AEMSVerifyRequestSign(requestBean, "/aems/repayplan/getRepayPlan")) {
                logger.info("----验签失败----");
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_CE000002);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000002);
                resultBean.setStatusDesc("验签失败！");
                return resultBean;
            }

            // 根据机构获取标的还款
            Integer totalCounts = this.aemsBorrowRepayPlanService.selectBorrowRepayPlanCountsByInstCode(requestBean);
            logger.info("instCode"+instCode+"----repayStatus"+requestBean.getRepayType()+"----asset_id"+requestBean.getProductId());
            if (totalCounts == 0) {
                logger.info("该资产不存在,或标的未放款/未还款");
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000003);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000003);
                resultBean.setStatusDesc("该资产不存在,或标的未放款/未还款");
                return resultBean;
            }
            // 获取标的列表
            List<AemsBorrowRepayPlanCustomizeVO> aemsBorrowRepayPlanCustomizeList = this.aemsBorrowRepayPlanService.selectBorrowRepayPlanList(requestBean);
            if (aemsBorrowRepayPlanCustomizeList != null && aemsBorrowRepayPlanCustomizeList.size() > 0) {
                resultBean.setDetailList(aemsBorrowRepayPlanCustomizeList);
                resultBean.setStatus(AemsErrorCodeConstant.SUCCESS);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
                resultBean.setStatusDesc("查询成功");
                resultBean.setTotalCounts(totalCounts);
                return resultBean;
            } else {
                logger.info("没有查询到对应借款标的");
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000001);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000001);
                resultBean.setStatusDesc("没有查询到对应借款标的");
            }
        } catch (Exception e) {
            logger.error("查询还款失败， 原因: ", e);
        }
        return resultBean;
    }
    /**
     * 查询还款详情查询
     *
     * @param requestBean
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "AEMS系统还款计划详情查询" , notes = "AEMS系统还款计划详情查询")
    @RequestMapping("/getRepayPlanDetail")
    public AemsBorrowRepayPlanResultBean getRepayPlanInfoDetail(@RequestBody AemsBorrowRepayPlanRequestBean requestBean) {
        logger.info("查询还款详情查询, requestBean is :{}", JSONObject.toJSONString(requestBean));
        AemsBorrowRepayPlanResultBean resultBean = new AemsBorrowRepayPlanResultBean();
        try {
            // 机构编号
            String instCode = requestBean.getInstCode();
            // 机构编号
            if (Validator.isNull(instCode)) {
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_ZC000002);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_ZC000002);
                resultBean.setStatusDesc("机构编号不能为空");
                return resultBean;
            }
            // 资产编号
            String productIdVa = requestBean.getProductId();
            if (Validator.isNull(productIdVa)) {
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000006);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000006);
                resultBean.setStatusDesc("资产编号不能为空");
                return resultBean;
            }
            // 查询类型
            String repayType = requestBean.getRepayType();
            if (Validator.isNull(repayType)) {
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000004);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000004);
                resultBean.setStatusDesc("查询类型不能为空");
                return resultBean;
            }
            // 分页验证
            commonSvrChkService.checkLimit(requestBean.getLimitStart(), requestBean.getLimitEnd());

            // 验签
            if (!SignUtil.AEMSVerifyRequestSign(requestBean, "/aems/repayplan/getRepayPlanDetail")) {
                logger.info("----验签失败----");
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_CE000002);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000002);
                resultBean.setStatusDesc("验签失败！");
                return resultBean;
            }
            // 查询分期项目
            requestBean.setIsMonth("1");
            // 根据机构获取标的还款
            Integer totalCounts = this.aemsBorrowRepayPlanService.selectBorrowRepayPlanCountsByInstCode(requestBean);
            if (totalCounts == 0) {
                logger.info("该资产不存在,或标的未放款/未还款");
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000003);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000003);
                resultBean.setStatusDesc("该资产不存在,或标的未放款/未还款");
                return resultBean;
            }
            // 获取标的列表
            logger.info("获取标的列表...");
            List<AemsBorrowRepayPlanCustomizeVO> aemsBorrowRepayPlanCustomizeList = this.aemsBorrowRepayPlanService.selectBorrowRepayPlanList(requestBean);
            List<AemsBorrowRepayPlanCustomizeVO> detailList = new ArrayList<AemsBorrowRepayPlanCustomizeVO>();
            if (aemsBorrowRepayPlanCustomizeList != null && aemsBorrowRepayPlanCustomizeList.size() > 0) {
                for (int i = 0; i < aemsBorrowRepayPlanCustomizeList.size(); i++) {
                    AemsBorrowRepayPlanCustomizeVO bean = aemsBorrowRepayPlanCustomizeList.get(i);
                    String borrowNid = bean.getBorrowNid();
                    RightBorrowVO borrow = this.aemsBorrowRepayPlanService.selectBorrowByBorrowNid(borrowNid);
                    if (borrow == null) {
                        logger.info("根据标的编号查询借款信息失败,借款编号:[" + borrowNid + "].");
                        continue;
                    }

                    BorrowInfoVO borrowInfoVO = this.aemsBorrowRepayPlanService.getBorrowInfoByNid(borrowNid);
                    if (borrowInfoVO == null) {
                        logger.info("根据标的编号查询借款详情信息失败,借款编号:[" + borrowNid + "].");
                        continue;
                    }
                    // 根据标的编号查询资产推送表
                    HjhPlanAssetVO hjhPlanAsset = this.aemsBorrowRepayPlanService.selectHjhPlanAssetByBorrowNid(borrowNid);
                    if (hjhPlanAsset == null || StringUtils.isEmpty(hjhPlanAsset.getAssetId())){
                        logger.error("根据标的编号查询资产推送信息失败,借款编号:[" + borrowNid + "].");
                        resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000005);
                        resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000005);
                        resultBean.setStatusDesc("标的号对应的资产推送信息不存在,标的编号:["+borrowNid+"].");
                        return resultBean;
                    }
                    // 资产编号
                    String productId = hjhPlanAsset.getAssetId();

                    // 还款方式
                    String borrowStyle = borrow.getBorrowStyle();
                    // 根据还款方式判断是否分期
                    boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                            || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                    // 如果不是分期
                    if (!isMonth) {
                        continue;
                    }
                    //查询各期提前还款减息
                    ProjectBeanVO form =new  ProjectBeanVO();
                    form.setBorrowNid(borrowNid);
                    form.setUserId(borrow.getUserId()+"");
                    //查询各期提前还款减息
                    ProjectBeanVO repayProject=null;
                    List<ProjectRepayVO> projectRepayBean=null;
                    // 根据标的编号查询还款计划
                    List<BorrowRepayPlanVO> repayPlanList = this.aemsBorrowRepayPlanService.selectBorrowRepayPlanByBorrowNid(borrowNid);
                    if (repayPlanList != null && repayPlanList.size() > 0) {
                        //判断是否逾期 逾期或延期时返回false 逾期或延期时不计算提前还款提前还款减息
                        Boolean flag=aemsBorrowRepayPlanService.getFlag(borrow);
                        if(flag){
                            //true
                            repayProject = repayService.getRepayProjectDetail(form);
                            if(null !=repayProject){
                                projectRepayBean = repayProject.getUserRepayList();
                            }
                        }
                        int ii=0;
                        // 循环还款计划
                        for (BorrowRepayPlanVO borrowRepayPlan : repayPlanList) {
                            AemsBorrowRepayPlanCustomizeVO result = new AemsBorrowRepayPlanCustomizeVO();
                            //资产机构编号
                            result.setInstCode(instCode);
                            // 资产编号
                            result.setProductId(productId);
                            // 标的号
                            result.setBorrowNid(borrowRepayPlan.getBorrowNid());
                            // 期数
                            result.setRepayPeriod(String.valueOf(borrowRepayPlan.getRepayPeriod()));
                            // 应还时间
                            result.setRepayTime(GetDate.getDateTimeMyTime(borrowRepayPlan.getRepayTime()));
                            if (borrowRepayPlan.getRepayStatus() == 1) {
                                // 已经还款
                                result.setRepayYseTime(GetDate.getDateMyTimeInMillis(borrowRepayPlan.getRepayYestime()));
                            } else {
                                result.setRepayYseTime("");
                            }
                            //  本期应还本金
                            result.setRepayCapital(String.valueOf(borrowRepayPlan.getRepayCapital()));
                            // 本期应还利息
                            result.setRepayInterest(String.valueOf(borrowRepayPlan.getRepayInterest()));
                            // 管理费
                            result.setManageFee(StringUtil.valueOf(borrowRepayPlan.getRepayFee()));
                            // 本期实际还款
                            result.setRepayAccount(String.valueOf(borrowRepayPlan.getRepayAccountAll()));
                            // 还款状态
                            result.setRepayStatus(String.valueOf(borrowRepayPlan.getRepayStatus()));

                            // 本期应还总额
                            result.setPlanRepayTotal(String.valueOf(borrowRepayPlan.getRepayCapital().add(borrowRepayPlan.getRepayInterest()).add(borrowRepayPlan.getRepayFee())));
                            // 本期实际还款本息
                            result.setRepayAccount(String.valueOf(borrowRepayPlan.getRepayCapitalYes().add(borrowRepayPlan.getRepayInterestYes())));

                            //  本期实还本金
                            result.setRepayCapitalYes(String.valueOf(borrowRepayPlan.getRepayCapitalYes()));
                            // 本期实还利息
                            result.setRepayInterestYes(String.valueOf(borrowRepayPlan.getRepayInterestYes()));
                            // 本期实还服务费
                            if(borrowRepayPlan.getRepayStatus() != null&&borrowRepayPlan.getRepayStatus().intValue()==1){
                                result.setManageFeeYes(StringUtil.valueOf(borrowRepayPlan.getRepayFee()));
                            }else{
                                result.setManageFeeYes("0.00");
                            }
                            // 还款服务费
                            result.setRepayFee(StringUtil.valueOf(borrowRepayPlan.getRepayFee()));
                            // 提前还款减息
                            result.setReduceInterest(StringUtil.valueOf(borrowRepayPlan.getChargeInterest()));
                            // 逾期违约金
                            result.setLateInterest(StringUtil.valueOf(borrowRepayPlan.getLateInterest()));
                            BigDecimal repay=borrowRepayPlan.getRepayCapital().add(borrowRepayPlan.getRepayInterest());
                            if(borrowRepayPlan.getRepayAccount().compareTo(repay) == 1){
                                result.setLateInterest(StringUtil.valueOf(borrowRepayPlan.getRepayAccount().subtract(repay)));
                            }
                            // 逾期天
                            result.setLateDays(borrowRepayPlan.getLateDays());
                            //实还总额=本期实际还款本息+本期实还服务费+本期逾期违约金-本期提前还款减息+延期）（已还款返回
                            BigDecimal repayTotal = borrowRepayPlan.getRepayAccount().add(borrowRepayPlan.getRepayFee()).add(borrowRepayPlan.getLateInterest())
                                    .add(borrowRepayPlan.getChargeInterest()).add(borrowRepayPlan.getDelayInterest());
                            if(borrowRepayPlan.getRepayStatus() != null&&borrowRepayPlan.getRepayStatus().intValue()==1){
                                result.setRepayTotal(StringUtil.valueOf(repayTotal));
                            }else{
                                result.setRepayTotal("0.00");
                            }
                            if(!CollectionUtils.isEmpty(projectRepayBean)&&projectRepayBean.size() >0){
                                //获取提前还款间隙
                                if(StringUtils.isNotEmpty(projectRepayBean.get(ii).getStatus())&&"0".equals(projectRepayBean.get(ii).getStatus())){
                                    result.setReduceInterest(projectRepayBean.get(ii).getChargeInterest());
                                }
                            }
                            ii++;

                            detailList.add(result);
                        }
                    }
                }
                resultBean.setStatusDesc("查询成功");
                resultBean.setStatus(AemsErrorCodeConstant.SUCCESS);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
                resultBean.setTotalCounts(totalCounts);
                resultBean.setDetailList(detailList);
                return resultBean;
            } else {
                logger.info("没有查询到对应借款标的");
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000001);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000001);
                resultBean.setStatusDesc("没有查询到对应借款标的");
            }
        } catch (Exception e) {
            logger.error("查询还款详情失败， 原因: ", e);
        }
        return resultBean;
    }

}
