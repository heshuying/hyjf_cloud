/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.CouponCheck;
import com.hyjf.am.config.service.CheckService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponCheckResponse;
import com.hyjf.am.resquest.admin.AdminCouponCheckRequest;
import com.hyjf.am.vo.config.CouponCheckVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
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
 * @version AdminCouponCheckController, v0.1 2018/7/4 11:22
 */
@RestController
@RequestMapping("/am-config/checkList")
public class AdminCouponCheckController extends BaseConfigController {

    @Autowired
    private CheckService checkService;

    @PostMapping("/getCheckList")
    public CouponCheckResponse getCheckList(@RequestBody @Valid AdminCouponCheckRequest request) {
        logger.info("优惠券列表..." + JSONObject.toJSON(request));
        CouponCheckResponse response = new CouponCheckResponse();
        Map<String, Object> mapParam = paramSet(request);
        int count = checkService.countCouponCheck(mapParam);
        Paginator paginator = new Paginator(request.getCurrPage(), count, request.getPageSize());
        if (request.getLimit() == 0) {
            paginator = new Paginator(request.getPaginatorPage(), count);
        }
        List<CouponCheck> couponChecks = checkService.searchCouponCheck(mapParam, paginator.getOffset(), paginator.getLimit());
        if (count > 0) {
            if (!CollectionUtils.isEmpty(couponChecks)) {
                List<CouponCheckVO> couponCheckVOS = CommonUtils.convertBeanList(couponChecks, CouponCheckVO.class);
                response.setRecordTotal(count);
                response.setResultList(couponCheckVOS);
                response.setMessage(Response.SUCCESS_MSG);
            }
        }
        return response;
    }


    @PostMapping("/deleteCheckList")
    public CouponCheckResponse deleteCheckList(@RequestBody @Valid AdminCouponCheckRequest request) {
        logger.info("删除优惠券信息..." + JSONObject.toJSON(request));
        CouponCheckResponse response = new CouponCheckResponse();
        int id = request.getId();
        this.checkService.deleteCheckList(id);
        response.setMessage(Response.SUCCESS_MSG);
        return response;
    }


    @PostMapping("/insertCoupon")
    public CouponCheckResponse insertCoupon(@RequestBody @Valid AdminCouponCheckRequest request) {
        logger.info("插入优惠券信息..." + JSONObject.toJSON(request));
        CouponCheckResponse response = new CouponCheckResponse();
        CouponCheck couponCheck = new CouponCheck();
        couponCheck.setFileName(request.getFileName());
        couponCheck.setCreateTime(GetDate.str2Timestamp(request.getCreateTime()));
        couponCheck.setFilePath(request.getFilePath());
        couponCheck.setDeFlag(0);
        couponCheck.setStatus(1);
        int count = checkService.insertCoupon(couponCheck);
        response.setMessage(Response.FAIL_MSG);
        response.setRecordTotal(count);
        if (count > 0) {
            response.setMessage(Response.SUCCESS_MSG);
        }
        return response;
    }


    @GetMapping("/selectCoupon/{id}")
    public CouponCheckVO selectCoupon(@PathVariable String id) {
        CouponCheckVO couponCheckVO = new CouponCheckVO();
        CouponCheck couponCheck = checkService.selectCoupon(id);
        if (couponCheck != null) {
            BeanUtils.copyProperties(couponCheck, couponCheckVO);
        }
        return couponCheckVO;
    }


    @PostMapping("/updateCoupon")
    public CouponCheckResponse updateCoupon(@RequestBody @Valid AdminCouponCheckRequest request) {
        CouponCheckResponse response = new CouponCheckResponse();
        boolean result = checkService.updateCoupon(request);
        response.setBool(result);
        return response;
    }



    /**
     * 封装查询条件
     *
     * @param request
     * @return
     */
    private Map<String, Object> paramSet(AdminCouponCheckRequest request) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("createTimeStart", request.getCreateTimeStart());
        mapParam.put("createTimeEnd", request.getCreateTimeEnd());
        mapParam.put("status", request.getStatus());
        return mapParam;
    }

}
