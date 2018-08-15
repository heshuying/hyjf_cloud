package com.hyjf.cs.user.controller.api.bankopen;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.user.bean.ApiBankOpenRequestBean;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunss
 */
@Api(value = "第三方用户开户",tags = "api端-用户开户")
@Controller
@RequestMapping("/hyjf-api/user/open")
public class ApiBankOpenController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(ApiBankOpenController.class);

    @Autowired
    private BankOpenService bankOpenService;

    @Autowired
    SystemConfig systemConfig;

    @ApiOperation(value = "用户开户", notes = "用户开户")
    @PostMapping(value = "/openBankAccount", produces = "application/json; charset=utf-8")
    public ModelAndView openBankAccount(@RequestBody @Valid ApiBankOpenRequestBean requestBean , HttpServletRequest request) {
        logger.info("第三方请求页面开户, ApiBankOpenRequestBean is :{}", JSONObject.toJSONString(requestBean));
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> paramMap = bankOpenService.checkApiParam(requestBean);
        if("0".equals(paramMap.get("status"))){
            modelAndView.addObject("callBackForm", paramMap);
            return modelAndView;
        }
        UserVO user = this.bankOpenService.getUsersByMobile(requestBean.getMobile());
        OpenAccountPageBean openAccountPageBean = getOpenAccountPageBean(requestBean);
        openAccountPageBean.setUserId(user.getUserId());
        openAccountPageBean.setClientHeader(ClientConstants.CLIENT_HEADER_API);
        //modelAndView = bankOpenService.getOpenAccountMV(openAccountPageBean);
        //保存开户日志  银行卡号不必传了
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), requestBean.getMobile(), openAccountPageBean.getOrderId(), requestBean.getPlatform(), requestBean.getTrueName(), requestBean.getIdNo(), "");
        if (uflag == 0) {
            logger.info("保存开户日志失败,手机号:[" + requestBean.getMobile() + "],用户ID:[" + user.getUserId() + "]");
            modelAndView.addObject("callBackForm", paramMap);
            return modelAndView;
        }
        logger.info("开户end");
        return modelAndView;
    }

    private OpenAccountPageBean getOpenAccountPageBean(ApiBankOpenRequestBean requestBean) {
        OpenAccountPageBean bean = new OpenAccountPageBean();
        BeanUtils.copyProperties(requestBean,bean);
        // 同步调用路径
        String retUrl = systemConfig.getWebHost()
                + "/server/autoPlus/return?acqRes="
                + requestBean.getAcqRes() + "&callback=" + requestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl =systemConfig.getWebHost()
                + "/server/autoPlus/bgReturn?acqRes="
                +  requestBean.getAcqRes() + "&phone"+requestBean.getMobile()+"&callback=" + requestBean.getBgRetUrl().replace("#", "*-*-*");
        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        return bean;
    }

    /**
     * 第三方开户同步跳转地址
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "第三方端用户同步回调", notes = "api端-用户开户")
    @PostMapping(value = "/return")
    public Map<String, String> returnPage(HttpServletRequest request) {
        String isSuccess = request.getParameter("isSuccess");

        logger.info("第三方端开户同步请求,isSuccess:{}", isSuccess);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        if (isSuccess == null || !"1".equals(isSuccess)) {
            resultMap.put("status", "fail");
        } else {
            resultMap.put("status", "success");
        }
        logger.info("第三方端开户同步请求返回值：", JSONObject.toJSONString(resultMap));
        return resultMap;
    }

    /**
     * 页面开户异步处理
     *
     * @param bean
     * @return
     */
    @ApiOperation(value = "页面开户异步处理", notes = "页面开户异步处理")
    @PostMapping("/bgReturn")
    public BankCallResult openAccountBgReturn(BankCallBean bean, @RequestParam("phone") String mobile) {
        logger.info("开户异步处理start,userId:{}", bean.getLogUserId());
        bean.setMobile(mobile);
        BankCallResult result = bankOpenService.openAccountBgReturn(bean);
        return result;
    }

}