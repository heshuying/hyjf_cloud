package com.hyjf.am.market.controller;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.auto.AdsType;
import com.hyjf.am.market.service.AppConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;
import com.hyjf.am.vo.market.AppBannerVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lisheng
 * @version AppConfigController, v0.1 2018/7/11 15:09
 */
@RestController
@RequestMapping("/am-market/appconfig")
public class AppConfigController {
    private static final Logger logger = LoggerFactory.getLogger(AppConfigController.class);
    @Autowired
    AppConfigService appConfigService;

    /**
     * 查询广告列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getRecordList")
    public AppBannerResponse findAppBannerData(@RequestBody AppBannerRequest request) {
        AppBannerResponse appBannerResponse = new AppBannerResponse();
        Integer count = appConfigService.countRecordList(request);
        if (count > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), count);
            List<Ads> recordList = appConfigService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
            List<AdsVO> appBannerVos = CommonUtils.convertBeanList(recordList, AdsVO.class);
            appBannerResponse.setResultList(appBannerVos);
        }
        List<AdsType> adsTypeList = appConfigService.getAdsTypeList();
        appBannerResponse.setRecordTotal(count);
        return appBannerResponse;
    }

    /**
     * 插入广告列表
     *
     * @param request
     * @return
     */
    @PostMapping("/insertRecord")
    public AppBannerResponse insertBannerData(@RequestBody AdsWithBLOBsVO request) {
        AppBannerResponse appBannerResponse = new AppBannerResponse();
        Ads ads = new Ads();
        BeanUtils.copyProperties(request, ads);
        Boolean result = appConfigService.insertRecord(ads);
        if(result){
            appBannerResponse.setRtn(Response.SUCCESS);
            appBannerResponse.setMessage(Response.SUCCESS_MSG);
        }else{
            appBannerResponse.setRtn(Response.FAIL);
            appBannerResponse.setMessage(Response.FAIL_MSG);
        }
        return appBannerResponse;
    }

    /**
     * 修改活动维护信息
     *
     * @param form
     * @return
     */
    @PostMapping("/updateRecord")
    public AppBannerResponse updateAction(@RequestBody AdsWithBLOBsVO form) {
        AppBannerResponse appBannerResponse = new AppBannerResponse();
        // 更新
        Boolean result = appConfigService.updateRecord(form);
        if(result){
            appBannerResponse.setRtn(Response.SUCCESS);
            appBannerResponse.setMessage(Response.SUCCESS_MSG);
        }else{
            appBannerResponse.setRtn(Response.FAIL);
            appBannerResponse.setMessage(Response.FAIL_MSG);
        }
        return appBannerResponse;
    }


    /**
     * 修改状态
     *
     * @param form
     * @return
     */
    @PostMapping("/updateStatus")
    public AppBannerResponse updateStatus(@RequestBody AdsWithBLOBsVO form) {
        AppBannerResponse appBannerResponse = new AppBannerResponse();
        // 修改状态
        if (StringUtils.isNotEmpty(form.getIds())) {
            Integer id = Integer.valueOf(form.getIds());
            Ads record = appConfigService.getRecord(id);
            if (record.getStatus() == 1) {
                form.setStatus(0);
            } else {
                form.setStatus(1);
            }
            Boolean result = appConfigService.updateRecord(form);
            if(result){
                appBannerResponse.setRtn(Response.SUCCESS);
                appBannerResponse.setMessage(Response.SUCCESS_MSG);
            }else{
                appBannerResponse.setRtn(Response.FAIL);
                appBannerResponse.setMessage(Response.FAIL_MSG);
            }
        }
        return appBannerResponse;
    }


    /**
     * 删除
     * @return
     */
    @RequestMapping("deleteAppBanner")
    public AppBannerResponse deleteRecordAction(@RequestBody AdsWithBLOBsVO form) {
        AppBannerResponse appBannerResponse = new AppBannerResponse();
        // 解析json字符串
        Integer id = Integer.valueOf(form.getIds());
        boolean result = appConfigService.deleteRecord(id);
        if(result){
            appBannerResponse.setRtn(Response.SUCCESS);
            appBannerResponse.setMessage(Response.SUCCESS_MSG);
        }else{
            appBannerResponse.setRtn(Response.FAIL);
            appBannerResponse.setMessage(Response.FAIL_MSG);
        }

        return appBannerResponse;
    }

}
