/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.aems.repayplan;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.AemsBorrowRepayPlanCustomizeVO;
import com.hyjf.am.vo.trade.BorrowVO;
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
import com.hyjf.cs.trade.service.svrcheck.CommonSvrChkService;
import com.hyjf.cs.trade.util.SignUtil;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/aems/repayplan")
public class AemsBorrowRepayPlanController extends BaseTradeController {

    @Autowired
    private CommonSvrChkService commonSvrChkService;

    @Autowired
    private AemsBorrowRepayPlanService aemsBorrowRepayPlanService;
    /**
     * 查询还款计划
     *
     * @param requestBean
     * @return
     */
    @ResponseBody
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
            if (!SignUtil.AEMSVerifyRequestSign(requestBean, "")) {
                logger.info("----验签失败----");
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_CE000002);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000002);
                resultBean.setStatusDesc("验签失败！");
                return resultBean;
            }

            // 根据机构获取标的还款
            Integer totalCounts = this.aemsBorrowRepayPlanService.selectBorrowRepayPlanCountsByInstCode(requestBean);
            if (totalCounts == 0) {
                logger.info("该机构未推送标的,或标的未放款");
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000003);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000003);
                resultBean.setStatusDesc("该机构未推送标的,或标的未放款");
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
            if (!SignUtil.AEMSVerifyRequestSign(requestBean, "")) {
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
                logger.info("该机构未推送标的,或标的未放款");
                resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000003);
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000003);
                resultBean.setStatusDesc("该机构未推送标的,或标的未放款");
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
                    // 根据标的编号查询还款计划
                    List<BorrowRepayPlanVO> repayPlanList = this.aemsBorrowRepayPlanService.selectBorrowRepayPlanByBorrowNid(borrowNid);
                    if (repayPlanList != null && repayPlanList.size() > 0) {
                        // 循环还款计划
                        for (BorrowRepayPlanVO borrowRepayPlan : repayPlanList) {
                            AemsBorrowRepayPlanCustomizeVO result = new AemsBorrowRepayPlanCustomizeVO();
                            // 资产编号
                            result.setProductId(productId);
                            // 标的号
                            result.setBorrowNid(borrowRepayPlan.getBorrowNid());
                            // 期数
                            result.setRepayPeriod(String.valueOf(borrowRepayPlan.getRepayPeriod()));
                            // 应还时间
                            result.setRepayTime(GetDate.getDateMyTimeInMillis(borrowRepayPlan.getRepayTime()));
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
                            // 本期应还金额
                            result.setPlanRepayTotal(String.valueOf(borrowRepayPlan.getRepayAccount().add(borrowRepayPlan.getRepayFee())));
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
