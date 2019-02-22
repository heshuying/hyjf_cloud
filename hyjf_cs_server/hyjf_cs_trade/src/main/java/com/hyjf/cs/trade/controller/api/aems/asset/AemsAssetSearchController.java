package com.hyjf.cs.trade.controller.api.aems.asset;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.api.AsseStatusRequest;
import com.hyjf.am.vo.api.ApiAssetStatusCustomizeVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.bean.AemsAssetStatusRequestBean;
import com.hyjf.cs.trade.bean.AemsAssetStatusResultBean;
import com.hyjf.cs.trade.service.aems.asset.AemsAssetSearchService;
import com.hyjf.common.constants.AemsErrorCodeConstant;
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
 *Aems资产状态查询
 * @author Zha Daojian
 * @date 2018/12/5 11:40
 * @param
 * @return
 **/
@Api(value = "api端-Aems资产状态查询接口",tags = "api端-Aems资产状态查询接口")
@RestController
@RequestMapping("/hyjf-api/aems/asset")
public class AemsAssetSearchController extends BaseController {


    @Autowired
    AemsAssetSearchService amesassetSearchService;

    @PostMapping("/status")
    @ApiParam(required = true, name = "findDetailById", value = "Aems第三方资产状态查询接口")
    @ApiOperation(value = "AEMS第三方资产状态查询接口", httpMethod = "POST", notes = "AEMS第三方资产状态查询接口")
    public AemsAssetStatusResultBean findDetailById(@RequestBody @Valid AemsAssetStatusRequestBean bean) {
        logger.info(bean.getAssetId()+"资产状态查询接口开始-----------------------------");
        logger.info("Aems第三方请求参数："+JSONObject.toJSONString(bean));
        AemsAssetStatusResultBean resultBean = new AemsAssetStatusResultBean();
        resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000001);
        resultBean.setStatusDesc("请求参数非法");
        //空验证
        if (Validator.isNull(bean) || StringUtils.isBlank(bean.getAssetId())
                || StringUtils.isBlank(bean.getInstCode()) ||  StringUtils.isBlank(bean.getChkValue())){
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("资产编号不为空");
            logger.info("-------------------请求参数非法--------------------");
            return resultBean;
        }
        //验签
        if(!SignUtil.AEMSVerifyRequestSign(bean, "/aems/asset/status")){
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_CE000002);
            resultBean.setStatusDesc("验签失败！");
            logger.info("-------------------验签失败！--------------------");
            return resultBean;
        }
        logger.info(bean.getInstCode()+"  ----Aems资产查询接口开始");
        //查询条件
        AsseStatusRequest request = new AsseStatusRequest();
        String assetId = bean.getAssetId();
        String instCode = bean.getInstCode();
        request.setAssetId(assetId);
        request.setInstCode(instCode);
        ApiAssetStatusCustomizeVO assetDetailCustomizeVO = amesassetSearchService.selectAssetStatusById(request);
        if (assetDetailCustomizeVO == null) {
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_ZT000009);
            resultBean.setStatusDesc("未查询到该资产编号（" + assetId + ", " + instCode + "）！");
            logger.error("---------------未查询到该资产编号（" + assetId + ", " + instCode + "）！---------------");
            return resultBean;
        }
        resultBean.setStatusForResponse(AemsErrorCodeConstant.SUCCESS);
        resultBean.setStatusDesc("请求成功");
        resultBean.setNid(assetDetailCustomizeVO.getNid());
        resultBean.setAssetStatus(assetDetailCustomizeVO.getStatus()+"");
        resultBean.setBorrowNid(assetDetailCustomizeVO.getBorrowNid()+"");

        logger.info(bean.getInstCode()+"  ----Aems资产查询接口结束");
        return resultBean;
    }
}
