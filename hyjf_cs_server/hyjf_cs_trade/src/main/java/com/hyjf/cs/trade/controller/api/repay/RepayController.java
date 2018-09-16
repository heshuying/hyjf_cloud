package com.hyjf.cs.trade.controller.api.repay;

import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.ApiSignUtil;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.bean.*;
import com.hyjf.cs.trade.bean.RepayResultBean;
import com.hyjf.cs.trade.bean.repay.*;
import com.hyjf.cs.trade.service.repay.BatchHjhBorrowRepayApiService;
import com.hyjf.cs.trade.service.repay.RepayService;
import com.hyjf.cs.trade.service.synbalance.SynBalanceService;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import com.hyjf.cs.trade.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hyjf.cs.trade.bean.BaseBean;
import com.hyjf.cs.trade.bean.BaseResultBean;
import com.hyjf.cs.trade.bean.BatchCenterCustomize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

/**
 * @version RepayController, v0.1 2018/8/27 14:39
 * @Author: Zha Daojian
 */

@Api(value = "api端-第三方资产状态查询接口",tags = "api端-第三方资产状态查询接口")
@RestController
@RequestMapping("/hyjf-api/server/user/repay")
public class RepayController extends BaseController {

    @Autowired
    private BatchHjhBorrowRepayApiService batchBorrowRepayService;

    @Autowired
    private SynBalanceService synBalanceService;

    @Autowired
    private RepayService repayService;

    //获取还款信息接口
    public static final String METHOD_REPAY_INFO = "userRepayInfo";
    //获取还款结果接口
    public static final String METHOD_REPAY_RESULT = "userRepayResult";

    @PostMapping("/getrepayresult")
    @ApiParam(required = true, name = "findDetailById", value = "第三方资产状态查询接口")
    @ApiOperation(value = "第三方资产状态查询接口", httpMethod = "POST", notes = "第三方资产状态查询接口")
    public BaseResultBean getrepayresult(@RequestBody @Valid RepayRequestBean repaybean){
        RepayResultBean resultBean = new RepayResultBean();
        try {
            checkRepayResult(repaybean);
        } catch (Exception e) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            resultBean.setStatusDesc(e.getMessage());
            return resultBean;
        }
        BatchBorrowRecoverRequest batchBorrowRecoverRequest = new BatchBorrowRecoverRequest();
        // 借款编号 检索条件
        batchBorrowRecoverRequest.setBorrowNid(repaybean.getBorrowNid());
        // 投资人 检索条件
        batchBorrowRecoverRequest.setApiType(1);
        Integer count = this.repayService.countBatchCenter(batchBorrowRecoverRequest);
        if (count != null && count > 0) {
            List<BatchBorrowRecoverVo> recordList = this.repayService.getBatchBorrowRecoverList(batchBorrowRecoverRequest);
            BatchBorrowRecoverVo info = recordList.get(0);
            resultBean.setStatus(BaseResultBean.STATUS_SUCCESS);
            resultBean.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
            resultBean.setBorrowNid(info.getBorrowNid());
            resultBean.setBatchNo(info.getBatchNo());
            resultBean.setRole(info.getIsRepayOrgFlag());
            resultBean.setUserName(info.getUserName());
            resultBean.setPeriodNow(info.getPeriodNow());
            resultBean.setBorrowPeriod(info.getBorrowPeriod());
            resultBean.setBorrowAccount(info.getBorrowAccount());
            resultBean.setBatchAmount(info.getBatchAmount());
            resultBean.setBatchServiceFee(info.getBatchServiceFee());
            resultBean.setSucAmount(info.getSucAmount());
            resultBean.setFailAmount(info.getFailAmount());
            resultBean.setBatchStatus(info.getStatus());
            return resultBean;
        }
        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_HK000002);
        resultBean.setStatusDesc("借款标的没有还款记录!");
        return resultBean;
    }

    private BankOpenAccountVO checkRepayResult(RepayRequestBean info) {
        if (StringUtils.isBlank(info.getAccountId()) || StringUtils.isBlank(info.getBorrowNid()) || StringUtils.isBlank(info.getInstCode())) {
            throw new RuntimeException("参数非法,BorrowNid或accountId或instcode不得为空!");
        }
        //验签
        if (SignUtil.verifyRequestSign(info, "userRepayResult")) {
            logger.info("-------------------验签失败！--------------------");
            throw new RuntimeException("验签失败!");

        }
        BankOpenAccountVO bankOpenAccount = synBalanceService.getBankOpenAccount(info.getAccountId());
        if (bankOpenAccount == null) {
            logger.info("-------------------该用户没有在平台开户！--------------------");
            throw new RuntimeException("该用户没有在平台开户!");
        }
        return bankOpenAccount;
    }

    /**
     * 获得标的还款计划
     * @param repaybean
     * @return
     */
    @ApiOperation(value = "api端-还款计划查询", notes = "api端-还款计划查询")
    @PostMapping(value = "/getrepayinfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public BaseResultBean getRepayPlanInfo(@RequestBody RepayParamBean repaybean) {
        UserRepayProjectBean resultBean = new UserRepayProjectBean();
        RepayProjectListBean userRepay = new RepayProjectListBean();
        BankOpenAccountVO openAccount = null;
        try {
            openAccount = checkRepayPlanInfo(repaybean);
        } catch (Exception e) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            resultBean.setStatusDesc(e.getMessage());
            return resultBean;
        }

        //判断查询类型 0:待还款 1:已还款
        String repayType = repaybean.getRepayType();
        // 用户ID
        if (openAccount != null) {
            String productId = repaybean.getProductId();
            String instCode = repaybean.getInstCode();
            String borrowNid = batchBorrowRepayService.getborrowIdByProductId(productId,instCode);
            if (StringUtils.isBlank(borrowNid)) {
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_HK000001);
                resultBean.setStatusDesc("没有查询到对应借款编号!");
                return resultBean;
            }
            userRepay.setUserId(openAccount.getUserId() + "");
            userRepay.setRoleId("0");// 角色为借款人
            userRepay.setStatus(repayType);
            userRepay.setBorrowNid(borrowNid);
            //查询还款列表
            List<WebUserRepayProjectListCustomizeVO> recordList = repayService.searchUserRepayList(userRepay, 0, 0);
            if (recordList != null && recordList.size() > 0) {
                WebUserRepayProjectListCustomizeVO repayInfo = recordList.get(0);
                resultBean.setBorrowNid(repayInfo.getBorrowNid());
                resultBean.setBorrowInterest(repayInfo.getBorrowInterest());
                resultBean.setBorrowAccount(repayInfo.getBorrowAccount());
                resultBean.setYesAccount(repayInfo.getYesAccount());
                resultBean.setYesAccountTime(repayInfo.getYesAccountTime());
                resultBean.setBorrowTotal(repayInfo.getBorrowTotal());
                if ("0".equals(repayType)) {//待还款
                    //获取还款计划
                    ProjectBean form = new ProjectBean();
                    form.setUserId(openAccount.getUserId() + "");
                    form.setUsername(openAccount.getUserName());
                    form.setRoleId("0");
                    form.setBorrowNid(borrowNid);
                    form.setAllRepay(false);
                    // 查询用户的还款详情
                    try {
                        ProjectBean repayProject = repayService.searchRepayProjectDetail(form);
                        List<ProjectRepayListBean> list = getRepayDetailList(repayProject.getUserRepayList());
                        resultBean.setDetailList(list);
                        resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
                        resultBean.setStatusDesc("查询成功!");
                        return resultBean;
                    } catch (Exception e) {
                        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                        resultBean.setStatusDesc("查询对应还款计划异常!");
                        return resultBean;
                    }
                }else{
                    resultBean.setRepayYesTime(repayInfo.getRepayYesTime());
                    resultBean.setRepayTotal(repayInfo.getRepayTotal());
                    return resultBean;
                }
            }else{
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                resultBean.setStatusDesc("没有查询到对应还款计划!");
                return resultBean;
            }

        }
        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000006);
        resultBean.setStatusDesc("没有用户开户信息!");
        return resultBean;
    }


    /**
     * 获取待还款详细还款信息
     * @param userRepayList
     * @return
     */
    private List<ProjectRepayListBean> getRepayDetailList(List<ProjectRepayBean> userRepayList) {
        List<ProjectRepayListBean> list = new ArrayList<>();
        if (userRepayList != null) {
            for (int i = 0; i < userRepayList.size(); i++) {
                BigDecimal planRepay = new BigDecimal(0);
                ProjectRepayBean resultInfo = userRepayList.get(i);
                BigDecimal capital = new BigDecimal(resultInfo.getRepayCapital());
                BigDecimal interest = new BigDecimal(resultInfo.getRepayInterest());
                BigDecimal detailRepayAccount = capital.add(interest);
                ProjectRepayListBean bean = new ProjectRepayListBean();
                bean.setRepayPeriod(resultInfo.getRepayPeriod());
                bean.setRepayTime(resultInfo.getRepayTime());
                bean.setRepayCapital(resultInfo.getRepayCapital());
                bean.setRepayInterest(resultInfo.getRepayInterest());
                bean.setManageFee(resultInfo.getManageFee());
                bean.setRepayAccount(detailRepayAccount.toString());
                bean.setRepayStatus(resultInfo.getStatus());
                BigDecimal repayAccount = new BigDecimal(bean.getRepayAccount());
                BigDecimal repayManageFee = new BigDecimal(bean.getManageFee());
                planRepay = repayAccount.add(repayManageFee);
                bean.setPlanRepayTotal(planRepay.toString());
                list.add(bean);
            }
        }
        return list;
    }


    /**
     * 获取还款计划验签
     * @param info
     * @return
     */
    private BankOpenAccountVO checkRepayPlanInfo(RepayParamBean info) {
        if (StringUtils.isBlank(info.getAccountId()) || StringUtils.isBlank(info.getProductId()) || StringUtils.isBlank(info.getInstCode())) {
            throw new RuntimeException("参数非法,ProductId或accountId或instCode不得为空!");
        }
        //验签(测试暂时关闭验签功能)
        if(!this.verifyRequestSign(info, METHOD_REPAY_INFO)){
            throw new RuntimeException("验签失败!");
        }
        BankOpenAccountVO bankOpenAccount = repayService.getBankOpenAccount(Integer.valueOf(info.getAccountId()));
        if (bankOpenAccount == null) {
            throw new RuntimeException("该用户没有在平台开户!");
        }
        return bankOpenAccount;
    }


    /**
     * 获得还款批次处理结果
     * @param repaybean
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "获得还款批次处理结果", notes = "获得还款批次处理结果")
    @PostMapping(value = "/synbalance", produces = "application/json; charset=utf-8")
    @ResponseBody
    public BaseResultBean getRepayResult(@RequestBody RepayParamBean repaybean, HttpServletRequest request, HttpServletResponse response){
        com.hyjf.cs.trade.bean.repay.RepayResultBean resultBean = new com.hyjf.cs.trade.bean.repay.RepayResultBean();
        try {
            checkRepayResult(repaybean);
        } catch (Exception e) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            resultBean.setStatusDesc(e.getMessage());
            return resultBean;
        }
        BatchCenterCustomize batchCenterCustomize = new BatchCenterCustomize();
        // 借款编号 检索条件
        batchCenterCustomize.setBorrowNid(repaybean.getBorrowNid());
        // 投资人 检索条件
        batchCenterCustomize.setApiType(1);
        Long count = this.batchBorrowRepayService.countBatchCenter(batchCenterCustomize);
        if (count != null && count > 0) {
            List<BatchCenterCustomize> recordList = this.batchBorrowRepayService.selectBatchCenterList(batchCenterCustomize);
            BatchCenterCustomize info = recordList.get(0);
            resultBean.setStatus(BaseResultBean.STATUS_SUCCESS);
            resultBean.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
            resultBean.setBorrowNid(info.getBorrowNid());
            resultBean.setBatchNo(info.getBatchNo());
            resultBean.setRole(info.getIsRepayOrgFlag());
            resultBean.setUserName(info.getUserName());
            resultBean.setPeriodNow(info.getPeriodNow());
            resultBean.setBorrowPeriod(info.getBorrowPeriod());
            resultBean.setBorrowAccount(info.getBorrowAccount());
            resultBean.setBatchAmount(info.getBatchAmount());
            resultBean.setBatchServiceFee(info.getBatchServiceFee());
            resultBean.setSucAmount(info.getSucAmount());
            resultBean.setFailAmount(info.getFailAmount());
            resultBean.setBatchStatus(info.getStatus());
            return resultBean;
        }
        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_HK000002);
        resultBean.setStatusDesc("借款标的没有还款记录!");
        return resultBean;
    }

    private BankOpenAccountVO checkRepayResult(RepayParamBean info) {
        if (StringUtils.isBlank(info.getAccountId()) || StringUtils.isBlank(info.getBorrowNid()) || StringUtils.isBlank(info.getInstCode())) {
            throw new RuntimeException("参数非法,BorrowNid或accountId或instcode不得为空!");
        }
        //验签
        if(!this.verifyRequestSign(info, METHOD_REPAY_RESULT)){
            throw new RuntimeException("验签失败!");
        }
        BankOpenAccountVO bankOpenAccount = repayService.getBankOpenAccount(info.getAccountId());
        if (bankOpenAccount == null) {
            throw new RuntimeException("该用户没有在平台开户!");
        }
        return bankOpenAccount;
    }

    /**
     * 验证外部请求签名
     *
     * @param paramBean
     * @return
     */
    protected boolean verifyRequestSign(BaseBean paramBean, String methodName) {
        String sign = org.apache.commons.lang.StringUtils.EMPTY;
        // 机构编号必须参数
        String instCode = paramBean.getInstCode();
        if (org.apache.commons.lang.StringUtils.isEmpty(instCode)) {
            return false;
        }
        if(METHOD_REPAY_INFO.equals(methodName)){//获取还款计划
            RepayParamBean bean = (RepayParamBean) paramBean;
            sign = bean.getAccountId() + bean.getProductId() + bean.getRepayType() + bean.getInstCode() + bean.getTimestamp();
            ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
        }else if(METHOD_REPAY_RESULT.equals(methodName)){
            RepayParamBean bean = (RepayParamBean) paramBean;
            sign = bean.getChannel() + bean.getAccountId() + bean.getBorrowNid() + bean.getInstCode() + bean.getTimestamp();
            ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
        }
        return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);
    }

}
