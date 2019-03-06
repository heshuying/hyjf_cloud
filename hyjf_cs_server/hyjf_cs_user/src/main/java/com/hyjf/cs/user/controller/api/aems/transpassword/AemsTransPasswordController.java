package com.hyjf.cs.user.controller.api.aems.transpassword;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.AemsErrorCodeConstant;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.bean.AemsTransPasswordRequestBean;
import com.hyjf.cs.user.bean.AemsTransPasswordResultBean;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.aems.transpassword.impl.AemsTransPasswordService;
import com.hyjf.cs.user.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/***
 * Aems设置/修改交易密码
* @author Zha Daojian
* @date 2018/9/13 13:45
* @param
* @return
**/
@Api(value = "api端-AEMS设置/修改交易密码",tags = "api端-AEMS设置/修改交易密码")
@RestController
@RequestMapping("/hyjf-api/aems/transpassword")
public class AemsTransPasswordController extends BaseController {
    Logger _log = LoggerFactory.getLogger("AEMS设置/修改交易密码Controller");

    @Autowired
    private AemsTransPasswordService transPasswordService;

    @Autowired
    private SystemConfig systemConfig;

    /** 设置密码失败页面*/
    private static final String PASSWORD_ERROR_PATH = "/bank/user/personalsetting/error";


    /***
     * 设置&重置交易密码
    * @author Zha Daojian
    * @date 2018/9/13 13:45
    * @param transPasswordRequestBean
    * @return org.springframework.web.servlet.ModelAndView
    **/
    @ApiOperation(value = "AEMS设置/修改交易密码", notes = "AEMS设置/修改交易密码")
    @PostMapping(value = "/setPassword", produces = "application/json; charset=utf-8")
    public ModelAndView setPassword(@RequestBody AemsTransPasswordRequestBean transPasswordRequestBean) {
        _log.info("设置交易密码 start");
        ModelAndView modelAndView = new ModelAndView();
        _log.info(transPasswordRequestBean.getAccountId()+"设置交易密码开始-----------------------------");
        _log.info("第三方请求参数："+JSONObject.toJSONString(transPasswordRequestBean));
        //验证请求参数
        if (Validator.isNull(transPasswordRequestBean.getAccountId())||
                Validator.isNull(transPasswordRequestBean.getRetUrl())||
                Validator.isNull(transPasswordRequestBean.getBgRetUrl())||
                Validator.isNull(transPasswordRequestBean.getChannel())||
                Validator.isNull(transPasswordRequestBean.getInstCode())) {
            
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("statusDesc","请求参数非法");
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000001);
                params.put("status", resultBean.getStatus());
                params.put("chkValue", resultBean.getChkValue());

                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            if(Validator.isNotNull(transPasswordRequestBean.getRetUrl())){
                AemsTransPasswordResultBean repwdResult = new AemsTransPasswordResultBean();
                repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
                modelAndView = new ModelAndView(BaseDefine.CALL_BACK_TRANSPASSWORD_VIEW);
                // 设置交易密码
                repwdResult.set("statusDesc", "请求参数非法");
                
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000001);
                repwdResult.set("chkValue", resultBean.getChkValue());
                repwdResult.set("status", resultBean.getStatus());
                repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
                modelAndView.addObject("callBackForm", repwdResult);
                return modelAndView;
            }
            
            
            modelAndView = new ModelAndView(PASSWORD_ERROR_PATH);
            _log.info("-------------------请求参数非法--------------------");
            modelAndView.addObject("message", "请求参数非法");
            return modelAndView;
        }
        
        //验签
        if(!SignUtil.AEMSVerifyRequestSign(transPasswordRequestBean, "/aems/transpassword/setPassword")){
            _log.info("-------------------验签失败！--------------------");
            AemsTransPasswordResultBean repwdResult = new AemsTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView = new ModelAndView(BaseDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc", "验签失败！");
            
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000002);
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("chkValue", resultBean.getChkValue());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","验签失败！");
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                params.put("chkValue", resultBean.getChkValue());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;
        }
        
        
        //根据账号找出用户ID
        String accountId = StringUtils.isEmpty(transPasswordRequestBean.getAccountId()) ? "0" : transPasswordRequestBean.getAccountId();
        BankOpenAccountVO bankOpenAccount = transPasswordService.getBankOpenAccountByAccount(accountId);
        if(bankOpenAccount == null){
            modelAndView = new ModelAndView(PASSWORD_ERROR_PATH);
            _log.info("-------------------没有根据电子银行卡找到用户"+transPasswordRequestBean.getAccountId()+"！--------------------");

            AemsTransPasswordResultBean repwdResult = new AemsTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            modelAndView = new ModelAndView(BaseDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc", "没有根据电子银行卡找到用户 ");
            
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000004);
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","没有根据电子银行卡找到用户 ");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;
            
        }
        //用户ID
        UserVO user = transPasswordService.getUsersById(bankOpenAccount.getUserId());
        if (user == null) {
            _log.info("-------------------"+transPasswordRequestBean.getAccountId() + "用户不存在汇盈金服账户！--------------------");
            AemsTransPasswordResultBean repwdResult = new AemsTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            modelAndView = new ModelAndView(BaseDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc","用户不存在汇盈金服账户！");
            
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000006);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", repwdResult.getStatus());
                params.put("statusDesc","用户不存在汇盈金服账户！");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;
            
        }
        Integer userId = user.getUserId();
        // 未开户
        if (user.getBankOpenAccount().intValue() != 1) {
            _log.info("-------------------用户未开户！--------------------");
            AemsTransPasswordResultBean repwdResult = new AemsTransPasswordResultBean();
            repwdResult.setCallBackAction(transPasswordRequestBean.getRetUrl());
            repwdResult.set("accountId", transPasswordRequestBean.getAccountId());
            modelAndView = new ModelAndView(BaseDefine.CALL_BACK_TRANSPASSWORD_VIEW);
            // 设置交易密码
            repwdResult.set("statusDesc", "用户未开户！");
            
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000007);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
            repwdResult.set("acqRes",transPasswordRequestBean.getAcqRes());
            modelAndView.addObject("callBackForm", repwdResult);
            if(Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", transPasswordRequestBean.getAccountId());
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","用户未开户！");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",transPasswordRequestBean.getAcqRes());
                CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
            }
            return modelAndView;
        }

        UserInfoVO usersInfo = transPasswordService.getUserInfo(userId);

        // 同步调用路径
        String retUrl = systemConfig.getServerHost()+"/hyjf-api/aems/transpassword";
        // 异步调用路
        String bgRetUrl = "http://CS-USER/hyjf-api/aems/transpassword";
        // 调用设置密码接口
        retUrl += "/passwordReturn?acqRes="+transPasswordRequestBean.getAcqRes()+"&logUserId="+user.getUserId()+"&callback="+transPasswordRequestBean.getRetUrl().replace("#", "*-*-*");
        bgRetUrl += "/passwordBgreturn?acqRes="+transPasswordRequestBean.getAcqRes()+"&callback="+transPasswordRequestBean.getBgRetUrl().replace("#", "*-*-*");

        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        // 接口版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        // 机构代码
        bean.setInstCode(systemConfig.getBankInstcode());
        bean.setBankCode(systemConfig.getBankCode());
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(transPasswordRequestBean.getChannel());
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
        // 电子账号
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        bean.setIdNo(usersInfo.getIdcard());
        bean.setName(usersInfo.getTruename());
        bean.setMobile(user.getMobile());

        // 页面异步返回URL(必须)
        bean.setNotifyUrl(bgRetUrl);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDRESETPAGE);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));

        bean.setSuccessfulUrl(retUrl+"&isSuccess=1&ordid=" + bean.getLogOrderId());
        // 页面同步返回 URL
        bean.setRetUrl(retUrl+"&isSuccess=1&ordid=" + bean.getLogOrderId());


        // 跳转到江西银行画面
        try {
            modelAndView = BankCallUtils.callApi(bean);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        _log.info("设置交易密码end");
        return modelAndView;
    }

    /**
     *重置交易密码同步回调
     * @author Zha Daojian
     * @date 2018/9/13 13:48
     * @param request, response, bean
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequestMapping("/passwordReturn")
    public ModelAndView passwordReturn(HttpServletRequest request, HttpServletResponse response,
                                       @ModelAttribute BankCallBean bean) {
        _log.info("设置交易密码同步回调start");
        ModelAndView modelAndView = new ModelAndView(BaseDefine.CALL_BACK_TRANSPASSWORD_VIEW);
        AemsTransPasswordResultBean repwdResult = new AemsTransPasswordResultBean();
        repwdResult.setCallBackAction(request.getParameter("callback").replace("*-*-*","#"));
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        BankOpenAccountVO bankOpenAccount = transPasswordService.getBankOpenAccount(userId);
        
        // 调用查询电子账户密码是否设置
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        selectbean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET_QUERY);
        selectbean.setInstCode(systemConfig.getBankInstcode());
        selectbean.setBankCode(systemConfig.getBankCode());
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(BankCallConstant.CHANNEL_PC);
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));// 电子账号

        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);
        repwdResult.set("accountId", bankOpenAccount.getAccount());
        
        if ("1".equals(retBean.getPinFlag())) {
            // 是否设置密码中间状态
            transPasswordService.updateUserIsSetPassword(userId);
            
            modelAndView.addObject("statusDesc", "交易密码设置成功！");
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        } else {
            // 设置交易密码
            modelAndView.addObject("statusDesc", "交易密码设置失败,失败原因：" + transPasswordService.getBankRetMsg(bean.getRetCode()));
            
            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE999999);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        }
        repwdResult.set("acqRes",request.getParameter("acqRes"));
        modelAndView.addObject("callBackForm", repwdResult);
        _log.info("设置交易密码同步回调end");
        return modelAndView;
    }

    /**
     **重置交易密码异步回调
     * @author Zha Daojian
     * @date 2018/9/13 13:49
     * @param request, response, bean
     * @return com.hyjf.pay.lib.bank.bean.BankCallResult
     **/
    @ResponseBody
    @RequestMapping("passwordBgreturn")
    public BankCallResult passwordBgreturn(HttpServletRequest request, HttpServletResponse response,
                                           @ModelAttribute BankCallBean bean) {
        _log.info("设置交易密码异步回调start");
        // 返回值  9-22修改
        BankCallResult result = new BankCallResult();
        String message = "";
        String status = "";
        Map<String, String> params = new HashMap<String, String>();
        // 返回值修改 end
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        UserVO user = this.transPasswordService.getUsersById(userId);
        
        
        BankOpenAccountVO bankOpenAccount = transPasswordService.getBankOpenAccount(userId);
        
        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 开户后保存相应的数据以及日志
                this.transPasswordService.updateUserIsSetPassword(userId);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            message = "交易密码设置成功";
            status = AemsErrorCodeConstant.SUCCESS;
        }else{
         // 设置交易密码
            message = "交易密码设置失败,失败原因：" + transPasswordService.getBankRetMsg(bean.getRetCode());
            status = AemsErrorCodeConstant.STATUS_CE999999;
        }
        // 返回值  9-22修改
        params.put("accountId", bankOpenAccount.getAccount());
        params.put("status", status);
        params.put("statusDesc",message);
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes",request.getParameter("acqRes"));
        CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*","#"), params);
        
        _log.info("设置交易密码异步回调end");
        result.setMessage("设置交易密码成功");
        result.setStatus(true);
        // 返回值  9-22修改 end
        
        return result;
    }


}
