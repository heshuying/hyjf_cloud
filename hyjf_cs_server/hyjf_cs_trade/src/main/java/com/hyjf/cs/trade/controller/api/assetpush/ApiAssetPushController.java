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
@RestController
@RequestMapping("/hyjf-api/api")
public class ApiAssetPushController extends BaseTradeController {

    Logger logger = LoggerFactory.getLogger(ApiAssetPushController.class);

    @Autowired
    private ApiAssetPushService pushService;

    @PostMapping("/push")
    public PushResultBean push(@RequestBody PushRequestBean pushRequestBean) {

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
        if (SignUtil.verifyRequestSign(pushRequestBean, "/push")) {
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

        logger.info(pushRequestBean.getInstCode() + " 结束推送资产");

        return resultBean;
    }

}
