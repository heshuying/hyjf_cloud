/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.borrow.controller.api;

import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.borrow.bean.assetpush.PushBean;
import com.hyjf.cs.borrow.bean.assetpush.PushRequestBean;
import com.hyjf.cs.borrow.bean.assetpush.PushResultBean;
import com.hyjf.cs.borrow.service.ApiAssetPushService;
import com.hyjf.cs.borrow.util.ErrorCodeConstant;
import com.hyjf.cs.borrow.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资产推送接口
 *
 * @author fuqiang
 * @version ApiAssetPushController, v0.1 2018/6/11 17:52
 */
@RestController
@RequestMapping("/api")
public class ApiAssetPushController {

    Logger _log = LoggerFactory.getLogger(ApiAssetPushController.class);

    @Autowired
    private ApiAssetPushService pushService;

    @PostMapping("/push")
    public PushResultBean push(@RequestBody PushRequestBean pushRequestBean, HttpServletRequest request, HttpServletResponse response) {

        PushResultBean resultBean = new PushResultBean();
        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
        resultBean.setStatusDesc("请求参数非法");

        // 验证请求参数
        List<PushBean> reqData = pushRequestBean.getReqData();
        if (Validator.isNull(reqData) || Validator.isNull(pushRequestBean.getInstCode())
                || Validator.isNull(pushRequestBean.getAssetType()) || Validator.isNull(pushRequestBean.getChkValue())) {
            _log.info("------请求参数非法-------" + pushRequestBean);
            return resultBean;
        }

        // 验签
        if (SignUtil.verifyRequestSign(pushRequestBean, "/push")) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            _log.info("-------------------验签失败！--------------------");
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }

        _log.info(pushRequestBean.getInstCode() + " 开始推送资产 ");

        if (CustomConstants.INST_CODE_HYJF.equals(pushRequestBean.getInstCode())) {
            _log.info(pushRequestBean.getInstCode() + "  " + pushRequestBean.getAssetType() + " ------平台不能推送资产");
            return resultBean;
        }

        resultBean = pushService.assetPush(pushRequestBean);

        _log.info(pushRequestBean.getInstCode() + " 结束推送资产");

        return resultBean;
    }

}
