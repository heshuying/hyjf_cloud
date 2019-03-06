package com.hyjf.cs.user.controller.app.bindcard;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.user.bean.BaseMapBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bindcard.BindCardService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * app端解绑银行卡
 * @author hesy
 * @version AppDeleteCardController, v0.1 2018/7/19 9:38
 */
@Api(value = "app端-解绑银行卡",tags = "app端-解绑银行卡")
@RestController
@RequestMapping("/hyjf-app/bank/app/deleteCard")
public class AppDeleteCardController extends BaseUserController {
    @Autowired
    BindCardService bindCardService;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 解绑银行卡
     * @param userId
     * @param token
     * @param request
     * @return
     */
    @PostMapping("/deleteCard")
    @ApiOperation(value = "解绑银行卡", notes = "解绑银行卡")
    public JSONObject deleteCard(@RequestHeader(value = "userId") Integer userId, @RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        JSONObject info = new JSONObject();
        info.put("request", "/hyjf-app/bank/app/deleteCard/deleteCard");
        WebViewUserVO webViewUserVO = bindCardService.getWebViewUserByUserId(userId, BankCallConstant.CHANNEL_APP);
        String cardNo = request.getParameter("bankNumber");// 银行卡号

        logger.info("delete bankcard userId IS:{}, cardNo IS:{}", userId, cardNo);
        if (webViewUserVO == null) {
            info.put(CustomConstants.APP_STATUS, 1);
            info.put(CustomConstants.APP_STATUS_DESC, "您未登陆，请先登录");
            info.put("successUrl", "");
            return info;
        }
        // 取得用户在汇付天下的客户号
        String checkResult = bindCardService.checkParamUnBindCardAPP(webViewUserVO,cardNo);
        if (StringUtils.isNotBlank(checkResult)) {
            info.put(CustomConstants.APP_STATUS, 1);
            info.put(CustomConstants.APP_STATUS_DESC, checkResult);
            info.put("successUrl", "");
            return info;
        }

        // 请求银行绑卡接口
        BankCallBean bankBean = null;
        try {
            bankBean = bindCardService.callBankUnBindCard(cardNo, userId);
            logger.info("调用江西银行解绑银行卡接口结束.... ");
        } catch (Exception e) {
            logger.error("调用江西银行解绑银行卡接口出错...", e);
            info.put(CustomConstants.APP_STATUS, 1);
            info.put(CustomConstants.APP_STATUS_DESC, "调用银行接口失败,请联系客服!");
            info.put("successUrl", "");
            return info;
        }
        // 回调数据处理
        if (bankBean == null || !(BankCallStatusConstant.RESPCODE_SUCCESS.equals(bankBean.getRetCode()))) {
            info.put(CustomConstants.APP_STATUS, 1);
            info.put(CustomConstants.APP_STATUS_DESC, "抱歉，银行卡删除错误，请联系客服！");
            info.put("successUrl", "");
            return info;
        }

        logger.info("执行删除卡后处理,判断银行卡状态，删除平台本地银行卡信息...");
        // 执行删除卡后处理,判断银行卡状态，删除平台本地银行卡信息
        try {
            boolean isdelFlag = bindCardService.updateAfterDeleteCard(userId,webViewUserVO.getUsername(),cardNo);
            // 删除失败
            if (!isdelFlag) {
                info.put(CustomConstants.APP_STATUS, 1);
                info.put(CustomConstants.APP_STATUS_DESC, "抱歉，银行卡删除错误，请联系客服！");
                info.put("successUrl", "");
                return info;
            } else {
                BaseMapBean baseMapBean=new BaseMapBean();
                baseMapBean.set(CustomConstants.APP_STATUS, BaseResultBeanFrontEnd.SUCCESS);
                //baseMapBean.set(CustomConstants.APP_STATUS_DESC, "恭喜您！您的普通银行卡删除成功");
                baseMapBean.set(CustomConstants.APP_STATUS_DESC, "");
                baseMapBean.set("token", URLEncoder.encode(token, "UTF-8"));
                baseMapBean.set("sign", "");
                Integer urlType = bindCardService.getBankInterfaceFlagByType("BIND_CARD");
                baseMapBean.set("urlType", urlType.toString());//绑卡开关 0跳转老接口  1跳转新接口
                baseMapBean.setCallBackAction(systemConfig.AppFrontHost + "/user/bankCard/unbind/result/success");
                info.put(CustomConstants.APP_STATUS, 0);
                //info.put(CustomConstants.APP_STATUS_DESC, "恭喜您！您的普通银行卡删除成功");
                info.put(CustomConstants.APP_STATUS_DESC, "");
                info.put("successUrl", baseMapBean.getUrl());
                return info;
            }
        } catch (Exception e) {
            info.put(CustomConstants.APP_STATUS, 1);
            info.put(CustomConstants.APP_STATUS_DESC, "抱歉，银行卡删除错误，请联系客服！");
            info.put("successUrl", "");
        }
        return info;
    }
}
