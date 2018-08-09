package com.hyjf.am.trade.controller.front.repay;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.RepayListResponse;
import com.hyjf.am.resquest.trade.ApiCronUpdateRequest;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequestUpdateRequest;
import com.hyjf.am.trade.bean.repay.RepayBean;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.service.front.repay.RepayManageService;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

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
        Response<BigDecimal> response = new Response<>();
        BigDecimal waitTotal = repayManageService.selectUserRepayFeeWaitTotal(userId);
        response.setResult(waitTotal);
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
    public Integer repayCount(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        Integer result = repayManageService.selectRepayCount(requestBean);
        return result;
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
    public Integer orgRepayCount(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        Integer result = repayManageService.selectOrgRepayCount(requestBean);
        return result;
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
    public Integer orgRepayedCount(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        Integer result = repayManageService.selectOrgRepayedCount(requestBean);
        return result;
    }

    /**
     * 获取用户还款列表
     * @param projectBean
     * @return
     */
    @RequestMapping(value = "/selectUserRepayedList")
    public RepayListResponse selectUserRepayedList(@RequestBody RepayListRequest projectBean){
        RepayListResponse response = new RepayListResponse();
        int recordTotal = this.repayManageService.countUserRepayedListTotal(projectBean);// 查询记录总数（个人和机构）
        if(recordTotal > 0){
            Paginator paginator = new Paginator(projectBean.getCurrPage(), recordTotal, projectBean.getPageSize());
            // 获取借款人用户待还款（或已还款 ）by role ,status的项目列表
            // 获取垫付机构用户待垫付（或已垫付 ）by role ,status的项目列表      ---->应该这个字段realAccountYes
            List<RepayListCustomizeVO> recordList = repayManageService.searchUserRepayList(projectBean, 0, recordTotal);
            response.setResultList(recordList);
            return response;
        }
        return null;
    }

    /**
     * 还款申请更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    @RequestMapping(value = "/update")
    public Boolean repayRequestUpdate(@RequestBody @Valid RepayRequestUpdateRequest requestBean){
        logger.info("还款申请更新开始，repuestBean: " + JSON.toJSONString(requestBean));

        if (requestBean == null || StringUtils.isBlank(requestBean.getRepayBeanData()) || StringUtils.isBlank(requestBean.getBankCallBeanData())){
            return false;
        }

        // 更新相关数据库表
        try {
            RepayBean repayBean = JSON.parseObject(requestBean.getRepayBeanData(), RepayBean.class);
            BankCallBean bankCallBean = JSON.parseObject(requestBean.getBankCallBeanData(), BankCallBean.class);

            boolean result = repayManageService.updateRepayMoney(repayBean, bankCallBean);
            if(!result){
                return false;
            }else {
                repayManageService.updateBorrowCreditStautus(repayBean.getBorrowNid());
            }
            return true;
        } catch (Exception e) {
            logger.error("还款申请更新数据库失败", e);
            return false;
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

}
