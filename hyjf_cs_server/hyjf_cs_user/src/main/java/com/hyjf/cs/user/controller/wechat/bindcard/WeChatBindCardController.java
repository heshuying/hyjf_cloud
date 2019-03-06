package com.hyjf.cs.user.controller.wechat.bindcard;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * App端绑卡
 * @author hesy
 * @version AppBindCardController, v0.1 2018/7/19 9:34
 */
@Api(value = "weChat端-绑卡",tags = "weChat端-绑卡")
@RestController
@RequestMapping("/hyjf-wechat/wx/bindCardPage")
public class WeChatBindCardController extends BaseUserController {
    @Autowired
    BindCardService bindCardService;
    @Autowired
    SystemConfig systemConfig;


//    @PostMapping("/bindCardPage")
//    @ApiOperation(value = "绑卡", notes = "绑卡")
//    public ModelAndView bindCardPage(HttpServletRequest request, @RequestHeader(value = "userId") Integer userId) {
//
//        ModelAndView modelAndView = new ModelAndView();
//        WebViewUserVO webViewUserVO = bindCardService.getWebViewUserByUserId(userId);
//        // 检查参数
//        ResultEnum checkResult = bindCardService.checkParamBindCardPageWeChat(webViewUserVO);
//
//        if (checkResult != null) {
//            logger.info("checkResult is:{}", checkResult.getStatusDesc());
//            return getErrorModelAndView(checkResult);
//        }
//
//        // 请求银行接口
//        try {
//            // 同步调用路径
//            String retUrl = systemConfig.appHost + request.getContextPath()
//                    + "wx/bindCardPage/return.do";
//            // 异步调用路
//            String bgRetUrl = systemConfig.appHost + request.getContextPath()
//                    + "wx/bindCardPage/notifyReturn.do";
//            // 拼装参数 调用江西银行
//            String forgetPassworedUrl = systemConfig.forgetpassword;
//            BindCardPageBean bean = new BindCardPageBean();
//            bean.setTxCode(BankCallConstant.TXCODE_BIND_CARD_PAGE);
//            bean.setChannel(BankCallConstant.CHANNEL_WEI);
//            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
//            bean.setIdNo(webViewUserVO.getIdcard());
//            bean.setName(webViewUserVO.getTruename());
//            bean.setAccountId(webViewUserVO.getBankAccount());
//            bean.setUserIP(GetCilentIP.getIpAddr(request));
//            bean.setUserId(userId);
//            bean.setRetUrl(retUrl);
//            bean.setSuccessfulUrl(retUrl+"&isSuccess=1");
//            bean.setNotifyUrl(bgRetUrl);
//            bean.setForgetPassworedUrl(forgetPassworedUrl);
//            // 微官网 1
//            bean.setPlatform("1");
//            modelAndView = bindCardService.getCallbankMV(bean);
//
//            logger.info("绑卡调用页面end");
//            return modelAndView;
//        } catch (Exception e) {
//            logger.error("调用银行接口失败", e);
//            return getErrorModelAndView(ResultEnum.ERROR_022);
//        }
//
//    }

    private ModelAndView getErrorModelAndView(ResultEnum param) {
        ModelAndView modelAndView = new ModelAndView("/jumpHTML");
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, param.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, param.getStatusDesc());
        baseMapBean.setCallBackAction(systemConfig.getServerHost() + "/user/bankCard/bind/result/failed");
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }

    /**
     * 页面绑卡同步回调
     * @param request
     * @param response
     * @param bean
     * @return
     */
    @PostMapping("/return")
    @ApiOperation(value = "绑卡同步回调", notes = "绑卡同步回调")
    public ModelAndView bindCardReturn(HttpServletRequest request, HttpServletResponse response,
                                       @ModelAttribute BankCallBean bean) {

        boolean checkTender = RedisUtils.tranactionSet(RedisConstants.CONCURRENCE_BIND_CARD + bean.getLogOrderId(), 600);
        ModelAndView modelAndView = new ModelAndView();
        String frontParams = request.getParameter("frontParams");
        String isSuccess = request.getParameter("isSuccess");
        if(StringUtils.isBlank(bean.getRetCode())&&StringUtils.isNotBlank(frontParams)){
            JSONObject jsonParm = JSONObject.parseObject(frontParams);
            if(jsonParm.containsKey("RETCODE")){
                bean.setRetCode(jsonParm.getString("RETCODE"));
            }
        }
        BaseMapBean baseMapBean=new BaseMapBean();
        bean.convert();
        // 银行返回响应代码
        String retCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
        logger.info("绑卡同步返回值,用户ID:[" + bean.getLogUserId() + "],retCode:[" + retCode + "]");
        // 绑卡后处理
        try {
            if(checkTender){
                BankCardVO bankCardVO = bindCardService.queryUserCardValid(bean.getLogUserId(), bean.getCardNo());
                if (bankCardVO == null) {
                    UserVO users =bindCardService.getUsersById(Integer.parseInt(bean.getLogUserId()));
                    bean.setAccountId(request.getParameter("account"));
                    bean.setMobile(users.getMobile());
                    // 保存银行卡信息
                    bindCardService.updateAfterBindCard(bean);
                }
            }/*else{
                Thread.sleep(3000);
            }*/
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)||"1".equals(isSuccess)) {
            // 成功
            modelAndView = new ModelAndView("/jumpHTML");
            baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.SUCCESS5.getStatus());
            baseMapBean.set(CustomConstants.APP_STATUS_DESC, ResultEnum.SUCCESS5.getStatusDesc());
            baseMapBean.setCallBackAction(systemConfig.getServerHost() + "/user/bankCard/bind/result/success");
            modelAndView.addObject("callBackForm", baseMapBean);
            return modelAndView;
        } else {
            return getErrorModelAndView(ResultEnum.ERROR_045);
        }
    }


    /**
     * 页面绑卡异步回调
     * @param request
     * @param bean
     * @return
     */
    @PostMapping("/notifyReturn")
    @ApiOperation(value = "绑卡异步回调", notes = "绑卡异步回调")
    public BankCallResult bgreturn(HttpServletRequest request,
                                   @ModelAttribute BankCallBean bean) {
        // 上送的异步地址里面有
        BankCallResult result = new BankCallResult();
        String phone = request.getParameter("phone");
        logger.info("页面绑卡异步回调start");
        bean.setMobile(phone);
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());

        // 绑卡后处理
        try {
            boolean checkTender = RedisUtils.tranactionSet(RedisConstants.CONCURRENCE_BIND_CARD + bean.getLogOrderId(), 600);
            if(checkTender){
                // 保存银行卡信息
                bindCardService.updateAfterBindCard(bean);
            }
        } catch (Exception e) {
            logger.error("页面绑卡异步回调更新异常", e);
            logger.error(e.getMessage());
        }
        logger.info("页面绑卡成功,用户ID:[" + userId + ",用户电子账户号:[" + bean.getAccountId() + "]");
        result.setStatus(true);
        return result;
    }
}
