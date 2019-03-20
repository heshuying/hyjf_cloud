package com.hyjf.cs.user.controller.api.bankopen;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.user.bean.ApiBankOpenRequestBean;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunss
 */
@Api(value = "第三方用户开户", tags = "api端-用户开户")
@Controller
@RequestMapping("/hyjf-api/server/user/accountOpenEncryptPage")
public class ApiBankOpenController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(ApiBankOpenController.class);

    @Autowired
    private BankOpenService bankOpenService;

    @Autowired
    SystemConfig systemConfig;

    @ApiOperation(value = "用户开户", notes = "用户开户")
    @PostMapping(value = "/open.do", produces = "application/json; charset=utf-8")
    @HystrixCommand(commandKey = "开户(api)-openBankAccount", fallbackMethod = "fallBackApiBankOpen", ignoreExceptions = CheckException.class, commandProperties = {
            //设置断路器生效
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //一个统计窗口内熔断触发的最小个数3/10s
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            //熔断5秒后去尝试请求
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30")})
    public ModelAndView openBankAccount(@RequestBody @Valid ApiBankOpenRequestBean requestBean) {
        logger.info("第三方请求页面开户, ApiBankOpenRequestBean is :{}", JSONObject.toJSONString(requestBean));
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> paramMap = bankOpenService.checkApiParam(requestBean);
        paramMap.put("callBackAction", requestBean.getRetUrl());
        if ("0".equals(paramMap.get("status"))) {
            return callbackErrorView(paramMap);
        }
        UserVO user = this.bankOpenService.getUsersByMobile(requestBean.getMobile());
        OpenAccountPageBean openAccountPageBean = getOpenAccountPageBean(requestBean);
        openAccountPageBean.setUserId(user.getUserId());
        openAccountPageBean.setClientHeader(ClientConstants.CLIENT_HEADER_API);

        try {
            BankCallBean bean = getCallbankMV(openAccountPageBean);
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        //保存开户日志  银行卡号不必传了
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), requestBean.getMobile(),
                openAccountPageBean.getOrderId(), requestBean.getPlatform(), requestBean.getTrueName(), requestBean.getIdNo(), "", "");
        if (uflag == 0) {
            logger.info("保存开户日志失败,手机号:[" + requestBean.getMobile() + "],用户ID:[" + user.getUserId() + "]");
            paramMap.put("status", ErrorCodeConstant.STATUS_CE999999);
            paramMap.put("statusDesc", "请求银行错误");
            return callbackErrorView(paramMap);
        }
        logger.info("开户end");
        return modelAndView;
    }

    public BankCallBean getCallbankMV(OpenAccountPageBean openBean) {
        // 根据身份证号码获取性别
        String idType = BankCallConstant.ID_TYPE_IDCARD;
        // 调用开户接口
        BankCallBean openAccoutBean = new BankCallBean(openBean.getUserId(), BankCallConstant.TXCODE_ACCOUNT_OPEN_ENCRYPT_PAGE,
                Integer.parseInt(openBean.getPlatform()), BankCallConstant.BANK_URL_ACCOUNT_OPEN_ENCRYPT_PAGE);
        openAccoutBean.setIdentity(openBean.getIdentity());
        /**1：出借角色2：借款角色3：代偿角色*/
        openAccoutBean.setChannel(openBean.getChannel());
        openAccoutBean.setIdType(idType);
        openAccoutBean.setName(openBean.getTrueName());
        openAccoutBean.setGender(openBean.getGender());
        openAccoutBean.setMobile(openBean.getMobile());
        // 代偿角色的账户类型为  00100-担保账户  其他的是 00000-普通账户
        if (openBean.getIdentity().equals("3")) {
            openAccoutBean.setAcctUse(BankCallConstant.ACCOUNT_USE_GUARANTEE);
        } else {
            openAccoutBean.setAcctUse(BankCallConstant.ACCOUNT_USE_COMMON);
        }

        openAccoutBean.setIdentity(openBean.getIdentity());
        // 同步地址  是否跳转到前端页面
        openAccoutBean.setRetUrl(openBean.getRetUrl());
        openAccoutBean.setSuccessfulUrl(openBean.getSuccessfulUrl());
        openAccoutBean.setNotifyUrl(openBean.getNotifyUrl());
        openAccoutBean.setCoinstName(openBean.getCoinstName() == null ? "汇盈金服" : openBean.getCoinstName());
        openAccoutBean.setLogRemark("开户+设密码页面");
        openAccoutBean.setLogIp(openBean.getIp());
        openBean.setOrderId(openAccoutBean.getLogOrderId());
        return openAccoutBean;
    }


    public ModelAndView fallBackApiBankOpen(@RequestBody @Valid ApiBankOpenRequestBean requestBean) {
        logger.info("==================已进入 开户(api) fallBackApiBankOpen 方法================");
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("status", ErrorCodeConstant.STATUS_CE999999);
        paramMap.put("statusDesc", "开户失败");
        return callbackErrorView(paramMap);
    }

    private OpenAccountPageBean getOpenAccountPageBean(ApiBankOpenRequestBean requestBean) {
        OpenAccountPageBean bean = new OpenAccountPageBean();
        BeanUtils.copyProperties(requestBean, bean);
        String retUrl = systemConfig.getServerHost() + "/hyjf-api/server/user/accountOpenEncryptPage";
        String successUrl = systemConfig.getServerHost() + "/hyjf-api/server/user/accountOpenEncryptPage";
        // 异步调用路
        String bgRetUrl = "http://CS-USER/hyjf-api/server/user/accountOpenEncryptPage";
        // 调用设置密码接口
        retUrl += "/return?acqRes=" + requestBean.getAcqRes() + "&callback=" + requestBean.getRetUrl().replace("#", "*-*-*");
        successUrl += "/return?acqRes=" + requestBean.getAcqRes() + "&isSuccess=1&callback=" + requestBean.getRetUrl().replace("#", "*-*-*");
        bgRetUrl += "/bgReturn?acqRes=" + requestBean.getAcqRes() + "&phone=" + requestBean.getMobile() + "&openclient=" + requestBean.getPlatform() + "&roleId=" + requestBean.getIdentity() +
                "&callback=" + requestBean.getBgRetUrl().replace("#", "*-*-*");
        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        bean.setSuccessfulUrl(successUrl);
        return bean;
    }

    /**
     * 第三方开户同步跳转地址
     *
     * @param request
     * @return
     */
    @ApiIgnore
    @ApiOperation(value = "第三方端用户同步回调", notes = "用户开户")
    @RequestMapping(value = "/return")
    public ModelAndView returnPage(HttpServletRequest request) {
        String isSuccess = request.getParameter("isSuccess");
        String url = request.getParameter("callback").replace("*-*-*", "#");
        String phone = request.getParameter("phone");
        logger.info("第三方端开户同步请求,isSuccess:{}", isSuccess);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        resultMap.put("callBackAction", url);
        if (isSuccess == null || !"1".equals(isSuccess)) {
            // 失败
            resultMap.put("status", ErrorCodeConstant.STATUS_CE999999);
            resultMap.put("statusDesc", "开户失败,调用银行接口失败");
            resultMap.put("acqRes", request.getParameter("acqRes"));
            return callbackErrorView(resultMap);
        } else {
            resultMap.put("status", ErrorCodeConstant.SUCCESS);
            resultMap.put("status", "页面开户成功");
            resultMap.put("phone", phone);
            return callbackErrorView(resultMap);
        }
    }

    /**
     * 页面开户异步处理
     *
     * @param bean
     * @return
     */
    @ApiIgnore
    @ApiOperation(value = "页面开户异步处理", notes = "页面开户异步处理")
    @PostMapping("/bgReturn")
    @ResponseBody
    public BankCallResult openAccountBgReturn(HttpServletRequest request,
                                              @RequestBody BankCallBean bean,
                                              @RequestParam("phone") String mobile,
                                              @RequestParam("roleId") String roleId,
                                              @RequestParam("openclient") String openclient) {
        logger.info("开户异步处理start,userId:{}", bean.getLogUserId());
        // 查询用
        bean.setRemark(bean.getMobile());
        bean.setMobile(mobile);
        bean.setLogClient(Integer.parseInt(openclient));
        bean.setIdentity(roleId);
        BankCallResult result = new BankCallResult();
        String retCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone", mobile);
        params.put("acqRes", request.getParameter("acqRes"));
        BaseResultBean resultBean = new BaseResultBean();
        try {
            result = bankOpenService.openAccountBgReturn(bean);
        } catch (Exception e) {
            logger.error("出错了", e);
            params.put("status", ErrorCodeConstant.STATUS_CE999999);
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            params.put("statusDesc", "开户失败,调用银行接口失败");
            params.put("chkValue", resultBean.getChkValue());
            result.setStatus(false);
            CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*", "#"), params);
            return result;
        }
        // 开户失败
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode) ) {
            if("0".equals(bean.getStatus())) {
                params.put("status", ErrorCodeConstant.STATUS_CE999999);
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                params.put("statusDesc", "开户失败,调用银行接口失败");
                params.put("chkValue", resultBean.getChkValue());
                params.put("openStatus", "0");
                result.setStatus(true);
            }else{
                params.put("status", ErrorCodeConstant.SUCCESS);
                resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
                params.put("statusDesc", "开户成功");
                params.put("chkValue", resultBean.getChkValue());
                params.put("accountId", bean.getAccountId());
                params.put("payAllianceCode", bean.getPayAllianceCode());
                params.put("idNo", bean.getIdNo());
                params.put("isOpenAccount", "1");
                params.put("openStatus", "2");
                result.setStatus(true);
            }
        } else {
            params.put("status", ErrorCodeConstant.SUCCESS);
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            params.put("statusDesc", "开户成功");
            params.put("chkValue", resultBean.getChkValue());
            params.put("accountId", bean.getAccountId());
            params.put("payAllianceCode", bean.getPayAllianceCode());
            params.put("idNo", bean.getIdNo());
            params.put("isOpenAccount", "1");
            // 交易状态0：交易失败
            //1：交易成功
            //2：开户成功设置交易密码失败
            params.put("openStatus", "1");
            result.setStatus(true);
        }
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*", "#"), params);
        return result;
    }

}