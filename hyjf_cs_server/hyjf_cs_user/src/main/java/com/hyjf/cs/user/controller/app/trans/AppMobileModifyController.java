package com.hyjf.cs.user.controller.app.trans;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.UserConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.DES;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.bean.BankMobileModifyBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.trans.MobileModifyService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * APP绑定新手机号
 *
 * @author hesy
 * @version AppMobileModifyController, v0.1 2018/7/18 14:53
 */
@Api(value = "app端-绑定新手机号", tags = "app端-绑定新手机号")
@RestController
@RequestMapping("/hyjf-app/appUser")
public class AppMobileModifyController extends BaseUserController {
    @Autowired
    SystemConfig systemConfig;
    @Autowired
    MobileModifyService mobileModifyService;

    /**
     * 修改银行预留手机号
     *
     * @param userId
     * @return
     * @Author liushouyi
     */
    @ApiOperation(value = "app银行预留手机号码修改", notes = "app银行预留手机号码修改")
    @PostMapping(value = "/bankMobileModify", produces = "application/json; charset=utf-8")
    @ResponseBody
    public AppResult<Object> bankMobileModify(@RequestHeader(value = "userId") int userId, @RequestHeader(value = "sign") String sign, @RequestParam("platform") String platForm, HttpServletRequest request) {
        logger.info("app银行预留手机号修改,userId:" + userId);
        AppResult<Object> result = new AppResult<Object>();
        // 跳转给原生页状态用000
        result.setStatusInfo(BaseResult.SUCCESS, BaseResult.SUCCESS_DESC);
        // 获取用户信息
        UserVO user = this.mobileModifyService.getUsersById(userId);
        if (user == null) {
            // 获取用户信息失败
            throw new ReturnMessageException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
        // 目前只有个人用户可修改
        if (null == user.getUserType() || user.getUserType() == 1) {
            // 只针对个人用户修改手机号
            throw new ReturnMessageException(MsgEnum.ERR_USER_PERSON_ONLY);
        }
        BankOpenAccountVO bankOpenAccountVO = this.mobileModifyService.getBankOpenAccount(userId);
        if (null == bankOpenAccountVO || StringUtils.isBlank(bankOpenAccountVO.getAccount())) {
            // 用户未开户
            throw new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }

        BankMobileModifyBean bean = new BankMobileModifyBean();
        bean.setAccountId(bankOpenAccountVO.getAccount());
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        bean.setUserId(user.getUserId());
        bean.setIp(CustomUtil.getIpAddr(request));
        // 0：web 1:wechat 2:android 3:ios
        bean.setPlatform(platForm);
        bean.setClientHeader(ClientConstants.CLIENT_HEADER_APP);
        bean.setBankMobile(user.getBankMobile());

        // 组装参数
        Map<String, Object> data = mobileModifyService.getBankMobileModify(bean, sign);
        result.setData(data);
        // 插入修改日志表
        boolean re = mobileModifyService.insertBankMobileModify(bankOpenAccountVO.getAccount(), user.getBankMobile(), userId);
        if (!re) {
            logger.info("app保存修改预留手机号日志失败,手机号:[" + user.getBankMobile() + "],用户ID:[" + user.getUserId() + "]");
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }
        logger.info("app修改银行预留手机号结束。");
        return result;
    }

    /**
     * 获取最新预留银行手机号
     *
     * @param userId
     * @return
     * @Author liushouyi
     */
    @ApiOperation(value = "获取最新预留银行手机号", notes = "获取最新预留银行手机号")
    @PostMapping("/getNewBankMobile")
    public String getNewBankMobile(@RequestHeader(value = "userId") int userId) {
        // 调用银行接口获取最新银行预留手机号
        String newBankMobile = mobileModifyService.getNewBankMobile(userId);
        if (StringUtils.isBlank(newBankMobile)) {
            throw new CheckException(MsgEnum.STATUS_CE000004);
        }
        return newBankMobile;
    }

    /**
     * @Description 修改预留手机号异步回调结果查询
     * @Author liushouyi
     */
    @ApiOperation(value = "修改预留手机号异步回调结果查询", notes = "修改预留手机号异步回调结果查询")
    @PostMapping(value = "/searchMobileModifyMess", produces = "application/json; charset=utf-8")
    @ResponseBody
    public AppResult<Object> getMobileModifyMessage(@RequestParam(value = "logOrdId") String logOrdId, HttpServletRequest request) {
        logger.info("修改预留手机号异步回调结果start,logOrdId:{}", logOrdId);
        AppResult<Object> result = new AppResult<Object>();
        // 跳转给原生页状态用000
        result.setStatusInfo(BaseResult.SUCCESS, BaseResult.SUCCESS_DESC);
        String retMsg = mobileModifyService.getMobileModifyMess(logOrdId);
        Map<String, String> map = new HashedMap();
        map.put("status", "2");
        // 未返回查询结果返回处理异常
        if (StringUtils.isBlank(retMsg)) {
            map.put("error", "未知错误");
            result.setData(map);
            return result;
        }
        map.put("error", retMsg);
        // 未查询到返回结果码显示处理中
        if ("WATING".equals(retMsg)) {
            map.put("status", "1");
        }
        // 处理成功
        if ("00000000".equals(retMsg)) {
            map.put("status", "0");
            map.put("error", BaseResult.SUCCESS_DESC);
        }
        result.setData(map);
        return result;
    }

    @ApiOperation(value = "绑定新手机", notes = "绑定新手机")
    @PostMapping("/bindNewPhoneAction")
    public JSONObject bindNewPhoneAction(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/hyjf-app/app/appUser/bindNewPhoneAction");

        // 唯一标识
        String sign = request.getParameter("sign");

        // 江西银行业务码
        String bankCode = request.getParameter("bankCode");
        String platform = request.getParameter("platform");
        logger.info("江西银行业务码bankCode :{}", bankCode);

        String failReturnUrl = systemConfig.AppFrontHost + "/user/setting/mobile/result/failed";
        String successReturnUrl = systemConfig.AppFrontHost + "/user/setting/mobile/result/success";

        // 验证码
        String verificationCode = request.getParameter("newVerificationCode");
        // 手机号
        String mobile = request.getParameter("newMobile");
        logger.info("绑定新手机获取mobile :{}", mobile);
        // 取得加密用的Key
        String key = SecretUtil.getKey(sign);
        logger.info("取得加密用的key :{}", key);
        if (Validator.isNull(key)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        // 业务逻辑
        try {
            if (userId != null) {
                // 取得验证码
                mobile = DES.decodeValue(key, mobile);
                logger.info("des解密后得到的mobile :{}", mobile);
                if (Validator.isNull(mobile)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "手机号不能为空");
                    return ret;
                }
                if (Validator.isNull(verificationCode)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "验证码不能为空");
                    return ret;
                }
                if (!Validator.isMobile(mobile)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "请输入您的真实手机号码");
                    return ret;
                }
                {
                    UserVO userVO = mobileModifyService.getUsersByMobile(mobile);
                    if (userVO != null) {
                        ret.put("status", "1");
                        ret.put("statusDesc", "该手机号已经注册");
                        return ret;
                    }
                }
                // 判断是否开户  假如未开户  修改平台手机号   已开户 修改江西银行和平台
                BankOpenAccountVO bankOpenAccount = mobileModifyService.getBankOpenAccount(userId);
                if (bankOpenAccount == null) {
                    int cnt = mobileModifyService.updateCheckMobileCode(mobile, verificationCode, UserConstant.PARAM_TPL_BDYSJH, platform, UserConstant.CKCODE_NEW, UserConstant.CKCODE_YIYAN, true);
                    if (cnt > 0) {
                        // 未开户 修改平台手机号
                        UserVO userVO = new UserVO();
                        userVO.setUserId(userId);
                        userVO.setMobile(mobile);
                        Integer result = mobileModifyService.updateUserByUserId(userVO);
                        if (result > 0) {
                            ret.put("status", "0");
                            ret.put("statusDesc", "修改手机号成功");
                            ret.put("mobile", mobile);
                            ret.put("successUrl", successReturnUrl + "?status=000&statusDesc=" + "您已绑定手机号" + mobile.substring(0, 3).concat("****").concat(mobile.substring(7)));
                        } else {
                            ret.put("status", "1");
                            ret.put("statusDesc", "修改手机号码失败");
                        }
                    } else {
                        ret.put("status", "1");
                        ret.put("statusDesc", "验证码无效");
                    }
                    return ret;
                }

                if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(bankCode) || StringUtils.isEmpty(verificationCode)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "请求参数非法");
                    return ret;
                }

                // 调用电子账号手机号修改增强
                BankCallBean retBean = mobileModifyService.callMobileModify(userId, mobile, verificationCode, bankCode);
                if (retBean == null) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "修改手机号失败，系统异常");
                    ret.put("successUrl", failReturnUrl + "?status=99&statusDesc=修改手机号失败，系统异常！");
                    return ret;
                }
                //返回失败
                if (!BankCallConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
                    String errorMsg = retBean.getRestMsg();
                    if (StringUtils.isBlank(errorMsg)) {
                        errorMsg = this.mobileModifyService.getBankReturnErrorMsg(retBean.getRetCode());
                    }
                    if (StringUtils.isBlank(errorMsg)) {
                        errorMsg = "修改手机号失败...";
                    }
                    ret.put("status", "1");
                    ret.put("statusDesc", errorMsg);
                    ret.put("successUrl", failReturnUrl + "?status=99&statusDesc=" + errorMsg);
                    return ret;
                }
                //修改手机号
                UserVO userVO = new UserVO();
                userVO.setUserId(userId);
                userVO.setMobile(mobile);
                Integer result = mobileModifyService.updateUserByUserId(userVO);
                if (result > 0) {
                    // add by liuyang 修改手机号之后 发送同步CA认证信息修改MQ start
                    this.mobileModifyService.updateUserCAMQ(userId);
                    // add by liuyang 修改手机号之后 发送同步CA认证信息修改MQ end
                    ret.put("status", "0");
                    ret.put("statusDesc", "修改手机号成功");
                    ret.put("mobile", mobile);
                    ret.put("successUrl", successReturnUrl + "?status=000&statusDesc=" + "您已绑定手机号" + mobile.substring(0, 3).concat("****").concat(mobile.substring(7)));
                } else {
                    ret.put("status", "1");
                    ret.put("statusDesc", "修改手机号码失败");
                }
            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "用户信息不存在");
                ret.put("successUrl", failReturnUrl + "?status=99&statusDesc=用户信息不存在!");
            }
        } catch (Exception e) {
            logger.error("绑定新手机发生错误...", e);
            ret.put("status", "1");
            ret.put("statusDesc", "绑定新手机发生错误");
            ret.put("successUrl", failReturnUrl + "?status=99&statusDesc=绑定新手机发生错误");
        }
//		}
        return ret;
    }
}
