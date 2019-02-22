/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.coupon;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.trade.CouponConfigExportCustomizeResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.dao.model.customize.CouponConfigCustomize;
import com.hyjf.am.trade.dao.model.customize.CouponConfigExportCustomize;
import com.hyjf.am.trade.service.front.coupon.CouponConfigService;
import com.hyjf.am.vo.admin.CouponConfigCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigExportCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version CouponConfigController, v0.1 2018/9/27 11:56
 */
@RestController
@RequestMapping("/am-trade/couponConfig")
public class CouponConfigController extends BaseController {

    @Autowired
    private CouponConfigService couponConfigService;

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
        Paginator paginator = new Paginator(couponConfigRequest.getCurrPage(), recordTotal, couponConfigRequest.getPageSize());
        if (couponConfigRequest.getPageSize() == 0) {
            paginator = new Paginator(couponConfigRequest.getCurrPage(), recordTotal);
        }
        List<CouponConfigCustomize> recordList = couponConfigService.getRecordList(mapParam, paginator.getOffset(), paginator.getLimit());
        if (recordTotal > 0) {
            if (!CollectionUtils.isEmpty(recordList)) {
                List<CouponConfigCustomizeVO> cvo = CommonUtils.convertBeanList(recordList, CouponConfigCustomizeVO.class);
                response.setResultList(cvo);
                response.setCount(recordTotal);
                response.setRtn(Response.SUCCESS);
            }else {
                response.setRtn(returnCode);
            }
        }
        return response;
    }

    /**
     * 通过id查询优惠券信息
     * @param couponConfigRequest
     * @return
     */
    @PostMapping("/getCouponConfig")
    public CouponConfigResponse getCouponConfig(@RequestBody CouponConfigRequest couponConfigRequest) {
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

    /**
     * 保存优惠券配置信息
     * @param configRequest
     * @return
     */
    @PostMapping("/saveCouponConfig")
    public CouponConfigResponse saveCouponConfig(@RequestBody @Valid CouponConfigRequest configRequest) {
        CouponConfigResponse ccr = new CouponConfigResponse();
        try {
            if (StringUtils.isNotEmpty(configRequest.getId())) {
                CouponConfig couponConfig = new CouponConfig();
                BeanUtils.copyProperties(configRequest, couponConfig);
                couponConfig.setExpirationDate(Integer.parseInt(configRequest.getExpirationDate()));
                couponConfig.setId(Integer.valueOf(configRequest.getId()));
                couponConfig.setUpdateTime(GetDate.getDate());
                int result = couponConfigService.saveCouponConfig(couponConfig);
                if (result > 0) {
                    ccr.setRtn(Response.SUCCESS);
                } else {
                    ccr.setRtn(Response.FAIL);
                }
            }
        } catch (Exception e) {
            logger.error("保存优惠券发行信息失败", e);
            ccr.setRtn(Response.FAIL);
            ccr.setMessage(Response.FAIL_MSG);
        }
        return ccr;
    }

    /**
     * 插入优惠券配置信息
     * @param couponConfigRequest
     * @return
     */
    @PostMapping("/insertCouponConfig")
    public CouponConfigResponse insertAction(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
        CouponConfigResponse ccr = new CouponConfigResponse();
        try {
            CouponConfig couponConfig = new CouponConfig();
            BeanUtils.copyProperties(couponConfigRequest, couponConfig);
            if (couponConfigRequest.getExpirationDate() != null) {
                couponConfig.setExpirationDate(GetDate.strYYYYMMDD2Timestamp2(couponConfigRequest.getExpirationDate()));
            }
            couponConfig.setCouponCode(GetCode.getCouponCode(couponConfigRequest
                    .getCouponType()));
            couponConfig.setStatus(1);
            couponConfig.setDelFlag(0);
            couponConfig.setUpdateTime(GetDate.getDate());
            couponConfig.setCreateTime(GetDate.getDate());
            int result = couponConfigService.insertAction(couponConfig);
            if (result > 0) {
                ccr.setRtn(Response.SUCCESS);
            } else {
                ccr.setRtn(Response.FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ccr;
    }

    /**
     * 根据id删除优惠券信息
     * @param couponConfigRequest
     * @return
     */
    @PostMapping("/deleteCouponConfig")
    public CouponConfigResponse deleteAction(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
        CouponConfigResponse response = new CouponConfigResponse();
        try {
            if (StringUtils.isNotEmpty(couponConfigRequest.getId())) {
                int result = couponConfigService.deleteCouponConfig(Integer.parseInt(couponConfigRequest.getId()));
                if (result > 0) {
                    response.setRtn(Response.SUCCESS);
                } else {
                    response.setRtn(Response.FAIL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 根据id获取要修改的优惠券信息
     * @param couponConfigRequest
     * @return
     */
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
            ccr.setMessage("优惠券信息为空");
        }
        return ccr;
    }

    /**
     * 审核
     * @param request
     * @return
     */
    @PostMapping("/updateAuditInfo")
    public CouponConfigResponse updateAuditInfo(@RequestBody @Valid CouponConfigRequest request) {
        CouponConfigResponse configResponse = new CouponConfigResponse();
        CouponConfig couponConfig = new CouponConfig();
        BeanUtils.copyProperties(request,couponConfig);
        couponConfig.setId(Integer.parseInt(request.getId()));
        long nowTime = System.currentTimeMillis() / 1000;
        couponConfig.setAuditUser(request.getAuditUser());
        couponConfig.setUpdateUserId(Integer.parseInt(request.getAuditUser()));
        couponConfig.setAuditTime((int)nowTime);
        couponConfig.setUpdateTime(GetDate.getDate());
        couponConfig.setExpirationDate(GetDate.strYYYYMMDD2Timestamp2(request.getExpirationDate()));
        int result = couponConfigService.saveCouponConfig(couponConfig);
        if (result > 0) {
            configResponse.setRtn(Response.SUCCESS);
        } else {
            configResponse.setRtn(Response.FAIL);
            configResponse.setMessage(Response.FAIL_MSG);
        }
        return configResponse;
    }

    /**
     * VIP中心-优惠券发行 查询导出列表总数
     * @param request
     * @return
     */
    @RequestMapping("/getCountForExport")
    public IntegerResponse getCountForExport(@RequestBody CouponConfigRequest request){
        Map<String, Object> mapParam = paramSet(request);
        int recordTotal = couponConfigService.countRecord(mapParam);
        return new IntegerResponse(recordTotal);
    }

    /**
     * 查询导出列表
     * @param request
     * @return
     */
    @RequestMapping("/getExportConfigList")
    public CouponConfigExportCustomizeResponse getExportConfigList(@RequestBody CouponConfigRequest request) {
        CouponConfigExportCustomizeResponse response = new CouponConfigExportCustomizeResponse();
        CouponConfigCustomize configCustomize = new CouponConfigCustomize();
        BeanUtils.copyProperties(request,configCustomize);
        if (request.getCouponType() != null) {
            configCustomize.setCouponType(String.valueOf(request.getCouponType()));
        }
        //分页参数配置
        Paginator paginator = new Paginator(request.getCurrPage(), request.getExportCount(), request.getPageSize());
        configCustomize.setLimitStart(paginator.getOffset());
        configCustomize.setLimitEnd(paginator.getLimit());
        if (request.getTimeStartSrch() != null) {
            configCustomize.setTimeStartSrch(request.getTimeStartSrch() + " 00:00:00");
        }
        if (request.getTimeEndSrch() != null) {
            configCustomize.setTimeEndSrch(request.getTimeEndSrch() + " 23:59:59");
        }
        List<CouponConfigExportCustomize> configExportCustomizes = couponConfigService.getExportRecordList(configCustomize);
        if (!CollectionUtils.isEmpty(configExportCustomizes)) {
            List<CouponConfigExportCustomizeVO> configExportCustomizeVOS = CommonUtils.convertBeanList(configExportCustomizes,CouponConfigExportCustomizeVO.class);
            response.setResultList(configExportCustomizeVOS);
        }
        return response;
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
     * 获取admin优惠券发放配置
     *
     * @param request
     * @return
     */
    @PostMapping("/adminCouponConfig")
    public CouponConfigCustomizeResponse getCouponConfigList(@RequestBody CouponConfigRequest request) {
        CouponConfigCustomizeResponse response = new CouponConfigCustomizeResponse();
        //加载优惠券配置列表
        CouponConfigCustomize configCustomize = new CouponConfigCustomize();
        configCustomize.setStatus(2);
        configCustomize.setLimitStart(-1);
        configCustomize.setLimitEnd(-1);
        List<CouponConfigCustomize> couponConfigCustomizes = couponConfigService.getCouponConfigList(request);
        if (!CollectionUtils.isEmpty(couponConfigCustomizes)) {
            List<CouponConfigCustomizeVO> couponConfigCustomizeVOS = CommonUtils.convertBeanList(couponConfigCustomizes, CouponConfigCustomizeVO.class);
            response.setResultList(couponConfigCustomizeVOS);
        }
        return response;
    }

    /**
     * 根据优惠券编码查询优惠券配置信息
     * @param couponCode
     * @return
     */
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
        if (couponConfigRequest.getTimeStartSrch() != null) {
            map.put("timeStartSrch", couponConfigRequest.getTimeStartSrch() + " 00:00:00");
        }
        if (couponConfigRequest.getTimeEndSrch() != null) {
            map.put("timeEndSrch", couponConfigRequest.getTimeEndSrch() + " 23:59:59");
        }
        return map;
    }
}
