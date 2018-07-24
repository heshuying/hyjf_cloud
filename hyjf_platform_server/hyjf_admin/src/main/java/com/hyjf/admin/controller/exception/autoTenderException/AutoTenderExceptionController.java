/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.autoTenderException;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.beans.request.AutoTenderExceptionRequestBean;
import com.hyjf.admin.beans.request.BorrowInvestRequestBean;
import com.hyjf.admin.beans.request.TenderExceptionSolveRequestBean;
import com.hyjf.admin.beans.response.BorrowInvestResponseBean;
import com.hyjf.admin.beans.vo.AdminBorrowInvestCustomizeVO;
import com.hyjf.admin.beans.vo.AdminPlanAccedeListVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AutoTenderExceptionService;
import com.hyjf.admin.service.BorrowInvestService;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.admin.service.exception.HjhCreditEndExceptionService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AutoTenderExceptionResponse;
import com.hyjf.am.resquest.admin.AutoTenderExceptionRequest;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.resquest.admin.TenderExceptionSolveRequest;
import com.hyjf.am.vo.admin.AdminPlanAccedeListCustomizeVO;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanBorrowTmpVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.bean.RedisBorrow;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author nixiaoling
 * @version AutoTenderExceptionController, v0.1 2018/7/12 10:27
 */
@Api(value = "异常中心-汇计划投资异常",description = "异常中心-汇计划投资异常")
@RestController
@RequestMapping("/hyjf-admin/autotenderexception")
public class AutoTenderExceptionController extends BaseController {

    @Autowired
    private AutoTenderExceptionService autoTenderExceptionService;
    @Autowired
    private BorrowInvestService borrowInvestService;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private HjhCreditEndExceptionService hjhCreditEndExceptionService;


    private static final String PERMISSIONS = "accedelist";


    @ApiOperation(value = "汇计划投资异常列表显示", notes = "汇计划投资异常列表显示")
    @PostMapping(value = "/selectAccedeRecordList")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<AdminPlanAccedeListVO>> selectAccedeRecordList(HttpServletRequest request, @RequestBody AutoTenderExceptionRequestBean autoTenderExceptionRequestBean) {
        AutoTenderExceptionRequest autoTenderExceptionRequest = new AutoTenderExceptionRequest();
        BeanUtils.copyProperties(autoTenderExceptionRequestBean, autoTenderExceptionRequest);
        AutoTenderExceptionResponse autoTenderExceptionResponse = autoTenderExceptionService.selectAccedeRecordList(autoTenderExceptionRequest);
        if (autoTenderExceptionResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(autoTenderExceptionResponse)) {
            return new AdminResult<>(FAIL, autoTenderExceptionResponse.getMessage());

        }
        List<AdminPlanAccedeListVO> listVOList = new ArrayList<AdminPlanAccedeListVO>();
        List<AdminPlanAccedeListCustomizeVO> listCustomizeVOS  =autoTenderExceptionResponse.getResultList();
        if(null!=listCustomizeVOS&&listCustomizeVOS.size()>0){
            listVOList = CommonUtils.convertBeanList(listCustomizeVOS,AdminPlanAccedeListVO.class);
        }
        return new AdminResult<ListResult<AdminPlanAccedeListVO>>(ListResult.build(listVOList, autoTenderExceptionResponse.getCount()));
    }

    /**
     * 投资明细画面初始化
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "投资明细列表显示", notes = "投资明细列表显示")
    @PostMapping(value = "/tenderInfoAction")
    @ResponseBody
    public AdminResult<ListResult<AdminBorrowInvestCustomizeVO>> tenderInfoAction(HttpServletRequest request, @RequestBody BorrowInvestRequestBean borrowInvestRequestBean) {
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        BeanUtils.copyProperties(borrowInvestRequestBean, borrowInvestRequest);
        BorrowInvestResponseBean responseBean = borrowInvestService.getBorrowInvestList(borrowInvestRequest);
        if (responseBean == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<AdminBorrowInvestCustomizeVO> adminBorrowInvestCustomizeVOList = new ArrayList<AdminBorrowInvestCustomizeVO>();
        List<BorrowInvestCustomizeVO> borrowInvestCustomizeVOList = responseBean.getRecordList();
        if(null!=borrowInvestCustomizeVOList&&borrowInvestCustomizeVOList.size()>0){
            adminBorrowInvestCustomizeVOList = CommonUtils.convertBeanList(borrowInvestCustomizeVOList,AdminBorrowInvestCustomizeVO.class);
        }
        return new AdminResult<ListResult<AdminBorrowInvestCustomizeVO>>(ListResult.build(adminBorrowInvestCustomizeVOList, responseBean.getTotal()));
    }

    /**
     * 汇计划加入明细异常处理
     * @return
     */
    @ApiOperation(value = "异常处理", notes = "异常处理")
    @PostMapping(value = "/tenderExceptionAction")
    @ResponseBody
    public AdminResult tenderExceptionAction(HttpServletRequest request,@RequestBody TenderExceptionSolveRequestBean tenderExceptionSolveRequestBean){
        String userId = tenderExceptionSolveRequestBean.getUserId();
        String planOrderId = tenderExceptionSolveRequestBean.getPlanOrderId();
        String debtPlanNid = tenderExceptionSolveRequestBean.getDebtPlanNid();
        logger.info("===自动投资异常处理开始。。。 userId:"+userId+",planOrderId:"+planOrderId+",debtPlanNid:"+debtPlanNid);
        TenderExceptionSolveRequest tenderExceptionSolveRequest = new TenderExceptionSolveRequest();
        BeanUtils.copyProperties(tenderExceptionSolveRequestBean, tenderExceptionSolveRequest);
        String retMessage = "系统异常";
        try {
            int userIdint = Integer.parseInt(userId);

            HjhAccedeVO hjhAccede = autoTenderExceptionService.getHjhAccedeVO(tenderExceptionSolveRequest);
            if(hjhAccede == null){
                return new AdminResult<>(FAIL, planOrderId+" 没有加入明细");
            }
            if(hjhAccede.getOrderStatus() == null ||
                    hjhAccede.getOrderStatus().intValue() >= 90){
                return new AdminResult<>(FAIL, planOrderId+" >90 失败(联系管理员) 手动处理");

            }
            // 原始标，异常先查异常表，访问银行接口，如果订单成功，查看表是否OK, 修复后，更新状态。没修复不更新
            // 债转也是一样。如失败则直接更新
            HjhPlanBorrowTmpVO hjhPlanBorrowTmp = autoTenderExceptionService.getHjhPlanBorrowTmpVO(tenderExceptionSolveRequest);
            if(hjhPlanBorrowTmp == null){
                logger.info(planOrderId+" 没有投资异常明细");
                return new AdminResult<>(FAIL, planOrderId+" 没有投资异常明细");
            }
            logger.info(planOrderId+""+hjhPlanBorrowTmp.getBorrowNid());
            if(StringUtils.isBlank(hjhPlanBorrowTmp.getOrderId())){
                return new AdminResult<>(FAIL, planOrderId+" 没有投资异常订单");

            }
            BankOpenAccountVO borrowUserAccount = userCenterService.queryBankOpenAccountByUserId(userIdint);
            if (borrowUserAccount == null || StringUtils.isBlank(borrowUserAccount.getAccount())) {
                logger.info("投资人未开户:" + userId);
                return new AdminResult<>(FAIL, "投资人未开户?:" + userId);

            }
            // 借款人账户
            String borrowUserAccountId = borrowUserAccount.getAccount();
            // 为了判断
            int orderStatus = hjhAccede.getOrderStatus()%10;
            hjhAccede.setOrderStatus(orderStatus);
            // 原始标投资
            if(hjhPlanBorrowTmp.getBorrowType() == 0){
                BorrowVO borrow = autoTenderExceptionService.selectBorrowByNid(hjhPlanBorrowTmp.getBorrowNid());
                if (borrow == null) {
                    logger.error("[" + hjhPlanBorrowTmp.getBorrowNid() + "]" + "标的号不存在 ");
                    return new AdminResult<>(FAIL, planOrderId+" 标的号不存在 "+hjhPlanBorrowTmp.getBorrowNid());

                }
                // 目前处理 510000 银行系统返回异常
                // CA101141	投标记录不存在
                BankCallBean debtQuery = autoTenderExceptionService.debtStatusQuery(userIdint, borrowUserAccountId,hjhPlanBorrowTmp.getOrderId());
                if(debtQuery == null || StringUtils.isBlank(debtQuery.getRetCode())){
                    logger.info(userIdint+"  "+borrowUserAccountId+"  "+hjhPlanBorrowTmp.getOrderId()+" 请求银行查询无响应");
                    return new AdminResult<>(FAIL, planOrderId+" 请求银行查询无响应");
                }
                String queryRetCode = debtQuery.getRetCode();
                boolean bankQueryisOK = false;
                if (BankCallConstant.RESPCODE_SUCCESS.equals(queryRetCode)) {
                    String state = StringUtils.isNotBlank(debtQuery.getState()) ? debtQuery.getState() : "";
                    logger.info(("查询投资请求成功:" + hjhPlanBorrowTmp.getOrderId())+ " 状态 "+state);
                    // 1：投标中； 2：计息中；4：本息已返还；9：已撤销；
                    if (StringUtils.isNotBlank(state)) {
                        if (state.equals("1")) {
                            bankQueryisOK = true;
                        } else if (state.equals("2")) {
                        }
                    }
                }
                if(bankQueryisOK){
                    logger.info("查询银行记录成功:" + userId);
                    debtQuery.setOrderId(hjhPlanBorrowTmp.getOrderId());
                    // 如果有tmp表，说明第一接口调用了，调用成功，表更新失败，无如都是需要调用下面的方法
                    boolean isOK = autoTenderExceptionService.updateBorrowForAutoTender(borrow,hjhAccede,debtQuery);
                    if(isOK){
                        // 更改加入明细状态和投资临时表状态
                        autoTenderExceptionService.updateTenderByParam(orderStatus,hjhAccede.getId());
                        // 只有不是接口成功表失败的情况才会退回队列
                        if(!BankCallConstant.RESPCODE_SUCCESS.equals(hjhPlanBorrowTmp.getRespCode())){
                            // 投资成功后减掉redis 钱
                            String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + RedisConstants.HJH_SLASH + hjhAccede.getPlanNid();
                            RedisBorrow redisBorrow = new RedisBorrow();
                            redisBorrow.setBorrowAccountWait(borrow.getBorrowAccountWait().subtract(hjhPlanBorrowTmp.getAccount()));
                            redisBorrow.setBorrowNid(borrow.getBorrowNid());

                            // 银行成功后，如果标的可投金额非0，推回队列的头部，标的可投金额为0，不再推回队列
                            if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) != 0) {
                                String redisStr = JSON.toJSONString(redisBorrow);
                                logger.info("退回队列 " + queueName + redisStr);
                                RedisUtils.rightpush(queueName,redisStr);
                            }
                        }
                    }
                    // 投标记录不存在才会继续，不然属于未知情况
                }else if ("CA101141".equals(queryRetCode)){
                    // 更改加入明细状态和投资临时表状态
                    logger.info(hjhPlanBorrowTmp.getRespCode()+" 查询银行记录成功，但是投资失败:" + userId);
                    autoTenderExceptionService.updateTenderByParam(hjhAccede.getId(),orderStatus);

                    // 只有不是接口成功表失败的情况才会退回队列
                    if(!BankCallConstant.RESPCODE_SUCCESS.equals(hjhPlanBorrowTmp.getRespCode())){
                        // 投资成功后减掉redis 钱
                        String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + RedisConstants.HJH_SLASH + hjhAccede.getPlanNid();
                        RedisBorrow redisBorrow = new RedisBorrow();
                        redisBorrow.setBorrowAccountWait(borrow.getBorrowAccountWait());
                        redisBorrow.setBorrowNid(borrow.getBorrowNid());

                        // 银行成功后，如果标的可投金额非0，推回队列的头部，标的可投金额为0，不再推回队列
                        if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) != 0) {
                            String redisStr = JSON.toJSONString(redisBorrow);
                            logger.info("退回队列 " + queueName + redisStr);
                            RedisUtils.rightpush(queueName,redisStr);
                        }
                    }
                }else{
                    return new AdminResult<>(FAIL, planOrderId+" 暂时没有处理该异常，需要协调开发处理  "+ queryRetCode);
                }
                // 债转标投资
            }else if(hjhPlanBorrowTmp.getBorrowType() == 1){
                HjhDebtCreditVO credit =hjhCreditEndExceptionService.selectHjhDebtCreditByCreditNid(hjhPlanBorrowTmp.getBorrowNid());
                if (credit == null) {
                    logger.error("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]" + "债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid());
                    return new AdminResult<>(FAIL, planOrderId+" 债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid());
                }

                BankCallBean bean = autoTenderExceptionService.creditStatusQuery(userIdint, borrowUserAccountId, hjhPlanBorrowTmp.getOrderId());
                String queryRetCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
                if(BankCallConstant.RESPCODE_SUCCESS.equals(queryRetCode)){
                    logger.info("查询银行记录成功:" + userId);

                    //更新表
                    HjhPlanVO hjhPlan =  autoTenderExceptionService.getFirstHjhPlanVO(hjhAccede.getPlanNid());
                    BankOpenAccountVO sellerBankOpenAccount = userCenterService.queryBankOpenAccountByUserId(credit.getUserId());
                    if (sellerBankOpenAccount == null) {
                        logger.info("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]" + "转出用户没开户 "+credit.getUserId());
                        return new AdminResult<>(FAIL, planOrderId+" 债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid());
                    }
                    String sellerUsrcustid = sellerBankOpenAccount.getAccount();//出让用户的江西银行电子账号
                    // 生成承接日志
                    // 债权承接订单日期
                    String orderDate = GetDate.getServerDateTime(1, new Date());
                    Boolean isLast = false;
                    //是否标的的最后一笔投资/承接(0:非最后一笔；1:最后一笔)
                    if(hjhPlanBorrowTmp.getIsLast()==1){
                        isLast = true;
                    }
                    // 计算实际金额 保存creditTenderLog表
                    Map<String, Object> resultMap = autoTenderExceptionService.saveCreditTenderLogNoSave(credit, hjhAccede,
                            hjhPlanBorrowTmp.getOrderId(), orderDate, hjhPlanBorrowTmp.getAccount(),isLast);
                    if (Validator.isNull(resultMap)) {
                        logger.info("保存creditTenderLog表失败，计划订单号：" + hjhAccede.getAccedeOrderId());
                        return new AdminResult<>(FAIL, "保存creditTenderLog表失败，计划订单号：" + hjhAccede.getAccedeOrderId());
                    }
                    //汇计划自动投资(收债转服务费)
                    logger.info("[" + hjhAccede.getAccedeOrderId() + "]" + "承接用计算完成"
                            + "\n,分期数据结果:" + resultMap.get("assignResult")
                            + "\n,承接总额:" + resultMap.get("assignAccount")
                            + "\n,承接本金:" + resultMap.get("assignCapital")
                            + "\n,承接利息:" + resultMap.get("assignInterest")
                            + "\n,承接支付金额:" + resultMap.get("assignPay")
                            + "\n,承接垫付利息:" + resultMap.get("assignAdvanceMentInterest")
                            + "\n,承接延期利息:" + resultMap.get("assignRepayDelayInterest")
                            + "\n,承接逾期利息:" + resultMap.get("assignRepayLateInterest")
                            + "\n,分期本金:" + resultMap.get("assignPeriodCapital")
                            + "\n,分期利息:" + resultMap.get("assignPeriodInterest")
                            + "\n,分期垫付利息:" + resultMap.get("assignPeriodAdvanceMentInterest")
                            + "\n,分期承接延期利息:" + resultMap.get("assignPeriodRepayDelayInterest")
                            + "\n,分期承接延期利息:" + resultMap.get("assignPeriodRepayLateInterest")
                            + "\n,承接服务率:" + resultMap.get("serviceApr")
                            + "\n,承接服务费:" + resultMap.get("serviceFee"));
                    // add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
                    bean.setOrderId(hjhPlanBorrowTmp.getOrderId());
                    boolean isOK = autoTenderExceptionService.updateCreditForAutoTender(credit, hjhAccede, hjhPlan, bean, borrowUserAccountId, sellerUsrcustid, resultMap);
                    if(isOK){
                        // 更改加入明细状态和投资临时表状态
                        autoTenderExceptionService.updateTenderByParam(orderStatus,hjhAccede.getId());
                        // 更新后重新查
                        credit =hjhCreditEndExceptionService.selectHjhDebtCreditByCreditNid(hjhPlanBorrowTmp.getBorrowNid());
                        if (credit == null) {
                            logger.error("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]" + "债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid());
                            return new AdminResult<>(FAIL, planOrderId+" 债转号不存在 结束债权 "+hjhPlanBorrowTmp.getBorrowNid());
                        }
                        // 只有不是接口成功表失败的情况才会退回队列
                        if(!BankCallConstant.RESPCODE_SUCCESS.equals(hjhPlanBorrowTmp.getRespCode())){
                            // 投资成功后减掉redis 钱
                            String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_CREDIT + RedisConstants.HJH_SLASH + hjhAccede.getPlanNid();
                            RedisBorrow redisBorrow = new RedisBorrow();
                            BigDecimal await = credit.getLiquidationFairValue().subtract(credit.getCreditPrice());
                            redisBorrow.setBorrowAccountWait(await);
                            redisBorrow.setBorrowNid(credit.getCreditNid());
                            logger.info(credit.getCreditNid()+" 更新表退回队列  "+await);
                            // 银行成功后，如果标的可投金额非0，推回队列的头部，标的可投金额为0，不再推回队列
                            if (await.compareTo(BigDecimal.ZERO) >= 0) {
                                String redisStr = JSON.toJSONString(redisBorrow);
                                logger.info("退回队列 " + queueName + redisStr);
                                RedisUtils.rightpush(queueName,redisStr);
                            }
                        }
                        /** 4.7. 完全承接时，结束债券  */
                        if (credit.getCreditAccountWait().compareTo(BigDecimal.ZERO) == 0) {
                            //获取出让人投标成功的授权号
                            String sellerAuthCode = hjhCreditEndExceptionService.getSellerAuthCode(credit.getSellOrderId(), credit.getSourceType());
                            if (sellerAuthCode == null) {
                                logger.info("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]未取得出让人" + credit.getUserId() + "的债权类型" +
                                        credit.getSourceType() + "(1原始0原始)的授权码，结束债权失败。");
                                return new AdminResult<>(FAIL, planOrderId+"(1原始0原始)的授权码，结束债权失败。");
                            }

                            //调用银行结束债权接口
                            boolean ret = hjhCreditEndExceptionService.requestDebtEnd(credit, sellerUsrcustid, sellerAuthCode);
                            if (!ret) {
                                logger.info("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]被承接标的" + hjhPlanBorrowTmp.getBorrowNid() +"被完全承接，银行结束债权失败。");
                            }
                            logger.info("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]被承接标的" + hjhPlanBorrowTmp.getBorrowNid() +"被完全承接，银行结束债权成功。");
                            //银行结束债权后，更新债权表为完全承接
                            ret = hjhCreditEndExceptionService.updateCreditForEnd(credit);
                            if (!ret) {
                                logger.info("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]银行结束债权后，更新债权表为完全承接失败。");
                            }
                        }
                    }
                }else if ("CA110112".equals(queryRetCode)){
                    // 更改加入明细状态和投资临时表状态
                    autoTenderExceptionService.updateTenderByParam(orderStatus,hjhAccede.getId());
                    // 只有不是接口成功表失败的情况才会退回队列
                    if(!BankCallConstant.RESPCODE_SUCCESS.equals(hjhPlanBorrowTmp.getRespCode())){
                        // 投资成功后减掉redis 钱
                        String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_CREDIT + RedisConstants.HJH_SLASH + hjhAccede.getPlanNid();
                        RedisBorrow redisBorrow = new RedisBorrow();
                        BigDecimal await = credit.getLiquidationFairValue().subtract(credit.getCreditPrice());
                        redisBorrow.setBorrowAccountWait(await);
                        redisBorrow.setBorrowNid(credit.getCreditNid());
                        logger.info(credit.getCreditNid()+" 更新表退回队列  "+await);
                        // 银行成功后，如果标的可投金额非0，推回队列的头部，标的可投金额为0，不再推回队列
                        if (await.compareTo(BigDecimal.ZERO) >= 0) {
                            String redisStr = JSON.toJSONString(redisBorrow);
                            logger.info("退回队列 " + queueName + redisStr);
                            RedisUtils.rightpush(queueName,redisStr);
                        }
                    }
                }else{
                    return new AdminResult<>(FAIL, planOrderId+" 暂时没有处理该异常，需要协调开发处理  "+ queryRetCode);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "汇计划投资异常记录列表导出", notes = "汇计划投资异常录列表导出")
    @PostMapping(value = "/exportregist")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody AutoTenderExceptionRequestBean autoTenderExceptionRequestBean) throws Exception {
        AutoTenderExceptionRequest autoTenderExceptionRequest = new AutoTenderExceptionRequest();
        BeanUtils.copyProperties(autoTenderExceptionRequestBean, autoTenderExceptionRequest);
        autoTenderExceptionRequest.setLimitFlg(true);
        // 表格sheet名称
        String sheetName = "加入明细";
        String fileName = URLEncoder.encode(sheetName) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        AutoTenderExceptionResponse autoTenderExceptionResponse = autoTenderExceptionService.selectAccedeRecordList(autoTenderExceptionRequest);
        List<AdminPlanAccedeListCustomizeVO> resultList  = new ArrayList<AdminPlanAccedeListCustomizeVO>();
        if(null!=autoTenderExceptionResponse){
            resultList = autoTenderExceptionResponse.getResultList();
        }
        String[] titles = new String[] { "序号","加入订单号", "计划编号","锁定期", "用户名", "加入金额", "已投资金额(元)","待还总额(元) ","待还本金(元) ","待还利息(元) ","操作平台","订单状态",  "计息时间", "加入时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (resultList != null && resultList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < resultList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }
                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    AdminPlanAccedeListCustomizeVO planAccedeDetail = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 计划订单号
                    else if (celLength == 1) {
                        cell.setCellValue(org.apache.commons.lang.StringUtils.isEmpty(planAccedeDetail.getPlanOrderId()) ? org.apache.commons.lang.StringUtils.EMPTY : planAccedeDetail.getPlanOrderId());
                    }
                    // 计划编号
                    else if (celLength == 2) {
                        cell.setCellValue(planAccedeDetail.getDebtPlanNid());
                    }
                    // 锁定期
                    else if (celLength == 3) {
                        cell.setCellValue(org.apache.commons.lang.StringUtils.isEmpty(planAccedeDetail.getDebtLockPeriod()) ? org.apache.commons.lang.StringUtils.EMPTY : planAccedeDetail.getDebtLockPeriod() + "天");
                    }
                    // 用户名
                    else if (celLength == 4) {
                        cell.setCellValue(org.apache.commons.lang.StringUtils.isEmpty(planAccedeDetail.getUserName()) ? org.apache.commons.lang.StringUtils.EMPTY : planAccedeDetail.getUserName());
                    }
                    // 加入金额
                    else if (celLength == 6) {
                        cell.setCellValue(org.apache.commons.lang.StringUtils.isEmpty(planAccedeDetail.getAccedeAccount()) ? org.apache.commons.lang.StringUtils.EMPTY : planAccedeDetail.getAccedeAccount());
                    }
                    // 已投资金额
                    else if (celLength == 7) {
                        cell.setCellValue(org.apache.commons.lang.StringUtils.isEmpty(planAccedeDetail.getAlreadyInvest()) ? org.apache.commons.lang.StringUtils.EMPTY : planAccedeDetail.getAlreadyInvest());
                    }
                    // 待收总额
                    else if (celLength == 8) {
                        cell.setCellValue(org.apache.commons.lang.StringUtils.isEmpty(planAccedeDetail.getWaitTotal()) ? org.apache.commons.lang.StringUtils.EMPTY : planAccedeDetail.getWaitTotal());
                    }
                    // 待收本金
                    else if (celLength == 9) {
                        cell.setCellValue(org.apache.commons.lang.StringUtils.isEmpty(planAccedeDetail.getWaitCaptical()) ? org.apache.commons.lang.StringUtils.EMPTY : planAccedeDetail.getWaitCaptical());
                    }
                    // 待收利息
                    else if (celLength == 10) {
                        cell.setCellValue(org.apache.commons.lang.StringUtils.isEmpty(planAccedeDetail.getWaitInterest()) ? org.apache.commons.lang.StringUtils.EMPTY : planAccedeDetail.getWaitInterest());
                    }

                    // 平台
                    else if (celLength == 11) {
                        if ("0".equals(planAccedeDetail.getPlatform())) {
                            cell.setCellValue("PC");
                        } else if ("1".equals(planAccedeDetail.getPlatform())) {
                            cell.setCellValue("微官网");
                        } else if ("2".equals(planAccedeDetail.getPlatform())) {
                            cell.setCellValue("android");
                        } else if ("3".equals(planAccedeDetail.getPlatform())) {
                            cell.setCellValue("ios");
                        }
                    }
                    // 订单状态
                    else if (celLength == 12) {
                        if (0 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("自动投标中");
                        } else if (2 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("自动投标成功");
                        } else if (3 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("锁定中");
                        } else if (5 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("退出中");
                        } else if (7 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("已退出");
                        } else if (99 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("自动投资异常");
                        }
                    }
                    // 计息时间
                    else if (celLength == 13) {
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(planAccedeDetail.getCountInterestTime())) {
                            cell.setCellValue(planAccedeDetail.getCountInterestTime());
                        }
                    }
                    // 加入时间
                    else if (celLength == 14) {
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(planAccedeDetail.getCreateTime())) {
                            cell.setCellValue(planAccedeDetail.getCreateTime());
                        }
                    }
                    // 预期年化
                    else if (celLength == 15) {
                        cell.setCellValue(org.apache.commons.lang.StringUtils.isEmpty(planAccedeDetail.getExpectApr()) ? org.apache.commons.lang.StringUtils.EMPTY : planAccedeDetail.getExpectApr() + "%");
                    }
                    // 用户属性（当前）
                    else if (celLength == 16) {
                        if ("0".equals(planAccedeDetail.getUserAttribute())) {
                            cell.setCellValue("无主单");
                        } else if ("1".equals(planAccedeDetail.getUserAttribute())) {
                            cell.setCellValue("有主单");
                        } else if ("2".equals(planAccedeDetail.getUserAttribute())) {
                            cell.setCellValue("线下员工");
                        } else if ("3".equals(planAccedeDetail.getUserAttribute())) {
                            cell.setCellValue("线上员工");
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

}
