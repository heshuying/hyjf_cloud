package com.hyjf.am.trade.controller.front.repay;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.*;
import com.hyjf.am.response.trade.RepayListResponse;
import com.hyjf.am.response.user.WebUserRepayTransferCustomizeResponse;
import com.hyjf.am.response.user.WebUserTransferBorrowInfoCustomizeResponse;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.WebUserRepayTransferRequest;
import com.hyjf.am.trade.bean.repay.ProjectBean;
import com.hyjf.am.trade.bean.repay.RepayBean;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.customize.WebUserRepayTransferCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserTransferBorrowInfoCustomize;
import com.hyjf.am.trade.service.front.repay.RepayManageService;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.user.WebUserRepayTransferCustomizeVO;
import com.hyjf.am.vo.user.WebUserTransferBorrowInfoCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDateUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * 还款管理
 * @author hesy
 * @version RepayManageController, v0.1 2018/6/26 17:41
 */
@RestController
@RequestMapping("/am-trade/repay")
public class RepayManageController extends BaseController {
    @Autowired
    RepayManageService repayManageService;

    /**
     * 普通借款人管理费总待还
     * @return
     */
    @RequestMapping(value = "/feewait_total_user/{userId}")
    public BigDecimalResponse userRepayFeeWaitTotal(@PathVariable Integer userId) {
        logger.info("普通借款人管理费总待还feewait_total_user，userId：" + userId);
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal waitTotal = repayManageService.selectUserRepayFeeWaitTotal(userId);
        response.setResultDec(waitTotal);
        logger.info("response: {}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 担保机构管理费总待还
     * @return
     */
    @RequestMapping(value = "/feewait_total_org/{userId}")
    public BigDecimalResponse orgRepayFeeWaitTotal(@PathVariable Integer userId) {
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal waitTotal = repayManageService.selectOrgRepayFeeWaitTotal(userId);
        if(null==waitTotal){
            response.setResultDec(new BigDecimal(0));
        }
        response.setResultDec(waitTotal);
        return response;
    }

    /**
     * 担保机构待还
     * @param userId
     * @return
     */
    @RequestMapping(value = "/repaywait_total_org/{userId}")
    public BigDecimalResponse orgRepayWaitTotal(@PathVariable Integer userId) {
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal waitTotal = repayManageService.selectRepayOrgRepaywait(userId);
        response.setResultDec(waitTotal);
        return response;
    }

    /**
     * 检索还款列表
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/repaylist")
    public RepayListResponse repayList(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        List<RepayListCustomizeVO> resultList = repayManageService.selectRepayList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 统计还款总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/repaycount")
    public IntegerResponse repayCount(@RequestBody @Valid RepayListRequest requestBean) {
        IntegerResponse responseBean = new IntegerResponse();
        Integer result = repayManageService.selectRepayCount(requestBean);
        responseBean.setResultInt(result);
        return responseBean;
    }

    /**
     * 检索垫付机构待还款列表
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/orgrepaylist")
    public RepayListResponse orgRepayList(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        List<RepayListCustomizeVO> resultList = repayManageService.selectOrgRepayList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 统计垫付机构待还款总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/orgrepaycount")
    public IntegerResponse orgRepayCount(@RequestBody @Valid RepayListRequest requestBean) {
        IntegerResponse responseBean = new IntegerResponse();
        Integer result = repayManageService.selectOrgRepayCount(requestBean);
        responseBean.setResultInt(result);
        return responseBean;
    }

    /**
     * 检索垫付机构已还款列表
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/orgrepayedlist")
    public RepayListResponse orgRepayedList(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        List<RepayListCustomizeVO> resultList = repayManageService.selectOrgRepayedList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 统计垫付机构已还款总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/orgrepayedcount")
    public IntegerResponse orgRepayedCount(@RequestBody @Valid RepayListRequest requestBean) {
        IntegerResponse responseBean = new IntegerResponse();
        Integer result = repayManageService.selectOrgRepayedCount(requestBean);
        responseBean.setResultInt(result);
        return responseBean;
    }

    /**
     * 垫付机构本期应还总额
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/orgrepay_waittotal_current")
    public BigDecimalResponse orgRepayWaitTotalCurrent(@RequestBody @Valid RepayListRequest requestBean) {
        BigDecimalResponse responseBean = new BigDecimalResponse();
        BigDecimal result = repayManageService.selectOrgRepayWaitCurrent(requestBean);
        responseBean.setResultDec(result);
        return responseBean;
    }

    /**
     * 还款详情页面数据获取
     * @param requestBean
     * @return
     */
    @PostMapping(value = "/repay_detail")
    public StringResponse repayDetail(@RequestBody @Valid RepayRequestDetailRequest requestBean){
        logger.info("开始获取还款详情页面数据，requestBean：" + JSON.toJSONString(requestBean));
        StringResponse responseBean = new StringResponse();
        ProjectBean projectBean = new ProjectBean();
        projectBean.setUserId(String.valueOf(requestBean.getUserId()));
        projectBean.setUsername(requestBean.getUserName());
        projectBean.setRoleId(requestBean.getRoleId());
        projectBean.setBorrowNid(requestBean.getBorrowNid());
        responseBean.setResultStr(JSON.toJSONString(projectBean));
        try {
            projectBean = repayManageService.searchRepayProjectDetail(projectBean, requestBean.getAllRepay());
            responseBean.setResultStr(JSON.toJSONString(projectBean));
            return responseBean;
        } catch (Exception e) {
            logger.error("还款申请详情页面异常", e);
            responseBean.setRtn(Response.ERROR);
            responseBean.setMessage("还款申请详情页面异常");
            return responseBean;
        }
    }
    /**
     * 还款申请更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    @RequestMapping(value = "/update")
    public BooleanResponse updateRepayMoney(@RequestBody @Valid RepayRequestUpdateRequest requestBean){
        logger.info("还款申请更新开始，repuestBean: " + JSON.toJSONString(requestBean));

        if (requestBean == null || StringUtils.isBlank(requestBean.getRepayBeanData()) || StringUtils.isBlank(requestBean.getBankCallBeanData())){
            return new BooleanResponse(false);
        }

        // 更新相关数据库表
        try {
            RepayBean repayBean = JSON.parseObject(requestBean.getRepayBeanData(), RepayBean.class);
            BankCallBean bankCallBean = JSON.parseObject(requestBean.getBankCallBeanData(), BankCallBean.class);

            boolean result = repayManageService.updateRepayMoney(repayBean, bankCallBean, requestBean.isAllRepay());
            return new BooleanResponse(result);
        } catch (Exception e) {
            logger.error("还款申请更新数据库失败", e);
            return new BooleanResponse(false);
        }
    }

    /**
     * 更新借款API任务表
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/update_apicron")
    public Response<Boolean> updateBorrowApicron(@RequestBody ApiCronUpdateRequest requestBean){
        Integer status = requestBean.getStatus();
        BorrowApicronVO apicronVO = requestBean.getApicronVO();
        if(status == null || apicronVO == null){
            return new Response<>(Response.FAIL, "请求信息不全");
        }
        BorrowApicron apicron = new BorrowApicron();
        BeanUtils.copyProperties(apicronVO, apicron);

        try {
            repayManageService.updateBorrowApicron(apicron, requestBean.getStatus());
        } catch (Exception e) {
            return new Response(Response.ERROR, "api任务表更新异常");
        }

        return new Response(true);
    }

    /**
     * 如果有正在出让的债权,先去把出让状态停止
     * @param borrowNid
     * @return
     */
    @GetMapping(value = "/update_borrowcredit_status/{borrowNid}")
    public Response<Boolean> updateBorrowCreditStautus(@PathVariable String borrowNid){
        if(StringUtils.isBlank(borrowNid)){
            return new Response<>(Response.FAIL, "请求信息不全");
        }

        try {
            repayManageService.updateBorrowCreditStautus(borrowNid);
        } catch (Exception e) {
            return new Response(Response.ERROR, "更新债权状态失败");
        }

        return new Response(true);
    }

    /**
     * 获取计算完的还款Bean
     * @param paraMap
     * @return
     */
    @PostMapping(value = "/get_repaybean")
    public StringResponse getRepayBean(@RequestBody Map<String,String> paraMap){
        StringResponse responseBean = new StringResponse();
        RepayBean repayByTerm = null;

        String roleId = paraMap.get("roleId");
        String borrowNid = paraMap.get("borrowNid");
        String userId = paraMap.get("userId");
        boolean isAllRepay = Boolean.valueOf(paraMap.get("isAllRepay"));
        logger.info("获取计算完的还款Bean开始：{}", paraMap);
        try {
            Borrow borrow = repayManageService.getBorrowByNid(borrowNid);
            Account account = repayManageService.getAccount(Integer.parseInt(userId));
            // 担保机构
            if(roleId.equals("3")){
                // 一次性还款
                if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle())) {
                    repayByTerm = this.repayManageService.searchRepayTotalV2(borrow.getUserId(), borrow);
                    repayByTerm.setRepayUserId(Integer.parseInt(userId));// 垫付机构id
                } // 分期还款
                else {
                    if(isAllRepay) {
                        repayByTerm = this.repayManageService.searchRepayPlanTotal(borrow.getUserId(), borrow);
                    }else {
                        repayByTerm = this.repayManageService.searchRepayByTermTotalV2(borrow.getUserId(), borrow, borrow.getBorrowApr(), borrow.getBorrowStyle(), borrow.getBorrowPeriod());
                    }
                    repayByTerm.setRepayUserId(Integer.parseInt(userId));// 垫付机构id
                }
            }else { // 普通借款用户
                if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle())) {
                    // 一次性还款
                    repayByTerm = this.repayManageService.searchRepayTotalV2(Integer.parseInt(userId), borrow);
                }else {
                    // 分期还款
                    if(isAllRepay) {
                        repayByTerm = this.repayManageService.searchRepayPlanTotal(Integer.parseInt(userId), borrow);
                    }else {
                        repayByTerm = this.repayManageService.searchRepayByTermTotalV2(Integer.parseInt(userId), borrow, borrow.getBorrowApr(), borrow.getBorrowStyle(), borrow.getBorrowPeriod());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("还款数据计算失败", e);
            responseBean.setRtn(Response.ERROR);
            responseBean.setMessage("还款数据计算失败");
            responseBean.setResultStr(JSON.toJSONString(repayByTerm));
            return responseBean;
        }
        responseBean.setResultStr(JSON.toJSONString(repayByTerm));
        return responseBean;
    }

    /**
     * 获取担保机构批量还款页面数据
     */
    @PostMapping(value = "/get_batch_reapydata")
    public StringResponse getOrgBatchRepayData(@RequestBody BatchRepayDataRequest requestBean) {
        StringResponse responseBean = new StringResponse();
        ProjectBean projectBean = repayManageService.getOrgBatchRepayData(requestBean.getUserId(), requestBean.getStartDate(), requestBean.getEndDate());
        responseBean.setResultStr(JSON.toJSONString(projectBean));
        return responseBean;
    }

    /**
     * 计算担保机构批量还款总垫付金额并插入冻结日志
     */
    @PostMapping(value = "/get_batch_reapy_total")
    public BigDecimalResponse getOrgBatchRepayTotal(@RequestBody BatchRepayTotalRequest requestBean) {
        BigDecimalResponse responseBean = new BigDecimalResponse();
        String userId = requestBean.getUserId();
        String userName = requestBean.getUserName();
        String orderId = requestBean.getOrderId();
        String account = requestBean.getAccount();
        RepayListRequest requestListBean = new RepayListRequest();
        requestListBean.setUserId(requestBean.getUserId());
        requestListBean.setRoleId("3");
        requestListBean.setStartDate(requestBean.getStartDate());
        requestListBean.setEndDate(requestBean.getEndDate());
        requestListBean.setStatus("0");
        requestListBean.setRepayStatus("0");
        List<RepayListCustomizeVO> orgRepayList = repayManageService.selectOrgRepayList(requestListBean);
        if(orgRepayList == null || orgRepayList.size() == 0){
            throw new CheckException(MsgEnum.ERR_DATA_NOT_EXISTS);
        }
        int allRepaySize = orgRepayList.size();//所有还款标的数目
        BigDecimal repayTotal = BigDecimal.ZERO;
        logger.info("【批量还款垫付】冻结订单号：{}，开始计算总垫付金额。总还款笔数：{}，垫付机构用户名：{}",orderId, allRepaySize, userName);
        for (RepayListCustomizeVO repayInfo:orgRepayList) {
            String borrowNid = repayInfo.getBorrowNid();
            try {
                Borrow borrow = repayManageService.getBorrowByNid(borrowNid);
                RepayBean repayBean = null;
//                boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrow.getBorrowNid(), 600);
//                if (!tranactionSetFlag) {//设置失败
//                    logger.error("【批量还款垫付】借款编号：{}，正在处理项目债转！", borrowNid);
//                    long retTime = RedisUtils.ttl(RedisConstants.HJH_DEBT_SWAPING + borrow.getBorrowNid());
//                    String dateStr = com.hyjf.common.util.calculate.DateUtils.nowDateAddSecond((int) retTime);
//                    throw new CheckException(MsgEnum.ERR_AMT_REPAY_AUTO_CREDIT, dateStr);
//                }
                // 计算垫付机构还款
                if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle())) {
                    repayBean = repayManageService.searchRepayTotalV2(borrow.getUserId(), borrow);
                } else {// 分期还款
                    // 批量垫付没有一次性还款
                    repayBean = repayManageService.searchRepayByTermTotalV2(borrow.getUserId(), borrow, borrow.getBorrowApr(), borrow.getBorrowStyle(), borrow.getBorrowPeriod());
                }
                repayBean.setRepayUserId(Integer.parseInt(userId));
                checkForRepayRequestOrg(borrowNid, userId, userName, repayBean);
                //还款去重
                boolean result = repayManageService.checkRepayInfo(null, borrowNid);
                if (!result) {
                    logger.error("【批量还款垫付】冻结订单号：{}，借款编号:{}，项目正在还款中...", orderId, borrowNid);
                    continue;
                }
                //插入垫付机构冻结信息日志表 add by wgx 2018-09-11
                repayManageService.insertRepayOrgFreezeLog(Integer.parseInt(userId), orderId, account, borrowNid, repayBean, userName, false);
                BigDecimal total = repayBean.getRepayAccountAll();
                repayTotal = repayTotal.add(total);
            } catch (Exception e) {
                logger.error("【批量还款垫付】批量垫付单个标的发生异常！", e);
            }
        }
        logger.info("【批量还款垫付】冻结订单号：{}，总垫付金额：{}",orderId, repayTotal);
        responseBean.setResultDec(repayTotal);
        return responseBean;
    }

    /**
     * 垫付机构校验
     */
    private void checkForRepayRequestOrg(String borrowNid, String userId, String userName, RepayBean repayBean) {
        if (StringUtils.isBlank(borrowNid) || StringUtils.isBlank(userId)) {
            logger.error("【批量还款校验】还款请求参数不全！", borrowNid);
            throw new CheckException(MsgEnum.ERR_PARAM_NUM);
        }
        // 平台密码校验、服务费授权校验、开户校验在处理批次还款前进行校验
        Borrow borrow = repayManageService.getBorrowByNid(borrowNid);
        if (borrow == null) {
            logger.error("【批量还款校验】借款编号：{}，标的信息不存在！", borrowNid);
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        boolean hasFailCredit = repayManageService.getFailCredit(borrowNid);
        if(hasFailCredit){//还款提交失败，请联系汇盈金服业务部门核实处理。
            logger.error("【批量还款校验】借款编号：{}，有承接失败的债权！", borrowNid);
            throw new CheckException(MsgEnum.ERR_AMT_REPAY_FAIL_CREDIT);
        }
        Account account = repayManageService.getAccount(Integer.parseInt(userId));
        if (repayBean.getRepayAccountAll().compareTo(account.getBankBalance()) == 0 || repayBean.getRepayAccountAll().compareTo(account.getBankBalance()) == -1) {
            // 垫付机构符合还款条件，可以还款
            // 垫付机构批量还款 ，不验证银行账户余额
        } else {
            // 用户平台账户余额不足
            logger.error("【批量还款校验】平台账户余额不足！担保机构用户名：{}", userName);
            throw new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
        }
    }

    /**
     * 根据借款编号查询当前标的是否有承接失败的债权
     * @return
     */
    @GetMapping(value = "/getFailCredit/{borrowNid}")
    public boolean getFailCredit(@PathVariable String borrowNid){
        return repayManageService.getFailCredit(borrowNid);
    }

    /**
     *
     * @return
     */
    @GetMapping(value = "/get_user_transfer_borrow_info/{borrowNid}")
    public WebUserTransferBorrowInfoCustomizeResponse getUserTransferBorrowInfo(@PathVariable String borrowNid){
        WebUserTransferBorrowInfoCustomizeResponse response = new WebUserTransferBorrowInfoCustomizeResponse();
        WebUserTransferBorrowInfoCustomize borrowInfoCustomize = repayManageService.getUserTransferBorrowInfo(borrowNid);

        if (borrowInfoCustomize != null){
            WebUserTransferBorrowInfoCustomizeVO userTransferBorrowInfoCustomizeVO = new WebUserTransferBorrowInfoCustomizeVO();
            BeanUtils.copyProperties(borrowInfoCustomize,userTransferBorrowInfoCustomizeVO);
            response.setResult(userTransferBorrowInfoCustomizeVO);
        }
        return response;
    }

    /**
     * 转让通知借款人 统计
     * @param repayTransferRequest
     * @return
     * @Author : huanghui
     */
    @PostMapping(value = "/getUserRepayDetailAjaxCount", produces = "application/json; charset=utf-8")
    public IntegerResponse getUserRepayDetailAjaxCount(@RequestBody WebUserRepayTransferRequest repayTransferRequest){
        IntegerResponse responseBean = new IntegerResponse();
        Integer listCount = repayManageService.selectUserRepayTransferDetailListTotal(repayTransferRequest.getBorrowNid(), repayTransferRequest.getVerificationFlag());
        responseBean.setResultInt(listCount);
        return responseBean;
    }

    /**
     * 获取列表
     * @param repayTransferRequest
     * @return
     */
    @PostMapping(value = "/userRepayDetailAjax", produces = "application/json; charset=utf-8")
    public WebUserRepayTransferCustomizeResponse userRepayDetailAjax(@RequestBody WebUserRepayTransferRequest repayTransferRequest){
        WebUserRepayTransferCustomizeResponse repayTransferCustomizeResponse = new WebUserRepayTransferCustomizeResponse();
        repayTransferRequest.setBorrowNid(repayTransferRequest.getBorrowNid());
        repayTransferRequest.setVerificationFlag(repayTransferRequest.getVerificationFlag());

        List<WebUserRepayTransferCustomize> repayList = repayManageService.selectUserRepayTransferDetailList(repayTransferRequest);

        if(null != repayList && repayList.size() > 0){
            // 数据格式化的格式 10,000.00
            DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

            // 遍历列表, 给承接人和转让人用户名加密
            for (WebUserRepayTransferCustomize re: repayList) {
                re.setCreditUserName(repayManageService.usernameEncryption(re.getCreditUserName()));
                re.setUndertakerUserName(repayManageService.usernameEncryption(re.getUndertakerUserName()));
                re.setAssignCapitalString(decimalFormat.format(re.getAssignCapital()));
                re.setAssignOrderDateStr(re.getAssignOrderDate());
            }
        }

        String returnCode = "0";
        List<WebUserRepayTransferCustomizeVO> voList = null;
        if (CollectionUtils.isNotEmpty(repayList)){
            voList = CommonUtils.convertBeanList(repayList, WebUserRepayTransferCustomizeVO.class);
        }
        repayTransferCustomizeResponse.setRtn(returnCode);
        repayTransferCustomizeResponse.setResultList(voList);

        return repayTransferCustomizeResponse;
    }
}
