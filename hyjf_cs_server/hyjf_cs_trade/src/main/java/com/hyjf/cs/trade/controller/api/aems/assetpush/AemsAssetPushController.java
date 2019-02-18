/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.aems.assetpush;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.AemsPushBean;
import com.hyjf.cs.trade.bean.AemsPushRequestBean;
import com.hyjf.cs.trade.bean.AemsPushResultBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.aems.assetpush.AemsAssetPushService;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import com.hyjf.cs.trade.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AEMS资产推送接口
 * @author Zha Daojian
 * @date 2018/12/19 15:27
 * @param
 * @return
 **/
@Api(tags = "api端-AEMS资产推送接口")
@RestController
@RequestMapping("/hyjf-api/aems/assetpush")
public class AemsAssetPushController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(AemsAssetPushController.class);

    @Autowired
    private AemsAssetPushService pushService;

    @PostMapping("/push")
    @ApiParam(required = true, name = "pushRequestBean", value = "个人资产信息")
    @ApiOperation(value = "个人资产推送", httpMethod = "POST", notes = "个人资产推送")
    public JSONObject push(@RequestBody AemsPushRequestBean pushRequestBean) {
        logger.info(this.getClass().getName(), "api端-资产推送接口 个人资产推送 start", pushRequestBean.toString(), "/hyjf-api/aems/assetpush/push.do");

        JSONObject result = new JSONObject();
        // 验证请求参数
        List<AemsPushBean> reqData = pushRequestBean.getReqData();
        if (Validator.isNull(reqData) || Validator.isNull(pushRequestBean.getInstCode()) ||
                Validator.isNull(pushRequestBean.getAssetType()) || Validator.isNull(pushRequestBean.getChkValue())) {
            logger.info("------请求参数非法-------" + pushRequestBean);
            result.put("status", ErrorCodeConstant.STATUS_CE000001);
            result.put("statusDesc", "请求参数非法");
            return result;
        }

        // 验签
        if (!SignUtil.AEMSVerifyRequestSign(pushRequestBean, "/aems/assetpush/push")) {
            logger.info("------------------验签失败！---------------------");
            result.put("status", ErrorCodeConstant.STATUS_CE000002);
            result.put("statusDesc", "验签失败！");
            return result;
        }

        logger.info(pushRequestBean.getInstCode() + " 开始推送资产 ");

        if (CustomConstants.INST_CODE_HYJF.equals(pushRequestBean.getInstCode())) {
            logger.info(pushRequestBean.getInstCode() + "  " + pushRequestBean.getAssetType() + " ------平台不能推送资产");
            result.put("status", ErrorCodeConstant.STATUS_ZT000010);
            result.put("statusDesc", "不能推送本平台资产！");
            return result;
        }

        AemsPushResultBean resultBean = pushService.assetPush(pushRequestBean);

        logger.info(this.getClass().getName(), "api端-资产推送接口 个人资产推送 end", pushRequestBean.toString(), "/hyjf-api/server/assetpush/push.do");
        result.put("status", resultBean.getStatus());
        result.put("statusDesc", resultBean.getStatusDesc());
        result.put("data", resultBean);
        return result;
    }

    @PostMapping("/pushcompany")
    @ApiParam(required = true, name = "pushRequestBean", value = "企业资产信息")
    @ApiOperation(value = "企业资产推送", httpMethod = "POST", notes = "企业资产推送")
    public JSONObject pushcompany(@RequestBody AemsPushRequestBean pushRequestBean) {
        logger.info(this.getClass().getName(), "api端-资产推送接口 企业资产推送 start", pushRequestBean.toString(), "/hyjf-api/aems/assetpush/pushcompany.do");
        JSONObject result = new JSONObject();

        // 验证请求参数
        List<AemsPushBean> reqData = pushRequestBean.getReqData();
        if (Validator.isNull(reqData) || Validator.isNull(pushRequestBean.getInstCode())
                || Validator.isNull(pushRequestBean.getAssetType()) || Validator.isNull(pushRequestBean.getChkValue())) {
            logger.info("------请求参数非法-------" + pushRequestBean);
            result.put("status", ErrorCodeConstant.STATUS_CE000001);
            result.put("statusDesc", "请求参数非法");
            return result;
        }

        // 验签
        if (!SignUtil.AEMSVerifyRequestSign(pushRequestBean, "/aems/assetpush/pushcompany")) {
            logger.info("------------------验签失败！---------------------");
            result.put("status", ErrorCodeConstant.STATUS_CE000002);
            result.put("statusDesc", "验签失败！");
            return result;
        }

        logger.info(pushRequestBean.getInstCode() + " 开始推送资产 ");

        if(CustomConstants.INST_CODE_HYJF.equals(pushRequestBean.getInstCode())){
            logger.info(pushRequestBean.getInstCode() + "  "+ pushRequestBean.getAssetType() + " ------不能推送本平台资产");
            result.put("status", ErrorCodeConstant.STATUS_ZT000010);
            result.put("statusDesc", "不能推送本平台资产！");
            return result;
        }

        AemsPushResultBean resultBean = pushService.companyAssetPush(pushRequestBean);

        logger.info(this.getClass().getName(), "api端-资产推送接口 企业资产推送 end", "/hyjf-api/server/assetpush/pushcompany.do");
        result.put("status", resultBean.getStatus());
        result.put("statusDesc", resultBean.getStatusDesc());
        result.put("data", resultBean);
        return result;
    }

}
