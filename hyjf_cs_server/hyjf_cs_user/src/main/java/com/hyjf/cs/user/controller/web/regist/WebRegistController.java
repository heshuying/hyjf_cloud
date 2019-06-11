/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.regist;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.RandomValidateCode;
import com.hyjf.common.validator.Validator;
import com.hyjf.common.validator.ValidatorCheckUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.register.RegisterService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.cs.user.vo.RegisterRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version RegistController, v0.1 2018/6/11 13:59
 */

@Api(value = "web端用户注册接口",tags = "web端-用户注册接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user")
public class WebRegistController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WebRegistController.class);
    @Autowired
    private RegisterService registService;

    /**
     * 初期化,跳转到注册页面
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "注册初始化",notes = "注册初始化")
    @ApiImplicitParam(name = "param",value = "{referer:'推荐人',from:'原有逻辑推荐方'}",dataType = "Map")
    @PostMapping(value = "/init")
    public WebResult init(HttpServletRequest request,@RequestBody Map<String,String> param){
        WebResult result = new WebResult();
        JSONObject ret = new JSONObject();
        String referer = request.getHeader("Referer");
        if (null != referer && referer.contains("activity/activity68/getUserStatus")) {
            ret.put("activity68", "1");
        }
        // 如果有推荐人，把推荐人带过去。 着陆页跳转。。。
        String reff = param.get("referer");
     /*   if (StringUtils.isEmpty(reff)) {
            Object rr = request.getSession().getAttribute("from_id");
            if (rr != null) {
                reff = rr.toString();
            }
        }*/
        if (reff != null && !"".equals(reff.trim())) {
            ret.put("newRegReferree", reff);
        } else {// 这是原有逻辑
            ret.put("newRegReferree", param.get("from"));
        }
        ret.put("pubexponent", "10001");
        ret.put("pubmodules", RSAJSPUtil.getPunlicKeys());
        result.setData(ret);
        return result;
    }


    /**
     * @Author: zhangqingqing
     * @Desc :注册
     * @Param: * @param registerVO
     * @Date: 16:39 2018/5/30
     * @Return: ApiResult
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public WebResult register(@RequestBody RegisterRequest registerRequest,
                              @RequestHeader(value = "wjtClient",required = false) String wjtClient,
                              HttpServletRequest request) {
        logger.info("Web端用户注册接口, registerVO is :{}", JSONObject.toJSONString(registerRequest));
        WebResult result = new WebResult();
        // 1. 参数检查
        registerRequest.setPlatform(CommonConstant.CLIENT_PC);
        String password = registerRequest.getPassword();
        password = RSAJSPUtil.rsaToPassword(password);
        registerRequest.setPassword(password);
        registService.checkParam(registerRequest);
        if(wjtClient!=null){
            logger.info("温金投用户注册开始");
            registerRequest.setIsWjt("1");
        }
        WebViewUserVO webViewUserVO = registService.register(registerRequest.getMobile(),
                registerRequest.getVerificationCode(), registerRequest.getPassword(),
                registerRequest.getReffer(), CommonConstant.HYJF_INST_CODE, registerRequest.getUtmId(), String.valueOf(ClientConstants.WEB_CLIENT), GetCilentIP.getIpAddr(request), registerRequest.getUserType(),registerRequest.getIsWjt());

        if (webViewUserVO != null) {
            // add by liuyang 神策数据统计追加 20181029 start
            if (registerRequest != null && StringUtils.isNotBlank(registerRequest.getPresetProps())) {
                logger.info("用户注册:神策预置属性:[" + registerRequest.getPresetProps() + "]");
                try {
                    SensorsDataBean sensorsDataBean = new SensorsDataBean();
                    // 将json串转换成Bean
                    Map<String, Object> sensorsDataMap = JSONObject.parseObject(registerRequest.getPresetProps(), new TypeReference<Map<String, Object>>() {
                    });
                    sensorsDataBean.setPresetProps(sensorsDataMap);
                    sensorsDataBean.setUserId(webViewUserVO.getUserId());
                    // 发送神策数据统计MQ
                    this.registService.sendSensorsDataMQ(sensorsDataBean);
                } catch (MQException e) {
                    logger.error(e.getMessage());
                }
            }
            // add by liuyang 神策数据统计追加 20181029 end

            // add by cuigq wbs客户信息回调 20190417 start
            if(!(Strings.isNullOrEmpty(registerRequest.getUtmId()) || Strings.isNullOrEmpty(registerRequest.getCustomerId()))){

                WbsRegisterMqVO wbsRegisterMqVO=new WbsRegisterMqVO();
                wbsRegisterMqVO.setUtmId(registerRequest.getUtmId());
                wbsRegisterMqVO.setCustomerId(registerRequest.getCustomerId());
                wbsRegisterMqVO.setAssetCustomerId(String.valueOf(webViewUserVO.getUserId()));

                try {
                    registService.sendWbsMQ(wbsRegisterMqVO);
                } catch (MQException e) {
                    logger.info("用户【{}】注册后，发达MQ到Wbs失败！",webViewUserVO.getUsername());
                    logger.error(e.getMessage(),e);
                }
            }
            // add by cuigq wbs客户信息回调 20190417 end

            logger.info("Web端用户注册成功, userId is :{}", webViewUserVO.getUserId());
            result.setData(webViewUserVO);
        } else {
            logger.error("Web端用户注册失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_USER_REGISTER.getMsg());
        }
        return result;
    }


    /**
     * @Author: zhangqingqing
     * @Desc : 检查手机号是否已存在
     * @Param: * @param mobile
     * @Date: 17:00 2018/6/13
     * @Return: boolean
     */
    @ResponseBody
    @ApiOperation(value = "检查手机号是否已存在", notes = "检查手机号是否已存在")
    @ApiImplicitParam(name = "param",value = "{mobile: string}", dataType = "Map")
    @PostMapping(value = "/checkPhone", produces = "application/json; charset=utf-8")
    public boolean checkPhone(@RequestBody Map<String,String> param) {
        logger.info("Web端检查手机号是否已存在, mobile is :{}",JSONObject.toJSONString(param));
        if (param.get("mobile") != null && !"".equals(param.get("mobile").trim())) {
            if (Validator.isMobile(param.get("mobile"))) {
                if (registService.existUser(param.get("mobile"))) {
                    // 存在用户,返回false
                    return false;
                } else {
                    return true;
                }

            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * 检查手机号码或用户名唯一性
     *
     * @param
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "检查手机号码或用户名唯一性", notes = "检查手机号码或用户名唯一性")
    @ApiImplicitParam(name = "param",value = "{name:string,param:string}", dataType = "Map")
    @PostMapping(value = "/checkaction", produces = "application/json; charset=utf-8")
    public boolean checkAction(@RequestBody Map<String,String> param) {
        logger.info("Web端检查手机号码或用户名唯一性, param is :{}",JSONObject.toJSONString(param));
        String name = param.get("name");
        String mobile = param.get("param");
        if ("userName".equals(name)) {
            if (registService.existUser(mobile)) {
                // 存在用户,返回false
                return false;
            } else {
                JSONObject info = new JSONObject();
                return ValidatorCheckUtil.validateMobile(info, null, null, mobile, 11, false);
            }
        } else {
            return false;
        }
    }

    /**
     * 生成图片验证码
     *
     * @param request
     * @param
     */
    @ApiOperation(value = "生成图片验证码",notes = "生成图片验证码")
    @PostMapping(value = "getcaptcha", produces = "application/json; charset=utf-8")
    public void randomCode(HttpServletRequest request) {

    }

    /**
     * 检查图片验证码
     *
     * @param request
     * @param
     */
    @ApiOperation(value = "检查图片验证码",notes = "检查图片验证码")
    @PostMapping(value = "checkcaptcha", produces = "application/json; charset=utf-8")
    public boolean checkcaptcha(HttpServletRequest request) {
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        return randomValidateCode.checkRandomCode(request, request.getParameter("newRegVerifyCode"));
    }

    /**
     * 判断推荐人是否存在 如果存在返回true，如果不存在返回false;
     *
     * @param param
     * @param
     * @return
     */
    @ResponseBody
    @ApiOperation(value = " 判断推荐人是否存在",notes = " 判断推荐人是否存在")
    @PostMapping(value = "checkRecommend", produces = "application/json; charset=utf-8")
    public boolean checkRecommend(@RequestBody Map<String,String> param) {
        logger.info("Web端判断推荐人是否存在, param is :{}",JSONObject.toJSONString(param));
        String recommend = param.get("newRegReferree");
        if (registService.countUserByRecommendName(recommend) <= 0) {
            return false;
        }
        return true;
    }

}
