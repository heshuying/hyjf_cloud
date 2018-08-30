package com.hyjf.cs.trade.controller.api.asset;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.bean.AssetStatusRequestBean;
import com.hyjf.cs.trade.bean.AssetStatusResultBean;
import com.hyjf.cs.trade.service.asset.AssetSearchService;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import com.hyjf.cs.trade.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 资产状态查询
 * @version AssetSearchController, v0.1 2018/8/27 10:11
 * @Author: Zha Daojian
 */
@Api(value = "api端-资产状态查询接口",tags = "api端-资产状态查询接口")
@RestController
@RequestMapping("/hyjf-api/asset")
public class AssetSearchController extends BaseController {

    @Autowired
    AssetSearchService assetSearchService;

    @PostMapping("/status")
    @ApiParam(required = true, name = "findDetailById", value = "第三方资产状态查询接口")
    @ApiOperation(value = "第三方资产状态查询接口", httpMethod = "POST", notes = "第三方资产状态查询接口")
    public AssetStatusResultBean findDetailById(@RequestBody @Valid AssetStatusRequestBean bean) {
        logger.info(bean.getAccountId()+"资产状态查询接口开始-----------------------------");
        logger.info("第三方请求参数："+JSONObject.toJSONString(bean));
        AssetStatusResultBean resultBean = new AssetStatusResultBean();
        resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
        resultBean.setStatusDesc("请求参数非法");

        //空验证
        if (Validator.isNull(bean) || StringUtils.isBlank(bean.getAssetId())
                || StringUtils.isBlank(bean.getInstCode()) ||  StringUtils.isBlank(bean.getChkValue())){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("资产编号不为空");
            logger.error("-------------------请求参数非法--------------------");
            return resultBean;
        }

        if (SignUtil.verifyRequestSign(bean, "/status")) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            logger.info("-------------------验签失败！--------------------");
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }

        logger.info(bean.getInstCode()+"  ----资产查询接口开始");
        //查询条件
        AssetListRequest request = new AssetListRequest();
        String assetId = bean.getAssetId();
        String instCode = bean.getInstCode();
        AssetDetailCustomizeVO assetDetailCustomizeVO = assetSearchService.selectStatusById(request);
        if (assetDetailCustomizeVO == null) {
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_ZT000009);
            resultBean.setStatusDesc("未查询到该资产编号（" + assetId + ", " + instCode + "）！");
            logger.error("---------------未查询到该资产编号（" + assetId + ", " + instCode + "）！---------------");
            return resultBean;
        }
        resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
        resultBean.setStatusDesc("请求成功");
        resultBean.setAssetStatus(assetDetailCustomizeVO.getStatus()+"");
        resultBean.setBorrowNid(assetDetailCustomizeVO.getBorrowNid()+"");

        logger.info(bean.getInstCode()+"  ----资产查询接口结束");
        return resultBean;
    }
}
