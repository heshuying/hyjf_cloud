package com.hyjf.cs.user.controller.api.openaccountplus;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.IdCard15To18;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.OpenAccountPlusRequest;
import com.hyjf.cs.user.bean.OpenAccountPlusResult;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bankopen.impl.BankOpenServiceImpl;
import com.hyjf.cs.user.util.GetCilentIP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 外部服务接口：注册开户二合一
 *
 * @author hesy
 */
@RestController
@RequestMapping("/hyjf-api/server/user/plus/openaccountplus")
public class OpenAccountPlusController extends BaseUserController {

    private final Logger logger = LoggerFactory.getLogger(OpenAccountPlusController.class);

    @Autowired
    BankOpenServiceImpl bankOpenService;

    /**
     * @param openAccountRequestBean
     * @param request
     * @return
     */
    @RequestMapping("/sendsmscode")
    public OpenAccountPlusResult sendCode(@RequestBody OpenAccountPlusRequest openAccountRequestBean,
                                          HttpServletRequest request) {
        logger.info("注册开户二合一发送短信验证码开始 sendsmscode");

        OpenAccountPlusResult resultBean = new OpenAccountPlusResult();
        // 手机号
        String mobile = openAccountRequestBean.getMobile();
        // 真实姓名
        String trueName = openAccountRequestBean.getTrueName();
        // 身份证号
        String idNo = openAccountRequestBean.getIdNo();
        // 推荐人
        String referee = openAccountRequestBean.getReferee();
        // 推广平台
        String utmId = openAccountRequestBean.getUtmId();
        // 注册渠道
        String channel = openAccountRequestBean.getChannel();
        // 注册平台
        String platform = openAccountRequestBean.getPlatform();
        // 机构编号
        String instCode = openAccountRequestBean.getInstCode();
        // 第三方绑定用户id
        Integer bindUniqueId = openAccountRequestBean.getBindUniqueId();

        String ip = GetCilentIP.getIpAddr(request);

        logger.info("入参 resultBean:" + JSONObject.toJSONString(openAccountRequestBean));

        resultBean = checkParameters(mobile, trueName, idNo, utmId, channel, platform, instCode);
        if(resultBean != null){
            return resultBean;
        }

        resultBean = bankOpenService.checkAndUpdateForSendCode(openAccountRequestBean, ip);


        return resultBean;
    }

    /**
     * 参数合法性检查
     */
    private OpenAccountPlusResult checkParameters(String mobile, String trueName, String idNo, String utmId, String channel,
                                                  String platform, String instCode) {
        OpenAccountPlusResult resultBean = new OpenAccountPlusResult();
        // 机构编号
        if (Validator.isNull(instCode)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZC000004);
            resultBean.setStatusDesc("机构编号非法");
            return resultBean;
        }

        // 开户平台
        if (Validator.isNull(platform)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("开户平台非法");
            return resultBean;
        }
        // 开户平台只支持 0：PC 1：微官网 2：Android 3：iOS 4：其他
        if (!Arrays.asList("0", "1", "2", "3", "4").contains(platform)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("开户平台非法");
            return resultBean;
        }

        // 手机号非空校验
        if (Validator.isNull(mobile)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZC000001);
            resultBean.setStatusDesc("手机号不能为空");
            return resultBean;
        }
        // 手机号合法校验
        if (!Validator.isMobile(mobile)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("手机号非法");
            return resultBean;
        }

        // 真实姓名验证
        if (Validator.isNull(trueName)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZC000007);
            resultBean.setStatusDesc("姓名不能为空");
            return resultBean;
        }
        // 身份证号验证
        if (Validator.isNull(idNo)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZC000008);
            resultBean.setStatusDesc("身份证号不能为空");
            return resultBean;
        }

        // 身份证号合法性验证
        boolean isPass = false;
        try {
            isPass = IdCard15To18.verify(idNo);
        } catch (Exception e) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZC000021);
            resultBean.setStatusDesc("身份证号非法");
            return resultBean;
        }
        if (!isPass) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZC000021);
            resultBean.setStatusDesc("身份证号非法");
            return resultBean;
        }

        // 第三方操作平台
        if (Validator.isNull(utmId)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZC000018);
            resultBean.setStatusDesc("第三方操作平台");
            return resultBean;
        }

        // 渠道
        if (Validator.isNull(channel)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("渠道非法");
            return resultBean;
        }

        // 渠道只支持 APP:000001, 渠道PC:000002 渠道Wechat:000003
        if (!Arrays.asList("000001", "000002", "000003").contains(channel)) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("渠道非法");
            return resultBean;
        }

        return null;
    }



}
