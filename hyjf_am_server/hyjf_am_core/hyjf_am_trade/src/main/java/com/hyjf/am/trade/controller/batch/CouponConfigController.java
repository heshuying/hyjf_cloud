/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.trade.dao.model.customize.trade.CouponConfigCustomize;
import com.hyjf.am.vo.admin.CouponConfigCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.service.CouponConfigService;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponConfigController, v0.1 2018/6/19 19:20
 */
@RestController
@RequestMapping("/am-trade/couponConfig")
public class CouponConfigController extends BaseController {
    @Autowired
    private CouponConfigService couponConfigService;

    @RequestMapping("/selectCouponConfig/{couponCode}")
    public CouponConfigResponse selectCouponConfig(@PathVariable String couponCode) {
        CouponConfigResponse response = new CouponConfigResponse();
        CouponConfig couponConfig = couponConfigService.selectCouponConfig(couponCode);
        if (couponConfig != null) {
            CouponConfigVO couponConfigVO = new CouponConfigVO();
            BeanUtils.copyProperties(couponConfig, couponConfigVO);
            response.setResult(couponConfigVO);
        }
        return response;
    }

    /**
     * 后台admin优惠券发行列表
     *
     * @param couponConfigRequest
     * @return
     */
    @PostMapping("/getRecordList")
    public CouponConfigCustomizeResponse getRecordList(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
        CouponConfigCustomizeResponse response = new CouponConfigCustomizeResponse();
        String returnCode = Response.FAIL;
        Map<String, Object> mapParam = paramSet(couponConfigRequest);
        int recordTotal = couponConfigService.countRecord(mapParam);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(couponConfigRequest.getPaginatorPage(), recordTotal);
            List<CouponConfigCustomize> recordList = couponConfigService.getRecordList(mapParam, paginator.getOffset(), paginator.getLimit());
            if (!CollectionUtils.isEmpty(recordList)) {
                List<CouponConfigCustomizeVO> cvo = CommonUtils.convertBeanList(recordList, CouponConfigCustomizeVO.class);
                response.setResultList(cvo);
                response.setCount(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
            return response;
        }
        return new CouponConfigCustomizeResponse();
    }


    @PostMapping("/getCouponConfig")
    public CouponConfigResponse getCouponConfig(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
        CouponConfigResponse ccr = new CouponConfigResponse();
        if (!StringUtils.isEmpty(couponConfigRequest.getId())) {
            CouponConfig ccf = couponConfigService.getCouponConfig(Integer.parseInt(couponConfigRequest.getId()));
            if (ccf != null) {
                CouponConfigVO configVO = new CouponConfigVO();
                BeanUtils.copyProperties(ccf, configVO);
                ccr.setResult(configVO);
            }
            return ccr;
        }
        return ccr;
    }


    @PostMapping("/saveCouponConfig")
    public CouponConfigResponse saveCouponConfig(@RequestBody @Valid CouponConfigRequest configRequest) {
        CouponConfigResponse ccr = new CouponConfigResponse();
        try {
            if (StringUtils.isNotEmpty(configRequest.getId())) {
                CouponConfig couponConfig = new CouponConfig();
                BeanUtils.copyProperties(configRequest, couponConfig);
                Map<String, Object> resultMap = couponConfigService.saveCouponConfig(couponConfig);
                if ((Boolean) resultMap.get("success")) {
                    return ccr;
                } else {
                    ccr.setRtn("failed");
                    ccr.setMessage((String) resultMap.get("msg"));
                    return ccr;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ccr.setMessage("校验失败");
        return ccr;
    }


    @PostMapping("/insertCouponConfig")
    public CouponConfigResponse insertAction(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
        CouponConfigResponse ccr = new CouponConfigResponse();
        try {
            CouponConfig couponConfig = new CouponConfig();
            BeanUtils.copyProperties(couponConfigRequest, couponConfig);
            Map<String, Object> resultMap = couponConfigService.insertAction(couponConfig);
            if ((Boolean) resultMap.get("success")) {
                return ccr;
            } else {
                ccr.setRtn("failed");
                ccr.setMessage((String) resultMap.get("msg"));
                return ccr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ccr.setMessage("校验失败");
        return ccr;
    }


    @PostMapping("/deleteCouponConfig")
    public CouponConfigResponse deleteAction(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
        CouponConfigResponse response = new CouponConfigResponse();
        try {
            if (StringUtils.isNotEmpty(couponConfigRequest.getId())) {
                Map<String, Object> resultMap = couponConfigService.deleteCouponConfig(Integer.parseInt(couponConfigRequest.getId()));
                if ((Boolean) resultMap.get("success")) {
                    return response;
                } else {
                    response.setRtn("failed");
                    response.setMessage((String) resultMap.get("msg"));
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setMessage("校验失败");
        return response;
    }

    @PostMapping("/getAuditInfo")
    public CouponConfigResponse getAuditInfo(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
        CouponConfigResponse ccr = new CouponConfigResponse();
        if (!StringUtils.isEmpty(couponConfigRequest.getId())) {
            CouponConfig ccf = couponConfigService.getCouponConfig(Integer.parseInt(couponConfigRequest.getId()));
            if (ccf != null) {
                CouponConfigVO configVO = new CouponConfigVO();
                BeanUtils.copyProperties(ccf, configVO);
                ccr.setResult(configVO);
            }
            return ccr;
        }
        return ccr;
    }


    @PostMapping("/updateAuditInfo")
    public CouponConfigResponse updateAuditInfo(@RequestBody @Valid CouponConfigRequest request) {
        CouponConfigResponse configResponse = new CouponConfigResponse();
        CouponConfig couponConfig = new CouponConfig();
        long nowTime = System.currentTimeMillis() / 1000;
        couponConfig.setAuditUser(request.getAuditUser());
        couponConfig.setUpdateUserId(Integer.parseInt(request.getAuditUser()));
        couponConfig.setAuditTime((int)nowTime);
        couponConfig.setUpdateTime(GetDate.getTimestamp(nowTime));
        Map<String,Object> map = couponConfigService.saveCouponConfig(couponConfig);
        if ((Boolean) map.get("success")) {
            return configResponse;
        } else {
            configResponse.setRtn("failed");
            configResponse.setMessage((String) map.get("msg"));
            return configResponse;
        }
    }


    /**
     * 根据优惠券编号查询已发行数量
     * @param couponCode
     * @return
     */
    @GetMapping("/checkCouponSendExcess/{couponCode}")
    public CouponConfigCustomizeResponse checkCouponSendExcess(@PathVariable String couponCode) {
        CouponConfigCustomizeResponse response = new CouponConfigCustomizeResponse();
        int remain = couponConfigService.checkCouponSendExcess(couponCode);
        if (remain > 0) {
            response.setCount(remain);
            return response;
        }
        response.setMessage(Response.FAIL_MSG);
        return response;
    }


    /**
     * 查询条件设置
     *
     * @param couponConfigRequest
     * @return
     */
    private Map<String, Object> paramSet(CouponConfigRequest couponConfigRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("couponName", couponConfigRequest.getCouponName());
        map.put("couponCode", couponConfigRequest.getCouponCode());
        map.put("couponType", couponConfigRequest.getCouponType());
        map.put("status", couponConfigRequest.getStatus());
        map.put("timeStartSrch", couponConfigRequest.getTimeStartSrch());
        map.put("timeEndSrch", couponConfigRequest.getTimeEndSrch());
        return map;
    }
}
