package com.hyjf.cs.user.controller.app.bindcard;

import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.BaseResultBeanFrontEnd;
import com.hyjf.cs.user.result.BindBeanDeatail;
import com.hyjf.cs.user.result.BindCardResultBean;
import com.hyjf.cs.user.result.SendSmsResultBean;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * App端绑卡(接口)
 * @author hesy
 * @version AppBindCardController, v0.1 2018/7/19 9:34
 */
@Api(value = "app端-绑卡（接口）",tags = "app端-绑卡（接口）")
@RestController
@RequestMapping("/hyjf-app/user/bankCard")
public class AppBindCardController extends BaseUserController {
    /** 绑卡错误页面 */
    public static final String JUMP_HTML_ERROR_PATH = "/user/bankCard/bind/result/failed";
    /** 绑卡成功页面 */
    public static final String JUMP_HTML_SUCCESS_PATH = "/user/bankCard/bind/result/success";
    /** 绑卡处
     * 理中页面 */
    public static final String JUMP_HTML_HANDLING_PATH = "/user/bankCard/bind/result/handing";

    @Autowired
    BindCardService bindCardService;
    @Autowired
    SystemConfig systemConfig;

    @GetMapping("/bind_data")
    @ApiOperation(value = "获取绑卡数据", notes = "获取绑卡数据")
    public BaseResultBeanFrontEnd getBindCardData(@RequestHeader(value = "userId", required = false) Integer userId, HttpServletRequest request){
        BindCardResultBean resultBean = new BindCardResultBean();
//        resultBean.setStatus(BindCardBean.SUCCESS);
//        resultBean.setStatusDesc(BindCardBean.SUCCESS_MSG);

        logger.info("获取绑卡数据接口开始，userId：" + userId);

//        Integer userId = Integer.valueOf(request.getParameter("userId"));


        if(userId == null){
            resultBean.setStatus(CustomConstants.APP_STATUS_FAIL);
            resultBean.setStatusDesc("用户未登录");
            return resultBean;
        }


        BindBeanDeatail beanDetail = new BindBeanDeatail();
        try {
            // 查询用户信息获取真实姓名和银行卡和手机号码
//            List<BankCard> bankCardList= userBindCardService.getAccountBankByUserId(String.valueOf(userId));
            UserVO users = bindCardService.getUsersById(userId);
            UserInfoVO userInfoVO = bindCardService.getUserInfo(userId);
            if(userInfoVO != null){
                String idcard = userInfoVO.getIdcard().substring(0, 3) + "***********" + userInfoVO.getIdcard().substring(userInfoVO.getIdcard().length() - 4);
                userInfoVO.setIdcard(idcard);

                //获取实名信息
                String trueName = userInfoVO.getTruename();
                userInfoVO.setTruename(trueName.replaceFirst(trueName.substring(0,1),"*"));
            }

            if (users != null) {
//                BankCard bankCard = bankCardList.get(0);
                beanDetail.setTelNo(users.getMobile());
            } else {
                resultBean.setStatus(BindCardResultBean.FAIL);
                resultBean.setStatusDesc(BindCardResultBean.FAIL_MSG);
            }

            if (userInfoVO != null) {
                beanDetail.setUserName(userInfoVO.getTruename());
                beanDetail.setUserCardId(userInfoVO.getIdcard());
            } else {
                resultBean.setStatus(BindCardResultBean.FAIL);
                resultBean.setStatusDesc(BindCardResultBean.FAIL_MSG);
            }

        } catch (Exception e) {
            resultBean.setStatus(BindCardResultBean.FAIL);
            resultBean.setStatusDesc(BindCardResultBean.FAIL_MSG);
        }
        resultBean.setFormData(beanDetail);
        resultBean.setStatus(BindCardResultBean.SUCCESS);
        resultBean.setStatusDesc(BindCardResultBean.SUCCESS_MSG);

        return resultBean;
    }

    /**
     * 绑定银行卡发送短信验证码
     * @param request
     * @return
     */
    @GetMapping("/bind/smscode")
    @ApiOperation(value = "绑卡发送验证码", notes = "绑卡发送验证码")
    public BaseResultBeanFrontEnd sendSmsCode(@RequestHeader(value = "userId", required = false) Integer userId, HttpServletRequest request) {
        SendSmsResultBean result = new SendSmsResultBean();

        logger.info("绑卡发送验证码接口开始：userId：" + userId);
        WebViewUserVO webViewUserVO = bindCardService.getWebViewUserByUserId(userId, BankCallConstant.CHANNEL_APP);

        if (webViewUserVO == null) {
            result.setStatus(SendSmsResultBean.FAIL);
            result.setStatusDesc("用户未登录");
            return result;
        }

        String mobile = request.getParameter("mobile"); // 手机号
        if (StringUtils.isEmpty(mobile)) {
            result.setStatus(SendSmsResultBean.FAIL);
            result.setStatusDesc("手机号不能为空");
            return result;
        }

        String cardNo = request.getParameter("cardNo"); // 银行卡号
        if (StringUtils.isEmpty(cardNo)) {
            result.setStatus(SendSmsResultBean.FAIL);
            result.setStatusDesc("银行卡号不能为空");
            return result;
        }
        // 请求发送短信验证码
        BankCallBean bean = bindCardService.callSendCode(userId,mobile, BankCallMethodConstant.TXCODE_CARD_BIND_PLUS, ClientConstants.CHANNEL_APP,cardNo);
        if (bean == null) {
            result.setStatus(SendSmsResultBean.FAIL);
            result.setStatusDesc("发送短信验证码异常");
            return result;
        }
        // 返回失败
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
            if("JX900651".equals(bean.getRetCode())){


                result.setStatus(SendSmsResultBean.SUCCESS);
                result.setStatusDesc(SendSmsResultBean.SUCCESS_MSG);
                result.setSrvAuthCode(bean.getSrvAuthCode());
                return result;
            }
            result.setStatus(SendSmsResultBean.FAIL);
            result.setStatusDesc("发送短信验证码失败，失败原因：" + bindCardService.getBankRetMsg(bean.getRetCode()));
            return result;
        }
        result.setStatus(SendSmsResultBean.SUCCESS);
        result.setStatusDesc(SendSmsResultBean.SUCCESS_MSG);
        result.setSrvAuthCode(bean.getSrvAuthCode());
        return result;
    }

    /**
     * 组装重定向url
     * @auther: hesy
     * @date: 2018/8/15
     */
    private String getRedirectUrl(String url, Map<String,String> paraMap){
        StringBuilder resultUrl = new StringBuilder("redirect:" + systemConfig.AppFrontHost + url + "?");
        try {
            for(Map.Entry<String, String> entry : paraMap.entrySet()){
                resultUrl.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8")).append("&");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("组装重定向url异常", e);
        }
        logger.info("重定向地址：" + resultUrl.toString());
        return resultUrl.toString();
    }

    private ModelAndView getErrorModel(String desc){
        Map<String,String> paraMap=new HashMap<>();
        paraMap.put(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
        paraMap.put(CustomConstants.APP_STATUS_DESC, desc);
        return new ModelAndView(getRedirectUrl(JUMP_HTML_ERROR_PATH, paraMap));
    }
    /**
     * 用户绑卡
     *
     * @param request
     * @return
     */
    @PostMapping("/bind")
    @ApiOperation(value = "用户绑卡", notes = "用户绑卡")
    public ModelAndView bindCardPlus(@RequestHeader(value = "userId", required = false) Integer userId, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        logger.info("用户绑卡开始：userId:" + userId);
        WebViewUserVO webViewUserVO = bindCardService.getWebViewUserByUserId(userId, BankCallConstant.CHANNEL_APP);
        if (webViewUserVO == null) {
            return getErrorModel("用户未登录");
        }
        // 唯一标识
        String sign = request.getParameter("sign");
        String cardNo = request.getParameter("cardNo");
        if (Validator.isNull(cardNo)) {
            return getErrorModel("获取银行卡号为空");
        }
        if (userId == null || userId == 0) {
            return getErrorModel("用户未登录");
        }
        // 检查验证码是否正确
        String code = request.getParameter("code");
        logger.info("输入验证码code is: {}", code);
        if (Validator.isNull(code)) {
            return getErrorModel("验证码无效");
        }

        // 检查验证码是否正确
        String lastSrvAuthCode = request.getParameter("lastSrvAuthCode");
        logger.info("输入验证码lastSrvAuthCode is: {}", lastSrvAuthCode);
        if (Validator.isNull(lastSrvAuthCode)) {
            return getErrorModel("请先发送短信");
        }

        String mobile = request.getParameter("telNo");
        if (Validator.isNull(mobile)) {
            return getErrorModel("手机号不能为空");
        }
        // 取得用户在汇付天下的客户号
        if (Validator.isNull(webViewUserVO.getBankAccount())) {
            return getErrorModel("用户未开户");
        }
        // 调用汇付接口(4.2.2 用户绑卡接口)
        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogUserId(StringUtil.valueOf(userId));
        bean.setLogRemark("用户绑卡增强");
        bean.setTxCode(BankCallConstant.TXCODE_CARD_BIND_PLUS);
        bean.setChannel(BankCallConstant.CHANNEL_APP);// 交易渠道
        bean.setAccountId(webViewUserVO.getBankAccount());// 存管平台分配的账号
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);// 证件类型01身份证
        bean.setIdNo(webViewUserVO.getIdcard());// 证件号
        bean.setName(webViewUserVO.getTruename());// 姓名
        bean.setMobile(mobile);// 手机号
        bean.setCardNo(cardNo);// 银行卡号
        bean.setLastSrvAuthCode(lastSrvAuthCode);
        bean.setSmsCode(code);
        bean.setUserIP(GetCilentIP.getIpAddr(request));// 客户IP
        LogAcqResBean logAcq = new LogAcqResBean();
        logAcq.setCardNo(cardNo);
        bean.setLogAcqResBean(logAcq);
        BankCallBean retBean=null;
        // 跳转到江西银行画面
        try {
            retBean = BankCallUtils.callApiBg(bean);
        } catch (Exception e) {
            logger.error("调用银行接口失败", e);
            return getErrorModel("调用银行接口失败");

        }

        // 回调数据处理
        if (retBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode()))) {
            // 执行结果(失败)
            String message = "";
            if(retBean==null){
                message = "绑卡失败，请重试";
            }else{
                message = this.bindCardService.getBankRetMsg(retBean.getRetCode());
            }

            return getErrorModel("失败原因:" + message);
        }

        try {
            // 绑卡后处理
            this.bindCardService.updateAfterBindCard(bean);
            BankCardVO bankCardVO = bindCardService.queryUserCardValid(bean.getLogUserId(), bean.getCardNo());

            if (bankCardVO != null) {
                Map<String,String> paraMap=new HashMap<>();
                paraMap.put(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                paraMap.put(CustomConstants.APP_STATUS_DESC, "");
                return new ModelAndView(getRedirectUrl(JUMP_HTML_SUCCESS_PATH, paraMap));
            } else {
                Map<String,String> paraMap=new HashMap<>();
                paraMap.put(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                paraMap.put(CustomConstants.APP_STATUS_DESC, "银行处理中，请稍后查看");
                return new ModelAndView(getRedirectUrl(JUMP_HTML_HANDLING_PATH, paraMap));
            }

        } catch (Exception e) {
            // 执行结果(失败)
            logger.error(e.getMessage());
            return getErrorModel("执行失败");
        }

    }
}
