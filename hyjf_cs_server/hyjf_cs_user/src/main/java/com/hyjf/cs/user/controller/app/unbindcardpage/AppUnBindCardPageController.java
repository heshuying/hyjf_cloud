package com.hyjf.cs.user.controller.app.unbindcardpage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.bean.DeleteCardPageBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.web.bindcard.WebBindCardPageController;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.unbindcard.UnBindCardService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 合规四期-解绑银行卡
 *
 * @author nxl
 * @version WebUnBindCardPageController, v0.1 2018/10/15 14:26
 */
@Api(value = "app端-用户解绑卡接口(页面调用)", tags = "app端-用户解绑卡接口(页面调用)")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-app/bank/app/deleteCardPage")
public class AppUnBindCardPageController extends BaseUserController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WebBindCardPageController.class);

    @Autowired
    UnBindCardService unBindCardService;
    @Autowired
    SystemConfig systemConfig;
    @Autowired
    CommonProducer commonProducer;


    @PostMapping("/deleteCard")
    @ApiOperation(value = "解绑银行卡接口页面", notes = "解绑银行卡接口页面")
    public JSONObject getCashUrl(HttpServletRequest request, HttpServletResponse response, @RequestHeader(value = "userId") Integer userId) {
        JSONObject ret = new JSONObject();
        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // 唯一标识
        String sign = request.getParameter("sign");
        // token
        String token = request.getParameter("token");
        // order
        String order = request.getParameter("order");
        // card 银行卡号
        String cardNo = request.getParameter("bankNumber");// 银行卡号

        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(token) || Validator.isNull(sign) || Validator.isNull(randomString)
                || Validator.isNull(order) || Validator.isNull(cardNo)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        if (userId == null || userId <= 0) {
            ret.put("status", "1");
            ret.put("statusDesc", "用户未登录！");
            return ret;
        }
        UserVO user = unBindCardService.getUsersById(userId);
        UserInfoVO userInfoVO = unBindCardService.getUserInfo(userId);
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE11);
        userOperationLogEntity.setIp(com.hyjf.common.util.GetCilentIP.getIpAddr(request));
        userOperationLogEntity.setPlatform(Integer.valueOf(platform));
        userOperationLogEntity.setRemark("");
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(user.getUsername());
        userOperationLogEntity.setUserRole(String.valueOf(userInfoVO.getRoleId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        // 取得用户的客户号
        BankOpenAccountVO accountChinapnrTender = unBindCardService.getBankOpenAccount(userId);
        if (accountChinapnrTender == null || org.apache.commons.lang3.StringUtils.isEmpty(accountChinapnrTender.getAccount())) {
            ret.put("status", "1");
            ret.put("statusDesc", "用户未开户！");
            return ret;
        }
        // 用户余额大于零不让解绑
        AccountVO account = unBindCardService.getAccountByUserId(userId);
        // 用户在银行的账户余额
        BigDecimal bankBalance = unBindCardService.queryBankBlance(userId, accountChinapnrTender.getAccount());
        if ((Validator.isNotNull(account.getBankBalance()) && account.getBankBalance().compareTo(BigDecimal.ZERO) > 0)
                || ((Validator.isNotNull(bankBalance) && bankBalance.compareTo(BigDecimal.ZERO) > 0))
                || (Validator.isNotNull(account.getBankTotal()) && account.getBankTotal().compareTo(BigDecimal.ZERO) > 0)) {
            ret.put("status", "1");
            ret.put("statusDesc", "抱歉，银行卡解绑错误，请联系客服！");
            return ret;
        }
        // 根据银行卡Id获取用户的银行卡信息
        BankCardVO bankCardVO = unBindCardService.queryUserCardValid(userId.toString(), cardNo);
        if (bankCardVO == null || StringUtils.isEmpty(bankCardVO.getCardNo())) {
            ret.put("status", "1");
            ret.put("statusDesc", "获取用户银行卡信息失败！");
            return ret;
        }
        try {
            ret.put("status", "0");
            ret.put("statusDesc", "成功");
            String RECHARGE_URL = super.getFrontHost(systemConfig, platform) + "/public/formsubmit?requestType=" + CommonConstant.APP_BANK_REQUEST_TYPE_UNBINDCARD;
            StringBuffer sbUrl = new StringBuffer(RECHARGE_URL);
            sbUrl.append("&").append("version").append("=").append(version);
            sbUrl.append("&").append("netStatus").append("=").append(netStatus);
            sbUrl.append("&").append("platform").append("=").append(platform);
            sbUrl.append("&").append("randomString").append("=").append(randomString);
            sbUrl.append("&").append("sign").append("=").append(sign);
            sbUrl.append("&").append("token").append("=").append(strEncode(token));
            sbUrl.append("&").append("order").append("=").append(strEncode(order));
            sbUrl.append("&").append("bankNumber").append("=").append(cardNo);
            logger.info("返回的解卡url为: {}", sbUrl.toString());
            ret.put("url", sbUrl.toString());
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "获取解卡URL失败");
        }
        return ret;
    }

    /**
     * 绑卡接口
     */
    @PostMapping("/deleteCardPage")
    @ApiOperation(value = "解绑银行卡接口页面", notes = "解绑银行卡接口页面")
    public AppResult<Object> bindCardPage(@RequestHeader(value = "userId") Integer userId, @RequestParam(value = "sign") String sign, @RequestParam(value = "bankNumber") String bankNumber, HttpServletRequest request) {
        // 平台
        String platform = request.getParameter("platform");
        AppResult<Object> result = new AppResult<Object>();
        if (userId == null) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
        if (StringUtils.isBlank(bankNumber)) {
            throw new ReturnMessageException(MsgEnum.STATUS_ZC000009);
        }
        WebViewUserVO user = unBindCardService.getUserFromCache(userId);
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE11);
        userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
        userOperationLogEntity.setPlatform(Integer.valueOf(platform));
        userOperationLogEntity.setRemark("");
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(user.getUsername());
        userOperationLogEntity.setUserRole(String.valueOf(user.getRoleId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        // 取得用户在汇付天下的客户号
        BankOpenAccountVO accountChinapnrTender = unBindCardService.getBankOpenAccount(userId);
        // 根据用户id查找账户信息管理
        AccountVO accountVO = unBindCardService.getAccountByUserId(userId);
        // 根据银行卡Id获取用户的银行卡信息
        BankCardVO bankCardVO = unBindCardService.queryUserCardValid(userId.toString(), bankNumber);
        // 条件校验
        unBindCardService.checkParamUnBindCardPage(user, accountChinapnrTender, accountVO, bankCardVO);
        //获取用户info信息
        UserInfoVO userInfoVO = unBindCardService.getUserInfo(user.getUserId());
        // 异步调用路
        String bgRetUrl = "http://CS-USER/hyjf-app/bank/app/deleteCardPage/bgReturn?userId=" + user.getUserId();
        DeleteCardPageBean deleteCardPageBean = new DeleteCardPageBean();
        deleteCardPageBean.setUserId(user.getUserId());
        deleteCardPageBean.setAccountId(accountChinapnrTender.getAccount());
        deleteCardPageBean.setName(userInfoVO.getTruename());
        deleteCardPageBean.setIdNo(userInfoVO.getIdcard());
        deleteCardPageBean.setCardNo(bankCardVO.getCardNo());// 银行卡号
        deleteCardPageBean.setMobile(user.getMobile());
        deleteCardPageBean.setNotifyUrl(bgRetUrl);
        deleteCardPageBean.setPlatform(request.getParameter("platform"));
        //调用解绑银行卡接口
        Map<String, Object> data = unBindCardService.callUnBindCardPage(deleteCardPageBean, BankCallConstant.CHANNEL_APP, sign, request);
        result.setStatus(BaseResult.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 绑卡异步回调
     *
     * @param bean
     * @param request
     * @return
     */
    @ApiOperation(value = "绑卡接口回调", notes = "绑卡接口回调")
    @PostMapping(value = "/bgReturn")
    @ResponseBody
    public BankCallResult bindCardBgReturn(@RequestBody BankCallBean bean, HttpServletRequest request) {

        BankCallResult result = new BankCallResult();
        logger.info("app端页面解卡异步回调start");
        int userId = Integer.parseInt(bean.getLogUserId());
        // 绑卡后处理
        try {
            if (BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
                logger.info("app端删除银行卡成功");
                // 删除银行卡信息
                unBindCardService.updateAfterUnBindCard(bean, userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("app端页面解卡成功,用户ID:[" + userId + ",用户电子账户号:[" + bean.getAccountId() + "]");
        result.setStatus(true);
        return result;
    }

    /**
     * @Description 调用银行失败原因
     * @Author
     */
    @ApiOperation(value = "调用银行失败原因", notes = "查询调用银行失败原因")
    @PostMapping("/searchFiledMess")
    @ApiImplicitParam(name = "param", value = "{logOrdId:String}", dataType = "Map")
    @ResponseBody
    public WebResult<Object> searchFiledMess(@RequestBody Map<String, String> param) {
        logger.info("调用银行失败原因start,logOrdId:{}", param);
        WebResult<Object> result = new WebResult<Object>();
        String retMsg = unBindCardService.getFailedMess(param.get("logOrdId"));
        Map<String, String> map = new HashMap<>();
        map.put("error", retMsg);
        result.setData(map);
        return result;
    }


}
