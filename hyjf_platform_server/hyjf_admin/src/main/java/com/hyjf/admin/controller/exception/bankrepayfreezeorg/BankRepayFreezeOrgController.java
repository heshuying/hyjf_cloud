package com.hyjf.admin.controller.exception.bankrepayfreezeorg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.repaybean.RepayBean;
import com.hyjf.admin.beans.request.BankRepayFreezeOrgCheckRequestBean;
import com.hyjf.admin.beans.request.BankRepayFreezeOrgProcessRequestBean;
import com.hyjf.admin.beans.request.RepayFreezeOrgRequestBean;
import com.hyjf.admin.beans.response.BankRepayFreezeOrgCheckResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.exception.BankRepayFreezeOrgService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.RepayFreezeOrgRequest;
import com.hyjf.am.vo.admin.BankRepayFreezeOrgCustomizeVO;
import com.hyjf.am.vo.trade.repay.BankRepayOrgFreezeLogVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 担保机构还款冻结异常处理
 * @author hesy
 * @version BankRepayFreezeOrgController, v0.1 2018/7/11 11:32
 */
@Api(value = "异常中心-担保机构还款冻结异常处理", tags = "异常中心-担保机构还款冻结异常处理")
@RestController
@RequestMapping("/hyjf-admin/exception/bankRepayFreezeOrg")
public class BankRepayFreezeOrgController extends BaseController {

    public static final String PERMISSIONS = "bankrepayFreezeOrg";

    @Autowired
    private AdminCommonService adminCommonService;
    @Autowired
    private BankRepayFreezeOrgService bankRepayFreezeOrgService;


    @ApiOperation(value = "担保机构还款冻结异常页面初始化", notes = "担保机构还款冻结异常页面初始化")
    @PostMapping(value = "/init")
    public AdminResult<Map<String,Object>> initPage() {
        List<DropDownVO> hjhInstConfigList = adminCommonService.selectHjhInstConfigList();

        Map<String,Object> data = new HashMap<>();
        data.put("instList", hjhInstConfigList);

        return new AdminResult<>(data);
    }

    @ApiOperation(value = "担保机构还款冻结异常列表", notes = "担保机构还款冻结异常列表")
    @PostMapping(value = "/list")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<BankRepayFreezeOrgCustomizeVO>> list(@RequestBody RepayFreezeOrgRequestBean requestBean) {
        RepayFreezeOrgRequest repayFreezeOrgRequest = new RepayFreezeOrgRequest();
        BeanUtils.copyProperties(requestBean,repayFreezeOrgRequest);
        Integer count = bankRepayFreezeOrgService.selectCount(repayFreezeOrgRequest);

        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        page.setTotal(count);
        repayFreezeOrgRequest.setLimitStart(page.getOffset());
        repayFreezeOrgRequest.setLimitEnd(page.getLimit());

        List<BankRepayFreezeOrgCustomizeVO> resultList = bankRepayFreezeOrgService.selectList(repayFreezeOrgRequest);
        return new AdminResult<>(ListResult.build(resultList, count));
    }

    /**
     * 冻结异常情况查询
     */
    @ApiOperation(value = "冻结异常情况查询", notes = "冻结异常情况查询")
    @ResponseBody
    @PostMapping(value = "/check")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<BankRepayFreezeOrgCheckResponseBean> checkRepayFreezeOrgAction(HttpServletRequest request, @RequestBody BankRepayFreezeOrgCheckRequestBean form) {
        AdminResult result = new AdminResult();
        BankRepayFreezeOrgCheckResponseBean responseBean = new BankRepayFreezeOrgCheckResponseBean();
        result.setData(responseBean);
        logger.info("请求参数：" + JSON.toJSONString(form));

        String orderId = form.getOrderId();
        String borrowNid = form.getBorrowNid();
        Integer currentPeriod = form.getCurrentPeriod();
        if (StringUtils.isBlank(orderId)) {
            logger.info("请求参数不全");
            result.setStatusInfo(AdminResult.FAIL, "参数错误，请稍后再试！");
            return result;
        }
        BankRepayOrgFreezeLogVO repayFreezeFlog = this.bankRepayFreezeOrgService.getBankRepayOrgFreezeLogList(orderId, borrowNid, currentPeriod);
        if (Validator.isNull(repayFreezeFlog)) {
            logger.info("处理失败，代偿冻结记录不存在");
            result.setStatusInfo(AdminResult.FAIL, "处理失败，代偿冻结记录不存在");
            return result;
        }
        if(repayFreezeFlog.getCreateTime() != null){
            responseBean.setCreateTimeInt(String.valueOf(GetDate.getMillis10(repayFreezeFlog.getCreateTime())));
        }
        BeanUtils.copyProperties(repayFreezeFlog, responseBean);
        BankCallBean bean = new BankCallBean();
        bean.setTxCode(BankCallConstant.TXCODE_BALANCE_FREEZE_QUERY);// 消息类型
        bean.setAccountId(repayFreezeFlog.getAccount());// 电子账号
        bean.setOrgOrderId(repayFreezeFlog.getOrderId());
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(repayFreezeFlog.getRepayUserId()));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId(String.valueOf(repayFreezeFlog.getRepayUserId())); // 操作者ID
        bean.setLogClient(0);
        // 调用接口
        BankCallBean callApiBg = BankCallUtils.callApiBg(bean);
        logger.info("【代偿冻结异常处理】单笔还款申请冻结查询银行返回：" + JSON.toJSONString(callApiBg));
        if (callApiBg != null) {
            responseBean.setRetCode(callApiBg.getRetCode());
            responseBean.setState(callApiBg.getState());
            responseBean.setMsg(callApiBg.getRetMsg());
            result.setStatusInfo(AdminResult.SUCCESS, StringUtils.isNotBlank(callApiBg.getRetMsg())?callApiBg.getRetMsg():AdminResult.SUCCESS_DESC);
        }
        return result;
    }

    /**
     * 冻结异常情况单个处理
     */
    @ApiOperation(value = "冻结异常情况处理", notes = "冻结异常情况处理")
    @ResponseBody
    @PostMapping("/process")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult bankAccountCheckAction(HttpServletRequest request, @RequestBody BankRepayFreezeOrgProcessRequestBean form) {
        logger.info("请求参数：" + JSON.toJSONString(form));
        AdminResult result = new AdminResult();
        String orderId = form.getOrderId();
        String borrowNid = form.getBorrowNid();
        Integer currentPeriod = form.getCurrentPeriod();
        if (StringUtils.isBlank(orderId) && StringUtils.isBlank(borrowNid)) {
            result.setStatusInfo(AdminResult.FAIL, "参数错误，请稍后再试！");
            return result;
        }
        BankRepayOrgFreezeLogVO repayFreezeFlog = this.bankRepayFreezeOrgService.getBankRepayOrgFreezeLogList(orderId, borrowNid, currentPeriod);
        if (Validator.isNull(repayFreezeFlog)) {
            logger.info("处理失败，代偿冻结记录不存在");
            result.setStatusInfo(AdminResult.FAIL, "处理失败，代偿冻结记录不存在");
            return result;
        }
        BeanUtils.copyProperties(repayFreezeFlog, form);

        BankCallBean callApiBg = new BankCallBean();
        if (BankCallConstant.RESPCODE_SUCCESS.equals(form.getRetCode()) && "0".equals(form.getState())) {
            return updateRepayMoney(form, callApiBg);
        } else if (BankCallConstant.RESPCODE_SUCCESS.equals(form.getRetCode()) && !"0".equals(form.getState())) {
            logger.info("【代偿冻结异常处理】订单号：{},未冻结状态,解除冻结！",orderId);
            bankRepayFreezeOrgService.deleteFreezeLogById(form.getId());
            RedisUtils.del("batchOrgRepayUserid_" + repayFreezeFlog.getRepayUserId());
            result.setStatusInfo(AdminResult.FAIL, "未冻结状态,解除冻结");
            return result;
        } else if (form.getCreateTimeInt() != null && GetDate.getNowTime10() < form.getCreateTimeInt() + 60 * 20) {
            logger.info("【代偿冻结异常处理】订单号：{},冻结时间不满20分钟，不予处理！",orderId);
            result.setStatusInfo(AdminResult.FAIL, "处理失败，请稍后再试！");
            return result;
        } else {
            BankCallBean bean = new BankCallBean();
            bean.setTxCode(BankCallConstant.TXCODE_BALANCE_FREEZE_QUERY);// 消息类型
            bean.setAccountId(repayFreezeFlog.getAccount());// 电子账号
            bean.setOrgOrderId(repayFreezeFlog.getOrderId());
            bean.setLogOrderId(GetOrderIdUtils.getUsrId(repayFreezeFlog.getRepayUserId()));
            bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
            bean.setLogUserId(String.valueOf(repayFreezeFlog.getRepayUserId())); // 操作者ID
            bean.setLogClient(0);
            // 调用接口
            callApiBg = BankCallUtils.callApiBg(bean);
            logger.info("单笔还款申请冻结查询银行返回：" + JSON.toJSONString(callApiBg));
            if (Validator.isNotNull(callApiBg)) {
                if (BankCallConstant.RESPCODE_SUCCESS.equals(callApiBg.getRetCode()) && !"0".equals(callApiBg.getState())) {
                    logger.info("【代偿冻结异常处理】订单号：{},未冻结状态,解除冻结！", orderId);
                    bankRepayFreezeOrgService.deleteFreezeLogById(form.getId());
                    RedisUtils.del("batchOrgRepayUserid_" + repayFreezeFlog.getRepayUserId());
                } else if (BankCallConstant.RESPCODE_SUCCESS.equals(callApiBg.getRetCode()) && "0".equals(callApiBg.getState())) {
                    return updateRepayMoney(form, callApiBg);
                } else {
                    logger.info("【代偿冻结异常处理】订单号：{},未冻结或已解冻状态,解除冻结！", orderId);
                    bankRepayFreezeOrgService.deleteFreezeLogById(form.getId());
                }
            } else {
                result.setStatusInfo(AdminResult.FAIL, "处理失败，请稍后再试！");
                return result;
            }
            result.setStatusInfo(AdminResult.SUCCESS, "处理成功");
            return result;
        }
    }

    /**
     * 处理冻结成功的状态
     *
     * @return
     */
    private AdminResult updateRepayMoney(BankRepayFreezeOrgProcessRequestBean form, BankCallBean callApiBg) {
        AdminResult result = new AdminResult();
        logger.info("【代偿冻结异常处理】已冻结，冻结订单号为:" + form.getOrderId());
        String borrowNid = form.getBorrowNid();
        Integer userId = form.getRepayUserId();
        String orderId = form.getOrderId();
        boolean isAllRepay = form.getAllRepayFlag() == 1;
        try {
            // 担保机构的还款
            RepayBean repay = bankRepayFreezeOrgService.getRepayBean(userId, "3", borrowNid, isAllRepay);
            if (repay != null) {
                // 还款后变更数据
                callApiBg.setOrderId(orderId);
                // 用于用户资金明细
                callApiBg.setTxDate(orderId.substring(0, 8));
                callApiBg.setTxTime(orderId.substring(8, 14));
                callApiBg.setSeqNo(orderId.substring(14));
                boolean updateResult = this.bankRepayFreezeOrgService.updateForRepayRequest(repay, callApiBg, isAllRepay);
                if (updateResult) {
                    bankRepayFreezeOrgService.deleteFreezeLogById(form.getId());
                    // 如果有正在出让的债权,先去把出让状态停止
                    this.bankRepayFreezeOrgService.updateBorrowCreditStautus(borrowNid);

                    //RedisUtils.del("batchOrgRepayUserid_" + form.getRepayUserId());
                    logger.info("【代偿冻结异常处理】担保机构:" + userId + "还款申请成功,标的号:" + borrowNid + ",订单号:" + orderId);
                } else {
                    logger.error("【代偿冻结异常处理】担保机构:" + userId + "还款更新数据失败,标的号:" + borrowNid + ",订单号:" + orderId);
                    result.setStatusInfo(AdminResult.FAIL, "还款更新数据失败");
                    return result;
                }
            } else {
                logger.info("【代偿冻结异常处理】获取担保机构:" + userId + "还款信息失败,标的号:" + borrowNid + ",订单号:" + orderId);
                result.setStatusInfo(AdminResult.FAIL, "还款信息失败");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("【代偿冻结异常处理】担保机构:" + userId + "更新异常,标的号:" + borrowNid + ",订单号:" + orderId);
            result.setStatusInfo(AdminResult.FAIL, "更新异常");
            return result;
        }
        result.setStatusInfo(AdminResult.SUCCESS, "处理成功");
        return result;
    }

}
