package com.hyjf.cs.user.controller.api.wrb;

/**
 * @author lisheng
 * @version ApiUserLoginController, v0.1 2018/11/15 16:40
 */

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.bean.FclcPostBean;
import com.hyjf.cs.user.result.BaseResultBeanFrontEnd;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.service.register.RegisterService;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Api(value = "风车理财第三方登录",tags = "api端-风车理财第三方登录")
@Controller
@RequestMapping("/hyjf-wechat/api/user/login")
public class ApiUserLoginController {

    Logger logger = LoggerFactory.getLogger(ApiUserLoginController.class);
    @Value("${hyjf.front.wei.host}")
    public String wechatHost;

    @Autowired
    public LoginService loginService;
    @Autowired
    private RegisterService registService;
    /** 汇盈金服调用风车理财接口通知注册绑定成功 **/
    @Value("${wrb.callback.bind.url}")
    public String HYJF_REQ_PUB_KEY_PATH;

    /**
     * 第三方用户自动登陆接口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/third_login")
    public ModelAndView nfcfLogin(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam String param,
                                  @RequestParam(value = "sign", required = false) String sign) {
        FclcPostBean bean = new FclcPostBean();
        try {
            bean = WrbParseParamUtil.mapToBean(WrbParseParamUtil.parseParam(param), FclcPostBean.class);
        } catch (Exception e) {
            logger.error("参数解析失败....", e);
        }
        // 验证
        this.checkFclcPostBean(bean);
        Map<String, String> params = new HashMap<>();
        params.put("from", "hyjf");
        params.put("ticket", bean.getTicket());
        ModelAndView modelAndView = new ModelAndView("wrb/wrb_result");
        BaseMapBean baseMapBean=new BaseMapBean();
        baseMapBean.setCallBackAction(wechatHost+"/login");
        //回调风车理财获取用户账号ID
        String returnParams = WrbParseParamUtil.wrbCallback(HYJF_REQ_PUB_KEY_PATH, params);
        if(StringUtils.isNotBlank(returnParams)){
            JSONObject jsonObject = JSONObject.parseObject(returnParams);
            //if(jsonObject.getInteger("retcode") == 0){
                String wrbUserId = jsonObject.getString("wrb_user_id");
                String pfUserId = jsonObject.getString("pf_user_id");
                wrbUserId = "111";
                pfUserId= "5781";
                if(!StringUtils.isNoneBlank(pfUserId)){
                    modelAndView.addObject("callBackForm", baseMapBean);
                    return modelAndView;
                }
                // 查询userId
                Integer userId = loginService.getUserIdByBind(Integer.parseInt(wrbUserId), WrbCommonDateUtil.FCLC_INSTCODE);
                // userid不存在,跳转登录页面
                if (userId != null&&userId!=0) {
                    UserVO users = loginService.getUsersById(userId);
                    BankOpenAccountVO account = registService.getBankOpenAccount(userId);
                    String accountId = null;
                    if (account != null && StringUtils.isNoneBlank(account.getAccount())) {
                        accountId = account.getAccount();
                        /*********** 登录时自动同步线下充值记录 start ***********/
                        if (users.getBankOpenAccount() == 1) {
                            CommonSoaUtils.synBalance(users.getUserId());
                        }
                        /*********** 登录时自动同步线下充值记录 end ***********/
                    }
                    sign = SecretUtil.createToken(userId, users.getUsername(), accountId);
                    baseMapBean.set("sign", sign);
                    //登录成功之后风车理财的特殊标记，供后续投资使用
                    CookieUtils.addCookie(request, response, CustomConstants.TENDER_FROM_TAG,
                            CustomConstants.WRB_CHANNEL_CODE);
                    RedisUtils.del("loginFrom"+userId);
                    RedisUtils.set("loginFrom"+userId, "2", 1800);

                    // 回调url（h5错误页面）

                    // 跳转url
                    if (bean.getTarget_url() != null) {
                        baseMapBean.setCallBackAction(bean.getTarget_url());

                    } else {
                        // 跳转首页（个人主页）
                        baseMapBean.setCallBackAction(wechatHost);
                    }
                    baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                    baseMapBean.set(CustomConstants.APP_STATUS_DESC, "用户登陆成功！");
                    modelAndView.addObject("callBackForm", baseMapBean);
                    return modelAndView;
                }
            /*}else {
                logger.info("回调风车理财票据："+bean.getTicket() + ",返回数据："+returnParams);
            }*/
        }
        modelAndView.addObject("callBackForm", baseMapBean);
        return modelAndView;
    }


    public void checkFclcPostBean(FclcPostBean bean) {
        //传入信息验证
        CheckUtil.check(Validator.isNotNull(bean.getFrom()), MsgEnum.ERR_OBJECT_REQUIRED, "from");
        CheckUtil.check(Validator.isNotNull(bean.getTicket()), MsgEnum.ERR_OBJECT_REQUIRED, "ticket");
        CheckUtil.check(Validator.isNotNull(bean.getTarget_url()), MsgEnum.ERR_OBJECT_REQUIRED, "target_url");
    }

}
