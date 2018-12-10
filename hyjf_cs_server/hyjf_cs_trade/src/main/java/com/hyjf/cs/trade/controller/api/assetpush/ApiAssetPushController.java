/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.assetpush;

import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.assetpush.PushBean;
import com.hyjf.cs.trade.bean.assetpush.PushRequestBean;
import com.hyjf.cs.trade.bean.assetpush.PushResultBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.borrow.ApiAssetPushService;
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
 * 资产推送接口
 *
 * @author fuqiang
 * @version ApiAssetPushController, v0.1 2018/6/11 17:52
 */
@Api(tags = "api端-资产推送接口")
@RestController
@RequestMapping("/hyjf-api/server/assetpush")
public class ApiAssetPushController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(ApiAssetPushController.class);

    @Autowired
    private ApiAssetPushService pushService;

    @PostMapping("/push.do")
    @ApiParam(required = true, name = "pushRequestBean", value = "个人资产信息")
    @ApiOperation(value = "个人资产推送", httpMethod = "POST", notes = "个人资产推送")
    public PushResultBean push(@RequestBody PushRequestBean pushRequestBean) {
        logger.info(this.getClass().getName(), "api端-资产推送接口 个人资产推送 start", pushRequestBean.toString(), "/hyjf-api/server/assetpush/push.do");

        PushResultBean resultBean = new PushResultBean();
        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
        resultBean.setStatusDesc("请求参数非法");

        // 验证请求参数
        List<PushBean> reqData = pushRequestBean.getReqData();
        if (Validator.isNull(reqData) || Validator.isNull(pushRequestBean.getInstCode())
                || Validator.isNull(pushRequestBean.getAssetType()) || Validator.isNull(pushRequestBean.getChkValue())) {
            logger.info("------请求参数非法-------" + pushRequestBean);
            return resultBean;
        }

        // 验签
        if (!SignUtil.verifyRequestSign(pushRequestBean, "/push")) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            logger.info("-------------------验签失败！--------------------");
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }

        logger.info(pushRequestBean.getInstCode() + " 开始推送资产 ");

        if (CustomConstants.INST_CODE_HYJF.equals(pushRequestBean.getInstCode())) {
            logger.info(pushRequestBean.getInstCode() + "  " + pushRequestBean.getAssetType() + " ------平台不能推送资产");
            return resultBean;
        }

        resultBean = pushService.assetPush(pushRequestBean);

        logger.info(this.getClass().getName(), "api端-资产推送接口 个人资产推送 end", pushRequestBean.toString(), "/hyjf-api/server/assetpush/push.do");

        return resultBean;
    }

    @PostMapping("/pushcompany.do")
    @ApiParam(required = true, name = "pushRequestBean", value = "企业资产信息")
    @ApiOperation(value = "企业资产推送", httpMethod = "POST", notes = "企业资产推送")
    public PushResultBean pushcompany(@RequestBody PushRequestBean pushRequestBean) {
        logger.info(this.getClass().getName(), "api端-资产推送接口 企业资产推送 start", pushRequestBean.toString(), "/hyjf-api/server/assetpush/pushcompany.do");
        PushResultBean resultBean = new PushResultBean();
        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
        resultBean.setStatusDesc("请求参数非法");

        // 验证请求参数
        List<PushBean> reqData = pushRequestBean.getReqData();
        if (Validator.isNull(reqData) || Validator.isNull(pushRequestBean.getInstCode())
                || Validator.isNull(pushRequestBean.getAssetType()) || Validator.isNull(pushRequestBean.getChkValue())) {
            logger.info("------请求参数非法-------" + pushRequestBean);
            return resultBean;
        }

        // 验签
        if (!SignUtil.verifyRequestSign(pushRequestBean, "/pushcompany")) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            logger.info("-------------------验签失败！--------------------");
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }

        logger.info(pushRequestBean.getInstCode() + " 开始推送资产 ");

        if(CustomConstants.INST_CODE_HYJF.equals(pushRequestBean.getInstCode())){
            logger.info(pushRequestBean.getInstCode() + "  "+ pushRequestBean.getAssetType() + " ------不能推送本平台资产");
            resultBean.setStatusDesc("不能推送本平台资产！");
            return resultBean;
        }

        resultBean = pushService.companyAssetPush(pushRequestBean);

        logger.info(this.getClass().getName(), "api端-资产推送接口 企业资产推送 end", "/hyjf-api/server/assetpush/pushcompany.do");
        return resultBean;
    }

}
