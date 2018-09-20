package com.hyjf.am.trade.controller.front.repay;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.trade.RepayListResponse;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.trade.bean.repay.ProjectBean;
import com.hyjf.am.trade.bean.repay.RepayBean;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.service.front.repay.RepayManageService;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
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
    public Response<BigDecimal> userRepayFeeWaitTotal(@PathVariable Integer userId) {
        logger.info("普通借款人管理费总待还feewait_total_user，userId：" + userId);
        Response<BigDecimal> response = new Response<>();
        BigDecimal waitTotal = repayManageService.selectUserRepayFeeWaitTotal(userId);
        response.setResult(waitTotal);
        logger.info("response: {}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 担保机构管理费总待还
     * @return
     */
    @RequestMapping(value = "/feewait_total_org/{userId}")
    public Response<BigDecimal> orgRepayFeeWaitTotal(@PathVariable Integer userId) {
        Response<BigDecimal> response = new Response<>();
        BigDecimal waitTotal = repayManageService.selectOrgRepayFeeWaitTotal(userId);
        if(null==waitTotal){
            response.setResult(new BigDecimal(0));
        }
        response.setResult(waitTotal);
        return response;
    }

    /**
     * 担保机构待还
     * @param userId
     * @return
     */
    @RequestMapping(value = "/repaywait_total_org/{userId}")
    public Response<BigDecimal> orgRepayWaitTotal(@PathVariable Integer userId) {
        Response<BigDecimal> response = new Response<>();
        BigDecimal waitTotal = repayManageService.selectRepayOrgRepaywait(userId);
        response.setResult(waitTotal);
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
     * 还款详情页面数据获取
     * @param requestBean
     * @return
     */
    @PostMapping(value = "/repay_detail")
    public StringResponse repayDetail(@RequestBody @Valid RepayRequestDetailRequest requestBean){
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
            Borrow borrow = repayManageService.getBorrow(borrowNid);
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

        logger.info("计算完的还款bean数据：{}", JSON.toJSONString(repayByTerm));
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
}
