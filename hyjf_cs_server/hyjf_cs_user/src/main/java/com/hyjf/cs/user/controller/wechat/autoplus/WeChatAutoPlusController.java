/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.autoplus;

import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.user.bean.AutoPlusResultBean;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusController, v0.1 2018/6/11 14:37
 */

@Api(tags = "weChat端-用户授权自动出借债转接口")
@RestController
@RequestMapping("/hyjf-wechat/wx/user/autoplus")
public class WeChatAutoPlusController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatAutoPlusController.class);

    @Autowired
    AutoPlusService autoPlusService;

    @Autowired
    SystemConfig systemConfig;


    /**
     *
     * 发送授权短信验证码
     * 请求地址: /wx/user/autoplus/sendcode.page
     * 需要参数: 授权类型userAutoType(0 自动投标授权 1 自动债转授权) mobile
     * @author sunss
     * @param
     * @param userAutoType
     * @param mobile
     * @return
     */
    @ApiOperation(value = "授权发送短信验证码", notes = "授权发送短信验证码")
    @PostMapping(value = "/sendcode.do")
    @ResponseBody
    public BaseResultBean sendSmsCode(@RequestHeader(value = "userId") Integer userId, @RequestParam String userAutoType, String mobile) {
        logger.info("发送授权短信验证码 接口,手机号为：【" + mobile + "】,授权类型为【" + userAutoType + "】,userid为：【" + userId + "】");
        String returnRequest = "/user/autoplus/sendcode";
        AutoPlusResultBean result = new AutoPlusResultBean(returnRequest);
        CheckUtil.check(userId!=null,MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = autoPlusService.getUsersById(userId);
        if (StringUtils.isBlank(mobile)) {
            mobile = user.getMobile();
        }
        String srvTxCode = autoPlusService.checkSmsParam(user,userAutoType);
        // 请求银行接口
        BankCallBean bankBean = null;
        try {
            bankBean = autoPlusService.callSendCode(userId,mobile,srvTxCode, ClientConstants.CHANNEL_WEI,null);
        } catch (Exception e) {
            logger.error("请求验证码接口发生异常", e);
            throw new CheckException(MsgEnum.ERROR_BANK_SEND_CODE);
        }
        if(bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
            logger.error("请求验证码接口发生异常");
            throw new CheckException(MsgEnum.ERROR_BANK_SEND_CODE);
        }else {
            result.setSrvAuthCode(bankBean.getSrvAuthCode());
            result.setStatus("000");
            result.setStatusDesc("短信发送成功！");
        }
        return result;
    }

    /**
     * 用户自动债转授权
     * @param userId
     * @param sign
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "用户自动债转授权", notes = "用户自动债转授权")
    @PostMapping(value = "userAuthCredit.page")
    public WeChatResult userAuthCredit(@RequestHeader(value = "wjtClient",required = false) String wjtClient,
                                       @RequestHeader(value = "userId") Integer userId, @RequestHeader(value = "sign") String sign, HttpServletRequest request){
        WeChatResult<Object> result = new WeChatResult<>();
        String srvAuthCode = request.getParameter("srvAuthCode");
        String code = request.getParameter("code");
        // 判断是否授权过
        HjhUserAuthVO hjhUserAuth = autoPlusService.getHjhUserAuth(userId);
        if (StringUtils.isBlank(code) || StringUtils.isBlank(srvAuthCode)) {
            return getErrorModelAndView(ResultEnum.PARAM, sign, "1", hjhUserAuth);
        }
        UserVO users = autoPlusService.getUsersById(userId);
        if (users.getBankOpenAccount() == 0) {
            // 未开户
            return getErrorModelAndView(ResultEnum.USER_ERROR_200, sign, "1", hjhUserAuth);
        }
        // 判断用户是否设置过交易密码
        if (users.getIsSetPassword() == 0) {
            // 未设置交易密码
            return getErrorModelAndView(ResultEnum.USER_ERROR_201, sign, "1", hjhUserAuth);
        }
        if (hjhUserAuth != null && hjhUserAuth.getAutoCreditStatus().intValue()==1) {
            result = getErrorModelAndView(ResultEnum.USER_ERROR_203, sign,"1", hjhUserAuth);
            if (hjhUserAuth.getAutoCreditStatus() == 1) {
                return result;
            }
        }
        // 组装发往江西银行参数
        BankCallBean bean = autoPlusService.weChatGetCommonBankCallBean(users, 2, srvAuthCode, code, sign,wjtClient);
        // 插入日志
        this.autoPlusService.insertUserAuthLog(users, bean, 1, BankCallConstant.QUERY_TYPE_2);
        Map<String, Object> map = new HashMap<>();
        try {
           map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            return getErrorModelAndView(ResultEnum.USER_ERROR_205, sign, "1", hjhUserAuth);
        }
        result.setData(map);
        return result;
    }

    /**
     * 用户授权自动债转异步回调
     * @param bean
     * @return
     */
    @ApiOperation(value = "用户授权自动债转异步回调", notes = "用户授权自动债转异步回调")
    @PostMapping(value = "/userAuthCreditBgreturn")
    public String userCreditAuthInvesBgreturn(@RequestBody BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean,BankCallConstant.QUERY_TYPE_2);
        return result;
    }

    /**
     * 自动出借授权接口
     * @param userId
     * @param sign
     * @param request
     * @return
     */
    @ApiOperation(value = "自动出借授权接口")
    @PostMapping(value = "/userAuthInves.page")
    public WeChatResult userAuthInves(@RequestHeader(value = "wjtClient",required = false) String wjtClient,
                                      @RequestHeader(value = "userId") Integer userId,@RequestHeader(value = "sign") String sign,HttpServletRequest request) {
        WeChatResult<Object> result = new WeChatResult<>();
        String srvAuthCode = request.getParameter("srvAuthCode");
        String code = request.getParameter("code");
        logger.info("自动出借授权接口,srvAuthCode为：【" + srvAuthCode + "】,code为【" + code + "】,userid为：【" + userId + "】");
        // 获取授权信息
        HjhUserAuthVO hjhUserAuth = autoPlusService.getHjhUserAuth(userId);
        //检查参数
        if (StringUtils.isBlank(code) || StringUtils.isBlank(srvAuthCode)) {
            return getErrorModelAndView(ResultEnum.PARAM, sign, "0",hjhUserAuth);
        }
        UserVO users = autoPlusService.getUsersById(userId);
        //判断是否开户
        if (users.getBankOpenAccount() == 0) {
            // 未开户
            return getErrorModelAndView(ResultEnum.USER_ERROR_200, sign,"0", hjhUserAuth);
        }
        // 判断用户是否设置过交易密码
        if (users.getIsSetPassword() == 0) {
            // 未设置交易密码
            return getErrorModelAndView(ResultEnum.USER_ERROR_201, sign,"0", hjhUserAuth);
        }
        // 判断是否授权过
        if (hjhUserAuth != null && hjhUserAuth.getAutoInvesStatus().intValue()==1) {
            //
            result = getErrorModelAndView(ResultEnum.USER_ERROR_202, sign,"0", hjhUserAuth);
            if (hjhUserAuth.getAutoInvesStatus() == 1) {
                return result;
            }
        }
        // 组装发往江西银行参数
        BankCallBean bean = autoPlusService.weChatGetCommonBankCallBean(users, 1, srvAuthCode, code, sign,wjtClient);
        // 插入日志
        this.autoPlusService.insertUserAuthLog(users, bean, 1, BankCallConstant.QUERY_TYPE_1);
        Map<String, Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            return getErrorModelAndView(ResultEnum.USER_ERROR_204, sign,"0", hjhUserAuth);
        }
        result.setData(map);
        return result;
    }

    /**
     * @Author: zhangqingqing
     * @Desc :用户授权自动出借异步回调
     * @Param: * @param bean
     * @Date: 16:37 2018/5/30
     * @Return: String
     */
    @ApiOperation(value = "用户授权自动出借异步回调", notes = "用户授权自动出借异步回调")
    @ResponseBody
    @PostMapping(value = "/userAuthInvesBgreturn")
    public String userAuthInvesBgreturn(@RequestBody BankCallBean bean) {
        String result = autoPlusService.userBgreturn(bean,BankCallConstant.QUERY_TYPE_1);
        return result;
    }


    /**
     * 组装跳转错误页面MV
     * @param param
     * @param sign
     * @param type
     * @param hjhUserAuth
     * @return
     */
    private WeChatResult getErrorModelAndView(ResultEnum param, String sign, String type, HjhUserAuthVO hjhUserAuth) {
        WeChatResult<Object> result = new WeChatResult<>();
        BaseMapBean baseMapBean = new BaseMapBean();
        result.setStatus(param.getStatus());
        result.setStatusDesc(param.getStatusDesc());
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.set("autoInvesStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoInvesStatus()==null?"0":hjhUserAuth.getAutoInvesStatus()+ "");
        baseMapBean.set("autoCreditStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoCreditStatus()==null?"0":hjhUserAuth.getAutoCreditStatus() + "");
        baseMapBean.set("userAutoType", type);
        baseMapBean.setCallBackAction(systemConfig.getServerHost() + CommonConstant.JUMP_HTML_ERROR_PATH);
        Map<String,Object> map = new HashMap<>();
        map.put("callBackForm", baseMapBean);
        result.setData(map);
        return result;
    }

    /**
     * @Description 调用银行失败原因
     * @Author
     */
    @ApiOperation(value = "调用银行失败原因", notes = "查询调用银行失败原因")
    @PostMapping("/searchFiledMess")
    @ApiImplicitParam(name = "param",value = "{logOrdId:String}",dataType = "Map")
    @ResponseBody
    public WeChatResult<Object> searchFiledMess(@RequestBody Map<String,String> param) {
        logger.info("调用银行失败原因start,logOrdId:{}", param);
        WeChatResult<Object> result = new WeChatResult<Object>();
        Map<String,String> map = new HashedMap();
        map.put("isSetPassword","0");
        String retMsg = autoPlusService.getFailedMess(param.get("logOrdId"));
        if(retMsg.equals("00000000")){
            map.put("isSetPassword","1");
        }else {
            map.put("error",retMsg);
        }
        result.setData(map);
        result.setStatus("000");
        return result;
    }

}
